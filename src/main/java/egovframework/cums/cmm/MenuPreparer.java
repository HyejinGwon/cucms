package egovframework.cums.cmm;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.tiles.Attribute;
import org.apache.tiles.AttributeContext;
import org.apache.tiles.preparer.PreparerException;
import org.apache.tiles.preparer.ViewPreparer;
import org.apache.tiles.request.Request;
import org.egovframe.rte.fdl.cmmn.exception.EgovBizException;
import org.egovframe.rte.psl.dataaccess.util.EgovMap;

import egovframework.cums.cmm.service.CumsCmmUseService;




public class MenuPreparer implements ViewPreparer { // ViewPreparer 안에 있는 execute라는 메소드를 상속받아서 사용한다.
/*
 * 실행순서 : Controller 진입 > interceptor pre 실행 > controller 실행 > interceptor post실행 > preparer 실행 
 * */
	@Resource(name = "cumsCmmUseService")
	private CumsCmmUseService service;
	@Override
    public void execute(Request request, AttributeContext attributeContext) throws PreparerException {
        System.out.println("Tiles Menu @@@@@@@@@@@@@@@@@@@@@@@@");
        List<EgovMap> bbsMngList;
        List<EgovMap> authrtMngList;
        List<EgovMap> menuMngList;
		try {
			menuMngList = service.retrieveMenuMngList();
			authrtMngList = service.retrieveAuthrtMngList();
			bbsMngList = service.retrieveBbsMngList();
	        
	        attributeContext.putAttribute("menuMngList", new Attribute(menuMngList), true);
	        attributeContext.putAttribute("bbsMngList", new Attribute(bbsMngList), true);
	        attributeContext.putAttribute("authrtMngList", new Attribute(authrtMngList), true);

		} catch (EgovBizException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
            }
	
}
