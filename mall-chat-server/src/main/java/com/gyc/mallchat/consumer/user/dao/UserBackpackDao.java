package com.gyc.mallchat.consumer.user.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gyc.mallchat.consumer.common.domain.enums.YesOrNoEnum;
import com.gyc.mallchat.consumer.user.domain.entity.UserBackpack;
import com.gyc.mallchat.consumer.user.mapper.UserBackpackMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户背包表 服务实现类
 * </p>
 *
 * @author gyc
 * @since 2024-04-10
 */
@Service
public class UserBackpackDao extends ServiceImpl<UserBackpackMapper, UserBackpack> {

    @Autowired
    private ItemConfigDao itemConfigDao;


    /**
     * 某个用户的改名卡有效次数
     *
     * @param uid
     * @param itemId
     * @return
     */
    public Integer getChangeNameByValidCount(Long uid, Long itemId) {
        return lambdaQuery().eq(UserBackpack::getUid, uid)
                .eq(UserBackpack::getItemId, itemId)
                .eq(UserBackpack::getStatus, YesOrNoEnum.NO.getStatus())
                .count();
    }

    /**
     * 某个用户的徽章列表(拥有和未拥有)
     *
     * @param uid
     * @param itemIds
     * @return
     */
    public List<UserBackpack> getBadgesByUserId(Long uid, List<Long> itemIds) {
        return lambdaQuery().eq(UserBackpack::getUid, uid)
                .eq(UserBackpack::getStatus, YesOrNoEnum.NO.getStatus())
                .in(UserBackpack::getItemId, itemIds)
                .list();
    }

    public UserBackpack getByIdempotent(String idempotent) {
        return lambdaQuery().eq(UserBackpack::getIdempotent, idempotent).one();
    }

}
