package ec.com.wolfdev.lembretes.application.config;

import static springfox.documentation.builders.PathSelectors.regex;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.client.LinkDiscoverer;
import org.springframework.hateoas.client.LinkDiscoverers;
import org.springframework.hateoas.mediatype.collectionjson.CollectionJsonLinkDiscoverer;
import org.springframework.plugin.core.SimplePluginRegistry;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	@Bean
    public Docket lembreteApi() {

        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("ec.com.wolfdev.lembretes.modules"))
                .paths(regex("/api.*|/auth.*"))
                .build()
                .apiInfo(metaInfo())
                .securitySchemes(Arrays.asList(apiKey()));
    }

    private ApiInfo metaInfo() {

        ApiInfo apiInfo = new ApiInfo(
                "Lembretes API REST",
                "API REST de gestão de lembretes.",
                "1.0",
                "Terms of Service",
                new Contact("Alex Villa", "https://github.com/BillyAlexander/",
                        "alex22wb@gmail.com"),
                "MIT License",
                "https://github.com/BillyAlexander/membretes/blob/master/LICENSE", new ArrayList<VendorExtension>()
        );

        return apiInfo;
    }
    
    @Bean
    public LinkDiscoverers discoverers() {
        List<LinkDiscoverer> plugins = new ArrayList<>();
        plugins.add(new CollectionJsonLinkDiscoverer());
        return new LinkDiscoverers(SimplePluginRegistry.create(plugins));

    }
    
    private ApiKey apiKey() {
        return new ApiKey("accessToken", "Authorization", "header");
    }

}
