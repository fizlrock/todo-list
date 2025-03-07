package dev.fizlrock.todo.restapi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@Configuration
@EnableWebFlux
public class WebConfig implements WebFluxConfigurer {

  @Override
  public void addCorsMappings(CorsRegistry registry) {

    registry
        .addMapping("/**") // Указывает на все эндпоинты
        .allowedOrigins("http://localhost:4200") // Разрешает запросы с любых источников
        .allowedMethods(
            "GET", "POST", "PUT", "DELETE", "OPTIONS") // Разрешает все основные HTTP методы
        .allowedHeaders("*") // Разрешает все заголовки
        .allowCredentials(true) // Разрешает отправку учетных данных (например, cookies)
        .maxAge(3600); // Устанавливает максимальный возраст кэша CORS-заголовков
  }
}
