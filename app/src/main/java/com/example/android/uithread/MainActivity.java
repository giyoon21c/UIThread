package com.example.android.uithread;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import static android.R.attr.button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG=MainActivity.class.getSimpleName();
    private Button buttonStart, buttonStop;
    private boolean mStopLoop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i(TAG, "Thread id: " + Thread.currentThread().getId());

        buttonStart = (Button) findViewById(R.id.buttonThreadStarter);
        buttonStop = (Button) findViewById(R.id.buttonStopThread);

        buttonStart.setOnClickListener(this);
        buttonStop.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonThreadStarter:
                mStopLoop = true;

                /*
                while (mStopLoop) {
                    Log.i(TAG, "Thread id in while loop:" + Thread.currentThread().getId());
                }
                */

                // create a new thread instead of while in the main UI thread
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (mStopLoop) {
                            Log.i(TAG, "Thread id in while loop:" + Thread.currentThread().getId());
                        }
                    }
                }).start();

                break;
            case R.id.buttonStopThread:
                mStopLoop = false;
                break;
        }
    }
}
