package egovframework.cums.cop.cmy.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.cums.cop.cmy.service.CumsCommuMasterService;
import egovframework.cums.cop.cmy.service.CumsCommunity;
import egovframework.cums.cop.cmy.service.CumsCommunityVO;

import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.egovframe.rte.fdl.cmmn.exception.FdlException;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;

@Service("CumsCommuMasterService")
public class CumsCommuMasterServiceImpl extends EgovAbstractServiceImpl implements CumsCommuMasterService{

	@Resource(name = "CumsCommuMasterDAO")
    private CumsCommuMasterDAO cumsCommuMasterDAO;

    @Resource(name = "egovCmmntyIdGnrService")
    private EgovIdGnrService idgenService;
	
	@Override
	public Map<String, Object> selectCommuMasterList(CumsCommunityVO cmmntyVO) {
		
		List<?> result = cumsCommuMasterDAO.selectCommuMasterList(cmmntyVO);
		int cnt = cumsCommuMasterDAO.selectCommuMasterListCnt(cmmntyVO);

		Map<String, Object> map = new HashMap<String, Object>();

		map.put("resultList", result);
		map.put("resultCnt", Integer.toString(cnt));

		return map;
	}

	@Override
	public String insertCommuMaster(CumsCommunity community) throws FdlException {
		//게시판 ID 채번
		String cmmntyId = idgenService.getNextStringId();
		community.setCmmntyId(cmmntyId);
		
		cumsCommuMasterDAO.insertCommuMaster(community);
		
		return cmmntyId;
	}

	@Override
	public CumsCommunityVO selectCommuMaster(CumsCommunityVO cmmntyVO) throws Exception {
		CumsCommunityVO resultVO = cumsCommuMasterDAO.selectCommuMasterDetail(cmmntyVO);
        if (resultVO == null)
            throw processException("info.nodata.msg");
        return resultVO;
	}

	@Override
	public void updateCommuMaster(CumsCommunity community) {
		cumsCommuMasterDAO.updateCommuMaster(community);
	}

	@Override
	public void deleteBBSMasterInf(CumsCommunity community) {
		cumsCommuMasterDAO.deleteCommuMaster(community);
	}

	@Override
	public List<CumsCommunityVO> selectCommuMasterListPortlet(CumsCommunityVO cmmntyVO) throws Exception {
		return cumsCommuMasterDAO.selectCommuMasterListPortlet(cmmntyVO);
	}

}
