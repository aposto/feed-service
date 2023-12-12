package com.example.feedservice.application.service

import com.example.feedservice.application.port.outbound.ModifyNewsPort
import com.example.feedservice.domain.CommandResult
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.*

class NewsCommandServiceTest : BehaviorSpec({
    val modifyNewsPort = mockk< ModifyNewsPort>()
    val useCase = NewsCommandService(modifyNewsPort)

//    afterTest {
//        unmockkAll()
//    }

    given("deleteNews") {
        val newsId = 1L
        val notNewsId = 99999L
        val admin = "0"

        coEvery {modifyNewsPort.deleteNews(any(), any()) } throws RuntimeException("NOT_FOUND")
        coEvery {modifyNewsPort.deleteNews(any(), newsId) } just Runs

        `when`("delete in documents") {
            then("CommandResult success and error is null") {
                useCase.deleteNews(admin, newsId).success shouldBe true
                coVerify { modifyNewsPort.deleteNews(admin, newsId) }
            }
        }
        `when`("delete in not-found-documents") {
            then("Has CommandResult error string") {
                useCase.deleteNews(admin, notNewsId).error shouldBe "NOT_FOUND"
            }
        }
    }

    given("updateNews") {

    }
})
