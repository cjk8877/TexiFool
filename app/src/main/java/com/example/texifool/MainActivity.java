package com.example.texifool;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button setStartPointBtn, setEndPointBtn, startMatchBtn;
    TextView[] txList = new TextView[11];
    Dialog setPointDlg;
    TextView schoolDlgBtn, stationDlgBtn;
    LinearLayout schoolDlg, stationDlg, bottomChatBtn;
    String matchCode;
    String[] path = new String[2];
    final String TAG = "MAIN";
    String load;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setPointDlg = new Dialog(MainActivity.this);
        setPointDlg.setContentView(R.layout.dialog);

        startMatchBtn = (Button) findViewById(R.id.startMatchBtn);
        setEndPointBtn = (Button) findViewById(R.id.setEndPointBtn);
        setStartPointBtn = (Button) findViewById(R.id.setStartPointBtn);
        bottomChatBtn = (LinearLayout) findViewById(R.id.bottomChatBtn);

        bottomChatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent chatIntent = new Intent(getApplicationContext(), ChatRoomActivity.class);
                chatIntent.putExtra("name", "none");
                startActivity(chatIntent);
            }
        });


        //시작지점 선택 onclick
        setStartPointBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSetPointDlg(setStartPointBtn, 0);
            }
        });

        //도착지점 선택 onclick
        setEndPointBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSetPointDlg(setEndPointBtn, 1);
            }

        });

        //매칭 시작 onclick
        startMatchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent matchingIntent = new Intent(getApplicationContext(), Matching.class);

                if(path[0] != null && path[1] != null){
                    load = setStartPointBtn.getText().toString() + "-\n" + setEndPointBtn.getText().toString();
                    matchCode = path[0] + path[1];
                    Log.d(TAG, matchCode);
                    matchingIntent.putExtra("load", load);
                    matchingIntent.putExtra("matchCode", matchCode);
                    startActivity(matchingIntent);
                }else{
                    Toast.makeText(getApplicationContext(), "출발지, 도착지를 선택하십시오", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    //지점 선택 onclick시 보여주는 dialog
    public void showSetPointDlg(Button selectedBtn, int target){
        setPointDlg.show();
        //학교 지정
        txList[0] = setPointDlg.findViewById(R.id.btn1);
        txList[1] = setPointDlg.findViewById(R.id.btn2);
        txList[2] = setPointDlg.findViewById(R.id.btn3);
        txList[3] = setPointDlg.findViewById(R.id.btn4);
        txList[4] = setPointDlg.findViewById(R.id.btn5);

        //여기부터 역을 지정
        txList[5] = setPointDlg.findViewById(R.id.sbtn1);
        txList[6] = setPointDlg.findViewById(R.id.sbtn2);
        txList[7] = setPointDlg.findViewById(R.id.sbtn3);
        txList[8] = setPointDlg.findViewById(R.id.sbtn4);

        schoolDlgBtn = setPointDlg.findViewById(R.id.schoolDlgBtn);
        stationDlgBtn = setPointDlg.findViewById(R.id.stationDlgBtn);

        schoolDlg = setPointDlg.findViewById(R.id.schoolDlg);
        stationDlg = setPointDlg.findViewById(R.id.stationDlg);

        //각각 button에 onclick 지정
        for(int i = 0; i < 9; i++){
            int finalI = i;
            txList[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectedBtn.setText(txList[finalI].getText().toString());
                    setPointDlg.dismiss();
                    path[target] = Integer.toString(finalI);
                }
            });
        }

        //학교 선택으로 변경
        schoolDlgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                schoolDlg.setVisibility(View.VISIBLE);
                stationDlg.setVisibility(View.GONE);

                schoolDlgBtn.setBackgroundColor(Color.parseColor("#A1D4A0"));
                stationDlgBtn.setBackgroundColor(Color.parseColor("#DADADA"));
            }
        });

        //역 선택으로 변경
        stationDlgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                schoolDlg.setVisibility(View.GONE);
                stationDlg.setVisibility(View.VISIBLE);

                schoolDlgBtn.setBackgroundColor(Color.parseColor("#DADADA"));
                stationDlgBtn.setBackgroundColor(Color.parseColor("#A1D4A0"));
            }
        });
    }
}