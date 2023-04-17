package egovframework.cums.sym.mnu.mpm.service.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import egovframework.com.cmm.ComDefaultVO;
import egovframework.com.cmm.service.impl.EgovComAbstractDAO;
import egovframework.cums.sym.mnu.mpm.service.CumsMenuManageVO;

@Repository("cumsMenuManageDAO")
public class CumsMenuManageDAO extends EgovComAbstractDAO {
	/**
	 * 메뉴목록을 조회
	 * @param vo ComDefaultVO
	 * @return List
	 * @exception Exception
	 */
	public List<?> selectMenuManageList(ComDefaultVO vo) throws Exception{
		return selectList("cumsMenuManageDAO.selectMenuManageList_D", vo);
	}

    /**
	 * 메뉴목록관리 총건수를 조회한다.
	 * @param vo ComDefaultVO
	 * @return int
	 * @exception Exception
	 */
    public int selectMenuManageListTotCnt(ComDefaultVO vo) {
        return (Integer)selectOne("cumsMenuManageDAO.selectMenuManageListTotCnt_S", vo);
    }

	/**
	 * 메뉴목록관리 기본정보를 조회
	 * @param vo ComDefaultVO
	 * @return CumsMenuManageVO
	 * @exception Exception
	 */
	public CumsMenuManageVO selectMenuManage(ComDefaultVO vo)throws Exception{
		return (CumsMenuManageVO)selectOne("cumsMenuManageDAO.selectMenuManage_D", vo);
	}

	/**
	 * 메뉴목록 기본정보를 등록
	 * @param vo CumsMenuManageVO
	 * @exception Exception
	 */
	public void insertMenuManage(CumsMenuManageVO vo){
		insert("cumsMenuManageDAO.insertMenuManage_S", vo);
	}

	/**
	 * 메뉴목록 기본정보를 수정
	 * @param vo CumsMenuManageVO
	 * @exception Exception
	 */
	public void updateMenuManage(CumsMenuManageVO vo){
		update("cumsMenuManageDAO.updateMenuManage_S", vo);
	}

	/**
	 * 메뉴목록 기본정보를 삭제
	 * @param vo CumsMenuManageVO
	 * @exception Exception
	 */
	public void deleteMenuManage(CumsMenuManageVO vo){
		delete("cumsMenuManageDAO.deleteMenuManage_S", vo);
	}

	/**
	 * 메뉴 전체목록을 조회
	 * @return list
	 * @exception Exception
	 */
	public List<?> selectMenuList() throws Exception{
		ComDefaultVO vo  = new ComDefaultVO();
		return selectList("cumsMenuManageDAO.selectMenuListT_D", vo);
	}


	/**
	 * 메뉴번호 존재여부를 조회
	 * @param vo CumsMenuManageVO
	 * @return int
	 * @exception Exception
	 */
	public int selectMenuNoByPk(CumsMenuManageVO vo) throws Exception{
		return (Integer)selectOne("cumsMenuManageDAO.selectMenuNoByPk", vo);
	}



	/**
	 * 메뉴번호를 상위메뉴로 참조하고 있는 메뉴 존재여부를 조회
	 * @param vo CumsMenuManageVO
	 * @return int
	 * @exception Exception
	 */
	public int selectUpperMenuNoByPk(CumsMenuManageVO vo) throws Exception{
		return (Integer)selectOne("cumsMenuManageDAO.selectUpperMenuNoByPk", vo);
	}


	/**
	 * 메뉴정보 전체삭제 초기화
	 * @return boolean
	 * @exception Exception
	 */
	public boolean deleteAllMenuList(){
		CumsMenuManageVO vo = new CumsMenuManageVO();
		insert("cumsMenuManageDAO.deleteAllMenuList", vo);
		return true;
	}

    /**
	 * 메뉴정보 존재여부 조회한다.
	 * @return int
	 * @exception Exception
	 */
    public int selectMenuListTotCnt() {
    	CumsMenuManageVO vo = new CumsMenuManageVO();
        return (Integer)selectOne("cumsMenuManageDAO.selectMenuListTotCnt", vo);
    }


	/*### 메뉴관련 프로세스 ###*/
	/**
	 * MainMenu Head Menu 조회
	 * @param vo CumsMenuManageVO
	 * @return List
	 * @exception Exception
	 */
	public List<?> selectMainMenuHead(CumsMenuManageVO vo) throws Exception{
		return selectList("cumsMenuManageDAO.selectMainMenuHead", vo);
	}

	/**
	 * MainMenu Left Menu 조회
	 * @param vo CumsMenuManageVO
	 * @return List
	 * @exception Exception
	 */
	public List<?> selectMainMenuLeft(CumsMenuManageVO vo) throws Exception{
		return selectList("cumsMenuManageDAO.selectMainMenuLeft", vo);
	}

	/**
	 * MainMenu Head MenuURL 조회
	 * @param vo CumsMenuManageVO
	 * @return  String
	 * @exception Exception
	 */
	public String selectLastMenuURL(CumsMenuManageVO vo) throws Exception{
		return (String)selectOne("cumsMenuManageDAO.selectLastMenuURL", vo);
	}

	/**
	 * MainMenu Left Menu 조회
	 * @param vo CumsMenuManageVO
	 * @return int
	 * @exception Exception
	 */
	public int selectLastMenuNo(CumsMenuManageVO vo) throws Exception{
		return (Integer)selectOne("cumsMenuManageDAO.selectLastMenuNo", vo);
	}

	/**
	 * MainMenu Left Menu 조회
	 * @param vo CumsMenuManageVO
	 * @return int
	 * @exception Exception
	 */
	public int selectLastMenuNoCnt(CumsMenuManageVO vo) throws Exception{
		return (Integer)selectOne("cumsMenuManageDAO.selectLastMenuNoCnt", vo);
	}
}
