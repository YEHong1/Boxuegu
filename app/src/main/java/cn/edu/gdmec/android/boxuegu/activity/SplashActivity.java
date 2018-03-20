package cn.edu.gdmec.android.boxuegu.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import cn.edu.gdmec.android.boxuegu.R;

/**
 * Created by apple on 18/3/13.
 */

public class SplashActivity extends AppCompatActivity {
    private TextView tv_version;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView就是设置一个Activity的显示界面
        setContentView(R.layout.activity_splash);
        //设置此界面为竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //让组件和对应的ID对应起来
        tv_version = (TextView) findViewById(R.id.tv_version);
        try {
            //获取程序包信息
            PackageInfo info=getPackageManager().getPackageInfo(getPackageName(),0);
            //给控件设置实际内容
            tv_version.setText("Version:"+info.versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            tv_version.setText("Version");
        }

        //让此界面延迟3秒后在跳转，timer中有一个线程，这个线程不断执行task
        Timer timer=new Timer();
        //TimerTask类表示一个在指定时间内执行的task
        TimerTask task=new TimerTask() {
            @Override
            public void run() {
                //Intent（意图）主要是解决Android应用的各项组件之间的通讯。
                //从SplashActivity界面跳转到MainActivity.class界面
                Intent intent=new Intent(SplashActivity.this,MainActivity.class);
                //startActivity创建并显示一个Activity，并向其传递数据。
                startActivity(intent);
                /*
                finish掉的Activity只能通过Intent跳转再次来到，不能通过后退按钮到达，
                因为finish之后该activity已经被销毁了。
                我们并不希望程序在主界面按返回键时跳转到欢迎界面。
                最好养成习惯：在一个Activity用完之后应该将之finish掉*/
                SplashActivity.this.finish();
            }
        };
        //设置这个task在延迟3秒之后自动执行
        timer.schedule(task,3000);

    }

}
