package egovframework.cums.cmm.service.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.egovframe.rte.fdl.cmmn.exception.EgovBizException;
import org.egovframe.rte.psl.dataaccess.util.EgovMap;
import org.springframework.stereotype.Service;

import egovframework.com.cmm.ComDefaultCodeVO;
import egovframework.com.cmm.service.CmmnDetailCode;
import egovframework.cums.cmm.service.CumsCmmUseService;



/**
 * @Class Name : CumsCmmUseServiceImpl.java
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

@Service("cumsCmmUseService")
public class CumsCmmUseServiceImpl implements CumsCmmUseService {
	@Resource(name = "cumsCmmUseDAO")
	private CumsCmmUseDAO dao;
	
	/**
     * 왼쪽메뉴의 메뉴관리 정보를 조회한다.
     *
     * @param 
     * @return
	 * @throws EgovBizException 
     */
	@Override
	public List<EgovMap> retrieveMenuMngList() throws EgovBizException {
		return dao.retrieveMenuMngList();
	}
	
	/**
     * 왼쪽메뉴의 게시판관리 정보를 조회한다.
     *
     * @param 
     * @return
	 * @throws EgovBizException 
     */
	@Override
	public List<EgovMap> retrieveBbsMngList() throws EgovBizException {
		return dao.retrieveBbsMngList();
	}
	
	/**
     * 왼쪽메뉴의 권한관리 정보를 조회한다.
     *
     * @param 
     * @return
	 * @throws EgovBizException 
     */
	@Override
	public List<EgovMap> retrieveAuthrtMngList() throws EgovBizException {
		return dao.retrieveAuthrtMngList();
	}
	
	/**
     * 공통코드를 조회한다.
     *
     * @param vo
     * @return
     * @throws Exception
     */
    public List<CmmnDetailCode> selectCmmCodeDetail(ComDefaultCodeVO vo) throws Exception {
    	return dao.selectCmmCodeDetail(vo);
    }

    /**
     * ComDefaultCodeVO의 리스트를 받아서 여러개의 코드 리스트를 맵에 담아서 리턴한다.
     *
     * @param voList
     * @return
     * @throws Exception
     */
    public Map<String, List<CmmnDetailCode>> selectCmmCodeDetails(List<?> voList) throws Exception {
		ComDefaultCodeVO vo;
		Map<String, List<CmmnDetailCode>> map = new HashMap<String, List<CmmnDetailCode>>();

		Iterator<?> iter = voList.iterator();
		while (iter.hasNext()) {
		    vo = (ComDefaultCodeVO)iter.next();
		    map.put(vo.getCodeId(), dao.selectCmmCodeDetail(vo));
		}

		return map;
    }

    /**
     * 조직정보를 코드형태로 리턴한다.
     *
     * @param 조회조건정보 vo
     * @return 조직정보 List
     * @throws Exception
     */
    public List<CmmnDetailCode> selectOgrnztIdDetail(ComDefaultCodeVO vo) throws Exception {
    	return dao.selectOgrnztIdDetail(vo);
    }

    /**
     * 그룹정보를 코드형태로 리턴한다.
     *
     * @param 조회조건정보 vo
     * @return 그룹정보 List
     * @throws Exception
     */
    public List<CmmnDetailCode> selectGroupIdDetail(ComDefaultCodeVO vo) throws Exception {
    	return dao.selectGroupIdDetail(vo);
    }
}
