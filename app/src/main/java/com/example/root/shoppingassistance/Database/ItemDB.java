package com.example.root.shoppingassistance.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.root.shoppingassistance.Model.Item;

/**
 * Created by vibodha on 1/30/18.
 */

public class ItemDB {

    private static ItemDB itemDB = null;
    private DatabaseConnector databaseConnector;
    Context context;

    //Item table
    private final static String Table_Item="item";
    //item table columns
    private final static String Key_Item_ID="id";
    private final static String Key_Item_Name="name";
    private final static String Key_Item_type_ID="type_id";
    private final static String Key_Item_Price="price";
    private final static String Key_Item_Purchase_Date="date_of_purchace";

    //Item type table
    private final static String Table_Item_Type="item_type";
    //item type table columns
    private final static String Key_Item_Type_Name="item_type_name";

    //Attribute table
    private final static String Table_Attribute="attribute";
    //attribute table columns
    private final static String Key_Attribute_ID="attribute_id";
    private final static String Key_Attribute_Name="attribute_name";

    //Attribute type table
    private final static String Table_Attribute_Type="attribute_type";
    //attribute table columns
    private final static String Key_Attribute_Type_ID="attribute_type_id";
    private final static String Key_Attribute_type_Name="attribute_type_name";

    //ItemAttribute table
    private final static String Table_Item_Attribute="item_attribute";

    //ItemTypeAttributeType table
    private final static String Table_Item_Type_Attribute_type="item_type_attribute_type";

    //Shop table
    private final static String Table_Shop="shop";
    //shop table columns
    private final static String Key_Shop_ID="shop_id";
    private final static String Key_Shop_Name="shop_name";
    private final static String Key_Shop_Address="shop_address";

    //ItemShop table
    private final static String Table_item_shop="item_shop";


    private ItemDB(Context context) {
        this.context = context;
        databaseConnector = DatabaseConnector.getInstance(context);
    }
    //Singleton
    public static ItemDB getInstance(Context context){

        if(itemDB ==null){
            itemDB =new ItemDB(context);
        }
        return itemDB;
    }

    public boolean addItem(Item item) {
        long result;
        String query;
        int itemTypeID=0;
        int itemID=0;

        SQLiteDatabase dbWrite = databaseConnector.getWritableDatabase();
        SQLiteDatabase dbRead = databaseConnector.getReadableDatabase();

        ContentValues itemContent = new ContentValues();
        ContentValues itemTypeContent = new ContentValues();
        ContentValues shopContent = new ContentValues();
        ContentValues attributeContent = new ContentValues();
        ContentValues attributeTypeContent = new ContentValues();

        //add item type
        itemTypeContent.put(Key_Item_Type_Name,item.getCategory());
        result = dbWrite.insert(Table_Item_Type,null,itemTypeContent);
        if(result>0){
            query = "Select Max("+Key_Item_type_ID+") as max_id from "+Table_Item_Type+";";
            Cursor cursor=dbRead.rawQuery(query,null);
            if(cursor.moveToNext()){
                itemTypeID=cursor.getInt(cursor.getColumnIndex("max_id"));
            }
        }
        else{
            return false;
        }

        //add item
        itemContent.put(Key_Item_type_ID,String.valueOf(itemTypeID));
        itemContent.put(Key_Item_Name,item.getName());
        itemContent.put(Key_Item_Price,item.getPrice());
        itemContent.put(Key_Item_Purchase_Date,item.getDateOfPurchace().toString());
        result = dbWrite.insert(Table_Item,null,itemContent);
        if(result>0){
            query = "Select Max("+Key_Item_ID+") as max_id from "+Table_Item+";";
            Cursor cursor=dbRead.rawQuery(query,null);
            if(cursor.moveToNext()){
                itemID=cursor.getInt(cursor.getColumnIndex("max_id"));
            }
        }
        else{
            return false;
        }

        return true;
    }

}
