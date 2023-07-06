package egovframework.cums.cop.bbs.service.impl;

import egovframework.com.cmm.service.impl.EgovComAbstractDAO;
import egovframework.cums.cop.bbs.service.CumsBoardMaster;
import egovframework.cums.cop.bbs.service.CumsBoardMasterVO;

import org.springframework.stereotype.Repository;

/**
 * 2단계 기능 추가 (댓글관리, 만족도조사) 관리를 위한 데이터 접근 클래스
 * @author 공통컴포넌트개발팀 한성곤
 * @since 2009.06.26
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *   2009.06.26  한성곤          최초 생성
 *
 * </pre>
 */
@Repository("CumsBBSAddedOptionsDAO")
public class CumsBBSAddedOptionsDAO extends EgovComAbstractDAO {

    /**
     * 신규 게시판 추가기능 정보를 등록한다.
     * 
     * @param BoardMaster
     */
    public String insertAddedOptionsInf(CumsBoardMaster boardMaster) throws Exception {
	return Integer.toString(insert("CumsBBSAddedOptions.insertAddedOptionsInf", boardMaster));
    }
    
    /**
     * 게시판 추가기능 정보 한 건을 상세조회 한다.
     * 
     * @param BoardMasterVO
     */
    public CumsBoardMasterVO selectAddedOptionsInf(CumsBoardMaster vo) throws Exception {
	return (CumsBoardMasterVO)selectOne("CumsBBSAddedOptions.selectAddedOptionsInf", vo);
    }
    
    /**
     * 게시판 추가기능 정보를 수정한다.
     * 
     * @param BoardMaster
     */
    public void updateAddedOptionsInf(CumsBoardMaster boardMaster) throws Exception {
	update("CumsBBSAddedOptions.updateAddedOptionsInf", boardMaster);
    }
}
