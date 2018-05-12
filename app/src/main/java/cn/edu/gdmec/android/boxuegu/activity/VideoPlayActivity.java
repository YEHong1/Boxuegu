package cn.edu.gdmec.android.boxuegu.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import cn.edu.gdmec.android.boxuegu.R;
import cn.edu.gdmec.android.boxuegu.bean.VideoBean;

public class VideoPlayActivity extends AppCompatActivity {

    private VideoView videoView;
    private MediaController controller;
    private String videoPath;
    private int position;
    private VideoBean vbl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_video_play);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        videoPath = getIntent().getStringExtra("videoPath");
        position = getIntent().getIntExtra("position", 0);
        init();
    }

    private void init() {
        videoView = (VideoView) findViewById(R.id.VideoView);
        controller = new MediaController(this);
        videoView.setMediaController(controller);
        play();
    }
    /**
     * 播放视频
     */
    private void play() {
        if (TextUtils.isEmpty(videoPath)) {
            Toast.makeText(this, "本地没有此视频，暂时无法播放", Toast.LENGTH_SHORT).show();
            return;
        }

        String uri= videoPath;
        videoView.setVideoPath(uri);
        videoView.requestFocus();
        videoView.start();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //把视频详情界面传过来的被点击视频得位置传递回去
        Intent data = new Intent();
        data.putExtra("position", position);
        setResult(RESULT_OK, data);
        return super.onKeyDown(keyCode, event);
    }
}