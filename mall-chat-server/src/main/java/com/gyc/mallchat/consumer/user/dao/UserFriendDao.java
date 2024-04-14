package com.gyc.mallchat.consumer.user.dao;

import com.gyc.mallchat.consumer.common.domain.vo.req.CursorPageBaseReq;
import com.gyc.mallchat.consumer.common.domain.vo.res.CursorPageBaseResp;
import com.gyc.mallchat.consumer.common.utils.CursorUtils;
import com.gyc.mallchat.consumer.user.domain.entity.UserFriend;
import com.gyc.mallchat.consumer.user.mapper.UserFriendMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户联系人表 服务实现类
 * </p>
 *
 * @author gyc
 * @since 2024-04-13
 */
@Service
public class UserFriendDao extends ServiceImpl<UserFriendMapper, UserFriend>  {

    /**
     * 游标翻页查询
     * @param uid
     * @param request
     * @return
     */
    public CursorPageBaseResp<UserFriend> getFriendPage(Long uid, CursorPageBaseReq request) {
        return CursorUtils.getCursorPageByMysql(
                this,
                request,
                wrapper -> wrapper.eq(UserFriend::getFriendUid, uid)
                , UserFriend::getId);
    }

    public List<UserFriend> check(Long uid, List<Long> uids) {
        return lambdaQuery().eq(UserFriend::getUid, uid)
                .in(UserFriend::getFriendUid, uids)
                .list();
    }

    public List<UserFriend> getFriendByUid(Long uid, Long targetUid) {
        return lambdaQuery().eq(UserFriend::getUid, uid)
                .eq(UserFriend::getFriendUid, targetUid)
                .or()
                .eq(UserFriend::getUid, targetUid)
                .eq(UserFriend::getFriendUid, uid)
                .list();
    }
}
