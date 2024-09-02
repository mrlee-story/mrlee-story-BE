package kr.co.mrlee.story.dto.object;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import kr.co.mrlee.story.repository.resultset.GetCommentListResultSet;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentListItem {
	private int commentNumber;
	
	private int writerNumber;
	private String writerEmail;
	private String writerNickname;
	private String writerProfileImage;
	private int writerAuthorizationLevel;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	private Date regdate;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	private Date updatedate;
	
	private String content;

	public CommentListItem(GetCommentListResultSet rs) {
		this.commentNumber = rs.getCommentNumber();
		
		this.writerNumber = rs.getWriterNumber();
		this.writerEmail = rs.getWriterEmail();
		if (writerEmail!=null) {
			writerEmail = writerEmail.replaceAll("^(.{3})[^@]*(@.+)$", "$1****$2");
		}
		this.writerNickname = rs.getWriterNickname();
		this.writerProfileImage = rs.getWriterProfileImage();
		this.writerAuthorizationLevel = rs.getWriterAuthorizationLevel();
		
		this.regdate = rs.getRegdate();
		this.updatedate = rs.getUpdatedate();
	
		this.content = rs.getContent();
    }
	
	public static List<CommentListItem> copyList(List<GetCommentListResultSet> resultSets) {
        List<CommentListItem> list = new ArrayList<>();
        for (GetCommentListResultSet rs : resultSets) {
            CommentListItem item = new CommentListItem(rs);
            list.add(item);
        }
        return list;
	}
}
