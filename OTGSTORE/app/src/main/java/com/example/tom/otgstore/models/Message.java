package com.example.tom.otgstore.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by foush on 11/18/17.
 */


    public class Message implements Parcelable{

        @SerializedName("id")
        private String id;
        @SerializedName("content")
        private String content;
        @SerializedName("name")
        private String name;
        @SerializedName("quantity")
        private String quantity;
        @SerializedName("price")
        private String price;
        @SerializedName("photoUrl")
        private String photoUrl;

        public String getName(){return name;}
        public String getQuantity(){return quantity;}
        public String getPrice(){return price;}
        public String getPhotoUrl(){return photoUrl;}
        public String getContent() {
            return content;
        }
        public String getId() {
        return id;
    }

        //set the content of the items , I used setter cause they are private
        public void setName(String name){this.name=name;}
        public void setQuantity(String quantity){this.quantity=quantity;}
        public void setPrice(String price){this.price=price;}
        public void setPhotoUrl(String photoUrl){this.photoUrl=photoUrl;}
        public void setContent(String content) {
            this.content = content;
        }









        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(id);
            dest.writeString(content);
            dest.writeString(name);
            dest.writeString(quantity);
            dest.writeString(photoUrl);
            dest.writeString(price);

        }
    }
