package com.gyc.mallchat.consumer.user.service.impl;

import cn.hutool.core.thread.NamedThreadFactory;
import cn.hutool.http.HttpUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.gyc.mallchat.consumer.common.domain.vo.res.ApiResult;
import com.gyc.mallchat.consumer.common.thread.GlobalUncaughtExceptionHandler;
import com.gyc.mallchat.consumer.common.utils.JsonUtils;
import com.gyc.mallchat.consumer.user.dao.UserDao;
import com.gyc.mallchat.consumer.user.domain.entity.IpDetail;
import com.gyc.mallchat.consumer.user.domain.entity.IpInfo;
import com.gyc.mallchat.consumer.user.domain.entity.User;
import com.gyc.mallchat.consumer.user.service.IpService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * ClassName: IpServiceImpl
 * Package: com.gyc.mallchat.consumer.user.service.impl
 * Description:
 *
 * @Author gyc
 * @Create 2024/4/12 11:18
 * @Version 1.0
 */
@Service
@Slf4j
public class IpServiceImpl implements IpService, DisposableBean {

    private static final ExecutorService executor = new ThreadPoolExecutor(1, 1,
            0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>(500),
            new NamedThreadFactory("refresh-ipDetail", null, false,
                    GlobalUncaughtExceptionHandler.getInstance()));
    public static final int TRY_COUNT = 3;
    public static final String TAOBAO_GET_IP_URL = "https://ip.taobao.com/outGetIpInfo?ip=%s&accessKey=alibaba-inc";

    @Autowired
    private UserDao userDao;


    @Override
    public void refreshIpDetailAsync(Long id) {
        executor.execute(() -> {
            User user = userDao.getById(id);
            IpInfo ipInfo = user.getIpInfo();
            if (Objects.isNull(ipInfo)) {
                return;
            }
            //如果不为空，就是要进行刷新更新值
            String ip = ipInfo.needRefreshIp();
            if (StringUtils.isBlank(ip)) {
                return;
            }
            //尝试更新ipDetail三次，三次不成功就失败
            IpDetail ipDetail = tryGetIpDetailOrNullThreeTimes(ip);
            if (Objects.nonNull(ipDetail)) {
                ipInfo.refreshIpDetail(ipDetail);
                User update = new User();
                update.setId(id);
                update.setIpInfo(ipInfo);
                userDao.updateById(update);
            }
        });
    }

    private static IpDetail tryGetIpDetailOrNullThreeTimes(String ip) {
        for (int i = 0; i < TRY_COUNT; i++) {
            IpDetail ipDetail = getIpDetailOrNull(ip);
            //不为空，可以直接返回了
            if (Objects.nonNull(ipDetail)) {
                return ipDetail;
            }
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                log.error("tryGetIpDetailOrNullThreeTimes is error ,{}", e.getMessage());
            }
        }
        return null;
    }

    private static IpDetail getIpDetailOrNull(String ip) {
        try {
            String url = String.format(TAOBAO_GET_IP_URL, ip);
            String body = HttpUtil.get(url);
            ApiResult<IpDetail> result = JsonUtils.toObj(body, new TypeReference<ApiResult<IpDetail>>() {
            });
            IpDetail ipDetail =
                    result.getData();
            return ipDetail;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void destroy() throws Exception {
        //使用线程池优雅停机
        executor.shutdown();
        if (!executor.awaitTermination(30, TimeUnit.SECONDS)) {//最多等30秒，处理不完就拉倒
            if (log.isErrorEnabled()) {
                log.error("Timed out while waiting for executor [{}] to terminate", executor);
            }
        }
    }
}
