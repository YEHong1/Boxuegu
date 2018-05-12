package cn.edu.gdmec.android.boxuegu.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.edu.gdmec.android.boxuegu.R;
import cn.edu.gdmec.android.boxuegu.activity.VideoPlayActivity;
import cn.edu.gdmec.android.boxuegu.bean.VideoBean;
import cn.edu.gdmec.android.boxuegu.utils.AnalysisUtils;
import cn.edu.gdmec.android.boxuegu.utils.DBUtils;

public class VideoListAdapter extends BaseAdapter{
    private Context context;
    private List<VideoBean> vbl = new ArrayList<VideoBean>();//视频列表数据
    private int selectedPosition = -1;//点击时选中的位置
    private DBUtils db;
    private LayoutInflater layoutInflater;

    public VideoListAdapter(Context context) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        db = DBUtils.getInstance(context);
    }

    /**
     * 设置数据更新界面
     */
    public void setData(List<VideoBean> vbl) {
        this.vbl = vbl;
        notifyDataSetChanged();
    }

    /**
     * 获取Item的总数
     */
    @Override
    public int getCount() {
        return vbl.size();
    }

    public void setSelectedPosition(int position) {
        selectedPosition = position;
    }

    /**
     * 根据position得到对应Item的对象
     */
    @Override
    public VideoBean getItem(int position) {
        return vbl.get(position);
    }

    /**
     * 根据position得到对应Item的id
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * 得到相应position对应的Item视图
     * position是当前Item的位置
     * convertView参数就是滚出屏幕的Item的View
     */
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = layoutInflater.inflate(R.layout.video_list_item, null);
            convertView.setTag(new ViewHolder(convertView));
        }
        inititalizeViews((VideoBean) getItem(position), (ViewHolder) convertView.getTag(),position,convertView);
        return convertView;
    }

    private void inititalizeViews(final VideoBean vbl, final ViewHolder holder, final int position, View convertView){
        holder.ivLeftIcon.setImageResource(R.drawable.course_bar_icon);
        holder.tv_title.setTextColor(Color.parseColor("#333333"));
        if (vbl != null){
            holder.tv_title.setText(vbl.secondTitle);
            if (selectedPosition == position){
                holder.ivLeftIcon.setImageResource(R.drawable.course_intro_icon);
                holder.tv_title.setTextColor(Color.parseColor("#009958"));
            }else {
                holder.ivLeftIcon.setImageResource(R.drawable.course_bar_icon);
                holder.tv_title.setTextColor(Color.parseColor("#333333"));
            }
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSelectedPosition(position);
                if (vbl != null){
                    String videoPath = vbl.videoPath;
                    notifyDataSetChanged();
                    if (TextUtils.isEmpty(videoPath)){
                        Toast.makeText(context,"本地没有此视频，暂无法播放",Toast.LENGTH_LONG).show();
                    }else {
                        if (readLoginStatus()){
                            String userName = AnalysisUtils.readLoginUserName(context);
                            db.saveVideoPlayList(getItem(position),userName);
                        }
                        Intent intent = new Intent(context, VideoPlayActivity.class);
                        intent.putExtra("videoPath",videoPath);
                        intent.putExtra("position",position);
                        ((Activity) context).startActivityForResult(intent,1);
                    }
                }
            }
        });
    }

    private boolean readLoginStatus(){
        SharedPreferences sp = context.getSharedPreferences("loginInfo",Context.MODE_PRIVATE);
        boolean isLogin = sp.getBoolean("isLogin",false);
        return isLogin;
    }

   protected class ViewHolder {
        public TextView tv_title;
        public ImageView ivLeftIcon;
        public ViewHolder(View view){
            ivLeftIcon = (ImageView) view.findViewById(R.id.iv_left_icon);
            tv_title = (TextView) view.findViewById(R.id.tv_video_title);
        }
    }
}
