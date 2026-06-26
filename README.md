# open-market

오픈마켓의 핵심 도메인을 구현한 Kotlin/Spring Boot 기반 API 프로젝트입니다.

회원 가입부터 상품 등록, 장바구니, 지갑 충전, 주문까지 커머스 서비스의 기본 흐름을 다룹니다.

주문/결제 흐름에서는 Redis 분산락과 RabbitMQ 지연 메시지를 사용해 동시성과 결제 만료 처리를 구현했습니다.

## 기술 스택

| 구분 | 기술 |
| --- | --- |
| Language | Kotlin 2.2.21, Java 17 |
| Framework | Spring Boot 3.4.2, Spring Web, Spring Validation |
| Persistence | Spring Data JPA, Hibernate, QueryDSL |
| Database | H2 |
| Cache | Caffeine, Spring Cache |
| Lock | Redis, Redisson, Spring AOP |
| Message Queue | RabbitMQ, Spring AMQP |
| Logging | kotlin-logging, MDC |
| Test | JUnit5, Kotest, MockK, Fixture Monkey, Testcontainers, MockMvc |
| Infra | Docker Compose |

## 주요 기능

### 회원 가입 및 로그인
- 계정을 생성할 때 지갑, 장바구니를 생성합니다. 
- 회원 가입 진행 시 이메일로 본인 인증을 진행합니다. (예정)
- 로그인 시 JWT 토큰을 발급합니다. (예정)

### 캐시 충전
- 외부 결제 시스템과 연동해 지갑 충전을 진행합니다. (예정)
- 일일 충전 한도는 1,000,000원 입니다.

### 장바구니
- 장바구니에 재고가 남은 상품의 수량을 정해서 담아둘 수 있습니다.
- 장바구니의 상품은 제거, 수량 증가, 구매 의향 체크박스가 있습니다.

### 주문하기
- 장바구니에 담긴 물건중 체크된 상품만 주문할 수 있습니다.
- 하나의 주문으로 한번에 결제할 수 있으며, 결제 후에는 장바구니에서 제거됩니다.
- 주문 생성 후 10분 이내 결제하지 않으면 주문이 취소됩니다.

### 결제하기 (예정)
- 생성한 주문에 대한 결제를 진행합니다.
- 상품 재고가 차감되고 지갑에서 결제 금액이 차감됩니다.
- 배달 대행사와 연동해 배송을 진행합니다. (예정)

## 프로젝트 구조

```text
open-market
├── api
│   ├── controller
│   ├── dto
│   └── exception
├── domain
│   ├── member
│   ├── category
│   ├── product
│   ├── cart
│   ├── order
│   └── wallet
├── support
│   ├── exception
│   ├── jpa
│   ├── event
│   ├── logging
│   ├── redis
│   └── rabbitmq
└── docker
```

## 모듈 구성

| 모듈 | 설명 |
| --- | --- |
| `api` | REST API, 요청/응답 DTO, 전역 예외 처리 |
| `domain` | 도메인 로직, UseCase, Entity, Repository |
| `support:exception` | 공통 예외와 에러 응답 |
| `support:jpa` | BaseEntity, Auditing, QueryDSL 설정 |
| `support:event` | 도메인 이벤트 발행 |
| `support:logging` | TraceId, MDC, Logging AOP |
| `support:redis` | Redis 설정, Redisson 분산락 |
| `support:rabbitmq` | RabbitMQ 설정, 결제 만료 큐 |

## 핵심 구현

### Redis 분산락

지갑 충전과 주문 재고 차감처럼 동시성 제어가 필요한 구간에 Redisson 기반 분산락을 적용했습니다.

- `@DistributedLock`: 단일 리소스 락
- `@DistributedMultiLock`: 여러 상품 재고 차감을 위한 멀티락

### RabbitMQ 결제 만료

주문 생성 후 RabbitMQ TTL queue에 메시지를 발행하고, TTL 만료 후 DLX를 통해 만료 큐로 이동시켜 미결제 주문을 만료 처리합니다.

### 카테고리 캐시

카테고리 트리 조회는 Caffeine local cache를 사용합니다. 카테고리 추가 시 캐시를 무효화해 최신 트리 구조를 다시 조회합니다.
