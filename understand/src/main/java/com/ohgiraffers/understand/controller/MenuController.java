package com.ohgiraffers.understand.controller;

import com.ohgiraffers.understand.dto.MenuDTO;
import com.ohgiraffers.understand.exception.NotInsertNameException;
import com.ohgiraffers.understand.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
}
