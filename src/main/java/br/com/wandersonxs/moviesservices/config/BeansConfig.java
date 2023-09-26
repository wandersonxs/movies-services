package br.com.wandersonxs.moviesservices.config;

import br.com.wandersonxs.moviesservices.helper.FileHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeansConfig {

    @Bean
    public FileHelper fileHelper() {
        return new FileHelper();
    }

    @Bean
    public ObjectMapper objectMapper(){
        return new ObjectMapper();
    }

}
