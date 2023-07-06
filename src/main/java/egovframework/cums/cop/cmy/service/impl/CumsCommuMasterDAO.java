package egovframework.cums.cop.cmy.service.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import egovframework.com.cmm.service.impl.EgovComAbstractDAO;
import egovframework.cums.cop.cmy.service.CumsCommunity;
import egovframework.cums.cop.cmy.service.CumsCommunityVO;

@Repository("CumsCommuMasterDAO")
public class CumsCommuMasterDAO extends EgovComAbstractDAO{

	public List<?> selectCommuMasterList(CumsCommunityVO cmmntyVO) {
		return list("CommuMaster.selectCommuMasterList", cmmntyVO);
	}

	public int selectCommuMasterListCnt(CumsCommunityVO cmmntyVO) {
		return (Integer)selectOne("CommuMaster.selectCommuMasterListCnt", cmmntyVO);
	}

	public void insertCommuMaster(CumsCommunity CumsCommunity) {
		insert("CommuMaster.insertCommuMaster", CumsCommunity);
		
	}

	public CumsCommunityVO selectCommuMasterDetail(CumsCommunityVO cmmntyVO) {
		return (CumsCommunityVO) selectOne("CommuMaster.selectCommuMasterDetail", cmmntyVO);
	}

	public void updateCommuMaster(CumsCommunity CumsCommunity) {
		update("CommuMaster.updateCommuMaster", CumsCommunity);
	}

	public void deleteCommuMaster(CumsCommunity CumsCommunity) {
		update("CommuMaster.deleteCommuMaster", CumsCommunity);
	}
	
    /**
     * 포트릿을 위한 커뮤니티 정보 목록 정보를 조회한다.
     *
     * @param cmmntyVO
     * @return
     * @throws Exception
     */
	@SuppressWarnings("unchecked")
    public List<CumsCommunityVO> selectCommuMasterListPortlet(CumsCommunityVO cmmntyVO) throws Exception {
		return (List<CumsCommunityVO>) list("CommuMaster.selectCommuMasterListPortlet", cmmntyVO);
    }

}
