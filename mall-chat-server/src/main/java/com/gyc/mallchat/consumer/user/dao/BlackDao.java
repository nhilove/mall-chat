package com.gyc.mallchat.consumer.user.dao;

import com.gyc.mallchat.consumer.user.domain.entity.Black;
import com.gyc.mallchat.consumer.user.mapper.BlackMapper;
import com.gyc.mallchat.consumer.user.service.IBlackService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 黑名单 服务实现类
 * </p>
 *
 * @author gyc
 * @since 2024-04-12
 */
@Service
public class BlackDao extends ServiceImpl<BlackMapper, Black> {

}
