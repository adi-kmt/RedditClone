package com.adikmt.utils.db


/** Database factory */
interface DataBaseFactory {
    fun connect()

    suspend fun dropAll()
}

/**
 * Database config
 *
 * @property driver
 * @property url
 * @property username
 * @property password
 * @property maxPoolSize - indicating max number of connections
 */
data class DatabaseConfig(
    val driver: String,
    val url: String,
    val username: String,
    val password: String,
    val maxPoolSize: Int,
)