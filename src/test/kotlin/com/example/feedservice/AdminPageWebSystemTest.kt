package com.example.feedservice

import com.example.feedservice.adapter.inbound.dto.PageRequest
import com.example.feedservice.adapter.inbound.dto.PageResponse
import com.example.feedservice.adapter.outbound.document.docs.makePageId
import com.example.feedservice.application.port.outbound.ModifyPagePort
import io.kotest.core.spec.style.BehaviorSpec
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.BodyInserters

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class AdminPageWebSystemTest(
    private val webClient: WebTestClient,
    private val modifyPagePort: ModifyPagePort,
) : BehaviorSpec({



    given("POST /admin/v1/page") {
        val requestBody = PageRequest("demo-region", "demo-school")
        `when`("신규 페이지 생성") {
            val response = webClient.post()
                .uri("/admin/v1/page")
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(requestBody))
                .exchange()
            then("status 200") {
                response
                    .expectStatus().isOk
                    .expectBody(PageResponse::class.java)
                    .returnResult().responseBody
            }
        }
        `when`("이미 존재 하는 페이지 생성") {
            val response = webClient.post()
                .uri("/admin/v1/page")
                .body(BodyInserters.fromValue(requestBody))
                .exchange()
            then("status 400 BAD_REQUEST") {
                response
                    .expectStatus().isBadRequest
            }
        }
    }

    afterSpec {
        modifyPagePort.deletePage("0", makePageId("demo-region", "demo-school"))
    }

})
