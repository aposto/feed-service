package com.example.feedservice.adapter.outbound.document.docs

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document(collection = "pages")
data class PageDoc(
    @Id
    val id: String,
    val region: String,
    val schoolName: String,
    val createAt: LocalDateTime = LocalDateTime.now()
)

fun makePageId(region: String, schoolName: String) = "${region}_$schoolName"
