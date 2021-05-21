package com.example.millionairs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.millionairs.Adapter.ChatAdapter;

import java.util.ArrayList;

public class ConvoBotActivity extends AppCompatActivity {

    ImageView sendImageView;
    EditText messageEditText;
    ChatAdapter chatAdapter = null;
    ArrayList<Chat> mChatList = new ArrayList();
    RecyclerView recyclerViewChat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_convo_bot);
        sendImageView = findViewById(R.id.send_image_button);
        messageEditText = findViewById(R.id.text_message);
        recyclerViewChat = findViewById(R.id.recycler_view_chat);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerViewChat.setLayoutManager(linearLayoutManager);
        sendImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = messageEditText.getText().toString();
                if (message.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Please write a message", Toast.LENGTH_SHORT).show();
                }
                else {
                    sendMessage(message, true);
                    messageEditText.setText("");
                }
            }
        });
    }

    private void sendMessage(String message, boolean isUser) {
        mChatList.add(new Chat(message, isUser));
        Log.d("Message", String.valueOf(mChatList.get(mChatList.size() - 1).getMessage()));
        chatAdapter = new ChatAdapter(getApplicationContext(), mChatList);
        recyclerViewChat.setAdapter(chatAdapter);
        chatAdapter.notifyDataSetChanged();
    }

    private void RespondInsurances(){
        mChatList.add(new Chat("How are you? Insurances", false));
        chatAdapter = new ChatAdapter(getApplicationContext(), mChatList);
        recyclerViewChat.setAdapter(chatAdapter);
        chatAdapter.notifyDataSetChanged();
    }
}