package com.naruto.trango.homepage.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.naruto.trango.R;
import com.naruto.trango.homepage.SimpleClasses.Container1;

import java.util.List;

public class Container1Adapter extends RecyclerView.Adapter<Container1Adapter.ContainerViewHolder>{

    List<Container1> mContainer1List;

    public Container1Adapter( List<Container1> mContainer1List) {

        this.mContainer1List = mContainer1List;
    }

    @Override
    public Container1Adapter.ContainerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.recycler_view_1_single_layout,parent,false);
        return new ContainerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Container1Adapter.ContainerViewHolder holder, int position) {

        Container1 aContainer1 = mContainer1List.get(position);

        holder.iv_icon.setImageResource(aContainer1.getImage_id());
        holder.tv_title.setText(aContainer1.getTitle());
        holder.tv_desc.setText(aContainer1.getDesc());


    }

    @Override
    public int getItemCount() {
        return mContainer1List.size();
    }

    public class ContainerViewHolder extends RecyclerView.ViewHolder {

        ImageView iv_icon;
        TextView tv_title, tv_desc;

        public ContainerViewHolder(View itemView) {
            super(itemView);

            iv_icon  = itemView.findViewById(R.id.icon);
            tv_title = itemView.findViewById(R.id.title);
            tv_desc  = itemView.findViewById(R.id.description);



        }
    }
}
