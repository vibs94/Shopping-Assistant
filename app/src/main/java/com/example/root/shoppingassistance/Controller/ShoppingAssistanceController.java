package com.example.root.shoppingassistance.Controller;


import com.example.root.shoppingassistance.Model.Attribute;
import com.example.root.shoppingassistance.Model.Item;
import com.example.root.shoppingassistance.Model.Shop;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;


public class ShoppingAssistanceController {

    List<Item> items = new ArrayList<Item>();
    List<Item> categoryItems;
    List<Item> orderedItems;
    List<Item> removedItems;
    List<Item> randomItems;
    List<Item> cart;
    int i;
    double range;

    private static ShoppingAssistanceController shoppingAssistanceController=  null;

    // Singleton
    private ShoppingAssistanceController() throws ParseException {
        getItems();
        categoryItems = new ArrayList<Item>();
        cart  = new ArrayList<Item>();
    }

    // Get an instance of ShoppingAssistanceController
    public static ShoppingAssistanceController getInstance(){
        if(shoppingAssistanceController==null){
            try {
                shoppingAssistanceController=new ShoppingAssistanceController();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return shoppingAssistanceController;
    }

    public void init() throws ParseException {
        getItems();
        categoryItems = new ArrayList<Item>();
        cart  = new ArrayList<Item>();
    }

    // Return the list of all the items
    public List<Item> getItems() throws ParseException {

            Item item1 = new Item();
            item1.setCategory("shirt");
            item1.setName("formal shirt");
            item1.setPrice(3000);
            String sDate = "2018-01-31";
            Date date = new SimpleDateFormat("yyyy-mm-dd").parse(sDate);
            item1.setDateOfPurchace(date);
            Shop shop = new Shop();
            shop.setShopName("Nolimit");
            shop.setAddress("752,Galle Rd,Colombo 06");
            item1.setShop(shop);
            Attribute attribute = new Attribute("type ? formal or casual", "formal", 1);
            item1.addAttribute(attribute);
            attribute = new Attribute("size", "medium", 2);
            item1.addAttribute(attribute);
            item1.setNoOfPurchaces(1);

            items.add(item1);


            Item item2 = new Item();
            item2.setCategory("shirt");
            item2.setName("casual shirt");
            item2.setPrice(3000);
            item2.setDateOfPurchace(date);
            Shop shop2 = new Shop();
            shop2.setShopName("Fashion Bug");
            shop2.setAddress("25,Galle Rd,Moratuwa");
            item2.setShop(shop2);
            attribute = new Attribute("type ? formal or casual", "casual", 1);
            item2.addAttribute(attribute);
            attribute = new Attribute("size", "large", 2);
            item2.addAttribute(attribute);
            item2.setNoOfPurchaces(2);

            items.add(item2);

            Item item3 = new Item();
            item3.setCategory("mobile");
            item3.setName("smart phone");
            item3.setPrice(74000.00);
            item3.setDateOfPurchace(date);
            Shop shop3 = new Shop();
            shop3.setShopName("IDealz");
            shop3.setAddress("6,Liberty Plaza,Colombo 4");
            item3.setShop(shop3);
            attribute = new Attribute("kind ? smart phone or normal phone", "smartphone", 1);
            item3.addAttribute(attribute);
            attribute = new Attribute("brand", "apple", 2);
            item3.addAttribute(attribute);
            attribute = new Attribute("model", "iphone 6", 3);
            item3.addAttribute(attribute);
            item3.setNoOfPurchaces(1);

            items.add(item3);

            Item item4 = new Item();
            item4.setCategory("mobile");
            item4.setName("smart phone");
            item4.setPrice(73000.00);
            item4.setDateOfPurchace(date);
            Shop shop4 = new Shop();
            shop4.setShopName("Greenware");
            shop4.setAddress("61,Liberty Plaza,Colombo 4");
            item4.setShop(shop4);
            attribute = new Attribute("kind ? smart phone or normal phone", "smartphone", 1);
            item4.addAttribute(attribute);
            attribute = new Attribute("brand", "apple", 2);
            item4.addAttribute(attribute);
            attribute = new Attribute("model", "iphone 6", 3);
            item4.addAttribute(attribute);
            item4.setNoOfPurchaces(2);

            items.add(item4);

            Item item5 = new Item();
            item5.setCategory("mobile");
            item5.setName("samsung smart phone");
            item5.setPrice(3000);
            item5.setDateOfPurchace(date);
            Shop shop5 = new Shop();
            shop5.setShopName("Greenware");
            shop5.setAddress("61,Liberty Plaza,Colombo 4");
            item5.setShop(shop5);
            attribute = new Attribute("kind ? smart phone or normal phone", "smartphone", 1);
            item5.addAttribute(attribute);
            attribute = new Attribute("brand", "samsung", 2);
            item5.addAttribute(attribute);
            attribute = new Attribute("model", "S 6", 3);
            item5.addAttribute(attribute);
            item5.setNoOfPurchaces(1);

            items.add(item5);

            Item item6 = new Item();
            item6.setCategory("mobile");
            item6.setName("smart phone");
            item6.setPrice(76000.00);
            item6.setDateOfPurchace(date);
            Shop shop6 = new Shop();
            shop6.setShopName("Greenware");
            shop6.setAddress("61,Liberty Plaza,Colombo 4");
            item6.setShop(shop6);
            attribute = new Attribute("kind ? smart phone or normal phone", "smartphone", 1);
            item6.addAttribute(attribute);
            attribute = new Attribute("brand", "apple", 2);
            item6.addAttribute(attribute);
            attribute = new Attribute("model", "iphone 6", 3);
            item6.addAttribute(attribute);
            item6.setNoOfPurchaces(2);

            items.add(item6);

            return items;


    }

    // Get the next question for the given response
    public String nextQuestion(int index,String lastResponse){

        String nextQ = "invalid";
        Item removedItem;
        if (categoryItems==null||categoryItems.isEmpty()){
            return nextQ;
        }
        else{
            if(categoryItems.size()>0) {
                for (i = 0; i < categoryItems.size(); i++) {
                    if (categoryItems.get(i).getAttributes().get((index-1)).getAttributeName().equals(lastResponse)) {
                        if (index < categoryItems.get(i).getAttributes().size()) {
                            nextQ = categoryItems.get(i).getAttributes().get(index).getAttributeType();
                            for (int j = 0; j < categoryItems.size(); j++) {
                                if (!categoryItems.get(j).getAttributes().get((index-1)).getAttributeName().equals(lastResponse)) {
                                    removedItem = categoryItems.remove(j);
                                    removedItems.add(0,removedItem);
                                    j--;
                                    i--;
                                }
                            }
                            return nextQ;
                        } else {
                            return "success";
                        }
                    }
                }

            }
            return nextQ;
        }
    }

    // Get the first question for the given category
    public String getFirstQ(){
        return categoryItems.get(0).getAttributes().get(0).getAttributeType();
    }

    // Get the list of items for given category
    public List<Item> getItemsOfCategory(String s) {
        categoryItems = new ArrayList<Item>();
        removedItems = new ArrayList<Item>();
        for(i=0;i<items.size();i++){
            if(items.get(i).getCategory().equals(s)){
                categoryItems.add(items.get(i));
            }
        }

        return categoryItems;
    }

    // Get the suggestions based on the responses
    public List<Item> getCategoryItems() {

        orderedItems =  new ArrayList<Item>();
        List<Double> scores = new ArrayList<Double>();

        for(int i = 0;i<categoryItems.size();i++){
            scores.add(getItemScore(categoryItems.get(i)));
        }

        int index;
        for(int i = 0;i<scores.size();i++){
            index = scores.indexOf(Collections.max(scores));
            if(categoryItems.get(index).getPrice()<=range){
                orderedItems.add(categoryItems.get(index));
            }
            scores.set(index,Double.NEGATIVE_INFINITY);
        }

        return orderedItems;
    }

    // Get the priority score for a given item
    private double getItemScore(Item item){

        double score;
        double currentDate = (double) (new Date().getTime()-(item.getDateOfPurchace().getTime()))/Math.pow(10.0,7.0);

        score = currentDate*item.getNoOfPurchaces()/item.getPrice();

        return score;
    }

    // Get the items in the cart
    public List<Item> getCart(){
        return cart;
    }

    // Add an item to the cart
    public void addToCart(int index){
        if(index<=orderedItems.size()) {
            cart.add(orderedItems.get(index - 1));
        }
        else{
            cart.add(randomItems.get(index-orderedItems.size()-1));
        }
    }

    // Set the range for a given item
    public void setRange(double range){
        this.range = range;
    }

    // Generate random list of items
    public List<Item> getRandomItems(){
        randomItems = new ArrayList<Item>();
        for(int i=0;i<removedItems.size();i++){
            if(removedItems.get(i).getPrice()<=range){
                randomItems.add(removedItems.get(i));
            }
        }
        return randomItems;
    }

    private Item getItem(String itemName){
        for(int i=0;i<items.size();i++){
            if(items.get(i).getName().equals(itemName)){
                return items.get(i);
            }
        }
        return null;
    }

    public boolean addToCart(String itemName){
        Item item = getItem(itemName);
        if (item!=null){
            cart.add(item);
            return true;
        }
        else {
            return false;
        }
    }
}
