package com.imooc.service.impl;

import com.imooc.dao.VideoDao;
import com.imooc.domain.*;
import com.imooc.exception.ConditionException;
import com.imooc.service.UserCoinService;
import com.imooc.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Service
public class VideoServiceImpl implements VideoService {
    @Autowired
    private VideoDao videoDao;

    @Autowired
    private UserCoinService userCoinService;

    @Override
    @Transactional
    public void addVideos(Video video) {
        Date now = new Date();
        video.setCreateTime(now);
        video.setUpdateTime(now);
        videoDao.addVideo(video);
        List<VideoTag> videoTagList = video.getVideoTagList();
        for (VideoTag videoTag : videoTagList) {
            videoTag.setCreateTime(now);
            videoTag.setVideoId(video.getId());
        }
        videoDao.batchAddVideoTags(videoTagList);
    }

    @Override
    public PageResult<Video> pageListVideos(Integer size, Integer no, String area) {
        if (size==null || no==null){
            throw new ConditionException("参数异常");
        }
        Map<String,Object> params=new HashMap<>();
        params.put("start",(no-1)*size);
        params.put("limit",size);
        params.put("area",area);
        Integer total = videoDao.pageCountVideos(params);
        List<Video> list = new ArrayList<>();
        if (total>0){
            list=videoDao.pageListVideos(params);
        }
        PageResult<Video> result = new PageResult<>();
        result.setList(list);
        result.setTotal(total);
        return result;
    }

    @Override
    public void viewVideoOnlineBySlices(HttpServletRequest request, HttpServletResponse response, String url) {

    }

    @Override
    public void addVideoLike(Long videoId, Long userId) {
        Video videoById = videoDao.getVideoById(videoId);
        if (videoById==null){
            throw new ConditionException("非法视频");
        }
        VideoLike dbVideoLike = videoDao.getVideoLikeByUserIdAndVideoId(videoId, userId);
        if (dbVideoLike!=null){
            throw new ConditionException("已经赞过");
        }
        VideoLike videoLike = new VideoLike();
        videoLike.setVideoId(videoId);
        videoLike.setUserId(userId);
        videoLike.setCreateTime(new Date());
        videoDao.addVideoLike(videoLike);
    }

    @Override
    public void deleteVideoList(Long videoId, Long userId) {
        Video videoById = videoDao.getVideoById(videoId);
        if (videoById==null){
            throw new ConditionException("非法视频");
        }
        VideoLike dbVideoLike = videoDao.getVideoLikeByUserIdAndVideoId(videoId, userId);
        if (dbVideoLike==null){
            throw new ConditionException("没赞过");
        }
        VideoLike videoLike = new VideoLike();
        videoLike.setVideoId(videoId);
        videoLike.setUserId(userId);
        videoDao.deleteVideoList(videoLike);
    }

    @Override
    public Map<String,Object> getVideoLikes(Long videoId, Long userId) {
        Integer videoLikes = videoDao.getVideoLikes(videoId);
        boolean like= userId != null && videoDao.getVideoLikeByUserIdAndVideoId(videoId, userId) != null;
        Map<String, Object> result = new HashMap<>();
        result.put("count",videoLikes);
        result.put("like",like);
        return result;
    }

    @Override
    public Map<String, Object> getVideoCollections(Long videoId, Long userId) {
        Long count = videoDao.getVideoCollections(videoId);
        VideoCollection videoCollection = videoDao.getVideoCollectionByVideoIdAndUserId(videoId, userId);
        boolean like = videoCollection != null;
        Map<String, Object> result = new HashMap<>();
        result.put("count", count);
        result.put("like", like);
        return result;
    }

    @Override
    @Transactional
    public void addVideoCoins(VideoCoin videoCoin, Long userId) {
        Integer amount = videoCoin.getAmount();
        Long videoId = videoCoin.getVideoId();
        if (videoId==null){
            throw new ConditionException("参数异常");
        }
        Video videoById = videoDao.getVideoById(videoId);
        if (videoById==null){
            throw new ConditionException("非法视频");
        }
        Integer userCoinAmounts = userCoinService.getUserCoinsAmounts(userId);
        userCoinAmounts=userCoinAmounts==null?0:userCoinAmounts;
        if (userCoinAmounts<amount){
            throw new ConditionException("余额不足");
        }
        //查询用户已经投的币
        VideoCoin dbVideoCoin = videoDao.getVideoCoinByVideoIdAndUserId(videoId,userId);
        //更新或增加投币
        if (dbVideoCoin==null){
            videoCoin.setUserId(userId);
            Date now = new Date();
            videoCoin.setCreateTime(now);
            videoCoin.setUpdateTime(now);
            videoDao.addVideoCoins(videoCoin);
        }else{
            videoCoin.setUserId(userId);
            videoCoin.setAmount(dbVideoCoin.getAmount()+videoCoin.getAmount());
            videoCoin.setUpdateTime(new Date());
            videoDao.updateVideoCoins(dbVideoCoin);
        }
        //用户扣减投币
        userCoinService.updateUserCoinAmount(userId,userCoinAmounts-amount);
    }

    @Override
    public Map<String, Object> getVideoCoins(Long videoId, Long userId) {
        Long count = videoDao.getVideoCoinsAmount(videoId);
        VideoCoin dbVideoCoin = videoDao.getVideoCoinByVideoIdAndUserId(videoId, userId);
        boolean like= dbVideoCoin != null;
        Map<String,Object> result=new HashMap<>();
        result.put("count",count);
        result.put("like",like);
        return result;
    }


    @Transactional
    @Override
    public void addVideoCollection(VideoCollection videoCollection, Long userId) {
        Long videoId = videoCollection.getVideoId();
        Long groupId = videoCollection.getGroupId();
        if(videoId == null || groupId == null){
            throw new ConditionException("参数异常！");
        }
        Video video = videoDao.getVideoById(videoId);
        if(video == null){
            throw new ConditionException("非法视频！");
        }
        //删除原有视频收藏
        videoDao.deleteVideoCollection(videoId, userId);
        //添加新的视频收藏
        videoCollection.setUserId(userId);
        videoCollection.setCreateTime(new Date());
        videoDao.addVideoCollection(videoCollection);
    }

    @Override
    public void deleteVideoCollection(Long videoId, Long userId) {
        videoDao.deleteVideoCollection(videoId, userId);
    }


}
