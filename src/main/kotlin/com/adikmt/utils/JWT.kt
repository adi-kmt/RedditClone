package com.adikmt.utils

import org.mindrot.jbcrypt.BCrypt

private const val SALT = 7

fun hash(password: String): String = BCrypt.hashpw(password, bcryptSalt())
private fun bcryptSalt() = BCrypt.gensalt(SALT)
fun checkPassword(unhashedPassword: String, hashed: String): Boolean = BCrypt.checkpw(unhashedPassword, hashed)