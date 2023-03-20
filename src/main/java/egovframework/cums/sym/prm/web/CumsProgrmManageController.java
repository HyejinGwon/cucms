package egovframework.cums.sym.prm.web;

import java.util.List;
import java.util.Map;

import egovframework.com.cmm.ComDefaultVO;
import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.annotation.IncludedInfo;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.cop.ems.service.EgovSndngMailRegistService;
import egovframework.com.cop.ems.service.SndngMailVO;
import egovframework.cums.sym.prm.service.CumsProgrmManageService;
import egovframework.cums.sym.prm.service.CumsProgrmManageDtlVO;
import egovframework.cums.sym.prm.service.CumsProgrmManageVO;
import egovframework.com.utl.fcc.service.EgovStringUtil;
import org.egovframe.rte.fdl.property.EgovPropertyService;
import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springmodules.validation.commons.DefaultBeanValidator;

/**
 * 프로그램목록 관리및 변경을 처리하는 비즈니스 구현 클래스
 * @author 개발환경 개발팀 이용
 * @since 2009.06.01
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *   2009.03.20  이  용          최초 생성
 *   2011.08.22  서준식          selectProgrmChangRequstProcess() 메서드 처리일자 trim 처리
 *   2011.8.26	정진오			IncludedInfo annotation 추가
 * </pre>
 */

@Controller
public class CumsProgrmManageController {

	/** Validator */
	@Autowired
	private DefaultBeanValidator beanValidator;

	/** EgovPropertyService */
    @Resource(name = "propertiesService")
    protected EgovPropertyService propertiesService;

    /** EgovProgrmManageService */
	@Resource(name = "cumsProgrmManageService")
    private CumsProgrmManageService cumsProgrmManageService;

	/** EgovMessageSource */
    @Resource(name="egovMessageSource")
    EgovMessageSource egovMessageSource;

    /** EgovSndngMailRegistService */
	@Resource(name = "sndngMailRegistService")
    private EgovSndngMailRegistService sndngMailRegistService;

    /**
     * 프로그램목록을 상세화면 호출 및 상세조회한다.
     * @param tmp_progrmNm  String
     * @return 출력페이지정보 "sym/prm/EgovProgramListDetailSelectUpdt"
     * @exception Exception
     */
    @RequestMapping(value="/sym/prm/CumsProgramListDetailSelect.do")
    public String selectProgrm(
    		@RequestParam("tmp_progrmNm") String tmp_progrmNm ,
   		    @ModelAttribute("searchVO") ComDefaultVO searchVO,
    		ModelMap model)
            throws Exception {
        // 0. Spring Security 사용자권한 처리
    	Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
    	if(!isAuthenticated) {
    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
        	return "egovframework/com/uat/uia/EgovLoginUsr";
    	}
    	
    	CumsProgrmManageVO vo = new CumsProgrmManageVO();
    	vo.setProgrmFileNm(tmp_progrmNm);
    	CumsProgrmManageVO cumsProgrmManageVO = cumsProgrmManageService.selectProgrm(vo);
        model.addAttribute("cumsProgrmManageVO", cumsProgrmManageVO);
        return "cums/com/sym/prm/CumsProgramListDetailSelectUpdt";
    }

    /**
     * 프로그램목록 리스트조회한다.
     * @param searchVO ComDefaultVO
     * @return 출력페이지정보 "sym/prm/EgovProgramListManage"
     * @exception Exception
     */
    @IncludedInfo(name="프로그램관리",order = 1111 ,gid = 60)
    @RequestMapping(value="/sym/prm/CumsProgramListManageSelect.do")
    public String selectProgrmList(@ModelAttribute("searchVO") ComDefaultVO searchVO, ModelMap model)
            throws Exception {
        // 0. Spring Security 사용자권한 처리
    	Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
    	if(!isAuthenticated) {
    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
        	return "egovframework/com/uat/uia/EgovLoginUsr";
    	}
    	// 내역 조회
    	/** EgovPropertyService.sample */
    	searchVO.setPageUnit(propertiesService.getInt("pageUnit"));
    	searchVO.setPageSize(propertiesService.getInt("pageSize"));

    	/** pageing */
    	PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());
		paginationInfo.setPageSize(searchVO.getPageSize());

		searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		searchVO.setLastIndex(paginationInfo.getLastRecordIndex());
		searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

        List<?> list_progrmmanage = cumsProgrmManageService.selectProgrmList(searchVO);
        model.addAttribute("list_progrmmanage", list_progrmmanage);
        model.addAttribute("searchVO", searchVO);

        int totCnt = cumsProgrmManageService.selectProgrmListTotCnt(searchVO);
		paginationInfo.setTotalRecordCount(totCnt);
        model.addAttribute("paginationInfo", paginationInfo);

      	return "cums/com/sym/prm/CumsProgramListManage"; 

    }

    /**
     * 프로그램목록 멀티 삭제한다.
     * @param checkedProgrmFileNmForDel String
     * @return 출력페이지정보 "forward:/sym/prm/EgovProgramListManageSelect.do"
     * @exception Exception
     */
    @RequestMapping("/sym/prm/CumsProgrmManageListDelete.do")
    public String deleteProgrmManageList(
            @RequestParam("checkedProgrmFileNmForDel") String checkedProgrmFileNmForDel ,
            @ModelAttribute("cumsProgrmManageVO") CumsProgrmManageVO cumsProgrmManageVO,
            ModelMap model)
            throws Exception {
		String sLocationUrl = null;
    	String resultMsg    = "";
        // 0. Spring Security 사용자권한 처리
    	Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
    	if(!isAuthenticated) {
    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
        	return "egovframework/com/uat/uia/EgovLoginUsr";
    	}
    	String [] delProgrmFileNm = checkedProgrmFileNmForDel.split(",");
		if (delProgrmFileNm == null || (delProgrmFileNm.length ==0)){
    		resultMsg = egovMessageSource.getMessage("fail.common.delete");
    		sLocationUrl = "forward:/sym/prm/CumsProgramListManageSelect.do";
		}else{
    	   cumsProgrmManageService.deleteProgrmManageList(checkedProgrmFileNmForDel);
    	   resultMsg = egovMessageSource.getMessage("success.common.delete");
    	   sLocationUrl ="forward:/sym/prm/CumsProgramListManageSelect.do";
		}
		model.addAttribute("resultMsg", resultMsg);
        //status.setComplete();
        return sLocationUrl ;
    }

    /**
     * 프로그램목록을 등록화면으로 이동 및 등록 한다.
     * @param cumsProgrmManageVO CumsProgrmManageVO
     * @param commandMap     Map
     * @return 출력페이지정보 등록화면 호출시 "sym/prm/EgovProgramListRegist",
     *         출력페이지정보 등록처리시 "forward:/sym/prm/EgovProgramListManageSelect.do"
     * @exception Exception
     */
    @RequestMapping(value="/sym/prm/CumsProgramListRegist.do")
    public String insertProgrmList(
    		@RequestParam Map<?, ?> commandMap,
    		@ModelAttribute("cumsProgrmManageVO") CumsProgrmManageVO cumsProgrmManageVO,
			BindingResult bindingResult,
			ModelMap model)
            throws Exception {
        String resultMsg = "";
        String sLocationUrl = null;
    	// 0. Spring Security 사용자권한 처리
    	Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
    	if(!isAuthenticated) {
    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
        	return "egovframework/com/uat/uia/EgovLoginUsr";
    	}

        String sCmd = commandMap.get("cmd") == null ? "" : (String)commandMap.get("cmd");
        if(sCmd.equals("insert")){
	        beanValidator.validate(cumsProgrmManageVO, bindingResult);
			if (bindingResult.hasErrors()){
				sLocationUrl = "cums/com/sym/prm/CumsProgramListRegist";
				return sLocationUrl;
			}
			if(cumsProgrmManageVO.getProgrmDc()==null || cumsProgrmManageVO.getProgrmDc().equals("")){cumsProgrmManageVO.setProgrmDc(" ");}
	    	cumsProgrmManageService.insertProgrm(cumsProgrmManageVO);
			resultMsg = egovMessageSource.getMessage("success.common.insert");
	        sLocationUrl = "forward:/sym/prm/CumsProgramListManageSelect.do";
        }else{
            sLocationUrl = "cums/com/sym/prm/CumsProgramListRegist";
        }
    	model.addAttribute("resultMsg", resultMsg);
		return sLocationUrl;
    }

    /**
     * 프로그램목록을 수정 한다.
     * @param cumsProgrmManageVO CumsProgrmManageVO
     * @return 출력페이지정보 "forward:/sym/prm/EgovProgramListManageSelect.do"
     * @exception Exception
     */
    /*프로그램목록수정*/
    @RequestMapping(value="/sym/prm/CumsProgramListDetailSelectUpdt.do")
    public String updateProgrmList(
    		@ModelAttribute("cumsProgrmManageVO") CumsProgrmManageVO cumsProgrmManageVO,
    		BindingResult bindingResult,
    		ModelMap model)
            throws Exception {
		String resultMsg = "";
        String sLocationUrl = null;
    	// 0. Spring Security 사용자권한 처리
   	    Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
    	if(!isAuthenticated) {
    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
        	return "egovframework/com/uat/uia/EgovLoginUsr";
    	}

        beanValidator.validate(cumsProgrmManageVO, bindingResult);
		if (bindingResult.hasErrors()){
			sLocationUrl = "forward:/sym/prm/EgovProgramListDetailSelect.do";
			return sLocationUrl;
		}
		if(cumsProgrmManageVO.getProgrmDc()==null || cumsProgrmManageVO.getProgrmDc().equals("")){cumsProgrmManageVO.setProgrmDc(" ");}
		cumsProgrmManageService.updateProgrm(cumsProgrmManageVO);
		resultMsg = egovMessageSource.getMessage("success.common.update");
        sLocationUrl = "forward:/sym/prm/EgovProgramListManageSelect.do";
    	model.addAttribute("resultMsg", resultMsg);
		return sLocationUrl;
    }

    /**
     * 프로그램목록을 삭제 한다.
     * @param cumsProgrmManageVO CumsProgrmManageVO
     * @return 출력페이지정보 "forward:/sym/prm/EgovProgramListManageSelect.do"
     * @exception Exception
     */
    @RequestMapping(value="/sym/prm/CumsProgramListManageDelete.do")
    public String deleteProgrmList(
    		@ModelAttribute("cumsProgrmManageVO")
    		CumsProgrmManageVO cumsProgrmManageVO,
    		ModelMap model)
            throws Exception {
    	String resultMsg = "";
        // 0. Spring Security 사용자권한 처리
    	Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
    	if(!isAuthenticated) {
    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
        	return "egovframework/com/uat/uia/EgovLoginUsr";
    	}
        cumsProgrmManageService.deleteProgrm(cumsProgrmManageVO);
        resultMsg = egovMessageSource.getMessage("success.common.delete");
    	model.addAttribute("resultMsg", resultMsg);
        return "forward:/sym/prm/EgovProgramListManageSelect.do";
    }

    /**
     * 프로그램변경요청목록 조회한다.
     * @param searchVO ComDefaultVO
     * @return 출력페이지정보 "sym/prm/EgovProgramChangeRequst"
     * @exception Exception
     */
    @IncludedInfo(name="프로그램변경요청관리",order = 1112 ,gid = 60)
    @RequestMapping(value="/sym/prm/CumsProgramChangeRequstSelect.do")
    public String selectProgrmChangeRequstList(
    		@ModelAttribute("searchVO") ComDefaultVO searchVO,
    		ModelMap model)
            throws Exception {
        // 0. Spring Security 사용자권한 처리
    	Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
    	if(!isAuthenticated) {
    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
        	return "egovframework/com/uat/uia/EgovLoginUsr";
    	}
    	// 내역 조회
    	/** EgovPropertyService.sample */
    	searchVO.setPageUnit(propertiesService.getInt("pageUnit"));
    	searchVO.setPageSize(propertiesService.getInt("pageSize"));

    	/** pageing */
    	PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());
		paginationInfo.setPageSize(searchVO.getPageSize());

		searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		searchVO.setLastIndex(paginationInfo.getLastRecordIndex());
		searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

        List<?> list_changerequst = cumsProgrmManageService.selectProgrmChangeRequstList(searchVO);
        model.addAttribute("list_changerequst", list_changerequst);

        int totCnt = cumsProgrmManageService.selectProgrmChangeRequstListTotCnt(searchVO);
 		paginationInfo.setTotalRecordCount(totCnt);
        model.addAttribute("paginationInfo", paginationInfo);

         return "egovframework/com/sym/prm/EgovProgramChangeRequst";
    }

    /**
     * 프로그램변경요청목록을 상세조회한다.
     * @param cumsProgrmManageVO CumsProgrmManageVO
     * @return 출력페이지정보 "sym/prm/EgovProgramChangRequstDetailSelectUpdt"
     * @exception Exception
     */
    @RequestMapping(value="/sym/prm/CumsProgramChangRequstDetailSelect.do")
    public String selectProgrmChangeRequst(
    		@ModelAttribute("cumsProgrmManageDtlVO") CumsProgrmManageDtlVO cumsProgrmManageDtlVO,
    		ModelMap model)
            throws Exception {
         // 0. Spring Security 사용자권한 처리
    	 Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
    	 if(!isAuthenticated) {
    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
        	return "egovframework/com/uat/uia/EgovLoginUsr";
    	 }
         if(cumsProgrmManageDtlVO.getProgrmFileNm()== null||cumsProgrmManageDtlVO.getProgrmFileNm().equals("")){
	    	 String FileNm = cumsProgrmManageDtlVO.getTmpProgrmNm();
	    	 cumsProgrmManageDtlVO.setProgrmFileNm(FileNm);
	         int tmpNo = cumsProgrmManageDtlVO.getTmpRqesterNo();
	         cumsProgrmManageDtlVO.setRqesterNo(tmpNo);
         }
         CumsProgrmManageDtlVO resultVO = cumsProgrmManageService.selectProgrmChangeRequst(cumsProgrmManageDtlVO);
         model.addAttribute("cumsProgrmManageVO", resultVO);
         return "egovframework/com/sym/prm/EgovProgramChangRequstDetailSelectUpdt";
    }

    /**
     * 프로그램변경요청 화면을 호출및 프로그램변경요청을 등록한다.
     * @param cumsProgrmManageVO CumsProgrmManageVO
     * @param commandMap        Map
     * @return 출력페이지정보 등록화면 호출시 "sym/prm/EgovProgramChangRequstStre",
     *         출력페이지정보 등록처리시 "forward:/sym/prm/EgovProgramChangeRequstSelect.do"
     * @exception Exception
     */
    /*프로그램변경요청등록*/
    @RequestMapping(value="/sym/prm/CumsProgramChangRequstStre.do")
    public String insertProgrmChangeRequst(
    		@RequestParam Map<?, ?> commandMap,
    		@ModelAttribute("cumsProgrmManageDtlVO") CumsProgrmManageDtlVO cumsProgrmManageDtlVO,
    		BindingResult bindingResult,
    		ModelMap model)
            throws Exception {
    	String resultMsg = "";
    	// 0. Spring Security 사용자권한 처리
    	Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
    	if(!isAuthenticated) {
    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
       	    return "egovframework/com/uat/uia/EgovLoginUsr";
    	}
		//로그인 객체 선언
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
        String sLocationUrl = null;
        String sCmd = commandMap.get("cmd") == null ? "" : (String)commandMap.get("cmd");
        if(sCmd.equals("insert")){
    		//beanValidator 처리
	        beanValidator.validate(cumsProgrmManageDtlVO, bindingResult);
			if (bindingResult.hasErrors()){
				sLocationUrl = "egovframework/com/sym/prm/EgovProgramChangRequstStre";
				return sLocationUrl;
			}
			if(cumsProgrmManageDtlVO.getChangerqesterCn()==null || cumsProgrmManageDtlVO.getChangerqesterCn().equals("")){cumsProgrmManageDtlVO.setChangerqesterCn("");}
			if(cumsProgrmManageDtlVO.getRqesterProcessCn()==null || cumsProgrmManageDtlVO.getRqesterProcessCn().equals("")){cumsProgrmManageDtlVO.setRqesterProcessCn("");}
			cumsProgrmManageService.insertProgrmChangeRequst(cumsProgrmManageDtlVO);
	    	resultMsg = egovMessageSource.getMessage("success.common.insert");
	        sLocationUrl = "forward:/sym/prm/EgovProgramChangeRequstSelect.do";
        }else{
	        /* MAX요청번호 조회 */
        	CumsProgrmManageDtlVO tmp_vo = cumsProgrmManageService.selectProgrmChangeRequstNo(cumsProgrmManageDtlVO);
			int _tmp_no = tmp_vo.getRqesterNo();
			cumsProgrmManageDtlVO.setRqesterNo(_tmp_no);
			cumsProgrmManageDtlVO.setRqesterPersonId((user == null || user.getUniqId() == null) ? "" : user.getUniqId());
            sLocationUrl = "egovframework/com/sym/prm/EgovProgramChangRequstStre";
        }
    	model.addAttribute("resultMsg", resultMsg);
		return sLocationUrl;
    }

    /**
     * 프로그램변경 요청을 수정 한다.
     * @param cumsProgrmManageVO  CumsProgrmManageVO
     * @return 출력페이지정보 "forward:/sym/prm/EgovProgramChangeRequstSelect.do"
     * @exception Exception
     */
    @RequestMapping(value="/sym/prm/CumsProgramChangRequstDetailSelectUpdt.do")
    public String updateProgrmChangeRequst(
    		@ModelAttribute("cumsProgrmManageDtlVO") CumsProgrmManageDtlVO cumsProgrmManageDtlVO,
    		BindingResult bindingResult,
    		ModelMap model)
            throws Exception {
        String sLocationUrl = null;
    	String resultMsg = "";
        // 0. Spring Security 사용자권한 처리
    	Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
    	if(!isAuthenticated) {
    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
        	return "egovframework/com/uat/uia/EgovLoginUsr";
    	}
		//로그인 객체 선언
		LoginVO loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		//beanValidator 처리
        beanValidator.validate(cumsProgrmManageDtlVO, bindingResult);
		if (bindingResult.hasErrors()){
			sLocationUrl = "forward:/sym/prm/EgovProgramChangRequstDetailSelect.do";
			return sLocationUrl;
		}

		//KISA 보안약점 조치 (2018-10-29, 윤창원)
    	if(EgovStringUtil.isNullToString(cumsProgrmManageDtlVO.getRqesterPersonId()).equals(loginVO == null ? "" : EgovStringUtil.isNullToString(loginVO.getId()))){
			if(cumsProgrmManageDtlVO.getChangerqesterCn()==null || cumsProgrmManageDtlVO.getChangerqesterCn().equals("")){cumsProgrmManageDtlVO.setChangerqesterCn(" ");}
			if(cumsProgrmManageDtlVO.getRqesterProcessCn()==null || cumsProgrmManageDtlVO.getRqesterProcessCn().equals("")){cumsProgrmManageDtlVO.setRqesterProcessCn(" ");}
	        cumsProgrmManageService.updateProgrmChangeRequst(cumsProgrmManageDtlVO);
    		resultMsg = egovMessageSource.getMessage("success.common.update");
	        sLocationUrl = "forward:/sym/prm/EgovProgramChangeRequstSelect.do";
    	}else{
    		resultMsg = "수정이 실패하였습니다. 변경요청 수정은 변경요청자만 수정가능합니다.";
    		cumsProgrmManageDtlVO.setTmpProgrmNm(cumsProgrmManageDtlVO.getProgrmFileNm());
    		cumsProgrmManageDtlVO.setTmpRqesterNo(cumsProgrmManageDtlVO.getRqesterNo());
			sLocationUrl = "forward:/sym/prm/EgovProgramChangRequstDetailSelect.do";
    	}
    	model.addAttribute("resultMsg", resultMsg);
		return sLocationUrl;
    }

    /**
     * 프로그램변경 요청을 삭제 한다.
     * @param cumsProgrmManageVO  CumsProgrmManageVO
     * @return 출력페이지정보 "forward:/sym/prm/EgovProgramChangeRequstSelect.do"
     * @exception Exception
     */
    @RequestMapping(value="/sym/prm/CumsProgramChangRequstDelete.do")
    public String deleteProgrmChangeRequst(
    		@ModelAttribute("cumsProgrmManageDtlVO") CumsProgrmManageDtlVO cumsProgrmManageDtlVO,
    		ModelMap model)
            throws Exception {
        String sLocationUrl = null;
        // 0. Spring Security 사용자권한 처리
    	Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
    	if(!isAuthenticated) {
    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
        	return "egovframework/com/uat/uia/EgovLoginUsr";
    	}
		//로그인 객체 선언
		LoginVO loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		//KISA 보안약점 조치 (2018-10-29, 윤창원)
    	if(EgovStringUtil.isNullToString(cumsProgrmManageDtlVO.getRqesterPersonId()).equals(loginVO == null ? "" : EgovStringUtil.isNullToString(loginVO.getId()))){
    	//cumsProgrmManageVO.setRqesterPersonId(user.getId());
    		model.addAttribute("resultMsg", egovMessageSource.getMessage("success.common.delete"));
	        cumsProgrmManageService.deleteProgrmChangeRequst(cumsProgrmManageDtlVO);
	        sLocationUrl = "forward:/sym/prm/EgovProgramChangeRequstSelect.do";
    	}else{
    		model.addAttribute("resultMsg", egovMessageSource.getMessage("comSymPrm.progrmManageController.checkRqesterPersonId")); //삭제에 실패하였습니다. 변경요청자만 삭제가능합니다.
			sLocationUrl = "forward:/sym/prm/EgovProgramChangRequstDetailSelect.do";
    	}
        return sLocationUrl;
    }

    /**
     * 프로그램변경 요청에 대한 처리 사항을 조회한다.
     * @param searchVO ComDefaultVO
     * @return 출력페이지정보 "sym/prm/EgovProgramChangeRequstProcess"
     * @exception Exception
     */
    @IncludedInfo(name="프로그램변경요청처리",order = 1113 ,gid = 60)
    @RequestMapping(value="/sym/prm/CumsProgramChangeRequstProcessListSelect.do")
    public String selectProgrmChangeRequstProcessList(
    		@ModelAttribute("searchVO") ComDefaultVO searchVO,
    		ModelMap model)
            throws Exception {
         // 0. Spring Security 사용자권한 처리
    	 Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
    	 if(!isAuthenticated) {
    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
        	return "egovframework/com/uat/uia/EgovLoginUsr";
    	 }
     	 // 내역 조회
     	 /** EgovPropertyService.sample */
     	 searchVO.setPageUnit(propertiesService.getInt("pageUnit"));
     	 searchVO.setPageSize(propertiesService.getInt("pageSize"));

     	 /** pageing */
     	 PaginationInfo paginationInfo = new PaginationInfo();
 		 paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
 		 paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());
 		 paginationInfo.setPageSize(searchVO.getPageSize());

 		 searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
 		 searchVO.setLastIndex(paginationInfo.getLastRecordIndex());
 		 searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

         List<?> list_changerequst = cumsProgrmManageService.selectChangeRequstProcessList(searchVO);
         model.addAttribute("list_changerequst", list_changerequst);

         int totCnt = cumsProgrmManageService.selectChangeRequstProcessListTotCnt(searchVO);
  		 paginationInfo.setTotalRecordCount(totCnt);
         model.addAttribute("paginationInfo", paginationInfo);

         return "egovframework/com/sym/prm/EgovProgramChangeRequstProcess";
    }

    /**
     * 프로그램변경 요청에 대한 처리 사항을 상세조회한다.
     * @param cumsProgrmManageVO CumsProgrmManageVO
     * @return 출력페이지정보 "sym/prm/EgovProgramChangRequstProcessDetailSelectUpdt"
     * @exception Exception
     */
    @RequestMapping(value="/sym/prm/CumsProgramChangRequstProcessDetailSelect.do")
    public String selectProgrmChangRequstProcess(
    		@ModelAttribute("cumsProgrmManageDtlVO") CumsProgrmManageDtlVO cumsProgrmManageDtlVO,
    		ModelMap model)
            throws Exception {
         // 0. Spring Security 사용자권한 처리
    	 Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
    	 if(!isAuthenticated) {
    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
        	return "egovframework/com/uat/uia/EgovLoginUsr";
    	 }
         if(cumsProgrmManageDtlVO.getProgrmFileNm()==null){
	    	 String _FileNm = cumsProgrmManageDtlVO.getTmpProgrmNm();
	    	 cumsProgrmManageDtlVO.setProgrmFileNm(_FileNm);
	         int _Tmp_no = cumsProgrmManageDtlVO.getTmpRqesterNo();
	         cumsProgrmManageDtlVO.setRqesterNo(_Tmp_no);
         }
         CumsProgrmManageDtlVO resultVO = cumsProgrmManageService.selectProgrmChangeRequst(cumsProgrmManageDtlVO);
         if(resultVO.getProcessDe() != null){
        	 resultVO.setProcessDe(resultVO.getProcessDe().trim());//2011.08.22
         }

         if(resultVO.getOpetrId()== null){
	     	 LoginVO user =
				(LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
	         resultVO.setOpetrId(user == null ? "" : EgovStringUtil.isNullToString(user.getId()));
         }
         model.addAttribute("cumsProgrmManageVO", resultVO);
         return "egovframework/com/sym/prm/EgovProgramChangRequstProcessDetailSelectUpdt";
    }

    /**
     * 프로그램변경요청처리 내용을 수정 한다.
     * @param cumsProgrmManageVO CumsProgrmManageVO
     * @return 출력페이지정보 "forward:/sym/prm/EgovProgramChangeRequstProcessListSelect.do"
     * @exception Exception
     */
    @SuppressWarnings("unused")
	@RequestMapping(value="/sym/prm/CumsProgramChangRequstProcessDetailSelectUpdt.do")
    public String updateProgrmChangRequstProcess(
    		@ModelAttribute("cumsProgrmManageDtlVO") CumsProgrmManageDtlVO cumsProgrmManageDtlVO,
    		BindingResult bindingResult,
    		ModelMap model)
            throws Exception {
        String sLocationUrl = null;
    	boolean result = true;
    	// 0. Spring Security 사용자권한 처리
    	Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
    	if(!isAuthenticated) {
    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
        	return "egovframework/com/uat/uia/EgovLoginUsr";
    	}

        beanValidator.validate(cumsProgrmManageDtlVO, bindingResult);
		if (bindingResult.hasErrors()){
			sLocationUrl = "forward:/sym/prm/EgovProgramChangRequstProcessDetailSelect.do";
			return sLocationUrl;
		}

    	LoginVO user =
			(LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
    	
    	//KISA 보안약점 조치 (2018-10-29, 윤창원)
    	if (cumsProgrmManageDtlVO.getOpetrId() != null) {
	    	if(cumsProgrmManageDtlVO.getOpetrId().equals(user == null ? "" : EgovStringUtil.isNullToString(user.getId()))){
				if(cumsProgrmManageDtlVO.getChangerqesterCn()==null || cumsProgrmManageDtlVO.getChangerqesterCn().equals("")){cumsProgrmManageDtlVO.setChangerqesterCn(" ");}
				if(cumsProgrmManageDtlVO.getRqesterProcessCn()==null || cumsProgrmManageDtlVO.getRqesterProcessCn().equals("")){cumsProgrmManageDtlVO.setRqesterProcessCn(" ");}
		        cumsProgrmManageService.updateProgrmChangeRequstProcess(cumsProgrmManageDtlVO);
		        model.addAttribute("resultMsg", egovMessageSource.getMessage("success.common.update"));
	
		        CumsProgrmManageDtlVO vo = new CumsProgrmManageDtlVO();
		        vo = cumsProgrmManageService.selectRqesterEmail(cumsProgrmManageDtlVO);
		        String sTemp = null;
		        //KISA 보안약점 조치 (2018-10-29, 윤창원)
		        if("A".equals(cumsProgrmManageDtlVO.getProcessSttus())){
		        	sTemp = egovMessageSource.getMessage("comSymPrm.progrmManageController.processSttusA"); //신청중
		        }else if("P".equals(cumsProgrmManageDtlVO.getProcessSttus())){
		        	sTemp = egovMessageSource.getMessage("comSymPrm.progrmManageController.processSttusP"); //진행중
		        }else if("R".equals(cumsProgrmManageDtlVO.getProcessSttus())){
		        	sTemp = egovMessageSource.getMessage("comSymPrm.progrmManageController.processSttusR"); //반려
		        }else if("C".equals(cumsProgrmManageDtlVO.getProcessSttus())){
		        	sTemp = egovMessageSource.getMessage("comSymPrm.progrmManageController.processSttusC"); //처리완료
		        }
		    	// 프로그램 변경요청 사항을 이메일로  발송한다.(메일연동솔루션 활용)
		    	SndngMailVO sndngMailVO = new SndngMailVO();
		    	sndngMailVO.setDsptchPerson(user == null ? "" : EgovStringUtil.isNullToString(user.getId()));
		    	sndngMailVO.setRecptnPerson(vo.getTmpEmail());
		    	sndngMailVO.setSj(egovMessageSource.getMessage("comSymPrm.progrmManageController.email.Sj")); //프로그램변경요청  처리.
		    	sndngMailVO.setEmailCn(egovMessageSource.getMessage("comSymPrm.progrmManageController.email.emailCn")+" : "+sTemp); //프로그램 변경요청 사항이 처리 되었습니다
		    	sndngMailVO.setAtchFileId(null);
		    	result = sndngMailRegistService.insertSndngMail(sndngMailVO);
		        sLocationUrl = "forward:/sym/prm/EgovProgramChangeRequstProcessListSelect.do";
	    	}else{
	    		model.addAttribute("resultMsg", egovMessageSource.getMessage("comSymPrm.progrmManageController.updateProgrmChangRequstProcess.fail")); //수정이 실패하였습니다. 변경요청처리 수정은 변경처리해당 담당자만 처리가능합니다.
	            cumsProgrmManageDtlVO.setTmpProgrmNm(cumsProgrmManageDtlVO.getProgrmFileNm());
	            cumsProgrmManageDtlVO.setTmpRqesterNo(cumsProgrmManageDtlVO.getRqesterNo());
				sLocationUrl = "forward:/sym/prm/EgovProgramChangRequstProcessDetailSelect.do";
	    	}
    	}
		return sLocationUrl;
    }

    /**
     * 프로그램변경요청처리를 삭제 한다.
     * @param cumsProgrmManageVO  CumsProgrmManageVO
     * @return 출력페이지정보 "forward:/sym/prm/EgovProgramChangeRequstProcessListSelect.do"
     * @exception Exception
     */
    /*프로그램변경요청처리 삭제*/
    @RequestMapping(value="/sym/prm/CumsProgramChangRequstProcessDelete.do")
    public String deleteProgrmChangRequstProcess(
    		@ModelAttribute("cumsProgrmManageDtlVO") CumsProgrmManageDtlVO cumsProgrmManageDtlVO,
    		ModelMap model)
            throws Exception {
         // 0. Spring Security 사용자권한 처리
    	 Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
    	 if(!isAuthenticated) {
    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
        	return "egovframework/com/uat/uia/EgovLoginUsr";
    	 }
         cumsProgrmManageService.deleteProgrmChangeRequst(cumsProgrmManageDtlVO);

         return "forward:/sym/prm/EgovProgramChangeRequstProcessListSelect.do";
    }

    /**
     * 프로그램변경이력리스트를 조회한다.
     * @param searchVO ComDefaultVO
     * @return 출력페이지정보 "sym/prm/EgovProgramChgHst"
     * @exception Exception
     */
    @IncludedInfo(name="프로그램변경이력",order = 1114 ,gid = 60)
    @RequestMapping(value="/sym/prm/CumsProgramChgHstListSelect.do")
    public String selectProgrmChgHstList(
    		@ModelAttribute("searchVO") ComDefaultVO searchVO,
    		ModelMap model)
            throws Exception {
         // 0. Spring Security 사용자권한 처리
    	 Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
    	 if(!isAuthenticated) {
    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
        	return "egovframework/com/uat/uia/EgovLoginUsr";
    	 }
     	 // 내역 조회
     	 /** EgovPropertyService.sample */
     	 searchVO.setPageUnit(propertiesService.getInt("pageUnit"));
     	 searchVO.setPageSize(propertiesService.getInt("pageSize"));

     	 /** pageing */
     	 PaginationInfo paginationInfo = new PaginationInfo();
 		 paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
 		 paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());
 		 paginationInfo.setPageSize(searchVO.getPageSize());

 		 searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
 		 searchVO.setLastIndex(paginationInfo.getLastRecordIndex());
 		 searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

         List<?> list_changerequst = cumsProgrmManageService.selectProgrmChangeRequstList(searchVO);
         model.addAttribute("list_changerequst", list_changerequst);

         int totCnt = cumsProgrmManageService.selectProgrmChangeRequstListTotCnt(searchVO);
  		 paginationInfo.setTotalRecordCount(totCnt);
         model.addAttribute("paginationInfo", paginationInfo);

         return "egovframework/com/sym/prm/EgovProgramChgHst";
    }

    /*프로그램변경이력상세조회*/
    /**
     * 프로그램변경이력을 상세조회한다.
     * @param cumsProgrmManageVO CumsProgrmManageVO
     * @return 출력페이지정보 "sym/prm/EgovProgramChgHstDetail"
     * @exception Exception
     */
    @RequestMapping(value="/sym/prm/CumsProgramChgHstListDetailSelect.do")
    public String selectProgramChgHstListDetail(
    		@ModelAttribute("cumsProgrmManageDtlVO") CumsProgrmManageDtlVO cumsProgrmManageDtlVO,
    		ModelMap model)
            throws Exception {
         // 0. Spring Security 사용자권한 처리
    	 Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
    	 if(!isAuthenticated) {
    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
        	return "egovframework/com/uat/uia/EgovLoginUsr";
    	 }
         String _FileNm = cumsProgrmManageDtlVO.getTmpProgrmNm();
         cumsProgrmManageDtlVO.setProgrmFileNm(_FileNm);
         int _tmp_no = cumsProgrmManageDtlVO.getTmpRqesterNo();
         cumsProgrmManageDtlVO.setRqesterNo(_tmp_no);

         CumsProgrmManageDtlVO resultVO = cumsProgrmManageService.selectProgrmChangeRequst(cumsProgrmManageDtlVO);
         model.addAttribute("resultVO", resultVO);
         return "egovframework/com/sym/prm/EgovProgramChgHstDetail";
    }

    /**
     * 프로그램파일명을 조회한다.
     * @param searchVO ComDefaultVO
     * @return 출력페이지정보 "sym/prm/EgovFileNmSearch"
     * @exception Exception
     */
    @RequestMapping(value="/sym/prm/CumsProgramListSearch.do")
    public String selectProgrmListSearch(
    		@ModelAttribute("searchVO") ComDefaultVO searchVO,
    		ModelMap model)
            throws Exception {
        // 0. Spring Security 사용자권한 처리
    	Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
    	if(!isAuthenticated) {
    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
        	return "egovframework/com/uat/uia/EgovLoginUsr";
    	}
    	// 내역 조회
    	searchVO.setPageUnit(propertiesService.getInt("pageUnit"));
    	searchVO.setPageSize(propertiesService.getInt("pageSize"));

    	/** pageing */
    	PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());
		paginationInfo.setPageSize(searchVO.getPageSize());

		searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		searchVO.setLastIndex(paginationInfo.getLastRecordIndex());
		searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

        List<?> list_progrmmanage = cumsProgrmManageService.selectProgrmList(searchVO);
        model.addAttribute("list_progrmmanage", list_progrmmanage);

        int totCnt = cumsProgrmManageService.selectProgrmListTotCnt(searchVO);
		paginationInfo.setTotalRecordCount(totCnt);
        model.addAttribute("paginationInfo", paginationInfo);

      	return "egovframework/com/sym/prm/EgovFileNmSearch";

    }

    /**
     * 프로그램파일명을 조회한다. (New)
     * @param searchVO ComDefaultVO
     * @return 출력페이지정보 "sym/prm/EgovFileNmSearch"
     * @exception Exception
     */
    @RequestMapping(value="/sym/prm/CumsProgramListSearchNew.do")
    public String selectProgrmListSearchNew(
    		@ModelAttribute("searchVO") ComDefaultVO searchVO,
    		ModelMap model)
            throws Exception {
        // 0. Spring Security 사용자권한 처리
    	Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
    	if(!isAuthenticated) {
    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
        	return "egovframework/com/uat/uia/EgovLoginUsr";
    	}
    	// 내역 조회
    	searchVO.setPageUnit(propertiesService.getInt("pageUnit"));
    	searchVO.setPageSize(propertiesService.getInt("pageSize"));

    	/** pageing */
    	PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());
		paginationInfo.setPageSize(searchVO.getPageSize());

		searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		searchVO.setLastIndex(paginationInfo.getLastRecordIndex());
		searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

        List<?> list_progrmmanage = cumsProgrmManageService.selectProgrmList(searchVO);
        model.addAttribute("list_progrmmanage", list_progrmmanage);

        int totCnt = cumsProgrmManageService.selectProgrmListTotCnt(searchVO);
		paginationInfo.setTotalRecordCount(totCnt);
        model.addAttribute("paginationInfo", paginationInfo);

      	return "egovframework/com/sym/prm/EgovFileNmSearchNew";

    }
}