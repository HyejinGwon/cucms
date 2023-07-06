package egovframework.cums.cop.bbs.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.com.cmm.EgovComponentChecker;
import egovframework.cums.cop.bbs.service.CumsBBSMasterService;
import egovframework.cums.cop.bbs.service.CumsBlog;
import egovframework.cums.cop.bbs.service.CumsBlogUser;
import egovframework.cums.cop.bbs.service.CumsBlogVO;
import egovframework.cums.cop.bbs.service.CumsBoardMaster;
import egovframework.cums.cop.bbs.service.CumsBoardMasterVO;

import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.egovframe.rte.fdl.cmmn.exception.FdlException;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;

@Service("CumsBBSMasterService")
public class CumsBBSMasterServiceImpl extends EgovAbstractServiceImpl implements CumsBBSMasterService {

	@Resource(name = "cumsBBSMasterDao")
    private CumsBBSMasterDAO cumsBBSMasterDao;

    @Resource(name = "egovBBSMstrIdGnrService")
    private EgovIdGnrService idgenService;
	
    //---------------------------------
    // 2009.06.26 : 2단계 기능 추가
    //---------------------------------
    @Resource(name = "CumsBBSAddedOptionsDAO")
    private CumsBBSAddedOptionsDAO addedOptionsDAO;
    ////-------------------------------
    
	@Override
	public Map<String, Object> selectNotUsedBdMstrList(CumsBoardMasterVO boardMasterVO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteBBSMasterInf(CumsBoardMaster boardMaster) {
		cumsBBSMasterDao.deleteBBSMaster(boardMaster);	
	}

	@Override
	public void updateBBSMasterInf(CumsBoardMaster boardMaster) throws Exception {
		cumsBBSMasterDao.updateBBSMaster(boardMaster);
		
		//---------------------------------
		// 2009.06.26 : 2단계 기능 추가
		//---------------------------------
		if (boardMaster.getOption().equals("comment") || boardMaster.getOption().equals("stsfdg")) {
		    addedOptionsDAO.insertAddedOptionsInf(boardMaster);
		}
		
	}

	@Override
	public CumsBoardMasterVO selectBBSMasterInf(CumsBoardMasterVO boardMasterVO) throws Exception {
		CumsBoardMasterVO resultVO = cumsBBSMasterDao.selectBBSMasterDetail(boardMasterVO);
        if (resultVO == null)
            throw processException("info.nodata.msg");
        
//    	if(EgovComponentChecker.hasComponent("CumsBBSCommentService") || EgovComponentChecker.hasComponent("CumsBBSSatisfactionService")){//2011.09.15
//    	    CumsBoardMasterVO options = addedOptionsDAO.selectAddedOptionsInf(boardMasterVO);
//    	    
//    	    if (options != null) {
//	    		if (options.getCommentAt().equals("Y")) {
//	    			resultVO.setOption("comment");
//	    		}
//	
//	    		if (options.getStsfdgAt().equals("Y")) {
//	    			resultVO.setOption("stsfdg");
//	    		}
//    	    } else {
//    	    	resultVO.setOption("na");	// 미지정 상태로 수정 가능 (이미 지정된 경우는 수정 불가로 처리)
//    	    }
//    	}
        
        return resultVO;
	}

	@Override
	public Map<String, Object> selectBBSMasterInfs(CumsBoardMasterVO boardMasterVO) {
		List<?> result = cumsBBSMasterDao.selectBBSMasterInfs(boardMasterVO);
		int cnt = cumsBBSMasterDao.selectBBSMasterInfsCnt(boardMasterVO);
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("resultList", result);
		map.put("resultCnt", Integer.toString(cnt));

		return map;
	}
	
	@Override
	public Map<String, Object> selectBlogMasterInfs(CumsBoardMasterVO boardMasterVO) {
		List<?> result = cumsBBSMasterDao.selectBlogMasterInfs(boardMasterVO);
		int cnt = cumsBBSMasterDao.selectBlogMasterInfsCnt(boardMasterVO);
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("resultList", result);
		map.put("resultCnt", Integer.toString(cnt));

		return map;
	}

	@Override
	public void insertBBSMasterInf(CumsBoardMaster boardMaster) throws Exception {
		
		//게시판 ID 채번
		String bbsId = idgenService.getNextStringId();
		boardMaster.setBbsId(bbsId);
		
		cumsBBSMasterDao.insertBBSMasterInf(boardMaster);
		
		//---------------------------------
		// 2009.06.26 : 2단계 기능 추가
		//---------------------------------
		if (boardMaster.getOption().equals("comment") || boardMaster.getOption().equals("stsfdg")) {
		    addedOptionsDAO.insertAddedOptionsInf(boardMaster);
		}

	}
	
	@Override
	public String checkBlogUser(CumsBlogVO blogVO) {

		int userCnt = cumsBBSMasterDao.checkExistUser(blogVO);
		
		if (userCnt == 0) {
		    return "";
		} else {
		    return "EXIST";
		}
	}
	
	@Override
	public CumsBlogVO checkBlogUser2(CumsBlogVO blogVO) {
		CumsBlogVO userBlog = cumsBBSMasterDao.checkExistUser2(blogVO);
		return userBlog;
	}
	
	@Override
	public void insertBoardBlogUserRqst(CumsBlogUser blogUser) {
		cumsBBSMasterDao.insertBoardBlogUserRqst(blogUser);
	}
	
	@Override
	public void insertBlogMaster(CumsBlog blog) throws FdlException {
		cumsBBSMasterDao.insertBlogMaster(blog);
	}
	
	@Override
	public CumsBlogVO selectBlogDetail(CumsBlogVO blogVO) throws Exception {
		CumsBlogVO resultVO = cumsBBSMasterDao.selectBlogDetail(blogVO);
        if (resultVO == null)
            throw processException("info.nodata.msg");
        return resultVO;
	}

	@Override
	public List<CumsBlogVO> selectBlogListPortlet(CumsBlogVO blogVO) throws Exception{
		return cumsBBSMasterDao.selectBlogListPortlet(blogVO);
	}

	@Override
	public List<CumsBoardMasterVO> selectBBSListPortlet(CumsBoardMasterVO boardMasterVO) throws Exception {
		return cumsBBSMasterDao.selectBBSListPortlet(boardMasterVO);
	}

}
