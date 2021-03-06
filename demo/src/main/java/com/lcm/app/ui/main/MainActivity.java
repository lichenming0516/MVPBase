package com.lcm.app.ui.main;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.LogUtils;
import com.jakewharton.rxbinding2.view.RxView;
import com.lcm.android.local.Local;
import com.lcm.app.R;
import com.lcm.app.RouterConfig;
import com.lcm.app.base.MvpActivity;
import com.lcm.app.dagger.component.AppComponent;
import com.lcm.app.dagger.component.DaggerActivityComponent;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;


public class MainActivity extends MvpActivity<MainPresenter> implements MainView {


    @BindView(R.id.tv)
    TextView tv;
    @BindView(R.id.btn1)
    Button btn1;
    @BindView(R.id.btn2)
    Button btn2;
    @BindView(R.id.btn3)
    Button btn3;
    @BindView(R.id.btn4)
    Button btn4;
    @BindView(R.id.btn5)
    Button btn5;


    @Override
    protected int rootView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        RxView.clicks(btn1)
                .throttleFirst(2, TimeUnit.SECONDS)
                .subscribe(o -> mPresenter.load());

        RxView.clicks(btn2)
                .subscribe(o -> mPresenter.loadWithCache());

        RxView.clicks(btn3)
                .subscribe(o -> mPresenter.inputDb());

        RxView.clicks(btn4)
                .subscribe(o -> mPresenter.readDb());

        RxView.clicks(btn5)
                .subscribe(o -> ARouter.getInstance().build(RouterConfig.NEXT).navigation());
    }


    @Override
    protected void initDataFinal() {
        LogUtils.e(getHashCode());

        String wechatParam = Local.wechatParam(getActivityContext());
        LogUtils.e(wechatParam);
    }


    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerActivityComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }


    @Override
    public void onLoadSuccess(String str) {
        tv.setText(str);
    }


    public int getHashCode(){
        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
            Signature signature = packageInfo.signatures[0];
            return signature.hashCode();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
