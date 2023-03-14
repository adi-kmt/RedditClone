object Deps {

  const val logback = "ch.qos.logback:logback-classic:${Versions.logback}"
  const val kotlinLogging = "io.github.microutils:kotlin-logging-jvm:${Versions.kotlinLogging}"
  const val mysqlConnector = "mysql:mysql-connector-java:${Versions.mysqlConnector}"
  const val hikaricp = "com.zaxxer:HikariCP:${Versions.hikaricp}"
  const val jbcrypt = "org.mindrot:jbcrypt:${Versions.jbcrypt}"
  const val serializationJson = "org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.serializationJson}"
  const val scientist = "com.github.spoptchev:scientist:${Versions.scientist}"
  const val coroutinesSlf4j = "org.jetbrains.kotlinx:kotlinx-coroutines-slf4j:${Versions.coroutines}"
  const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"

  object Arrow {
    const val stack = "io.arrow-kt:arrow-stack:${Versions.arrow}"
    const val core = "io.arrow-kt:arrow-core"
  }

  object Ktor {
    const val core = "io.ktor:ktor-server-core:${Versions.ktor}"
    const val auth = "io.ktor:ktor-auth:${Versions.ktor}"
    const val jwt = "io.ktor:ktor-auth-jwt:${Versions.ktor}"
    const val session = "io.ktor:ktor-server-sessions:${Versions.ktor}"
    const val locations = "io.ktor:ktor-locations:${Versions.ktor}"
    const val hostCommon = "io.ktor:ktor-server-host-common:${Versions.ktor}"
    const val serialization = "io.ktor:ktor-serialization:${Versions.ktor}"
    const val serializationJson = "io.ktor:ktor-serialization-kotlinx-json:${Versions.ktor}"
    const val netty = "io.ktor:ktor-server-netty:${Versions.ktor}"
    const val contentNegotiation = "io.ktor:ktor-server-content-negotiation:${Versions.ktor}"

    object Client {
      const val core = "io.ktor:ktor-client-core:${Versions.ktor}"
      const val cio = "io.ktor:ktor-client-cio:${Versions.ktor}"
      const val serialization = "io.ktor:ktor-client-serialization:${Versions.ktor}"
      const val auth = "io.ktor:ktor-client-auth:${Versions.ktor}"
    }
  }

  object Exposed {
    const val core = "org.jetbrains.exposed:exposed-core:${Versions.exposed}"
    const val dao = "org.jetbrains.exposed:exposed-dao:${Versions.exposed}"
    const val jdbc = "org.jetbrains.exposed:exposed-jdbc:${Versions.exposed}"
    const val javaTime = "org.jetbrains.exposed:exposed-java-time:${Versions.exposed}"
  }

  object Koin {
    const val koin = "io.insert-koin:koin-ktor:${Versions.koin}"
    const val koinLogger = "io.insert-koin:koin-logger-slf4j:${Versions.koin}"
  }

  object Testing {
    const val coroutinesTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.kotlin}"
    const val serverTest = "io.ktor:ktor-server-test-host:${Versions.ktor}"
    const val kotestJunit5 = "io.kotest:kotest-runner-junit5:${Versions.kotest}"
    const val kotestAssertionsCore = "io.kotest:kotest-assertions-core:${Versions.kotest}"
    const val kotestAssertionsJson = "io.kotest:kotest-assertions-json:${Versions.kotest}"
    const val kotestAssertionsKtor = "io.kotest.extensions:kotest-assertions-ktor:1.0.3"
    const val kotestAssertionsArrow = "io.kotest.extensions:kotest-assertions-arrow:1.2.1"
    const val h2 = "com.h2database:h2:${Versions.h2}"
  }
}
