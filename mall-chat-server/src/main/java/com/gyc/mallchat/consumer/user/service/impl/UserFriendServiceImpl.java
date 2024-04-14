package com.gyc.mallchat.consumer.user.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gyc.mallchat.consumer.common.annotation.RedissonLock;
import com.gyc.mallchat.consumer.common.domain.vo.req.CursorPageBaseReq;
import com.gyc.mallchat.consumer.common.domain.vo.req.PageBaseReq;
import com.gyc.mallchat.consumer.common.domain.vo.res.CursorPageBaseResp;
import com.gyc.mallchat.consumer.common.domain.vo.res.PageBaseResp;
import com.gyc.mallchat.consumer.common.event.UserApplyEvent;
import com.gyc.mallchat.consumer.common.utils.AssertUtil;
import com.gyc.mallchat.consumer.user.dao.UserApplyDao;
import com.gyc.mallchat.consumer.user.dao.UserDao;
import com.gyc.mallchat.consumer.user.dao.UserFriendDao;
import com.gyc.mallchat.consumer.user.domain.entity.User;
import com.gyc.mallchat.consumer.user.domain.entity.UserApply;
import com.gyc.mallchat.consumer.user.domain.entity.UserFriend;
import com.gyc.mallchat.consumer.user.domain.enums.ApplyStatusEnum;
import com.gyc.mallchat.consumer.user.domain.vo.req.FriendApplyReq;
import com.gyc.mallchat.consumer.user.domain.vo.req.FriendApplyResp;
import com.gyc.mallchat.consumer.user.domain.vo.req.FriendApproveReq;
import com.gyc.mallchat.consumer.user.domain.vo.res.FriendCheckResp;
import com.gyc.mallchat.consumer.user.domain.vo.res.FriendResp;
import com.gyc.mallchat.consumer.user.domain.vo.res.FriendUnreadResp;
import com.gyc.mallchat.consumer.user.service.IUserFriendService;
import com.gyc.mallchat.consumer.user.service.adapter.FriendAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * ClassName: UserFriendServiceImpl
 * Package: com.gyc.mallchat.consumer.user.service.impl
 * Description:
 *
 * @Author gyc
 * @Create 2024/4/13 19:20
 * @Version 1.0
 */
@Service
@Slf4j
public class UserFriendServiceImpl implements IUserFriendService {

    @Autowired
    private UserFriendDao userFriendDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserApplyDao userApplyDao;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    /**
     * 游标分页查询好友列表
     *
     * @param uid
     * @param request
     * @return
     */
    @Override
    public CursorPageBaseResp<FriendResp> getFriendList(Long uid, CursorPageBaseReq request) {
        CursorPageBaseResp<UserFriend> friendPage = userFriendDao.getFriendPage(uid, request);
        //没有好友，返回空的游标分页集合
        if (CollectionUtil.isEmpty(friendPage.getList())) {
            log.info("No friend");
            return CursorPageBaseResp.empty();
        }
        //拿到所有好友的id
        List<Long> friendUids = friendPage.getList().stream()
                .map(UserFriend::getFriendUid)
                .collect(Collectors.toList());
        //查询好友信息
        List<User> userList = userDao.getUserFriendList(friendUids);
        //构造返回体
        return CursorPageBaseResp.init(friendPage, FriendAdapter.buildFriendResp(friendPage.getList(), userList));
    }

    @Override
    public FriendCheckResp check(Long uid, List<Long> uids) {
        List<UserFriend> userFriends = userFriendDao.check(uid, uids);
        Set<Long> friendUidSet = userFriends.stream().map(UserFriend::getUid).collect(Collectors.toSet());
        List<FriendCheckResp.FriendCheck> list = uids.stream().map(friendUid -> {
            FriendCheckResp.FriendCheck friendCheck = new FriendCheckResp.FriendCheck();
            friendCheck.setUid(friendUid);
            friendCheck.setIsFriend(friendUidSet.contains(friendUid));
            return friendCheck;
        }).collect(Collectors.toList());
        return new FriendCheckResp(list);
    }


    @Override
    public void deleteFriend(Long uid, Long targetUid) {
        //查询是否是好友
        List<UserFriend> userFriend = userFriendDao.getFriendByUid(uid, targetUid);
        if (CollectionUtil.isEmpty(userFriend)) {
            log.info("你们灭有好友关系");
            return;
        }
        //两边都删除
        List<Long> friendRecords = userFriend.stream().map(UserFriend::getUid).collect(Collectors.toList());
        userFriendDao.removeByIds(friendRecords);
        //todo 禁用房间（删除房间关系）
    }

    @Override
    @RedissonLock( key = "#uid" )
    @Transactional( rollbackFor = Exception.class )
    public void applyApprove(Long uid, FriendApproveReq request) {
        //是否有好友申请记录
        UserApply userApply = userApplyDao.getById(request.getApplyId());
        AssertUtil.isNotEmpty(userApply, "不存在好友申请记录");
        AssertUtil.equal(userApply.getTargetId(), uid, "不存在申请记录");
        AssertUtil.equal(userApply.getStatus(), ApplyStatusEnum.AGREE.getCode(), "已同意好友申请");
        //修改用户申请表中的状态
        userApplyDao.agree(request.getApplyId());
        //创建双方好友关系
        createFriendTwo(uid, request.getApplyId());
        //todo 创建聊天室 （单聊）

        //todo 发送已同意消息
    }

    /**
     * 用户好友关系表中要插入两条数据
     *
     * @param uid
     * @param applyId
     */
    private void createFriendTwo(Long uid, Long applyId) {
        UserFriend userFriend1 = new UserFriend();
        userFriend1.setUid(uid);
        userFriend1.setFriendUid(applyId);
        UserFriend userFriend2 = new UserFriend();
        userFriend2.setUid(applyId);
        userFriend2.setFriendUid(uid);
        userFriendDao.saveBatch(Arrays.asList(userFriend1, userFriend2));
    }

    @Override
    public FriendUnreadResp unread(Long uid) {
        Integer unreadCount = userApplyDao.getUnreadCount(uid);
        return new FriendUnreadResp(unreadCount);
    }

    /**
     * 分页查询好友申请列表
     *
     * @param uid
     * @param request
     * @return
     */
    @Override
    public PageBaseResp<FriendApplyResp> pageApplyFriend(Long uid, PageBaseReq request) {
        IPage<UserApply> userApplyIPage = userApplyDao.friendApplyPage(uid, request.plusPage());
        //没有哈有申请列表，返回空分页集合
        if (CollectionUtil.isEmpty(userApplyIPage.getRecords())) {
            return PageBaseResp.empty();
        }
        //将好友申请列表标记为已读
        readApples(uid, userApplyIPage);
        //构造分页返回体
        return PageBaseResp.init(userApplyIPage, FriendAdapter.buildFriendApplyResp(userApplyIPage.getRecords()));
    }


    private void readApples(Long uid, IPage<UserApply> userApplyIPage) {
        List<Long> idList = userApplyIPage.getRecords().stream().map(UserApply::getUid)
                .collect(Collectors.toList());
        userApplyDao.readApples(uid, idList);
    }


    @Override
    @RedissonLock(key = "#uid")
    public void apply(Long uid, FriendApplyReq request) {
        //我们需要检查以下是否已经是好友
        List<UserFriend> userFriend = userFriendDao.getFriendByUid(uid, request.getTargetUid());
        if (CollUtil.isNotEmpty(userFriend)) {
            AssertUtil.isEmpty(userFriend, "你们已经是好友了");
            return;
        }
        //是否持申请记录（自己的），申请过不用再申请
        UserApply selfApplying = userApplyDao.getFriendApplying(uid, request.getTargetUid());
        if (Objects.nonNull(selfApplying)) {
            log.info("已有好友申请记录,uid:{}, targetId:{}", uid, request.getTargetUid());
            return;
        }
        //是否持申请记录（别人的，目标是自己），申请过不用再申请
        UserApply friendApplying = userApplyDao.getFriendApplying(request.getTargetUid(), uid);
        if (Objects.nonNull(friendApplying)) {
            ((IUserFriendService) AopContext.currentProxy()).applyApprove(uid, new FriendApproveReq(friendApplying.getId()));
            return;
        }
        //申请入库
        UserApply insert = FriendAdapter.buildFriend(uid, request);
        userApplyDao.save(insert);
        //todo 发送通知事件
        applicationEventPublisher.publishEvent(new UserApplyEvent(this,insert));
    }


}
