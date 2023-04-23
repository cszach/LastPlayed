package com.dnguy38.lastplayed.data

data class MobileSession(
    val name: String,
    val key: String,
    val subscriber: Int
)

class MobileSessionResponse {
    val session: MobileSession? = null
}