package kr.co.mrlee.story.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import kr.co.mrlee.story.common.UserAuthority;
import kr.co.mrlee.story.dto.request.board.PostBoardGuestRequestDto;
import kr.co.mrlee.story.dto.request.board.PostCommentRequestDto;
import kr.co.mrlee.story.utils.UserAuthorityConverter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "guest")
@Table(name = "guest")
public class GuestEntity implements UserDetails {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int guestNumber;
	private String nickname;
	private String password;
	private String telNumber;
	private String profileImageUrl;
	private boolean agreed;
	@Convert(converter = UserAuthorityConverter.class)
	private UserAuthority authorizationLevel;

	public GuestEntity(PostBoardGuestRequestDto dto) {
		this.nickname = dto.getNickname();
		this.password = dto.getPassword();
		this.telNumber = dto.getTelNumber();
		this.profileImageUrl = dto.getProfileImageUrl();
		this.agreed = dto.getTelNumber()==null? true : dto.isAgreed();
		this.authorizationLevel = UserAuthority.WRITER;
	}

	public GuestEntity(PostCommentRequestDto dto, String randomProfileUrl) {
		this.nickname = dto.getNickname();
		this.password = dto.getPassword();
		this.profileImageUrl = randomProfileUrl;
		this.authorizationLevel = UserAuthority.COMMENTER;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority(this.authorizationLevel.getRoleName()));
		return authorities;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.nickname;
	}


}
