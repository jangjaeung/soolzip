package recipe.model.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import common.JDBCTemplate;
import recipe.model.dao.RecipeDAO;
import recipe.model.vo.PageData;
import recipe.model.vo.Recipe;
import recipe.model.vo.RecipeFile;
import recipe.model.vo.RecipeIngredient;
import recipe.model.vo.RecipeMakeProcess;

public class RecipeService {

	private JDBCTemplate jdbcTemplate;

	public RecipeService() {
		jdbcTemplate = JDBCTemplate.getConnection();
	}

	public int registerRecipe(Recipe recipe, List<RecipeIngredient> ingredList, List<RecipeMakeProcess> makeList) {
		int result = Integer.MIN_VALUE;
		Connection conn = null;
		RecipeDAO recipeDAO = new RecipeDAO();
		try {
			conn = jdbcTemplate.createConnection();
			// 레시피의 파일id의 (대표사진) 파일name 존재할시 file 인서트(대표사진)
			if (recipe.getRecipeFile().getFileName() != null) {
				recipe.setFileNo(String.valueOf(recipeDAO.inserRecipeFile(conn, recipe.getRecipeFile())));
			}
			// result는 등록된 레시피 번호 (시퀀스 번호)
			result = recipeDAO.insertRecipe(conn, recipe);

			if (result > Integer.MIN_VALUE) {

				for (RecipeMakeProcess tmp : makeList) {
					if (tmp.getRecipeFile().getFileName() != null) {
						tmp.setFileNo(String.valueOf(recipeDAO.inserRecipeFile(conn, tmp.getRecipeFile())));
					}
					if (recipeDAO.insertRecipeMakeProcess(conn, tmp, result) <= 0)
						throw new SQLException("error");
				}

				for (RecipeIngredient tmp : ingredList) {
					if (recipeDAO.insertRecipeIngred(conn, tmp, result) <= 0)
						throw new SQLException("error");
				}
				JDBCTemplate.commit(conn);
			} else {
				JDBCTemplate.rollback(conn);
			}
		} catch (SQLException e) {
			JDBCTemplate.rollback(conn);
			// e.printStackTrace();
		} finally {
			JDBCTemplate.close(conn);
		}
		return result;
	}

	public PageData printAllRecipe(int currentPage) {
//		List<Recipe> rList = null;
		PageData pd = new PageData();

		Connection conn = null;
		RecipeDAO rDao = new RecipeDAO();

		try {
			conn = jdbcTemplate.createConnection();
			pd.setRecipeList(rDao.selectAllRecipe(conn, currentPage));
			pd.setPageNavi(rDao.getPageNavi(conn, currentPage));
//			rList = rDao.selectAllRecipe(conn);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(conn); 
		}
		return pd;
	}

	

	public Recipe printOneRecipe(int recipeNo) {
		Recipe recipeOne = null;
		Connection conn = null;
		RecipeDAO rDao = new RecipeDAO();
		
		try {
			conn= jdbcTemplate.createConnection();
			recipeOne= rDao.selectOneRecipe(conn, recipeNo);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(conn);
		}
		return recipeOne;
	}

	public List<RecipeIngredient> printOneRecipeIngr(int recipeNo) {
		List<RecipeIngredient> iList = null;
		Connection conn = null;
		RecipeDAO rDao = new RecipeDAO();

		try {
			conn = jdbcTemplate.createConnection();
			iList = rDao.selectOneRecipeIngr(conn, recipeNo);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(conn);
		}
		return iList;
	}

	public List<RecipeMakeProcess> printOneRecipeMkProcess(int recipeNo) {
		List<RecipeMakeProcess> mList = null;
		Connection conn = null;
		RecipeDAO rDao = new RecipeDAO();

		try {
			conn = jdbcTemplate.createConnection();
			mList = rDao.selectOneRecipeMkProcess(conn, recipeNo);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(conn);
		}
		return mList;
	}

}
