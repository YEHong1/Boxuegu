package cn.edu.gdmec.android.boxuegu.fragment;


import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import cn.edu.gdmec.android.boxuegu.R;
import cn.edu.gdmec.android.boxuegu.adapter.ADViewPagerAdapter;
import cn.edu.gdmec.android.boxuegu.adapter.CourseAdapter;
import cn.edu.gdmec.android.boxuegu.bean.CourseBean;
import cn.edu.gdmec.android.boxuegu.utils.AnalysisUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class CourseFragment extends Fragment {
    private ViewPager vp_advertBanner;
    private View dots_1;
    private View dots_2;
    private View dots_3;
    private RelativeLayout rl_adBanner;
    private RecyclerView rl_list;

    public static final int MSG_AD_SLID = 002;
    private List<ImageView> viewList;
    private List<CourseBean> rList;
    private CourseAdapter adapter;
    private ADViewPagerAdapter adViewPagerAdapter;
    private List<View> dots;
    private int oldPoints = 0;
    private Thread thread;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case MSG_AD_SLID:
                    if (adViewPagerAdapter.getCount() > 0){
                        if (vp_advertBanner.getCurrentItem() == viewList.size() - 1){
                            vp_advertBanner.setCurrentItem(0);
                        }else {
                            vp_advertBanner.setCurrentItem(vp_advertBanner.getCurrentItem() + 1);
                        }
                    }
                    break;
            }
        }
    };

    public CourseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_course, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getCourseData();
        initView(view);
        setViewPager();
    }

    private void getCourseData() {
        try {
            InputStream is = getActivity().getResources().getAssets().open("chaptertitle.xml");
            rList = AnalysisUtils.getCourseInfos(is);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setViewPager(){
        thread = new Thread(){
            @Override
            public void run(){
                super.run();
                while (true){
                    try{
                        sleep(1000);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    handler.sendEmptyMessage(MSG_AD_SLID);
                }
            }
        };
        thread.start();
    }

    private void initView(View view){
        rl_list = view.findViewById(R.id.rv_list);
        adapter = new CourseAdapter(getActivity());
        adapter.setData(rList);
        rl_list.setLayoutManager(new GridLayoutManager(getActivity(),3));
        rl_list.setAdapter(adapter);
        vp_advertBanner = view.findViewById(R.id.vp_advertBanner);
        rl_adBanner = view .findViewById(R.id.rl_adBanner);

        viewList = new ArrayList<>();

        ImageView imageView1 = new ImageView(getActivity());
        imageView1.setImageResource(R.drawable.banner_1);

        ImageView imageView2 = new ImageView(getActivity());
        imageView2.setImageResource(R.drawable.banner_2);

        ImageView imageView3 = new ImageView(getActivity());
        imageView3.setImageResource(R.drawable.banner_3);

        viewList.add(imageView1);
        viewList.add(imageView2);
        viewList.add(imageView3);
        adViewPagerAdapter = new ADViewPagerAdapter(getActivity(), viewList);
        vp_advertBanner .setAdapter(adViewPagerAdapter);

        dots = new ArrayList<View>();
        dots_1 = (View) view.findViewById(R.id.dots_1);
        dots_2 = (View) view.findViewById(R.id.dots_2);
        dots_3 = (View) view.findViewById(R.id.dots_3);

        dots.add(dots_1);
        dots.add(dots_2);
        dots.add(dots_3);

        dots.get(0).setBackgroundResource(R.drawable.indicator_on);
        vp_advertBanner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                        dots.get(oldPoints).setBackgroundResource(R.drawable.indicator_off);
                        dots.get(position).setBackgroundResource(R.drawable.indicator_on);
                        oldPoints = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        resetSize();
    }

    /**
     * 计算控件大小
     */
    private void resetSize(){
        int sw = getScreenWidth(getActivity());
        //广告条高度
        int adLeight = sw / 2;
        ViewGroup.LayoutParams adlp = rl_adBanner.getLayoutParams();
        adlp.width = sw;
        adlp.height = adLeight;
        rl_adBanner.setLayoutParams(adlp);
    }

    /**
     * 读取屏幕宽
     */
    public static int getScreenWidth(Activity context){
        DisplayMetrics metrics = new DisplayMetrics();
        Display display = context.getWindowManager().getDefaultDisplay();
        display.getMetrics(metrics);
        return metrics.widthPixels;
    }
}
