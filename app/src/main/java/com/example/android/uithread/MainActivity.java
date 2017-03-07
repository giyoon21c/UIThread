package com.example.android.uithread;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static android.R.attr.button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG=MainActivity.class.getSimpleName();
    private Button buttonStart, buttonStop;
    private TextView threadCountTextView;
    private boolean mStopLoop;

    int count = 0;

    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i(TAG, "Thread id: " + Thread.currentThread().getId());

        buttonStart = (Button) findViewById(R.id.buttonThreadStarter);
        buttonStop = (Button) findViewById(R.id.buttonStopThread);
        threadCountTextView = (TextView) findViewById(R.id.textViewThreadCount);
        buttonStart.setOnClickListener(this);
        buttonStop.setOnClickListener(this);

        handler = new Handler(getApplicationContext().getMainLooper());
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
                            try {
                                Thread.sleep(1000);
                                count++;
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            Log.i(TAG, "Thread id in while loop:" + Thread.currentThread().getId()
                                    + " Thread count: " + count);

                            //cannot update element from UI thread directly.
                            //threadCountTextView.setText("" + count);
                            //Instead, use handler to post runnable to the message queue
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    threadCountTextView.setText("" + count);
                                }
                            });

                            //instead of using handler, textCountTextView can post a runnable as well
                            /*
                            threadCountTextView.post(new Runnable() {
                                @Override
                                public void run() {
                                    threadCountTextView.setText("" + count);
                                }
                            });
                            */
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
