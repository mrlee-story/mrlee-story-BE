package kr.co.mrlee.story.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;
import kr.co.mrlee.story.entity.CommentEntity;
import kr.co.mrlee.story.repository.resultset.GetCommentListResultSet;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Integer>{

	CommentEntity findByCommentNumberAndDeletedateIsNull(int contentNumber);

	CommentEntity findTop1ByBoardNumberAndDeletedateIsNullOrderByCommentNumberDesc(int boardNumber);

	@Transactional
	void deleteByBoardNumber(int boardNumber);

	@Query(
		value=	" SELECT  "
				+ "			tbl_c.comment_number AS commentNumber,"
				
				+ "			COALESCE(tbl_c.member_number, tbl_c.guest_number) AS writerNumber, "
				+ "		    tbl_m.email AS writerEmail, "
				+ "		    COALESCE(tbl_m.nickname, tbl_g.nickname) AS writerNickname, "
				+ "		    COALESCE(tbl_m.profile_image_url, tbl_g.profile_image_url) AS writerProfileImage, "
				+ "		    COALESCE(tbl_m.authorization_level , tbl_g.authorization_level ) AS writerAuthorizationLevel, "
				
				+ "			tbl_c.regdate AS regdate, "
				+ "			tbl_c.updatedate AS updatedate, "
				+ "            tbl_c.content AS content "
				+ "FROM ( "
				+ " 	SELECT * "
				+ "		FROM comment "
				+ "		WHERE deletedate IS NULL "
				+ "		  AND board_number = ?1 "
				+ "		  AND comment_number < ?2 "
				+ "		ORDER BY comment_number DESC "
				+ "		limit ?3"
				+ "  )AS tbl_c"
				+ " LEFT JOIN member AS tbl_m"
				+ "	  ON tbl_c.member_number = tbl_m.member_number   "
				+ " LEFT JOIN guest AS tbl_g"
				+ "	  ON tbl_c.guest_number = tbl_g.guest_number  "
				+ "ORDER BY commentNumber DESC"
		, nativeQuery = true
	)
	List<GetCommentListResultSet> getCommentList(int boardNumber, int cursor, int size);

	List<CommentEntity> findByBoardNumberAndDeletedateIsNull(int boardNumber);

	List<CommentEntity> findByMemberNumberAndDeletedateIsNull(int memberNumber);

	List<CommentEntity> findByBoardNumber(int boardNumber);

	List<CommentEntity> findByMemberNumber(int memberNumber);

}
