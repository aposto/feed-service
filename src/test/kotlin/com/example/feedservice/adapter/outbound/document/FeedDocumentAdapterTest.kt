package com.example.feedservice.adapter.outbound.document

import com.example.feedservice.adapter.outbound.document.docs.makePageId
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class FeedDocumentAdapterTest(val adapter: FeedDocumentAdapter) : FunSpec({

    test("getPageId") {
      val id = adapter.getPageId("sampleRegion", "sampleSchool")
      id shouldBe makePageId("sampleRegion", "sampleSchool")
    }

    test("createPage") {
        val id = adapter.createPage("sampleRegion", "sampleSchool")
        id shouldBe makePageId("sampleRegion", "sampleSchool")
    }
})
