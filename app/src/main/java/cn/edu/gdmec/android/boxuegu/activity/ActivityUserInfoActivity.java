package cn.edu.gdmec.android.boxuegu.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import cn.edu.gdmec.android.boxuegu.Bean.UserBean;
import cn.edu.gdmec.android.boxuegu.R;
import cn.edu.gdmec.android.boxuegu.utils.AnalysisUtils;
import cn.edu.gdmec.android.boxuegu.utils.DBUtils;

public class ActivityUserInfoActivity extends Activity implements View.OnClickListener {

    private TextView tv_back;
    private TextView tv_main_title;
    private RelativeLayout title_bar;
    private RelativeLayout rl_Head;
    private ImageView ivHeadIcon;
    private RelativeLayout rlAccount;
    private TextView tvUserName;
    private RelativeLayout rlNickName;
    private TextView tvNickName;
    private RelativeLayout rlSex;
    private TextView tvSex;
    private RelativeLayout rlSignature;
    private TextView tvSignature;
    private String spUserName;
    private String new_info;
    private TextView tv_qq;
    private RelativeLayout rl_qq;
    private static final int CHANGE_NICKNAME = 1;
    private static final int CHANGE_SIGNATURE = 2;
    private static final int CHANGE_QQ = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        spUserName = AnalysisUtils.readLoginUserName(this);
        initView();
        initData();

    }

    private void initView(){
        tv_back = (TextView)findViewById(R.id.tv_back);
        tv_main_title = (TextView)findViewById(R.id.tv_main_title);
        title_bar = (RelativeLayout)findViewById(R.id.title_bar);
        rl_Head = (RelativeLayout) findViewById(R.id.rl_head);
        ivHeadIcon = (ImageView) findViewById(R.id.iv_head_icon);
        rlAccount = (RelativeLayout) findViewById(R.id.rl_account);
        tvUserName = (TextView) findViewById(R.id.tv_user_name);
        rlNickName = (RelativeLayout) findViewById(R.id.rl_nickName);
        tvNickName = (TextView) findViewById(R.id.tv_nickName);
        rlSex = (RelativeLayout) findViewById(R.id.rl_sex);
        tvSex = (TextView) findViewById(R.id.tv_sex);
        rlSignature = (RelativeLayout) findViewById(R.id.rl_signature);
        tvSignature = (TextView) findViewById(R.id.tv_signature);
        tv_qq = (TextView)findViewById(R.id.tv_qq);
        rl_qq = (RelativeLayout)findViewById(R.id.rl_qq);

        tv_back.setOnClickListener(this);
        tv_main_title.setText("个人资料");
        title_bar.setBackgroundColor(Color.parseColor("#30B4FF"));
        rl_Head.setOnClickListener(this);
        rlAccount.setOnClickListener(this);
        rlNickName.setOnClickListener(this);
        rlSex.setOnClickListener(this);
        rlSignature.setOnClickListener(this);
        rl_qq.setOnClickListener(this);
    }

    private void initData(){
        UserBean bean = DBUtils.getInstance(this).getUserInfo(spUserName);
        if (bean == null){
            bean = new UserBean();
            bean.userName = spUserName;
            bean.nickName = "问答精灵";
            bean.sex = "男";
            bean.signature = "问答精灵";
            bean.qq = "未填写";
            DBUtils.getInstance(this).saveUserInfo(bean);
        }
        setValue(bean);
    }

    private void setValue(UserBean bean){
        tvUserName.setText(bean.userName);
        tvNickName.setText(bean.nickName);
        tvSex.setText(bean.sex);
        tvSignature.setText(bean.signature);
        tv_qq.setText(bean.qq);
    }

    private void sexDialog(String sex){
        int sexFlag = 0;
        if ("男".equals(sex)){
            sexFlag = 0;
        }else if ("女".equals(sex)){
            sexFlag = 1;
        }
        final String item[] = {"男","女"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("性别");
        builder.setSingleChoiceItems(item, sexFlag, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                Toast.makeText(ActivityUserInfoActivity.this,item[i],
                        Toast.LENGTH_SHORT).show();
                setSex(item[i]);
            }
        });
        builder.create().show();
    }

    private void setSex(String s){
        tvSex.setText(s);
        DBUtils.getInstance(ActivityUserInfoActivity.this).updateUserInfo("sex",s,spUserName);
    }

    public void enterActivityForResult(Class<?> to,int requestcode,Bundle b){
        Intent i =new Intent(this,to);
        i.putExtras(b);
        startActivityForResult(i,requestcode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case CHANGE_NICKNAME:
                if (data != null){
                    new_info = data.getStringExtra("nickName");
                    if (TextUtils.isEmpty(new_info)){
                        return;
                    }
                    tvNickName.setText(new_info);
                    DBUtils.getInstance(ActivityUserInfoActivity.this).updateUserInfo("nickName",
                            new_info,spUserName);
                }
                break;
            case CHANGE_SIGNATURE:
                if (data != null){
                    new_info = data.getStringExtra("signature");
                    if (TextUtils.isEmpty(new_info)){
                        return;
                    }
                    tvSignature.setText(new_info);
                    DBUtils.getInstance(ActivityUserInfoActivity.this).updateUserInfo("signature",
                            new_info,spUserName);
                }
                break;
            case CHANGE_QQ:
                if (data != null) {
                    new_info = data.getStringExtra("qq");
                    if (TextUtils.isEmpty(new_info)) {
                        return;
                    }
                    tv_qq.setText(new_info);
                    //更新数据库中签名字段
                    DBUtils.getInstance(ActivityUserInfoActivity.this).updateUserInfo("qq",
                            new_info, spUserName);
                }
                break;
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_back:
                this.finish();
                break;

            case R.id.rl_nickName:
                String name = tvNickName.getText().toString();
                Bundle bdName = new Bundle();
                bdName.putString("content",name);
                bdName.putString("title","昵称");
                bdName.putInt("flag",1);
                enterActivityForResult(ActivityChangeUserInfoActivity.class,CHANGE_NICKNAME,bdName);
                break;

            case R.id.rl_sex:
                String sex = tvSex.getText().toString();
                sexDialog(sex);
                break;

            case R.id.rl_signature:
                String signature = tvSignature.getText().toString();
                Bundle bdSignature = new Bundle();
                bdSignature.putString("content",signature);
                bdSignature.putString("title","签名");
                bdSignature.putInt("flag",2);
                enterActivityForResult(ActivityChangeUserInfoActivity.class,CHANGE_SIGNATURE,bdSignature);
                break;

            case R.id.rl_qq:
                String qq = tv_qq.getText().toString();
                Bundle bdqq = new Bundle();
                bdqq.putString("content",qq);
                bdqq.putString("title","QQ号码");
                bdqq.putInt("flag",3);
                enterActivityForResult(ActivityChangeUserInfoActivity.class,CHANGE_QQ,bdqq);
                break;
        }
    }
}
