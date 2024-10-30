package com.ohgiraffers.understand.exception;

// 기존 메뉴에 중복된 이름이 있을시 발생시킬 exception
public class NotInsertNameException extends Exception{

    public NotInsertNameException(String message) {
        super(message);
    }
}
