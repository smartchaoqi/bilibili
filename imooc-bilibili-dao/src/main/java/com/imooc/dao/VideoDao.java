package com.imooc.dao;

import com.imooc.domain.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface VideoDao {
    Integer addVideo(Video video);

    Integer batchAddVideoTags(List<VideoTag> videoTagList);

    Integer pageCountVideos(Map<String,Object> params);

    List<Video> pageListVideos(Map<String, Object> params);

    void addVideoLike(VideoLike videoLike);

    void deleteVideoList(VideoLike videoLike);

    VideoLike getVideoLikeByUserIdAndVideoId(@Param("videoId") Long videoId, @Param("userId") Long userId);

    Integer getVideoLikes(@Param("videoId") Long videoId);

    Video getVideoById(@Param("videoId") Long videoId);

    Long getVideoCollections(Long videoId);

    VideoCollection getVideoCollectionByVideoIdAndUserId(@Param("videoId") Long videoId,
                                                         @Param("userId") Long userId);

    Integer deleteVideoCollection(@Param("videoId") Long videoId,
                                  @Param("userId") Long userId);

    Integer addVideoCollection(VideoCollection videoCollection);

    void addVideoCoins(VideoCoin videoCoin);

    VideoCoin getVideoCoinByVideoIdAndUserId(@Param("videoId") Long videoId, @Param("userId") Long userId);

    void updateVideoCoins(VideoCoin dbVideoCoin);

    Long getVideoCoinsAmount(@Param("videoId") Long videoId);

    void addVideoComment(VideoComment videoComment);

    Integer pageCountVideoComments(Map<String, Object> params);

    List<VideoComment> pageListVideoComments(Map<String, Object> params);

    List<VideoComment> batchGetVideoCommentsByRootId(@Param("rootIds") List<Long> rootIds);

    Video getVideoDetail(@Param("videoId") Long videoId);

    VideoView getVideoView(Map<String, Object> params);

    void addVideoView(VideoView videoView);

    Integer getVideoViewCounts(@Param("videoId") Long videoId);

    List<UserPreference> getAllUserPreference(@Param("userId") Long userId);

    List<Video> batchGetVideosByIds(List<Long> itemIds);
}
