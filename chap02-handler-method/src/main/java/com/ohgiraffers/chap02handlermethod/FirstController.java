package com.ohgiraffers.chap02handlermethod;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.context.request.WebRequest;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;

@Controller
@RequestMapping("/first/*")
@SessionAttributes("id")        // controller 안에서만 공유해주는 key 지정, 여기서만 쓸수 있는 지역세션
public class FirstController {

    @GetMapping("regist")
    public void regist() {}
    // 메소드의 이름은 상관없고, ""주소(directory)가 중요

    @PostMapping("regist")
    public String registMenu(Model model, WebRequest webRequest) {
        String name = webRequest.getParameter("name");
        int price = Integer.parseInt(webRequest.getParameter("price"));
        int categoryCode = Integer.parseInt(webRequest.getParameter("categoryCode"));
        String message = name + "을(를) 신규 메뉴 목록의 " + categoryCode + "번 카테고리에 " + price + "원으로 등록 하셨습니다.";
        System.out.println(message);
        model.addAttribute("message", message);
        return "first/messagePrinter";
    }

    @GetMapping("modify")
    public void modify() {}

    // required = 파라미터 포함 여부, name = 이름, defaultValue = 기본 값
//    @PostMapping("modify")
//    public String modifyMenu(Model model, @RequestParam(required = false, name = "modifyName") String modifyName, @RequestParam(defaultValue = "0", name = "modifyPrice") int modifyPrice){
//        String message = modifyName + " 메뉴 가격을 " + modifyPrice + "원으로 변경 하였습니다.";
//        System.out.println(message);
//        model.addAttribute("message", message);
//        return "first/messagePrinter";
//    }

    @PostMapping("modify")
    public String modifyMenu(Model model, @RequestParam Map<String, String> params) {
        String modifyName = params.get("modifyName");
        int modifyPrice = Integer.parseInt(params.get("modifyPrice"));
        String message = modifyName + " 메뉴 가격을 " + modifyPrice + "원으로 변경 하였습니다.";
        System.out.println(message);
        model.addAttribute("message", message);
        return "first/messagePrinter";
    }

    @GetMapping("search")
    public void search(){}

    @PostMapping("search")      // @ModelAttribute에 바로 담아주기 때문에 Model 객체가 불필요
    public String searchMenu(@ModelAttribute("menu") MenuDTO menu){
        System.out.println(menu);
        return "first/searchResult";
    }

    // 4. session 이용하기
    @GetMapping("login")
    public void login(){}

    // 4-1 : HttpSession 을 매개변수로 선언하면 핸들러 메소드 호출 시 세션  객체를 호출함.
    @PostMapping("login")
    public String sessionTest(HttpSession session, @RequestParam String id) {
        session.setAttribute("id", id);
        return "first/loginResult";
    }

    @GetMapping("logout1")
    public String logoutTest1(HttpSession session) {
        session.invalidate();
        return "first/login";
    }

    /*
    *  4-2. SessionAttribute 를 이용하여 session에 값 담기
    *   클래스 레벨에 @SessionAttribute 어노테이션을 이용하여 세션에 값을 담을 key 를
    *   설정해두면, model 영역에 해당 key로 값이 추가되는 경우 Session에 자동 등록한다..
    *   (@SessionAttribute 로 지정된 속성은 해당 컨트롤러 내에서만 유효하다.)
    * */
    @PostMapping("login2")
    public String sessionTest2(Model model, @RequestParam String id) {
        model.addAttribute("id", id);
        return "first/loginResult";
    }

    // SessionAttribute로 등록된 값은 session의 상태를 관리하는
    // sessionStatus 의 setComplete() 메소드를 호출해야 사용이 만료된다.
    @GetMapping("logout2")
    public String logoutTest2(SessionStatus sessionStatus) {
        sessionStatus.setComplete();
        return "first/loginResult";
    }

    @GetMapping("body")
    public void body(){}

    /*
    * 5. @RequestBody 를 이용하는 방법
    * 해당 어노테이션은 http 본문 자체를 읽는 부분을
    * 모델로 변환시켜 주는 어노테이션이다.
    * */
    @PostMapping("body")
    public void bodyTest(@RequestBody String body) throws UnsupportedEncodingException {
        System.out.println(body);
        System.out.println(URLDecoder.decode(body, "UTF-8"));
    }

}
