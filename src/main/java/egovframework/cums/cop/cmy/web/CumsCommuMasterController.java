package egovframework.cums.cop.cmy.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springmodules.validation.commons.DefaultBeanValidator;

import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.annotation.IncludedInfo;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.utl.fcc.service.EgovStringUtil;
import egovframework.cums.cop.cmy.service.CumsCommuManageService;
import egovframework.cums.cop.cmy.service.CumsCommuMasterService;
import egovframework.cums.cop.cmy.service.CumsCommunity;
import egovframework.cums.cop.cmy.service.CumsCommunityUserVO;
import egovframework.cums.cop.cmy.service.CumsCommunityVO;

import org.egovframe.rte.fdl.property.EgovPropertyService;
import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

/**
 * 커뮤니티 정보를 관리하기 위한 컨트롤러 클래스
 * @author 공통서비스개발팀 이삼섭
 * @since 2009.06.01
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *   -------       --------    ---------------------------
 *   2009.4.2	이삼섭          최초 생성
 *   2011.8.26	정진오			IncludedInfo annotation 추가
 *   2011.9.7	정진오			커뮤니티 탈퇴 요청이 정상적으로 이뤄지지 않은 사항 수정함
 *   							커뮤니티 탈퇴 요청시 승인자를 선택하므로 탈퇴 승인자가 자신이 될 수 없음에도
 *   							세션에서 가져온 값(탈퇴신청자)을 탈퇴승인자로 설정하도록 되어 있었음
 *   2016.06.13 김연호          표준프레임워크 v3.6 개선
 * </pre>
 */

@Controller
public class CumsCommuMasterController {
	
    @Resource(name = "CumsCommuMasterService")
    private CumsCommuMasterService cumsCommuMasterService;
    
    @Resource(name = "CumsCommuManageService")
    private CumsCommuManageService cumsCommuManageService;

    @Resource(name = "propertiesService")
    protected EgovPropertyService propertyService;

    @Autowired
    private DefaultBeanValidator beanValidator;

    //Logger log = Logger.getLogger(this.getClass());
	
	/**
     * 커뮤니티에 대한 목록을 조회한다.
     * 
     * @param cmmntyVO
     * @param model
     * @return
     * @throws Exception
     */
    @IncludedInfo(name="커뮤니티관리", order = 270 ,gid = 40)
    @RequestMapping("/cop/cmy/CumsSelectCommuMasterList.do")
    public String selectCommuMasterList(@ModelAttribute("searchVO") CumsCommunityVO cmmntyVO, ModelMap model) throws Exception {
	cmmntyVO.setPageUnit(propertyService.getInt("pageUnit"));
	cmmntyVO.setPageSize(propertyService.getInt("pageSize"));

	PaginationInfo paginationInfo = new PaginationInfo();
	
	paginationInfo.setCurrentPageNo(cmmntyVO.getPageIndex());
	paginationInfo.setRecordCountPerPage(cmmntyVO.getPageUnit());
	paginationInfo.setPageSize(cmmntyVO.getPageSize());

	cmmntyVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
	cmmntyVO.setLastIndex(paginationInfo.getLastRecordIndex());
	cmmntyVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

	Map<String, Object> map = cumsCommuMasterService.selectCommuMasterList(cmmntyVO);
	int totCnt = Integer.parseInt((String)map.get("resultCnt"));
	
	paginationInfo.setTotalRecordCount(totCnt);

	model.addAttribute("resultList", map.get("resultList"));
	model.addAttribute("resultCnt", map.get("resultCnt"));
	model.addAttribute("paginationInfo", paginationInfo);

	return "cums/com/cop/bbs/CumsCommuMasterList";
    }

    /**
     * 커뮤니티 등록을 위한 등록페이지로 이동한다.
     * 
     * @param cmmntyVO
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/cop/cmy/insertCumsCommuMasterView.do")
    public String insertCommuMasterView(@ModelAttribute("searchVO") CumsCommunityVO cmmntyVO, ModelMap model) throws Exception {
    	model.addAttribute("commuMasterVO", new CumsCommunityVO());
    	
	return "egovframework/com/cop/cmy/EgovCommuMasterRegist";
    }

    /**
     * 커뮤니티 정보를 등록한다.
     * 
     * @param cmmntyVO
     * @param cmmnty
     * @param status
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/cop/cmy/insertCumsCommuMaster.do")
    public String insertCommuMaster(@ModelAttribute("searchVO") CumsCommunityVO cmmntyVO, @ModelAttribute("commuMaster") CumsCommunity community,
	    BindingResult bindingResult, ModelMap model) throws Exception {

		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
   	 	// KISA 보안취약점 조치 (2018-12-10, 신용호)
        if(!isAuthenticated) {
            return "egovframework/com/uat/uia/EgovLoginUsr";
        }
        
		beanValidator.validate(community, bindingResult);
	
		if (bindingResult.hasErrors()) {
		    return "egovframework/com/cop/cmy/EgovCommuMasterRegist";
		}
	
		community.setRegistSeCode("REGC02");
		community.setFrstRegisterId(user == null ? "" : EgovStringUtil.isNullToString(user.getUniqId()));
	
		String cmmntyId = "";
		if (isAuthenticated) {
		    cmmntyId = cumsCommuMasterService.insertCommuMaster(community);

		    //커뮤니티 개설자의 정보를 등록한다.
		    CumsCommunityUserVO cmmntyUserVO = new CumsCommunityUserVO();
		    cmmntyUserVO.setCmmntyId(cmmntyId);
		    cmmntyUserVO.setEmplyrId(user == null ? "" : EgovStringUtil.isNullToString(user.getUniqId()));
		    cmmntyUserVO.setMngrAt("Y");
		    cmmntyUserVO.setMberSttus("P");
		    cmmntyUserVO.setUseAt("Y");
		    cmmntyUserVO.setFrstRegisterId(user == null ? "" : EgovStringUtil.isNullToString(user.getUniqId()));
		    
		    cumsCommuManageService.insertCommuUserRqst(cmmntyUserVO);
		}
		
		
	
		return "forward:/cop/cmy/selectCommuMasterList.do";
    }

    /**
     * 커뮤니티에 대한 상세정보를 조회한다.
     * 
     * @param cmmntyVO
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/cop/cmy/selectCumsCommuMasterDetail.do")
    public String selectCommuMasterDetail(@ModelAttribute("searchVO") CumsCommunityVO cmmntyVO, ModelMap model, HttpServletRequest request) throws Exception {
		CumsCommunityVO result = cumsCommuMasterService.selectCommuMaster(cmmntyVO);

		//-----------------------
		// 제공 URL 
		//-----------------------
		result.setProvdUrl(request.getContextPath()+ "/cop/cmy/CommuMainPage.do?cmmntyId=" + result.getCmmntyId());
		////---------------------
	
		model.addAttribute("result", result);
	
		return "egovframework/com/cop/cmy/EgovCommuMasterDetail";
    }

    /**
     * 커뮤니티 정보 수정을 위한 수정페이지로 이동한다.
     * 
     * @param cmmntyVO
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/cop/cmy/updateCumsCommuMasterView.do")
    public String updateCommuMasterView(@ModelAttribute("searchVO") CumsCommunityVO cmmntyVO, ModelMap model)
	    throws Exception {
		
		CumsCommunityVO result = cumsCommuMasterService.selectCommuMaster(cmmntyVO);
		
		model.addAttribute("commuMasterVO", result);
	
		return "egovframework/com/cop/cmy/EgovCommuMasterUpdt";
    }

    /**
     * 커뮤니티 정보를 수정한다.
     * 
     * @param cmmntyVO
     * @param status
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/cop/cmy/updateCumsCommuMaster.do")
    public String updateCommuMaster(@ModelAttribute("searchVO") CumsCommunityVO cmmntyVO, @ModelAttribute("commuMaster") CumsCommunity community,
	    BindingResult bindingResult, ModelMap model) throws Exception {

		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		// KISA 보안취약점 조치 (2018-12-10, 신용호)
        if(!isAuthenticated) {
            return "egovframework/com/uat/uia/EgovLoginUsr";
        }
	
		beanValidator.validate(community, bindingResult);
		if (bindingResult.hasErrors()) {
	
		    CumsCommunityVO result = cumsCommuMasterService.selectCommuMaster(cmmntyVO);
		    model.addAttribute("result", result);
	
		    return "egovframework/com/cop/cmy/EgovCommuMasterUpdt";
		}
	
		community.setLastUpdusrId(user == null ? "" : EgovStringUtil.isNullToString(user.getUniqId()));
		
		cumsCommuMasterService.updateCommuMaster(community);
	
		return "forward:/cop/cmy/selectCommuMasterList.do";
    }
    
    /**
     * 커뮤니티 정보를 삭제한다.
     * 
     * @param cmmntyVO
     * @param status
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/cop/cmy/deleteCumsCommuMaster.do")
    public String deleteCommuMaster(@ModelAttribute("searchVO") CumsCommunityVO cmmntyVO, @ModelAttribute("commuMaster") CumsCommunity community,
	    BindingResult bindingResult, ModelMap model) throws Exception {

    	LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
    	Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();

    	if (isAuthenticated) {
    		community.setLastUpdusrId(user == null ? "" : EgovStringUtil.isNullToString(user.getUniqId()));
    	    cumsCommuMasterService.deleteBBSMasterInf(community);
    	}
    	return "forward:/cop/cmy/selectCommuMasterList.do";
        }
    
    /**
     * 포트릿을 위한 커뮤니티 정보 목록 정보를 조회한다.
     * 
     * @param cmmntyVO
     * @param sessionVO
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/cop/cmy/selectCumsCommuMasterListPortlet.do")
    public String selectCmmntyListPortlet(@ModelAttribute("searchVO") CumsCommunityVO cmmntyVO, ModelMap model) throws Exception {
	List<CumsCommunityVO> result = cumsCommuMasterService.selectCommuMasterListPortlet(cmmntyVO);
	
	model.addAttribute("resultList", result);

	return "egovframework/com/cop/cmy/EgovCommuMasterListPortlet";
    }
}
