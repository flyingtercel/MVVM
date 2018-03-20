package us.mifeng.mvvm02;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import us.mifeng.mvvm02.databinding.ListItemBinding;
import us.mifeng.mvvm02.modle.Course;

/**
 * Created by 黑夜之火 on 2018/3/19.
 */

public class MyAdapter extends BaseAdapter {
    ObservableList<Course>mList;
    private Context context;

    public MyAdapter(ObservableList<Course> mList, Context context) {
        this.mList = mList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Course getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ListItemBinding itemBinding;
        if (convertView == null){
            itemBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.list_item, parent, false);
            convertView = itemBinding.getRoot();
        }else{
            itemBinding = DataBindingUtil.getBinding(convertView);
        }
        itemBinding.setCourse(mList.get(position));

        return convertView;
    }
}
