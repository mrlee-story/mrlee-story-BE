package kr.co.mrlee.story.dto.response.user;

import kr.co.mrlee.story.common.UserAuthority;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserVO {
	
	private String nickname;
	private String profileImageUrl;
	private int authorizationLevel;
	
	public UserVO(String nickname, String profileImageUrl, UserAuthority authorizationLevel) {
		this.nickname = nickname;
		this.profileImageUrl = profileImageUrl;
		this.authorizationLevel = authorizationLevel.getCode();
	}
}
