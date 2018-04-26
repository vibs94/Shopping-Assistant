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

public class CartListAdapter extends BaseAdapter {


    Context context;
    LayoutInflater inflter;
    List<Item> cartItems;

    public CartListAdapter(Context context,List<Item> items){
        this.context = context;
        this.cartItems = items;
        inflter = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return cartItems.size();
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
        view = inflter.inflate(R.layout.cart_layout,null);
        TextView itemName = (TextView) view.findViewById(R.id.item_name);
        TextView itemPrice = (TextView) view.findViewById(R.id.item_price);
        TextView itemShop = (TextView) view.findViewById(R.id.item_shop);
        itemName.setText(cartItems.get(i).getName());
        itemPrice.setText(String.valueOf(cartItems.get(i).getPrice()));
        itemShop.setText(cartItems.get(i).getShop().getShopName()+", "+cartItems.get(i).getShop().getAddress());
        return view;
    }
}
