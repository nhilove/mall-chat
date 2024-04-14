package com.gyc.mallchat.consumer.user.domain.vo.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import java.util.List;

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
public class FriendCheckReq {

    @NotEmpty
    @Max( 50 )
    @ApiModelProperty( "好友列表集合" )
    private List<Long> uids;
}
