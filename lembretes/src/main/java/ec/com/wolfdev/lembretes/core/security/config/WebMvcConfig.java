package ec.com.wolfdev.lembretes.core.security.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class WebMvcConfig implements WebMvcConfigurer {
	@Value("${wolfdev.allowedOrigins}")
	private String[] allowedOrigins;
	
	@Override
	public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
        .allowedOrigins(allowedOrigins)
        .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
        .allowedHeaders("*")
        .allowCredentials(true)
        .maxAge(Const.MAX_AGE_SECS);
		WebMvcConfigurer.super.addCorsMappings(registry);
	}

}
