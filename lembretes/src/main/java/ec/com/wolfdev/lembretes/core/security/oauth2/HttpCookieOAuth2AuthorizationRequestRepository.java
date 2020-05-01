package ec.com.wolfdev.lembretes.core.security.oauth2;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.stereotype.Component;

import com.nimbusds.oauth2.sdk.util.StringUtils;

import ec.com.wolfdev.lembretes.core.security.Util.CookieUtils;
import ec.com.wolfdev.lembretes.core.security.config.Const;

@Component
public class HttpCookieOAuth2AuthorizationRequestRepository
		implements AuthorizationRequestRepository<OAuth2AuthorizationRequest> {

	public String oauth2AuthorizationRequestCookieName = Const.OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME;
	public String redirectUriParamCookieName = Const.REDIRECT_URI_PARAM_COOKIE_NAME;
	private int cookieExpireSeconds = 180;

	@Override
	public OAuth2AuthorizationRequest loadAuthorizationRequest(HttpServletRequest request) {
		return CookieUtils.getCookie(request, oauth2AuthorizationRequestCookieName)
				.map(cookie -> CookieUtils.deserialize(cookie, OAuth2AuthorizationRequest.class)).orElse(null);
	}

	@Override
	public void saveAuthorizationRequest(OAuth2AuthorizationRequest authorizationRequest, HttpServletRequest request,
			HttpServletResponse response) {
		if (authorizationRequest == null) {
			CookieUtils.deleteCookie(request, response, oauth2AuthorizationRequestCookieName);
			CookieUtils.deleteCookie(request, response, redirectUriParamCookieName);
			return;
		}

		CookieUtils.addCookie(response, oauth2AuthorizationRequestCookieName,
				CookieUtils.serialize(authorizationRequest), cookieExpireSeconds);
		String redirectUriAfterLogin = request.getParameter(redirectUriParamCookieName);
		if (StringUtils.isNotBlank(redirectUriAfterLogin)) {
			CookieUtils.addCookie(response, redirectUriParamCookieName, redirectUriAfterLogin, cookieExpireSeconds);
		}
	}

	@Override
	public OAuth2AuthorizationRequest removeAuthorizationRequest(HttpServletRequest request) {
		return this.loadAuthorizationRequest(request);
	}

	public void removeAuthorizationRequestCookies(HttpServletRequest request, HttpServletResponse response) {
		CookieUtils.deleteCookie(request, response, oauth2AuthorizationRequestCookieName);
		CookieUtils.deleteCookie(request, response, redirectUriParamCookieName);
	}
}
