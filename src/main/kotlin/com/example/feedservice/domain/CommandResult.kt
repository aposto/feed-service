package com.example.feedservice.domain

data class CommandResult(val error: String?, val success: Boolean = error == null)
