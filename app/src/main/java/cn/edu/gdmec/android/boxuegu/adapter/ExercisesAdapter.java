package cn.edu.gdmec.android.boxuegu.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.edu.gdmec.android.boxuegu.R;
import cn.edu.gdmec.android.boxuegu.activity.ExercisesDetailActivity;
import cn.edu.gdmec.android.boxuegu.bean.ExercisesBean;
import cn.edu.gdmec.android.boxuegu.utils.AnalysisUtils;

/**
 * 1.以下是ListView的经典使用方法，需要继承自BaseAdapter，并实现四个超类方法
 * 2.需要特别注意其中的getView( position,convertView, parent)，根据ListView特性复用了convertView,为了性能优化复用ViewHolder,
 * 并通过getItem(position)获取每个item的数据对象并赋值给ViewHolder
 * 3.由于课程列表显示的数据在这里自定义创建于ExercisesListLab，不需要监听数据变化，弃用setData
 */

public class ExercisesAdapter extends BaseAdapter {
    private Context mContext;
    private List<ExercisesBean> ebl;

    public ExercisesAdapter(Context context, List<ExercisesBean> ebl) {
        this.mContext = context;
        this.ebl = ebl;
    }

    /**
     * 获取Item的总数
     */
    @Override
    public int getCount() {
        return ebl == null ? 0 : ebl.size();
    }

    /**
     * 根据position得到对应的Item的对象
     */
    @Override
    public ExercisesBean getItem(int position) {
        return ebl == null ? null : ebl.get(position);
    }

    /**
     * 根据position得到对应Item的对象
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * 得到相应position对应的Item视图，position是当前Item的位置
     * convertView参数就是滚出屏幕的Item的View
     * 第一次进入或滑动屏幕时候被调用
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder vh;
        //复用ViewHolder，convertView
        if (convertView == null) {
            vh = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.exercises_list_item, null);
            vh.title = (TextView) convertView.findViewById(R.id.tv_title);
            vh.content = (TextView) convertView.findViewById(R.id.tv_content);
            vh.order = (TextView) convertView.findViewById(R.id.tv_order);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        //获取position对应的Item的数据对象
        final ExercisesBean bean = getItem(position);
        if (bean != null) {
            vh.order.setText(position + 1 + "");
            vh.title.setText(bean.title);
            vh.content.setText(bean.content);
            vh.order.setBackgroundResource(bean.background);
        }

        //每个Item的点击事件
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bean == null) {
                    return;
                }
                //跳转到习题详细页面
                Intent intent = new Intent(mContext, ExercisesDetailActivity.class);
                //把章节Id传递到习题详情页面
                intent.putExtra("id", bean.id);
                //把标题传递到习题详细页面
                intent.putExtra("title", bean.title);
                ((Activity) mContext).startActivityForResult(intent, 000);
            }
        });
        return convertView;
    }

    class ViewHolder {
        private TextView title, content;
        private TextView order;
    }

}

