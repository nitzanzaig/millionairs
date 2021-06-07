package millionairs.example.millionairs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import millionairs.example.millionairs.Adapter.ChatAdapter;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;
import com.nitzan.millionairs.R;

import org.jetbrains.annotations.NotNull;

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

        if(!Python.isStarted()){
            Python.start(new AndroidPlatform(this));
        }

        sendImageView = findViewById(R.id.send_image_button);
        messageEditText = findViewById(R.id.text_message);
        recyclerViewChat = findViewById(R.id.recycler_view_chat);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerViewChat.setLayoutManager(linearLayoutManager);
        chatAdapter = new ChatAdapter(getApplicationContext(), mChatList);
        recyclerViewChat.setAdapter(chatAdapter);
        sendMessage("Hello! My name is Fin, I'm your personal financial helper. ", false);
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
                    Python py = Python.getInstance();
                    @NotNull PyObject pyobj = py.getModule("chatbot");
                    PyObject obj = pyobj.callAttr("main", message);
                    sendMessage(obj.toString(), false);
                }
            }
        });
    }

    private void sendMessage(String message, boolean isUser) {
        mChatList.add(new Chat(message, isUser));
        Log.d("Message", String.valueOf(mChatList.get(mChatList.size() - 1).getMessage()));
        chatAdapter.notifyDataSetChanged();
    }
}