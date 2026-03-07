package lk.ijse.agmseurekaserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class AgmsEurekaServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AgmsEurekaServerApplication.class, args);
    }

}
