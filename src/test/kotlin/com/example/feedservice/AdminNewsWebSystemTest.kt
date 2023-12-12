package com.example.feedservice

import com.example.feedservice.adapter.inbound.dto.NewsResponse
import com.example.feedservice.adapter.inbound.dto.PageRequest
import com.example.feedservice.adapter.inbound.dto.PageResponse
import com.example.feedservice.adapter.outbound.document.docs.makePageId
import com.example.feedservice.application.port.outbound.ModifyPagePort
import com.example.feedservice.domain.NewsBody
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.longs.shouldBeGreaterThan
import io.kotest.matchers.shouldBe
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.BodyInserters
import kotlin.random.Random

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class AdminNewsWebSystemTest(
    private val webClient: WebTestClient,
) : BehaviorSpec({

    var lastId = 0L

    given("POST /admin/v1/news") {
        val requestBody = NewsBody("샘플 뉴스피드:${Random.nextInt()}", "샘플 뉴스피드 본문")
        `when`("새로운 뉴스 피드") {
            val response = webClient.post()
                .uri("/admin/v1/news")
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(requestBody))
                .exchange()
            then("status 200") {
                response.expectStatus().isOk
            }
            then("new ID generated") {
                val data = response
                    .expectBody(NewsResponse::class.java)
                    .returnResult().responseBody
                data!!.id shouldBeGreaterThan(0)
                data.error shouldBe null
                lastId = data.id
            }
        }
    }

    given("PUT /admin/v1/news/{newsId}") {
        `when`("최근 만든 뉴스 수정 id:$lastId") {
            val updateBody = NewsBody("수정 샘플 뉴스피드:${Random.nextInt()}", "수정된 뉴스피드 본문")
            val response = webClient.put()
                .uri("/admin/v1/news/{newsId}", lastId)
                .body(BodyInserters.fromValue(updateBody))
                .exchange()
            then("status 200") {
                response.expectStatus().isOk
            }
        }
    }

    given("DELETE /admin/v1/news/{newsId}") {
        `when`("최근 만든 뉴스 삭제 id:$lastId") {
            val response = webClient.delete()
                .uri("/admin/v1/news/{newsId}", lastId)
                .exchange()
            then("status 200") {
                response.expectStatus().isOk
            }
        }
    }


    afterSpec {
    }

})
