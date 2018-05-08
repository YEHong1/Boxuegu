package cn.edu.gdmec.android.boxuegu.bean;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import cn.edu.gdmec.android.boxuegu.R;

/**
 * 创建数据储存池，实例化习题列表数据
 **/
public class ExercisesListLab {
    private static ExercisesListLab mExercisesLab;
    private List<ExercisesBean> ebl;

    public static ExercisesListLab get() {
        if (mExercisesLab == null) {
            mExercisesLab = new ExercisesListLab();
        }
        return mExercisesLab;
    }

    public ExercisesListLab() {
        ebl = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ExercisesBean bean = new ExercisesBean();
            bean.id = (i + 1);
            switch (i) {
                case 0:
                    bean.title = "第1章 Android基础入门";
                    bean.content = ("共计5题");
                    bean.background = (R.drawable.exercises_bg_1);
                    break;
                case 1:
                    bean.title = "第2章 Android UI开发";
                    bean.content = ("共计5题");
                    bean.background = (R.drawable.exercises_bg_2);
                    break;
                case 2:
                    bean.title = "第3章 Activity";
                    bean.content = ("共计5题");
                    bean.background = (R.drawable.exercises_bg_3);
                    break;
                case 3:
                    bean.title = "第4章 数据存储";
                    bean.content = ("共计5题");
                    bean.background = (R.drawable.exercises_bg_4);
                    break;
                case 4:
                    bean.title = "第5章 SQLite数据库";
                    bean.content = ("共计5题");
                    bean.background = (R.drawable.exercises_bg_1);
                    break;
                case 5:
                    bean.title = "第6章 广播接受者";
                    bean.content = ("共计5题");
                    bean.background = (R.drawable.exercises_bg_2);
                    break;
                case 6:
                    bean.title = "第7章 服务";
                    bean.content = ("共计5题");
                    bean.background = (R.drawable.exercises_bg_3);
                    break;
                case 7:
                    bean.title = "第8章 内容提供者";
                    bean.content = ("共计5题");
                    bean.background = (R.drawable.exercises_bg_4);
                    break;
                case 8:
                    bean.title = "第9章 网络编程";
                    bean.content = ("共计5题");
                    bean.background = (R.drawable.exercises_bg_1);
                    break;
                case 9:
                    bean.title = "第10章 高级编程";
                    bean.content = ("共计5题");
                    bean.background = (R.drawable.exercises_bg_2);
                    break;
                default:
                    break;
            }
            ebl.add(bean);
        }
    }

    public List<ExercisesBean> getExercisesList() {
        return ebl;
    }
}
