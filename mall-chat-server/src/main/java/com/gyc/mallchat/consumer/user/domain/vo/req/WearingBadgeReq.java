package com.gyc.mallchat.consumer.user.domain.vo.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * ClassName: WearingBadgeReq
 * Package: com.gyc.mallchat.consumer.user.domain.vo.req
 * Description:
 *
 * @Author gyc
 * @Create 2024/4/11 16:33
 * @Version 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WearingBadgeReq {

    @NotNull
    @ApiModelProperty( "徽章id" )
    private Long itemId;

}

