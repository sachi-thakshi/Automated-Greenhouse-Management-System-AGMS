package lk.ijse.agmszoneservice;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient
public class AgmsZoneServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AgmsZoneServiceApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public RestTemplate restTemplate() {
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();

        // Set timeouts to 3 seconds (3000ms)
        factory.setConnectTimeout(3000);
        factory.setConnectionRequestTimeout(3000);

        return new RestTemplate(factory);
    }
}