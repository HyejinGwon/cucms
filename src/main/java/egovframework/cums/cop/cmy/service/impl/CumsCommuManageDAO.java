package egovframework.cums.cop.cmy.service.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import egovframework.com.cmm.service.impl.EgovComAbstractDAO;
import egovframework.cums.cop.cmy.service.CumsCommunityUser;
import egovframework.cums.cop.cmy.service.CumsCommunityUserVO;
import egovframework.cums.cop.cmy.service.CumsCommunityVO;

@Repository("CumsCommuManageDAO")
public class CumsCommuManageDAO extends EgovComAbstractDAO{

	public CumsCommunityUser selectSingleCommuUserDetail(CumsCommunityUser cmmntyUser) {
		return (CumsCommunityUser) selectOne("CommuManage.selectSingleCommuUserDetail", cmmntyUser);
	}

	public List<CumsCommunityUser> selectCommuManagerList(CumsCommunityVO cmmntyVO) {
		return selectList("CommuManage.selectCommuManagerList", cmmntyVO);
	}

	public int checkExistUser(CumsCommunityUser cmmntyUser) {
		return (Integer)selectOne("CommuManage.checkExistUser", cmmntyUser);
	}

	public void insertCommuUserRqst(CumsCommunityUser cmmntyUser) {
		insert("CommuManage.insertCommuUserRqst", cmmntyUser);
	}

	public List<?> selectCommuUserList(CumsCommunityUserVO cmmntyUserVO) {
		return list("CommuManage.selectCommuUserList", cmmntyUserVO);
	}

	public int selectCommuUserListCnt(CumsCommunityUserVO cmmntyUserVO) {
		return (Integer)selectOne("CommuManage.selectCommuUserListCnt", cmmntyUserVO);
	}

	public void insertCommuUser(CumsCommunityUserVO cmmntyUserVO) {
		update("CommuManage.insertCommuUser", cmmntyUserVO);
	}

	public void deleteCommuUser(CumsCommunityUserVO cmmntyUserVO) {
		delete("CommuManage.deleteCommuUser", cmmntyUserVO);
	}

	public void insertCommuUserAdmin(CumsCommunityUserVO cmmntyUserVO) {
		update("CommuManage.insertCommuUserAdmin", cmmntyUserVO);
	}

	public void deleteCommuUserAdmin(CumsCommunityUserVO cmmntyUserVO) {
		update("CommuManage.deleteCommuUserAdmin", cmmntyUserVO);
	}

}
