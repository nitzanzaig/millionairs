package millionairs.example.millionairs.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import millionairs.example.millionairs.Chat;
import com.nitzan.millionairs.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ChatAdapter extends RecyclerView.Adapter {
    private final List<Chat> mChatList;
    private final Context mContext;

    public ChatAdapter(Context mContext, List<Chat> mChatList) {
        this.mContext = mContext;
        this.mChatList = mChatList;
    }

    @NonNull
    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int position) {
        View view;
        if (position == 1) {
            view = LayoutInflater.from(this.mContext).inflate(R.layout.message_item_right, parent, false);
        } else {
            view = LayoutInflater.from(this.mContext).inflate(R.layout.message_item_left, parent, false);
        }
        return new RecyclerView.ViewHolder(view) {
            @Override
            public String toString() {
                return super.toString();
            }
        };
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {
        Chat chat = this.mChatList.get(position);
        TextView show_text_message = holder.itemView.findViewById(R.id.show_text_message);
        show_text_message.setText(chat.getMessage());
    }

    public int getItemCount() {
        return this.mChatList.size();
    }

    public int getItemViewType(int position) {
        return this.mChatList.get(position).isUser() ? 1 : 0;
    }
}
