package kr.co.mrlee.story.common;

import java.util.Arrays;

import kr.co.mrlee.story.provider.JwtProvider;
import lombok.Getter;

@Getter
public enum UserAuthority {
	GUEST(0, "Guest", "게스트"),
	WRITER(1, "Writer", "작성자"),
	COMMENTER(2, "Commenter", "댓글 작성자"),
	MEMBER(3, "Member", "회원"),
	ADMIN(275, "Admin", "관리자");
	
	private int code;
	private String engName;
	private String korName;
	
	private UserAuthority(int code, String engName, String korName) {
		this.code = code;
		this.engName = engName;
		this.korName = korName;
	}
	
	public int getCode() {
		return this.code;
	}
	
	public String getEngName() {
		return this.engName;
	}
	
	public String getKorName() {
		return this.korName;
	}
	
	public String getRoleName() {
		return this.engName.toUpperCase();
	}
	
//	public String getRoleFullName() {
//		return JwtProvider.ROLE_PREFIX+this.engName.toUpperCase();
//	}

	public static UserAuthority ofCode(Integer dbData) {
		return Arrays.stream(UserAuthority.values())
				.filter(v -> v.getCode()==dbData)
				.findAny()
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자 권한입니다."));
	}
	
	public static UserAuthority ofRoleName(String roleName) {
		return Arrays.stream(UserAuthority.values())
				.filter(v -> v.getRoleName().equalsIgnoreCase(roleName))
				.findAny()
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자 권한입니다."));
	}
}
