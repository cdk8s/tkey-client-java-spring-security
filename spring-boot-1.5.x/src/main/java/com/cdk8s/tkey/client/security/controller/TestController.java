package com.cdk8s.tkey.client.security.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.Map;


@Slf4j
@Controller
public class TestController {

	/**
	 * 该地址需要认证，可用于测试认证过程
	 */
	@RequestMapping("/user")
	@ResponseBody
	private Principal user(Principal principal) {
		if (principal instanceof OAuth2Authentication) {
			OAuth2Authentication oAuth2Authentication = (OAuth2Authentication) principal;

			Authentication userAuthentication = oAuth2Authentication.getUserAuthentication();
			Map userInfoAttribute = (Map) userAuthentication.getDetails();
			log.info("------zch------userInfoAttribute <{}>", userInfoAttribute.toString());

			String username = (String) oAuth2Authentication.getPrincipal();
			log.info("------zch------username <{}>", username);

			OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) oAuth2Authentication.getDetails();
			String tokenValue = details.getTokenValue();
			log.info("------zch------tokenValue <{}>", tokenValue);
		}

		return principal;
	}

	@RequestMapping("/")
	@ResponseBody
	private String index() {
		return "这是首页，无需认证。请访问 /user";
	}

	@RequestMapping("/logoutSuccess")
	@ResponseBody
	private String logoutSuccess() {
		return "登出成功";
	}

}
