package egovframework.cums.cop.bbs.service.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import egovframework.com.cmm.service.impl.EgovComAbstractDAO;
import egovframework.cums.cop.bbs.service.CumsBoard;
import egovframework.cums.cop.bbs.service.CumsBoardVO;

@Repository("CumsArticleDAO")
public class CumsArticleDAO extends EgovComAbstractDAO {

	public List<?> selectArticleList(CumsBoardVO boardVO) {
		return list("BBSArticle.selectArticleList", boardVO);
	}

	public int selectArticleListCnt(CumsBoardVO boardVO) {
		return (Integer)selectOne("BBSArticle.selectArticleListCnt", boardVO);
	}

	public int selectMaxInqireCo(CumsBoardVO boardVO) {
		return (Integer)selectOne("BBSArticle.selectMaxInqireCo", boardVO);
	}

	public void updateInqireCo(CumsBoardVO boardVO) {
		update("BBSArticle.updateInqireCo", boardVO);
	}

	public CumsBoardVO selectArticleDetail(CumsBoardVO boardVO) {
		return (CumsBoardVO) selectOne("BBSArticle.selectArticleDetail", boardVO);
	}
	
	public void replyArticle(CumsBoard board) {
		insert("BBSArticle.replyArticle", board);
	}

	public void insertArticle(CumsBoard board) {
		insert("BBSArticle.insertArticle", board);
	}

	public void updateArticle(CumsBoard board) {
		update("BBSArticle.updateArticle", board);
	}

	public void deleteArticle(CumsBoard board) {
		update("BBSArticle.deleteArticle", board);
		
	}

	public List<CumsBoardVO> selectNoticeArticleList(CumsBoardVO boardVO) {
		return (List<CumsBoardVO>) list("BBSArticle.selectNoticeArticleList", boardVO);
	}
	
	public List<?> selectGuestArticleList(CumsBoardVO vo) {
		return list("BBSArticle.selectGuestArticleList", vo);
	}

	public int selectGuestArticleListCnt(CumsBoardVO vo) {
		return (Integer)selectOne("BBSArticle.selectGuestArticleListCnt", vo);
	}
	
	/*
	 * 블로그 관련
	 */
	public CumsBoardVO selectArticleCnOne(CumsBoardVO boardVO) {
		return (CumsBoardVO) selectOne("BBSArticle.selectArticleCnOne", boardVO);
	}
	
	public List<CumsBoardVO> selectBlogNmList(CumsBoardVO boardVO) {
		return (List<CumsBoardVO>) list("BBSArticle.selectBlogNmList", boardVO);
	}
	
	public List<?> selectBlogListManager(CumsBoardVO vo) {
		return list("BBSArticle.selectBlogListManager", vo);
	}
	
	public int selectBlogListManagerCnt(CumsBoardVO vo) {
		return (Integer)selectOne("BBSArticle.selectBlogListManagerCnt", vo);
	}
	
	public List<CumsBoardVO> selectArticleDetailDefault(CumsBoardVO boardVO) {
		return (List<CumsBoardVO>) list("BBSArticle.selectArticleDetailDefault", boardVO);
	}
	
	public int selectArticleDetailDefaultCnt(CumsBoardVO boardVO) {
		return (Integer)selectOne("BBSArticle.selectArticleDetailDefaultCnt", boardVO);
	}
	
	public List<CumsBoardVO> selectArticleDetailCn(CumsBoardVO boardVO) {
		return (List<CumsBoardVO>) list("BBSArticle.selectArticleDetailCn", boardVO);
	}
	
	public int selectLoginUser(CumsBoardVO boardVO) {
		return (Integer)selectOne("BBSArticle.selectLoginUser", boardVO);
	}
	

}
