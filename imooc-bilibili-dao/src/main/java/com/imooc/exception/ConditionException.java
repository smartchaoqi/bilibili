package com.imooc.exception;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

public class ConditionException extends RuntimeException{
    public static final long serialVersionUID=1L;

    @Getter @Setter
    private String code;

    public ConditionException(String code,String name){
        super(name);
        this.code=code;
    }

    public ConditionException(String name){
        super(name);
        this.code="500";
    }
}
