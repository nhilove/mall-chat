package com.gyc.mallchat.consumer.user.service.impl;

import com.gyc.mallchat.consumer.common.event.UserBlackEvent;
import com.gyc.mallchat.consumer.common.event.UserRegisterEvent;
import com.gyc.mallchat.consumer.common.exception.BusinessException;
import com.gyc.mallchat.consumer.common.utils.AssertUtil;
import com.gyc.mallchat.consumer.user.dao.BlackDao;
import com.gyc.mallchat.consumer.user.dao.ItemConfigDao;
import com.gyc.mallchat.consumer.user.dao.UserBackpackDao;
import com.gyc.mallchat.consumer.user.dao.UserDao;
import com.gyc.mallchat.consumer.user.domain.entity.Black;
import com.gyc.mallchat.consumer.user.domain.entity.ItemConfig;
import com.gyc.mallchat.consumer.user.domain.entity.User;
import com.gyc.mallchat.consumer.user.domain.entity.UserBackpack;
import com.gyc.mallchat.consumer.user.domain.enums.BlackTypeEnum;
import com.gyc.mallchat.consumer.user.domain.enums.ItemEnum;
import com.gyc.mallchat.consumer.user.domain.vo.res.BadgeResp;
import com.gyc.mallchat.consumer.user.domain.vo.res.UserInfoResp;
import com.gyc.mallchat.consumer.user.service.IRoleService;
import com.gyc.mallchat.consumer.user.service.UserService;
import com.gyc.mallchat.consumer.user.service.adapter.UserAdapter;
import com.gyc.mallchat.consumer.user.domain.enums.RoleEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * ClassName: UserServiceImpl
 * Package: com.gyc.mallchat.consumer.user.service.impl
 * Description:
 *
 * @Author gyc
 * @Create 2024/4/9 17:06
 * @Version 1.0
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private UserBackpackDao userBackpackDao;
    @Autowired
    private ItemConfigDao itemConfigDao;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    private IRoleService iRoleService;

    @Autowired
    private BlackDao blackDao;

    @Override
    public User getUserByOpenId(String openId) {
        return userDao.getUserByOpenId(openId);
    }

    @Override
    @Transactional
    public Long register(User insert) {
        userDao.save(insert);
        // 用户注册事件，监听用户注册事件，用户注册要给他发送改名卡
        applicationEventPublisher.publishEvent(new UserRegisterEvent(this, insert));
        return insert.getId();
    }

    @Override
    public UserInfoResp getUserInfo(Long uid) {
        User user = userDao.getById(uid);
        //查询改名卡的次数
        Integer count = getChangeNameByValidCount(uid);
        return UserAdapter.buildUserInfoResp(user, count);
    }

    @Override
    public void modifyName(Long uid, String name) {
        User user = userDao.getUserByName(name);
        if (Objects.nonNull(user)) {
            //名字不唯一
            throw new BusinessException("名字重复");
        }
        Integer changeNameByValidCount = getChangeNameByValidCount(uid);
        if (changeNameByValidCount < 1) {
            //没有改名卡
            throw new BusinessException("没有改名卡");
        }
        user.setName(name);
        userDao.updateById(user);
    }

    /**
     * 查询当前用户的徽章列表
     *
     * @param uid
     * @return
     */
    @Override
    public List<BadgeResp> getBadges(Long uid) {
        //用户佩戴上的徽章
        User user = userDao.getById(uid);
        List<ItemConfig> itemConfigList = itemConfigDao.list();
        List<Long> itemIds = itemConfigList.stream().map(ItemConfig::getId).collect(Collectors.toList());
        List<UserBackpack> badgesByUserId = userBackpackDao.getBadgesByUserId(uid, itemIds);
        return UserAdapter.buildUserBackpack(badgesByUserId);
    }

    @Override
    public void wearBadge(Long uid, Long itemId) {
        User user = userDao.getById(uid);
        user.setItemId(itemId);
        userDao.updateById(user);
    }

    @Override
    @Transactional( rollbackFor = Exception.class )
    public void black(Long uid, Long blackId) {
        boolean admin = iRoleService.hasPower(uid, RoleEnum.ADMIN);
        AssertUtil.isTrue(admin, "没有此权限");
        Black user = new Black();
        user.setType(BlackTypeEnum.UID.getType());
        user.setTarget(blackId.toString());
        blackDao.save(user);
        //获取用户的ip,拉黑ip
        User blackUser = userDao.getById(blackId);
        blackIp(Optional.ofNullable(blackUser.getIpInfo().getCreateIp()).orElse(null));
        blackIp(Optional.ofNullable(blackUser.getIpInfo().getUpdateIp()).orElse(null));
        //发送事件，给所有用户
        applicationEventPublisher.publishEvent(new UserBlackEvent(this, blackUser));
    }

    /**
     * 拉黑ip
     *
     * @param ip
     */
    private void blackIp(String ip) {
        if (StringUtils.isBlank(ip)) {
            return;
        }
        try {
            Black user = new Black();
            user.setType(BlackTypeEnum.IP.getType());
            user.setTarget(ip);
            blackDao.save(user);
        } catch (Exception e) {

        }
    }

    /**
     * 剩余改名的次数
     *
     * @param uid
     * @return
     */
    private Integer getChangeNameByValidCount(Long uid) {
        Integer count = userBackpackDao.getChangeNameByValidCount(uid, ItemEnum.MODIFY_NAME_CARD.getId());
        return count;
    }

}
