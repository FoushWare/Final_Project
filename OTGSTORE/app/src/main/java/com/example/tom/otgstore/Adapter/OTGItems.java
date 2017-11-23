package com.example.tom.otgstore.Adapter;

/**
 * Created by fouad on 10/4/17.
 */

/**
 *There we gather the item contents in one class to utilize this in the MainActivity
 * when we make listener to the database and get back the new child add
 * and sent it to the adapter
 * to display it
 * */

public class OTGItems {
   //the content of the item
   private String name;
   private String quantity;
   private String price;
   private String photoUrl;
  //constructor
   public OTGItems(){}
   //constructor to build the item content
   public OTGItems(String name, String quantity,String price, String photoUrl){
       this.name=name;
       this.quantity=quantity;
       this.price=price;
       this.photoUrl=photoUrl;
   }
   //get the content of the items ,I used getter cause they are private
   public String getName(){return name;}
   public String getQuantity(){return quantity;}
   public String getPrice(){return price;}
   public String getPhotoUrl(){return photoUrl;}
   //set the content of the items , I used setter cause they are private
   public void setName(String name){this.name=name;}
   public void setQuantity(String quantity){this.quantity=quantity;}
   public void setPrice(String price){this.price=price;}
   public void setPhotoUrl(String photoUrl){this.photoUrl=photoUrl;}

}
