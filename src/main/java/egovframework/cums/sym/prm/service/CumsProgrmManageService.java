package egovframework.cums.sym.prm.service;

import java.util.List;

import egovframework.com.cmm.ComDefaultVO;
import egovframework.cums.sym.prm.service.CumsProgrmManageDtlVO;
import egovframework.cums.sym.prm.service.CumsProgrmManageVO;

public interface CumsProgrmManageService {
	
	/**
	 * 프로그램 상세정보를 조회
	 * @param vo ComDefaultVO
	 * @return CumsProgrmManageVO
	 * @exception Exception
	 */
	CumsProgrmManageVO selectProgrm(CumsProgrmManageVO vo) throws Exception;
	/**
	 * 프로그램 목록을 조회
	 * @param vo ComDefaultVO
	 * @return List
	 * @exception Exception
	 */
	List<?> selectProgrmList(ComDefaultVO vo) throws Exception;
	/**
	 * 프로그램목록 총건수를 조회한다.
	 * @param vo ComDefaultVO
	 * @return int
	 * @exception Exception
	 */
	int selectProgrmListTotCnt(ComDefaultVO vo) throws Exception;
	/**
	 * 프로그램 정보를 등록
	 * @param vo CumsProgrmManageVO
	 * @exception Exception
	 */
	void insertProgrm(CumsProgrmManageVO vo) throws Exception;

	/**
	 * 프로그램 정보를 수정
	 * @param vo CumsProgrmManageVO
	 * @exception Exception
	 */
	void updateProgrm(CumsProgrmManageVO vo) throws Exception;

	/**
	 * 프로그램 정보를 삭제
	 * @param vo CumsProgrmManageVO
	 * @exception Exception
	 */
	void deleteProgrm(CumsProgrmManageVO vo) throws Exception;

	/**
	 * 프로그램 파일 존재여부를 조회
	 * @param vo ComDefaultVO
	 * @return int
	 * @exception Exception
	 */
	int selectProgrmNMTotCnt(ComDefaultVO vo) throws Exception;

	/**
	 * 프로그램변경요청 정보를 조회
	 * @param vo CumsProgrmManageDtlVO
	 * @return CumsProgrmManageDtlVO  프로그램변경요청 리스트
	 * @exception Exception
	 */
	CumsProgrmManageDtlVO selectProgrmChangeRequst(CumsProgrmManageDtlVO vo) throws Exception;

	/**
	 * 프로그램변경요청 목록을 조회
	 * @param vo ComDefaultVO
	 * @return List
	 * @exception Exception
	 */
	List<?> selectProgrmChangeRequstList(ComDefaultVO vo) throws Exception;
	/**
	 * 프로그램변경요청목록 총건수를 조회한다.
	 * @param vo ComDefaultVO
	 * @return int
	 * @exception Exception
	 */
	int selectProgrmChangeRequstListTotCnt(ComDefaultVO vo) throws Exception;

	/**
	 * 프로그램변경요청을 등록
	 * @param vo CumsProgrmManageDtlVO
	 * @exception Exception
	 */
	void insertProgrmChangeRequst(CumsProgrmManageDtlVO vo) throws Exception;

	/**
	 * 프로그램변경요청을 수정
	 * @param vo CumsProgrmManageDtlVO
	 * @exception Exception
	 */
	void updateProgrmChangeRequst(CumsProgrmManageDtlVO vo) throws Exception;

	/**
	 * 프로그램변경요청을 삭제
	 * @param vo CumsProgrmManageDtlVO
	 * @exception Exception
	 */
	void deleteProgrmChangeRequst(CumsProgrmManageDtlVO vo) throws Exception;

	/**
	 * 프로그램변경요청 요청번호MAX 정보를 조회
	 * @param vo CumsProgrmManageDtlVO
	 * @return CumsProgrmManageDtlVO
	 * @exception Exception
	 */
	CumsProgrmManageDtlVO selectProgrmChangeRequstNo(CumsProgrmManageDtlVO vo) throws Exception;

	/**
	 * 프로그램변경요청처리 목록을 조회
	 * @param vo ComDefaultVO
	 * @return List
	 * @exception Exception
	 */
	List<?> selectChangeRequstProcessList(ComDefaultVO vo) throws Exception;

	/**
	 * 프로그램변경요청처리목록 총건수를 조회한다.
	 * @param vo ComDefaultVO
	 * @return int
	 * @exception Exception
	 */
	int selectChangeRequstProcessListTotCnt(ComDefaultVO vo) throws Exception;

	/**
	 * 프로그램변경요청처리를 수정
	 * @param vo CumsProgrmManageDtlVO
	 * @exception Exception
	 */
	void updateProgrmChangeRequstProcess(CumsProgrmManageDtlVO vo) throws Exception;

	/**
	 * 화면에 조회된 메뉴 목록 정보를 데이터베이스에서 삭제
	 * @param checkedProgrmFileNmForDel String
	 * @exception Exception
	 */
	void deleteProgrmManageList(String checkedProgrmFileNmForDel) throws Exception;

	/**
	 * 프로그램변경요청자 Email 정보를 조회
	 * @param vo CumsProgrmManageDtlVO
	 * @return CumsProgrmManageDtlVO  프로그램변경요청 리스트
	 * @exception Exception
	 */
	CumsProgrmManageDtlVO selectRqesterEmail(CumsProgrmManageDtlVO vo) throws Exception;

}
