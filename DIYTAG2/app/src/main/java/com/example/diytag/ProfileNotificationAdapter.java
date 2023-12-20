package com.example.diytag;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ProfileNotificationAdapter extends RecyclerView.Adapter<ProfileNotificationAdapter.NotificationViewHolder> {

    private List<String> notificationList;

    // Constructor
    public ProfileNotificationAdapter(List<String> notificationList) {
        this.notificationList = notificationList;
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.profile_notification_view, parent, false);
        return new NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        String notificationMessage = notificationList.get(position);
        holder.bind(notificationMessage);
    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }

    public void addNotificationToList(String notificationMessage) {
        notificationList.add(0, notificationMessage); // Idagdag sa unahan ng list
        notifyItemInserted(0); // I-update ang adapter para mapakita ang bagong notification sa unahan
    }

    // ViewHolder class
    public static class NotificationViewHolder extends RecyclerView.ViewHolder {
        private TextView notificationMessage;

        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);
            notificationMessage = itemView.findViewById(R.id.notificationMessage);
        }

        public void bind(String notificationMessageText) {
            notificationMessage.setText(notificationMessageText);
        }
    }
}

