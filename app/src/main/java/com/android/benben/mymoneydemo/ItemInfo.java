package com.android.benben.mymoneydemo;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Time      2017/5/23 21:28 .
 * Author   : LiYuanXiong.
 * Content  :
 */

public class ItemInfo extends DataSupport {
    private String date;
    private int money;
    private List<String> names = new ArrayList<>();

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public List<String> getNames() {
        return names;
    }

    public void setNames(List<String> names) {
        this.names = names;
    }
}
