package com.example.feedservice.adapter.outbound.document

import com.example.feedservice.adapter.outbound.document.docs.PageDoc
import com.example.feedservice.adapter.outbound.document.docs.makePageId
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.stereotype.Component

@Component
class FeedDocumentAdapter(
    val pageRepo: MongoPageRepository,
) {
    suspend fun getPageId(region: String, schoolName: String): String {
        return pageRepo.findById(makePageId(region, schoolName)).awaitSingleOrNull()?.id
            ?: createPage(region, schoolName)
    }

    suspend fun createPage(region: String, schoolName: String): String {
        val id = makePageId(region, schoolName)
        return pageRepo.save(PageDoc(id, region, schoolName)).awaitSingle().let { id }
    }
}
