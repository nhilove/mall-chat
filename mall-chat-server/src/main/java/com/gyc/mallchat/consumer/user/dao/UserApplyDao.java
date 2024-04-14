package com.gyc.mallchat.consumer.user.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gyc.mallchat.consumer.user.domain.entity.UserApply;
import com.gyc.mallchat.consumer.user.domain.enums.ApplyReadStatusEnum;
import com.gyc.mallchat.consumer.user.domain.enums.ApplyStatusEnum;
import com.gyc.mallchat.consumer.user.domain.enums.ApplyTypeEnum;
import com.gyc.mallchat.consumer.user.mapper.UserApplyMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户申请表 服务实现类
 * </p>
 *
 * @author gyc
 * @since 2024-04-13
 */
@Service
public class UserApplyDao extends ServiceImpl<UserApplyMapper, UserApply> {

    public void applyTwo(Long uid, String msg, Long targetUid) {

    }

    public UserApply getFriendApplying(Long uid, Long targetUid) {
        return lambdaQuery().eq(UserApply::getUid, uid)
                .eq(UserApply::getTargetId, targetUid)
                .eq(UserApply::getStatus, ApplyStatusEnum.WAIT_APPROVAL)
                .eq(UserApply::getType, ApplyTypeEnum.ADD_FRIEND.getCode())
                .one();

    }

    public IPage<UserApply> friendApplyPage(Long uid, Page plusPage) {
        return lambdaQuery()
                .eq(UserApply::getUid, uid)
                .eq(UserApply::getType, ApplyTypeEnum.ADD_FRIEND.getCode())
                .orderByDesc(UserApply::getCreateTime)
                .page(plusPage);
    }

    public void readApples(Long uid, List<Long> idList) {
        //把未读状态的信息设置为已读状态
        lambdaUpdate()
                .eq(UserApply::getTargetId, uid)
                .eq(UserApply::getReadStatus, ApplyReadStatusEnum.UNREAD.getCode())
                .in(UserApply::getId, idList)
                .set(UserApply::getReadStatus, ApplyReadStatusEnum.READ.getCode())
                .update();
    }

    public Integer getUnreadCount(Long targetId) {
        return lambdaQuery()
                .eq(UserApply::getTargetId, targetId)
                .eq(UserApply::getReadStatus, ApplyReadStatusEnum.UNREAD.getCode())
                .count();
    }

    public void agree(Long applyId) {
        lambdaUpdate().eq(UserApply::getId,applyId)
                .set(UserApply::getStatus,ApplyStatusEnum.AGREE.getCode())
                .update();
    }
}
