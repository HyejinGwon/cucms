package egovframework.cums.cop.cmy.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springmodules.validation.commons.DefaultBeanValidator;

import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.EgovWebUtil;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.cop.tpl.service.EgovTemplateManageService;
import egovframework.com.cop.tpl.service.TemplateInfVO;
import egovframework.com.utl.fcc.service.EgovStringUtil;
import egovframework.cums.cop.bbs.service.CumsArticleService;
import egovframework.cums.cop.bbs.service.CumsBoardMasterVO;
import egovframework.cums.cop.bbs.service.CumsBoardVO;
import egovframework.cums.cop.cmy.service.CumsCommuBBSMasterService;
import egovframework.cums.cop.cmy.service.CumsCommuManageService;
import egovframework.cums.cop.cmy.service.CumsCommuMasterService;
import egovframework.cums.cop.cmy.service.CumsCommunityUser;
import egovframework.cums.cop.cmy.service.CumsCommunityUserVO;
import egovframework.cums.cop.cmy.service.CumsCommunityVO;

import org.egovframe.rte.fdl.property.EgovPropertyService;
import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

/**
 * 커뮤니티 사용자관리, 커뮤니티 게시판을 관리하기 위한 컨트롤러 클래스
 * @author 공통서비스개발팀 김연호
 * @since 2016.08.01
 * @version 3.6
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일               수정자            수정내용
 *   ----------   --------   ---------------------------
 *   2016.06.13   김연호            최초 생성 - 표준프레임워크 v3.6 개선
 *   2019.05.17   신용호            KISA 취약점 조치 및 보완
 *   
 * </pre>
 */

@Controller
public class CumsCommuManageController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CumsCommuManageController.class);
	
	@Resource(name = "CumsCommuManageService")
    private CumsCommuManageService cumsCommuManageService;
	
	@Resource(name = "CumsCommuBBSMasterService")
	private CumsCommuBBSMasterService cumsCommuBBSMasterService;
	
	@Resource(name = "CumsCommuMasterService")
	private CumsCommuMasterService cumsCommuMasterService;
	
	@Resource(name = "CumsArticleService")
	private CumsArticleService cumsArticleService;

	@Resource(name = "EgovTemplateManageService")
	private EgovTemplateManageService egovTemplateManageService;
	
    @Resource(name = "propertiesService")
    protected EgovPropertyService propertyService;

    @Autowired
    private DefaultBeanValidator beanValidator;
	
    /** EgovMessageSource */
	@Resource(name = "egovMessageSource")
	EgovMessageSource egovMessageSource;
    
	/**
     * 커뮤니티 메인페이지를 조회한다.
     * 
     * @param cmmntyVO
     * @param sessionVO
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/cop/cmy/CumscmmntyMain.do")
    public String selectCmmntyMain(@ModelAttribute("searchVO") CumsCommunityVO cmmntyVO
    		,ModelMap model
    		,HttpServletRequest request) throws Exception {

		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
   	 	// KISA 보안취약점 조치 (2018-12-10, 신용호)
        if(!isAuthenticated) {
            return "egovframework/com/uat/uia/EgovLoginUsr";
        }

        cmmntyVO.setEmplyrId(user == null ? "" : EgovStringUtil.isNullToString(user.getUniqId()));
	
//		String tmplatCours = cmmntyService.selectCmmntyTemplat(cmmntyVO);
		String tmplatCours = "";
		if ("".equals(tmplatCours) || tmplatCours == null) {
		    tmplatCours = "egovframework/com/cop/tpl/EgovCmmntyBaseTmpl";
		}
		Map<String, Object> map = cumsCommuManageService.selectCommuInf(cmmntyVO);
	
		//model.addAttribute("cmmntyVO", cmmntyVO);
		model.addAttribute("cmmntyVO", (CumsCommunityVO)map.get("cmmntyVO"));
		model.addAttribute("cmmntyUser", (CumsCommunityUser)map.get("cmmntyUser"));
			
		//--------------------------------
		// 게시판 목록 정보 처리
		//--------------------------------
		CumsBoardMasterVO bbsVo = new CumsBoardMasterVO();
		
		bbsVo.setCmmntyId(cmmntyVO.getCmmntyId());
		
		List<CumsBoardMasterVO> bbsResult = cumsCommuBBSMasterService.selectCommuBBSMasterListMain(bbsVo);
	
		model.addAttribute("bbsList", bbsResult);
		////------------------------------
		
		if (isAuthenticated) {
		    model.addAttribute("isAuthenticated", "Y");
		} else {
		    model.addAttribute("isAuthenticated", "N");
		}
		model.addAttribute("returnMsg", request.getParameter("returnMsg"));
		
		return "egovframework/com/cop/cmy/EgovCommuMain";
    }
    
    /**
     * 커뮤니티 메인페이지의 기본 내용(게시판 4개 표시) 조회한다.
     * 
     * @param cmmntyVO
     * @param sessionVO
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/cop/cmy/CumscmmntyMainContents.do")
    public String selectCmmntyMainContents(@ModelAttribute("searchVO") CumsCommunityVO cmmntyVO, ModelMap model) throws Exception {
		
    	LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		// KISA 보안취약점 조치 (2018-12-10, 신용호)
        if(!isAuthenticated) {
            return "egovframework/com/uat/uia/EgovLoginUsr";
        }
	
		cmmntyVO.setEmplyrId(user == null ? "" : EgovStringUtil.isNullToString(user.getUniqId()));
	
		//--------------------------------
		// 게시판 목록 정보 처리
		//--------------------------------
		CumsBoardMasterVO bbsVo = new CumsBoardMasterVO();
		
		bbsVo.setCmmntyId(cmmntyVO.getCmmntyId());

		List<CumsBoardMasterVO> bbsResult = cumsCommuBBSMasterService.selectCommuBBSMasterListMain(bbsVo);
	
		// 방명록 제외 처리
		for (int i = 0; i < bbsResult.size(); i++) {
		    if ("BBST04".equals(bbsResult.get(i).getBbsTyCode())) {
			bbsResult.remove(i);
		    }
		}
	
		model.addAttribute("bbsList", bbsResult);
	
		//--------------------------------
		// 게시물 목록 정보 처리
		//--------------------------------
		CumsBoardVO boardVo = null;
		CumsBoardMasterVO masterVo = null;
		
		ArrayList<Object> target = new ArrayList<Object>();	// Object => List<CumsBoardVO>
		for (int i = 0; i < bbsResult.size() && i < 4; i++) {
		    masterVo = bbsResult.get(i);
		    boardVo = new CumsBoardVO();
	
		    boardVo.setBbsId(masterVo.getBbsId());
		    boardVo.setBbsNm(masterVo.getBbsNm());
	
		    boardVo.setPageUnit(4);
		    boardVo.setPageSize(4);
	
		    boardVo.setFirstIndex(0);
		    boardVo.setRecordCountPerPage(4);
	
		    Map<String, Object> map = cumsArticleService.selectArticleList(boardVo);
	
		    target.add(map.get("resultList"));
		}
	
		model.addAttribute("articleList", target);
	
		return "egovframework/com/cop/cmy/EgovCmmntyBaseTmplContents";
    }
    
    /**
     * 커뮤니티 가입신청을 등록한다.
     * 
     * @param cmmntyUser
     * @param sessionVO
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/cop/cmy/insertCumsCommuUserBySelf.do")
    public String insertCmmntyUserBySelf(@ModelAttribute("cmmntyUser") CumsCommunityUser cmmntyUser, ModelMap model) throws Exception {
    	
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		//KISA 보안취약점 조치 (2018-12-10, 신용호)
        if(!isAuthenticated) {
            return "egovframework/com/uat/uia/EgovLoginUsr";
        }
	
		String retVal = "";
	
		if ("".equals(cmmntyUser.getMngrAt())) {
		    cmmntyUser.setMngrAt("N");
		}
		cmmntyUser.setUseAt("Y");
		cmmntyUser.setFrstRegisterId(user == null ? "" : EgovStringUtil.isNullToString(user.getUniqId()));
		cmmntyUser.setEmplyrId(user == null ? "" : EgovStringUtil.isNullToString(user.getUniqId()));
		cmmntyUser.setMberSttus("A");
		
		if (isAuthenticated) {
	
		    //---------------------------------------------
		    // 승인요청 처리
		    //---------------------------------------------
		    retVal = cumsCommuManageService.checkCommuUserDetail(cmmntyUser);
	
		    //요청건이 없을 경우 
		    if (!retVal.equals("EXIST")) {
				
				cumsCommuManageService.insertCommuUserRqst(cmmntyUser);
				retVal = egovMessageSource.getMessage("comCopCmy.commuMain.joinMember.info.success"); //가입신청이 정상처리되었습니다.
		    } else {
		    	
		    	retVal = egovMessageSource.getMessage("comCopCmy.commuMain.joinMember.info.fail"); //이미 가입처리가 되어 있습니다.
		    }
		    ////-------------------------------------------
		}
		model.addAttribute("returnMsg", retVal);
		model.addAttribute("cmmntyId", cmmntyUser.getCmmntyId());
		
		return "redirect:/cop/cmy/cmmntyMain.do";
    }
    
    /**
     * 커뮤니티를 탈퇴한다.
     * 
     * @param cmmntyUser
     * @param sessionVO
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/cop/cmy/deleteCumsCommuUserBySelf.do")
    public String deleteCmmntyUserBySelf(@ModelAttribute("cmmntyUser") CumsCommunityUserVO cmmntyUserVO, ModelMap model) throws Exception {
    	
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		//KISA 보안취약점 조치 (2018-12-10, 신용호)
        if(!isAuthenticated) {
            return "egovframework/com/uat/uia/EgovLoginUsr";
        }
		
		//로그인한 사용자가 관리자인지 확인한다.
		CumsCommunityUserVO userVO = new CumsCommunityUserVO();
		userVO.setCmmntyId(cmmntyUserVO.getCmmntyId());
		userVO.setEmplyrId(user == null ? "" : EgovStringUtil.isNullToString(user.getUniqId()));
		Boolean isCommuAdmin = cumsCommuManageService.selectIsCommuAdmin(userVO);
	
		//관리자는 탈퇴할 수 없음.
		String resultMsg = "";
		if(isAuthenticated && !isCommuAdmin) {
			cmmntyUserVO.setEmplyrId(user == null ? "" : EgovStringUtil.isNullToString(user.getUniqId()));
			cumsCommuManageService.deleteCommuUser(cmmntyUserVO);
			resultMsg = egovMessageSource.getMessage("comCopCmy.commuMain.deleteMember.info.success"); //탈퇴신청이 정상처리되었습니다.
		} else {
			resultMsg = egovMessageSource.getMessage("comCopCmy.commuMain.deleteMember.info.admin"); //관리자는 탈퇴할수 없습니다.
		}

		model.addAttribute("cmmntyId", cmmntyUserVO.getCmmntyId());
		model.addAttribute("returnMsg", resultMsg);
		
		return "redirect:/cop/cmy/cmmntyMain.do";
    }
    
    /**
     * 커뮤니티 사용자 목록을 조회한다.
     * 
     * @param cmmntyUserVO
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/cop/cmy/selectCumsCommuUserList.do")
    public String selectCommuUserList(@ModelAttribute("searchVO") CumsCommunityUserVO cmmntyUserVO, ModelMap model) throws Exception {
		cmmntyUserVO.setPageUnit(propertyService.getInt("pageUnit"));
		cmmntyUserVO.setPageSize(propertyService.getInt("pageSize"));
	
		PaginationInfo paginationInfo = new PaginationInfo();
		
		paginationInfo.setCurrentPageNo(cmmntyUserVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(cmmntyUserVO.getPageUnit());
		paginationInfo.setPageSize(cmmntyUserVO.getPageSize());
	
		cmmntyUserVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		cmmntyUserVO.setLastIndex(paginationInfo.getLastRecordIndex());
		cmmntyUserVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
	
		Map<String, Object> map = cumsCommuManageService.selectCommuUserList(cmmntyUserVO);
		int totCnt = Integer.parseInt((String)map.get("resultCnt"));
		
		paginationInfo.setTotalRecordCount(totCnt);
	
		model.addAttribute("resultList", map.get("resultList"));
		model.addAttribute("resultCnt", map.get("resultCnt"));
		model.addAttribute("paginationInfo", paginationInfo);
	
		return "egovframework/com/cop/cmy/EgovCommuUserList";
    }
    
    /**
     * 커뮤니티 사용자를 등록한다.
     * 
     * @param cmmntyUserVO
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/cop/cmy/insertCumsCommuUser.do")
    public String insertCommuUser(@ModelAttribute("searchVO") CumsCommunityUserVO cmmntyUserVO, ModelMap model) throws Exception {

    	LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
   	 	// KISA 보안취약점 조치 (2018-12-10, 신용호)
        if(!isAuthenticated) {
            return "egovframework/com/uat/uia/EgovLoginUsr";
        }
    	
		//로그인한 사용자가 관리자인지 확인한다.
		CumsCommunityUserVO userVO = new CumsCommunityUserVO();
		userVO.setCmmntyId(cmmntyUserVO.getCmmntyId());
		userVO.setEmplyrId(user == null ? "" : EgovStringUtil.isNullToString(user.getUniqId()));
		Boolean isCommuAdmin = cumsCommuManageService.selectIsCommuAdmin(userVO);
		
		
		if(isAuthenticated && isCommuAdmin) {
			cmmntyUserVO.setLastUpdusrId(user == null ? "" : EgovStringUtil.isNullToString(user.getUniqId()));
			cumsCommuManageService.insertCommuUser(cmmntyUserVO);
		}
		
		
		
		return "forward:/cop/cmy/selectCommuUserList.do";
    }
    
    /**
     * 커뮤니티 사용자를 탈퇴시킨다. (가입거절 포함)
     * 
     * @param cmmntyUserVO
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/cop/cmy/deleteCumsCommuUser.do")
    public String deleteCommuUser(@ModelAttribute("searchVO") CumsCommunityUserVO cmmntyUserVO, ModelMap model) throws Exception {

    	LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
   	 	// KISA 보안취약점 조치 (2018-12-10, 신용호)
        if(!isAuthenticated) {
            return "egovframework/com/uat/uia/EgovLoginUsr";
        }
    	
		//로그인한 사용자가 관리자인지 확인한다.
		CumsCommunityUserVO userVO = new CumsCommunityUserVO();
		userVO.setCmmntyId(cmmntyUserVO.getCmmntyId());
		userVO.setEmplyrId(user == null ? "" : EgovStringUtil.isNullToString(user.getUniqId()));
		Boolean isCommuAdmin = cumsCommuManageService.selectIsCommuAdmin(userVO);
		
		
		if(isAuthenticated && isCommuAdmin) {
			cumsCommuManageService.deleteCommuUser(cmmntyUserVO);
		}
		
		
		
		return "forward:/cop/cmy/selectCommuUserList.do";
    }
    
    /**
     * 커뮤니티 관리자를 등록한다.
     * 
     * @param cmmntyUserVO
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/cop/cmy/insertCumsCommuUserAdmin.do")
    public String insertCommuUserAdmin(@ModelAttribute("searchVO") CumsCommunityUserVO cmmntyUserVO, ModelMap model) throws Exception {

    	LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
   	 	// KISA 보안취약점 조치 (2018-12-10, 신용호)
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
        if(!isAuthenticated) {
            return "egovframework/com/uat/uia/EgovLoginUsr";
        }
    	
		//로그인한 사용자가 관리자인지 확인한다.
		CumsCommunityUserVO userVO = new CumsCommunityUserVO();
		userVO.setCmmntyId(cmmntyUserVO.getCmmntyId());
		userVO.setEmplyrId(user == null ? "" : EgovStringUtil.isNullToString(user.getUniqId()));
		Boolean isCommuAdmin = cumsCommuManageService.selectIsCommuAdmin(userVO);
		
		
		if(isAuthenticated && isCommuAdmin) {
			cmmntyUserVO.setLastUpdusrId(user == null ? "" : EgovStringUtil.isNullToString(user.getUniqId()));
			cumsCommuManageService.insertCommuUserAdmin(cmmntyUserVO);
		}
		
		
		
		return "forward:/cop/cmy/selectCommuUserList.do";
    }
    
    /**
     * 커뮤니티 관리자를 해제한다.
     * 
     * @param cmmntyUserVO
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/cop/cmy/deleteCumsCommuUserAdmin.do")
    public String deleteCommuUserAdmin(@ModelAttribute("searchVO") CumsCommunityUserVO cmmntyUserVO, ModelMap model) throws Exception {

    	LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
   	 	// KISA 보안취약점 조치 (2018-12-10, 신용호)
        if(!isAuthenticated) {
            return "egovframework/com/uat/uia/EgovLoginUsr";
        }
    	
		//로그인한 사용자가 관리자인지 확인한다.
		CumsCommunityUserVO userVO = new CumsCommunityUserVO();
		userVO.setCmmntyId(cmmntyUserVO.getCmmntyId());
		userVO.setEmplyrId(user == null ? "" : EgovStringUtil.isNullToString(user.getUniqId()));
		Boolean isCommuAdmin = cumsCommuManageService.selectIsCommuAdmin(userVO);
		
		
		//커뮤니티 개설자는 관리자해제를 할 수 없음.
		CumsCommunityVO cmmntyVO = new CumsCommunityVO();
		cmmntyVO.setCmmntyId(cmmntyUserVO.getCmmntyId());
		cmmntyVO = cumsCommuMasterService.selectCommuMaster(cmmntyVO);
		//커뮤니티 최초등록자를 확인한다. 일치할 경우 관리자 해제 불가.
		if(cmmntyVO.getFrstRegisterId().equals(cmmntyUserVO.getEmplyrId())) {
			return "forward:/cop/cmy/selectCommuUserList.do";
		}
		
		
		
		if(isAuthenticated && isCommuAdmin) {
			cmmntyUserVO.setLastUpdusrId(user == null ? "" : EgovStringUtil.isNullToString(user.getUniqId()));
			cumsCommuManageService.deleteCommuUserAdmin(cmmntyUserVO);
		}
		
		
		
		return "forward:/cop/cmy/selectCommuUserList.do";
    }

    /**
     * 미리보기 커뮤니티 메인페이지를 조회한다.
     * 
     * @param cmmntyVO
     * @param sessionVO
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/cop/cmy/previewCumsCmmntyMainPage.do")
    public String previewCmmntyMainPage(@ModelAttribute("searchVO") CumsCommunityVO cmmntyVO, ModelMap model) throws Exception {

		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
	
		cmmntyVO.setEmplyrId(user == null ? "" : EgovStringUtil.isNullToString(user.getUniqId()));
	
		String tmplatCours = cmmntyVO.getSearchWrd();
		
		CumsCommunityVO vo = new CumsCommunityVO();
		
		vo.setCmmntyNm("미리보기 커뮤니티");
		vo.setCmmntyIntrcn("미리보기를 위한 커뮤니티입니다.");
		vo.setUseAt("Y");
		vo.setFrstRegisterId(user == null ? "" : EgovStringUtil.isNullToString(user.getUniqId()));	// 본인
		
		CumsCommunityUser cmmntyUser = new CumsCommunityUser();
		
		cmmntyUser.setEmplyrId(user == null ? "" : EgovStringUtil.isNullToString(user.getUniqId()));
		cmmntyUser.setEmplyrNm("관리자");
	
		model.addAttribute("cmmntyVO", vo);
		model.addAttribute("cmmntyUser", cmmntyUser);
			
		//--------------------------------
		// 게시판 목록 정보 처리
		//--------------------------------
		List<CumsBoardMasterVO> bbsResult = new ArrayList<CumsBoardMasterVO>();
		
		CumsBoardMasterVO target = null;
		
		target = new CumsBoardMasterVO();
		target.setBbsNm("방명록");
		bbsResult.add(target);
		
		target = new CumsBoardMasterVO();
		target.setBbsNm("공지게시판");
		bbsResult.add(target);
		
		target = new CumsBoardMasterVO();
		target.setBbsNm("갤러리");
		bbsResult.add(target);
		
		target = new CumsBoardMasterVO();
		target.setBbsNm("자유게시판");
		bbsResult.add(target);
		
		target = new CumsBoardMasterVO();
		target.setBbsNm("자료실");
		bbsResult.add(target);
		
		model.addAttribute("bbsList", bbsResult);
		////------------------------------
	
		if (isAuthenticated) {
		    model.addAttribute("isAuthenticated", "Y");
		} else {
		    model.addAttribute("isAuthenticated", "N");
		}
		
		model.addAttribute("preview", "true");
		
		// 안전한 경로 문자열로 조치
		tmplatCours = EgovWebUtil.filePathBlackList(tmplatCours);

		// 화이트 리스트 체크
		List<TemplateInfVO> templateWhiteList = egovTemplateManageService.selectTemplateWhiteList();
		LOGGER.debug("Template > WhiteList Count = {}",templateWhiteList.size());
		if ( tmplatCours == null ) tmplatCours = "";
		for(TemplateInfVO templateInfVO : templateWhiteList){
			LOGGER.debug("Template > whiteList TmplatCours = "+templateInfVO.getTmplatCours());
            if ( tmplatCours.equals(templateInfVO.getTmplatCours()) ) {
            	return tmplatCours;
            }
        }
		
		LOGGER.debug("Template > WhiteList mismatch! Please check Admin page!");
		return "egovframework/com/cmm/egovError";
    }
    
    /**
     * 커뮤니티 메인페이지의 기본 내용(게시판 4개 표시) 조회한다.
     * 
     * @param cmmntyVO
     * @param sessionVO
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/cop/cmy/previewCumsCmmntyMainContents.do")
    public String previewCmmntyMainContents(@ModelAttribute("searchVO") CumsCommunityVO cmmntyVO, ModelMap model) throws Exception {
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		@SuppressWarnings("unused")
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
	
		cmmntyVO.setEmplyrId(user == null ? "" : EgovStringUtil.isNullToString(user.getUniqId()));
	
		//--------------------------------
		// 게시판 목록 정보 처리
		//--------------------------------
		List<CumsBoardMasterVO> bbsResult = new ArrayList<CumsBoardMasterVO>();
	
		CumsBoardMasterVO master = null;
		
		master = new CumsBoardMasterVO();
		master.setBbsNm("공지게시판");
		bbsResult.add(master);
		
		master = new CumsBoardMasterVO();
		master.setBbsNm("갤러리");
		bbsResult.add(master);
		
		master = new CumsBoardMasterVO();
		master.setBbsNm("자유게시판");
		bbsResult.add(master);
		
		master = new CumsBoardMasterVO();
		master.setBbsNm("자료실");
		bbsResult.add(master);
	
		model.addAttribute("bbsList", bbsResult);
	
		//--------------------------------
		// 게시물 목록 정보 처리
		//--------------------------------	
		ArrayList<Object> target = new ArrayList<Object>();	// Object => List<CumsBoardVO>
		for (int i = 0; i < bbsResult.size() && i < 4; i++) {
	
		    target.add(null);
		}
	
		model.addAttribute("boardList", target);
		
		model.addAttribute("preview", "true");
	
		return "egovframework/com/cop/tpl/EgovCmmntyBaseTmplContents";
    }

    
}
