package kr.co.mrlee.story.dto.response.user;

import kr.co.mrlee.story.entity.GuestEntity;
import lombok.Getter;

@Getter
public class GuestVO extends UserVO {

	private int guestNumber;
	
	public GuestVO(GuestEntity guest) {
		super(guest.getNickname(), guest.getProfileImageUrl(), guest.getAuthorizationLevel());
		this.guestNumber = guest.getGuestNumber();
	}
}
