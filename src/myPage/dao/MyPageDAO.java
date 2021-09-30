package myPage.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import common.JDBCTemplate;
import message.model.vo.Message;
import recipe.model.vo.Recipe;
import recipe.model.vo.RecipeReply;
import story.model.vo.Story;
import user.model.vo.User;

public class MyPageDAO {

	// 회원정보 수정
	public int updateUser(Connection conn, User user) {
		PreparedStatement pstmt = null;
		int result = 0;
		String query = "UPDATE USERS SET USER_PWD = ?, USER_EMAIL =?, USER_PHONE=? WHERE USER_ID=?";
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, user.getUserPwd());
			pstmt.setString(2, user.getUserEmail());
			pstmt.setString(3, user.getUserPhone());
			pstmt.setString(4, user.getUserId());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}
		return result;
	}

	// 마이페이지 입장시 유저vo에 유저 정보담기
	public User addUser(Connection conn, String userId) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		User user = null;
		String query = "SELECT USER_NO, USER_ID, USER_PWD, USER_EMAIL, USER_PHONE FROM USERS WHERE USER_ID=?";

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, userId);
			rset = pstmt.executeQuery();

			if (rset.next()) {
				user = new User();
				user.setUserNo(rset.getInt("USER_NO"));
				user.setUserId(rset.getString("USER_ID"));
				user.setUserPwd(rset.getString("USER_PWD"));
				user.setUserEmail(rset.getString("USER_EMAIL"));
				user.setUserPhone(rset.getString("USER_PHONE"));
				System.out.println(user.toString());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		return user;
	}

	// 마이페이지 내가쓴 공개 레시피 갯수
	public int countRecipe(Connection conn, String userId) {
		PreparedStatement pstmt = null;
		int count = 0;
		ResultSet rset = null;
		String query = "SELECT COUNT(*) FROM RECIPE WHERE USER_ID=? AND RECIPE_SAVESTATE='1'";

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, userId);
			rset = pstmt.executeQuery();
			if (rset.next()) {
				count = rset.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		return count;
	}

	// 마이페이지 내가쓴 스토리 갯수
	public int countStory(Connection conn, String userId) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		int count = 0;
		String query = "SELECT COUNT(*) FROM STORY WHERE USER_ID = ?";

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, userId);
			rset = pstmt.executeQuery();
			if (rset.next()) {
				count = rset.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}

		return count;
	}
	
	// 마이페이지 전체공개 레시피 리스트
	public List<Recipe> myPageSelectAllRecipe(Connection conn, String userId) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		List<Recipe> rList = null;
		String query = "select recipe_no, user_id, recipe_title, F.file_name,recipe_enrollDate, recipe_replycount, recipe_LikeCount from recipe R,recipe_file F where  R.file_no = F.file_no and r.file_no is not null and USER_ID=? and recipe_savestate='1' order by recipe_no";
		
		try {
			pstmt=conn.prepareStatement(query);
			pstmt.setString(1, userId);
			rset=pstmt.executeQuery();
			rList = new ArrayList<Recipe>();
			while(rset.next()) {
				Recipe recipe= new Recipe();
				recipe.setRecipeNo(rset.getInt("RECIPE_NO"));
				recipe.setUserId(rset.getString("USER_ID"));
				recipe.setRecipeTitle(rset.getString("RECIPE_TITLE"));
				recipe.setFileName(rset.getString("FILE_NAME"));
				recipe.setRecipeReplyCount(rset.getInt("RECIPE_REPLYCOUNT"));
				recipe.setRecipeLikeCount(rset.getInt("RECIPE_LIKECOUNT"));
				recipe.setRecipeEnrollDate(rset.getDate("RECIPE_ENROLLDATE"));
				rList.add(recipe);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		
		return rList;
	}
	//마이페이지 임시저장레시피 조회
	public List<Recipe> myCacheRecipe(Connection conn, String userId) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		List<Recipe> cList = null;
		String query = "select recipe_title, F.file_name,recipe_enrollDate, recipe_replycount, recipe_LikeCount from recipe R,recipe_file F where  R.file_no = F.file_no and r.file_no is not null and USER_ID=? and recipe_savestate='0' order by recipe_no";
		
		try {
			pstmt=conn.prepareStatement(query);
			pstmt.setString(1, userId);
			rset=pstmt.executeQuery();
			cList = new ArrayList<Recipe>();
			while(rset.next()) {
				Recipe recipe= new Recipe();
				recipe.setRecipeTitle(rset.getString("RECIPE_TITLE"));
				recipe.setFileName(rset.getString("file_name"));
				recipe.setRecipeReplyCount(rset.getInt("recipe_replycount"));
				recipe.setRecipeLikeCount(rset.getInt("recipe_LikeCount"));
				recipe.setRecipeEnrollDate(rset.getDate("recipe_enrollDate"));
				cList.add(recipe);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		
		return cList;
	}
	//마이페이지 스토리 조회
	public List<Story> myStory(Connection conn, String userId) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		List<Story> sList = null;
		String query = "SELECT story_enrolldate, STORY_NO,STORY_CONTENTS,STORYFILE_NAME,STORY_TAG,USER_ID,(select count(*) from STORY_REPLY r where r.story_no=s.story_no)as \"SCNT\" FROM STORY S, STORY_FILE F WHERE S.FILE_NO = F.STORYFILE_NO and user_Id=?";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, userId);
			rset = pstmt.executeQuery();
			sList = new ArrayList<>();
			while(rset.next()) {
				Story story = new Story();
				story.setStoryEnrollDate(rset.getDate("STORY_ENROLLDATE"));
				story.setStoryNo(rset.getInt("STORY_NO"));
				story.setStoryContents(rset.getString("STORY_CONTENTS"));
				story.setFileName(rset.getString("STORYFILE_NAME"));
				story.setStoryTag(rset.getString("STORY_TAG"));
				story.setUserId(rset.getString("USER_ID"));
				story.setStoryReplyCount(rset.getInt("SCNT"));
				sList.add(story);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		return sList;
	}
	//스크랩 리스트
	public List<Recipe> myScrap(Connection conn, String userId) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		List<Recipe> scList = null;
		String query = "select R.recipe_no, recipe_title, F.file_name ,recipe_enrollDate, recipe_replycount, recipe_LikeCount from recipe R,recipe_file F,RECIPE_SCRAP S where(R.file_no = F.file_no and r.file_no is not null) and (R.USER_ID=?) AND (S.RECIPE_NO = R.RECIPE_NO)";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, userId);
			rset = pstmt.executeQuery();
			scList = new ArrayList<>();
			while(rset.next()) {
				Recipe recipe = new Recipe();
				recipe.setRecipeNo(rset.getInt("RECIPE_NO"));
				recipe.setRecipeTitle(rset.getString("RECIPE_TITLE"));
				recipe.setFileName(rset.getString("FILE_NAME"));
				recipe.setRecipeEnrollDate(rset.getDate("RECIPE_ENROLLDATE"));
				recipe.setRecipeReplyCount(rset.getInt("RECIPE_REPLYCOUNT"));
				recipe.setRecipeLikeCount(rset.getInt("RECIPE_LIKECOUNT"));
				scList.add(recipe);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		return scList;
	}

	public List<RecipeReply> myRecipeReply(Connection conn, String userId) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		List<RecipeReply> reList = null;
		String query = "select R.recipe_no, P.contents ,P.enrollDate, R.recipe_title from recipe R,recipe_reply P where  R.recipe_no = P.recipe_no and P.REPLY_NAME=?";
		
		try {
			pstmt=conn.prepareStatement(query);
			pstmt.setString(1, userId);
			rset = pstmt.executeQuery();
			reList = new ArrayList<>();
			while(rset.next()) {
				RecipeReply re = new RecipeReply();
				re.setRecipeNo(rset.getInt("RECIPE_NO"));
				re.setReplyContents(rset.getString("CONTENTS"));
				re.setReplyDate(rset.getTimestamp("ENROLLDATE"));
				re.setRecipeTitle(rset.getString("RECIPE_TITLE"));
				reList.add(re);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		return reList;
	}

	public List<Message> myMessageSendList(Connection conn, String userId) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String query ="select * from message where msg_send_user = ? and sent_del='N' order by MSG_SEND_DATE DESC";
		List<Message> msList = null;
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, userId);
			rset = pstmt.executeQuery();
			msList= new ArrayList<>();
			
			while(rset.next()) {
				Message msg = new Message();
				msg.setMsgNo(rset.getInt("MSG_NO"));
				msg.setMsgGetUser(rset.getString("MSG_GET_USER"));
				msg.setMsgSendUser(rset.getString("MSG_SEND_USER"));
				msg.setMsgName(rset.getString("MSG_NAME"));
				msg.setMsgContents(rset.getString("MSG_CONTENTS"));
				msg.setMsgSendDate(rset.getTimestamp("MSG_SEND_DATE"));
				msList.add(msg);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		return msList;
	}

	public List<Message> myMessageGetList(Connection conn, String userId) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String query ="select * from message where msg_get_user = ? and recv_del='N' order by MSG_SEND_DATE DESC";
		List<Message> mgList = null;
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, userId);
			rset = pstmt.executeQuery();
			mgList= new ArrayList<>();
			
			while(rset.next()) {
				Message msg = new Message();
				msg.setMsgNo(rset.getInt("MSG_NO"));
				msg.setMsgGetUser(rset.getString("MSG_GET_USER"));
				msg.setMsgSendUser(rset.getString("MSG_SEND_USER"));
				msg.setMsgName(rset.getString("MSG_NAME"));
				msg.setMsgContents(rset.getString("MSG_CONTENTS"));
				msg.setMsgSendDate(rset.getTimestamp("MSG_SEND_DATE"));
				mgList.add(msg);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		return mgList;
	}
	//받은메세지 상세페이지
	public List<Message> myGetMessageDetail(Connection conn, int msgNo) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		List<Message> mdList = null;
		String query = "select * from message where msg_no=?";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, msgNo);
			rset = pstmt.executeQuery();
			mdList = new ArrayList<>();
			
			while(rset.next()) {
				Message msg = new Message();
				msg.setMsgNo(rset.getInt("MSG_NO"));
				msg.setMsgGetUser(rset.getString("MSG_GET_USER"));
				msg.setMsgSendUser(rset.getString("MSG_SEND_USER"));
				msg.setMsgName(rset.getString("MSG_NAME"));
				msg.setMsgContents(rset.getString("MSG_CONTENTS"));
				msg.setMsgSendDate(rset.getTimestamp("MSG_SEND_DATE"));
				mdList.add(msg);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
			JDBCTemplate.close(rset);
		}
		return mdList;
	}
	//받은 메세지 삭제
	public int deleteMsg(Connection conn, int msgNo) {
		int result = 0;
		PreparedStatement pstmt = null;
		String query = "update message set recv_del='Y' where msg_no=?";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, msgNo);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}
		return result;
	}

	public int replyMessage(Connection conn, Message msg) {
		int result = 0;
		PreparedStatement pstmt = null;
		String query = "INSERT INTO MESSAGE VALUES(MSG_NO_SEQ.NEXTVAL,?,?,?,?,DEFAULT,DEFAULT,DEFAULT)";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, msg.getMsgGetUser());
			pstmt.setString(2, msg.getMsgSendUser());
			pstmt.setString(3, msg.getMsgName());
			pstmt.setString(4, msg.getMsgContents());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}
		
		return result;
	}
	
}