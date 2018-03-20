package us.mifeng.mvvm02.modle;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableList;

/**
 * Created by 黑夜之火 on 2018/3/19.
 */

public class User {
    public final ObservableList<String>list = new ObservableArrayList<>();
    public final ObservableField<String>name = new ObservableField<>();
    public final ObservableField<Integer>age = new ObservableField<>();
    public final ObservableField<Boolean>flag = new ObservableField<>();
}
