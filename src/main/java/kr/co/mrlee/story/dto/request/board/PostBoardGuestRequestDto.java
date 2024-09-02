package kr.co.mrlee.story.dto.request.board;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostBoardGuestRequestDto {
	@NotBlank
	private String title;
	@NotBlank
	private String content;
	@NotNull
	private List<String> boardImageUrlList;
	@NotBlank
	private String nickname;
	@NotBlank
	private String password;
	@NotBlank @Pattern(regexp = "^[0-9]{9,13}$")
	private String telNumber;
	private String profileImageUrl;
	@NotNull
	private boolean secret;
	@NotNull
	private boolean agreed;
}
