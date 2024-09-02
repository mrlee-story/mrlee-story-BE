package kr.co.mrlee.story.provider;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@NoArgsConstructor
@Slf4j
@AllArgsConstructor
public class JwtProvider {
	public static final String BEARER_PREFIX = "Bearer ";
	public static final String AUTHORIZATION_HEADER_KEY = "Authorization";
	public static final long EXPIRATION_TIME_IN_MS = 3600000;
	public static final String ROLE_PREFIX = "ROLE_";
	
	private static final SignatureAlgorithm ALG = SignatureAlgorithm.HS256;
	
	@Value("${jwt-secret-key}")
	private String secretKey;
	
	public String create(String id, List<String>  roles, int numId) {
		Claims claims = Jwts.claims().setSubject(id);
		claims.put("roles", roles);
		claims.put("numId", numId);
		
		Date now = new Date();
		
		/*
		 * JWT 구조
		 * 	- Header (헤더)
		 * 		- typ : 토큰 타입(JWY)
		 * 		- alg : 해싱 알고리즘(토큰 검증 시 signature 부분에서 사용) -> 이 소스에서는 HMAC SHA256 사용
		 * 	- Payload (정보)
		 * 		- registered claim (등록 클레임)
		 * 			- iss : 토큰 발급자(issuer)
		 * 			- sub : 토큰 제목(subject)
		 * 			- aud : 토큰 대상자(audience)
		 * 			- exp : 토큰 만료 기간(expiration) -> 항상 현재 시간 이후로 설정
		 * 			- nbf : 토큰 활성 날짜(not before) -> 이 시간 이전에는 토큰 처리되지 않음
		 * 			- iat : 토큰 발급 시작(issued at)
		 * 			- jti : jwt 고유 식별자
		 *		- public claim (공개 클레임)
		 *			: 충돌이 방지된 이름을 가지고 있어야 하며, 충돌 방지 위해 URI 형식으로 생성
		 *		- private claim (비공개 클레임)
		 *			: 클라이언트-서버 협의 하 사용되는 클레임(이름 중복되어 충돌될 수 있으므로 유의)
		 *	- Signature (서명)
		 *		: 정보의 인코딩 값을 합쳐서 비밀키로 해싱
		 *
		 *			hash로 만든 hex 값을 base64로 인코딩
		 * 	- 
		 */
		
		String jwtAccessToken = BEARER_PREFIX + Jwts.builder().signWith(ALG, secretKey)
				// 클레임 적용
				.setClaims(claims)
				// iat
				.setIssuedAt(now)
				// exp
				.setExpiration(new Date(now.getTime() + EXPIRATION_TIME_IN_MS))
				// nbf
				.setNotBefore(now)
				.compact()
				;
		return jwtAccessToken;
	}
	
	/*
	 * 검증과 Calims 반환을 동시 처리
	 */
	public Claims getValidatedClaims(String jwt) {
		if (!StringUtils.hasText(jwt)) {
			return null;
		}
		
		if (jwt.startsWith(BEARER_PREFIX)) {
			jwt = jwt.replace(BEARER_PREFIX, "");
		}
		
		try {
			return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwt).getBody();
		}  catch (SecurityException | MalformedJwtException e) {
	        log.info("잘못된 JWT 서명입니다.");
	    } catch (ExpiredJwtException e) {
	        log.info("만료된 JWT 토큰입니다.");
	    } catch (UnsupportedJwtException e) {
	        log.info("지원되지 않는 JWT 토큰입니다.");
	    } catch (IllegalArgumentException e) {
	        log.info("JWT 토큰이 잘못되었습니다.");
	    }
		return null;
	}
	
	public String parseBearerToken(HttpServletRequest request) {
        if (request.getHeader("Authorization")==null) return null;
        
        String authorization = request.getHeader("Authorization");

        // authorization 필드를 가지고 있는지 여부
        boolean hasAuthorization = StringUtils.hasText(authorization);
        // authorization value가 Bearer인지 여부
        boolean isBearer = authorization.startsWith(BEARER_PREFIX);

        if (!hasAuthorization || !isBearer) {
            return null;
        }

//        String token = authorization.substring(7);
        String token = authorization.replace(BEARER_PREFIX, "");
        
        return token;
	}
}
