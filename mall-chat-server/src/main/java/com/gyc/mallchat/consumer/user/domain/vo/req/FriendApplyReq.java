package com.gyc.mallchat.consumer.user.domain.vo.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * ClassName: FriendCheckReq
 * Package: com.gyc.mallchat.consumer.user.domain.vo.req
 * Description:
 *
 * @Author gyc
 * @Create 2024/4/14 15:09
 * @Version 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FriendApplyReq {

    @NotBlank
    @ApiModelProperty( "申请备注" )
    private String msg;

    @NotNull
    @ApiModelProperty( "申请目标uid" )
    private Long targetUid;


}
