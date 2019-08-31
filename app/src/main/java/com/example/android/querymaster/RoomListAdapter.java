package com.example.android.querymaster;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RoomListAdapter extends RecyclerView.Adapter<RoomListAdapter.OrderObjectHolder> {
    
    
    private static MyClickListener myClickListener;

    public static class OrderObjectHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener
    {
        TextView 

        public OrderObjectHolder(View itemView) {
            super(itemView);
            clientName=itemView.findViewById(R.id.client_name_order_view);
            
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            myClickListener.onItemClick(getAdapterPosition(), v);
        }
    }

    public void setOnItemClickListener(MyClickListener myClickListener) {
        RoomListAdapter.myClickListener = myClickListener;
    }


    public RoomListAdapter(ArrayList<Room> myDataset) {
        OrderList = myDataset;
    }

    @Override
    public OrderObjectHolder onCreateViewHolder(ViewGroup parent,int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_row, parent, false);
        OrderObjectHolder dataObjectHolder = new OrderObjectHolder(view);
        return dataObjectHolder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull OrderObjectHolder holder, final int position) {

        holder.clientName.setText(OrderList.get(position).getmClientName());
        holder.date.setText(OrderList.get(position).getmOrderDate());
        holder.status.setText("Pending");
        holder.message.setText(OrderList.get(position).getmOrderMsg());
        holder.numberOfItems.setText(Integer.toString(OrderList.get(position).getmSnackList().size()).concat(" Items"));
        holder.changeStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    public void addItem(Order dataObj, int index) {
        OrderList.add(index, dataObj);
        notifyItemInserted(index);
    }

    public void deleteItem(int index) {
        OrderList.remove(index);
        notifyItemRemoved(index);
        //notifyItemRangeChanged(index,CartListActivity.getNumberOfItemsInCart());
    }
    @Override
    public int getItemCount() {
        return OrderList.size();
    }

    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }

}
