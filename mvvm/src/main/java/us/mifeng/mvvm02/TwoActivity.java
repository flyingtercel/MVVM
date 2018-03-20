package us.mifeng.mvvm02;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import us.mifeng.mvvm02.databinding.ActivityTwoBinding;
import us.mifeng.mvvm02.modle.Course;

public class TwoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_two);
        ActivityTwoBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_two);

        ObservableList<Course>mList=new ObservableArrayList<>();
        for(int i=0;i<20;i++){
            Course course = new Course();
            course.cName.set("我是ListView上显示的数据"+i);
            course.cId.set(i);
            mList.add(course);
        }
        MyAdapter adapter = new MyAdapter(mList,this);
        binding.setMyAdapter(adapter);
        adapter.notifyDataSetChanged();

    }
}
