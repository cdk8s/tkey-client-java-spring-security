package com.cdk8s.tkey.client.security.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;


@Slf4j
@Controller
public class TestController {

	@Autowired
	private OAuth2AuthorizedClientService authorizedClientService;

	//=====================================业务处理 start=====================================

	/**
	 * 该地址需要认证，可用于测试认证过程
	 */
	@RequestMapping("/user")
	@ResponseBody
	private OAuth2AuthenticationToken user() {

		OAuth2AuthenticationToken authentication = (OAuth2AuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		log.info("------zch------username <{}>", username);

		Map<String, Object> userInfoAttributes = authentication.getPrincipal().getAttributes();
		log.info("------zch------userInfoAttribute <{}>", userInfoAttributes.toString());

		OAuth2AuthorizedClient authorizedClient = authorizedClientService.loadAuthorizedClient(authentication.getAuthorizedClientRegistrationId(), username);

		OAuth2AccessToken accessToken = authorizedClient.getAccessToken();
		log.info("------zch------tokenValue <{}>", accessToken.getTokenValue());

		return authentication;
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

	//=====================================业务处理  end=====================================

	//=====================================私有方法 start=====================================

	//=====================================私有方法  end=====================================
}
