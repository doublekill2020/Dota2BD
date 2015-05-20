/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2015 Umeng, Inc
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package cn.edu.mydotabuff.ui;

import android.content.Context;

import com.umeng.comm.core.beans.CommUser;
import com.umeng.comm.core.beans.Source;
import com.umeng.comm.core.beans.CommUser.Gender;
import com.umeng.comm.core.login.LoginListener;
import com.umeng.comm.core.login.Loginable;
import com.umeng.comm.core.utils.Log;

import java.util.Map;
import java.util.Random;

/**
 * 自定义实现登录系统
 * 
 * @author mrsimple
 */
public class MyLoginImpl implements Loginable {

    private boolean isLogin = false;
    private Map<String, Object> info;
    public MyLoginImpl(Map<String, Object> info){
        this.info = info;
    }

    @Override
    public void login(Context context, LoginListener listener) {
        Log.d("", "### 使用自己的账户系统登录,然后将标识用户唯一性的id和source传递给社区SDK ");

        //
        CommUser loginedUser = new CommUser(info.get("uid")+"");
        loginedUser.name = info.get("screen_name")+"";
        // 登录的来源
        loginedUser.source = Source.QQ;
        loginedUser.gender = Gender.MALE;
        isLogin = true;
        // 登录完成回调给社区SDK
        listener.onComplete(200, loginedUser);
    }

    @Override
    public void logout(Context context, LoginListener listener) {
        Log.d("", "### 注销登录 ");
        isLogin = false;
        listener.onComplete(200, null);
    }

    @Override
    public boolean isLogined(Context context) {
        Log.d("", "### 使用自己的账户系统判断是否已经登录");
        return isLogin;
    }

}
