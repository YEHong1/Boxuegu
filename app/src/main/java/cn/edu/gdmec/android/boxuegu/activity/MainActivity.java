package cn.edu.gdmec.android.boxuegu.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import cn.edu.gdmec.android.boxuegu.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Timer timer=new Timer();
        //TimerTask类表示一个在指定时间内执行的task
        TimerTask task=new TimerTask() {
            @Override
            public void run() {

                Intent intent=new Intent(MainActivity.this,LoginAcitivity.class);
                startActivity(intent);

                MainActivity.this.finish();
            }
        };
        //设置这个task在延迟3秒之后自动执行
        timer.schedule(task,3000);
    }

}
