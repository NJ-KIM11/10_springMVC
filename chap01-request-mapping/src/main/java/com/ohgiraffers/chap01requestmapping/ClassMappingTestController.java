package com.ohgiraffers.chap01requestmapping;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/order/*")
public class ClassMappingTestController {

    @GetMapping("/regist")
    public String registOrder(Model model) {
        model.addAttribute("message", "Get 방식의 주문 등록용 핸들러 메소드 호출");
        return "mappingResult";
    }

    // 여러 경로를 받을수 있다 (value 받는 값을 객체화)
    @RequestMapping(value = {"modify", "delete"}, method = RequestMethod.POST)
    public String modifyOrder(Model model) {
        model.addAttribute("message", "post 방식의 주문 정보 수정 핸들러 메소드 호출");
        return "mappingResult";
    }

    /*
    *  PathVariable
    *  @PathVariable 어노테이션을 이용해 변수를 받아올 수 있다..
    *  path variable 로 전달되는 {변수명}은 반드시 매개변수명과 동일해야 한다.
    *  만약 동일하지 않으면 @PathVariable("이름")을 설정해 주어야 한다.
    *  세가지 변수명은 일치시@켜주는게 좋다(에러 방지)
    * */
    @GetMapping("detail/{orderNo}")
    public String selectOrderDetail(Model model, @PathVariable("orderNo") int orderNo) {
        model.addAttribute("message", orderNo + "번 주문 상세내용 조회용 핸들러 메소드 호출");
        return "mappingResult";
    }

    @RequestMapping
    public String otherRequest(Model model) {
        model.addAttribute("message","order 요청이긴 하지만 다른 기능이 준비되지 않음");
        return "mappingResult";
    }
}
