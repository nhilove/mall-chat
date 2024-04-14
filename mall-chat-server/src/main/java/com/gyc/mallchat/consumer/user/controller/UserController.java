package com.gyc.mallchat.consumer.user.controller;


import com.gyc.mallchat.consumer.common.domain.vo.res.ApiResult;
import com.gyc.mallchat.consumer.common.utils.RequestHolder;
import com.gyc.mallchat.consumer.user.domain.vo.req.BlackReq;
import com.gyc.mallchat.consumer.user.domain.vo.req.ModifyNameReq;
import com.gyc.mallchat.consumer.user.domain.vo.req.WearingBadgeReq;
import com.gyc.mallchat.consumer.user.domain.vo.res.BadgeResp;
import com.gyc.mallchat.consumer.user.domain.vo.res.UserInfoResp;
import com.gyc.mallchat.consumer.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author gyc
 * @since 2024-04-08
 */
@RestController
@RequestMapping( "/capi/user" )
@Api( tags = "用户接口" )
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation( "获取用户信息" )
    @GetMapping( "/userInfo" )
    public ApiResult<UserInfoResp> getUserInfo() {
        return ApiResult.success(userService.getUserInfo(RequestHolder.get().getUid()));
    }

    @ApiOperation( "修改用户名" )
    @PutMapping( "/modifyName" )
    public ApiResult<Void> modifyName(@Valid @RequestBody ModifyNameReq modifyNameReq) {
        userService.modifyName(RequestHolder.get().getUid(), modifyNameReq.getName());
        return ApiResult.success();
    }

    @ApiOperation( "可选徽章预览" )
    @GetMapping( "/badges" )
    public ApiResult<List<BadgeResp>> badges() {
        return ApiResult.success(userService.getBadges(RequestHolder.get().getUid()));
    }

    @ApiOperation( "佩戴徽章" )
    @PutMapping( "/badge" )
    public ApiResult<Void> wearBadge(@Valid @RequestBody WearingBadgeReq wearingBadgeReq) {
        userService.wearBadge(RequestHolder.get().getUid(), wearingBadgeReq.getItemId());
        return ApiResult.success();
    }

    @ApiOperation( "拉黑用户" )
    @PutMapping( "/black" )
    public ApiResult<Void> black(@Valid @RequestBody BlackReq blackReq) {
        userService.black(RequestHolder.get().getUid(), blackReq.getUid());
        return ApiResult.success();
    }




}

