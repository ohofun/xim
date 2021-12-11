package io.github.ohofun.dim.starter.annotation;

import java.lang.annotation.*;

/**
 * @author ohofun
 * @version 1.0
 * @date 2021/12/11 20:24
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.METHOD})
public @interface WebSocketFilter {
}
