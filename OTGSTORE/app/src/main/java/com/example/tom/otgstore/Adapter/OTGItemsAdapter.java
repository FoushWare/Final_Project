package com.example.tom.otgstore.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.tom.otgstore.R;

import java.util.List;

/**
 * Created by fouad on 10/4/17.
 */

public class OTGItemsAdapter extends ArrayAdapter<OTGitems> {
    public OTGItemsAdapter(Context context,int resource, List<OTGitems> objects) {
        super(context, resource,objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            convertView=((Activity) getContext()).getLayoutInflater().inflate(R.layout.list_item, parent, false);
        }

        //get all views of the item content [ name, price , quantity ,photo]
        TextView name= (TextView)convertView.findViewById(R.id.name);
        TextView price= (TextView)convertView.findViewById(R.id.price);
        TextView quantity= (TextView)convertView.findViewById(R.id.quantity);
        ImageView photoImageView=(ImageView)convertView.findViewById(R.id.catalog_item_image);



       //get the position of the item when clicking on it and  with it we can get every thing related to this item
        OTGitems item=getItem(position);

        //check if the item has photo in the db or not

        boolean isphoto=item.getPhotoUrl() != null;
       if(isphoto){
           Glide.with(photoImageView.getContext())
                   .load(item.getPhotoUrl())
                   .into(photoImageView);
       }//else there will be will be a placeholder image of the item

        return convertView;
    }//End of getView method
}//End of the OTGItemsAdapter class
