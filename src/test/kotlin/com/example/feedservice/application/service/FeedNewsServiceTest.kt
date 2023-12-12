package com.example.feedservice.application.service

import arrow.core.Either
import com.example.feedservice.application.port.outbound.ModifyNewsPort
import com.example.feedservice.domain.Failure
import com.example.feedservice.domain.NewsBody
import com.example.feedservice.domain.NewsDetail
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.*
import java.time.LocalDateTime

class FeedNewsServiceTest : BehaviorSpec({
    val feedNewsPort = mockk<ModifyNewsPort>()
    val useCase = FeedNewsService(feedNewsPort)

    given("feed news") {
        val newsBody = NewsBody("제목", "내용")
        val admin = "0"

        val now = LocalDateTime.now()

        coEvery { feedNewsPort.createNews(admin, any()) } coAnswers { throw RuntimeException("SYSTEM") }
        coEvery { feedNewsPort.createNews(any(), newsBody) } coAnswers { NewsDetail(1L, newsBody, now) }

        `when`("write valid news") {
            then("result Either.Right NewsDetail") {
                useCase.feedNews(admin, newsBody) shouldBe Either.Right(NewsDetail(1L, newsBody, now))
                coVerify {
                    feedNewsPort.createNews(admin, newsBody)
                }
            }
            `when`("write invalid news ") {
                then("result Either.Left failure") {
                    useCase.feedNews(admin, NewsBody("xxx", "xxx")) shouldBe Either.Left(Failure("CREATE NEWS EXCEPTION 0 SYSTEM"))
                }
            }
        }
    }
})
