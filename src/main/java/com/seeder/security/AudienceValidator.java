package com.seeder.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.jwt.Jwt;

class AudienceValidator implements OAuth2TokenValidator<Jwt> {
	private final String audience;

	AudienceValidator(String audience) {
		this.audience = audience;
	}
	Logger logger = LoggerFactory.getLogger(this.getClass());

	public OAuth2TokenValidatorResult validate(Jwt jwt) {
		if (jwt.getAudience().contains(audience)) {
			logger.info("OAuth2TokenValidatorResult success");
			return OAuth2TokenValidatorResult.success();
		}
		return OAuth2TokenValidatorResult.failure();
	}
}
