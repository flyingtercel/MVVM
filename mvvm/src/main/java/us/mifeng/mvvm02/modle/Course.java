package us.mifeng.mvvm02.modle;

import android.databinding.ObservableField;

/**
 * Created by 黑夜之火 on 2018/3/19.
 */

public class Course {
    public final ObservableField<String> cName= new ObservableField<>();
    public final ObservableField<Integer> cId = new ObservableField<>();
}
