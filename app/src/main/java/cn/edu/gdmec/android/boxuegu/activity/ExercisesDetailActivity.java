package cn.edu.gdmec.android.boxuegu.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import cn.edu.gdmec.android.boxuegu.R;
import cn.edu.gdmec.android.boxuegu.adapter.ExercisesDetailAdapter;
import cn.edu.gdmec.android.boxuegu.bean.ExercisesBean;
import cn.edu.gdmec.android.boxuegu.utils.AnalysisUtils;

public class ExercisesDetailActivity extends AppCompatActivity {
    private TextView tv_main_title;
    private TextView tv_back;
    private RelativeLayout title_bar;
    private RecyclerView rv_list;
    private TextView tv_dibu;
    private String title;
    private int id;
    private List<ExercisesBean> ebl;
    private ExercisesDetailAdapter adapter;
    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercises_detail);
        //设置此界面为竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        id = getIntent().getIntExtra("id", 0);
        title = getIntent().getStringExtra("title");
        initData();
        init();
    }

    private void initData() {
        try {
            //获取习题数据
            InputStream is = getResources().getAssets().open("chapter" + id + ".xml");
            ebl = AnalysisUtils.getExercisesInfos(is);
            Intent data = new Intent();
            //没有被点击，需要这么设置否则闪退
            data.putExtra("count", count);
            data.putExtra("id", id);
            setResult(RESULT_OK, data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化控件
     */
    private void init() {
        tv_main_title = (TextView) findViewById(R.id.tv_main_title);
        tv_main_title.setText(title);
        tv_back = (TextView) findViewById(R.id.tv_back);
        title_bar = (RelativeLayout) findViewById(R.id.rl_title_bar);
        title_bar.setBackgroundColor(Color.parseColor("#30B4FF"));
        tv_dibu = (TextView) findViewById(R.id.tv_dibu);
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ExercisesDetailActivity.this.finish();
            }
        });
        rv_list = (RecyclerView) findViewById(R.id.rv_list);
        /**必要**/
        rv_list.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        adapter = new ExercisesDetailAdapter(ExercisesDetailActivity.this, ebl, new ExercisesDetailAdapter.OnSelectListener() {

            @Override
            public void onSelectA(int position, ImageView iv_a, ImageView iv_b, ImageView iv_c, ImageView iv_d) {
                //判断如果答案不是1即是A选项
                if (ebl.get(position).answer != 1) {
                    ebl.get(position).select = 1;
                } else {
                    ebl.get(position).select = 0;
                }
                switch (ebl.get(position).answer) {
                    case 1:
                        iv_a.setImageResource(R.drawable.exercises_right_icon);
                        break;
                    case 2:
                        iv_a.setImageResource(R.drawable.exercises_error_icon);
                        iv_b.setImageResource(R.drawable.exercises_right_icon);
                        break;
                    case 3:
                        iv_a.setImageResource(R.drawable.exercises_error_icon);
                        iv_c.setImageResource(R.drawable.exercises_right_icon);
                        break;
                    case 4:
                        iv_a.setImageResource(R.drawable.exercises_error_icon);
                        iv_d.setImageResource(R.drawable.exercises_right_icon);
                        break;
                }
                AnalysisUtils.setABCDEnable(false, iv_a, iv_b, iv_c, iv_d);
                //当实现这些接口的时候，我们已经从adapter得到了需要的position，又要重新在item设置监听来获取adapter与Activity的信息回调，实属舍近求远
                tv_dibu.setText("第" + (position + 1) + "道题完成，共五题");
                count++;
                Intent data = new Intent();
                data.putExtra("id", id);
                data.putExtra("count", count);
                setResult(RESULT_OK, data);
            }

            @Override
            public void onSelectB(int position, ImageView iv_a, ImageView iv_b, ImageView iv_c, ImageView iv_d) {

                //判断如果答案不是2即是B选项
                if (ebl.get(position).answer != 2) {
                    ebl.get(position).select = 2;
                } else {
                    ebl.get(position).select = 0;
                }
                switch (ebl.get(position).answer) {
                    case 1:
                        iv_a.setImageResource(R.drawable.exercises_right_icon);
                        iv_b.setImageResource(R.drawable.exercises_error_icon);
                        break;
                    case 2:
                        iv_b.setImageResource(R.drawable.exercises_right_icon);
                        break;
                    case 3:
                        iv_b.setImageResource(R.drawable.exercises_error_icon);
                        iv_c.setImageResource(R.drawable.exercises_right_icon);
                        break;
                    case 4:
                        iv_b.setImageResource(R.drawable.exercises_error_icon);
                        iv_d.setImageResource(R.drawable.exercises_right_icon);
                        break;
                }
                AnalysisUtils.setABCDEnable(false, iv_a, iv_b, iv_c, iv_d);
                tv_dibu.setText("第" + (position + 1) + "道题完成，共五题");
                count++;
                Intent data = new Intent();
                data.putExtra("id", id);
                data.putExtra("count", count);
                setResult(RESULT_OK, data);
            }

            @Override
            public void onSelectC(int position, ImageView iv_a, ImageView iv_b, ImageView iv_c, ImageView iv_d) {
                //判断如果答案不是3即是C选项
                if (ebl.get(position).answer != 3) {
                    ebl.get(position).select = 3;
                } else {
                    ebl.get(position).select = 0;
                }
                switch (ebl.get(position).answer) {
                    case 1:
                        iv_a.setImageResource(R.drawable.exercises_right_icon);
                        iv_c.setImageResource(R.drawable.exercises_error_icon);
                        break;
                    case 2:
                        iv_b.setImageResource(R.drawable.exercises_right_icon);
                        iv_c.setImageResource(R.drawable.exercises_error_icon);
                        break;
                    case 3:
                        iv_c.setImageResource(R.drawable.exercises_right_icon);
                        break;
                    case 4:
                        iv_c.setImageResource(R.drawable.exercises_error_icon);
                        iv_d.setImageResource(R.drawable.exercises_right_icon);
                        break;
                }
                AnalysisUtils.setABCDEnable(false, iv_a, iv_b, iv_c, iv_d);
                tv_dibu.setText("第" + (position + 1) + "道题完成，共五题");
                count++;
                Intent data = new Intent();
                data.putExtra("id", id);
                data.putExtra("count", count);
                setResult(RESULT_OK, data);
            }

            @Override
            public void onSelectD(int position, ImageView iv_a, ImageView iv_b, ImageView iv_c, ImageView iv_d) {
                //判断如果答案不是4即是D选项
                if (ebl.get(position).answer != 4) {
                    ebl.get(position).select = 4;
                } else {
                    ebl.get(position).select = 0;
                }
                switch (ebl.get(position).answer) {
                    case 1:
                        iv_a.setImageResource(R.drawable.exercises_right_icon);
                        iv_d.setImageResource(R.drawable.exercises_error_icon);
                        break;
                    case 2:
                        iv_d.setImageResource(R.drawable.exercises_error_icon);
                        iv_b.setImageResource(R.drawable.exercises_right_icon);
                        break;
                    case 3:
                        iv_d.setImageResource(R.drawable.exercises_error_icon);
                        iv_c.setImageResource(R.drawable.exercises_right_icon);
                        break;
                    case 4:
                        iv_d.setImageResource(R.drawable.exercises_right_icon);
                        break;
                }
                AnalysisUtils.setABCDEnable(false, iv_a, iv_b, iv_c, iv_d);
                tv_dibu.setText("第" + (position + 1) + "道题完成，共五题");
                count++;
                Intent data = new Intent();
                data.putExtra("id", id);
                data.putExtra("count", count);
                setResult(RESULT_OK, data);
            }
        }
        );
        rv_list.setAdapter(adapter);
    }
}
