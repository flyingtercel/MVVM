package us.mifeng.mvvm02.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import us.mifeng.mvvm02.R;
import us.mifeng.mvvm02.bean.MKTBean;
import us.mifeng.mvvm02.databinding.CbenItemBinding;

/**
 * Created by 黑夜之火 on 2018/3/20.
 */

public class CBeanAdapter extends BaseAdapter {
    private ArrayList<MKTBean.CBean>mList;
    private Context context;

    public CBeanAdapter(ArrayList<MKTBean.CBean> mList, Context context) {
        this.mList = mList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public MKTBean.CBean getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CbenItemBinding binding;
        if (convertView == null){
            binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.cben_item, parent, false);
            convertView = binding.getRoot();
        }else{
            binding = DataBindingUtil.getBinding(convertView);
        }
        binding.setCBean(getItem(position));
        Glide.with(context).load(getItem(position).getTeacher_img()).into(binding.imagView);

        return convertView;
    }
}
