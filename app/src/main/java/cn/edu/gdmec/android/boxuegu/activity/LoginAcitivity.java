package cn.edu.gdmec.android.boxuegu.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import cn.edu.gdmec.android.boxuegu.R;

public class LoginAcitivity extends AppCompatActivity {
    //标题
    private TextView tv_main_title;
    //返回、立即注册和找回密码的文本
    private TextView tv_back,tv_register,tv_find_psw;
    //登录按钮
    private Button btn_login;
    //保存用户名、密码和已保存的密码的字符串
    private String userName,psw,spPsw;
    //用户名和密码的输入值
    private EditText et_user_name,et_psw;

    private void init(){
        tv_main_title = (TextView) findViewById(R.id.tv_main_title);
        tv_main_title.setText("登录");
        tv_back = (TextView) findViewById(R.id.tv_back);
        tv_register = (TextView) findViewById(R.id.tv_register);
        tv_find_psw = (TextView) findViewById(R.id.tv_find_psw);
        btn_login = (Button) findViewById(R.id.btn_login);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_acitivity);
    }
}
