package com.ohgiraffers.chap01requestmapping;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.awt.*;

@Controller
public class MethodMappingTestController {

    @RequestMapping("/menu/regist")
    public String registMenu(Model model){
        // Model 객체는 응답을 위한 객체
        model.addAttribute("message", "신규 메뉴 등록용 핸들러 메소드 호출");

        return "mappingResult";
    }

    @RequestMapping(value = "/menu/modify", method = RequestMethod.GET)
    public String modifyMenu(Model model){
        model.addAttribute("message", "Get 방식의 메뉴 수정 호출");
        return "mappingResult";
    }

    /*
    *  요청 메소드 전용 어노테이션
    *   요청 메소드      어노테이션
    *       post        @PostMapping
    *       get         @GetMapping
    *       Put         @PutMapping         // 수정 리소스의 모든것을 업데이트  a,b,c  ==> a', c'
    *       Delete      @DeleteMapping      // 삭제
    *       Patch       @PatchMapping       // 수정 리소스 일부 업데이트       a,b,c  ==> a', b , c'
    * */

    @GetMapping("/menu/delete")
    public String getDeleteMenu(Model model){
        model.addAttribute("message", "Get 방식의 삭제 핸들러 메소드 호출");
        return "mappingResult";
    }

    @PostMapping("/menu/delete")
    public String postDeleteMenu(Model model){
        model.addAttribute("message", "Post 방식의 삭제 핸들러 메소드 호출");
        return "mappingResult";
    }


}
