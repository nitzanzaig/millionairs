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

public class TipBotActivity extends AppCompatActivity {

    ImageView sendImageView;
    EditText messageEditText;
    ChatAdapter chatAdapter = null;
    ArrayList<Chat> mChatList = new ArrayList();
    RecyclerView recyclerViewChat;
    int count = 0;
    String[] insurances_convo = {"Are you familiar with all of your insurances?",
            "Is there an insurance that you own purchased more than once?",
            "Do you know what you get from your insurances?",
            "Are you interested in all of your insurances?"
    };
    String[] insurances_responses = {"You should check the following website to see all of your insurances: \n" +
            "https://harb.cma.gov.il/",
            "Great job! You're being very responsible :)",
            "Call your insurance company and ask them about it",
            "Call your insurance company and cancel them."
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tip_bot);
        sendImageView = findViewById(R.id.send_image_button);
        messageEditText = findViewById(R.id.text_message);
        recyclerViewChat = findViewById(R.id.recycler_view_chat);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerViewChat.setLayoutManager(linearLayoutManager);
        mChatList.add(new Chat("Hello! My name is Fin, I'm your personal financial helper. " +
                "\nCan I ask you some questions?" +
                "(All questions are yes and no questions)", false));
        chatAdapter = new ChatAdapter(getApplicationContext(), mChatList);
        recyclerViewChat.setAdapter(chatAdapter);
        chatAdapter.notifyDataSetChanged();
        Intent intent = getIntent();
        String subject = intent.getStringExtra("subject");
        switch (subject){
            case "Insurances":
                RespondInsurances();
                break;
            case "Communication":
                RespondCommunication();
                break;
            case "Rights":
                RespondRights();
                break;
            case "Pension":
                RespondPension();
                break;
            case "Commissions":
                RespondComissions();
                break;
            case "Work place rights":
                RespondWorkRights();
                break;
        }
        sendImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = messageEditText.getText().toString();
                if (message.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Please write a message", Toast.LENGTH_SHORT).show();
                }
                else {
                    sendMessage(message);
                    messageEditText.setText("");
                    count++;
                    switch (subject){
                        case "Insurances":
                            RespondInsurances();
                            break;
                        case "Communication":
                            RespondCommunication();
                            break;
                        case "Rights":
                            RespondRights();
                            break;
                        case "Pension":
                            RespondPension();
                            break;
                        case "Commissions":
                            RespondComissions();
                            break;
                        case "Work place rights":
                            RespondWorkRights();
                            break;
                    }
                }
            }
        });
    }

    private void sendMessage(String message) {
        mChatList.add(new Chat(message, true));
        chatAdapter.notifyDataSetChanged();
    }

    private void RespondInsurances(){
        if (count >= 2 * insurances_convo.length) {
            mChatList.add(new Chat("Sorry, I don't have anymore questions for you on this topic. \n" +
                    "For more information about your insurances you can check the following website: \n" +
                    "https://harb.cma.gov.il/", false));
        }
        else {
            if (count % 2 == 0) {
                mChatList.add(new Chat(insurances_convo[count / 2], false));
            }
            else{
                if (mChatList.get(mChatList.size() - 1).getMessage().toLowerCase().equals("yes")){
                    switch (count){
                        case 1:
                            mChatList.add(new Chat(insurances_responses[1], false));
                            break;
                        case 3:
                            mChatList.add(new Chat(insurances_responses[2], false));
                            break;
                        case 5:
                            mChatList.add(new Chat(insurances_responses[1], false));
                            break;
                        case 7:
                            mChatList.add(new Chat(insurances_responses[1], false));
                            break;
                    }
                }
                else if (mChatList.get(mChatList.size() - 1).getMessage().toLowerCase().equals("no")){
                    switch (count){
                        case 1:
                            mChatList.add(new Chat(insurances_responses[0], false));
                            break;
                        case 3:
                            mChatList.add(new Chat(insurances_responses[1], false));
                            break;
                        case 5:
                            mChatList.add(new Chat(insurances_responses[2], false));
                            break;
                        case 7:
                            mChatList.add(new Chat(insurances_responses[3], false));
                            break;
                    }
                }
                count++;
                RespondInsurances();
            }
        }
        chatAdapter.notifyDataSetChanged();
    }
    private void RespondCommunication(){
        mChatList.add(new Chat("How are you? Communication", false));
        chatAdapter = new ChatAdapter(getApplicationContext(), mChatList);
        recyclerViewChat.setAdapter(chatAdapter);
        chatAdapter.notifyDataSetChanged();
    }
    private void RespondRights(){
        mChatList.add(new Chat("How are you? Rights", false));
        Log.d("Message", "respond");
        chatAdapter = new ChatAdapter(getApplicationContext(), mChatList);
        recyclerViewChat.setAdapter(chatAdapter);
        chatAdapter.notifyDataSetChanged();
    }
    private void RespondPension(){
        mChatList.add(new Chat("How are you? Pensions" , false));
        chatAdapter = new ChatAdapter(getApplicationContext(), mChatList);
        recyclerViewChat.setAdapter(chatAdapter);
        chatAdapter.notifyDataSetChanged();
    }
    private void RespondComissions(){
        mChatList.add(new Chat("How are you? Commissions", false));
        chatAdapter = new ChatAdapter(getApplicationContext(), mChatList);
        recyclerViewChat.setAdapter(chatAdapter);
        chatAdapter.notifyDataSetChanged();
    }
    private void RespondWorkRights(){
        mChatList.add(new Chat("How are you? Work place rights", false));
        chatAdapter = new ChatAdapter(getApplicationContext(), mChatList);
        recyclerViewChat.setAdapter(chatAdapter);
        chatAdapter.notifyDataSetChanged();
    }

}