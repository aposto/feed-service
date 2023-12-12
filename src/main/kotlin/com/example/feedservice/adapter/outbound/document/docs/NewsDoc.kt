package com.example.feedservice.adapter.outbound.document.docs

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document(collection = "news")
data class NewsDoc(
    @Id
    val id: Long,
    val writer: String,
    val title: String,
    val text: String,
    val updateAt: LocalDateTime = LocalDateTime.now(),
    val createAt: LocalDateTime = LocalDateTime.now()
)
