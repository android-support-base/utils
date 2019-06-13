package com.amlzq.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 发布检查
 */
@Documented // 元素包含到 Javadoc
@Retention(RetentionPolicy.SOURCE) // 源码阶段保留
@Target({ElementType.METHOD, // 方法
        ElementType.ANNOTATION_TYPE, // 注解
        ElementType.CONSTRUCTOR, // 构造方法
        ElementType.PARAMETER, // 方法内的参数
        ElementType.FIELD, // 属性
        ElementType.TYPE, // 类型，比如类、接口、枚举
        ElementType.PACKAGE, // 包
        ElementType.LOCAL_VARIABLE // 局部变量
})
public @interface ReleaseCheck {
}