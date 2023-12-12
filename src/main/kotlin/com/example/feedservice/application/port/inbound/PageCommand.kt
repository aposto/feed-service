package com.example.feedservice.application.port.inbound

import arrow.core.Either
import com.example.feedservice.domain.AdminId
import com.example.feedservice.domain.Failure


interface PageCommand {
    suspend fun createPage(admin: AdminId, region: String, schoolName: String): Either<Failure, String>
}
