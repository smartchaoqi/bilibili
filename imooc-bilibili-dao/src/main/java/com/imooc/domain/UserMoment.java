package com.imooc.domain;

import lombok.Data;

import java.util.Date;

@Data
public class UserMoment {
    private Long id;
    private Long userId;
    private String type; //0 视频 1 直播 2 专栏动态
    private Long contentId;
    private Date createTime;
    private Date updateTime;
}
