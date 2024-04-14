package com.gyc.mallchat.consumer.user.service.adapter;

import com.gyc.mallchat.consumer.user.domain.entity.User;
import com.gyc.mallchat.consumer.user.domain.entity.UserApply;
import com.gyc.mallchat.consumer.user.domain.entity.UserFriend;
import com.gyc.mallchat.consumer.user.domain.enums.ApplyReadStatusEnum;
import com.gyc.mallchat.consumer.user.domain.enums.ApplyStatusEnum;
import com.gyc.mallchat.consumer.user.domain.enums.ApplyTypeEnum;
import com.gyc.mallchat.consumer.user.domain.vo.req.FriendApplyReq;
import com.gyc.mallchat.consumer.user.domain.vo.req.FriendApplyResp;
import com.gyc.mallchat.consumer.user.domain.vo.res.FriendResp;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * ClassName: FriendAdpter
 * Package: com.gyc.mallchat.consumer.user.service.adapter
 * Description: 好友类与返回的体之间的适配器
 *
 * @Author gyc
 * @Create 2024/4/13 19:33
 * @Version 1.0
 */
public class FriendAdapter {


    public static List<FriendResp> buildFriendResp(List<UserFriend> friendList, List<User> userList) {
        Map<Long, User> userMap = userList.stream().collect(Collectors.toMap(User::getId, user -> user));
        return friendList.stream().map(userFriend -> {
            FriendResp friendResp = new FriendResp();
            friendResp.setUid(userFriend.getUid());
            User user = userMap.get(userFriend.getFriendUid());
            if (Objects.nonNull(user)) {
                friendResp.setActiveStatus(user.getStatus());
            }
            return friendResp;
        }).collect(Collectors.toList());
    }


    public static UserApply buildFriend(Long uid, FriendApplyReq request) {
        UserApply userApply = new UserApply();
        userApply.setUid(uid);
        userApply.setType(ApplyTypeEnum.ADD_FRIEND.getCode());
        userApply.setTargetId(request.getTargetUid());
        userApply.setMsg(request.getMsg());
        userApply.setStatus(ApplyStatusEnum.WAIT_APPROVAL.getCode());
        userApply.setReadStatus(ApplyReadStatusEnum.UNREAD.getCode());
        return userApply;
    }

    public static List<FriendApplyResp> buildFriendApplyResp(List<UserApply> records) {
        return records.stream().map(userApply -> {
            FriendApplyResp friendApplyResp = new FriendApplyResp();
            friendApplyResp.setUid(userApply.getUid());
            friendApplyResp.setType(userApply.getType());
            friendApplyResp.setApplyId(userApply.getId());
            friendApplyResp.setMsg(userApply.getMsg());
            friendApplyResp.setStatus(userApply.getStatus());
            return friendApplyResp;
        }).collect(Collectors.toList());
    }
}
