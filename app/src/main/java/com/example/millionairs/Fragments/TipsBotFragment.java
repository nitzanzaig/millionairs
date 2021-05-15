package com.example.millionairs.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.example.millionairs.R;

import java.util.ArrayList;
import java.util.List;

public class TipsBotFragment extends Fragment {

    ArrayList<String> messages = new ArrayList<>();
    ArrayAdapter arrayAdapter;

    public void chat(View view){
        EditText chatEditText = view.findViewById(R.id.chatEditText);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tips_bot, container, false);

        ListView chat = view.findViewById(R.id.chatListView);
        arrayAdapter = new ArrayAdapter(view.getContext(), android.R.layout.simple_list_item_1, messages);
        chat.setAdapter(arrayAdapter);
        messages.add("Bot: Hello, my name is Fin. I'm your private financial helper@! \n" +
                "Here we can talk about the following topics: ");
        messages.add("Bot: 1. Insurances \n" +
                "2. Communication \n" +
                "3. Your financial rights \n" +
                "4. Pension \n" +
                "5. Your work rights \n" +
                "6. Fees and Commissions");
        messages.add("Bot: Please choose the subject you want to talk about");
        arrayAdapter.notifyDataSetChanged();
        chat(view);

        return view;
    }
}