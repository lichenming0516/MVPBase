package com.lcm.app;

import com.alibaba.android.arouter.launcher.ARouter;
import com.lcm.android.base.BaseApplication;
import com.lcm.app.dagger.component.AppComponent;
import com.lcm.app.dagger.component.DaggerAppComponent;
import com.lcm.app.dagger.module.DBModule;
import com.lcm.app.dagger.module.DataModule;
import com.lcm.app.data.http.MyHttpHandler;
import com.squareup.leakcanary.LeakCanary;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * ****************************************************************
 * Author: LCM
 * Date: 2017/5/23 下午12:58
 * Desc:
 * *****************************************************************
 */

public class MyApplication extends BaseApplication{
    private static AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppComponent = DaggerAppComponent.builder()
                .appModule(getAppModule())
                .clientModule(getClientModule())
                .dataModule(new DataModule())
//                .dBModule(new DBModule())
//                .serviceModule(new ServiceModule())
//                .cacheModule(new CacheModule())
                .build();

        ARouter.init(this);
        ARouter.openDebug();
        ARouter.openLog();


        Realm.init(this);
        Realm.setDefaultConfiguration(new RealmConfiguration.Builder().name(BuildConfig.DB_Name).build());

        LeakCanary.install(this);
    }


    /**
     * 将AppComponent返回出去,供其它地方使用, AppComponent接口中声明的方法返回的实例, 在getAppComponent()拿到对象后都可以直接使用
     *
     * @return
     */
    public  AppComponent getAppComponent() {
        return mAppComponent;
    }

//    @Override
//    protected GlobeHttpHandler getHttpHandler() {
//        return new MyHttpHandler();
//    }
}
