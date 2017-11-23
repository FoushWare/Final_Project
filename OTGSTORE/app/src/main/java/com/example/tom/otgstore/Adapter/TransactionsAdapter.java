package com.example.tom.otgstore.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tom.otgstore.R;
import com.example.tom.otgstore.models.Message;

import java.util.List;

/**
 * Created by fouad on 10/4/17.
 */

public class TransactionsAdapter extends RecyclerView.Adapter<TransactionsAdapter.itemHolder> {


    //define the expected items that will push to the RecyclerView with our TransactionAdapter

    private List<Message> messages;
    private Context context;

    public TransactionsAdapter(Context context, List<Message> messageList) {
        this.messages=  messageList;
        this.context=context;

    }

    public class itemHolder extends RecyclerView.ViewHolder{
        TextView itemName,itemQuantity,itemPrice;
        ImageView itemImage;
        public itemHolder(View view){
            super(view);
            itemName=(TextView) view.findViewById(R.id.name);
            itemQuantity=(TextView) view.findViewById(R.id.quantity);
            itemPrice=(TextView) view.findViewById(R.id.price);
            itemImage=(ImageView)view.findViewById(R.id.image);

        }

    }












    @Override
    public itemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new itemHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(itemHolder holder, int position) {
        Message message = messages.get(position);
        holder.itemName.setText(message.getName());
        holder.itemPrice.setText(message.getPrice());
        holder.itemQuantity.setText(message.getQuantity());
        /**
         * cancel the image now till get it from the server and use glide for it
         * holder.itemImage(message.getPhotoUrl());
         * */




    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    }



