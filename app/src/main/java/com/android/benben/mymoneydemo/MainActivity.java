package com.android.benben.mymoneydemo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.litepal.crud.DataSupport;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    @InjectView(R.id.main_img)
    ImageView mImg;
    @InjectView(R.id.main_recycler)
    RecyclerView mRecycler;
    private static final int ADD_ITEM = 0;
    @InjectView(R.id.main_all)
    TextView mAll;
    @InjectView(R.id.main_use)
    TextView mUse;
    @InjectView(R.id.f_main_change_bsv)
    BenSportView mBsv;
    @InjectView(R.id.i_date)
    TextView iDate;
    @InjectView(R.id.i_money)
    TextView iMoney;
    @InjectView(R.id.i_names)
    TextView iNames;
    private int allMoney;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        bindData();

    }

    private void initMoney() {
        mAll.setText("总金额\n" + 3000);
        mUse.setText("已使用\n" + allMoney);

        if (allMoney != 0) {
            int size = allMoney / 30;
            if (size > 100) {
                mBsv.setNumber(100);
            } else {
                mBsv.setNumber(size);
            }
        } else {
            mBsv.setNumber(0);
        }
    }

    private void bindData() {
        final List<ItemInfo> allNews = DataSupport.findAll(ItemInfo.class);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        final MainAdapter mAdapter = new MainAdapter(this, allNews);
        mRecycler.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new MainAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
            }

            @Override
            public void onItemLongClick(View view, int position) {
                final ItemInfo info = allNews.get(position);
                String name = "";
                for (int i = 0; i < info.getNames().size(); i++) {
                    String s = info.getNames().get(i);
                    name += s+" ";
                }
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle("提示");
                dialog.setMessage("确认删除\n"+"时间:"+info.getDate()+"\n"+"金额:"+info.getMoney()+"\n"+"人员:"+ name+"\n这条数据");
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        DataSupport.deleteAll(ItemInfo.class, "date=? and money=?", info.getDate(), info.getMoney() + "");
//                       mAdapter.notifyDataSetChanged();
                        bindData();
                        Toast.makeText(MainActivity.this, "删除完成", Toast.LENGTH_SHORT).show();
                    }
                });

                dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, "取消删除", Toast.LENGTH_SHORT).show();
                    }
                });

                dialog.create();
                dialog.show();
            }
        });
        allMoney = 0;
        for (int i = 0; i < allNews.size(); i++) {
            int money = allNews.get(i).getMoney();
            allMoney += money;
        }
        initMoney();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        bindData();
    }


    @OnClick(R.id.main_img)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.main_img:
                startActivityForResult(new Intent(MainActivity.this, AddTimeActivity.class), ADD_ITEM);
                break;
        }
    }
}
