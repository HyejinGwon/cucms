package egovframework.cums.cop.cmy.service.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import egovframework.com.cmm.service.impl.EgovComAbstractDAO;
import egovframework.cums.cop.bbs.service.CumsBoardMasterVO;

@Repository("CumsCommuBBSMasterDAO")
public class CumsCommuBBSMasterDAO extends EgovComAbstractDAO {

	public List<CumsBoardMasterVO> selectCommuBBSMasterListMain(CumsBoardMasterVO boardMasterVO) {
		return selectList("CommuBBSMaster.selectCommuBBSMasterListMain", boardMasterVO);
	}

}
