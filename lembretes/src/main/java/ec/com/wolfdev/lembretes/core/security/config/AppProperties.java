package ec.com.wolfdev.lembretes.core.security.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@ConfigurationProperties(prefix = "app")
public class AppProperties {
	
	private final Auth auth = new Auth();
    private final OAuth2 oauth2 = new OAuth2();
	
	@NoArgsConstructor
	@Getter @Setter
    public static class Auth {
        private String tokenSecret;
        private long tokenExpirationMsec;
    }

	@NoArgsConstructor
    public static final class OAuth2 {
        private @Getter List<String> authorizedRedirectUris = new ArrayList<>();

        public OAuth2 authorizedRedirectUris(List<String> authorizedRedirectUris) {
            this.authorizedRedirectUris = authorizedRedirectUris;
            return this;
        }
    }
}
