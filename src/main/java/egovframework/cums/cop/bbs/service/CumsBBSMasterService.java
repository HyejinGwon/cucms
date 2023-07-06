package egovframework.cums.cop.bbs.service;

import java.util.List;
import java.util.Map;

import org.egovframe.rte.fdl.cmmn.exception.FdlException;

public interface CumsBBSMasterService {

	Map<String, Object> selectNotUsedBdMstrList(CumsBoardMasterVO boardMasterVO);

	void deleteBBSMasterInf(CumsBoardMaster boardMaster);

	void updateBBSMasterInf(CumsBoardMaster boardMaster) throws Exception;

	CumsBoardMasterVO selectBBSMasterInf(CumsBoardMasterVO boardMasterVO) throws Exception;

	Map<String, Object> selectBBSMasterInfs(CumsBoardMasterVO boardMasterVO);
	
	void insertBBSMasterInf(CumsBoardMaster boardMaster) throws Exception;

	/*
	 * 블로그 관련
	 */
	Map<String, Object> selectBlogMasterInfs(CumsBoardMasterVO boardMasterVO);
	
	String checkBlogUser(CumsBlogVO blogVO);
	
	CumsBlogVO checkBlogUser2(CumsBlogVO blogVO);

	void insertBoardBlogUserRqst(CumsBlogUser blogUser);
	
	void insertBlogMaster(CumsBlog blog) throws FdlException;
	
	CumsBlogVO selectBlogDetail(CumsBlogVO blogVO) throws Exception;

	List<CumsBlogVO> selectBlogListPortlet(CumsBlogVO blogVO) throws Exception;

	List<CumsBoardMasterVO> selectBBSListPortlet(CumsBoardMasterVO boardMasterVO) throws Exception;

}
