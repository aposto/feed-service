package com.example.feedservice.adapter.outbound.document

import com.example.feedservice.adapter.outbound.document.docs.PageDoc
import com.example.feedservice.application.port.outbound.ModifyPagePort
import com.example.feedservice.domain.AdminId
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.stereotype.Component

@Component
class PageDocumentAdapter(private val repo: MongoPageRepository) : ModifyPagePort {

    override suspend fun createPage(admin: AdminId, region: String, schoolName: String): String? {
        return repo.insert(PageDoc(id = admin, region = region, schoolName = schoolName))
            .awaitSingleOrNull()?.id
    }

    override suspend fun deletePage(admin: AdminId, pageId: String) {
        repo.deleteById(pageId).awaitSingleOrNull()
    }
}
