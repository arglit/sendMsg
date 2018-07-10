package com.argalit.dev.chat_function;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity
{
    FirebaseDatabase database = FirebaseDatabase.getInstance();//ссылка на базу данных
     DatabaseReference myRef = database.getReference("messages");// ссылка на блок с messages
    Button mSendButton;
    EditText textMessage;

    ArrayList<String> messages =   new ArrayList<>();;// сообщения получаемые из базы данных
    RecyclerView mMessagesRecycler;


    private static int MAX_MESSAGE_LENGTH = 150;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSendButton = findViewById(R.id.sent_message_b);
        textMessage = findViewById(R.id.message_input);
        mMessagesRecycler = findViewById(R.id.messages_recycler);
        mMessagesRecycler.setLayoutManager(new LinearLayoutManager(this));



        final DataAdapter dataAdapter = new DataAdapter(this, messages);

        mMessagesRecycler.setAdapter(dataAdapter);







        mSendButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
                public void onClick(View view)
            {
                String msg = textMessage.getText().toString();
                    if(msg.equals(""))
                {
                    Toast.makeText(MainActivity.this, "Введите сообщение", Toast.LENGTH_SHORT).show();
                    return;
                }
                    if(msg.length() > MAX_MESSAGE_LENGTH )
                {
                    Toast.makeText(MainActivity.this, "Привышен максимальный порог в 150 символов", Toast.LENGTH_LONG).show();
                    return;
                }


                    myRef.push().setValue(msg);
                    textMessage.setText("");
                


            }
        });
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s)
            {
                String msg = dataSnapshot.getValue(String.class);
                messages.add(msg);
                dataAdapter.notifyDataSetChanged();
                mMessagesRecycler.smoothScrollToPosition(messages.size());
            }

            @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s)
            {

            }

            @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot)
            {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s)
            {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });


    }
}
