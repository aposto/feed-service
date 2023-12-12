package com.example.feedservice.adapter.outbound.document

import com.example.feedservice.adapter.outbound.document.docs.NewsDoc
import com.example.feedservice.domain.NewsBody
import com.example.feedservice.domain.NewsDetail

object NewsMapper {
    fun mapToDomain(doc: NewsDoc): NewsDetail =
        NewsDetail(doc.id, body = NewsBody(subject = doc.title, text = doc.text), cratedAt = doc.createAt)
}
