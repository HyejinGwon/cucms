package egovframework.cums.cop.cmy.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import egovframework.cums.cop.cmy.service.CumsCommuManageService;
import egovframework.cums.cop.cmy.service.CumsCommunityUser;
import egovframework.cums.cop.cmy.service.CumsCommunityUserVO;
import egovframework.cums.cop.cmy.service.CumsCommunityVO;

import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;

@Service("CumsCommuManageService")
public class CumsCommuManageServiceImpl extends EgovAbstractServiceImpl implements CumsCommuManageService {

	@Resource(name = "CumsCommuMasterDAO")
	CumsCommuMasterDAO egovCommuMasterDao;
	
	@Resource(name = "CumsCommuManageDAO")
	CumsCommuManageDAO egovCommuManageDao;
	
	@Resource(name = "egovCmmntyIdGnrService")
    private EgovIdGnrService idgenService;

    private static final Logger LOGGER = LoggerFactory.getLogger(CumsCommuManageServiceImpl.class);
	
	@Override
	public Map<String, Object> selectCommuInf(CumsCommunityVO cmmntyVO) {
		
		//커뮤니티 기본정보 확인
		CumsCommunityVO vo = egovCommuMasterDao.selectCommuMasterDetail(cmmntyVO);

		CumsCommunityUser cmmntyUser = new CumsCommunityUser();

		cmmntyUser.setCmmntyId(cmmntyVO.getCmmntyId());
		cmmntyUser.setEmplyrId(cmmntyVO.getEmplyrId());

		cmmntyUser = egovCommuManageDao.selectSingleCommuUserDetail(cmmntyUser);

		//-----------------------------------------------------------------
		// 관리자 정보를 처리한다. (여러 명이 있을 수 있음 - DB 설계 문제상 문제)
		// 위의 처리는 cmmntyVO.getEmplyrId()가 ""이기 때문에 의미 없음..
		//-----------------------------------------------------------------
		List<CumsCommunityUser> managers = egovCommuManageDao.selectCommuManagerList(cmmntyVO);

		if (cmmntyUser == null) {
		    cmmntyUser = new CumsCommunityUser();
		}
		if (managers.size() == 1) {
			
		    cmmntyUser.setEmplyrId(managers.get(0).getEmplyrId());
		    cmmntyUser.setEmplyrNm(managers.get(0).getEmplyrNm());
		} else if (managers.size() > 1) {
		    cmmntyUser.setEmplyrId(managers.get(0).getEmplyrId());
		    cmmntyUser.setEmplyrNm(managers.get(0).getEmplyrNm() + "외 " + (managers.size() - 1) + "명");
		} else {
			LOGGER.debug("No managers...");
		}
		////---------------------------------------------------------------

		Map<String, Object> map = new HashMap<String, Object>();

		map.put("cmmntyVO", vo);
		map.put("cmmntyUser", cmmntyUser);

		return map;
	}

	@Override
	public String checkCommuUserDetail(CumsCommunityUser cmmntyUser) {

		//cmmntyId
		CumsCommunityVO vo = new CumsCommunityVO();
		vo.setCmmntyId(cmmntyUser.getCmmntyId());
		
		int userCnt = egovCommuManageDao.checkExistUser(cmmntyUser);
		
		if (userCnt == 0) {
		    return "";
		} else {
		    return "EXIST";
		}
	}

	@Override
	public void insertCommuUserRqst(CumsCommunityUser cmmntyUser) {
		egovCommuManageDao.insertCommuUserRqst(cmmntyUser);
	}

	@Override
	public Map<String, Object> selectCommuUserList(CumsCommunityUserVO cmmntyUserVO) {
		List<?> result = egovCommuManageDao.selectCommuUserList(cmmntyUserVO);
		int cnt = egovCommuManageDao.selectCommuUserListCnt(cmmntyUserVO);

		Map<String, Object> map = new HashMap<String, Object>();

		map.put("resultList", result);
		map.put("resultCnt", Integer.toString(cnt));

		return map;
	}

	@Override
	public Boolean selectIsCommuAdmin(CumsCommunityUserVO userVO) {
		
		CumsCommunityUser cmmntyUser = egovCommuManageDao.selectSingleCommuUserDetail(userVO);
		
		if(cmmntyUser==null) {
			return false;
		} else if(cmmntyUser.getMngrAt().equals("Y")) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void insertCommuUser(CumsCommunityUserVO cmmntyUserVO) {
		egovCommuManageDao.insertCommuUser(cmmntyUserVO);
	}

	@Override
	public void deleteCommuUser(CumsCommunityUserVO cmmntyUserVO) {
		egovCommuManageDao.deleteCommuUser(cmmntyUserVO);
	}

	@Override
	public void insertCommuUserAdmin(CumsCommunityUserVO cmmntyUserVO) {
		egovCommuManageDao.insertCommuUserAdmin(cmmntyUserVO);
	}

	@Override
	public void deleteCommuUserAdmin(CumsCommunityUserVO cmmntyUserVO) {
		egovCommuManageDao.deleteCommuUserAdmin(cmmntyUserVO);
	}

}
