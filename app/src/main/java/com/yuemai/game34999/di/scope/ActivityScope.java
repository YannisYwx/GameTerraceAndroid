package com.yuemai.game34999.di.scope;

import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/12/11  19:49
 * @email : 923080261@qq.com
 * @description :activity局部作用域
 */
@Scope
@Retention(RUNTIME)
public @interface ActivityScope {
}
