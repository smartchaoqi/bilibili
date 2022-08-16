package com.imooc.service;

import com.imooc.domain.PageResult;
import com.imooc.domain.Video;
import com.imooc.domain.VideoCoin;
import com.imooc.domain.VideoCollection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public interface VideoService {
    void addVideos(Video video);

    PageResult<Video> pageListVideos(Integer size, Integer no, String area);

    void viewVideoOnlineBySlices(HttpServletRequest request, HttpServletResponse response, String url);

    void addVideoLike(Long videoId, Long userId);

    void deleteVideoList(Long videoId, Long userId);

    Map<String,Object> getVideoLikes(Long videoId, Long userId);

    void addVideoCollection(VideoCollection videoCollection, Long userId);

    void deleteVideoCollection(Long videoId, Long userId);

    Map<String, Object> getVideoCollections(Long videoId, Long userId);

    void addVideoCoins(VideoCoin videoCoin, Long userId);

    Map<String, Object> getVideoCoins(Long videoId, Long userId);
}
