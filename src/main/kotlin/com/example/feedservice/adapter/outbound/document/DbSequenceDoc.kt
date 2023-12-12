package com.example.feedservice.adapter.outbound.document

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "db_sequences")
data class DbSequenceDoc(@Id val id: String, val seq: Long)
