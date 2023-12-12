package com.example.feedservice.application.port.outbound

import com.example.feedservice.domain.AdminId

interface ModifyPagePort {

    /**
     * @return create row id
     */
    suspend fun createPage(admin: AdminId, region: String, schoolName: String): String?

    suspend fun deletePage(admin: AdminId, pageId: String)
}
