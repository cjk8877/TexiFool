package com.example.texifool;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class Matching extends AppCompatActivity {
    InetAddress serverAddr;
    Socket socket;
    PrintWriter sendWriter;
    private String ip = "192.168.219.100";
    private int port = 8888;
    final String TAG = "MATCHING";
    private Chronometer chronometer;
    private boolean running;
    TextView loadTv;
    ImageView loadingImg, toolbarLogo;


    String matchCode, Path, load;
    String read;

    @Override
    protected void onStop() {
        super.onStop();
        try {
            sendWriter.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.matching);

        chronometer = (Chronometer) findViewById(R.id.matchingChronometer);
        chronometer.setFormat("%s");
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();
        running = true;

        loadTv = (TextView) findViewById(R.id.loadTv);
        loadingImg = (ImageView) findViewById(R.id.loadingImg);
        toolbarLogo = (ImageView) findViewById(R.id.toolbarLogo);

        Intent intent = getIntent();
        matchCode = intent.getStringExtra("matchCode");
        load = intent.getStringExtra("load");
        loadTv.setText(load);

        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate);
        loadingImg.setAnimation(animation);

        final int[] flag = {0};

        toolbarLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toMainIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(toMainIntent);
            }
        });

        //서버와 통신
        new Thread() {
            public void run() {
                try {
                    InetAddress serverAddr = InetAddress.getByName(ip);
                    socket = new Socket(serverAddr, port);
                    sendWriter = new PrintWriter(socket.getOutputStream());
                    BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    if(flag[0] == 0) {
                        sendWriter.println(matchCode);
                        sendWriter.flush();
                        flag[0]++;
                    }
                    while(true){
                        read = input.readLine().substring(0,10);
                        if(read!=null){
                            //매칭이 완료되어 보낸 값을 읽었을 때 이동
                            Intent matchingSuccessIntent = new Intent(getApplicationContext(), LoginActivity.class);
                            matchingSuccessIntent.putExtra("roomName", read);
                            startActivity(matchingSuccessIntent);
                            Log.d(TAG, read);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } }
        }.start();
    }

    class msgUpdate implements Runnable{
        private String msg;
        public msgUpdate(String str) {this.msg=str;}

        @Override
        public void run() {

        }



    }
}