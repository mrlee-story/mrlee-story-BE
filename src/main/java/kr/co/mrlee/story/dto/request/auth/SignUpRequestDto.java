package kr.co.mrlee.story.dto.request.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequestDto {
	@NotBlank @Email
    private String email;
	@NotBlank
    private String nickname;
	@NotBlank
    private String password;
	@NotBlank @Pattern(regexp = "^[0-9]{9,13}$")
    private String telNumber;
	
    private String profileImageUrl;
    @NotNull
    private boolean agreed;
    private int authorizationLevel;
}
