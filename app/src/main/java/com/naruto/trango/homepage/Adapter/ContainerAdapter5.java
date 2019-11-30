package com.naruto.trango.homepage.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.naruto.trango.R;
import com.naruto.trango.homepage.SimpleClasses.Container5;

import java.util.List;

public class ContainerAdapter5 extends RecyclerView.Adapter<ContainerAdapter5.Container5ViewHolder> {

    Context mCtx;
    List<Container5> mContainer5List;

    public ContainerAdapter5(List<Container5> mContainer5List) {

        this.mContainer5List = mContainer5List;
    }

    @Override
    public ContainerAdapter5.Container5ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.recycler_view_5_single_layout,parent,false);
        return new Container5ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ContainerAdapter5.Container5ViewHolder holder, int position) {

        Container5 aContainer = mContainer5List.get(position);

        holder.tv_num.setText(aContainer.getNumbers());
        holder.tv_title.setText(aContainer.getShorDes());
        holder.tv_desc.setText(aContainer.getDesc());

    }

    @Override
    public int getItemCount() {
        return mContainer5List.size();
    }

    public class Container5ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_num;
        TextView tv_title;
        TextView tv_desc;

        public Container5ViewHolder(View itemView) {
            super(itemView);

            tv_num   = itemView.findViewById(R.id.num);
            tv_title = itemView.findViewById(R.id.title);

            tv_desc  = itemView.findViewById(R.id.description);
        }
    }
}
