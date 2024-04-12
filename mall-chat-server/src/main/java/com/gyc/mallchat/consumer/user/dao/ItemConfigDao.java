package com.gyc.mallchat.consumer.user.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gyc.mallchat.consumer.user.domain.entity.ItemConfig;
import com.gyc.mallchat.consumer.user.mapper.ItemConfigMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 功能物品配置表 服务实现类
 * </p>
 *
 * @author gyc
 * @since 2024-04-10
 */
@Service
public class ItemConfigDao extends ServiceImpl<ItemConfigMapper, ItemConfig> {

    public List<ItemConfig> getByType(Integer type) {
        return lambdaQuery().eq(ItemConfig::getType, type).list();
    }
}
