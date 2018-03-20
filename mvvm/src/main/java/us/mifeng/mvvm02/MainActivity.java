package us.mifeng.mvvm02;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayMap;
import android.databinding.ObservableMap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import us.mifeng.mvvm02.databinding.ActivityMainBinding;
import us.mifeng.mvvm02.modle.User;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        ActivityMainBinding binding = DataBindingUtil.setContentView(MainActivity.this, R.layout.activity_main);
        User user = new User();
        binding.setUser(user);
        user.name.set("你好");
        user.age.set(23);
        user.flag.set(true);

        ObservableMap<String,String>map = new ObservableArrayMap<>();
        map.put("user","adfkajdfklj");
        map.put("sss","你没迪迪埃阿道夫");
        binding.setMap(map);


        for(int i=0;i<5;i++){
            user.list.add("哈哈哈哈哈哈哈"+i);
        }
        binding.setList(user.list);
        binding.setNum(1);

    }
}
