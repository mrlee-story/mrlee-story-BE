package kr.co.mrlee.story.dto.response.user;

import kr.co.mrlee.story.entity.MemberEntity;
import lombok.Getter;

@Getter
public class MemberVO extends UserVO {

	private int memberNumber;
	private String email;
	
	public MemberVO(MemberEntity member) {
		super(member.getNickname(), member.getProfileImageUrl(), member.getAuthorizationLevel());
		this.memberNumber = member.getMemberNumber();
		this.email = member.getEmail();
	}
}
