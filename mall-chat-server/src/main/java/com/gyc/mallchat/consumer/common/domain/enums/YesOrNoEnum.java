package com.gyc.mallchat.consumer.common.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * ClassName: YesOrNoEnum
 * Package: com.gyc.mallchat.consumer.common.domain.enums
 * Description:
 *
 * @Author gyc
 * @Create 2024/4/10 21:43
 * @Version 1.0
 */
@AllArgsConstructor
@Getter
public enum YesOrNoEnum {

    NO(0, "否"),
    YES(1, "是"),
    ;

    private final Integer status;
    private final String des;

}
