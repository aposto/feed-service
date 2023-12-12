package com.example.feedservice.adapter.outbound.document

import com.example.feedservice.adapter.outbound.document.docs.NewsDoc
import org.springframework.data.mongodb.repository.ReactiveMongoRepository

interface MongoNewsRepository : ReactiveMongoRepository<NewsDoc, Long> {
}

