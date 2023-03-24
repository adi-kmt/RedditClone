package com.adikmt.utils

import org.mindrot.jbcrypt.BCrypt

fun hash(password: String): String = BCrypt.hashpw(password, BCrypt.gensalt())

fun checkPassword(candidate: String, hashed: String): Boolean = BCrypt.checkpw(candidate, hashed)