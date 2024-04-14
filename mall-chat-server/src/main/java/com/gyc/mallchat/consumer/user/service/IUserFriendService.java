package com.gyc.mallchat.consumer.user.service;

import com.gyc.mallchat.consumer.common.domain.vo.req.CursorPageBaseReq;
import com.gyc.mallchat.consumer.common.domain.vo.req.PageBaseReq;
import com.gyc.mallchat.consumer.common.domain.vo.res.CursorPageBaseResp;
import com.gyc.mallchat.consumer.common.domain.vo.res.PageBaseResp;
import com.gyc.mallchat.consumer.user.domain.vo.req.FriendApplyReq;
import com.gyc.mallchat.consumer.user.domain.vo.req.FriendApplyResp;
import com.gyc.mallchat.consumer.user.domain.vo.req.FriendApproveReq;
import com.gyc.mallchat.consumer.user.domain.vo.res.FriendCheckResp;
import com.gyc.mallchat.consumer.user.domain.vo.res.FriendResp;
import com.gyc.mallchat.consumer.user.domain.vo.res.FriendUnreadResp;

import java.util.List;

/**
 * <p>
 * 用户联系人表 服务类
 * </p>
 *
 * @author gyc
 * @since 2024-04-13
 */
public interface IUserFriendService {

    /**
     * 游标分页查询好友列表
     * @param uid
     * @param request
     * @return
     */
    CursorPageBaseResp<FriendResp> getFriendList(Long uid, CursorPageBaseReq request);


    FriendCheckResp check(Long uid, List<Long> uids);

    void apply(Long uid, FriendApplyReq request);

    void deleteFriend(Long uid, Long targetUid);

    PageBaseResp<FriendApplyResp> pageApplyFriend(Long uid, PageBaseReq request);

    FriendUnreadResp unread(Long uid);

    void applyApprove(Long uid, FriendApproveReq request);
}
