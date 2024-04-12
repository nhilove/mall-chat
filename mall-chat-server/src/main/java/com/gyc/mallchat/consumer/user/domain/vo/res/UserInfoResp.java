package com.gyc.mallchat.consumer.user.domain.vo.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * ClassName: UserInfoResp
 * Package: com.gyc.mallchat.consumer.common.domain.vo.res
 * Description:
 *
 * @Author gyc
 * @Create 2024/4/10 16:16
 * @Version 1.0
 */
@Data
@ApiModel( "用户返回信息模型" )
public class UserInfoResp {
    /**
     * 用户id
     */
    @ApiModelProperty( value = "id" )
    private Long id;

    /**
     * 用户昵称
     */
    @ApiModelProperty( value = "用户昵称" )
    private String name;

    /**
     * 用户头像
     */
    @ApiModelProperty( value = "用户头像" )
    private String avatar;

    /**
     * 性别 1为男性，2为女性
     */
    @ApiModelProperty( value = "性别" )
    private Integer sex;

    /**
     * 改名卡次数
     */
    @ApiModelProperty( value = "性别改名卡次数" )
    private Integer modifyChangeName;
}
