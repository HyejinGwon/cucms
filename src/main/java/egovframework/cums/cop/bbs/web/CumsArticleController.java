package egovframework.cums.cop.bbs.web;

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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springmodules.validation.commons.DefaultBeanValidator;

import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.EgovWebUtil;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.service.EgovFileMngService;
import egovframework.com.cmm.service.EgovFileMngUtil;
import egovframework.com.cmm.service.FileVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.cmm.util.EgovXssChecker;
import org.egovframe.rte.fdl.property.EgovPropertyService;
import egovframework.com.utl.fcc.service.EgovStringUtil;
import egovframework.cums.cop.bbs.service.CumsArticleService;
import egovframework.cums.cop.bbs.service.CumsBBSMasterService;
import egovframework.cums.cop.bbs.service.CumsBBSSatisfactionService;
import egovframework.cums.cop.bbs.service.CumsBlogVO;
import egovframework.cums.cop.bbs.service.CumsBoard;
import egovframework.cums.cop.bbs.service.CumsBoardMaster;
import egovframework.cums.cop.bbs.service.CumsBoardMasterVO;
import egovframework.cums.cop.bbs.service.CumsBoardVO;
import egovframework.cums.cop.cmt.service.CumsArticleCommentService;
import egovframework.cums.cop.cmt.service.CumsCommentVO;

import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

/**
 * 게시물 관리를 위한 컨트롤러 클래스
 * @author 공통서비스개발팀 이삼섭
 * @since 2009.06.01
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *  수정일               수정자            수정내용
 *  ----------   -------    ---------------------------
 *  2009.03.19   이삼섭            최초 생성
 *  2009.06.29   한성곤            2단계 기능 추가 (댓글관리, 만족도조사)
 *  2011.07.01   안민정            댓글, 스크랩, 만족도 조사 기능의 종속성 제거
 *  2011.08.26   정진오            IncludedInfo annotation 추가
 *  2011.09.07   서준식            유효 게시판 게시일 지나도 게시물이 조회되던 오류 수정
 *  2016.06.13   김연호            표준프레임워크 3.6 개선
 *  2019.05.17   신용호            KISA 취약점 조치 및 보완
 *  2020.10.27   신용호            파일 업로드 수정 (multiRequest.getFiles)
 *  
 * </pre>
 */

@Controller
public class CumsArticleController {

	private static final Logger LOGGER = LoggerFactory.getLogger(CumsArticleController.class);
	
	@Resource(name = "CumsArticleService")
    private CumsArticleService cumsArticleService;

    @Resource(name = "CumsBBSMasterService")
    private CumsBBSMasterService cumsBBSMasterService;

    @Resource(name = "EgovFileMngService")
    private EgovFileMngService fileMngService;

    @Resource(name = "EgovFileMngUtil")
    private EgovFileMngUtil fileUtil;

    @Resource(name = "propertiesService")
    protected EgovPropertyService propertyService;
    
    @Resource(name="egovMessageSource")
    EgovMessageSource egovMessageSource;
    
    @Resource(name = "CumsArticleCommentService")
    protected CumsArticleCommentService cumsArticleCommentService;

    @Resource(name = "CumsBBSSatisfactionService")
    private CumsBBSSatisfactionService bbsSatisfactionService;

//	@Resource(name = "CumsTemplateManageService")
//	private CumsTemplateManageService cumsTemplateManageService;
    
    @Autowired
    private DefaultBeanValidator beanValidator;

    //protected Logger log = Logger.getLogger(this.getClass());
    
    /**
     * XSS 방지 처리.
     * 
     * @param data
     * @return
     */
    protected String unscript(String data) {
        if (data == null || data.trim().equals("")) {
            return "";
        }
        
        String ret = data;
        
        ret = ret.replaceAll("<(S|s)(C|c)(R|r)(I|i)(P|p)(T|t)", "&lt;script");
        ret = ret.replaceAll("</(S|s)(C|c)(R|r)(I|i)(P|p)(T|t)", "&lt;/script");
        
        ret = ret.replaceAll("<(O|o)(B|b)(J|j)(E|e)(C|c)(T|t)", "&lt;object");
        ret = ret.replaceAll("</(O|o)(B|b)(J|j)(E|e)(C|c)(T|t)", "&lt;/object");
        
        ret = ret.replaceAll("<(A|a)(P|p)(P|p)(L|l)(E|e)(T|t)", "&lt;applet");
        ret = ret.replaceAll("</(A|a)(P|p)(P|p)(L|l)(E|e)(T|t)", "&lt;/applet");
        
        ret = ret.replaceAll("<(E|e)(M|m)(B|b)(E|e)(D|d)", "&lt;embed");
        ret = ret.replaceAll("</(E|e)(M|m)(B|b)(E|e)(D|d)", "&lt;embed");
        
        ret = ret.replaceAll("<(F|f)(O|o)(R|r)(M|m)", "&lt;form");
        ret = ret.replaceAll("</(F|f)(O|o)(R|r)(M|m)", "&lt;form");

        return ret;
    }

    /**
     * 게시물에 대한 목록을 조회한다.
     * 
     * @param boardVO
     * @param sessionVO
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/cop/bbs/CumsSelectArticleList.do")
    public String selectArticleList(@ModelAttribute("searchVO") CumsBoardVO boardVO, ModelMap model) throws Exception {
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();	//KISA 보안취약점 조치 (2018-12-10, 이정은)

        if(!isAuthenticated) {
            return "egovframework/com/uat/uia/EgovLoginUsr";
        }
	
		CumsBoardMasterVO vo = new CumsBoardMasterVO();
		
		vo.setBbsId(boardVO.getBbsId());
		vo.setUniqId((user == null || user.getUniqId() == null) ? "" : user.getUniqId());
		CumsBoardMasterVO master = cumsBBSMasterService.selectBBSMasterInf(vo);
		
		//방명록은 방명록 게시판으로 이동
		if(master.getBbsTyCode().equals("BBST03")){
			return "forward:/cop/bbs/selectGuestArticleList.do";
		}
		
		
		boardVO.setPageUnit(propertyService.getInt("pageUnit"));
		boardVO.setPageSize(propertyService.getInt("pageSize"));
	
		PaginationInfo paginationInfo = new PaginationInfo();
		
		paginationInfo.setCurrentPageNo(boardVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(boardVO.getPageUnit());
		paginationInfo.setPageSize(boardVO.getPageSize());
	
		boardVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		boardVO.setLastIndex(paginationInfo.getLastRecordIndex());
		boardVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
	
		Map<String, Object> map = cumsArticleService.selectArticleList(boardVO);
		int totCnt = Integer.parseInt((String)map.get("resultCnt"));
		
		//공지사항 추출
		List<CumsBoardVO> noticeList = cumsArticleService.selectNoticeArticleList(boardVO);
		
		paginationInfo.setTotalRecordCount(totCnt);
	
		//-------------------------------
		// 기본 BBS template 지정 
		//-------------------------------
		if (master.getTmplatCours() == null || master.getTmplatCours().equals("")) {
		    master.setTmplatCours("/css/egovframework/com/cop/tpl/egovBaseTemplate.css");
		}
		////-----------------------------
	
		if(user != null) {
	    	model.addAttribute("sessionUniqId", user.getUniqId());
	    }
		
		model.addAttribute("resultList", map.get("resultList"));
		model.addAttribute("resultCnt", map.get("resultCnt"));
		model.addAttribute("articleVO", boardVO);
		model.addAttribute("boardMasterVO", master);
		model.addAttribute("paginationInfo", paginationInfo);
		model.addAttribute("noticeList", noticeList);
		return "egovframework/com/cop/bbs/EgovArticleList";
    }
    
    
    
    /**
     * 게시물에 대한 상세 정보를 조회한다.
     * 
     * @param boardVO
     * @param sessionVO
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/cop/bbs/CumsSelectArticleDetail.do")
    public String selectArticleDetail(@ModelAttribute("searchVO") CumsBoardVO boardVO, ModelMap model) throws Exception {
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();	//KISA 보안취약점 조치 (2018-12-10, 이정은)

        if(!isAuthenticated) {
            return "egovframework/com/uat/uia/EgovLoginUsr";
        }

	
		boardVO.setLastUpdusrId((user == null || user.getUniqId() == null) ? "" : user.getUniqId());
		CumsBoardVO vo = cumsArticleService.selectArticleDetail(boardVO);
	
		model.addAttribute("result", vo);
		model.addAttribute("sessionUniqId", (user == null || user.getUniqId() == null) ? "" : user.getUniqId());
		
		//비밀글은 작성자만 볼수 있음 
		if(!EgovStringUtil.isEmpty(vo.getSecretAt()) && vo.getSecretAt().equals("Y") && !((user == null || user.getUniqId() == null) ? "" : user.getUniqId()).equals(vo.getFrstRegisterId()))
			return"forward:/cop/bbs/selectArticleList.do";
		
		//----------------------------
		// template 처리 (기본 BBS template 지정  포함)
		//----------------------------
		CumsBoardMasterVO master = new CumsBoardMasterVO();
		
		master.setBbsId(boardVO.getBbsId());
		master.setUniqId((user == null || user.getUniqId() == null) ? "" : user.getUniqId());
		
		CumsBoardMasterVO masterVo = cumsBBSMasterService.selectBBSMasterInf(master);
	
		if (masterVo.getTmplatCours() == null || masterVo.getTmplatCours().equals("")) {
		    masterVo.setTmplatCours("/css/egovframework/com/cop/tpl/egovBaseTemplate.css");
		}
	
		////-----------------------------
		
		//----------------------------
		// 2009.06.29 : 2단계 기능 추가
		// 2011.07.01 : 댓글, 만족도 조사 기능의 종속성 제거
		//----------------------------
		if (cumsArticleCommentService != null){
			if (cumsArticleCommentService.canUseComment(boardVO.getBbsId())) {
			    model.addAttribute("useComment", "true");
			}
		}
		if (bbsSatisfactionService != null) {
			if (bbsSatisfactionService.canUseSatisfaction(boardVO.getBbsId())) {
			    model.addAttribute("useSatisfaction", "true");
			}
		}
		////--------------------------
		
		model.addAttribute("boardMasterVO", masterVo);
	
		return "egovframework/com/cop/bbs/EgovArticleDetail";
    }

    /**
     * 게시물 등록을 위한 등록페이지로 이동한다.
     * 
     * @param boardVO
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/cop/bbs/CumsInsertArticleView.do")
    public String insertArticleView(@ModelAttribute("searchVO") CumsBoardVO boardVO, ModelMap model) throws Exception {
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
	
		CumsBoardMasterVO bdMstr = new CumsBoardMasterVO();
		CumsBoardVO board = new CumsBoardVO();
		if (isAuthenticated) {
	
		    CumsBoardMasterVO vo = new CumsBoardMasterVO();
		    vo.setBbsId(boardVO.getBbsId());
		    vo.setUniqId((user == null || user.getUniqId() == null) ? "" : user.getUniqId());
	
		    bdMstr = cumsBBSMasterService.selectBBSMasterInf(vo);
		}
	
		//----------------------------
		// 기본 BBS template 지정 
		//----------------------------
		if (bdMstr.getTmplatCours() == null || bdMstr.getTmplatCours().equals("")) {
		    bdMstr.setTmplatCours("/css/egovframework/com/cop/tpl/egovBaseTemplate.css");
		}
	
		model.addAttribute("articleVO", boardVO);
		model.addAttribute("boardMasterVO", bdMstr);
		////-----------------------------
	
		return "egovframework/com/cop/bbs/EgovArticleRegist";
    }

    /**
     * 게시물을 등록한다.
     * 
     * @param boardVO
     * @param board
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/cop/bbs/cumsInsertArticle.do")
    public String insertArticle(final MultipartHttpServletRequest multiRequest, @ModelAttribute("searchVO") CumsBoardVO boardVO,
	    @ModelAttribute("bdMstr") CumsBoardMaster bdMstr, @ModelAttribute("board") CumsBoardVO board, BindingResult bindingResult, 
	    ModelMap model) throws Exception {

		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		
		if(!isAuthenticated) {	//KISA 보안취약점 조치 (2018-12-10, 이정은)
            return "egovframework/com/uat/uia/EgovLoginUsr";
        }
	
		beanValidator.validate(board, bindingResult);
		if (bindingResult.hasErrors()) {
	
		    CumsBoardMasterVO master = new CumsBoardMasterVO();
		    
		    master.setBbsId(boardVO.getBbsId());
		    master.setUniqId((user == null || user.getUniqId() == null) ? "" : user.getUniqId());
	
		    master = cumsBBSMasterService.selectBBSMasterInf(master);
		    
	
		    //----------------------------
		    // 기본 BBS template 지정 
		    //----------------------------
		    if (master.getTmplatCours() == null || master.getTmplatCours().equals("")) {
			master.setTmplatCours("css/egovframework/com/cop/tpl/egovBaseTemplate.css");
		    }
	
		    model.addAttribute("boardMasterVO", master);
		    ////-----------------------------
	
		    return "egovframework/com/cop/bbs/EgovArticleRegist";
		}
	
		if (isAuthenticated) {
		    List<FileVO> result = null;
		    String atchFileId = "";
		    
		    //final Map<String, MultipartFile> files = multiRequest.getFileMap();
		    final List<MultipartFile> files = multiRequest.getFiles("file_1");
		    if (!files.isEmpty()) {
		    	result = fileUtil.parseFileInf(files, "BBS_", 0, "", "");
		    	atchFileId = fileMngService.insertFileInfs(result);
		    }
		    board.setAtchFileId(atchFileId);
		    board.setFrstRegisterId((user == null || user.getUniqId() == null) ? "" : user.getUniqId());
		    board.setBbsId(boardVO.getBbsId());
		    board.setBlogId(boardVO.getBlogId());
		    
		    
		    //익명등록 처리 
		    if(board.getAnonymousAt() != null && board.getAnonymousAt().equals("Y")){
		    	board.setNtcrId("anonymous"); //게시물 통계 집계를 위해 등록자 ID 저장
		    	board.setNtcrNm("익명"); //게시물 통계 집계를 위해 등록자 Name 저장
		    	board.setFrstRegisterId("anonymous");
		    	
		    } else {
		    	board.setNtcrId((user == null || user.getUniqId() == null) ? "" : user.getUniqId()); //게시물 통계 집계를 위해 등록자 ID 저장
		    	board.setNtcrNm((user == null || user.getName() == null) ? "" : user.getName()); //게시물 통계 집계를 위해 등록자 Name 저장
		    	
		    }
		    
		    board.setNttCn(unscript(board.getNttCn()));	// XSS 방지
		    cumsArticleService.insertArticle(board);
		}
		//status.setComplete();
		if(boardVO.getBlogAt().equals("Y")){
			return "forward:/cop/bbs/selectArticleBlogList.do";
		}else{
			return "forward:/cop/bbs/selectArticleList.do";
		}
		
    }

    /**
     * 게시물에 대한 답변 등록을 위한 등록페이지로 이동한다.
     * 
     * @param boardVO
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/cop/bbs/CumsReplyArticleView.do")
    public String addReplyBoardArticle(@ModelAttribute("searchVO") CumsBoardVO boardVO, ModelMap model) throws Exception {
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
	
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();//KISA 보안취약점 조치 (2018-12-10, 이정은)

        if(!isAuthenticated) {
            return "egovframework/com/uat/uia/EgovLoginUsr";
        }
        
		CumsBoardMasterVO master = new CumsBoardMasterVO();
		CumsBoardVO articleVO = new CumsBoardVO();
		master.setBbsId(boardVO.getBbsId());
		master.setUniqId((user == null || user.getUniqId() == null) ? "" : user.getUniqId());
	
		master = cumsBBSMasterService.selectBBSMasterInf(master);
		boardVO = cumsArticleService.selectArticleDetail(boardVO);
		
		//----------------------------
		// 기본 BBS template 지정 
		//----------------------------
		if (master.getTmplatCours() == null || master.getTmplatCours().equals("")) {
		    master.setTmplatCours("/css/egovframework/com/cop/tpl/egovBaseTemplate.css");
		}
	
		model.addAttribute("boardMasterVO", master);
		model.addAttribute("result", boardVO);
	
		model.addAttribute("articleVO", articleVO);
		
		if(boardVO.getBlogAt().equals("chkBlog")){
			return "egovframework/com/cop/bbs/EgovArticleBlogReply";
		}else{
			return "egovframework/com/cop/bbs/EgovArticleReply";
		}
    }

    /**
     * 게시물에 대한 답변을 등록한다.
     * 
     * @param boardVO
     * @param board
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/cop/bbs/CumsReplyArticle.do")
    public String replyBoardArticle(final MultipartHttpServletRequest multiRequest, @ModelAttribute("searchVO") CumsBoardVO boardVO,
	    @ModelAttribute("bdMstr") CumsBoardMaster bdMstr, @ModelAttribute("board") CumsBoardVO board, BindingResult bindingResult, ModelMap model
	    ) throws Exception {

		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		
		if(!isAuthenticated) {	//KISA 보안취약점 조치 (2018-12-10, 이정은)
            return "egovframework/com/uat/uia/EgovLoginUsr";
        }
		
		beanValidator.validate(board, bindingResult);
		if (bindingResult.hasErrors()) {
		    CumsBoardMasterVO master = new CumsBoardMasterVO();
		    
		    master.setBbsId(boardVO.getBbsId());
		    master.setUniqId((user == null || user.getUniqId() == null) ? "" : user.getUniqId());
	
		    master = cumsBBSMasterService.selectBBSMasterInf(master);
		    
	
		    //----------------------------
		    // 기본 BBS template 지정 
		    //----------------------------
		    if (master.getTmplatCours() == null || master.getTmplatCours().equals("")) {
			master.setTmplatCours("/css/egovframework/com/cop/tpl/egovBaseTemplate.css");
		    }
	
		    model.addAttribute("articleVO", boardVO);
		    model.addAttribute("boardMasterVO", master);
		    ////-----------------------------
	
		    return "egovframework/com/cop/bbs/EgovArticleReply";
		}
	
		if (isAuthenticated) {
		    //final Map<String, MultipartFile> files = multiRequest.getFileMap();
		    final List<MultipartFile> files = multiRequest.getFiles("file_1");
		    String atchFileId = "";
	
		    if (!files.isEmpty()) {
				List<FileVO> result = fileUtil.parseFileInf(files, "BBS_", 0, "", "");
				atchFileId = fileMngService.insertFileInfs(result);
		    }
	
		    board.setAtchFileId(atchFileId);
		    board.setReplyAt("Y");
		    board.setFrstRegisterId((user == null || user.getUniqId() == null) ? "" : user.getUniqId());
		    board.setBbsId(board.getBbsId());
		    board.setParnts(Long.toString(boardVO.getNttId()));
		    board.setSortOrdr(boardVO.getSortOrdr());
		    board.setReplyLc(Integer.toString(Integer.parseInt(boardVO.getReplyLc()) + 1));
		    
		    //익명등록 처리 
		    if(board.getAnonymousAt() != null && board.getAnonymousAt().equals("Y")){
		    	board.setNtcrId("anonymous"); //게시물 통계 집계를 위해 등록자 ID 저장
		    	board.setNtcrNm("익명"); //게시물 통계 집계를 위해 등록자 Name 저장
		    	board.setFrstRegisterId("anonymous");
		    	
		    } else {
		    	board.setNtcrId((user == null || user.getId() == null) ? "" : user.getId()); //게시물 통계 집계를 위해 등록자 ID 저장
		    	board.setNtcrNm((user == null || user.getName() == null) ? "" : user.getName()); //게시물 통계 집계를 위해 등록자 Name 저장
		    	
		    }
		    board.setNttCn(unscript(board.getNttCn()));	// XSS 방지
		    
		    cumsArticleService.insertArticle(board);
		}
		
		return "forward:/cop/bbs/selectArticleList.do";
    }

    /**
     * 게시물 수정을 위한 수정페이지로 이동한다.
     * 
     * @param boardVO
     * @param vo
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/cop/bbs/CumsUpdateArticleView.do")
    public String updateArticleView(@ModelAttribute("searchVO") CumsBoardVO boardVO, @ModelAttribute("board") CumsBoardVO vo, ModelMap model)
	    throws Exception {

		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
	
		boardVO.setFrstRegisterId((user == null || user.getUniqId() == null) ? "" : user.getUniqId());
		
		CumsBoardMasterVO bmvo = new CumsBoardMasterVO();
		CumsBoardVO bdvo = new CumsBoardVO();
		
		vo.setBbsId(boardVO.getBbsId());
		
		bmvo.setBbsId(boardVO.getBbsId());
		bmvo.setUniqId((user == null || user.getUniqId() == null) ? "" : user.getUniqId());
	
		if (isAuthenticated) {
		    bmvo = cumsBBSMasterService.selectBBSMasterInf(bmvo);
		    bdvo = cumsArticleService.selectArticleDetail(boardVO);
		}
	
		//----------------------------
		// 기본 BBS template 지정 
		//----------------------------
		if (bmvo.getTmplatCours() == null || bmvo.getTmplatCours().equals("")) {
		    bmvo.setTmplatCours("/css/egovframework/com/cop/tpl/egovBaseTemplate.css");
		}
	
		//익명 등록글인 경우 수정 불가 
		if(bdvo.getNtcrId().equals("anonymous")){
			model.addAttribute("result", bdvo);
			model.addAttribute("boardMasterVO", bmvo);
			return "egovframework/com/cop/bbs/EgovArticleDetail";
		}
		
		model.addAttribute("articleVO", bdvo);
		model.addAttribute("boardMasterVO", bmvo);
		
		if(boardVO.getBlogAt().equals("chkBlog")){
			return "egovframework/com/cop/bbs/EgovArticleBlogUpdt";
		}else{
			return "egovframework/com/cop/bbs/EgovArticleUpdt";
		}
		
    }

    /**
     * 게시물에 대한 내용을 수정한다.
     * 
     * @param boardVO
     * @param board
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/cop/bbs/CumsUpdateArticle.do")
    public String updateBoardArticle(final MultipartHttpServletRequest multiRequest, @ModelAttribute("searchVO") CumsBoardVO boardVO,
	    @ModelAttribute("bdMstr") CumsBoardMaster bdMstr, @ModelAttribute("board") CumsBoard board, BindingResult bindingResult, ModelMap model) throws Exception {

		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		
		if(!isAuthenticated) {	//KISA 보안취약점 조치 (2018-12-10, 이정은)
            return "egovframework/com/uat/uia/EgovLoginUsr";
        }
		
		//--------------------------------------------------------------------------------------------
    	// @ XSS 대응 권한체크 체크  START
    	// param1 : 사용자고유ID(uniqId,esntlId)
    	//--------------------------------------------------------
    	LOGGER.debug("@ XSS 권한체크 START ----------------------------------------------");
    	//step1 DB에서 해당 게시물의 uniqId 조회
    	CumsBoardVO vo = cumsArticleService.selectArticleDetail(boardVO);
    	
    	//step2 EgovXssChecker 공통모듈을 이용한 권한체크
    	EgovXssChecker.checkerUserXss(multiRequest, vo.getFrstRegisterId()); 
      	LOGGER.debug("@ XSS 권한체크 END ------------------------------------------------");
    	//--------------------------------------------------------
    	// @ XSS 대응 권한체크 체크 END
    	//--------------------------------------------------------------------------------------------
	
		String atchFileId = boardVO.getAtchFileId();
	
		beanValidator.validate(board, bindingResult);
		if (bindingResult.hasErrors()) {
	
		    boardVO.setFrstRegisterId((user == null || user.getUniqId() == null) ? "" : user.getUniqId());
		    
		    CumsBoardMasterVO bmvo = new CumsBoardMasterVO();
		    CumsBoardVO bdvo = new CumsBoardVO();
		    
		    bmvo.setBbsId(boardVO.getBbsId());
		    bmvo.setUniqId((user == null || user.getUniqId() == null) ? "" : user.getUniqId());
	
		    bmvo = cumsBBSMasterService.selectBBSMasterInf(bmvo);
		    bdvo = cumsArticleService.selectArticleDetail(boardVO);
	
		    model.addAttribute("articleVO", bdvo);
		    model.addAttribute("boardMasterVO", bmvo);
	
		    return "egovframework/com/cop/bbs/EgovArticleUpdt";
		}
		
		if (isAuthenticated) {
		    
			//final Map<String, MultipartFile> files = multiRequest.getFileMap();
			final List<MultipartFile> files = multiRequest.getFiles("file_1");
		    if (!files.isEmpty()) {
				if (atchFileId == null || "".equals(atchFileId)) {
				    List<FileVO> result = fileUtil.parseFileInf(files, "BBS_", 0, atchFileId, "");
				    atchFileId = fileMngService.insertFileInfs(result);
				    board.setAtchFileId(atchFileId);
				} else {
				    FileVO fvo = new FileVO();
				    fvo.setAtchFileId(atchFileId);
				    int cnt = fileMngService.getMaxFileSN(fvo);
				    List<FileVO> _result = fileUtil.parseFileInf(files, "BBS_", cnt, atchFileId, "");
				    fileMngService.updateFileInfs(_result);
				}
		    }
	
		    board.setLastUpdusrId((user == null || user.getUniqId() == null) ? "" : user.getUniqId());
		    
		    board.setNtcrNm("");	// dummy 오류 수정 (익명이 아닌 경우 validator 처리를 위해 dummy로 지정됨)
		    board.setPassword("");	// dummy 오류 수정 (익명이 아닌 경우 validator 처리를 위해 dummy로 지정됨)
		    
		    board.setNttCn(unscript(board.getNttCn()));	// XSS 방지
		    
		    cumsArticleService.updateArticle(board);
		}
		
		return "forward:/cop/bbs/selectArticleList.do";
    }

    /**
     * 게시물에 대한 내용을 삭제한다.
     * 
     * @param boardVO
     * @param board
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/cop/bbs/CumsDeleteArticle.do")
    public String deleteBoardArticle(HttpServletRequest request, @ModelAttribute("searchVO") CumsBoardVO boardVO, @ModelAttribute("board") CumsBoard board,
	    @ModelAttribute("bdMstr") CumsBoardMaster bdMstr, ModelMap model) throws Exception {
	
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
	
		//--------------------------------------------------------------------------------------------
    	// @ XSS 대응 권한체크 체크  START
    	// param1 : 사용자고유ID(uniqId,esntlId)
    	//--------------------------------------------------------
    	LOGGER.debug("@ XSS 권한체크 START ----------------------------------------------");
    	//step1 DB에서 해당 게시물의 uniqId 조회
    	CumsBoardVO vo = cumsArticleService.selectArticleDetail(boardVO);
    	
    	//step2 EgovXssChecker 공통모듈을 이용한 권한체크
    	EgovXssChecker.checkerUserXss(request, vo.getFrstRegisterId()); 
      	LOGGER.debug("@ XSS 권한체크 END ------------------------------------------------");
    	//--------------------------------------------------------
    	// @ XSS 대응 권한체크 체크 END
    	//--------------------------------------------------------------------------------------------
		
		CumsBoardVO bdvo = cumsArticleService.selectArticleDetail(boardVO);
		//익명 등록글인 경우 수정 불가 
		if(bdvo.getNtcrId().equals("anonymous")){
			model.addAttribute("result", bdvo);
			model.addAttribute("boardMasterVO", bdMstr);
			return "egovframework/com/cop/bbs/EgovArticleDetail";
		}
		
		if (isAuthenticated) {
		    board.setLastUpdusrId((user == null || user.getUniqId() == null) ? "" : user.getUniqId());
		    
		    cumsArticleService.deleteArticle(board);
		}
		
		if(boardVO.getBlogAt().equals("chkBlog")){
			return "forward:/cop/bbs/selectArticleBlogList.do";
		}else{
			return "forward:/cop/bbs/selectArticleList.do";
		}
    }
    
    /**
     * 방명록에 대한 목록을 조회한다.
     * 
     * @param boardVO
     * @param sessionVO
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/cop/bbs/CumsSelectGuestArticleList.do")
    public String selectGuestArticleList(@ModelAttribute("searchVO") CumsBoardVO boardVO, ModelMap model) throws Exception {

		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		
		if(!isAuthenticated) {	//KISA 보안취약점 조치 (2018-12-10, 이정은)
            return "egovframework/com/uat/uia/EgovLoginUsr";
        }
		
		// 수정 및 삭제 기능 제어를 위한 처리
		model.addAttribute("sessionUniqId", (user == null || user.getUniqId() == null) ? "" : user.getUniqId());
		
		CumsBoardVO vo = new CumsBoardVO();
	
		vo.setBbsId(boardVO.getBbsId());
		vo.setBbsNm(boardVO.getBbsNm());
		vo.setNtcrNm((user == null || user.getName() == null) ? "" : user.getName());
		vo.setNtcrId((user == null || user.getUniqId() == null) ? "" : user.getUniqId());
	
		CumsBoardMasterVO masterVo = new CumsBoardMasterVO();
		
		masterVo.setBbsId(vo.getBbsId());
		masterVo.setUniqId((user == null || user.getUniqId() == null) ? "" : user.getUniqId());
		
		CumsBoardMasterVO mstrVO = cumsBBSMasterService.selectBBSMasterInf(masterVo);
	
		vo.setPageIndex(boardVO.getPageIndex());
		vo.setPageUnit(propertyService.getInt("pageUnit"));
		vo.setPageSize(propertyService.getInt("pageSize"));
	
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(vo.getPageIndex());
		paginationInfo.setRecordCountPerPage(vo.getPageUnit());
		paginationInfo.setPageSize(vo.getPageSize());
	
		vo.setFirstIndex(paginationInfo.getFirstRecordIndex());
		vo.setLastIndex(paginationInfo.getLastRecordIndex());
		vo.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
	
		Map<String, Object> map = cumsArticleService.selectGuestArticleList(vo);
		int totCnt = Integer.parseInt((String)map.get("resultCnt"));
		
		paginationInfo.setTotalRecordCount(totCnt);
	
		model.addAttribute("user", user);
		model.addAttribute("resultList", map.get("resultList"));
		model.addAttribute("resultCnt", map.get("resultCnt"));
		model.addAttribute("boardMasterVO", mstrVO);
		model.addAttribute("articleVO", vo);
		model.addAttribute("paginationInfo", paginationInfo);
	
		return "egovframework/com/cop/bbs/EgovGuestArticleList";
    }
    
	
    /**
     * 방명록에 대한 내용을 등록한다.
     * 
     * @param boardVO
     * @param board
     * @param sessionVO
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/cop/bbs/CumsInsertGuestArticle.do")
    public String insertGuestList(@ModelAttribute("searchVO") CumsBoardVO boardVO, @ModelAttribute("CumsBoard") CumsBoard board, BindingResult bindingResult,
	    ModelMap model) throws Exception {

		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
	
		if(!isAuthenticated) {	//KISA 보안취약점 조치 (2018-12-10, 이정은)
            return "egovframework/com/uat/uia/EgovLoginUsr";
        }
		
		beanValidator.validate(board, bindingResult);
		if (bindingResult.hasErrors()) {
	
		    CumsBoardVO vo = new CumsBoardVO();
	
		    vo.setBbsId(boardVO.getBbsId());
		    vo.setBbsNm(boardVO.getBbsNm());
		    vo.setNtcrNm(user == null ? "" : EgovStringUtil.isNullToString(user.getName()));
		    vo.setNtcrId((user == null || user.getUniqId() == null) ? "" : user.getUniqId());
	
		    CumsBoardMasterVO masterVo = new CumsBoardMasterVO();
		    
		    masterVo.setBbsId(vo.getBbsId());
		    masterVo.setUniqId((user == null || user.getUniqId() == null) ? "" : user.getUniqId());
		    
		    CumsBoardMasterVO mstrVO = cumsBBSMasterService.selectBBSMasterInf(masterVo);
	
		    vo.setPageUnit(propertyService.getInt("pageUnit"));
		    vo.setPageSize(propertyService.getInt("pageSize"));
	
		    PaginationInfo paginationInfo = new PaginationInfo();
		    paginationInfo.setCurrentPageNo(vo.getPageIndex());
		    paginationInfo.setRecordCountPerPage(vo.getPageUnit());
		    paginationInfo.setPageSize(vo.getPageSize());
	
		    vo.setFirstIndex(paginationInfo.getFirstRecordIndex());
		    vo.setLastIndex(paginationInfo.getLastRecordIndex());
		    vo.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
	
		    Map<String, Object> map = cumsArticleService.selectGuestArticleList(vo);
		    int totCnt = Integer.parseInt((String)map.get("resultCnt"));
		    
		    paginationInfo.setTotalRecordCount(totCnt);
	
		    model.addAttribute("resultList", map.get("resultList"));
		    model.addAttribute("resultCnt", map.get("resultCnt"));
		    model.addAttribute("boardMasterVO", mstrVO);
		    model.addAttribute("articleVO", vo);	    
		    model.addAttribute("paginationInfo", paginationInfo);
	
		    return "egovframework/com/cop/bbs/EgovGuestArticleList";
	
		}
	
		if (isAuthenticated) {
		    board.setFrstRegisterId((user == null || user.getUniqId() == null) ? "" : user.getUniqId());
		    
		    cumsArticleService.insertArticle(board);
	
		    boardVO.setNttCn("");
		    boardVO.setPassword("");
		    boardVO.setNtcrId("");
		    boardVO.setNttId(0);
		}
	
		return "forward:/cop/bbs/selectGuestArticleList.do";
    }
    
    /**
     * 방명록에 대한 내용을 삭제한다.
     * 
     * @param boardVO
     * @param sessionVO
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/cop/bbs/CumsDeleteGuestArticle.do")
    public String deleteGuestList(@ModelAttribute("searchVO") CumsBoardVO boardVO, @ModelAttribute("articleVO") CumsBoard board, ModelMap model) throws Exception {
		@SuppressWarnings("unused")
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		
		if (isAuthenticated) {
		    cumsArticleService.deleteArticle(boardVO);
		}
		
		return "forward:/cop/bbs/selectGuestArticleList.do";
    }
    
    /**
     * 방명록 수정을 위한 특정 내용을 조회한다.
     * 
     * @param boardVO
     * @param sessionVO
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/cop/bbs/CumsUpdateGuestArticleView.do")
    public String updateGuestArticleView(@ModelAttribute("searchVO") CumsBoardVO boardVO, @ModelAttribute("boardMasterVO") CumsBoardMasterVO brdMstrVO,
	    ModelMap model) throws Exception {

		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		
		if(!isAuthenticated) {	//KISA 보안취약점 조치 (2018-12-10, 이정은)
            return "egovframework/com/uat/uia/EgovLoginUsr";
        }
	
		// 수정 및 삭제 기능 제어를 위한 처리
		model.addAttribute("sessionUniqId", (user == null || user.getUniqId() == null) ? "" : user.getUniqId());
		
		CumsBoardVO vo = cumsArticleService.selectArticleDetail(boardVO);
	
		boardVO.setBbsId(boardVO.getBbsId());
		boardVO.setBbsNm(boardVO.getBbsNm());
		boardVO.setNtcrNm((user == null || user.getName() == null) ? "" : user.getName());
	
		boardVO.setPageUnit(propertyService.getInt("pageUnit"));
		boardVO.setPageSize(propertyService.getInt("pageSize"));
	
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(boardVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(boardVO.getPageUnit());
		paginationInfo.setPageSize(boardVO.getPageSize());
	
		boardVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		boardVO.setLastIndex(paginationInfo.getLastRecordIndex());
		boardVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
	
		Map<String, Object> map = cumsArticleService.selectGuestArticleList(boardVO);
		int totCnt = Integer.parseInt((String)map.get("resultCnt"));
		
		paginationInfo.setTotalRecordCount(totCnt);
	
		model.addAttribute("resultList", map.get("resultList"));
		model.addAttribute("resultCnt", map.get("resultCnt"));
		model.addAttribute("articleVO", vo);
		model.addAttribute("paginationInfo", paginationInfo);
	
		return "egovframework/com/cop/bbs/EgovGuestArticleList";
    }
    
    /**
     * 방명록을 수정하고 게시판 메인페이지를 조회한다.
     * 
     * @param boardVO
     * @param sessionVO
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/cop/bbs/CumsUpdateGuestArticle.do")
    public String updateGuestArticle(@ModelAttribute("searchVO") CumsBoardVO boardVO, @ModelAttribute CumsBoard board, BindingResult bindingResult,
	    ModelMap model) throws Exception {

		//BBST02, BBST04
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		
		if(!isAuthenticated) {	//KISA 보안취약점 조치 (2018-12-10, 이정은)
            return "egovframework/com/uat/uia/EgovLoginUsr";
        }
	
		beanValidator.validate(board, bindingResult);
		if (bindingResult.hasErrors()) {
	
		    CumsBoardVO vo = new CumsBoardVO();
	
		    vo.setBbsId(boardVO.getBbsId());
		    vo.setBbsNm(boardVO.getBbsNm());
		    vo.setNtcrNm((user == null || user.getName() == null) ? "" : user.getName());
		    vo.setNtcrId((user == null || user.getUniqId() == null) ? "" : user.getUniqId());
	
		    CumsBoardMasterVO masterVo = new CumsBoardMasterVO();
		    
		    masterVo.setBbsId(vo.getBbsId());
		    masterVo.setUniqId((user == null || user.getUniqId() == null) ? "" : user.getUniqId());
		    
		    CumsBoardMasterVO mstrVO = cumsBBSMasterService.selectBBSMasterInf(masterVo);
	
		    vo.setPageUnit(propertyService.getInt("pageUnit"));
		    vo.setPageSize(propertyService.getInt("pageSize"));
	
		    PaginationInfo paginationInfo = new PaginationInfo();
		    paginationInfo.setCurrentPageNo(vo.getPageIndex());
		    paginationInfo.setRecordCountPerPage(vo.getPageUnit());
		    paginationInfo.setPageSize(vo.getPageSize());
	
		    vo.setFirstIndex(paginationInfo.getFirstRecordIndex());
		    vo.setLastIndex(paginationInfo.getLastRecordIndex());
		    vo.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
	
		    Map<String, Object> map = cumsArticleService.selectGuestArticleList(vo);
		    int totCnt = Integer.parseInt((String)map.get("resultCnt"));
	
		    paginationInfo.setTotalRecordCount(totCnt);
		    
		    model.addAttribute("resultList", map.get("resultList"));
		    model.addAttribute("resultCnt", map.get("resultCnt"));
		    model.addAttribute("boardMasterVO", mstrVO);
		    model.addAttribute("articleVO", vo);
		    model.addAttribute("paginationInfo", paginationInfo);
	
		    return "egovframework/com/cop/bbs/EgovGuestArticleList";
		}
	
		if (isAuthenticated) {
		    cumsArticleService.updateArticle(board);
		    boardVO.setNttCn("");
		    boardVO.setPassword("");
		    boardVO.setNtcrId("");
		    boardVO.setNttId(0);
		}
	
		return "forward:/cop/bbs/selectGuestArticleList.do";
    }
    
    /*********************
     * 블로그관련
     * ********************/
    
    /**
     * 블로그 게시판에 대한 목록을 조회한다.
     * 
     * @param boardVO
     * @param sessionVO
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/cop/bbs/CumsSelectArticleBlogList.do")
    public String selectArticleBlogList(@ModelAttribute("searchVO") CumsBoardVO boardVO, ModelMap model) throws Exception {
    	
    	LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
    	
    	Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();	//KISA 보안취약점 조치 (2018-12-10, 이정은)

        if(!isAuthenticated) {
            return "egovframework/com/uat/uia/EgovLoginUsr";
        }
		
		CumsBlogVO blogVo = new CumsBlogVO();
		blogVo.setFrstRegisterId((user == null || user.getUniqId() == null) ? "" : user.getUniqId());
		blogVo.setBbsId(boardVO.getBbsId());
		blogVo.setBlogId(boardVO.getBlogId());
		CumsBlogVO master = cumsBBSMasterService.selectBlogDetail(blogVo);
		
		boardVO.setFrstRegisterId((user == null || user.getUniqId() == null) ? "" : user.getUniqId());

		//블로그 카테고리관리 권한(로그인 한 사용자만 가능)
		int loginUserCnt =  cumsArticleService.selectLoginUser(boardVO);
		
		//블로그 게시판 제목 추출
		List<CumsBoardVO> blogNameList = cumsArticleService.selectBlogNmList(boardVO);

		if(user != null) {
	    	model.addAttribute("sessionUniqId", (user == null || user.getUniqId() == null) ? "" : user.getUniqId());
	    }
		
		model.addAttribute("articleVO", boardVO);
		model.addAttribute("boardMasterVO", master);
		model.addAttribute("blogNameList", blogNameList);
		model.addAttribute("loginUserCnt", loginUserCnt);
		
		return "egovframework/com/cop/bbs/EgovArticleBlogList";
    }
    
    /**
     * 블로그 게시물에 대한 상세 타이틀을 조회한다.
     * 
     * @param boardVO
     * @param sessionVO
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/cop/bbs/CumsSelectArticleBlogDetail.do")
    public ModelAndView selectArticleBlogDetail(@ModelAttribute("searchVO") CumsBoardVO boardVO, ModelMap model) throws Exception {
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		
		 Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();	//KISA 보안취약점 조치 (2018-12-10, 이정은)

	        if(!isAuthenticated) {
	        	throw new IllegalAccessException("Login Required!");
	        }
	        
		CumsBoardVO vo = new CumsBoardVO();
		
		boardVO.setLastUpdusrId((user == null || user.getUniqId() == null) ? "" : user.getUniqId());
		
		boardVO.setPageUnit(propertyService.getInt("pageUnit"));
		boardVO.setPageSize(propertyService.getInt("pageSize"));
		
		PaginationInfo paginationInfo = new PaginationInfo();
		
		paginationInfo.setCurrentPageNo(boardVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(boardVO.getPageUnit());
		paginationInfo.setPageSize(boardVO.getPageSize());
		
		boardVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		boardVO.setLastIndex(paginationInfo.getLastRecordIndex());
		boardVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
		
		List<CumsBoardVO> blogSubJectList = cumsArticleService.selectArticleDetailDefault(boardVO);
		vo = cumsArticleService.selectArticleCnOne(boardVO);
		
		int totCnt = cumsArticleService.selectArticleDetailDefaultCnt(boardVO);
		paginationInfo.setTotalRecordCount(totCnt);
		
		ModelAndView mav = new ModelAndView("jsonView");
		mav.addObject("blogSubJectList", blogSubJectList);
		mav.addObject("paginationInfo", paginationInfo);
		
		if(vo.getNttCn() != null){
			mav.addObject("blogCnOne", vo);
		}
		
		//비밀글은 작성자만 볼수 있음 
		if(!EgovStringUtil.isEmpty(vo.getSecretAt()) && vo.getSecretAt().equals("Y") && !((user == null || user.getUniqId() == null) ? "" : user.getUniqId()).equals(vo.getFrstRegisterId()))
		mav.setViewName("forward:/cop/bbs/selectArticleList.do");
		return mav;
    }
    
    /**
     * 블로그 게시물에 대한 상세 내용을 조회한다.
     * 
     * @param boardVO
     * @param sessionVO
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/cop/bbs/CumsSelectArticleBlogDetailCn.do")
    public ModelAndView selectArticleBlogDetailCn(@ModelAttribute("searchVO") CumsBoardVO boardVO, @ModelAttribute("commentVO") CumsCommentVO commentVO, ModelMap model) throws Exception {
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		
		boardVO.setLastUpdusrId((user == null || user.getUniqId() == null) ? "" : user.getUniqId());
		
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();	//KISA 보안취약점 조치 (2018-12-10, 이정은)

        if(!isAuthenticated) {
        	throw new IllegalAccessException("Login Required!");
        }
		
		CumsBoardVO vo = cumsArticleService.selectArticleDetail(boardVO);
		
		//----------------------------
		// 댓글 처리
		//----------------------------
		CumsCommentVO articleCommentVO = new CumsCommentVO();
		commentVO.setWrterNm((user == null || user.getName() == null) ? "" : user.getName());
		
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(commentVO.getSubPageIndex());
		paginationInfo.setRecordCountPerPage(commentVO.getSubPageUnit());
		paginationInfo.setPageSize(commentVO.getSubPageSize());
	
		commentVO.setSubFirstIndex(paginationInfo.getFirstRecordIndex());
		commentVO.setSubLastIndex(paginationInfo.getLastRecordIndex());
		commentVO.setSubRecordCountPerPage(paginationInfo.getRecordCountPerPage());
	
		Map<String, Object> map = cumsArticleCommentService.selectArticleCommentList(commentVO);
		int totCnt = Integer.parseInt((String)map.get("resultCnt"));
		
		paginationInfo.setTotalRecordCount(totCnt);
		
	    //댓글 처리 END
		//----------------------------
		
		List<CumsBoardVO> blogCnList = cumsArticleService.selectArticleDetailCn(boardVO);
		ModelAndView mav = new ModelAndView("jsonView");
		
		// 수정 처리된 후 댓글 등록 화면으로 처리되기 위한 구현
		if (commentVO.isModified()) {
		    commentVO.setCommentNo("");
		    commentVO.setCommentCn("");
		}
		
		// 수정을 위한 처리
		if (!commentVO.getCommentNo().equals("")) {
			mav.setViewName ("forward:/cop/cmt/updateArticleCommentView.do");
		}
		
		mav.addObject("blogCnList", blogCnList);
		mav.addObject("resultUnder", vo);
		mav.addObject("paginationInfo", paginationInfo);
		mav.addObject("resultList", map.get("resultList"));
		mav.addObject("resultCnt", map.get("resultCnt"));
		mav.addObject("articleCommentVO", articleCommentVO);	// validator 용도
		
		commentVO.setCommentCn("");	// 등록 후 댓글 내용 처리
		
		//비밀글은 작성자만 볼수 있음 
		if(!EgovStringUtil.isEmpty(vo.getSecretAt()) && vo.getSecretAt().equals("Y") && !((user == null || user.getUniqId() == null) ? "" : user.getUniqId()).equals(vo.getFrstRegisterId()))
		mav.setViewName("forward:/cop/bbs/selectArticleList.do");
		return mav;
		
    }
    
    /**
     * 개인블로그 관리 
     * 
     * @param boardVO
     * @param sessionVO
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/cop/bbs/CumsSelectBlogListManager.do")
    public String selectBlogMasterList(@ModelAttribute("searchVO") CumsBoardVO boardVO, ModelMap model) throws Exception {
    	
    	LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
    	
    	
    	boardVO.setPageUnit(propertyService.getInt("pageUnit"));
    	boardVO.setPageSize(propertyService.getInt("pageSize"));
	
		PaginationInfo paginationInfo = new PaginationInfo();
		
		paginationInfo.setCurrentPageNo(boardVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(boardVO.getPageUnit());
		paginationInfo.setPageSize(boardVO.getPageSize());
	
		boardVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		boardVO.setLastIndex(paginationInfo.getLastRecordIndex());
		boardVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
		boardVO.setFrstRegisterId((user == null || user.getUniqId() == null) ? "" : user.getUniqId());
		
		Map<String, Object> map = cumsArticleService.selectBlogListManager(boardVO);
		int totCnt = Integer.parseInt((String)map.get("resultCnt"));
		
		paginationInfo.setTotalRecordCount(totCnt);
	
		model.addAttribute("resultList", map.get("resultList"));
		model.addAttribute("resultCnt", map.get("resultCnt"));	
		model.addAttribute("paginationInfo", paginationInfo);
    	
    	return "egovframework/com/cop/bbs/EgovBlogListManager";
    }

    /**
     * 템플릿에 대한 미리보기용 게시물 목록을 조회한다.
     * 
     * @param boardVO
     * @param sessionVO
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/cop/bbs/CumsPreviewBoardList.do")
    public String previewBoardArticles(@ModelAttribute("searchVO") CumsBoardVO boardVO, ModelMap model) throws Exception {
		//LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
	
		String template = boardVO.getSearchWrd();	// 템플릿 URL
		
		CumsBoardMasterVO master = new CumsBoardMasterVO();
		
		master.setBbsNm("미리보기 게시판");
	
		boardVO.setPageUnit(propertyService.getInt("pageUnit"));
		boardVO.setPageSize(propertyService.getInt("pageSize"));
	
		PaginationInfo paginationInfo = new PaginationInfo();
		
		paginationInfo.setCurrentPageNo(boardVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(boardVO.getPageUnit());
		paginationInfo.setPageSize(boardVO.getPageSize());
	
		boardVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		boardVO.setLastIndex(paginationInfo.getLastRecordIndex());
		boardVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
		
		CumsBoardVO target = null;
		List<CumsBoardVO> list = new ArrayList<CumsBoardVO>();
		
		target = new CumsBoardVO();
		target.setNttSj("게시판 기능 설명");
		target.setFrstRegisterId("ID");
		target.setFrstRegisterNm("관리자");
		target.setFrstRegisterPnttm("2019-01-01");
		target.setInqireCo(7);
		target.setParnts("0");
		target.setReplyAt("N");
		target.setReplyLc("0");
		target.setUseAt("Y");
		
		list.add(target);
		
		target = new CumsBoardVO();
		target.setNttSj("게시판 부가 기능 설명");
		target.setFrstRegisterId("ID");
		target.setFrstRegisterNm("관리자");
		target.setFrstRegisterPnttm("2019-01-01");
		target.setInqireCo(7);
		target.setParnts("0");
		target.setReplyAt("N");
		target.setReplyLc("0");
		target.setUseAt("Y");
		
		list.add(target);
		
		boardVO.setSearchWrd("");
	
		int totCnt = list.size();
		
		//공지사항 추출
		List<CumsBoardVO> noticeList = cumsArticleService.selectNoticeArticleList(boardVO);
	
		paginationInfo.setTotalRecordCount(totCnt);
	
		master.setTmplatCours(template);
		
		model.addAttribute("resultList", list);
		model.addAttribute("resultCnt", Integer.toString(totCnt));
		model.addAttribute("articleVO", boardVO);
		model.addAttribute("boardMasterVO", master);
		model.addAttribute("paginationInfo", paginationInfo);
		model.addAttribute("noticeList", noticeList);
		
		model.addAttribute("preview", "true");
	
		return "egovframework/com/cop/bbs/EgovArticleList";
    }

    /**
     * 미리보기 커뮤니티 메인페이지를 조회한다.
     * 
     * @param cmmntyVO
     * @param sessionVO
     * @param model
     * @return
     * @throws Exception
     
    @RequestMapping("/cop/bbs/previewBlogMainPage.do")
    public String previewBlogMainPage(@ModelAttribute("searchVO") CumsBoardVO boardVO, ModelMap model) throws Exception {

    	LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
    	Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();	//KISA 보안취약점 조치 (2018-12-10, 이정은)

    	String tmplatCours = boardVO.getSearchWrd();
    	
		CumsBlogVO master = new CumsBlogVO();
		master.setBlogNm("미리보기 블로그");
		master.setBlogIntrcn("미리보기를 위한 블로그입니다.");
		master.setUseAt("Y");
		master.setFrstRegisterId((user == null || user.getUniqId() == null) ? "" : user.getUniqId());
		
		boardVO.setFrstRegisterId((user == null || user.getUniqId() == null) ? "" : user.getUniqId());

		//블로그 카테고리관리 권한(로그인 한 사용자만 가능)
		int loginUserCnt =  cumsArticleService.selectLoginUser(boardVO);
		
		//블로그 게시판 제목 추출
		List<CumsBoardVO> blogNameList = new ArrayList<CumsBoardVO>();
		
		CumsBoardVO target = null;
		target = new CumsBoardVO();
		target.setBbsNm("블로그게시판#1");
		
		blogNameList.add(target);
		
		
		if(user != null) {
	    	model.addAttribute("sessionUniqId", user.getUniqId());
	    }
		
		model.addAttribute("articleVO", boardVO);
		model.addAttribute("boardMasterVO", master);
		model.addAttribute("blogNameList", blogNameList);
		model.addAttribute("loginUserCnt", 1);
		
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
        
    */
}
