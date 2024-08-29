group = "com.sohosquared"
version = "0.0.1-SNAPSHOT"
description = "qa-assignment"
java.sourceCompatibility = JavaVersion.VERSION_21

plugins {
	java
	`java-library`
	jacoco
	`project-report`
	id("org.springframework.boot") version "3.3.3"
	id("io.spring.dependency-management") version "1.1.6"
}

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenCentral()
}

val springdocOpenapiStarterWebfluxUiVersion = "2.3.0"
val swaggerAnnotationsVersion = "2.2.15"

dependencies {
	implementation("org.springframework.boot:spring-boot-starter")
	api("org.springdoc:springdoc-openapi-starter-webflux-ui:$springdocOpenapiStarterWebfluxUiVersion")
	api("org.springframework.boot:spring-boot-starter-webflux")
	annotationProcessor("io.swagger.core.v3:swagger-annotations:$swaggerAnnotationsVersion")
	annotationProcessor ("org.springframework.boot:spring-boot-configuration-processor")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
	testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
	testImplementation("io.rest-assured:rest-assured:5.4.0")
}

tasks.test {
	finalizedBy(tasks.jacocoTestReport)
	useJUnitPlatform()
	// Show the standard output and error streams
	testLogging {
		events("passed", "skipped", "failed")
		showStandardStreams = true
	}
}

tasks.jacocoTestReport {
	reports {
		xml.required.set(true)
	}
	dependsOn(tasks.test)
}