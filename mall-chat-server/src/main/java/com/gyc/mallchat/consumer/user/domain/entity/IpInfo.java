package com.gyc.mallchat.consumer.user.domain.entity;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;

/**
 * <p>
 * 用户ip信息
 * </p>
 *
 * @since 2023-04-10
 */
@Data
public class IpInfo implements Serializable {

    private static final long serialVersionUID = 1L;
    //注册时的ip
    private String createIp;
    //注册时的ip详情
    private IpDetail createIpDetail;
    //最新登录的ip
    private String updateIp;
    //最新登录的ip详情
    private IpDetail updateIpDetail;


    public void refreshIp(String ip) {
        if (ip == null) {
            return;
        }
        if (StringUtils.isBlank(createIp)) {
            createIp = ip;
        }
        updateIp = ip;
    }

    public String needRefreshIp() {
        //判断updateIpDetail中的ip和要更新的ip是否相同
        boolean noNeedRefresh = Optional.ofNullable(updateIpDetail)
                .map(IpDetail::getIp)
                .filter(ip -> Objects.equals(ip, updateIp))
                .isPresent();
        return noNeedRefresh ? null : updateIp;
    }

    public void refreshIpDetail(IpDetail ipDetail) {
        if (Objects.equals(createIp, ipDetail.getIp())) {
            createIpDetail = ipDetail;
        }
        if (Objects.equals(updateIp, ipDetail.getIp())) {
            updateIpDetail = ipDetail;
        }

    }
}