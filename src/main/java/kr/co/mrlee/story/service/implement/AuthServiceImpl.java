package kr.co.mrlee.story.service.implement;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import io.jsonwebtoken.Claims;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import kr.co.mrlee.story.common.ResponseStatus;
import kr.co.mrlee.story.common.UserAuthority;
import kr.co.mrlee.story.dto.request.auth.SignInGuestRequestDto;
import kr.co.mrlee.story.dto.request.auth.SignInRequestDto;
import kr.co.mrlee.story.dto.request.auth.SignUpRequestDto;
import kr.co.mrlee.story.dto.response.ResponseDto;
import kr.co.mrlee.story.dto.response.auth.SignInGuestResponseDto;
import kr.co.mrlee.story.dto.response.auth.SignInResponseDto;
import kr.co.mrlee.story.dto.response.auth.SignUpResponseDto;
import kr.co.mrlee.story.dto.response.board.DeleteCommentResponseDto;
import kr.co.mrlee.story.entity.BoardEntity;
import kr.co.mrlee.story.entity.BoardImageEntity;
import kr.co.mrlee.story.entity.CommentEntity;
import kr.co.mrlee.story.entity.GuestEntity;
import kr.co.mrlee.story.entity.MemberEntity;
import kr.co.mrlee.story.provider.JwtProvider;
import kr.co.mrlee.story.repository.BoardImageRepository;
import kr.co.mrlee.story.repository.BoardRepository;
import kr.co.mrlee.story.repository.CommentRepository;
import kr.co.mrlee.story.repository.FileRepository;
import kr.co.mrlee.story.repository.GuestRepository;
import kr.co.mrlee.story.repository.MemberRepository;
import kr.co.mrlee.story.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {
	
	private final JwtProvider jwtProvider;
	
	private final MemberRepository memberRepo;
	private final GuestRepository guestRepo;
	private final BoardRepository boardRepo;
	private final CommentRepository commentRepo;
	private final BoardImageRepository imageRepo;
	private final FileRepository fileRepo;
	
	private final PasswordEncoder passwordEncoder;
	

	@Override
	public ResponseEntity<? super SignUpResponseDto> signUp(@Valid SignUpRequestDto dto) {
		try {
			// 관리자는 회원가입 불가
			if (dto.getAuthorizationLevel()==UserAuthority.ADMIN.getCode()) {
				return SignUpResponseDto.validationFalid();
			}
			
            // 이메일 중복 여부
            String email = dto.getEmail();
            boolean existedEmail = memberRepo.existsByEmail(email);
            if (existedEmail) return SignUpResponseDto.duplicateEmail();

            // 닉네임 중복 여부
            String nickname = dto.getNickname();
            boolean existedNickname = memberRepo.existsByNickname(nickname);
            if (existedNickname) return SignUpResponseDto.duplicateNickName();

            // 폰번호 중복 여부
            String telNumber = dto.getTelNumber();
            boolean existedTelNumber = memberRepo.existsByTelNumber(telNumber);
            if (existedTelNumber) return SignUpResponseDto.duplicateTelNumber();

            String password = dto.getPassword();
            String encodedPassword = passwordEncoder.encode(password);
            dto.setPassword(encodedPassword);
            
            // 랜덤 프로필 이미지
            if (!StringUtils.hasText(dto.getProfileImageUrl())) {
            	String randomProfileUrl = fileRepo.getRandomProfileImageUrl();
            	dto.setProfileImageUrl(randomProfileUrl);
            }
            
            MemberEntity member = new MemberEntity(dto);
            memberRepo.save(member);
        } catch (Exception e) {
            log.warn(e.getMessage());
            return ResponseDto.databaseError();
        }

        return SignUpResponseDto.success();
	}

	@Override
	public ResponseEntity<? super SignInResponseDto> signIn(@Valid SignInRequestDto dto) {
		String token = null;

        try {
            String email = dto.getEmail();
            MemberEntity member = memberRepo.findByEmail(email);
            if (member==null) return SignInResponseDto.signInFail();

            String password = dto.getPassword();
            String encodedPassword = member.getPassword();
            boolean isMatched = passwordEncoder.matches(password, encodedPassword);
            if (!isMatched) return SignInResponseDto.signInFail();
            
            List<String> roles = member.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());

            int memberNumber = member.getMemberNumber();
            
            token = jwtProvider.create(email, roles, memberNumber);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }

        return SignInResponseDto.success(token);
	}

	@Override
	public ResponseEntity<? super SignInGuestResponseDto> signIn(@Valid SignInGuestRequestDto dto) {
		String token = null;
		
		try {
			UserAuthority userAuth = UserAuthority.ofCode(dto.getUserAuthority());
			String userPassword =dto.getPassword();
			
			int contentNumber = dto.getContentNumber();
			
			int guestNumber;
			
			switch(userAuth) {
			case COMMENTER:
				CommentEntity comment = commentRepo.findByCommentNumberAndDeletedateIsNull(contentNumber);
				if (comment==null || comment.getDeletedate() != null) {
					return SignInGuestResponseDto.noExistBoard();
				}
				guestNumber = comment.getGuestNumber();
				break;
			case WRITER:
				BoardEntity board = boardRepo.findByBoardNumberAndDeletedateIsNull(contentNumber);
				if (board==null || board.getDeletedate() != null) {
					return SignInGuestResponseDto.noExistBoard();
				}
				guestNumber = board.getGuestNumber();
				break;
			default:
				return SignInGuestResponseDto.authorizationFail();
			}

			GuestEntity guest = guestRepo.findByGuestNumber(guestNumber);
			
			if (guest==null) {
				return SignInGuestResponseDto.signInFail();
			}
			
			String dbNickname = guest.getNickname();
			String dbPassword = guest.getPassword();
			
			boolean isMatched = passwordEncoder.matches(userPassword, dbPassword);
			
			if (!isMatched) {
				return SignInGuestResponseDto.signInFail();
			}
			
			List<String> roles = guest.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
			
			String id = contentNumber+"_"+dbNickname;
			token = jwtProvider.create(id, roles, guestNumber);
			
		} catch(Exception e) {
			log.warn(e.getMessage());
			return ResponseDto.databaseError();
		}
		return SignInGuestResponseDto.success(token);
	}

	@Override
	@Transactional
	public ResponseEntity<ResponseDto> deleteMember(String token) {
		try {
			// 회원 인증
			Claims claims = jwtProvider.getValidatedClaims(token);
			if (token==null || claims==null) {
				return ResponseDto.validationFalid();
			}
			
			List<String> roles = claims.get("roles")==null ? null : (List<String>)claims.get("roles");
			int memberNumber = claims.get("numId")==null ? -1 : (int)claims.get("numId");
			if (roles==null || memberNumber==-1) {
				return ResponseDto.validationFalid();
			}
			
			
			
			// 회원이 작성한 게시물 리스트 조회
			List<BoardEntity> boardList = boardRepo.findByMemberNumber(memberNumber);
			
			List<String> deleteFileList = new ArrayList<>();
			// 회원이 작성한 게시물 Loop
			for (BoardEntity board : boardList) {
				int boardNumber = board.getBoardNumber();
				List<BoardImageEntity> imageEntities = imageRepo.findImageByBoardNumber(boardNumber);
				
				// 이미지 파일 삭제를 위한 리스트업
				for (BoardImageEntity image : imageEntities) {
					String url = image.getImageUrl();
					deleteFileList.add(url);
				}
				
				// 게시물 이미지 테이블 삭제
				imageRepo.deleteAll(imageEntities);
				
				// 댓글 테이블 삭제
				List<CommentEntity> commentList = commentRepo.findByBoardNumber(boardNumber);
				
				for (CommentEntity comment : commentList) {
					commentRepo.delete(comment);
				}
				
				// 게시물 테이블 삭제
				boardRepo.delete(board);
			}
			
			// 회원이 작성한 댓글 리스트 조회
			List<CommentEntity> commentList = commentRepo.findByMemberNumber(memberNumber);
			
			// 회원이 작성한 댓글 Loop
			for (CommentEntity comment : commentList) {
				// 댓글 삭제
				commentRepo.delete(comment);
				
				// 게시물에서 댓글 개수 decrease
				int boardNumber = comment.getBoardNumber();
				BoardEntity board = boardRepo.findByBoardNumber(boardNumber);
				board.decreaseCommentCount();
				boardRepo.save(board);
			}
			
			// 멤버 테이블에서 삭제
			memberRepo.deleteById(memberNumber);
			
			// 실제 이미지 삭제
			fileRepo.deleteByUrl(deleteFileList);
		} catch (Exception e) {
			log.warn(e.getMessage());
			return ResponseDto.databaseError();
		}
		
		
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(ResponseStatus.SUCCESS));
	}

}
