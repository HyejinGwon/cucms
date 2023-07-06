package egovframework.cums.cop.bbs.service;

import java.util.List;
import java.util.Map;

import org.egovframe.rte.fdl.cmmn.exception.FdlException;

public interface CumsArticleService {

	Map<String, Object> selectArticleList(CumsBoardVO boardVO);

	CumsBoardVO selectArticleDetail(CumsBoardVO boardVO);
	
	void insertArticle(CumsBoard board) throws FdlException;

	void updateArticle(CumsBoard board);

	void deleteArticle(CumsBoard board) throws Exception;

	List<CumsBoardVO> selectNoticeArticleList(CumsBoardVO boardVO);
	
	Map<String, Object> selectGuestArticleList(CumsBoardVO vo);
	
	/*
	 * 블로그 관련
	 */
	CumsBoardVO selectArticleCnOne(CumsBoardVO boardVO);
	
	List<CumsBoardVO> selectBlogNmList(CumsBoardVO boardVO);
	
	Map<String, Object> selectBlogListManager(CumsBoardVO boardVO);
	
	List<CumsBoardVO> selectArticleDetailDefault(CumsBoardVO boardVO);
	
	int selectArticleDetailDefaultCnt(CumsBoardVO boardVO);
	
	List<CumsBoardVO> selectArticleDetailCn(CumsBoardVO boardVO);
	
	int selectLoginUser(CumsBoardVO boardVO);
}
