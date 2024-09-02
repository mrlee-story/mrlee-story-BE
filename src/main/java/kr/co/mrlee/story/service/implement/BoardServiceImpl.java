package kr.co.mrlee.story.service.implement;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.StringUtils;

import io.jsonwebtoken.Claims;
import jakarta.transaction.Transactional;
import kr.co.mrlee.story.common.ResponseStatus;
import kr.co.mrlee.story.common.UserAuthority;
import kr.co.mrlee.story.dto.object.BoardListItem;
import kr.co.mrlee.story.dto.request.board.PatchBoardRequestDto;
import kr.co.mrlee.story.dto.request.board.PatchCommentRequestDto;
import kr.co.mrlee.story.dto.request.board.PostBoardGuestRequestDto;
import kr.co.mrlee.story.dto.request.board.PostBoardRequestDto;
import kr.co.mrlee.story.dto.request.board.PostCommentRequestDto;
import kr.co.mrlee.story.dto.response.ResponseDto;
import kr.co.mrlee.story.dto.response.auth.SignInGuestResponseDto;
import kr.co.mrlee.story.dto.response.board.DeleteBoardResponseDto;
import kr.co.mrlee.story.dto.response.board.DeleteCommentResponseDto;
import kr.co.mrlee.story.dto.response.board.GetBoardResponseDto;
import kr.co.mrlee.story.dto.response.board.GetCommentListResponseDto;
import kr.co.mrlee.story.dto.response.board.GetSearchBoardListResponseDto;
import kr.co.mrlee.story.dto.response.board.IncreaseViewCountResponseDto;
import kr.co.mrlee.story.dto.response.board.LatestBoardListResponseDto;
import kr.co.mrlee.story.dto.response.board.PatchBoardResponseDto;
import kr.co.mrlee.story.dto.response.board.PostBoardGuestResponseDto;
import kr.co.mrlee.story.dto.response.board.PostBoardResponseDto;
import kr.co.mrlee.story.dto.response.board.PostCommentResponseDto;
import kr.co.mrlee.story.entity.BoardEntity;
import kr.co.mrlee.story.entity.BoardImageEntity;
import kr.co.mrlee.story.entity.CommentEntity;
import kr.co.mrlee.story.entity.GuestEntity;
import kr.co.mrlee.story.entity.MemberEntity;
import kr.co.mrlee.story.entity.SearchLogEntity;
import kr.co.mrlee.story.provider.JwtProvider;
import kr.co.mrlee.story.repository.BoardImageRepository;
import kr.co.mrlee.story.repository.BoardRepository;
import kr.co.mrlee.story.repository.CommentRepository;
import kr.co.mrlee.story.repository.FileRepository;
import kr.co.mrlee.story.repository.GuestRepository;
import kr.co.mrlee.story.repository.MemberRepository;
import kr.co.mrlee.story.repository.SearchLogRepository;
import kr.co.mrlee.story.repository.resultset.GetBoardResultSet;
import kr.co.mrlee.story.repository.resultset.GetCommentListResultSet;
import kr.co.mrlee.story.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardServiceImpl implements BoardService {
	
	private final BoardRepository boardRepo; 
	private final BoardImageRepository imageRepo; 
	private final CommentRepository commentRepo;
	
	private final MemberRepository memberRepo;
	private final GuestRepository guestRepo;
	
	private final SearchLogRepository searchLogRepo;
	
	private final FileRepository fileRepo;
	
	private final JwtProvider jwtProvider;
	
	private final PasswordEncoder passwordEncoder;

	@Override
	public ResponseEntity<? super LatestBoardListResponseDto> getLatestBoardList(int cursor, int size, boolean notice) {
		if (cursor<=0) {
			BoardEntity top1 =  boardRepo.findTop1ByDeletedateIsNullOrderByBoardNumberDesc();
			if (top1 == null) {
				return LatestBoardListResponseDto.success(new ArrayList<>());
			}
			cursor = top1.getBoardNumber() + 1;
		}
		
		List<BoardListItem> latestBoardList = new ArrayList<>();
		
		List<GetBoardResultSet> rsList;
		
		try {
			rsList = boardRepo.getLatestBoardItemList(cursor, size, notice);
			
			for (GetBoardResultSet rs : rsList) {
				int boardNumber = rs.getBoardNumber();
				
				List<BoardImageEntity> imageEntities = rs.getSecret()? null : imageRepo.findImageByBoardNumber(boardNumber);
//						
				BoardListItem board = new BoardListItem(rs);
				if (!rs.getSecret()) {
					board.setImage(imageEntities);
				}
				
				latestBoardList.add(board);
			}
		} catch (Exception e) {
			log.warn(e.getMessage());
			return ResponseDto.databaseError();
		}
		return LatestBoardListResponseDto.success(latestBoardList);
	}

	@Override
	public ResponseEntity<? super GetBoardResponseDto> getBoard(int boardNumber, String token) {
		BoardListItem board = null;
		
		try {
			GetBoardResultSet rs = boardRepo.getBoardItem(boardNumber);
			if (rs==null) {
				return GetBoardResponseDto.noExistBoard();
			}
			
			boolean hasRole = true;
			
			validate:
			if (rs.getSecret()) {
				hasRole = false;
				
				//	인증되지 않음
				if (token==null) {
					break validate;
				}
				
//				토큰에 작성자 식별 정보에 대한 claim이 없을 경우
				Claims claims = jwtProvider.getValidatedClaims(token);
				if (claims==null) {
					break validate;
				}
				
				List<String> roles = claims.get("roles")==null ? null : (List<String>)claims.get("roles");
				int numId = claims.get("numId")==null ? -1 : (int)claims.get("numId");
				
				//	토큰에 작성자 식별 정보에 대한 claim이 없을 경우
				if (roles==null || numId==-1) {
					break validate;
				}
				
				//	관리자는 무조건 허용
				if ( roles.stream().anyMatch( role -> role.equalsIgnoreCase(UserAuthority.ADMIN.getRoleName()) ) ) {
					hasRole = true;
					break validate;
				};
				
				boolean isMember = roles.stream().anyMatch( role -> role.equalsIgnoreCase(UserAuthority.MEMBER.getRoleName()) );
				boolean isWriter = roles.stream().anyMatch( role -> role.equalsIgnoreCase(UserAuthority.WRITER.getRoleName()) );
				
				UserAuthority boardAuth = UserAuthority.ofCode( rs.getAuthorizationLevel() );
				
				
				if (
						// 게시물과 토큰 claim 간 등급 불일치
						( !boardAuth.equals(UserAuthority.MEMBER) && isMember ) || ( !boardAuth.equals(UserAuthority.WRITER) && isWriter)
						// 게시물과 토큰 claim 간 ID 불일치
						|| rs.getWriterNumber() != numId
						
					) {
					break validate;
				}
				
				// 위의 검증을 모두 통과했다면 권한 있음
				hasRole = true;
			}
			
			if (!hasRole) {
				return GetBoardResponseDto.authorizationFail(rs.getAuthorizationLevel());
			}
			
			List<BoardImageEntity> imageEntitites = imageRepo.findImageByBoardNumber(boardNumber);
			
			board = new BoardListItem(rs, imageEntitites);
		} catch (Exception e) {
			log.warn(e.getMessage());
			return ResponseDto.databaseError();
		}
		return GetBoardResponseDto.success(board);
	}

	@Override
	@Transactional
	public ResponseEntity<? super IncreaseViewCountResponseDto> increaseViewCount(int boardNumber) {
		try {
			BoardEntity board = boardRepo.findByBoardNumberAndDeletedateIsNull(boardNumber);
			if (board==null) return IncreaseViewCountResponseDto.noExistBoard();
			
			board = board.increaseViewCount();
			boardRepo.save(board);
		} catch (Exception e) {
			log.warn(e.getMessage());
			return ResponseDto.databaseError();
		}
		return IncreaseViewCountResponseDto.success();
	}
	
	@Override
	public ResponseEntity<? super GetCommentListResponseDto> getCommentList(int boardNumber, int cursor, int size) {
        List<GetCommentListResultSet> resultSets = new ArrayList<>();

        try {
        	if (cursor<=0) {
    			CommentEntity top1 =  commentRepo.findTop1ByBoardNumberAndDeletedateIsNullOrderByCommentNumberDesc(boardNumber);
    			if (top1 == null) {
    				return GetCommentListResponseDto.success(new ArrayList<>());
    			}
    			cursor = top1.getCommentNumber() + 1;
    		}
        	
            boolean existedBoard = boardRepo.existsByBoardNumberAndDeletedateIsNull(boardNumber);
            if (!existedBoard) return GetCommentListResponseDto.noExistBoard();

            resultSets = commentRepo.getCommentList(boardNumber, cursor, size);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }
        return GetCommentListResponseDto.success(resultSets);
	}
	
	@Override
	public ResponseEntity<? super GetSearchBoardListResponseDto> getSearchBoardList(String searchWord, String preSearchWord) {
        List<BoardListItem> searchBoardListResultSets = new ArrayList<>();

        try {
        	List<GetBoardResultSet> boardResultSets = boardRepo.getSearchBoardList(searchWord);
        	
        	for (GetBoardResultSet rs : boardResultSets) {
				int boardNumber = rs.getBoardNumber();
				
				List<BoardImageEntity> imageEntities = rs.getSecret()? null : imageRepo.findImageByBoardNumber(boardNumber);
//						
				BoardListItem board = new BoardListItem(rs);
				if (!rs.getSecret()) {
					board.setImage(imageEntities);
				}
				
				searchBoardListResultSets.add(board);
			}
        	
            SearchLogEntity searchLogEntity = new SearchLogEntity(searchWord, preSearchWord, false);
            searchLogRepo.save(searchLogEntity);

            boolean relation = preSearchWord != null;

            if (relation) {
                searchLogEntity = new SearchLogEntity(preSearchWord, searchWord, relation);
                searchLogRepo.save(searchLogEntity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }
        
        return GetSearchBoardListResponseDto.success(searchBoardListResultSets);
	}
	
	@Override
	@Transactional
	public ResponseEntity<? super PostBoardResponseDto> postBoard(PostBoardRequestDto dto, String email) {
		int boardNumber;
		
		try {
			MemberEntity member = memberRepo.findByEmail(email);
            if (member==null) {
            	return PostBoardResponseDto.notExistUser();
            }

            BoardEntity boardEntity = new BoardEntity(dto, member);
            boardEntity = boardRepo.save(boardEntity);

            boardNumber = boardEntity.getBoardNumber();
            
            List<String> boardImageList = dto.getBoardImageUrlList();
            List<BoardImageEntity> imageEntities = new ArrayList<>();

            for (String image : boardImageList) {
            	BoardImageEntity imageEntity = new BoardImageEntity(boardNumber, image);
                imageEntities.add(imageEntity);
            }

            imageRepo.saveAll(imageEntities);
		} catch(Exception e) {
			log.warn(e.getMessage());
			return ResponseDto.databaseError();
		}
		return PostBoardResponseDto.success(boardNumber);
	}

	@Override
	@Transactional
	public ResponseEntity<? super PostBoardGuestResponseDto> postBoard(PostBoardGuestRequestDto dto) {
		int boardNumber;
		
		try {
			// 회원가입(guest 테이블)
			String password = dto.getPassword();
			String encodedPassword = passwordEncoder.encode(password);
            dto.setPassword(encodedPassword);
            
            if (!StringUtils.hasText(dto.getProfileImageUrl())) {
            	String randomProfileUrl = fileRepo.getRandomProfileImageUrl();
            	dto.setProfileImageUrl(randomProfileUrl);
            }
            
            if ( StringUtils.hasText(dto.getTelNumber()) && !dto.isAgreed() ) {
            	return PostBoardGuestResponseDto.validationFalid();
            }
            
			GuestEntity guest = new GuestEntity(dto);
			guest = guestRepo.save(guest);
			
			// 로그인
            BoardEntity boardEntity = new BoardEntity(dto, guest);
            boardEntity = boardRepo.save(boardEntity);

            boardNumber = boardEntity.getBoardNumber();
            
            List<String> boardImageList = dto.getBoardImageUrlList();
            List<BoardImageEntity> imageEntities = new ArrayList<>();

            for (String image : boardImageList) {
            	BoardImageEntity imageEntity = new BoardImageEntity(boardNumber, image);
                imageEntities.add(imageEntity);
            }

            imageRepo.saveAll(imageEntities);
		} catch(Exception e) {
			log.warn(e.getMessage());
			return ResponseDto.databaseError();
		}
		return PostBoardGuestResponseDto.success(boardNumber);
	}

	@Override
	@Transactional
	public ResponseEntity<? super PostCommentResponseDto> postComment(PostCommentRequestDto dto, int boardNumber, String token) {
		int commentCount = 0;
		
		try {
			// 게시물 존재 여부 검증
			BoardEntity board = boardRepo.findByBoardNumberAndDeletedateIsNull(boardNumber);
			if (board==null) {
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				return PostCommentResponseDto.noExistBoard();
			}
			
			// 로그인 되어있거나 닉네임/패스워드 입력은 반드시 되어있어야 함
			String nickname = dto.getNickname();
			String password = dto.getPassword();
			Claims claims = jwtProvider.getValidatedClaims(token);
			
			MemberEntity member = null;
			GuestEntity guest = null;
			
			// 기존 회원 또는 작성자
            if (token!=null && claims!=null) {
            	// 권한 검증
            	List<String> roles = claims.get("roles")==null ? null : (List<String>)claims.get("roles");
				int numId = claims.get("numId")==null ? -1 : (int)claims.get("numId");
				
				if (roles==null || numId==-1) {
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					return PostCommentResponseDto.authorizationFail();
				}
				
				UserAuthority authCode = UserAuthority.ofRoleName(roles.get(0));
				
				boolean isMember = authCode.ordinal() >= UserAuthority.MEMBER.ordinal();
				
				member = memberRepo.findByMemberNumber(numId);
				guest = guestRepo.findByGuestNumber(numId);
				
				// 해당 등급의 사용자가 존재하지 않을 경우
				if ( (isMember && member==null) || (!isMember && guest==null) ) {
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					return PostCommentResponseDto.noExistUser();
				}
            // 신규 게스트 댓글 작성자
            } else if (nickname!=null && password!=null) {
            	String randomProfileUrl = fileRepo.getRandomProfileImageUrl();
    			String encodedPassword = passwordEncoder.encode(password);
                dto.setPassword(encodedPassword);
            	guest = new GuestEntity(dto, randomProfileUrl);
            	guestRepo.save(guest);
            }
			

			// 댓글 저장
			CommentEntity comment;
			if (member!=null) {
				comment = new CommentEntity(boardNumber, dto, member);
			} else {
				comment = new CommentEntity(boardNumber, dto, guest);
			}
			commentRepo.save(comment);
			
			// 댓글 수 저장
			board = board.increaseCommentCount();
			boardRepo.save(board);
            commentCount = board.getCommentCount();
		} catch(Exception e) {
			log.warn(e.getMessage());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return PostCommentResponseDto.databaseError();
		}
		return PostCommentResponseDto.success(commentCount);
	}

	@Override
	public ResponseEntity<? super PatchBoardResponseDto> patchBoard(int boardNumber, PatchBoardRequestDto dto, String token) {
		try {
			//	게시물 존재 여부 검증
			BoardEntity board = boardRepo.findByBoardNumberAndDeletedateIsNull(boardNumber);
			if (board==null) {
				return PatchBoardResponseDto.noExistBoard();
			}
			
			Claims claims = jwtProvider.getValidatedClaims(token);
            if (token==null || claims==null) {
            	return PatchBoardResponseDto.authorizationFail();
            }
            
            // 사용자 권한 검증
            boolean hasRole = false;
            
            MemberEntity member = null;
            GuestEntity guest = null;
            validate:
            if (token!=null) {
            	// 권한 검증
            	List<String> roles = claims.get("roles")==null ? null : (List<String>)claims.get("roles");
				int numId = claims.get("numId")==null ? -1 : (int)claims.get("numId");
				if (roles==null || numId==-1) break validate;
				// 관리자는 무조건 허용
				if ( roles.stream().anyMatch( role -> role.equalsIgnoreCase(UserAuthority.ADMIN.getRoleName()) ) ) {
					hasRole = true;
					break validate;
				};
				
				boolean isMember = roles.stream().anyMatch( role -> role.equalsIgnoreCase(UserAuthority.MEMBER.getRoleName()) );
				boolean isWriter = roles.stream().anyMatch( role -> role.equalsIgnoreCase(UserAuthority.WRITER.getRoleName()) );
				
				member = board.getMemberNumber()==null ? null : memberRepo.findByMemberNumber(numId);
				guest = board.getGuestNumber()==null? null : guestRepo.findByGuestNumber(numId);
				
				//	해당 등급의 사용자가 존재하지 않을 경우
				if ( (isMember && member==null) || (isWriter && guest==null) ) {
					return PatchBoardResponseDto.noExistUser();
				}
				
				//	어떠한 이유로 게시물에 멤버/게스트 정보가 모두 입력되었거나, 토큰에 멤버/작성자 정보가 모두 입력된 경우
				if (
						(member==null && guest==null) || (member!=null && guest!=null) ||
						(isMember == isWriter)
				) {
					break validate;
				}
				
				// 게시물의 등급/id가 토큰의 그것과 일치하면 검증된 것으로 함
				if (
						( member!=null && isMember && member.getMemberNumber() == numId) || ( guest!=null && isWriter && guest.getGuestNumber() == numId)
						
					) {
					hasRole = true;
					break validate;
				}
            }
			
			if (!hasRole) {
				return PatchBoardResponseDto.noPermission();
			}
			
			//	수정 내용 반영
			board = board.patch(dto);
			boardRepo.save(board);
			
			// 이미지 파일 삭제를 위한 리스트업
			List<String> deleteFileList = new ArrayList<>();
			List<BoardImageEntity> originImageEntities = imageRepo.findImageByBoardNumber(boardNumber);
			for (BoardImageEntity image : originImageEntities) {
				String url = image.getImageUrl();
				deleteFileList.add(url);
			}
			
			//	이미지 삭제
			imageRepo.deleteAll(originImageEntities);
//			imageRepo.deleteByBoardNumber(boardNumber);
			
			//	이미지 갱신
			List<String> boardImageList = dto.getBoardImageUrlList();
			List<BoardImageEntity> imageEntities = new ArrayList<>();
			for (String url : boardImageList) {
				BoardImageEntity image = new BoardImageEntity(boardNumber, url);
				imageEntities.add(image);
			}
			imageRepo.saveAll(imageEntities);
			

            //	모든 DB 갱신 작업을 마쳤다면 기존 실제 이미지 삭제
            fileRepo.deleteByUrl(deleteFileList);
		} catch (Exception e) {
			log.warn(e.getMessage());
			return ResponseDto.databaseError();
		}
		return PatchBoardResponseDto.success();
	}




	@Override
	@Transactional
	public ResponseEntity<ResponseDto> patchComment(int commentNumber, PatchCommentRequestDto requestBody, String token) {
		try {
			//	게시물 존재 여부 검증
			CommentEntity comment = commentRepo.findByCommentNumberAndDeletedateIsNull(commentNumber);
			if (comment==null) {
				return ResponseDto.databaseError();
			}
			
			Claims claims = jwtProvider.getValidatedClaims(token);
            if (token==null || claims==null) {
            	return ResponseDto.databaseError();
            }
            
            // 사용자 권한 검증
            boolean hasRole = false;
            
            MemberEntity member = null;
            GuestEntity guest = null;
            validate:
            if (token!=null) {
            	// 권한 검증
            	List<String> roles = claims.get("roles")==null ? null : (List<String>)claims.get("roles");
				int numId = claims.get("numId")==null ? -1 : (int)claims.get("numId");
				if (roles==null || numId==-1) break validate;
				// 관리자는 무조건 허용
				if ( roles.stream().anyMatch( role -> role.equalsIgnoreCase(UserAuthority.ADMIN.getRoleName()) ) ) {
					hasRole = true;
					break validate;
				};
				
				boolean isMember = roles.stream().anyMatch( role -> role.equalsIgnoreCase(UserAuthority.MEMBER.getRoleName()) );
				boolean isWriter = roles.stream().anyMatch( role -> role.equalsIgnoreCase(UserAuthority.WRITER.getRoleName()) || role.equalsIgnoreCase(UserAuthority.COMMENTER.getRoleName()) );
				
				member = comment.getMemberNumber()==null ? null : memberRepo.findByMemberNumber(numId);
				guest = comment.getGuestNumber()==null? null : guestRepo.findByGuestNumber(numId);
				
				//	해당 등급의 사용자가 존재하지 않을 경우
				if ( (isMember && member==null) || (isWriter && guest==null) ) {
					return ResponseDto.databaseError();
				}
				
				//	어떠한 이유로 게시물에 멤버/게스트 정보가 모두 입력되었거나, 토큰에 멤버/작성자 정보가 모두 입력된 경우
				if (
						(member==null && guest==null) || (member!=null && guest!=null) ||
						(isMember == isWriter)
				) {
					break validate;
				}
				
				// 게시물의 등급/id가 토큰의 그것과 일치하면 검증된 것으로 함
				if (
						( member!=null && isMember && member.getMemberNumber() == numId) || ( guest!=null && isWriter && guest.getGuestNumber() == numId)
						
					) {
					hasRole = true;
					break validate;
				}
            }
			
			if (!hasRole) {
				return ResponseDto.databaseError();
			}
			
			//	수정 내용 반영
			comment = comment.patch(requestBody);
			commentRepo.save(comment);

		} catch (Exception e) {
			log.warn(e.getMessage());
			return ResponseDto.databaseError();
		}
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(ResponseStatus.SUCCESS));
	}


	@Override
	@Transactional
	public ResponseEntity<? super DeleteBoardResponseDto> deleteBoard(int boardNumber, String token) {
		try {
			
			// 게시물 존재 여부 검증
            BoardEntity board= boardRepo.findByBoardNumberAndDeletedateIsNull(boardNumber);
            if (board == null) return DeleteBoardResponseDto.noExistBoard();
			
            Claims claims = jwtProvider.getValidatedClaims(token);
            if (token==null || claims==null) {
            	return DeleteBoardResponseDto.authorizationFail();
            }
            
            // 사용자 권한 검증
            boolean hasRole = false;
            
            MemberEntity member = null;
            GuestEntity guest = null;
            validate:
            if (token!=null) {
            	// 권한 검증
            	List<String> roles = claims.get("roles")==null ? null : (List<String>)claims.get("roles");
				int numId = claims.get("numId")==null ? -1 : (int)claims.get("numId");
				if (roles==null || numId==-1) break validate;
				// 관리자는 무조건 허용
				if ( roles.stream().anyMatch( role -> role.equalsIgnoreCase(UserAuthority.ADMIN.getRoleName()) ) ) {
					hasRole = true;
					break validate;
				};
				
				boolean isMember = roles.stream().anyMatch( role -> role.equalsIgnoreCase(UserAuthority.MEMBER.getRoleName()) );
				boolean isWriter = roles.stream().anyMatch( role -> role.equalsIgnoreCase(UserAuthority.WRITER.getRoleName()) );
				
				member = board.getMemberNumber()==null ? null : memberRepo.findByMemberNumber(numId);
				guest = board.getGuestNumber()==null? null : guestRepo.findByGuestNumber(numId);

				//	해당 등급의 사용자가 존재하지 않을 경우
				if ( (isMember && member==null) || (isWriter && guest==null) ) {
					return DeleteBoardResponseDto.noExistUser();
				}
				
				//	어떠한 이유로 게시물에 멤버/게스트 정보가 모두 입력되었거나, 토큰에 멤버/작성자 정보가 모두 입력된 경우
				if (
						(member==null && guest==null) || (member!=null && guest!=null) ||
						(isMember == isWriter)
				) {
					break validate;
				}
				
				// 게시물의 등급/id가 토큰의 그것과 일치하면 검증된 것으로 함
				if (
						( member!=null && isMember && member.getMemberNumber() == numId) || ( guest!=null && isWriter && guest.getGuestNumber() == numId)
						
					) {
					hasRole = true;
					break validate;
				}
            }
			
			if (!hasRole) {
				return DeleteBoardResponseDto.noPermission();
			}
			
			// 이미지 파일 삭제를 위한 리스트업
			List<String> deleteFileList = new ArrayList<>();
			List<BoardImageEntity> imageEntities = imageRepo.findImageByBoardNumber(boardNumber);
			for (BoardImageEntity image : imageEntities) {
				String url = image.getImageUrl();
				deleteFileList.add(url);
			}
			
            // 삭제 트랜잭션 처리(이미지)
            imageRepo.deleteAll(imageEntities);
//            imageRepo.deleteByBoardNumber(boardNumber);
//            commentRepo.deleteByBoardNumber(boardNumber);
            // 삭제 트랜잭션 처리(댓글)
            List<CommentEntity> commentList = commentRepo.findByBoardNumberAndDeletedateIsNull(boardNumber);
            
            for (CommentEntity comment : commentList) {
				comment = comment.delete();
				commentRepo.save(comment);
			}
            
            // 삭제 트랜잭션 처리(게시물)
            board = board.delete();
            boardRepo.save(board);
//            boardRepo.delete(board);
//            if (guest!=null) {
//            	guestRepo.delete(guest);
//            }
            
            //	모든 DB 삭제 작업을 마쳤다면 실제 이미지 삭제
            fileRepo.deleteByUrl(deleteFileList);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }
		
        return DeleteBoardResponseDto.success();
	}

	@Override
	@Transactional
	public ResponseEntity<? super DeleteCommentResponseDto> deleteComment(int commentNumber, String token) {
		int commentCount = 0; 
		try {
			// 게시물 존재 여부 검증
            CommentEntity comment= commentRepo.findByCommentNumberAndDeletedateIsNull(commentNumber);
            if (comment == null) {
            	return DeleteCommentResponseDto.noExistBoard();
            }
			
            Claims claims = jwtProvider.getValidatedClaims(token);
            if (token==null || claims==null) {
            	return DeleteCommentResponseDto.authorizationFail();
            }
            
            // 사용자 권한 검증
            boolean hasRole = false;
            
            MemberEntity member = null;
            GuestEntity guest = null;
            validate:
            if (token!=null) {
            	// 권한 검증
            	List<String> roles = claims.get("roles")==null ? null : (List<String>)claims.get("roles");
				int numId = claims.get("numId")==null ? -1 : (int)claims.get("numId");
				if (roles==null || numId==-1) break validate;
				// 관리자는 무조건 허용
				if ( roles.stream().anyMatch( role -> role.equalsIgnoreCase(UserAuthority.ADMIN.getRoleName()) ) ) {
					hasRole = true;
					break validate;
				};
				
				boolean isMember = roles.stream().anyMatch( role -> role.equalsIgnoreCase(UserAuthority.MEMBER.getRoleName()) );
				boolean isWriter = roles.stream().anyMatch( role -> role.equalsIgnoreCase(UserAuthority.WRITER.getRoleName()) || role.equalsIgnoreCase(UserAuthority.COMMENTER.getRoleName()) );
				
				member = comment.getMemberNumber()==null ? null : memberRepo.findByMemberNumber(numId);
				guest = comment.getGuestNumber()==null? null : guestRepo.findByGuestNumber(numId);

				//	해당 등급의 사용자가 존재하지 않을 경우
				if ( (isMember && member==null) || (isWriter && guest==null) ) {
					return DeleteCommentResponseDto.noExistUser();
				}
				
				//	어떠한 이유로 게시물에 멤버/게스트 정보가 모두 입력되었거나, 토큰에 멤버/작성자 정보가 모두 입력된 경우
				if (
						(member==null && guest==null) || (member!=null && guest!=null) ||
						(isMember == isWriter)
				) {
					break validate;
				}
				
				// 게시물의 등급/id가 토큰의 그것과 일치하면 검증된 것으로 함
				if (
						( member!=null && isMember && member.getMemberNumber() == numId) || ( guest!=null && isWriter && guest.getGuestNumber() == numId)
						
					) {
					hasRole = true;
					break validate;
				}
            }
			
			if (!hasRole) {
				return DeleteCommentResponseDto.authorizationFail();
			}
			
            // 일단 게스트/게시판 테이블에서 삭제하지 않고, 게시판 테이블에서 deletdate만 갱신해줌
            comment = comment.delete();
            commentRepo.save(comment);
            
            // 댓글 수 저장
            BoardEntity board = boardRepo.findByBoardNumberAndDeletedateIsNull(comment.getBoardNumber());
            if (board!=null) {
            	board = board.decreaseCommentCount();
            	boardRepo.save(board);
            	commentCount = board.getCommentCount();
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }
		
        return DeleteCommentResponseDto.success(commentCount);
	}


	

}
