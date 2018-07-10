package com.argalit.dev.chat_function;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class ViewHolder extends RecyclerView.ViewHolder
{
    TextView message;

    public ViewHolder(View itemView)
    {
        super(itemView);
        message = itemView.findViewById(R.id.message_item);
    }
}
