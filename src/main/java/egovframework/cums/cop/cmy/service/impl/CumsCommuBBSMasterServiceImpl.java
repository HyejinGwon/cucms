package egovframework.cums.cop.cmy.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.cums.cop.bbs.service.CumsBoardMasterVO;
import egovframework.cums.cop.cmy.service.CumsCommuBBSMasterService;

import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;

@Service("CumsCommuBBSMasterService")
public class CumsCommuBBSMasterServiceImpl extends EgovAbstractServiceImpl implements CumsCommuBBSMasterService {

	@Resource(name = "CumsCommuBBSMasterDAO")
    private CumsCommuBBSMasterDAO cumsCommuBBSMasterDao;
	
	@Resource(name = "egovBBSMstrIdGnrService")
    private EgovIdGnrService idgenService;
	
	@Override
	public List<CumsBoardMasterVO> selectCommuBBSMasterListMain(CumsBoardMasterVO boardMasterVO) {
		return cumsCommuBBSMasterDao.selectCommuBBSMasterListMain(boardMasterVO);
	}


}
