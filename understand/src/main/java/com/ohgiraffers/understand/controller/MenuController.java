package com.ohgiraffers.understand.controller;

import com.ohgiraffers.understand.dto.MenuDTO;
import com.ohgiraffers.understand.exception.NotInsertNameException;
import com.ohgiraffers.understand.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/menus/*")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @GetMapping("menus")
    public ModelAndView selectAllMenu(ModelAndView mv) {
        List<MenuDTO> menus = menuService.selectAllMenu();
        if(Objects.isNull(menus)){
            throw new NullPointerException();
        }
        mv.addObject("menus", menus);
        mv.setViewName("menus/allMenus");
        return mv;
    }

    @GetMapping("onemenu")
    public ModelAndView OneMenu(ModelAndView mv) {
        mv.setViewName("menus/oneMenu");
        return mv;
    }

    @GetMapping("onemenuaction")
    public ModelAndView selectOneMenu(ModelAndView mv, MenuDTO menuDTO) {
        int code = menuDTO.getCode();
        List<MenuDTO> menu = menuService.selectOneMenu(code);
        if(Objects.isNull(menu)){
            throw new NullPointerException();
        }
        mv.addObject("menus", menu);
        mv.setViewName("menus/allMenus");
        return mv;
    }

    @GetMapping("regist")
    public ModelAndView RegistMenu(ModelAndView mv) {
        mv.setViewName("menus/regist");
        return mv;
    }

    @PostMapping("insertMenu")
    public ModelAndView insertMenu(ModelAndView mv, MenuDTO menuDTO) throws NotInsertNameException {
        int result = menuService.insertMenu(menuDTO);

        if(result <= 0){
            mv.addObject("message", "가격은 음수일 수 없습니다.");
            mv.setViewName("/error/errorMessage");
        }else {
            mv.setViewName("/menus/returnMessage");
        }
        return mv;
    }

    @GetMapping("update")
    public ModelAndView update(ModelAndView mv){
        mv.setViewName("menus/update");
        return mv;
    }


    @PostMapping("update")
    public ModelAndView updateMenu(ModelAndView mv, @RequestParam(name = "code") int code, @RequestParam(defaultValue = "", name ="name") String name, @RequestParam(defaultValue = "0", name = "price") int price, @RequestParam(defaultValue = "0", name = "categoryCode") int categoryCode) {
//    public ModelAndView updateMenu(ModelAndView mv, MenuDTO menuDTO) {
        MenuDTO menuDTO = new MenuDTO();
        menuDTO.setCode(code);
        menuDTO.setName(name);
        menuDTO.setPrice(price);
        menuDTO.setCategoryCode(categoryCode);
        int result = menuService.updateMenu(menuDTO);
        if(result <= 0){
            mv.addObject("message", "업데이트 실패");
            mv.setViewName("/error/errorMessage");
        }else{
            mv.setViewName("menus/returnMessage");
        }
        return mv;
    }

    @GetMapping("delete")
    public ModelAndView delete(ModelAndView mv) {
        mv.setViewName("menus/delete");
        return mv;
    }

    @PostMapping("delete")
    public ModelAndView deleteMenu(ModelAndView mv, MenuDTO menuDTO) {
        int delete = menuService.deleteMenu(menuDTO);

        if(delete <= 0){
            mv.addObject("message", "삭제 실패");
            mv.setViewName("/error/errorMessage");
        }else{
            mv.setViewName("menus/returnMessage");
        }
        return mv;
    }
}
