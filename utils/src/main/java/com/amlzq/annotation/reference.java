package com.amlzq.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 参考链接
 */
@Documented // 元素包含到 Javadoc
@Retention(RetentionPolicy.SOURCE) // 源码阶段保留
@Target({ElementType.METHOD, // 方法
})
public @interface reference {
}