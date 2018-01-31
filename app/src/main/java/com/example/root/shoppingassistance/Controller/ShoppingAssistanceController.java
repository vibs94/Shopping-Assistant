package com.example.root.shoppingassistance.Controller;

import com.example.root.shoppingassistance.Model.Attribute;
import com.example.root.shoppingassistance.Model.Item;
import com.example.root.shoppingassistance.Model.Shop;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by vibodha on 1/31/18.
 */

public class ShoppingAssistanceController {

    List<Item> items = new ArrayList<Item>();
    int i;

    public ShoppingAssistanceController(String category) throws ParseException {
        getItems(category);
    }

    public List<Item> getItems(String category) throws ParseException {

        if (category.equals("trouser")) {
            List<Item> items = new ArrayList<Item>();

            Item item1 = new Item();
            item1.setCategory("trouser");
            item1.setName("regular demin");
            item1.setPrice(3000);
            String sDate = "2018-01-31";
            Date date = new SimpleDateFormat("yyyy-mm-dd").parse(sDate);
            item1.setDateOfPurchace(date);
            Shop shop = new Shop();
            shop.setShopName("Nolimit");
            shop.setAddress("752,Galle Rd,Colombo 06");
            item1.setShop(shop);
            Attribute attribute = new Attribute("gender", "male", 1);
            item1.addAttribute(attribute);
            attribute = new Attribute("category", "denim", 4);
            item1.addAttribute(attribute);
            attribute = new Attribute("type", "regular", 2);
            item1.addAttribute(attribute);
            attribute = new Attribute("size", "32", 3);
            item1.addAttribute(attribute);

            items.add(item1);

            Item item2 = new Item();
            item2.setCategory("trouser");
            item2.setName("casual regular");
            item2.setPrice(3000);
            item2.setDateOfPurchace(date);
            item2.setShop(shop);
            attribute = new Attribute("gender", "male", 1);
            item2.addAttribute(attribute);
            attribute = new Attribute("category", "casual", 4);
            item2.addAttribute(attribute);
            attribute = new Attribute("type", "regular", 2);
            item2.addAttribute(attribute);
            attribute = new Attribute("size", "32", 3);
            item2.addAttribute(attribute);

            return items;
        }
        else{
            return null;
        }

    }

    public String nextQuestion(int index,String lastResponse){

        String nextQ = "invalid";
        if (items==null||items.isEmpty()){
            return nextQ;
        }
        else{

            for (i=0;i<items.size();i++){
                if(items.get(i).getAttributes().get(index-1).getAttributeName().equals(lastResponse)){
                    return items.get(i).getAttributes().get(index).getAttributeType();
                }
            }
            return nextQ;

        }
    }

}
