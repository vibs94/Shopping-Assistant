package com.example.root.shoppingassistance.ListAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.root.shoppingassistance.Model.Item;
import com.example.root.shoppingassistance.R;

import java.util.List;


public class ItemListAdapter extends BaseAdapter{


    Context context;
    LayoutInflater inflter;
    List<Item> categoryItems;

    public ItemListAdapter(Context context,List<Item> items){
        this.context = context;
        this.categoryItems = items;
        inflter = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return categoryItems.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        view = inflter.inflate(R.layout.item_layout,null);
        TextView itemName = (TextView) view.findViewById(R.id.item_name);
        TextView itemPrice = (TextView) view.findViewById(R.id.item_price);
        TextView itemShop = (TextView) view.findViewById(R.id.item_shop);
        itemName.setText(categoryItems.get(i).getName());
        itemPrice.setText(String.valueOf(categoryItems.get(i).getPrice()));
        itemShop.setText(categoryItems.get(i).getShop().getShopName()+", "+categoryItems.get(i).getShop().getAddress());
        return view;
    }
}
