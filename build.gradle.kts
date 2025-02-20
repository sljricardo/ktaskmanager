import org.jooq.meta.jaxb.Logging

plugins {
	kotlin("jvm") version "1.9.25"
	kotlin("plugin.spring") version "1.9.25"
	id("org.springframework.boot") version "3.4.2"
	id("io.spring.dependency-management") version "1.1.7"
	id("nu.studer.jooq") version "9.0"
	id("jacoco")
}

group = "com.sljricardo"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-jooq")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.3.0")

	// Add SQLite JDBC Driver
	implementation("org.xerial:sqlite-jdbc")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")

	implementation("org.jooq:jooq:3.18.8")
	implementation("org.jooq:jooq-meta:3.18.8")
	implementation("org.jooq:jooq-codegen:3.18.8")

	jooqGenerator("org.jooq:jooq-meta:3.18.8")
	jooqGenerator("org.jooq:jooq-codegen:3.18.8")
	jooqGenerator("org.xerial:sqlite-jdbc:3.45.3.0") // Ensure latest SQLite JDBC version
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.jacocoTestReport {
	dependsOn(tasks.test) // Run tests before generating the report
	reports {
		xml.required.set(true)
		html.required.set(true)
		csv.required.set(false)
	}
}

tasks.jacocoTestCoverageVerification {
	violationRules {
		rule {
			element = "CLASS"
			limit {
				counter = "LINE"
				value = "COVEREDRATIO"
				minimum = "0.80".toBigDecimal() // Require at least 80% coverage
			}
		}
	}
}

jacoco {
	toolVersion = "0.8.12"
}

jooq {
	version.set("3.18.8")

	configurations {
		create("main") {
			jooqConfiguration.apply {
				logging = Logging.WARN
				jdbc.apply {
					driver = "org.sqlite.JDBC"
					url = "jdbc:sqlite:./mydb.db"
				}
				generator.apply {
					name = "org.jooq.codegen.KotlinGenerator"
					database.apply {
						name = "org.jooq.meta.sqlite.SQLiteDatabase"
						//inputSchema = "main"
					}
					generate.apply {
						isDeprecated = false
						isRecords = true
						isImmutablePojos = true
						isFluentSetters = true
					}
					target.apply {
						packageName = "com.sljricardo.jooq"
						directory = "src/main/kotlin/generator"
					}
					strategy.name = "org.jooq.codegen.DefaultGeneratorStrategy"
				}
			}
		}
	}
}
