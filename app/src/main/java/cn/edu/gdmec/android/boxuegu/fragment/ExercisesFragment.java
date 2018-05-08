package cn.edu.gdmec.android.boxuegu.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import cn.edu.gdmec.android.boxuegu.R;
import cn.edu.gdmec.android.boxuegu.adapter.ExercisesAdapter;
import cn.edu.gdmec.android.boxuegu.bean.ExercisesBean;
import cn.edu.gdmec.android.boxuegu.bean.ExercisesListLab;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExercisesFragment extends Fragment {
    private ListView lv_list;
    private ExercisesAdapter adapter;
    private List<ExercisesBean> ebl;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_exercises, container, false);
        lv_list = (ListView) view.findViewById(R.id.lv_list);
        //获取充满习题列表信息的泛型
        ebl = ExercisesListLab.get().getExercisesList();
        resetEbl();
        adapter = new ExercisesAdapter(getActivity(), ebl);
        lv_list.setAdapter(adapter);
        return view;
    }

    private void resetEbl() {
        SharedPreferences sp = getActivity().getSharedPreferences("loginInfo", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        Boolean isLogin = sp.getBoolean("isLogin",false);
        if(isLogin){
            for(int i=0;i<ebl.size();i++){
                int hasFinish=sp.getInt("hasFinish"+i,999);
                if(hasFinish!=999){
                    ebl.get(i-1).content = "已完成";
                }
            }
        }
    }
}
