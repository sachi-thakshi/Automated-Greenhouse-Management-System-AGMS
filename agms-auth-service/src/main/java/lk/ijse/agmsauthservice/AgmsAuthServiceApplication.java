package lk.ijse.agmsauthservice;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class AgmsAuthServiceApplication {

	public static void main(String[] args) {
		System.setProperty("spring.datasource.url", "jdbc:mysql://localhost:3306/agms_auth_db?createDatabaseIfNotExist=true");
		System.setProperty("spring.datasource.username", "root");
		System.setProperty("spring.datasource.password", "Ijse@1234");
		System.setProperty("spring.datasource.driver-class-name", "com.mysql.cj.jdbc.Driver");

		System.setProperty("spring.jpa.hibernate.ddl-auto", "update");
		System.setProperty("spring.jpa.show-sql", "true");
		System.setProperty("spring.jpa.properties.hibernate.dialect", "org.hibernate.dialect.MySQLDialect");

		System.setProperty("spring.application.name", "auth-service");
		System.setProperty("eureka.client.service-url.defaultZone", "http://localhost:8761/eureka/");
		System.setProperty("eureka.client.register-with-eureka", "true");
		System.setProperty("eureka.client.fetch-registry", "true");
		System.setProperty("eureka.instance.prefer-ip-address", "true");

		try {
			Dotenv dotenv = Dotenv.configure().directory("../").ignoreIfMissing().load();
			String secret = dotenv.get("JWT_SECRET_KEY");
			if (secret != null) {
				System.setProperty("jwt.secret", secret);
				System.setProperty("JWT_SECRET_KEY", secret);
			} else {
				String fallback = "emergency_secret_key_32_chars_min_length_for_agms";
				System.setProperty("jwt.secret", fallback);
				System.setProperty("JWT_SECRET_KEY", fallback);
			}
		} catch (Exception e) {
			System.setProperty("jwt.secret", "emergency_secret_key_32_chars_min_length_for_agms");
		}

		SpringApplication.run(AgmsAuthServiceApplication.class, args);
	}
}