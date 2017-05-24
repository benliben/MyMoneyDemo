package com.android.benben.mymoneydemo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.method.DigitsKeyListener;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Time      2017/5/23 21:18 .
 * Author   : LiYuanXiong.
 * Content  :
 */
public class AddTimeActivity extends AppCompatActivity {
    @InjectView(R.id.add_date_button)
    Button mDateButton;
    @InjectView(R.id.add_money)
    EditText mMoney;
    @InjectView(R.id.add_cb_lyx)
    CheckBox mCbLyx;
    @InjectView(R.id.add_cb_wgb)
    CheckBox mCbWgb;
    @InjectView(R.id.add_cb_lxc)
    CheckBox mCbLxc;
    @InjectView(R.id.add_cb_cs)
    CheckBox mCbCs;
    @InjectView(R.id.add_cb_zsw)
    CheckBox mCbZsw;
    @InjectView(R.id.add_cb_zpp)
    CheckBox mCbZpp;
    @InjectView(R.id.add_cb_lzq)
    CheckBox mCbLzq;
    @InjectView(R.id.add_confirm)
    Button mConfirm;
    private String dates;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        ButterKnife.inject(this);
        mMoney.setKeyListener(DigitsKeyListener.getInstance("0123456789"));


    }

    @OnClick({R.id.add_date_button, R.id.add_money, R.id.add_cb_lyx, R.id.add_cb_wgb, R.id.add_cb_lxc, R.id.add_cb_cs, R.id.add_cb_zsw, R.id.add_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.add_date_button:
                checkDate();
                break;
            case R.id.add_money:
                String s = mMoney.getText().toString();
                break;
            case R.id.add_cb_lyx:
                break;
            case R.id.add_cb_wgb:
                break;
            case R.id.add_cb_lxc:
                break;
            case R.id.add_cb_cs:
                break;
            case R.id.add_cb_zsw:
                break;
            case R.id.add_confirm:
                confirm();
                break;
        }
    }

    private void confirm() {
        List<String> names = new ArrayList<>();
        if (mCbLyx.isChecked()) {
            names.add("name1");
        }
        if (mCbWgb.isChecked()) {
            names.add("name2");
        }
        if (mCbLxc.isChecked()) {
            names.add("name3");
        }
        if (mCbCs.isChecked()) {
            names.add("name4");
        }
        if (mCbZsw.isChecked()) {
            names.add("name5");
        }
        if (mCbZpp.isChecked()) {
            names.add("name6");
        }
        if (mCbLzq.isChecked()) {
            names.add("name7");
        }
        int money;
        if (mMoney.getText().toString().equals("")) {
            Toast.makeText(this, "请输入金额", Toast.LENGTH_SHORT).show();
            return;
        } else {
            money = Integer.parseInt(mMoney.getText().toString());
        }
        ItemInfo data = new ItemInfo();
        if (dates == null) {
            Toast.makeText(this, "请选择日期", Toast.LENGTH_SHORT).show();
            return;
        }

        if (names.size() == 0) {
            Toast.makeText(this, "请选择人员", Toast.LENGTH_SHORT).show();
            return;
        }

        data.setDate(dates);
        data.setMoney(money);
        data.setNames(names);
        data.save();
        if (data.save()) {
            Toast.makeText(this, "成功", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "失败", Toast.LENGTH_SHORT).show();
        }
        setResult(RESULT_OK);

    }

    private void checkDate() {
        LayoutInflater inflater = LayoutInflater.from(AddTimeActivity.this);
        final View view = inflater.inflate(R.layout.d_date, null);

        final AlertDialog.Builder dialog = new AlertDialog.Builder(AddTimeActivity.this);
        dialog.setTitle("时间选择器");
        dialog.setView(view);

        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DatePicker date = (DatePicker) view.findViewById(R.id.add_date);
                int year = date.getYear();
                int month = date.getMonth() + 1;
                int day = date.getDayOfMonth();
                dates = year + "-" + month + "-" + day;
                mDateButton.setText(dates);

                Toast.makeText(AddTimeActivity.this, "你点击了确定", Toast.LENGTH_SHORT).show();
            }
        });
        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.create();
        dialog.show();

    }
}
