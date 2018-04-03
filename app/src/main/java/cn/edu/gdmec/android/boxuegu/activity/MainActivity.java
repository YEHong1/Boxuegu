package cn.edu.gdmec.android.boxuegu.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import cn.edu.gdmec.android.boxuegu.Fragment.FragmentCourseFragment;
import cn.edu.gdmec.android.boxuegu.Fragment.FragmentExercisesFragment;
import cn.edu.gdmec.android.boxuegu.Fragment.FragmentMyinfoFragment;
import cn.edu.gdmec.android.boxuegu.R;
import cn.edu.gdmec.android.boxuegu.utils.AnalysisUtils;

public class MainActivity extends FragmentActivity implements View.OnClickListener{

    private RelativeLayout main_body;
    private TextView bottom_bar_text_course;
    private ImageView bottom_bar_image_course;
    private RelativeLayout bottom_bar_course_btn;
    private TextView bottom_bar_text_exercises;
    private ImageView bottom_bar_image_exercises;
    private RelativeLayout bottom_bar_exercises;
    private TextView bottom_bar_text_myinfo;
    private ImageView bottom_bar_image_myinfo;
    private RelativeLayout bottom_bar_myinfo_btn;
    private LinearLayout main_bottom_bar;
    protected long exitTime;



    public void init() {
        Timer timer = new Timer();
        //TimerTask类表示一个在指定时间内执行的task
        TimerTask task = new TimerTask() {
            @Override
            public void run() {

                Intent intent = new Intent(MainActivity.this, LoginAcitivity.class);
                startActivity(intent);

                MainActivity.this.finish();
            }
        };
        //设置这个task在延迟3秒之后自动执行
        timer.schedule(task, 3000);
    }




    private void initView() {
        main_body = (RelativeLayout) findViewById(R.id.main_body);
        bottom_bar_text_course = (TextView) findViewById(R.id.bottom_bar_text_course);
        bottom_bar_image_course = (ImageView) findViewById(R.id.bottom_bar_image_course);
        bottom_bar_course_btn = (RelativeLayout) findViewById(R.id.bottom_bar_course_btn);
        bottom_bar_text_exercises = (TextView) findViewById(R.id.bottom_bar_text_exercises);
        bottom_bar_image_exercises = (ImageView) findViewById(R.id.bottom_bar_image_exercises);
        bottom_bar_exercises = (RelativeLayout) findViewById(R.id.bottom_bar_exercises);
        bottom_bar_text_myinfo = (TextView) findViewById(R.id.bottom_bar_text_myinfo);
        bottom_bar_image_myinfo = (ImageView) findViewById(R.id.bottom_bar_image_myinfo);
        bottom_bar_myinfo_btn = (RelativeLayout) findViewById(R.id.bottom_bar_myinfo_btn);
        main_bottom_bar = (LinearLayout) findViewById(R.id.main_bottom_bar);

        bottom_bar_course_btn.setOnClickListener(this);
        bottom_bar_exercises.setOnClickListener(this);
        bottom_bar_myinfo_btn.setOnClickListener(this);

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.main_body,new FragmentCourseFragment()).commit();
    }

    private void setMain(){
        this.getSupportFragmentManager().beginTransaction().add(R.id.main_body,new FragmentMyinfoFragment()).commit();
        setSelectStatus(2);
    }

    private void setSelectStatus(int index){
        switch (index){
            case 0:
                bottom_bar_image_course.setImageResource(R.drawable.main_course_icon_selected);
                bottom_bar_text_course.setTextColor(Color.parseColor("#0097F7"));

                bottom_bar_text_exercises.setTextColor(Color.parseColor("#666666"));
                bottom_bar_text_myinfo.setTextColor(Color.parseColor("#666666"));

                bottom_bar_image_exercises.setImageResource(R.drawable.main_exercises_icon);
                bottom_bar_image_myinfo.setImageResource(R.drawable.main_my_icon);
                break;

            case 1:
                bottom_bar_image_exercises.setImageResource(R.drawable.main_exercises_icon_selected);
                bottom_bar_text_exercises.setTextColor(Color.parseColor("#0097F7"));

                bottom_bar_text_course.setTextColor(Color.parseColor("#666666"));
                bottom_bar_text_myinfo.setTextColor(Color.parseColor("#666666"));

                bottom_bar_image_course.setImageResource(R.drawable.main_course_icon);
                bottom_bar_image_myinfo.setImageResource(R.drawable.main_my_icon);
                break;

            case 2:
                bottom_bar_image_myinfo.setImageResource(R.drawable.main_my_icon_selected);
                bottom_bar_text_myinfo.setTextColor(Color.parseColor("#0097F7"));

                bottom_bar_text_course.setTextColor(Color.parseColor("#666666"));
                bottom_bar_text_exercises.setTextColor(Color.parseColor("#666666"));

                bottom_bar_image_exercises.setImageResource(R.drawable.main_exercises_icon);
                bottom_bar_image_course.setImageResource(R.drawable.main_course_icon);
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        setMain();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bottom_bar_course_btn:
                getSupportFragmentManager().beginTransaction().replace(R.id.main_body,new FragmentCourseFragment()).commit();
                setSelectStatus(0);
                break;
            case R.id.bottom_bar_exercises:
                getSupportFragmentManager().beginTransaction().replace(R.id.main_body,new FragmentExercisesFragment()).commit();
                setSelectStatus(1);
                break;

            case R.id.bottom_bar_myinfo_btn:
                getSupportFragmentManager().beginTransaction().replace(R.id.main_body,new FragmentMyinfoFragment()).commit();
                setSelectStatus(2);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null){
            boolean isLogin = data.getBooleanExtra("isLogin",false);
            if (isLogin){
                setSelectStatus(0);
            }else {
                setSelectStatus(2);
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            if ((System.currentTimeMillis() -exitTime) > 2000){
                Toast.makeText(MainActivity.this,"再按一次退出博学谷",Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            }else {
                this.finish();
                if (AnalysisUtils.readLoginStatus(this)){
                    AnalysisUtils.clearLoginStatus(this);
                }
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
