package com.naruto.trango.transaction_history;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.naruto.trango.R;

import java.util.List;

public class WalletHistoryAdapter extends RecyclerView.Adapter<WalletHistoryAdapter.ViewHolder> {

    List<WalletStatementData> walletStatementDataList;
    private String tmpdata = "";
    private Context context;

    public WalletHistoryAdapter(List<WalletStatementData> walletStatementDataList, Context context) {
        this.walletStatementDataList = walletStatementDataList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.wallet_history_content, parent, false);
        return new WalletHistoryAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final WalletStatementData data = walletStatementDataList.get(position);

        if (data.getDate().equalsIgnoreCase(tmpdata)) {
            tmpdata = data.getDate();
            holder.tvdate.setVisibility(View.GONE);
            holder.tvevent.setText(data.getEvent());
            holder.tvtime.setText(data.getTime());
            holder.tvamount.setText("\u20B9" + data.getAmount());

            if (data.getEvent().equalsIgnoreCase("money added")) {
                holder.ivevent.setImageResource(R.drawable.money_added);
                holder.tvamount.setTextColor(context.getColor(R.color.dark_spring_green));
            } else {
                holder.ivevent.setImageResource(R.drawable.money_deducted);
            }
        } else {
            tmpdata = data.getDate();
            holder.tvdate.setVisibility(View.VISIBLE);
            holder.tvevent.setText(data.getEvent());
            holder.tvtime.setText(data.getTime());
            holder.tvdate.setText(data.getDate());
            holder.tvamount.setText("\u20B9" + data.getAmount());

            if (data.getEvent().equalsIgnoreCase("money added")) {
                holder.ivevent.setImageResource(R.drawable.money_added);
                holder.tvamount.setTextColor(context.getColor(R.color.dark_spring_green));
            } else {
                holder.ivevent.setImageResource(R.drawable.money_deducted);
            }
        }

    }

    @Override
    public int getItemCount() {
        if (walletStatementDataList != null) {
            return walletStatementDataList.size();
        } else {
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvevent, tvtime, tvamount, tvdate;
        ImageView ivevent;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvevent = itemView.findViewById(R.id.tv_wallet_history_event);
            tvdate = itemView.findViewById(R.id.tv_wallet_history_date);
            tvtime = itemView.findViewById(R.id.tv_wallet_history_time);
            tvamount = itemView.findViewById(R.id.tv_wallet_history_amount);
            ivevent = itemView.findViewById(R.id.iv_wallet_history_image);
        }
    }
}
