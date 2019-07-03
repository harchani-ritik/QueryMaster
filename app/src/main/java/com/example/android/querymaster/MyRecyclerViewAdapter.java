package com.example.android.querymaster;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

//MyRecyclerView Adapter is a custom adapter for QueryObjects
    public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.QueryObjectHolder> {
    private static String LOG_TAG = "MyRecyclerViewAdapter";
    private ArrayList<QueryObject> queryObjectArrayList;
    private static MyClickListener myClickListener;

    public static class QueryObjectHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener
    {
        TextView query;
        TextView answerlabel;
        TextView answerQuery;
        TextView timelabel;
        TextView name;

        public QueryObjectHolder(View itemView) {
            super(itemView);
            query = (TextView) itemView.findViewById(R.id.textView);
            answerlabel = (TextView) itemView.findViewById(R.id.textView2);
            answerQuery=(TextView)itemView.findViewById(R.id.answer_query);
            timelabel=itemView.findViewById(R.id.textView4);
            name = (TextView) itemView.findViewById(R.id.textView5);
            Log.i(LOG_TAG, "Adding Listener");
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            myClickListener.onItemClick(getAdapterPosition(), v);
        }
    }

    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }


    public MyRecyclerViewAdapter(ArrayList<QueryObject> myDataset) {
        queryObjectArrayList = myDataset;
    }

    @Override
    public QueryObjectHolder onCreateViewHolder(ViewGroup parent,int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_row, parent, false);
        QueryObjectHolder dataObjectHolder = new QueryObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(QueryObjectHolder holder, final int position) {

        holder.query.setText(queryObjectArrayList.get(position).getmQuery());

        final String query_answer=queryObjectArrayList.get(position).getmAnswer();
        if(query_answer!=null){
            holder.answerlabel.setText(query_answer);
            holder.name.setText(queryObjectArrayList.get(position).getmName());
        }
        else {
            holder.answerlabel.setText("No Answer on this Query Till Now!");
            holder.answerlabel.setTypeface(null, Typeface.ITALIC);
            holder.name.setVisibility(View.INVISIBLE);
        }

        holder.timelabel.setText(queryObjectArrayList.get(position).getmTime());
        holder.answerQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent myIntent = new Intent(v.getContext(), AnsweringActivity.class);
                    myIntent.putExtra("question", queryObjectArrayList.get(position).getmQuery());
                    myIntent.putExtra("queryTime", queryObjectArrayList.get(position).getmTime());
                    if(query_answer==null)
                        myIntent.putExtra("objKey", queryObjectArrayList.get(position).getmKey());
                    v.getContext().startActivity(myIntent);
            }
        });
    }

    public void addItem(QueryObject dataObj, int index) {
        queryObjectArrayList.add(index, dataObj);
        notifyItemInserted(index);
    }

    public void deleteItem(int index) {

        queryObjectArrayList.remove(index);
        notifyItemRemoved(index);
    }
    @Override
    public int getItemCount() {
        return queryObjectArrayList.size();
    }

    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }

}