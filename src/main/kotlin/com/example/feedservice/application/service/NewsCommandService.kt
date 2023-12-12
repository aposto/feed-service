package com.example.feedservice.application.service

import com.example.feedservice.application.port.inbound.NewsCommand
import com.example.feedservice.application.port.outbound.ModifyNewsPort
import com.example.feedservice.domain.AdminId
import com.example.feedservice.domain.CommandResult
import com.example.feedservice.domain.NewsBody
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class NewsCommandService(private val modifyNewsPort: ModifyNewsPort): NewsCommand {
    val log = KotlinLogging.logger {  }

    @Transactional
    override suspend fun deleteNews(admin: AdminId, id: Long): CommandResult {
        try {
            modifyNewsPort.deleteNews(admin, id)
            log.info { "Delete News by $admin, id=$id" }
            return CommandResult(null)
        } catch (t: Throwable) {
            log.warn(t) { "Delete News $admin, ${t.message}"}
            return CommandResult(t.message)
        }
    }

    @Transactional
    override suspend fun updateNews(admin: AdminId, id: Long, body: NewsBody): CommandResult {
        try {
            modifyNewsPort.updateNews(admin, id, body)
            log.info { "Update News by $admin, id=$id $body" }
            return CommandResult(null)
        } catch (t: Throwable) {
            log.warn(t) { "Update News $admin, $body, ${t.message} "}
            return CommandResult(t.message)
        }
    }
}
