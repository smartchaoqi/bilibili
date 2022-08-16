package com.imooc.api;

import com.imooc.domain.*;
import com.imooc.service.VideoService;
import com.imooc.support.UserSupport;
import com.qiniu.util.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
public class VideoApi {
    @Autowired
    private VideoService videoService;

    @Autowired
    private UserSupport userSupport;

    @PostMapping("/videos")
    public JsonResponse<String> addVideos(@RequestBody Video video){
        Long userId = userSupport.getCurrentUserId();
        video.setUserId(userId);
        videoService.addVideos(video);
        return JsonResponse.success();
    }

    @GetMapping("/videos")
    public JsonResponse<PageResult<Video>> pageListVideos(Integer size,Integer no,String area){
        PageResult<Video> result = videoService.pageListVideos(size,no,area);
        return new JsonResponse<>(result);
    }

    @GetMapping("/video-slices")
    public void viewVideoOnlineBySlices(HttpServletRequest request,
                                        HttpServletResponse response,
                                        String url){
        videoService.viewVideoOnlineBySlices(request,response,url);
    }

    @PostMapping("/video-likes")
    public JsonResponse<String> addVideoLike(@RequestBody Long videoId){
        Long userId = userSupport.getCurrentUserId();
        videoService.addVideoLike(videoId,userId);
        return JsonResponse.success();
    }

    @DeleteMapping("/video-likes")
    public JsonResponse<String> deleteVideoList(@RequestBody Long videoId){
        Long userId = userSupport.getCurrentUserId();
        videoService.deleteVideoList(videoId,userId);
        return JsonResponse.success();
    }

    @GetMapping("/video-likes")
    public JsonResponse<Map<String,Object>> getVideoLikes(@RequestBody Long videoId){
        Long userId=null;
        try{
            userId = userSupport.getCurrentUserId();
        }catch (Exception ignored){}
        Map<String,Object> total = videoService.getVideoLikes(videoId,userId);
        return new JsonResponse<>(total);
    }

    /**
     * 收藏视频
     */
    @PostMapping("/video-collections")
    public JsonResponse<String> addVideoCollection(@RequestBody VideoCollection videoCollection){
        Long userId = userSupport.getCurrentUserId();
        videoService.addVideoCollection(videoCollection, userId);
        return JsonResponse.success();
    }

    /**
     * 取消收藏视频
     */
    @DeleteMapping("/video-collections")
    public JsonResponse<String> deleteVideoCollection(@RequestParam Long videoId){
        Long userId = userSupport.getCurrentUserId();
        videoService.deleteVideoCollection(videoId, userId);
        return JsonResponse.success();
    }

    /**
     * 查询视频收藏数量
     */
    @GetMapping("/video-collections")
    public JsonResponse<Map<String, Object>> getVideoCollections(@RequestParam Long videoId){
        Long userId = null;
        try{
            userId = userSupport.getCurrentUserId();
        }catch (Exception ignored){}
        Map<String, Object> result = videoService.getVideoCollections(videoId, userId);
        return new JsonResponse<>(result);
    }

    /**
     * 视频投币
     * @param videoCoin
     * @return
     */
    @PostMapping("/video-coins")
    public JsonResponse<String> addVideoCoins(@RequestBody VideoCoin videoCoin){
        Long userId = userSupport.getCurrentUserId();
        videoService.addVideoCoins(videoCoin,userId);
        return JsonResponse.success();
    }

    /**
     * 查询用户投币数量
     * @param videoId
     * @return
     */
    @GetMapping("/video-coins")
    public JsonResponse<Map<String,Object>> getVideoCoins(@RequestParam Long videoId){
        Long userId=null;
        try{
            userId=userSupport.getCurrentUserId();
        }catch (Exception ignored){}
        Map<String,Object> result=videoService.getVideoCoins(videoId,userId);
        return new JsonResponse<>(result);
    }

    /**
     * 添加视频评论
     * @param videoComment
     * @return
     */
    @PostMapping("/video-comments")
    public JsonResponse<String> addVideoComment(@RequestBody VideoComment videoComment){
        Long userId = userSupport.getCurrentUserId();
        videoService.addVideoComment(videoComment,userId);
        return JsonResponse.success();
    }

    /**
     * 分页查询视频评论
     * @return
     */
    @GetMapping("/video-comments")
    public JsonResponse<PageResult<VideoComment>> pageListVideoComments(@RequestParam("no") Integer no,
                                                                        @RequestParam("size") Integer size,
                                                                        @RequestParam("videoId") Long videoId){
        PageResult<VideoComment> result = videoService.pageListVideoComments(no,size,videoId);
        return new JsonResponse<>(result);
    }

    /**
     * 获取视频详情
     * @param videoId
     * @return
     */
    @GetMapping("/video-details")
    public JsonResponse<Map<String,Object>> getVideoDetails(@RequestParam("videoId") Long videoId){
        Map<String,Object> result=videoService.getVideoDetails(videoId);
        return new JsonResponse<>(result);
    }
}
