package egovframework.cums.cmm.service.impl;

import java.util.List;

import egovframework.com.cmm.ComDefaultCodeVO;
import egovframework.com.cmm.service.CmmnDetailCode;
import egovframework.com.cmm.service.impl.EgovComAbstractDAO;

import org.egovframe.rte.fdl.cmmn.exception.EgovBizException;
import org.egovframe.rte.psl.dataaccess.util.EgovMap;
import org.springframework.stereotype.Repository;

/**
 * @Class Name : CumsCmmUseDAO.java
 * @Description : 업무에서 공용해서 사용해야 하는 서비스를 정의하기 위한 서비스 구현 클래스
 * @Modification Information
 *
 *    수정일          수정자         수정내용
 *    -------        -------     -------------------
 *    2023.3.06.   권혜진
 *
 * @author 권혜진
 * @since 2023.3.06.
 * @version
 * @see
 *
 */
@Repository("cumsCmmUseDAO")
public class CumsCmmUseDAO extends EgovComAbstractDAO {

	/**
     * 왼쪽메뉴의 메뉴관리 정보를 조회한다.
     *
     * @param 
     * @return
	 * @throws EgovBizException 
     */
    public List<EgovMap> retrieveMenuMngList() throws EgovBizException {
	return selectList("cumsCmmUseDAO.retrieveMenuMngList");
    }

    /**
     * 왼쪽메뉴의 게시판관리 정보를 조회한다.
     *
     * @param 
     * @return
	 * @throws EgovBizException 
     */
	public List<EgovMap> retrieveBbsMngList() {
		return selectList("cumsCmmUseDAO.retrieveBbsMngList");
	}
	
	/**
     * 왼쪽메뉴의 권한관리 정보를 조회한다.
     *
     * @param 
     * @return
	 * @throws EgovBizException 
     */
	public List<EgovMap> retrieveAuthrtMngList() {
		return selectList("cumsCmmUseDAO.retrieveAuthrtMngList");
	}

	/**
     * 주어진 조건에 따른 공통코드를 불러온다.
     * 
     * @param vo
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public List<CmmnDetailCode> selectCmmCodeDetail(ComDefaultCodeVO vo) throws Exception {
	return (List<CmmnDetailCode>) list("cumsCmmUseDAO.selectCumsCmmCodeDetail", vo);
    }

    /**
     * 공통코드로 사용할 조직정보를 를 불러온다.
     * 
     * @param vo
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public List<CmmnDetailCode> selectOgrnztIdDetail(ComDefaultCodeVO vo) throws Exception {
	return (List<CmmnDetailCode>) list("cmmUseDAO.selectOgrnztIdDetail", vo);
    }

    /**
     * 공통코드로 사용할그룹정보를 를 불러온다.
     * @param vo
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public List<CmmnDetailCode> selectGroupIdDetail(ComDefaultCodeVO vo) throws Exception {
	return (List<CmmnDetailCode>) list("cmmUseDAO.selectGroupIdDetail", vo);
    }
}
