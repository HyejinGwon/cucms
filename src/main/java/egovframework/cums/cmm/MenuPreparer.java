package egovframework.cums.cmm;

import java.util.ArrayList;
import java.util.List;

import org.apache.tiles.Attribute;
import org.apache.tiles.AttributeContext;
import org.apache.tiles.preparer.PreparerException;
import org.apache.tiles.preparer.ViewPreparer;
import org.apache.tiles.request.Request;

public class MenuPreparer implements ViewPreparer { // ViewPreparer 안에 있는 execute라는 메소드를 상속받아서 사용한다.
/*
 * 실행순서 : Controller 진입 > interceptor pre 실행 > controller 실행 > interceptor post실행 > preparer 실행 
 * */
	@Override
    public void execute(Request request, AttributeContext attributeContext) throws PreparerException {
        System.out.println("Tiles Menu @@@@@@@@@@@@@@@@@@@@@@@@");
        List<String> menuMngList = new ArrayList<String>();
        List<String> bbsMngList = new ArrayList<String>();
        List<String> authrtMngList = new ArrayList<String>();
        
        menuMngList.add("프로그램목록11"); // jsp 파일을 프로그램에 등록후 메뉴 생성관리에서 프로그램 선택한다.
        menuMngList.add("메뉴사용관리"); // 트리형태 + 체크박스로 사용여부 결정하는 페이지
        menuMngList.add("메뉴생성관리"); // 메뉴 생성 및 사용여부 변경하는 페이지
        
        authrtMngList.add("권한관리"); // 그룹별 접근 권한들을 체크하는 부분
        authrtMngList.add("권한그룹관리"); // 관리자, 직원, 방문사용자 등의 그룹을 관리
        authrtMngList.add("권한메뉴3");
        
        bbsMngList.add("게시판관리1");
        bbsMngList.add("게시판관리2");
        
        attributeContext.putAttribute("menuMngList", new Attribute(menuMngList), true);
        attributeContext.putAttribute("bbsMngList", new Attribute(bbsMngList), true);
        attributeContext.putAttribute("authrtMngList", new Attribute(authrtMngList), true);
    }
	
}
