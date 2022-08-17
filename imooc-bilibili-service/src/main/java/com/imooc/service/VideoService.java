package com.imooc.service;

import com.imooc.domain.*;
import org.apache.mahout.cf.taste.common.TasteException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
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

    void addVideoComment(VideoComment videoComment, Long userId);

    PageResult<VideoComment> pageListVideoComments(Integer no, Integer size, Long videoId);

    Map<String, Object> getVideoDetails(Long videoId);

    void addVideoView(VideoView videoView, HttpServletRequest request);

    Integer getVideoViewCounts(Long videoId);

    List<Video> recommend(Long userId) throws TasteException;
}
