package cn.edu.gdmec.android.boxuegu.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import java.util.ArrayList;
import java.util.List;

import cn.edu.gdmec.android.boxuegu.R;
import cn.edu.gdmec.android.boxuegu.activity.VideoPlayActivity;
import cn.edu.gdmec.android.boxuegu.bean.VideoBean;

/**
 * Created by admin on 2017/12/30.
 */

public class PlayHistoryAdapter extends BaseAdapter {

    private Context context;
    private List<VideoBean> vbl = new ArrayList<VideoBean>();
    private LayoutInflater layoutInflater;
    
    public PlayHistoryAdapter(Context context){
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    public void setData(List<VideoBean> vbl){
        this.vbl = vbl;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return vbl == null ? 0 : vbl.size();
    }

    @Override
    public Object getItem(int i) {
        return vbl == null ? 0 : vbl.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = layoutInflater.inflate(R.layout.play_history_list_item, null);
            convertView.setTag(new ViewHolder(convertView));
        }
        initializeViews((VideoBean) getItem(position), (ViewHolder) convertView.getTag(),convertView);
          return convertView;
        }
        
        private void initializeViews(final VideoBean vbl, ViewHolder holder, View convertView) {
        
            if (vbl != null) {
                holder.tv_adpater_title.setText(vbl.title);
                holder.tv_video_title.setText(vbl.secondTitle);
                switch (vbl.chapterId) {
                    case 1:
                        holder.iv_icon.setImageResource(R.drawable.video_play_icon1);
                        break;
                    case 2:
                        holder.iv_icon.setImageResource(R.drawable.video_play_icon2);
                        break;
                    case 3:
                        holder.iv_icon.setImageResource(R.drawable.video_play_icon3);
                        break;
                    case 4:
                        holder.iv_icon.setImageResource(R.drawable.video_play_icon4);
                        break;
                    case 5:
                        holder.iv_icon.setImageResource(R.drawable.video_play_icon5);
                        break;
                    case 6:
                        holder.iv_icon.setImageResource(R.drawable.video_play_icon6);
                        break;
                    case 7:
                        holder.iv_icon.setImageResource(R.drawable.video_play_icon7);
                        break;
                    case 8:
                        holder.iv_icon.setImageResource(R.drawable.video_play_icon8);
                        break;
                    case 9:
                        holder.iv_icon.setImageResource(R.drawable.video_play_icon9);
                        break;
                    case 10:
                        holder.iv_icon.setImageResource(R.drawable.video_play_icon10);
                        break;
                    default:
                        holder.iv_icon.setImageResource(R.drawable.video_play_icon1);
                        break;
                }
                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (vbl == null){
                            return;
                        }
                        //跳转到播放视频界面
                        Intent intent = new Intent(context, VideoPlayActivity.class);
                        intent.putExtra("videoPath", vbl.videoPath);
                        context.startActivity(intent);
                    }
                });
            }
    }

   protected class ViewHolder{
        TextView tv_adpater_title;
        TextView tv_video_title;
        ImageView iv_icon;
        public ViewHolder(View view){
            iv_icon = (ImageView) view.findViewById(R.id.iv_video_icon);
            tv_video_title = (TextView) view.findViewById(R.id.tv_video_title);
            tv_adpater_title = (TextView) view.findViewById(R.id.tv_adpater_title);
        }
    }
}
