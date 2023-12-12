package com.example.feedservice.adapter.outbound.document

import com.example.feedservice.adapter.outbound.document.docs.PageDoc
import org.springframework.data.mongodb.repository.ReactiveMongoRepository

interface MongoPageRepository : ReactiveMongoRepository<PageDoc, String> {
}
