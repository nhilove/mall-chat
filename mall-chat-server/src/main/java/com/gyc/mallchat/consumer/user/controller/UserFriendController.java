package com.gyc.mallchat.consumer.user.controller;


import com.gyc.mallchat.consumer.common.domain.vo.req.CursorPageBaseReq;
import com.gyc.mallchat.consumer.common.domain.vo.req.PageBaseReq;
import com.gyc.mallchat.consumer.common.domain.vo.res.ApiResult;
import com.gyc.mallchat.consumer.common.domain.vo.res.CursorPageBaseResp;
import com.gyc.mallchat.consumer.common.domain.vo.res.PageBaseResp;
import com.gyc.mallchat.consumer.common.utils.RequestHolder;
import com.gyc.mallchat.consumer.user.domain.vo.req.*;
import com.gyc.mallchat.consumer.user.domain.vo.res.FriendCheckResp;
import com.gyc.mallchat.consumer.user.domain.vo.res.FriendResp;
import com.gyc.mallchat.consumer.user.domain.vo.res.FriendUnreadResp;
import com.gyc.mallchat.consumer.user.service.IUserFriendService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * <p>
 * 用户联系人表 前端控制器
 * </p>
 *
 * @author gyc
 * @since 2024-04-13
 */
@RestController
@RequestMapping( "/capi/user/friend" )
@Api( tags = "联系人接口" )
@Slf4j
public class UserFriendController {

    @Autowired
    private IUserFriendService userFriendService;


    @ApiOperation( "检查是否是好友" )
    @GetMapping( "/check" )
    public ApiResult<FriendCheckResp> check(@Valid FriendCheckReq request) {
        Long uid = RequestHolder.get().getUid();
        return ApiResult.success(userFriendService.check(uid, request.getUids()));
    }

    @ApiOperation( "申请好友" )
    @PostMapping( "/apply" )
    public ApiResult<Void> apply(@Valid @RequestBody FriendApplyReq request) {
        Long uid = RequestHolder.get().getUid();
        userFriendService.apply(uid, request);
        return ApiResult.success();
    }

    @GetMapping("/apply/page")
    @ApiOperation("好友申请列表")
    public ApiResult<PageBaseResp<FriendApplyResp>> page(@Valid PageBaseReq request) {
        Long uid = RequestHolder.get().getUid();
        return ApiResult.success(userFriendService.pageApplyFriend(uid, request));
    }

    @ApiOperation( "删除好友" )
    @DeleteMapping()
    public ApiResult<Void> delete(@Valid @RequestBody FriendDeleteReq request) {
        Long uid = RequestHolder.get().getUid();
        userFriendService.deleteFriend(uid, request.getTargetUid());
        return ApiResult.success();
    }

    @ApiOperation( "申请未读数" )
    @GetMapping( "/apply/unread" )
    public ApiResult<FriendUnreadResp> unReadCount() {
        Long uid = RequestHolder.get().getUid();
        return ApiResult.success(userFriendService.unread(uid));
    }

    @ApiOperation( "审批通过" )
    @PutMapping( "/apply" )
    public ApiResult<Void> apply(@Valid @RequestBody FriendApproveReq request) {
        Long uid = RequestHolder.get().getUid();
        userFriendService.applyApprove(uid, request);
        return ApiResult.success();
    }


    @ApiOperation( "获取联系人列表" )
    @GetMapping( "/page" )
    public ApiResult<CursorPageBaseResp<FriendResp>> getUserInfo(@Valid CursorPageBaseReq request) {
        Long uid = RequestHolder.get().getUid();
        return ApiResult.success(userFriendService.getFriendList(uid, request));
    }
}

