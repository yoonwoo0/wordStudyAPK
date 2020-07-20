package com.example.wordstudy;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

public class SettingActivity extends AppCompatActivity {

    RadioButton rd_word, rd_mean;
    Button bt_ok;
    int mode ; // original mode = 0 , mean mode = 1

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        rd_word = (RadioButton)findViewById(R.id.rdbt_word);
        rd_mean = (RadioButton)findViewById(R.id.rdbt_mean);
        bt_ok = (Button)findViewById(R.id.btn_ok);

        Intent gMode = getIntent();
        mode = gMode.getIntExtra("mode",0);

        bt_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(rd_word.isChecked()){
                    mode = 0;
                    Toast.makeText(SettingActivity.this, "뜻 맞추기 모드", Toast.LENGTH_SHORT).show();
                }else if(rd_mean.isChecked()){
                    mode = 1;
                    Toast.makeText(SettingActivity.this, "영단어 맞추기 모드", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onBackPressed(){
        Intent homeIntent = new Intent(SettingActivity.this, MainActivity.class);
        homeIntent.putExtra("mode",mode);
        startActivity(homeIntent);
    }
}
