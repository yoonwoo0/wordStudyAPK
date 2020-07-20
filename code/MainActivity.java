package com.example.wordstudy;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

    Button bt_study,bt_word, bt_setting;
    int mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bt_study = (Button)findViewById(R.id.btn_study_home);
        bt_word = (Button)findViewById(R.id.btn_myWordNote);
        bt_setting = (Button)findViewById(R.id.btn_setting);

        Intent gMode = getIntent();
        mode = gMode.getIntExtra("mode",0);

        bt_study.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent homeStudy = new Intent(MainActivity.this, StudyActivity.class);
                homeStudy.putExtra("mode",mode);
                startActivity(homeStudy);
            }
        });

        bt_word.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent wordStudy = new Intent(MainActivity.this, MyNoteBookActivity.class);
                wordStudy.putExtra("mode",mode);
                startActivity(wordStudy);
            }
        });

        bt_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent setting = new Intent(MainActivity.this, SettingActivity.class);
                setting.putExtra("mode", mode);
                startActivity(setting);
            }
        });
    }

    @Override
    public void onBackPressed(){
        AlertDialog.Builder alertBox = new AlertDialog.Builder(this);
        alertBox.setMessage("어플을 종료하시겠습니까?");

        alertBox.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alertBox.setPositiveButton("예", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finishAffinity();
            }
        });
        AlertDialog alert = alertBox.create();
        alert.show();
    }
}
