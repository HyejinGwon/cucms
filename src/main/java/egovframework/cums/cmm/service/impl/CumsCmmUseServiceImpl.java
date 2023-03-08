package egovframework.cums.cmm.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.egovframe.rte.fdl.cmmn.exception.EgovBizException;
import org.egovframe.rte.psl.dataaccess.util.EgovMap;
import org.springframework.stereotype.Service;

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
	
}
