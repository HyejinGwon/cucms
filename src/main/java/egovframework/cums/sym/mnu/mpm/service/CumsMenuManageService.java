package egovframework.cums.sym.mnu.mpm.service;

import java.io.InputStream;
import java.util.List;

import egovframework.com.cmm.ComDefaultVO;

public interface CumsMenuManageService {
	/**
	 * 메뉴 상세정보를 조회
	 * @param vo ComDefaultVO
	 * @return CumsMenuManageVO
	 * @exception Exception
	 */
	CumsMenuManageVO selectMenuManage(ComDefaultVO vo) throws Exception;

	/**
	 * 메뉴 목록을 조회
	 * @param vo ComDefaultVO
	 * @return List
	 * @exception Exception
	 */
	List<?> selectMenuManageList(ComDefaultVO vo) throws Exception;

	/**
	 * 메뉴목록 총건수를 조회한다.
	 * @param vo ComDefaultVO
	 * @return int
	 * @exception Exception
	 */
	int selectMenuManageListTotCnt(ComDefaultVO vo) throws Exception;

	/**
	 * 메뉴번호 존재 여부를 조회한다.
	 * @param vo ComDefaultVO
	 * @return int
	 * @exception Exception
	 */
	int selectMenuNoByPk(CumsMenuManageVO vo) throws Exception;

	int selectUpperMenuNoByPk(CumsMenuManageVO vo) throws Exception;

	/**
	 * 메뉴 정보를 등록
	 * @param vo CumsMenuManageVO
	 * @exception Exception
	 */
	void insertMenuManage(CumsMenuManageVO vo) throws Exception;

	/**
	 * 메뉴 정보를 수정
	 * @param vo CumsMenuManageVO
	 * @exception Exception
	 */
	void updateMenuManage(CumsMenuManageVO vo) throws Exception;

	/**
	 * 메뉴 정보를 삭제
	 * @param vo CumsMenuManageVO
	 * @exception Exception
	 */
	void deleteMenuManage(CumsMenuManageVO vo) throws Exception;

	/**
	 * 화면에 조회된 메뉴 목록 정보를 데이터베이스에서 삭제
	 * @param checkedMenuNoForDel String
	 * @exception Exception
	 */
	void deleteMenuManageList(String checkedMenuNoForDel) throws Exception;

	/*  메뉴 생성 관리  */

	/**
	 * 메뉴 목록을 조회
	 * @return List
	 * @exception Exception
	 */
	List<?> selectMenuList() throws Exception;

	/*### 메뉴관련 프로세스 ###*/
	/**
	 * MainMenu Head Menu 조회
	 * @param vo CumsMenuManageVO
	 * @return List
	 * @exception Exception
	 */
	List<?> selectMainMenuHead(CumsMenuManageVO vo) throws Exception;

	/**
	 * MainMenu Head Left 조회
	 * @param vo CumsMenuManageVO
	 * @return List
	 * @exception Exception
	 */
	List<?> selectMainMenuLeft(CumsMenuManageVO vo) throws Exception;

	/**
	 * MainMenu Head MenuURL 조회
	 * @param iMenuNo int
	 * @param sUniqId String
	 * @return String
	 * @exception Exception
	 */
	String selectLastMenuURL(int iMenuNo, String sUniqId) throws Exception;

	/* 일괄처리 프로세스   */

	/**
	 * 메뉴일괄초기화 프로세스 메뉴목록테이블, 프로그램 목록테이블 전체 삭제
	 * @return boolean
	 */
	boolean menuBndeAllDelete() throws Exception;

	/**
	 * 메뉴일괄등록 프로세스
	 * @param  vo CumsMenuManageVO
	 * @param  inputStream InputStream
	 * @exception Exception
	 */
	String menuBndeRegist(CumsMenuManageVO vo, InputStream inputStream) throws Exception;
}
