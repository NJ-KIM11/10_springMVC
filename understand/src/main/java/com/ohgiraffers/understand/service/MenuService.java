package com.ohgiraffers.understand.service;

import com.ohgiraffers.understand.dto.MenuDTO;
import com.ohgiraffers.understand.exception.NotInsertNameException;
import com.ohgiraffers.understand.model.MenuDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MenuService {

    @Autowired
    private MenuDAO menuDAO;

    public List<MenuDTO> selectAllMenu() {
        List<MenuDTO> menus = menuDAO.selectAllMenu();
        return menus;
    }


    public List<MenuDTO> selectOneMenu(int code) {
        List<MenuDTO> menu = menuDAO.selectOneMenu(code);
        return menu;
    }

    public int insertMenu(MenuDTO menuDTO) throws NotInsertNameException {
        List<MenuDTO> menus = menuDAO.selectAllMenu();
        // 이름만 불러오는 메소드 요청 만들어서 날리는게 더 좋다!(메모리 덜 씀)

        List<String> names = new ArrayList<>();

        for (MenuDTO menu : menus) {
            names.add(menu.getName());
        }

        if(names.contains(menuDTO.getName()) || menuDTO.getName().isEmpty()) {
            throw new NotInsertNameException("");
        }

        if(menuDTO.getPrice() <= 0){
            return 0;
        }
        int result = menuDAO.insertMenu(menuDTO);
        return result;
    }

    public int updateMenu(MenuDTO menuDTO) {
        int menu = menuDAO.updateMenu(menuDTO);
        return menu;
    }

    public int deleteMenu(MenuDTO menuDTO) {
        int menu = menuDAO.deleteMenu(menuDTO);
        return menu;
    }
}
