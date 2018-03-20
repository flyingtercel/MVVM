package us.mifeng.mvvm02;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import us.mifeng.mvvm02.databinding.ActivityLoginBinding;
import us.mifeng.mvvm02.modle.BtnClick;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_login);
        ActivityLoginBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        binding.setEvent(new BtnClick() {
            @Override
            public void onClick1(View view) {
                startActivity(new Intent(LoginActivity.this,MainActivity.class));
            }

            @Override
            public void onClick2(View view) {
                startActivity(new Intent(LoginActivity.this,OneActivity.class));
            }

            @Override
            public void onClick3(View view) {
                startActivity(new Intent(LoginActivity.this,TwoActivity.class));
            }

            @Override
            public void onClick4(View view) {
                startActivity(new Intent(LoginActivity.this,RequestActivity.class));
            }
        });

    }
}
