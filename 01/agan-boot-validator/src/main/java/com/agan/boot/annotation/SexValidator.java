package com.agan.boot.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 阿甘
 * @see 课程地址：https://study.163.com/course/courseMain.htm?courseId=1004348001&share=2&shareId=1016671292
 * @version 1.0
 * 注：如有任何疑问欢迎加入QQ群977438372 进行讨论
 */
public class SexValidator implements ConstraintValidator<Sex, Byte> {



    @Override
    public void initialize(Sex constraintAnnotation) {

    }

    /**
     *
     * 校验的实现逻辑
     */
    @Override
    public boolean isValid(Byte value, ConstraintValidatorContext context) {
        if ( value ==0 || value == 1 ) {
            return true;
        }else{
            return false;
        }
    }
}
