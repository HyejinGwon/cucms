package egovframework.cums.sym.prm.service.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import egovframework.com.cmm.ComDefaultVO;
import egovframework.com.cmm.service.impl.EgovComAbstractDAO;
import egovframework.cums.sym.prm.service.CumsProgrmManageDtlVO;
import egovframework.cums.sym.prm.service.CumsProgrmManageVO;

@Repository("cumsProgrmManageDAO")
public class CumsProgrmManageDAO  extends EgovComAbstractDAO {

	/**
	 * 프로그램 목록을 조회
	 * @param vo ComDefaultVO
	 * @return List
	 * @exception Exception
	 */
	public List<?> selectProgrmList(ComDefaultVO vo) throws Exception{
		return selectList("cumsProgrmManageDAO.selectProgrmList_D", vo);
	}

    /**
	 * 프로그램목록 총건수를 조회한다.
	 * @param vo ComDefaultVO
	 * @return int
	 * @exception Exception
	 */
    public int selectProgrmListTotCnt(ComDefaultVO vo) {
        return (Integer)selectOne("cumsProgrmManageDAO.selectProgrmListTotCnt_S", vo);
    }

	/**
	 * 프로그램 기본정보를 조회
	 * @param vo ComDefaultVO
	 * @return CumsProgrmManageVO
	 * @exception Exception
	 */
	public CumsProgrmManageVO selectProgrm(CumsProgrmManageVO vo)throws Exception{
		return (CumsProgrmManageVO)selectOne("cumsProgrmManageDAO.selectProgrm_D", vo);
	}

	/**
	 * 프로그램 기본정보 및 URL을 등록
	 * @param vo CumsProgrmManageVO
	 * @exception Exception
	 */
	public void insertProgrm(CumsProgrmManageVO vo){
		insert("cumsProgrmManageDAO.insertProgrm_S", vo);
	}

	/**
	 * 프로그램 기본정보 및 URL을 수정
	 * @param vo CumsProgrmManageVO
	 * @exception Exception
	 */
	public void updateProgrm(CumsProgrmManageVO vo){
		update("cumsProgrmManageDAO.updateProgrm_S", vo);
	}

	/**
	 * 프로그램 기본정보 및 URL을 삭제
	 * @param vo CumsProgrmManageVO
	 * @exception Exception
	 */
	public void deleteProgrm(CumsProgrmManageVO vo){
		delete("cumsProgrmManageDAO.deleteProgrm_S", vo);
	}

	/**
	 * 프로그램 파일 존재여부를 조회
	 * @param vo CumsProgrmManageVO
	 * @return int
	 * @exception Exception
	 */
	public int selectProgrmNMTotCnt(ComDefaultVO vo) throws Exception{
		return (Integer)selectOne("cumsProgrmManageDAO.selectProgrmNMTotCnt", vo);
	}


	/**
	 * 프로그램변경요청 목록을 조회
	 * @param vo ComDefaultVO
	 * @return List
	 * @exception Exception
	 */

	public List<?> selectProgrmChangeRequstList(ComDefaultVO vo) throws Exception{
		return selectList("cumsProgrmManageDAO.selectProgrmChangeRequstList_D", vo);
	}

    /**
	 * 프로그램변경요청 총건수를 조회한다.
	 * @param vo ComDefaultVO
	 * @return  int
	 * @exception Exception
	 */
    public int selectProgrmChangeRequstListTotCnt(ComDefaultVO vo) {
        return (Integer)selectOne("cumsProgrmManageDAO.selectProgrmChangeRequstListTotCnt_S", vo);
    }

	/**
	 * 프로그램변경요청 정보를 조회
	 * @param vo CumsProgrmManageDtlVO
	 * @return CumsProgrmManageDtlVO
	 * @exception Exception
	 */
	public CumsProgrmManageDtlVO selectProgrmChangeRequst(CumsProgrmManageDtlVO vo)throws Exception{
		return (CumsProgrmManageDtlVO)selectOne("cumsProgrmManageDAO.selectProgrmChangeRequst_D", vo);
	}

	/**
	 * 프로그램변경요청을 등록
	 * @param vo CumsProgrmManageDtlVO
	 * @exception Exception
	 */
	public void insertProgrmChangeRequst(CumsProgrmManageDtlVO vo){
		insert("cumsProgrmManageDAO.insertProgrmChangeRequst_S", vo);
	}

	/**
	 * 프로그램변경요청을 수정
	 * @param vo CumsProgrmManageDtlVO
	 * @exception Exception
	 */
	public void updateProgrmChangeRequst(CumsProgrmManageDtlVO vo){
		update("cumsProgrmManageDAO.updateProgrmChangeRequst_S", vo);
	}

	/**
	 * 프로그램변경요청을 삭제
	 * @param vo CumsProgrmManageDtlVO
	 * @exception Exception
	 */
	public void deleteProgrmChangeRequst(CumsProgrmManageDtlVO vo){
		delete("cumsProgrmManageDAO.deleteProgrmChangeRequst_S", vo);
	}

	/**
	 * 프로그램변경요청 요청번호MAX 정보를 조회
	 * @param vo CumsProgrmManageDtlVO
	 * @return CumsProgrmManageDtlVO
	 * @exception Exception
	 */
	public CumsProgrmManageDtlVO selectProgrmChangeRequstNo(CumsProgrmManageDtlVO vo){
		return (CumsProgrmManageDtlVO)selectOne("cumsProgrmManageDAO.selectProgrmChangeRequstNo_D", vo);
	}

	/**
	 * 프로그램변경요청 목록을 조회
	 * @param vo ComDefaultVO
	 * @return List
	 * @exception Exception
	 */
	public List<?> selectChangeRequstProcessList(ComDefaultVO vo) throws Exception{
		return selectList("cumsProgrmManageDAO.selectChangeRequstProcessList_D", vo);
	}

    /**
	 * 프로그램변경요청 총건수를 조회한다.
	 * @param vo ComDefaultVO
	 * @return int
	 * @exception Exception
	 */
    public int selectChangeRequstListProcessTotCnt(ComDefaultVO vo) {
        return (Integer)selectOne("cumsProgrmManageDAO.selectChangeRequstProcessListTotCnt_S", vo);
    }

	/**
	 * 프로그램변경요청 처리 수정
	 * @param vo CumsProgrmManageDtlVO
	 * @exception Exception
	 */
	public void updateProgrmChangeRequstProcess(CumsProgrmManageDtlVO vo){
		update("cumsProgrmManageDAO.updateProgrmChangeRequstProcess_S", vo);
	}


	/**
	 * 프로그램목록 전체삭제 초기화
	 * @return boolean
	 * @exception Exception
	 */
	public boolean deleteAllProgrm(){
		CumsProgrmManageVO vo = new CumsProgrmManageVO();
		update("cumsProgrmManageDAO.deleteAllProgrm", vo);
		return true;
	}

	/**
	 * 프로그램변경내역 전체삭제 초기화
	 * @return boolean
	 * @exception Exception
	 */
	public boolean deleteAllProgrmDtls(){
		CumsProgrmManageDtlVO vo = new CumsProgrmManageDtlVO();
		update("cumsProgrmManageDAO.deleteAllProgrmDtls", vo);
		return true;
	}

    /**
	 * 프로그램목록 데이타 존재여부 조회한다.
	 * @return int
	 * @exception Exception
	 */
    public int selectProgrmListTotCnt() {
    	CumsProgrmManageVO vo = new CumsProgrmManageVO();
        return (Integer)selectOne("cumsProgrmManageDAO.selectProgrmListTotCnt", vo);
    }

	/**
	 * 프로그램변경요청자 Email 정보를 조회
	 * @param vo CumsProgrmManageDtlVO
	 * @return CumsProgrmManageDtlVO
	 * @exception Exception
	 */
	public CumsProgrmManageDtlVO selectRqesterEmail(CumsProgrmManageDtlVO vo){
		return (CumsProgrmManageDtlVO)selectOne("cumsProgrmManageDAO.selectRqesterEmail", vo);
	}
}
