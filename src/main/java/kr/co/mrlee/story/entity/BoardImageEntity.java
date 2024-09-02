package kr.co.mrlee.story.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import kr.co.mrlee.story.common.UserAuthority;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "board_image")
@Table(name = "board_image")
public class BoardImageEntity {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int boardImageNumber;
	private int boardNumber;
	private String imageUrl;
	

	public BoardImageEntity(int boardNumber, String image) {
		this.boardNumber = boardNumber;
		this.imageUrl =image;
	}
}
