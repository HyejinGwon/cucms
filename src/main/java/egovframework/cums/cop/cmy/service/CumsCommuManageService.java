package egovframework.cums.cop.cmy.service;

import java.util.Map;

public interface CumsCommuManageService {

	Map<String, Object> selectCommuInf(CumsCommunityVO cmmntyVO);

	String checkCommuUserDetail(CumsCommunityUser cmmntyUser);

	void insertCommuUserRqst(CumsCommunityUser cmmntyUser);

	Map<String, Object> selectCommuUserList(CumsCommunityUserVO cmmntyUserVO);

	Boolean selectIsCommuAdmin(CumsCommunityUserVO userVO);

	void insertCommuUser(CumsCommunityUserVO cmmntyUserVO);

	void deleteCommuUser(CumsCommunityUserVO cmmntyUserVO);

	void insertCommuUserAdmin(CumsCommunityUserVO cmmntyUserVO);

	void deleteCommuUserAdmin(CumsCommunityUserVO cmmntyUserVO);

}
