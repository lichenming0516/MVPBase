package com.lcm.app.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.blankj.utilcode.util.ToastUtils;
import com.lcm.android.mvp.BaseMvpFragment;
import com.lcm.android.mvp.BaseMvpPresenter;
import com.lcm.android.mvp.BaseView;
import com.lcm.app.MyApplication;
import com.lcm.app.dagger.component.AppComponent;

/**
 * ****************************************************************
 * Author: LCM
 * Date: 2017/5/23 下午6:11
 * Desc:
 * *****************************************************************
 */

public abstract class MvpFragment<P extends BaseMvpPresenter> extends BaseMvpFragment<P> implements BaseView {
    protected MyApplication application;

    @Override
    public Context getActivityContext() {
        return getActivity();
    }

    @Override
    public void finishView() {

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPresenter.onAttachView(this);

        initDataFinal();
    }

    @Override
    protected void initData() {
        return;
    }

    @Override
    protected void ComponentInject() {
        application = (MyApplication) getActivity().getApplication();
        setupActivityComponent(application.getAppComponent());
    }

    //防止mPresenter 未绑定view  获取数据出错
    protected abstract void initDataFinal();


    //提供AppComponent(提供所有的单例对象)给子类，进行Component依赖
    protected abstract void setupActivityComponent(AppComponent appComponent);


}
