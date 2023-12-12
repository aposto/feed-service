package com.example.feedservice.domain

import java.time.LocalDateTime

data class NewsDetail(val id: Long, val body: NewsBody, val cratedAt: LocalDateTime)
