package kr.co.mrlee.story.filter;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.co.mrlee.story.provider.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtProvider jwtProvider;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		try {
			String token = jwtProvider.parseBearerToken(request);
			Claims claims = jwtProvider.getValidatedClaims(token);
			
			if (claims == null) {
				filterChain.doFilter(request, response);
				return;
			}
			
			String id = claims.getSubject();
			Collection<? extends GrantedAuthority> roles = ((List<String>)claims.get("roles")).stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
			
			AbstractAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(id, null, roles);
			authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			
			SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
			securityContext.setAuthentication(authToken);
			
			SecurityContextHolder.setContext(securityContext);
		} catch(Exception e) {
			log.warn(e.getMessage());
		}
		
		filterChain.doFilter(request, response);
	}

	
}
