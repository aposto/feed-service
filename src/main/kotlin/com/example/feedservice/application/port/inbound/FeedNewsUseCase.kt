package com.example.feedservice.application.port.inbound

import arrow.core.Either
import com.example.feedservice.domain.Failure
import com.example.feedservice.domain.NewsBody
import com.example.feedservice.domain.NewsDetail

interface FeedNewsUseCase {
    suspend fun feedNews(adminId: String, body: NewsBody): Either<Failure, NewsDetail>
}

