package egovframework.cums.sym.prm.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.springframework.stereotype.Service;

import egovframework.com.cmm.ComDefaultVO;
import egovframework.cums.sym.prm.service.CumsProgrmManageDtlVO;
import egovframework.cums.sym.prm.service.CumsProgrmManageVO;
import egovframework.cums.sym.prm.service.CumsProgrmManageService;

@Service("cumsProgrmManageService")
public class CumsProgrmManageServiceImpl extends EgovAbstractServiceImpl implements CumsProgrmManageService {
	@Resource(name="cumsProgrmManageDAO")
	private CumsProgrmManageDAO cumsProgrmManageDAO;
	
	/**
	 * 프로그램 상세정보를 조회
	 * @param vo ComDefaultVO
	 * @return CumsProgrmManageVO
	 * @exception Exception
	 */
	@Override
	public CumsProgrmManageVO selectProgrm(CumsProgrmManageVO vo) throws Exception{
         	return cumsProgrmManageDAO.selectProgrm(vo);
	}
	/**
	 * 프로그램 목록을 조회
	 * @param vo ComDefaultVO
	 * @return List
	 * @exception Exception
	 */
	@Override
	public List<?> selectProgrmList(ComDefaultVO vo) throws Exception {
   		return cumsProgrmManageDAO.selectProgrmList(vo);
	}
	/**
	 * 프로그램목록 총건수를 조회한다.
	 * @param vo  ComDefaultVO
	 * @return Integer
	 * @exception Exception
	 */
    @Override
	public int selectProgrmListTotCnt(ComDefaultVO vo) throws Exception {
        return cumsProgrmManageDAO.selectProgrmListTotCnt(vo);
	}
	/**
	 * 프로그램 정보를 등록
	 * @param vo CumsProgrmManageVO
	 * @exception Exception
	 */
    @Override
	public void insertProgrm(CumsProgrmManageVO vo) throws Exception {
    	cumsProgrmManageDAO.insertProgrm(vo);
	}

	/**
	 * 프로그램 정보를 수정
	 * @param vo CumsProgrmManageVO
	 * @exception Exception
	 */
	@Override
	public void updateProgrm(CumsProgrmManageVO vo) throws Exception {
    	cumsProgrmManageDAO.updateProgrm(vo);
	}

	/**
	 * 프로그램 정보를 삭제
	 * @param vo CumsProgrmManageVO
	 * @exception Exception
	 */
	@Override
	public void deleteProgrm(CumsProgrmManageVO vo) throws Exception {
    	cumsProgrmManageDAO.deleteProgrm(vo);
	}

	/**
	 * 프로그램 파일 존재여부를 조회
	 * @param vo ComDefaultVO
	 * @return int
	 * @exception Exception
	 */
	@Override
	public int selectProgrmNMTotCnt(ComDefaultVO vo) throws Exception{
		return cumsProgrmManageDAO.selectProgrmNMTotCnt(vo);
	}

	/**
	 * 프로그램변경요청 정보를 조회
	 * @param vo CumsProgrmManageDtlVO
	 * @return CumsProgrmManageDtlVO
	 * @exception Exception
	 */
	@Override
	public CumsProgrmManageDtlVO selectProgrmChangeRequst(CumsProgrmManageDtlVO vo) throws Exception{
       	return cumsProgrmManageDAO.selectProgrmChangeRequst(vo);
	}

	/**
	 * 프로그램변경요청 목록을 조회
	 * @param vo ComDefaultVO
	 * @return List
	 * @exception Exception
	 */
	@Override
	public List<?> selectProgrmChangeRequstList(ComDefaultVO vo) throws Exception {
   		return cumsProgrmManageDAO.selectProgrmChangeRequstList(vo);
	}

	/**
	 * 프로그램변경요청목록 총건수를 조회한다.
	 * @param vo ComDefaultVO
	 * @return int
	 * @exception Exception
	 */
    @Override
	public int selectProgrmChangeRequstListTotCnt(ComDefaultVO vo) throws Exception {
        return cumsProgrmManageDAO.selectProgrmChangeRequstListTotCnt(vo);
	}

	/**
	 * 프로그램변경요청을 등록
	 * @param vo CumsProgrmManageDtlVO
	 * @exception Exception
	 */
	@Override
	public void insertProgrmChangeRequst(CumsProgrmManageDtlVO vo) throws Exception {
    	cumsProgrmManageDAO.insertProgrmChangeRequst(vo);
	}

	/**
	 * 프로그램변경요청을 수정
	 * @param vo CumsProgrmManageDtlVO
	 * @exception Exception
	 */
	@Override
	public void updateProgrmChangeRequst(CumsProgrmManageDtlVO vo) throws Exception {
    	cumsProgrmManageDAO.updateProgrmChangeRequst(vo);
	}

	/**
	 * 프로그램변경요청을 삭제
	 * @param vo CumsProgrmManageDtlVO
	 * @exception Exception
	 */
	@Override
	public void deleteProgrmChangeRequst(CumsProgrmManageDtlVO vo) throws Exception {
    	cumsProgrmManageDAO.deleteProgrmChangeRequst(vo);
	}

	/**
	 * 프로그램변경요청 요청번호MAX 정보를 조회
	 * @param vo CumsProgrmManageDtlVO
	 * @return CumsProgrmManageDtlVO
	 * @exception Exception
	 */
	@Override
	public CumsProgrmManageDtlVO selectProgrmChangeRequstNo(CumsProgrmManageDtlVO vo) throws Exception {
   		return cumsProgrmManageDAO.selectProgrmChangeRequstNo(vo);
	}

	/**
	 * 프로그램변경요청처리 목록을 조회
	 * @param vo ComDefaultVO
	 * @return List
	 * @exception Exception
	 */
	@Override
	public List<?> selectChangeRequstProcessList(ComDefaultVO vo) throws Exception {
   		return cumsProgrmManageDAO.selectChangeRequstProcessList(vo);
	}

	/**
	 * 프로그램변경요청처리목록 총건수를 조회한다.
	 * @param vo ComDefaultVO
	 * @return int
	 * @exception Exception
	 */
    @Override
	public int selectChangeRequstProcessListTotCnt(ComDefaultVO vo) throws Exception {
        return cumsProgrmManageDAO.selectChangeRequstListProcessTotCnt(vo);
	}

	/**
	 * 프로그램변경요청처리를 수정
	 * @param vo CumsProgrmManageDtlVO
	 * @exception Exception
	 */
	@Override
	public void updateProgrmChangeRequstProcess(CumsProgrmManageDtlVO vo) throws Exception {
    	cumsProgrmManageDAO.updateProgrmChangeRequstProcess(vo);
	}

	/**
	 * 화면에 조회된 메뉴 목록 정보를 데이터베이스에서 삭제
	 * @param checkedProgrmFileNmForDel String
	 * @exception Exception
	 */
	@Override
	public void deleteProgrmManageList(String checkedProgrmFileNmForDel) throws Exception {
		CumsProgrmManageVO vo = null;
		String [] delProgrmFileNm = checkedProgrmFileNmForDel.split(",");
		for (int i=0; i<delProgrmFileNm.length ; i++){
			vo = new CumsProgrmManageVO();
			vo.setProgrmFileNm(delProgrmFileNm[i]);
			cumsProgrmManageDAO.deleteProgrm(vo);
		}
	}

	/**
	 * 프로그램변경요청자 Email 정보를 조회
	 * @param vo CumsProgrmManageDtlVO
	 * @return CumsProgrmManageDtlVO
	 * @exception Exception
	 */
	@Override
	public CumsProgrmManageDtlVO selectRqesterEmail(CumsProgrmManageDtlVO vo) throws Exception{
       	return cumsProgrmManageDAO.selectRqesterEmail(vo);
	}

}
