package com.example.shimul.androidcontrol;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import java.io.PrintWriter;
import java.net.Socket;


public class Website extends AppCompatActivity {

    private Toolbar toolbar;
    private boolean isConnected=false;
    private boolean mouseMoved=false;
    private Socket socket;
    private PrintWriter out;
    public static String data,ipaddres;
    private Socket client;
    private Boolean flag = false;
    private PrintWriter printwriter;
    public  int count=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_website);
        ipaddres ="192.168.173.1";
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
    }

    public  void click(MenuItem item)
    {
        Intent intent=new Intent(Website.this,Reading.class);
        startActivity(intent);
    }

    public void oncli(View view) {
        switch (view.getId())
        {
            case R.id.fb:
                data="facebook";
                break;
            case R.id.tw:
                data="twtr";
                break;
            case R.id.daily:
                data="dailym";
                break;
            case R.id.you:
                data="youtube";
                break;
            case R.id.go:
                data="goal";
                break;
            case R.id.cric:
                data="cricinfo";
                break;
            case R.id.googl:
                data="google";
                break;
            case R.id.instr:
                data="instragram";
                break;

        }

        if (flag == true) {
            Context context = getApplicationContext();

            CharSequence text = "Connection failed or Invalid IpAddress !!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

            flag = false;

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(R.string.dialog_message).setTitle(R.string.dialog_title);

            builder.setMessage("Connection failed or Invalid IpAddress !!")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            finish();
                        }
                    });


            AlertDialog alert = builder.create();
            alert.setTitle("AlertDialog");
            alert.show();


        }
        SendMessage sendMessageTask = new SendMessage();
        sendMessageTask.execute();
    }

    private class SendMessage extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {

                client = new Socket(ipaddres, 4444); // connect to the server
                printwriter = new PrintWriter(client.getOutputStream(), true);
                printwriter.write(data); // write the message to output stream
                printwriter.flush();
                printwriter.close();
                client.close(); // closing the connection

            } catch (Exception e) {
                flag = true;
            }
            return null;

        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
