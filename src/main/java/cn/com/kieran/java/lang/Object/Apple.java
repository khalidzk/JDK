/*
 * Copyright (c) 2001-2018 GuaHao.com Corporation Limited. All rights reserved.
 * This software is the confidential and proprietary information of GuaHao Company.
 * ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with GuaHao.com.
 */
package cn.com.kieran.java.lang.Object;

/**
 * TODO
 *
 * @author zhengkai
 * @version V1.0
 * @since 2018-07-28 15:09
 */
public class Apple implements Cloneable {

    @Override
    protected Apple clone() throws CloneNotSupportedException {
        return (Apple) super.clone();
    }

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}