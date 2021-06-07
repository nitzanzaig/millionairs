package millionairs.example.millionairs.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.nitzan.millionairs.R;
import millionairs.example.millionairs.TipBotActivity;

import java.util.ArrayList;

public class TipsBotFragment extends Fragment {

    ArrayList<String> options = new ArrayList<>();
    ArrayAdapter arrayAdapter;
    Button insurancesButton,communicationButton,rightsButton, pensionButton,commissionsButton,workplaceButton ;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tips_bot, container, false);
        insurancesButton = view.findViewById(R.id.InsurancesButton);
        insurancesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(), TipBotActivity.class);
                intent.putExtra("subject", "Insurances");
                startActivity(intent);
            }
        });

        communicationButton = view.findViewById(R.id.CommunicationButton);
        communicationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(), TipBotActivity.class);
                intent.putExtra("subject", "Communication");
                startActivity(intent);
            }
        });
        rightsButton = view.findViewById(R.id.RightsButton);
        rightsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(), TipBotActivity.class);
                intent.putExtra("subject", "Rights");
                startActivity(intent);
            }
        });
        pensionButton = view.findViewById(R.id.PensionButton);
        pensionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(), TipBotActivity.class);
                intent.putExtra("subject", "Pension");
                startActivity(intent);
            }
        });
        commissionsButton = view.findViewById(R.id.CommissionsButton);
        commissionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(), TipBotActivity.class);
                intent.putExtra("subject", "Commissions");
                startActivity(intent);
            }
        });
        workplaceButton = view.findViewById(R.id.WorkplacerightsButton);
        workplaceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(), TipBotActivity.class);
                intent.putExtra("subject", "Work place rightss");
                startActivity(intent);
            }
        });

        /*communicationButton = view.findViewById(R.id.CommunicationButton);
        options.add(0, "Insurances");
        options.add(1, "Communication");
        options.add(2, "Rights");
        options.add(3, "Pension");
        options.add(4, "Commissions");
        options.add(5, "Work place rights");
        ListView listView = view.findViewById(R.id.optionsListView);
        arrayAdapter = new ArrayAdapter(view.getContext(), android.R.layout.simple_list_item_1, options);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), TipBotActivity.class);
                switch (position){
                    case 0:
                        intent.putExtra("subject", "Insurances");
                        startActivity(intent);
                        break;
                    case 1:
                        intent.putExtra("subject", "Communication");
                        startActivity(intent);
                        break;
                    case 2:
                        intent.putExtra("subject", "Rights");
                        startActivity(intent);
                        break;
                    case 3:
                        intent.putExtra("subject", "Pension");
                        startActivity(intent);
                        break;
                    case 4:
                        intent.putExtra("subject", "Commissions");
                        startActivity(intent);
                        break;
                    case 5:
                        intent.putExtra("subject", "Work place rights");
                        startActivity(intent);
                        break;
                }
            }
        });*/
        return view;
    }
}