package us.mifeng.mvvm02;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import us.mifeng.mvvm02.adapter.CBeanAdapter;
import us.mifeng.mvvm02.bean.MKTBean;
import us.mifeng.mvvm02.databinding.ActivityRequestBinding;
import us.mifeng.mvvm02.retrofit.RetrofitService;

public class RequestActivity extends AppCompatActivity {
    private ArrayList<MKTBean.CBean>list = new ArrayList<>();
    private ActivityRequestBinding binding;
    private CBeanAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_request);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_request);
        requestPost();
        adapter = new CBeanAdapter(list,this);
        binding.setAdapter(adapter);

    }

    private void requestPost() {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl("http://api.dameiketang.com/")
                .build();
        RetrofitService rs = retrofit.create(RetrofitService.class);
        Observable<MKTBean> mktData = rs.getMktData("6894681b-ad8b-47e4-9f17-1cf07324464c");
        mktData.map(new Func1<MKTBean, List<MKTBean.CBean>>() {
            @Override
            public List<MKTBean.CBean> call(MKTBean mktBean) {
                return mktBean.getC();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<MKTBean.CBean>>() {
                    @Override
                    public void call(List<MKTBean.CBean> cBeen) {
                        Log.i("tag","====="+cBeen.get(0).getTeacher_name());
                        if (cBeen!=null){
                            list.clear();
                            list.addAll(cBeen);
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
    }
}
