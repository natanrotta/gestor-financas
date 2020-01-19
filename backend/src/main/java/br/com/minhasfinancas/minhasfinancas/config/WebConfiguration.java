package br.com.minhasfinancas.minhasfinancas.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebMvc
@Configuration
public class WebConfiguration implements WebMvcConfigurer {
    //Class responsável por liberar as requisições
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //Adiciona as URL de onde vai vir as requisições
        registry.addMapping("/**").allowedMethods("GET", "POST","PUT", "DELETE", "OPTIONS");
    }
}
