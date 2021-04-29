package com.company;

public class WrongFormatException extends Exception{
    private String msg;

    public WrongFormatException(String msg){
        super(msg);
    }

    public String getMsg() {
        return msg;
    }
}
