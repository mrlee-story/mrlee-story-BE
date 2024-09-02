package kr.co.mrlee.story.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;
import kr.co.mrlee.story.entity.BoardImageEntity;

@Repository
public interface BoardImageRepository extends JpaRepository<BoardImageEntity, Integer>{

	List<BoardImageEntity> findImageByBoardNumber(int boardNumber);
	
//	@Transactional
//	void deleteByBoardNumber(int boardNumber);

}
