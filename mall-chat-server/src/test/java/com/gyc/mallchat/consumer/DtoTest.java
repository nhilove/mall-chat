package com.gyc.mallchat.consumer;

import com.gyc.mallchat.consumer.user.dao.UserDao;
import com.gyc.mallchat.consumer.user.domain.entity.User;
import com.gyc.mallchat.consumer.user.service.LoginService;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpQrCodeTicket;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * ClassName: DtoTest
 * Package: com.gyc.mallchat.common
 * Description:
 *
 * @Author gyc
 * @Create 2024/4/8 22:10
 * @Version 1.0
 */
@SpringBootTest
@RunWith( SpringRunner.class )
public class DtoTest {

    @Autowired
    private UserDao userDao;

    @Autowired
    private WxMpService wxMpService;


    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void redis() {
        redisTemplate.opsForValue().set("name", "卷心菜");
        String name = (String) redisTemplate.opsForValue().get("name");
        System.out.println(name); //卷心菜
    }

    @Autowired
    private LoginService loginService;

    @Test
    public void token() {
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1aWQiOjExMDAyLCJjcmVhdGVUaW1lIjoxNzEyNzE2OTY5fQ.JU_O7gaAPYT-m9Dz-_rIPGDVw0Wocj7bQLUPsCgX54k";
        Long validUid = loginService.getValidUid(token);
        System.out.println(validUid);
    }


    @Test
    public void user() {
//        User byId = userDao.getById(1);
        User insert = new User();
        insert.setName("11");
        insert.setOpenId("123");
        userDao.save(insert);
    }

    @Test
    public void wx() throws WxErrorException {
        WxMpQrCodeTicket qrCodeTicket = wxMpService.getQrcodeService().qrCodeCreateTmpTicket(1, 10000);
        String url = qrCodeTicket.getUrl();
        System.out.println(url);
    }

}
