package kr.co.mrlee.story.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import jakarta.servlet.DispatcherType;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.co.mrlee.story.common.UserAuthority;
import kr.co.mrlee.story.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;

@Configurable
@Configuration
@EnableWebSecurity 
@RequiredArgsConstructor
public class WebSecurityConfig {
	
	private final JwtAuthenticationFilter jwtAuthenticationFilter;
	
	@Bean
	protected SecurityFilterChain configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
			// 기본 CORS 정책을 사용
			.cors(Customizer.withDefaults())
			// 크로트 사이트 위조 미사용(REST API)
			.csrf(csrf -> csrf.disable())
			// http basic 인증 미사용
			.httpBasic(httpBasic -> httpBasic.disable())
			// 세션 인증 미사용
			.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			// 인가 옵션 설정
			.authorizeHttpRequests(
				auth -> {
					auth.dispatcherTypeMatchers(DispatcherType.REQUEST, DispatcherType.FORWARD).permitAll()
						.requestMatchers(HttpMethod.PATCH, "/api/board/**").hasAnyRole(UserAuthority.ADMIN.getRoleName(), UserAuthority.MEMBER.getRoleName(), UserAuthority.WRITER.getRoleName())
						.requestMatchers(HttpMethod.DELETE, "/api/board/**").hasAnyRole(UserAuthority.ADMIN.getRoleName(), UserAuthority.MEMBER.getRoleName(), UserAuthority.WRITER.getRoleName())
						.anyRequest().permitAll()
					;
				}
				)/* .anonymous(anonymous -> anonymous.disable()) */
			.exceptionHandling(handle -> handle.authenticationEntryPoint(new FailedAuthenticationEntryPoint()));
		;
		
		// 필터 추가
		httpSecurity.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
		return httpSecurity.build();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
}

class FailedAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        String jsonResponse = "{\"code\":\"AF\", \"message\":\"Authorization Failed(Filter)\"}";
        response.getWriter().write(jsonResponse);
	}
	
}
