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
import jakarta.validation.Valid;
import kr.co.mrlee.story.common.UserAuthority;
import kr.co.mrlee.story.dto.request.auth.SignUpRequestDto;
import kr.co.mrlee.story.utils.UserAuthorityConverter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "member")
@Table(name = "member")
public class MemberEntity implements UserDetails {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int memberNumber;
	private String email;
	private String nickname;
	private String password;
	private String telNumber;
	private String profileImageUrl;
	private boolean agreed;
	@Convert(converter = UserAuthorityConverter.class)
	private UserAuthority authorizationLevel;

	
	public MemberEntity(@Valid SignUpRequestDto dto) {
		this.email = dto.getEmail();
		this.nickname = dto.getNickname();
		this.password = dto.getPassword();
		this.telNumber = dto.getTelNumber();
		this.profileImageUrl = dto.getProfileImageUrl();
		this.agreed = dto.isAgreed();
		this.authorizationLevel = UserAuthority.ofCode(dto.getAuthorizationLevel());
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
		return this.email;
	}

}
