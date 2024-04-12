package com.gyc.mallchat.consumer.user.domain.vo.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * ClassName: BadgeResp
 * Package: com.gyc.mallchat.consumer.user.domain.vo.res
 * Description:
 *
 * @Author gyc
 * @Create 2024/4/11 15:45
 * @Version 1.0
 */
@Data
@ApiModel("徽章信息")
public class BadgeResp {

    @ApiModelProperty(value = "徽章id")
    private Long id;

    @ApiModelProperty(value = "徽章图标")
    private String img;

    @ApiModelProperty(value = "徽章描述")
    private String describe;

    @ApiModelProperty(value = "是否拥有 0否 1是")
    private Integer obtain;

    @ApiModelProperty(value = "是否佩戴  0否 1是")
    private Integer wearing;
}

