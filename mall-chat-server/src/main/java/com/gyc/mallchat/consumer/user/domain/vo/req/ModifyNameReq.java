package com.gyc.mallchat.consumer.user.domain.vo.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * ClassName: ModifyNameReq
 * Package: com.gyc.mallchat.consumer.user.domain.vo.req
 * Description:
 *
 * @Author gyc
 * @Create 2024/4/11 10:24
 * @Version 1.0
 */
@Data
public class ModifyNameReq {

    @ApiModelProperty( value = "新的用户名" )
    @Length( max = 6, message = "名字太长了，我记不住嗷！" )
    @NotNull
    private String name;
}
