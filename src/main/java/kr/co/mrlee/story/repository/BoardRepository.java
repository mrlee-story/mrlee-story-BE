package kr.co.mrlee.story.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import kr.co.mrlee.story.entity.BoardEntity;
import kr.co.mrlee.story.repository.resultset.GetBoardResultSet;

@Repository
public interface BoardRepository extends JpaRepository<BoardEntity, Integer> {

	BoardEntity findByBoardNumberAndDeletedateIsNull(int boardNumber);

	
	BoardEntity findTop1ByDeletedateIsNullOrderByBoardNumberDesc();
	
	@Query(
			value=
			" SELECT     " + 
			"          tbl_b.board_number AS boardNumber,     " + 
			"          tbl_b.title AS title,     " + 
			"          CASE WHEN tbl_b.secret = true THEN " +
			"		   		NULL " +
			"		   ELSE " +
			"          		tbl_b.content " +
			"		   END AS content,   " + 

			"          tbl_b.regdate AS regdate,      " + 
			"          tbl_b.updatedate AS updatedate,   " + 
			
			"          COALESCE(tbl_b.member_number, tbl_b.guest_number) AS writerNumber,   " + 
			"          tbl_m.email AS writerEmail,      " + 
			"          COALESCE(tbl_m.nickname, tbl_g.nickname) AS writerNickname,      " + 
			"          COALESCE(tbl_m.profile_image_url, tbl_g.profile_image_url) AS writerProfileImage,   " + 
			"          COALESCE(tbl_m.authorization_level , tbl_g.authorization_level ) AS authorizationLevel,   " +
			
			"          tbl_b.view_count AS viewCount,   " + 
			"          tbl_b.comment_count AS commentCount,   " + 
			"          tbl_b.notice AS notice,   " + 
			"          tbl_b.secret AS secret   " + 

			" FROM (" + 
			"				SELECT * "
			+ "				FROM board "
			+ "				WHERE deletedate IS NULL "
			+ "				  AND board_number < ?1 "
			+ "				  AND notice = ?3"
			+ "				ORDER BY board_number DESC "
			+ "				limit ?2" + 
			"			)AS tbl_b   " + 
			"              LEFT JOIN member AS tbl_m   " + 
			"              ON tbl_b.member_number = tbl_m.member_number   " + 
			"            " + 
			"              LEFT JOIN guest AS tbl_g   " + 
			"              ON tbl_b.guest_number = tbl_g.guest_number  " + 
			"			 ORDER BY tbl_b.board_number DESC", 
	        nativeQuery = true
		)
	List<GetBoardResultSet> getLatestBoardItemList(int cursor, int size, boolean notice);
	
	@Query(
			value=
			"SELECT     " + 
			"              tbl_b.board_number AS boardNumber,     " + 
			"              tbl_b.title AS title,     " + 
			"              tbl_b.content AS content,   " + 

			"              tbl_b.regdate AS regdate,      " + 
			"              tbl_b.updatedate AS updatedate,   " + 
			
			"              COALESCE(tbl_b.member_number, tbl_b.guest_number) AS writerNumber,   " + 
			"              tbl_m.email AS writerEmail,      " + 
			"              COALESCE(tbl_m.nickname, tbl_g.nickname) AS writerNickname,      " + 
			"              COALESCE(tbl_m.profile_image_url, tbl_g.profile_image_url) AS writerProfileImage,   " + 
			"              COALESCE(tbl_m.authorization_level , tbl_g.authorization_level ) AS authorizationLevel,   " +
			
			"              tbl_b.view_count AS viewCount,   " + 
			"              tbl_b.comment_count AS commentCount,   " + 
			"              tbl_b.notice AS notice,   " + 
			"              tbl_b.secret AS secret   " + 

			" FROM (" + 
			"	SELECT * "
			+ "	FROM board "
			+ "	WHERE deletedate IS NULL"
			+ "   AND board_number = ?1 " + 
			" )AS tbl_b   " + 
			"    LEFT JOIN member AS tbl_m   " + 
			"    ON tbl_b.member_number = tbl_m.member_number   " + 
			"    LEFT JOIN guest AS tbl_g   " + 
			"    ON tbl_b.guest_number = tbl_g.guest_number  ",
	        nativeQuery = true
		)
	GetBoardResultSet getBoardItem(int boardNumber);


	boolean existsByBoardNumberAndDeletedateIsNull(int boardNumber);


	@Query(
			value=
			" SELECT     " + 
			"          tbl_b.board_number AS boardNumber,     " + 
			"          tbl_b.title AS title,     " + 
			"          CASE WHEN tbl_b.secret = true THEN " +
			"		   		NULL " +
			"		   ELSE " +
			"          		tbl_b.content " +
			"		   END AS content,   " + 

			"          tbl_b.regdate AS regdate,      " + 
			"          tbl_b.updatedate AS updatedate,   " + 
			
			"          COALESCE(tbl_b.member_number, tbl_b.guest_number) AS writerNumber,   " + 
			"          tbl_m.email AS writerEmail,      " + 
			"          COALESCE(tbl_m.nickname, tbl_g.nickname) AS writerNickname,      " + 
			"          COALESCE(tbl_m.profile_image_url, tbl_g.profile_image_url) AS writerProfileImage,   " + 
			"          COALESCE(tbl_m.authorization_level , tbl_g.authorization_level ) AS authorizationLevel,   " +
			
			"          tbl_b.view_count AS viewCount,   " + 
			"          tbl_b.comment_count AS commentCount,   " + 
			"          tbl_b.notice AS notice,   " + 
			"          tbl_b.secret AS secret   " + 

			" FROM (" + 
			"				SELECT * "
			+ "				FROM board "
			+ "				WHERE deletedate IS NULL "
			+ "				  AND notice = false"
			+ "				  AND (title LIKE %?1% OR content LIKE %?1%)"
			+ "				ORDER BY board_number DESC "
			+ "			)AS tbl_b   " + 
			"              LEFT JOIN member AS tbl_m   " + 
			"              ON tbl_b.member_number = tbl_m.member_number   " + 
			"            " + 
			"              LEFT JOIN guest AS tbl_g   " + 
			"              ON tbl_b.guest_number = tbl_g.guest_number  " + 
			"			 ORDER BY tbl_b.board_number DESC", 
	        nativeQuery = true
		)
	List<GetBoardResultSet> getSearchBoardList(String searchWord);


	List<BoardEntity> findByMemberNumberAndDeletedateIsNull(int memberNumber);


	List<BoardEntity> findByMemberNumber(int memberNumber);


	BoardEntity findByBoardNumber(int boardNumber);

}
