package millionairs.example.millionairs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import millionairs.example.millionairs.Adapter.ChatAdapter;

import com.nitzan.millionairs.R;

import java.util.ArrayList;

public class TipBotActivity extends AppCompatActivity {

    ImageView sendImageView;
    EditText messageEditText;
    ChatAdapter chatAdapter = null;
    ArrayList<Chat> mChatList = new ArrayList();
    RecyclerView recyclerViewChat;
    int count = 0;
    String[] insurances_convo = {"Are you familiar with all the insurances you have?",
            "Is there an insurance you own that purchased more than once?",
            "Do you know what you get from your insurances?",
            "Are you interested in all of your insurances?"
    };
    String[] insurances_responses = {"You should check the following website to see all of your insurances: \n" +
            "https://harb.cma.gov.il/",
            "Great job! You're being very responsible :)",
            "Call your insurance company and ask them about it",
            "Call your insurance company and cancel them."
    };
    String[] communication_convo = {"Did your service providers give you the cheapest offers?",
            "Is the package you currently use the best one for you, or can you change your package? \n " +
                    "For example, can you change to a package that offers less data that will still be enough for your needs?",
            "If you pay for multiple devices, can you get a group discount or special offers?",
            "Do you need to have cable TV at home?",
            "Do you know what are the limits of resources in your living area?"
    };
    String[] communication_responses = {"You should try to negotiate with your current providers and compare prices with other companies.",
            "Great job! You're being very responsible :)",
            "Call different providers and see if any of them has a package that can fit your needs and be cheaper.",
            "Call your current provider and ask them about it.",
            "Call your current provider and cancel the ones you don't need",
            "Ask your provider about it. If your current package doesn't fit the resources in your living area, you are losing money."
    };
    String[] rights_convo = {"Are you eligible for a discount on your property taxes?",
            "Are you a student/serving in the IDF/just got released from the IDF?",
            "Are you eligible for taxes reductions due to your living area/family status etc..?"
    };
    String[] rights_responses = {"You should talk with your municipality and ask what is the process to get the discount.",
            "Ask your students' union/HR person in your (former) unit about the rights you deserve.",
            "You should check the following website to see all of the taxes reductions you're eligible for: \n" +
                    "https://www.gov.il/he/departments/israel_tax_authority"
    };
    String[] pension_convo = {"Did your employer open a pension program for you?",
            "Did you try to negotiate the terms of your pension program?",
            "Do you know what you get from your insurances?",
            "Are all of your pension programs being handled in the same insurance company?"
    };
    String[] pension_responses = {"You should ask your employer about it.",
            "Great job! You're being very responsible :)",
            "Call your insurance company and ask them about it. Check if you can negotiate with them about that.",
            "You should check the following website to check that \n" +
                    "https://www.wobi.co.il/%D7%A4%D7%A0%D7%A1%D7%99%D7%94/?referrer=https://www.google.com/&referrer=https://www.google.com/"
    };
    String[] commissions_convo = {"Have you tried to negotiate the commissions terms with your bank?",
            "Do you know if there is a bank that can offer better terms for you?",
            "Do you pay commissions for your credit card(s)? If so, can you lower them?",
            "Do you need all the credit cards you have?"
    };
    String[] commissions_responses = {"Try to negotiate with them about it. It can save you a lot of money!",
            "Great job! You're being very responsible :)",
            "Consider transferring your account to that bank.",
            "Try to negotiate with your credit card company about it. It can save you a lot of money!",
            "Consider cancelling the ones you don't need."
    };
    String[] workRights_convo = {"Do you use your vacation days or ask for their money value instead?",
            "Is your employer putting money in your pension program every month?",
            "Do you get refunds for travels from your employer?",
            "Is your employer paying social security taxes as a part of your paycheck?",
            "Do you get any special benefits from your employer? (A car/Food car etc..)"
    };
    String[] workRights_responses = {"I just wanted to let you know that you might have work rights." +
            "\nCheck with your employer or with your HR department if you can get any more rights"
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
                RespondCommissions();
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
                            RespondCommissions();
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
        if (count >= 2 * communication_convo.length) {
            mChatList.add(new Chat("Sorry, I don't have anymore questions for you on this topic", false));
        }
        else {
            if (count % 2 == 0) {
                mChatList.add(new Chat(communication_convo[count / 2], false));
            }
            else{
                if (mChatList.get(mChatList.size() - 1).getMessage().toLowerCase().equals("yes")){
                    switch (count){
                        case 1:
                            mChatList.add(new Chat(communication_responses[1], false));
                            break;
                        case 3:
                            mChatList.add(new Chat(communication_responses[1], false));
                            break;
                        case 5:
                            mChatList.add(new Chat(communication_responses[3], false));
                            break;
                        case 7:
                            mChatList.add(new Chat(communication_responses[1], false));
                            break;
                        case 9:
                            mChatList.add(new Chat(communication_responses[1], false));
                            break;
                    }
                }
                else if (mChatList.get(mChatList.size() - 1).getMessage().toLowerCase().equals("no")){
                    switch (count){
                        case 1:
                            mChatList.add(new Chat(communication_responses[0], false));
                            break;
                        case 3:
                            mChatList.add(new Chat(communication_responses[2], false));
                            break;
                        case 5:
                            mChatList.add(new Chat(communication_responses[1], false));
                            break;
                        case 7:
                            mChatList.add(new Chat(communication_responses[4], false));
                            break;
                        case 9:
                            mChatList.add(new Chat(communication_responses[5], false));
                            break;
                    }
                }
                count++;
                RespondCommunication();
            }
        }
        chatAdapter.notifyDataSetChanged();
    }
    private void RespondRights(){
        if (count >= 2 * rights_convo.length) {
            mChatList.add(new Chat("Sorry, I don't have anymore questions for you on this topic", false));
        }
        else {
            if (count % 2 == 0) {
                mChatList.add(new Chat(rights_convo[count / 2], false));
            }
            else{
                if (mChatList.get(mChatList.size() - 1).getMessage().toLowerCase().equals("yes")){
                    switch (count){
                        case 1:
                            mChatList.add(new Chat(communication_responses[0], false));
                            break;
                        case 3:
                            mChatList.add(new Chat(communication_responses[1], false));
                            break;
                        case 5:
                            mChatList.add(new Chat(communication_responses[2], false));
                            break;
                    }
                }
                count++;
                RespondRights();
            }
        }
        chatAdapter.notifyDataSetChanged();
    }
    private void RespondPension(){
        if (count >= 2 * pension_convo.length) {
            mChatList.add(new Chat("Sorry, I don't have anymore questions for you on this topic", false));
        }
        else {
            if (count % 2 == 0) {
                mChatList.add(new Chat(pension_convo[count / 2], false));
            }
            else{
                if (mChatList.get(mChatList.size() - 1).getMessage().toLowerCase().equals("no")){
                    switch (count){
                        case 1:
                            mChatList.add(new Chat(pension_responses[0], false));
                            break;
                        case 3:
                            mChatList.add(new Chat(pension_responses[2], false));
                            break;
                        case 5:
                            mChatList.add(new Chat(pension_responses[3], false));
                            break;
                    }
                }
                else if (mChatList.get(mChatList.size() - 1).getMessage().toLowerCase().equals("yes")){
                    mChatList.add(new Chat(pension_responses[1], false));
                }
                count++;
                RespondPension();
            }
        }
        chatAdapter.notifyDataSetChanged();
    }
    private void RespondCommissions(){
        if (count >= 2 * pension_convo.length) {
            mChatList.add(new Chat("Sorry, I don't have anymore questions for you on this topic", false));
        }
        else {
            if (count % 2 == 0) {
                mChatList.add(new Chat(pension_convo[count / 2], false));
            }
            else{
                if (mChatList.get(mChatList.size() - 1).getMessage().toLowerCase().equals("no")){
                    switch (count){
                        case 1:
                            mChatList.add(new Chat(pension_responses[1], false));
                            break;
                        case 3:
                            mChatList.add(new Chat(pension_responses[2], false));
                            break;
                        case 5:
                            mChatList.add(new Chat(pension_responses[3], false));
                            break;
                        case 7:
                            mChatList.add(new Chat(pension_responses[1], false));
                            break;
                    }
                }
                else if (mChatList.get(mChatList.size() - 1).getMessage().toLowerCase().equals("yes")){
                    switch (count){
                        case 1:
                            mChatList.add(new Chat(pension_responses[0], false));
                            break;
                        case 3:
                            mChatList.add(new Chat(pension_responses[1], false));
                            break;
                        case 5:
                            mChatList.add(new Chat(pension_responses[1], false));
                            break;
                        case 7:
                            mChatList.add(new Chat(pension_responses[4], false));
                            break;
                    }
                }
                count++;
                RespondCommissions();
            }
        }
        chatAdapter.notifyDataSetChanged();
    }
    private void RespondWorkRights(){
        if (count >= 2 * workRights_convo.length) {
            mChatList.add(new Chat("Sorry, I don't have anymore questions for you on this topic", false));
        }
        else {
            if (count % 2 == 0) {
                mChatList.add(new Chat(workRights_convo[count / 2], false));
            }
            else{
                mChatList.add(new Chat(workRights_responses[0], false));
                count++;
                RespondWorkRights();
            }
        }
        chatAdapter.notifyDataSetChanged();
    }

}