package org.jnjeaaaat.openmarket.member.fixture

import com.navercorp.fixturemonkey.kotlin.giveMeKotlinBuilder
import org.jnjeaaaat.openmarket.member.command.SignUpCommand
import org.jnjeaaaat.openmarket.member.dto.request.SignUpRequest
import org.jnjeaaaat.openmarket.member.entity.Member
import org.jnjeaaaat.openmarket.member.type.MemberType
import org.jnjeaaaat.openmarket.support.FixtureMonkeyConfig.fixtureMonkey

object MemberFixture {

    fun signUpRequest(
        email: String = "test@test.com",
        password: String = "Password1!",
        name: String = "test"
    ) = SignUpRequest(
        email = email,
        password = password,
        name = name
    )

    fun signUpCommand(
        email: String = "test@test.com",
        password: String = "Password1!",
        name: String = "test"
    ) = SignUpCommand(
        email = email,
        password = password,
        name = name
    )

    fun member(
        email: String = "test@test.com",
        password: String = "test1234!@",
        name: String = "test_name",
        address: String = "gangnam seoul, korea",
        zipCode: String = "12345",
        memberType: MemberType = MemberType.BUYER
    ): Member {
        return fixtureMonkey.giveMeKotlinBuilder<Member>()
            .set(Member::email, email)
            .set(Member::password, password)
            .set(Member::name, name)
            .set(Member::address, address)
            .set(Member::zipCode, zipCode)
            .set(Member::memberType, memberType)
            .sample()
    }
}