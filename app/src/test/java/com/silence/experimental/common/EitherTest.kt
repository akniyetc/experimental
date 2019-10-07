package com.silence.experimental.common

import com.silence.experimental.UnitTest
import com.silence.experimental.common.domain.entity.Either
import com.silence.experimental.common.domain.entity.Either.*
import org.amshove.kluent.shouldBe
import org.amshove.kluent.shouldBeInstanceOf
import org.amshove.kluent.shouldEqualTo
import org.junit.Test

class EitherTest : UnitTest() {

    @Test
    fun `either right should return correct type`() {
        val result = Right("right")

        result shouldBeInstanceOf Either::class.java
        result.isRight shouldBe true
        result.isLeft shouldBe false
        result.either({},
            { right ->
                right shouldBeInstanceOf String::class.java
                right shouldEqualTo "right"
            })
    }

    @Test
    fun `either left should return correct type`() {
        val result = Left("left")

        result shouldBeInstanceOf Either::class.java
        result.isRight shouldBe false
        result.isLeft shouldBe true
        result.either({ left ->
            left shouldBeInstanceOf String::class.java
            left shouldEqualTo "left"
        }, {})
    }
}