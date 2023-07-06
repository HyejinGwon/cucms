package egovframework.cums.cop.cmt.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.cums.cop.bbs.service.CumsBoardMaster;
import egovframework.cums.cop.bbs.service.CumsBoardMasterVO;
import egovframework.cums.cop.bbs.service.impl.CumsBBSAddedOptionsDAO;
import egovframework.cums.cop.cmt.service.CumsArticleCommentService;
import egovframework.cums.cop.cmt.service.CumsComment;
import egovframework.cums.cop.cmt.service.CumsCommentVO;

import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.egovframe.rte.fdl.cmmn.exception.FdlException;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;

@Service("CumsArticleCommentService")
public class CumsArticleCommentServiceImpl extends EgovAbstractServiceImpl implements CumsArticleCommentService {

    @Resource(name = "CumsBBSAddedOptionsDAO")
    private CumsBBSAddedOptionsDAO addedOptionsDAO;

    @Resource(name = "CumsArticleCommentDAO")
    private CumsArticleCommentDAO egovArticleCommentDao;
    
    @Resource(name = "egovAnswerNoGnrService")
    private EgovIdGnrService egovAnswerNoGnrService;

    /**
     * 댓글 사용 가능 여부를 확인한다.
     */
    public boolean canUseComment(String bbsId) throws Exception {
	//String flag = EgovProperties.getProperty("Globals.addedOptions");
	//if (flag != null && flag.trim().equalsIgnoreCase("true")) {//2011.09.15
	    CumsBoardMaster vo = new CumsBoardMaster();
	    
	    vo.setBbsId(bbsId);
	    
	    CumsBoardMasterVO options = addedOptionsDAO.selectAddedOptionsInf(vo);
	    
	    if (options == null) {
		return false;
	    }
	    
	    if (options.getCommentAt().equals("Y")) {
		return true;
	    }
	//}
	
	return false;
    }    
	
	@Override
	public Map<String, Object> selectArticleCommentList(CumsCommentVO commentVO) {
		List<?> result = egovArticleCommentDao.selectArticleCommentList(commentVO);
		int cnt = egovArticleCommentDao.selectArticleCommentListCnt(commentVO);
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("resultList", result);
		map.put("resultCnt", Integer.toString(cnt));

		return map;
	}


	@Override
	public void insertArticleComment(CumsComment comment) throws FdlException {
		comment.setCommentNo(egovAnswerNoGnrService.getNextLongId() + "");//2011.10.18
		egovArticleCommentDao.insertArticleComment(comment);
	}


	@Override
	public void deleteArticleComment(CumsCommentVO commentVO) {
		egovArticleCommentDao.deleteArticleComment(commentVO);
	}


	@Override
	public CumsCommentVO selectArticleCommentDetail(CumsCommentVO commentVO) {
		return egovArticleCommentDao.selectArticleCommentDetail(commentVO);
	}


	@Override
	public void updateArticleComment(CumsComment comment) {
		egovArticleCommentDao.updateArticleComment(comment);
	}

}
