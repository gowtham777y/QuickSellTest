package com.qstest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.qstest.Model.listItem;

import java.util.ArrayList;
import java.util.List;

public class listItemAdapter extends RecyclerView.Adapter<listItemAdapter.ViewHolder>{
    List<listItem> listItems= new ArrayList<>();
    private Context mContext;

    public listItemAdapter(List<listItem> items,Context context){
        this.listItems=items;
        this.mContext=context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        ViewHolder holder= new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (listItems.get(position).getProductDesc().length() != 0){
        holder.productDesc.setText(listItems.get(position).getProductDesc());}
        if (listItems.get(position).getProductName().length() != 0){
        holder.productName.setText(listItems.get(position).getProductName());}
        if (listItems.get(position).getProductPrice() != 0){
        holder.productPrice.setText(mContext.getResources().getString(R.string.Rs)+listItems.get(position).getProductPrice().toString());}

        if (listItems.get(position).getProductImage().length() != 0) {
            Glide.with(mContext)
                    .asBitmap()
                    .load(listItems.get(position).getProductImage())
                    .into(holder.productImage);
        }
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView productImage;
        private TextView productName;
        private TextView productDesc;
        private TextView productPrice;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage=itemView.findViewById(R.id.product_image);
            productName=itemView.findViewById(R.id.product_name);
            productDesc=itemView.findViewById(R.id.product_description);
            productPrice=itemView.findViewById(R.id.product_price);
        }
    }
}
