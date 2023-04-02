package com.adikmt.utils

import org.mindrot.jbcrypt.BCrypt

private const val SALT = 7

/**
 * Generates password Hash for given password
 *
 * @param password
 */
fun hash(password: String): String = BCrypt.hashpw(password, bcryptSalt())
private fun bcryptSalt() = BCrypt.gensalt(SALT)

/**
 * Check hashed password with user password
 *
 * @param plaintTextPassword
 * @param hashed
 */
fun checkPassword(plaintTextPassword: String, hashed: String): Boolean = BCrypt.checkpw(plaintTextPassword, hashed)