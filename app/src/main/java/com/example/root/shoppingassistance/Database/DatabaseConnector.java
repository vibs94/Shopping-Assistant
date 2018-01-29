package com.example.root.shoppingassistance.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by root on 1/28/18.
 */

public class DatabaseConnector extends SQLiteOpenHelper {

    private static final int db_version=1;
    public final static String dbName="itemdb";
    Cursor cursor;

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
    //attribute type table columns
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

    private static DatabaseConnector databaseConnector=null;

    public static DatabaseConnector getInstance(Context context){
        if(databaseConnector==null){
            databaseConnector=new DatabaseConnector(context);
        }
        return databaseConnector;
    }


    public DatabaseConnector(Context context) {
        super(context, dbName, null, db_version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        //create Item table
        String Create_Item_Table="CREATE TABLE "+Table_Item+"("
                +Key_Item_ID+" INTEGER PRIMARY KEY autoincrement,"
                +Key_Item_Name+" TEXT,"
                +Key_Item_type_ID+" INTEGER,"
                +Key_Item_Price+" FLOAT,"
                +Key_Item_Purchase_Date+" TEXT,"
                +")";

        //create Item_type table
        String Create_Item_Type_Table="CREATE TABLE "+Table_Item_Type+"("
                +Key_Item_type_ID+" INTEGER PRIMARY KEY autoincrement,"
                +Key_Item_Type_Name+" TEXT,"
                +")";

        //create Attribute table
        String Create_Attribute_Table="CREATE TABLE "+Table_Attribute+"("
                +Key_Attribute_ID+" INTEGER PRIMARY KEY autoincrement,"
                +Key_Attribute_Name+" TEXT,"
                +")";

        //create ItemAttribute table
        String Create_Item_Attribute_Table = "CREATE TABLE "+Table_Item_Attribute+"("
                +Key_Attribute_ID+" INTEGER ,"
                +Key_Item_ID+" INTEGER,"
                +Key_Attribute_index+" INTEGER,"
                +"FOREIGN KEY ("+Key_Attribute_ID+") REFERENCES "+Table_Attribute+" ("+Key_Attribute_ID+")"
                +"ON DELETE CASCADE ON UPDATE NO ACTION,"
                +"FOREIGN KEY ("+Key_Item_ID+") REFERENCES "+Table_Item+" ("+Key_Item_ID+")"
                +"ON DELETE CASCADE ON UPDATE NO ACTION,"
                +")";

        //create Attribute type table
        String Create_Attribute_Type_Table="CREATE TABLE "+Table_Attribute_Type+"("
                +Key_Attribute_Type_ID+" INTEGER PRIMARY KEY autoincrement,"
                +Key_Attribute_type_Name+" TEXT,"
                +")";

        //create ItemTypeAttributeType table
        String Create_Item_Type_Attribute_Type_Table = "CREATE TABLE "+Table_Item_Type_Attribute_type+"("
                +Key_Attribute_Type_ID+" INTEGER ,"
                +Key_Item_type_ID+" INTEGER,"
                +Key_Attribute_Type_index+" INTEGER,"
                +"FOREIGN KEY ("+Key_Attribute_Type_ID+") REFERENCES "+Table_Attribute_Type+" ("+Key_Attribute_Type_ID+")"
                +"ON DELETE CASCADE ON UPDATE NO ACTION,"
                +"FOREIGN KEY ("+Key_Item_type_ID+") REFERENCES "+Table_Item_Type+" ("+Key_Item_type_ID+")"
                +"ON DELETE CASCADE ON UPDATE NO ACTION,"
                +")";

        //create Item table
        String Create_Shop_Table="CREATE TABLE "+Table_Shop+"("
                +Key_Shop_ID+" INTEGER PRIMARY KEY autoincrement,"
                +Key_Shop_Name+" TEXT,"
                +Key_Shop_Address+" TEXT,"
                +")";

        //create ItemShop table
        String Create_Item_Shop_Table = "CREATE TABLE "+Table_item_shop+"("
                +Key_Shop_ID+" INTEGER ,"
                +Key_Item_ID+" INTEGER,"
                +"FOREIGN KEY ("+Key_Shop_ID+") REFERENCES "+Table_Shop+" ("+Key_Shop_ID+")"
                +"ON DELETE CASCADE ON UPDATE NO ACTION,"
                +"FOREIGN KEY ("+Key_Item_ID+") REFERENCES "+Table_Item+" ("+Key_Item_ID+")"
                +"ON DELETE CASCADE ON UPDATE NO ACTION,"
                +")";

        db.execSQL(Create_Item_Table);
        db.execSQL(Create_Item_Type_Table);
        db.execSQL(Create_Attribute_Table);
        db.execSQL(Create_Item_Attribute_Table);
        db.execSQL(Create_Attribute_Type_Table);
        db.execSQL(Create_Item_Type_Attribute_Type_Table);
        db.execSQL(Create_Shop_Table);
        db.execSQL(Create_Item_Shop_Table);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    //save new property
   /*public void addProperty() {
        //test////
        SQLiteDatabase testdb =this.getWritableDatabase();

        ContentValues values=new ContentValues();
        values.put(Key_Name,"Trouser");
        values.put(Key_Item_type_ID, 1);
        values.put(Key_attrib1, "Boss");
        testdb.insert(Table_Item,null, values);

        ContentValues values2=new ContentValues();
        values2.put(Key_attrib1_def,"brand");
        testdb.insert(Table_Item_Type,null, values2);

        testdb.close();

    }

    //get all the saved apartments from the database
    public ArrayList<String> viewAllItems(){
        SQLiteDatabase db =this.getReadableDatabase();
        String query="select * from item";
        cursor=db.rawQuery(query,null);
        ArrayList<String> items=new ArrayList<String>();
        while(cursor.moveToNext()){
            if (cursor.getString(cursor.getColumnIndex("name"))!=null)
                items.add(cursor.getString(cursor.getColumnIndex("name")));

        }
        return items;

    }*/

    //get matched item names
    public ArrayList<String> getItemAttribs(String name){
        SQLiteDatabase db =this.getReadableDatabase();
        ArrayList<String> itemAttribs = new ArrayList<String>();
        String query="select * from item";
        cursor=db.rawQuery(query,null);
        ArrayList<String> items=new ArrayList<String>();
        while(cursor.moveToNext()){
            if (cursor.getString(cursor.getColumnIndex("name"))!=null) {
                String itemName = cursor.getString(cursor.getColumnIndex("name"));
                if (name.contains(itemName)){
                    itemAttribs.add(itemName);
                }
            }
        }
        return itemAttribs;
    }
}
