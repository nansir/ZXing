package com.sir.app.test.common;

import com.sir.library.base.BaseApplication;
import com.sir.library.zxing.utils.ZXingLibrary;

/**
 * Created by zhuyinan on 2018/3/7.
 * Contact by 445181052@qq.com
 */
public class MyApplication extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        ZXingLibrary.initDisplayOpinion(getApplicationContext());
    }
}
