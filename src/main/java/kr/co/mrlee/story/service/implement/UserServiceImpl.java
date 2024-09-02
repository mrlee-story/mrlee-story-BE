package kr.co.mrlee.story.service.implement;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import kr.co.mrlee.story.common.UserAuthority;
import kr.co.mrlee.story.dto.response.ResponseDto;
import kr.co.mrlee.story.dto.response.user.GetSignInUserResponseDto;
import kr.co.mrlee.story.dto.response.user.GuestVO;
import kr.co.mrlee.story.dto.response.user.MemberVO;
import kr.co.mrlee.story.dto.response.user.UserVO;
import kr.co.mrlee.story.entity.GuestEntity;
import kr.co.mrlee.story.entity.MemberEntity;
import kr.co.mrlee.story.provider.JwtProvider;
import kr.co.mrlee.story.repository.GuestRepository;
import kr.co.mrlee.story.repository.MemberRepository;
import kr.co.mrlee.story.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
	
	private final JwtProvider jwtProvider;
	private final MemberRepository memberRepo;
	private final GuestRepository guestRepo;

	@Override
	public ResponseEntity<? super GetSignInUserResponseDto> getSignInUser(String token) {
		UserVO user = null;
		
		try {
			Claims claims = jwtProvider.getValidatedClaims(token);
			
			if (claims==null) {
				return GetSignInUserResponseDto.authorizationFail();
			}
			
			List<String> roles = (List<String>)claims.get("roles");
			int numId = (int)claims.get("numId");
			
			boolean isMember = roles.stream().anyMatch( role -> role.equalsIgnoreCase(UserAuthority.ADMIN.getRoleName()) || role.equalsIgnoreCase(UserAuthority.MEMBER.getRoleName()) );
			boolean isGuest = roles.stream().anyMatch( role -> role.equalsIgnoreCase(UserAuthority.WRITER.getRoleName()) || role.equalsIgnoreCase(UserAuthority.COMMENTER.getRoleName()) );
			
			if (isMember) {
				MemberEntity member = memberRepo.findByMemberNumber(numId);
				if (member==null) {
					return GetSignInUserResponseDto.noExistUser();
				}
				user = new MemberVO(member);
			} else if (isGuest) {
				GuestEntity guest = guestRepo.findByGuestNumber(numId);
				if (guest==null) {
					return GetSignInUserResponseDto.noExistUser();
				}
				user = new GuestVO(guest);
			} else {
				return GetSignInUserResponseDto.noExistUser();
			}
			
		} catch (Exception e) {
			log.warn(e.getMessage());
			return ResponseDto.databaseError();
		}
		return GetSignInUserResponseDto.success(user);
	}

}
