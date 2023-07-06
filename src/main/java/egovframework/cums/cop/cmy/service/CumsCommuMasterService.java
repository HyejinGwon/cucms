package egovframework.cums.cop.cmy.service;

import java.util.List;
import java.util.Map;

import org.egovframe.rte.fdl.cmmn.exception.FdlException;

public interface CumsCommuMasterService {

	Map<String, Object> selectCommuMasterList(CumsCommunityVO cmmntyVO);

	String insertCommuMaster(CumsCommunity community) throws FdlException;

	CumsCommunityVO selectCommuMaster(CumsCommunityVO cmmntyVO) throws Exception;

	void updateCommuMaster(CumsCommunity community);

	void deleteBBSMasterInf(CumsCommunity community);
	
	List<CumsCommunityVO> selectCommuMasterListPortlet(CumsCommunityVO cmmntyVO) throws Exception;
}
