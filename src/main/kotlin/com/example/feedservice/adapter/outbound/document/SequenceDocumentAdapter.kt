package com.example.feedservice.adapter.outbound.document

import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.data.mongodb.core.FindAndModifyOptions.options
import org.springframework.data.mongodb.core.ReactiveMongoOperations
import org.springframework.data.mongodb.core.query.Query.query
import org.springframework.data.mongodb.core.query.Criteria.where
import org.springframework.data.mongodb.core.query.Update
import org.springframework.stereotype.Component

@Component
class SequenceDocumentAdapter(
    private val op: ReactiveMongoOperations
) {
    suspend fun generateSequence(seqName: String): Long {
        return op.findAndModify(query(where("_id").`is`(seqName)),
            Update().inc("seq", 1), options().returnNew(true).upsert(true), DbSequenceDoc::class.java)
            .map { it.seq }
            .awaitSingle()
    }
}
