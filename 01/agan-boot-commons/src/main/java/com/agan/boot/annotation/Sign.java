package com.agan.boot.annotation;

import java.lang.annotation.*;
/**
 * @author 阿甘
 * @see https://study.163.com/course/courseMain.htm?courseId=1004348001&share=2&shareId=1016671292
 * @version 1.0
 * 注：如有任何疑问欢迎加入QQ群977438372 进行讨论
 */
@Target(value = {
        ElementType.TYPE,
        ElementType.METHOD
})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Sign {
}
