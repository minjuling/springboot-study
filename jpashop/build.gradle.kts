plugins {
	java
	id("org.springframework.boot") version "3.0.2" //
	id("io.spring.dependency-management") version "1.1.0"
}

group = "jpabook"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
} // lombok 때문에 생긴 세팅

repositories {
	mavenCentral()
} // 여기서 library를 다운 받겠다

dependencies {
	//웬만하면 스프링부트가 자기 버전이랑 맞는 라이브러리 버전 다 적어놔서 디펜던시에 안적어도 되는데
	//외부 api 갖다 쓸때는 버전 적어줘여함

	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-devtools")
	implementation("com.github.gavlyukovskiy:p6spy-spring-boot-starter:1.9.0")
	compileOnly("org.projectlombok:lombok")
	runtimeOnly("com.h2database:h2")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	implementation ("org.springframework.boot:spring-boot-starter-validation")
	implementation ("com.fasterxml.jackson.datatype:jackson-datatype-hibernate5-jakarta")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
