package kr.co.mrlee.story.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Cross Origin Filter
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry corsRegistry) {
        corsRegistry.addMapping("/**")  // 모든 경로에 허용
        .allowedMethods("*")            // 모든 Method에 허용
        .allowedOrigins("*");           // 모든 출처에 허용

    }
}
