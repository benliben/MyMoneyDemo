package com.android.benben.mymoneydemo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Time      2017/5/24 11:43 .
 * Author   : LiYuanXiong.
 * Content  :
 */
public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MyViewHolder> {
    private Context mContext;
    private List<ItemInfo> mData;
    private LayoutInflater mInflater;

    public MainAdapter(Context mContext, List<ItemInfo> mData) {
        this.mContext = mContext;
        this.mData = mData;
        this.mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(mInflater.inflate(R.layout.i_main, parent,false));
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        if (mData != null) {
            final ItemInfo info = mData.get(position);
            holder.mDate.setText(info.getDate());
            holder.mMoney.setText(info.getMoney()+"");
            String name = "";
            for (int i = 0; i < info.getNames().size(); i++) {
                String s = info.getNames().get(i);
                name += s+" ";
            }
            holder.mNames.setText(name);

            if (mOnItemClickListener != null) {
                holder.mLl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnItemClickListener.onItemLongClick(holder.mLl, position);
                    }
                });
            }


            final String finalName = name;
            holder.mLl.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView mDate;
        private TextView mMoney;
        private TextView mNames;
        private LinearLayout mLl;
        public MyViewHolder(View itemView) {
            super(itemView);
            mDate = (TextView) itemView.findViewById(R.id.i_date);
            mMoney = (TextView) itemView.findViewById(R.id.i_money);
            mNames = (TextView) itemView.findViewById(R.id.i_names);
            mLl = (LinearLayout) itemView.findViewById(R.id.i_ll);
        }
    }

    public interface OnItemClickListener{
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }
}
