package egovframework.cums.cop.bbs.service.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import egovframework.com.cmm.service.impl.EgovComAbstractDAO;
import egovframework.cums.cop.bbs.service.CumsBlog;
import egovframework.cums.cop.bbs.service.CumsBlogUser;
import egovframework.cums.cop.bbs.service.CumsBlogVO;
import egovframework.cums.cop.bbs.service.CumsBoardMaster;
import egovframework.cums.cop.bbs.service.CumsBoardMasterVO;

@Repository("cumsBBSMasterDao")
public class CumsBBSMasterDAO extends EgovComAbstractDAO {

	public List<?> selectBBSMasterInfs(CumsBoardMasterVO boardMasterVO) {
		return list("CumsBBSMaster.selectBBSMasterList", boardMasterVO);
	}

	public int selectBBSMasterInfsCnt(CumsBoardMasterVO boardMasterVO) {
		return (Integer)selectOne("CumsBBSMaster.selectBBSMasterListTotCnt", boardMasterVO);
	}
	
	public CumsBoardMasterVO selectBBSMasterDetail(CumsBoardMasterVO boardMasterVO) {
		return (CumsBoardMasterVO) selectOne("CumsBBSMaster.selectBBSMasterDetail", boardMasterVO);
	}

	public void insertBBSMasterInf(CumsBoardMaster boardMaster) {
		insert("CumsBBSMaster.insertBBSMaster", boardMaster);
	}

	public void updateBBSMaster(CumsBoardMaster boardMaster) {
		update("CumsBBSMaster.updateBBSMaster", boardMaster);
	}

	public void deleteBBSMaster(CumsBoardMaster boardMaster) {
		update("CumsBBSMaster.deleteBBSMaster", boardMaster);
	}
	
	/*
	 * 블로그 관련
	 */
	public List<?> selectBlogMasterInfs(CumsBoardMasterVO boardMasterVO) {
		return list("CumsBBSMaster.selectBlogMasterList", boardMasterVO);
	}
	
	public int selectBlogMasterInfsCnt(CumsBoardMasterVO boardMasterVO) {
		return (Integer)selectOne("CumsBBSMaster.selectBlogMasterListTotCnt", boardMasterVO);
	}
	
	public int checkExistUser(CumsBlogVO blogVO) {
		return (Integer)selectOne("CumsBBSMaster.checkExistUser", blogVO);
	}
	
	public CumsBlogVO checkExistUser2(CumsBlogVO blogVO) {
		return (CumsBlogVO) selectOne("CumsBBSMaster.checkExistUser2", blogVO);
	}
	
	public void insertBoardBlogUserRqst(CumsBlogUser blogUser) {
		insert("CumsBBSMaster.insertBoardBlogUserRqst", blogUser);
	}
	
	public void insertBlogMaster(CumsBlog blog) {
		insert("CumsBBSMaster.insertBlogMaster", blog);
	}
	
	public CumsBlogVO selectBlogDetail(CumsBlogVO blogVO) {
		return (CumsBlogVO) selectOne("CumsBBSMaster.selectBlogDetail", blogVO);
	}

	public List<CumsBlogVO> selectBlogListPortlet(CumsBlogVO blogVO) throws Exception{
		return (List<CumsBlogVO>) list("CumsBBSMaster.selectBlogListPortlet", blogVO);
	}

	public List<CumsBoardMasterVO> selectBBSListPortlet(CumsBoardMasterVO boardMasterVO) {
		return (List<CumsBoardMasterVO>) list("CumsBBSMaster.selectBBSListPortlet", boardMasterVO);
	}
}
