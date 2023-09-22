package br.com.wandersonxs.moviesservices.config;

import br.com.wandersonxs.moviesservices.helper.FileHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeansConfig {

    @Bean
    public FileHelper fileHelper() {
        return new FileHelper();
    }

}
