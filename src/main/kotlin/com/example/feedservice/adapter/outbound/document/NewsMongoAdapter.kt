package com.example.feedservice.adapter.outbound.document

import com.example.feedservice.adapter.outbound.document.docs.NewsDoc
import com.example.feedservice.application.port.outbound.ModifyNewsPort
import com.example.feedservice.domain.AdminId
import com.example.feedservice.domain.NewsBody
import com.example.feedservice.domain.NewsDetail
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class NewsMongoAdapter(private val sequencer: SequenceDocumentAdapter, private val repo: MongoNewsRepository) : ModifyNewsPort {
    companion object {
        const val NEWS_SEQUENCE = "news"
    }
    override suspend fun createNews(adminId: AdminId, newsBody: NewsBody): NewsDetail {
        return sequencer.generateSequence(NEWS_SEQUENCE)
            .let {
                repo.insert(NewsDoc(id = it, writer = adminId, title = newsBody.subject, text = newsBody.text))
            }
            .awaitSingle()
            .let(NewsMapper::mapToDomain)
    }

    override suspend fun deleteNews(adminId: AdminId, newsId: Long) {
        repo.deleteById(newsId).awaitSingleOrNull()
    }

    override suspend fun updateNews(adminId: AdminId, newsId: Long, newsBody: NewsBody): Long {
        return repo.findById(newsId)
            .awaitSingle()
            .let {
                repo.save(it.copy(title = newsBody.subject, text = newsBody.text, updateAt = LocalDateTime.now()))
            }
            .awaitSingle()
            .id
    }
}
