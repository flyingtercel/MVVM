package us.mifeng.mvvm02;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import us.mifeng.mvvm02.databinding.ActivityOneBinding;
import us.mifeng.mvvm02.modle.EventView;

public class OneActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_one);
        ActivityOneBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_one);
        binding.setEvent(new EventView() {
            @Override
            public void click(View view) {
                Toast.makeText(OneActivity.this, "你点击了按钮一", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void click1(View view) {
                Toast.makeText(OneActivity.this, "你点击了按钮二", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void click2(String ss) {
                Toast.makeText(OneActivity.this, ss, Toast.LENGTH_SHORT).show();

            }
        });

    }
}
