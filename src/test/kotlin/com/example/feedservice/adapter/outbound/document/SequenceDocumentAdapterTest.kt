package com.example.feedservice.adapter.outbound.document

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldNotBe
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class SequenceDocumentAdapterTest(val seq: SequenceDocumentAdapter) : FunSpec({

    test("generateSequence") {
        val s1 = seq.generateSequence("sample")
        val s2 = seq.generateSequence("sample")
        val s3 = seq.generateSequence("sample")

        s1 shouldNotBe s2
        s1 shouldNotBe s3
        s2 shouldNotBe s3
    }
})
