package com.example.feedservice.application.port.inbound

import com.example.feedservice.domain.AdminId
import com.example.feedservice.domain.CommandResult
import com.example.feedservice.domain.NewsBody

interface NewsCommand {
    suspend fun deleteNews(admin: AdminId, id: Long): CommandResult

    suspend fun updateNews(admin: AdminId, id: Long, body: NewsBody): CommandResult
}
