package com.amlzq.checker.nullness.compatqual;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Identical to {@code @Nullable}, but can only be written at declaration locations. This annotation
 * can be used in Java 7 code; it has no dependency on Java 8 classes.
 *
 * '@checker_framework.manual #nullness-checker Nullness Checker'
 * '@see org.checkerframework.checker.nullness.qual.Nullable'
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface NullableDecl {
}
