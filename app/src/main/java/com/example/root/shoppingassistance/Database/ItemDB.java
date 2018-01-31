package com.example.root.shoppingassistance.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.root.shoppingassistance.Model.Attribute;
import com.example.root.shoppingassistance.Model.Item;

import java.util.List;


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
    //ItemAttribute table column
    private final static String Key_Attribute_index="attribute_index";

    //ItemTypeAttributeType table
    private final static String Table_Item_Type_Attribute_type="item_type_attribute_type";
    //ItemTypeAttributeType table column
    private final static String Key_Attribute_Type_index="attribute_type_index";

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
        int attrID;
        int attrTypeID;
        int shopID=0;
        Cursor cursor;

        SQLiteDatabase dbWrite = databaseConnector.getWritableDatabase();
        SQLiteDatabase dbRead = databaseConnector.getReadableDatabase();

        ContentValues itemContent = new ContentValues();
        ContentValues itemTypeContent = new ContentValues();
        ContentValues shopContent = new ContentValues();
        ContentValues attributeContent = new ContentValues();
        ContentValues attributeTypeContent = new ContentValues();
        ContentValues itemAttributeContent;
        ContentValues itemShopContent = new ContentValues();

        //add item type
        itemTypeContent.put(Key_Item_Type_Name,item.getCategory());
        result = dbWrite.insert(Table_Item_Type,null,itemTypeContent);
        if(result>0){
            query = "Select Max("+Key_Item_type_ID+") as max_id from "+Table_Item_Type+";";
            cursor=dbRead.rawQuery(query,null);
            if(cursor.moveToNext()){
                itemTypeID=cursor.getInt(cursor.getColumnIndex("max_id"));
            }
        }
        else{
            return false;
        }

        //add item
        itemContent.put(Key_Item_type_ID,itemTypeID);
        itemContent.put(Key_Item_Name,item.getName());
        itemContent.put(Key_Item_Price,item.getPrice());
        itemContent.put(Key_Item_Purchase_Date,item.getDateOfPurchace().toString());
        result = dbWrite.insert(Table_Item,null,itemContent);
        if(result>0){
            query = "Select Max("+Key_Item_ID+") as max_id from "+Table_Item+";";
            cursor=dbRead.rawQuery(query,null);
            if(cursor.moveToNext()){
                itemID=cursor.getInt(cursor.getColumnIndex("max_id"));
            }
        }
        else{
            return false;
        }

        //add attributes
        List<Attribute> attributes = item.getAttributes();
        for(int i=0;i<attributes.size();i++) {
            //add attribute
            query = "select "+Key_Attribute_ID+" from "+Table_Attribute+" where "+Key_Attribute_Name+" = "+attributes.get(i).getAttributeName()+";";
            cursor = dbRead.rawQuery(query,null);
            if(cursor.moveToNext()){
                attrID = cursor.getInt(cursor.getColumnIndex(Key_Attribute_ID));
                itemAttributeContent = new ContentValues();
                itemAttributeContent.put(Key_Item_ID,itemID);
                itemAttributeContent.put(Key_Attribute_ID,attrID);
                itemAttributeContent.put(Key_Attribute_index,attributes.get(i).getAttributeIndex());
                result = dbWrite.insert(Table_Item_Attribute,null,itemAttributeContent);
                if (!(result>0)){
                    return  false;
                }
            }
            else{
                attributeContent.put(Key_Attribute_Name, attributes.get(i).getAttributeName());
                result = dbWrite.insert(Table_Attribute,null,attributeContent);
                if(result>0){
                    query = "Select Max("+Key_Attribute_ID+") as max_id from "+Table_Attribute+";";
                    cursor=dbRead.rawQuery(query,null);
                    if(cursor.moveToNext()){
                        attrID=cursor.getInt(cursor.getColumnIndex("max_id"));
                        itemAttributeContent = new ContentValues();
                        itemAttributeContent.put(Key_Item_ID,itemID);
                        itemAttributeContent.put(Key_Attribute_ID,attrID);
                        itemAttributeContent.put(Key_Attribute_index,attributes.get(i).getAttributeIndex());
                        result = dbWrite.insert(Table_Item_Attribute,null,itemAttributeContent);
                        if (!(result>0)){
                            return  false;
                        }
                    }
                }
                else {
                    return false;
                }

            }
            //add attribute type
            query = "select "+Key_Attribute_Type_ID+" from "+Table_Attribute_Type+" where "+Key_Attribute_type_Name+" = "+attributes.get(i).getAttributeType()+";";
            cursor = dbRead.rawQuery(query,null);
            if(cursor.moveToNext()){
                attrTypeID = cursor.getInt(cursor.getColumnIndex(Key_Attribute_Type_ID));
                itemAttributeContent = new ContentValues();
                itemAttributeContent.put(Key_Item_type_ID,itemTypeID);
                itemAttributeContent.put(Key_Attribute_Type_ID,attrTypeID);
                itemAttributeContent.put(Key_Attribute_Type_index,attributes.get(i).getAttributeIndex());
                result = dbWrite.insert(Table_Item_Type_Attribute_type,null,itemAttributeContent);
                if (!(result>0)){
                    return  false;
                }
            }
            else{
                attributeTypeContent.put(Key_Attribute_type_Name, attributes.get(i).getAttributeType());
                result = dbWrite.insert(Table_Attribute_Type,null,attributeTypeContent);
                if(result>0){
                    query = "Select Max("+Key_Attribute_Type_ID+") as max_id from "+Table_Attribute_Type+";";
                    cursor=dbRead.rawQuery(query,null);
                    if(cursor.moveToNext()){
                        attrTypeID=cursor.getInt(cursor.getColumnIndex("max_id"));
                        itemAttributeContent = new ContentValues();
                        itemAttributeContent.put(Key_Item_type_ID,itemTypeID);
                        itemAttributeContent.put(Key_Attribute_Type_ID,attrTypeID);
                        itemAttributeContent.put(Key_Attribute_Type_index,attributes.get(i).getAttributeIndex());
                        result = dbWrite.insert(Table_Item_Type_Attribute_type,null,itemAttributeContent);
                        if (!(result>0)){
                            return  false;
                        }
                    }
                }
                else {
                    return false;
                }

            }
        }

        //add shop
        shopContent.put(Key_Shop_Name,item.getShop().getShopName());
        shopContent.put(Key_Shop_Address,item.getShop().getAddress());
        result = dbWrite.insert(Table_Shop,null,shopContent);
        if (result>0) {
            query = "Select Max(" + Key_Shop_ID + ") as max_id from " + Table_Shop + ";";
            cursor = dbRead.rawQuery(query, null);
            if (cursor.moveToNext()) {
                shopID = cursor.getInt(cursor.getColumnIndex("max_id"));
                itemShopContent.put(Key_Shop_ID, shopID);
                itemShopContent.put(Key_Item_ID,itemID);
                result = dbWrite.insert(Table_item_shop,null,itemShopContent);
                if(!(result>0)){
                    return false;
                }
            }
        }
            return true;
        }

}
