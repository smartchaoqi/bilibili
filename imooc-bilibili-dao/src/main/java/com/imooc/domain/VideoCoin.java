package com.imooc.domain;

import lombok.Data;

import java.util.Date;

@Data
public class VideoCoin {

    private Long id;

    private Long videoId;

    private Long userId;

    private Integer amount;

    private Date createTime;

    private Date updateTime;
}
