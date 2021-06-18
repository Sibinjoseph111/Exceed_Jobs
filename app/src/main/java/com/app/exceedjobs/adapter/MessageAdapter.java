package com.app.exceedjobs.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.exceedjobs.R;
import com.app.exceedjobs.model.MessageModel;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    private List<MessageModel> messages;
    private Context mContext;

    public MessageAdapter(List<MessageModel> messages, Context mContext) {
        this.messages = messages;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.message_list_layout,parent,false);
        return new MessageAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title_TV.setText(messages.get(position).getTitle());
        holder.details_TV.setText(messages.get(position).getMessage());
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        View mView;
        TextView title_TV, details_TV;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mView = itemView;
            title_TV = mView.findViewById(R.id.messageListTitle_TV);
            details_TV = mView.findViewById(R.id.messageListDetail_TV);
        }
    }
}
