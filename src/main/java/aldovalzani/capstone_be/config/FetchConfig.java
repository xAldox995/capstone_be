package aldovalzani.capstone_be.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class FetchConfig {
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
