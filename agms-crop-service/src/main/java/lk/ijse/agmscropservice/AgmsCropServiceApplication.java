package lk.ijse.agmscropservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class AgmsCropServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(AgmsCropServiceApplication.class, args);
    }
}