package ec.com.wolfdev.lembretes.core.security.config;

public interface Const {
	public final long MAX_AGE_SECS = 3600;
	
	public final int COOKIE_EXPIRE_SECONDS = 180;	
	public final String OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME = "oauth2_auth_request";
	public final String REDIRECT_URI_PARAM_COOKIE_NAME = "redirect_uri";
	
	public final String TOKEN_TYPE = "Bearer";
}
