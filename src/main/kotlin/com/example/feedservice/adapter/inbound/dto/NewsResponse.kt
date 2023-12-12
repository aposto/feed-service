package com.example.feedservice.adapter.inbound.dto

data class NewsResponse(val id: Long, val error: String?, val success: Boolean = error == null)
