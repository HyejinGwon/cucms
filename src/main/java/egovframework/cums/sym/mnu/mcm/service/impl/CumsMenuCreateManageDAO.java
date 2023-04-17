package egovframework.cums.sym.mnu.mcm.service.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import egovframework.com.cmm.ComDefaultVO;
import egovframework.com.cmm.service.impl.EgovComAbstractDAO;
import egovframework.cums.sym.mnu.mcm.service.CumsMenuCreatVO;
import egovframework.cums.sym.mnu.mcm.service.CumsMenuSiteMapVO;

/**
 * 메뉴생성, 사이트맵 생성에 대한 DAO 클래스를 정의한다. *
 * @author 공통컴포넌트 개발팀 서준식
 * @since 2011.06.30
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *   2011.06.30  서 준 식   최초 생성(MenuManageDAO 클래스로 부터 분리
 *   					   메소드들을 MenuManageDAO 클래스에서 분리함)
 *
 * </pre>
 */

@Repository("cumsMenuCreateManageDAO")
public class CumsMenuCreateManageDAO extends EgovComAbstractDAO {
	
	/**
	 * ID 존재여부를 조회
	 * @param vo MenuManageVO
	 * @return int
	 * @exception Exception
	 */
	public int selectUsrByPk(ComDefaultVO vo) throws Exception{
		return (Integer)selectOne("cumsMenuCreateManageDAO.selectUsrByPk", vo);
	}

	/**
	 * ID에 대한 권한코드를 조회
	 * @param vo MenuCreatVO
	 * @return int
	 * @exception Exception
	 */
	public CumsMenuCreatVO selectAuthorByUsr(ComDefaultVO vo) throws Exception{
		return (CumsMenuCreatVO)selectOne("cumsMenuCreateManageDAO.selectAuthorByUsr", vo);
	}

	/**
	 * 메뉴생성관리 내역을 조회
	 * @param vo ComDefaultVO
	 * @return List
	 * @exception Exception
	 */
	public List<?> selectMenuCreatManagList(ComDefaultVO vo) throws Exception{
		return selectList("cumsMenuCreateManageDAO.selectMenuCreatManageList_D", vo);
	}

	/**
	 * 메뉴생성관리 총건수를 조회한다.
	 * @param vo ComDefaultVO
	 * @return int
	 * @exception Exception
	 */
    public int selectMenuCreatManagTotCnt(ComDefaultVO vo) {
        return (Integer)selectOne("cumsMenuCreateManageDAO.selectMenuCreatManageTotCnt_S", vo);
    }

    /*********** 메뉴 생성 관리 ***************/
	/**
	 * 메뉴생성 내역을 조회
	 * @param vo MenuCreatVO
	 * @return List
	 * @exception Exception
	 */
	public List<?> selectMenuCreatList(CumsMenuCreatVO vo) throws Exception{
		return selectList("cumsMenuCreateManageDAO.selectMenuCreatList_D", vo);
	}

	/**
	 * 메뉴생성내역 등록
	 * @param vo MenuCreatVO
	 * @exception Exception
	 */
	public void insertMenuCreat(CumsMenuCreatVO vo){
		insert("cumsMenuCreateManageDAO.insertMenuCreat_S", vo);
	}

	/**
	 * 메뉴생성 사이트맵 내용 조회
	 * @param vo MenuSiteMapVO
	 * @return List
	 * @exception Exception
	 */
	public List<?> selectMenuCreatSiteMapList(CumsMenuSiteMapVO vo) throws Exception{
		return selectList("cumsMenuCreateManageDAO.selectMenuCreatSiteMapList_D", vo);
	}



	/**
	 * 사이트맵 등록
	 * @param vo MenuSiteMapVO
	 * @exception Exception
	 */
	public void creatSiteMap(CumsMenuSiteMapVO vo){
		insert("cumsMenuCreateManageDAO.insertSiteMap_S", vo);
	}

	/**
	 * 사용자 권한별 사이트맵 내용 조회
	 * @param vo MenuSiteMapVO
	 * @return List
	 * @exception Exception
	 */
	public List<?> selectSiteMapByUser(CumsMenuSiteMapVO vo) throws Exception{
		return selectList("cumsMenuCreateManageDAO.selectSiteMapByUser", vo);
	}

	/**
	 * 메뉴생성내역 존재여부 조회한다.
	 * @param vo MenuCreatVO
	 * @return int
	 * @exception Exception
	 */
    public int selectMenuCreatCnt(CumsMenuCreatVO vo) {
        return (Integer)selectOne("cumsMenuCreateManageDAO.selectMenuCreatCnt_S", vo);
    }


	/**
	 * 메뉴생성내역 수정
	 * @param vo MenuCreatVO
	 * @exception Exception
	 */
	public void updateMenuCreat(CumsMenuCreatVO vo){
		update("cumsMenuCreateManageDAO.updateMenuCreat_S", vo);
	}


	/**
	 * 메뉴생성내역 삭제
	 * @param vo MenuCreatVO
	 * @exception Exception
	 */
	public void deleteMenuCreat(CumsMenuCreatVO vo){
		delete("cumsMenuCreateManageDAO.deleteMenuCreat_S", vo);
	}

	/**
	 * 사이트맵 존재여부 조회한다.
	 * @param vo MenuSiteMapVO
	 * @return int
	 * @exception Exception
	 */
    public int selectSiteMapCnt(CumsMenuSiteMapVO vo) {
        return (Integer)selectOne("cumsMenuCreateManageDAO.selectSiteMapCnt_S", vo);
    }

}
