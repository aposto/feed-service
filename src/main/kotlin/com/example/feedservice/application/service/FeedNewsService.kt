package com.example.feedservice.application.service

import arrow.core.Either
import com.example.feedservice.application.port.inbound.FeedNewsUseCase
import com.example.feedservice.application.port.outbound.ModifyNewsPort
import com.example.feedservice.domain.Failure
import com.example.feedservice.domain.NewsBody
import com.example.feedservice.domain.NewsDetail
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class FeedNewsService(private val feedNewsPort: ModifyNewsPort) : FeedNewsUseCase {
    val log = KotlinLogging.logger {  }

    @Transactional
    override suspend fun feedNews(adminId: String, body: NewsBody): Either<Failure, NewsDetail> {
        try {
            return feedNewsPort.createNews(adminId, body)
                .let { Either.Right(it) }
        } catch (t: Throwable) {
            log.warn(t) { "Feed News - ${t.message}" }
            return Either.Left(Failure("CREATE NEWS EXCEPTION $adminId ${t.message}"))
        }
    }
}
