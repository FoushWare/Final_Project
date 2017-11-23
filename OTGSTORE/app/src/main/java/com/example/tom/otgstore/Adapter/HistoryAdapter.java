package com.example.tom.otgstore.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tom.otgstore.R;
import com.example.tom.otgstore.models.ItemHistory;

import java.util.ArrayList;

/**
 * Created by Mirna on 11/21/2017.
 */


public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {

    private ArrayList<ItemHistory> itemsHistory;

    private Context context;


    public HistoryAdapter(ArrayList<ItemHistory> itemsHistory, Context context) {
        this.itemsHistory = itemsHistory;
        this.context = context;

    }

    @Override
    public HistoryViewHolder onCreateViewHolder(ViewGroup viewGroup, final int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_view_items_history_row, viewGroup, false);
        HistoryViewHolder viewHolder = new HistoryViewHolder(v);
        return viewHolder;
    }

    private void handleItemClick(int position) {
    }

    @Override
    public void onBindViewHolder(final HistoryViewHolder recentsViewHolder, int i) {

        int position = i;
        ItemHistory item = itemsHistory.get(position);
        if(item != null) {
            recentsViewHolder.itemHistoryName.setText(item.getName());
            recentsViewHolder.itemHistoryDate.setText(item.getDate());
            recentsViewHolder.itemHistoryQuantity.setText(String.valueOf(item.getQuantity()));
            recentsViewHolder.itemHistoryPrice.setText(String.valueOf(item.getPrice()));
        }
    }

    @Override
    public int getItemCount() {

        if (itemsHistory == null) {
            return 0;
        }

        return itemsHistory.size();
    }

    public static class HistoryViewHolder extends RecyclerView.ViewHolder {


        TextView itemHistoryName;
        TextView itemHistoryDate;
        TextView itemHistoryQuantity;
        TextView itemHistoryPrice;


        public HistoryViewHolder(View itemView) {
            super(itemView);
            itemHistoryName = (TextView) itemView.findViewById(R.id.item_history_name);
            itemHistoryDate = (TextView) itemView.findViewById(R.id.item_history_date);
            itemHistoryQuantity = (TextView) itemView.findViewById(R.id.item_history_quantity);
            itemHistoryPrice = (TextView) itemView.findViewById(R.id.item_history_price);

        }
    }

}
