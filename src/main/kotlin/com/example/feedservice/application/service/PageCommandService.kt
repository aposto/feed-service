package com.example.feedservice.application.service

import arrow.core.Either
import com.example.feedservice.application.port.inbound.PageCommand
import com.example.feedservice.application.port.outbound.ModifyPagePort
import com.example.feedservice.domain.AdminId
import com.example.feedservice.domain.Failure
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PageCommandService(private val modifyPagePort: ModifyPagePort) : PageCommand {
    val log = KotlinLogging.logger {  }

    @Transactional
    override suspend fun createPage(admin: AdminId, region: String, schoolName: String): Either<Failure, String> {
        try {
            return modifyPagePort.createPage(admin, region, schoolName)
                ?.let { Either.Right(it) }
                ?: Either.Left(Failure("CREATE PAGE FAILED $admin $region $schoolName"))
        } catch (t: Throwable) {
            log.warn(t) { "Crate Page - ${t.message}" }
            return Either.Left(Failure("CREATE PAGE EXCEPTION $admin $region $schoolName"))
        }
    }
}
