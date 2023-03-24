package com.example.texifool;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class LoginActivity extends AppCompatActivity {

    private EditText et_user;
    private Button btn_login;

    private ListView listView;
    private Button btn_create;

    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> arr_roomList = new ArrayList<>();
    private DatabaseReference reference = FirebaseDatabase.getInstance()
            .getReference().getRoot();
    private String name;

    private String str_name;
    private String str_room;

    Map<String, Object> map = new HashMap<String, Object>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("로그인");
        setContentView(R.layout.activity_login);

        et_user = (EditText)findViewById(R.id.et_user);
        btn_login = (Button)findViewById(R.id.btn_login);

        Intent intent = getIntent();


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str_name = et_user.getText().toString();

                if("".equals(str_name)){
                    Toast.makeText(LoginActivity.this, "닉네임을 입력해주세요!", Toast.LENGTH_SHORT).show();
                }else{
                    //firebase에 새로운 방 생성을 전달
                    str_room = intent.getStringExtra("roomName");
                    map.put(str_room, "");
                    reference.updateChildren(map);

                    Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
                    intent.putExtra("room_name", str_room);
                    intent.putExtra("user_name", str_name);
                    startActivity(intent);
                }

            }
        });


    }
}