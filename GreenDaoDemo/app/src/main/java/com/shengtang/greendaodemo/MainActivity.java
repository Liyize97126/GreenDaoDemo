package com.shengtang.greendaodemo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.shengtang.greendaodemo.dao.DaoMaster;
import com.shengtang.greendaodemo.dao.DaoSession;
import com.shengtang.greendaodemo.dao.UserBeanDao;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.tv_show_info)
    protected TextView mTvShowInfo;
    private View view;
    private EditText name, age;
    private UserBeanDao userBeanDao;
    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //绑定ButterKnife的Activity对象
        ButterKnife.bind(this);
        //弹窗对象信息
        view = LayoutInflater.from(this).inflate(R.layout.layout_dialog_view, null, false);
        name = view.findViewById(R.id.et_name);
        age = view.findViewById(R.id.et_age);
        alertDialog = new AlertDialog.Builder(this)
                .setTitle("输入添加信息")
                .setView(view)
                .setNegativeButton("取消", (dialog, which) -> {
                })
                .setPositiveButton("确定", (dialog, which) -> {
                    UserBean userBean = new UserBean();
                    userBean.setId(1L);
                    userBean.setName(name.getText().toString());
                    userBean.setAge(Integer.parseInt(age.getText().toString()));
                    long insert = userBeanDao.insertOrReplace(userBean);
                    Toast.makeText(MainActivity.this, "状态：" + insert, Toast.LENGTH_LONG).show();
                })
                .create();
        //初始化数据库
        DaoSession daoSession = DaoMaster.newDevSession(this, UserBeanDao.TABLENAME);
        userBeanDao = daoSession.getUserBeanDao();
    }

    @OnClick(R.id.bt_add)
    protected void addData() {
        alertDialog.show();
    }

    @OnClick(R.id.bt_delete)
    protected void deleteData() {

    }

    @OnClick(R.id.bt_update)
    protected void updateData() {

    }

    @OnClick(R.id.bt_query)
    protected void queryData() {
        List<UserBean> userBeans = userBeanDao.loadAll();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < userBeans.size(); i++) {
            stringBuilder.append(userBeans.get(i).toString());
        }
        mTvShowInfo.setText(stringBuilder.toString());
    }
}