package com.example.feedservice.application.port.outbound

import com.example.feedservice.domain.AdminId
import com.example.feedservice.domain.NewsBody
import com.example.feedservice.domain.NewsDetail

interface ModifyNewsPort {

    suspend fun createNews(adminId: AdminId, newsBody: NewsBody): NewsDetail
    suspend fun deleteNews(adminId: AdminId, newsId: Long)
    suspend fun updateNews(adminId: AdminId, newsId: Long, newsBody: NewsBody): Long
}
