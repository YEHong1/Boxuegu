package cn.edu.gdmec.android.boxuegu.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import cn.edu.gdmec.android.boxuegu.R;
import cn.edu.gdmec.android.boxuegu.utils.AnalysisUtils;
import cn.edu.gdmec.android.boxuegu.utils.MD5Utils;

public class ActivityFindPswActivity extends Activity implements View.OnClickListener {

    private TextView tvUserName;
    private TextView tvResetPsw;
    private TextView tv_back;
    private TextView tv_main_title;
    private TextView tv_save;
    private RelativeLayout title_bar;
    private TextView tv_user_name;
    private EditText et_user_name;
    private EditText et_validate_name;
    private EditText et_input_psw;
    private TextView tv_reset_psw;
    private Button btn_validate;
    private String validateName;

    private String from;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_psw);
        from = getIntent().getStringExtra("from");
        initView();
    }

    private EditText getEtUserName() {
        return (EditText) findViewById(R.id.et_user_name);
    }

    private EditText getEtValidateName() {
        return (EditText) findViewById(R.id.et_validate_name);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_validate:
                //TODO implement
                submit();
                break;
        }
    }

    private void initView() {
        tv_back = (TextView) findViewById(R.id.tv_back);
        tv_main_title = (TextView) findViewById(R.id.tv_main_title);
        tv_save = (TextView) findViewById(R.id.tv_save);
        title_bar = (RelativeLayout) findViewById(R.id.title_bar);
        tv_user_name = (TextView) findViewById(R.id.tv_user_name);
        et_user_name = (EditText) findViewById(R.id.et_user_name);
        et_validate_name = (EditText) findViewById(R.id.et_validate_name);
        et_input_psw = (EditText) findViewById(R.id.et_input_psw);
        tv_reset_psw = (TextView) findViewById(R.id.tv_reset_psw);
        btn_validate = (Button) findViewById(R.id.btn_validate);

        if ("security".equals(from)){
            tv_main_title.setText("设置密保");
            btn_validate.setText("设置");
        }else {
            tv_main_title.setText("找回密码");
            btn_validate.setText("设置");
            tv_user_name.setVisibility(View.VISIBLE);
            et_user_name.setVisibility(View.VISIBLE);
            tv_reset_psw.setVisibility(View.VISIBLE);
            et_input_psw.setVisibility(View.VISIBLE);
        }
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityFindPswActivity.this.finish();
            }
        });

        btn_validate.setOnClickListener(this);
    }

    private void submit() {
        // validate

        String validateName = et_validate_name.getText().toString().trim();
        if ("security".equals(from)){
            if (TextUtils.isEmpty(validateName)){
                Toast.makeText(this, "请输入要验证的姓名", Toast.LENGTH_SHORT).show();
                return;
            }else {
                Toast.makeText(this, "密保设置成功", Toast.LENGTH_SHORT).show();
                saveSecurity(validateName);
                ActivityFindPswActivity.this.finish();
            }
        }else{

            final String name = et_user_name.getText().toString().trim();
            String sp_security = readSercurity(name);
            if (TextUtils.isEmpty(name)) {
                Toast.makeText(this, "请输入您的用户名", Toast.LENGTH_SHORT).show();
                return;
            }else if(!isExistUserName(name)){
                Toast.makeText(this, "你输入的用户名不存在", Toast.LENGTH_SHORT).show();
                return;
            }else if (TextUtils.isEmpty(validateName)){
                Toast.makeText(this, "请输入要验证的姓名", Toast.LENGTH_SHORT).show();
                return;
            }else if (!validateName.equals(sp_security)){
                Toast.makeText(this, "输入的密保不正确", Toast.LENGTH_SHORT).show();
                return;
            }else {
                String newpsw = et_input_psw.getText().toString().trim();
                if(!TextUtils.isEmpty(newpsw)){
                    savePsw(name,newpsw);
                    Toast.makeText(this,"密码修改成功",Toast.LENGTH_SHORT).show();
                    ActivityFindPswActivity.this.finish();
                }else {
                    Toast.makeText(this,"请输入新密码",Toast.LENGTH_SHORT).show();
                }
            }
        }

    }

    private void savePsw(String name,String newpsw){
        String md5Psw = MD5Utils.md5(newpsw);
        SharedPreferences sp = getSharedPreferences("loginInfo",MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(name,md5Psw);
        editor.commit();
    }

    private boolean isExistUserName(String name){
        boolean hasUserName = false;
        SharedPreferences sp =getSharedPreferences("loginInfo",MODE_PRIVATE);
        String spPsw = sp.getString(name,"");
        if (!TextUtils.isEmpty(spPsw)){
            hasUserName = true;
        }
        return hasUserName;
    }

    private String readSercurity(String name){
        SharedPreferences sp =getSharedPreferences("loginInfo",MODE_PRIVATE);
        String security = sp.getString(name+"_security","");
        return security;
    }

    private void saveSecurity(String validateName){
        SharedPreferences sp =getSharedPreferences("loginInfo",MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(AnalysisUtils.readLoginUserName(this)+"_security",validateName);
        editor.commit();
    }
}
