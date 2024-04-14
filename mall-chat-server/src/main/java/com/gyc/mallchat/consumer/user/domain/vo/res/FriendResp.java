package com.gyc.mallchat.consumer.user.domain.vo.res;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ClassName: FriendResp
 * Package: com.gyc.mallchat.consumer.user.domain.vo.res
 * Description: 好友返回体
 *
 * @Author gyc
 * @Create 2024/4/13 19:26
 * @Version 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class  FriendResp {

    @ApiModelProperty("好友uid")
    private Long uid;

    @ApiModelProperty("好友状态")
    private Integer activeStatus;
}
