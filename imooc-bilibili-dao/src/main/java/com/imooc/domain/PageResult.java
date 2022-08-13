package com.imooc.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class PageResult<T> {
    private Integer total;
    private List<T> list;

    public PageResult(Integer total,List<T> list){
        this.total=total;
        this.list=list;
    }
}
