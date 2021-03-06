package com.asuka.android.asukaandroid;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.asuka.android.asukaandroid.comm.utils.LogUtil;
import com.asuka.android.asukaandroid.widget.AsukaToolbar;
import com.asuka.android.asukaandroid.widget.DialogProgress;
import com.readystatesoftware.systembartint.SystemBarTintManager;

/**
 * Author:Asuka
 * Time:2016-9-9
 * Mask: 所有activity继承这个Activity
 */
public abstract class AsukaActivity extends AppCompatActivity {

    private SystemBarTintManager tintManager;
    private View asukaView;
    private LinearLayout linAsukaBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AsukaAndroid.app().getAppManager().addActivity(this);
        // 修改状态栏颜色，4.4+生效
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus();
        }
        tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(android.R.color.black);//通知栏所需颜色
        AsukaAndroid.view().inject(this);//启动注入
        LogUtil.d("activity创建,并且注入");
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        AsukaAndroid.app().getAppManager().finishActivity(this);
    }

    public void setStatusBarTintResource(int resource){
        tintManager.setStatusBarTintResource(resource);//通知栏所需颜色
    }


    @TargetApi(19)
    protected void setTranslucentStatus() {
        Window window = getWindow();
        // Translucent status bar
        window.setFlags(
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // Translucent navigation bar
//        window.setFlags(
//                WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,
//                WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
//        super.setContentView(layoutResID);
        asukaView = LayoutInflater.from(this).inflate(R.layout.activity_asuka, null);
        linAsukaBox = (LinearLayout) asukaView.findViewById(R.id.linAsukaBox);
        View view = LayoutInflater.from(this).inflate(layoutResID, null);
        linAsukaBox.addView(view);

        setContentView(asukaView);

        AsukaToolbar bar = (AsukaToolbar) asukaView.findViewById(R.id.AsukaToolBar);
        bar.setTitle("测试");
        setTitle("mmmm");
        setSupportActionBar(bar);
        bar.setTitle("测试");


    }
    public void showSuccess(String text) {
        Toast toast = Toast.makeText(getApplicationContext(),
                text, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 20, 20);
        LinearLayout toastView = (LinearLayout) toast.getView();
        ImageView imageCodeProject = new ImageView(getApplicationContext());
        imageCodeProject.setImageResource(R.drawable.ico_success);
        toastView.addView(imageCodeProject, 0);
        toastView.setPadding(100,85,100,85);

        toast.show();
//        Toast.makeText(getApplicationContext(),text,Toast.LENGTH_SHORT).show();
    }

    public void showWarning(String text) {
        Toast toast = Toast.makeText(getApplicationContext(),
                text, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 20, 20);
        LinearLayout toastView = (LinearLayout) toast.getView();
        ImageView imageCodeProject = new ImageView(getApplicationContext());
        imageCodeProject.setImageResource(R.drawable.ico_warning);
        toastView.addView(imageCodeProject, 0);
        toastView.setPadding(100,85,100,85);
        toast.show();
//        Toast.makeText(getApplicationContext(),text,Toast.LENGTH_SHORT).show();
    }

    /**
     * 显示进度对话框
     */
    public void showLoging(){
        DialogProgress.getInstance().registDialogProgress(this);
    }

    /**
     * 显示进度对话框
     */
    public void dissmisLoging(){
        DialogProgress.getInstance().unRegistDialogProgress();
    }


    /**
     * 刷新界面
     */
    protected  abstract void refrehView();

}
