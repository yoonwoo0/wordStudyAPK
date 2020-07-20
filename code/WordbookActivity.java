package com.example.wordstudy;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class WordbookActivity extends Activity {

    Button bt_prev, bt_next, bt_test, bt_repeat;
    TextView txv_days, txv_word,txv_mean, txv_wordNum;
    int wb_index =0, wb_num = 1, prev_next_value, maxValue, minValue =1;;
    String day, word_arr[], mean_arr[];
    int mode;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_study_word);

        bt_prev = (Button)findViewById(R.id.btn_prev);
        bt_next = (Button)findViewById(R.id.btn_next);
        bt_test = (Button)findViewById(R.id.btn_test_wordbook);
        bt_repeat = (Button)findViewById(R.id.btn_repeat);
        txv_days = (TextView)findViewById(R.id.txv_days);
        txv_word = (TextView)findViewById(R.id.txv_word);
        txv_mean = (TextView)findViewById(R.id.txv_mean);
        txv_wordNum = (TextView)findViewById(R.id.txv_wordNum);

        Intent subIntent = getIntent();
        day = subIntent.getStringExtra("days");
        word_arr = subIntent.getStringArrayExtra("word");
        mean_arr = subIntent.getStringArrayExtra("mean");
        mode = subIntent.getIntExtra("mode",0);
        txv_days.setText(day);

        wb_index = (Integer.parseInt(day)-1)*10;
        txv_word.setText(word_arr[wb_index]);
        txv_mean.setText(mean_arr[wb_index]);

        minValue = wb_index;
        maxValue = wb_index+9;



        bt_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                prev_next_value = 1;

                view_wordbook();

            }
        });
        bt_prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prev_next_value = 0;

                view_wordbook();
            }
        });



        bt_repeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wb_index = minValue;
                wb_num = 1;
                txv_word.setText(word_arr[wb_index]);
                txv_mean.setText(mean_arr[wb_index]);
                txv_wordNum.setText(String.valueOf(wb_num));

                bt_test.setVisibility(View.INVISIBLE);
                bt_repeat.setVisibility(View.INVISIBLE);
            }
        });

        bt_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent wbIntent = new Intent(WordbookActivity.this, TestActivity.class);
                wbIntent.putExtra("word",word_arr);
                wbIntent.putExtra("mean",mean_arr);
                wbIntent.putExtra("days",day);
                wbIntent.putExtra("mode",mode);
                startActivity(wbIntent);
            }
        });
    }

    public void view_wordbook(){
        if(prev_next_value == 0){

            if(minValue < wb_index){
                wb_index-=1;
                wb_num-=1;
                txv_word.setText(word_arr[wb_index]);
                txv_mean.setText(mean_arr[wb_index]);
                txv_wordNum.setText(String.valueOf(wb_num));

                bt_test.setVisibility(View.INVISIBLE);
                bt_repeat.setVisibility(View.INVISIBLE);

            }else {
                Toast.makeText(WordbookActivity.this, "첫 번째 단어입니다.",Toast.LENGTH_SHORT).show();
            }
        }
        else if(prev_next_value == 1){

            if(wb_index < maxValue){
                wb_index+=1;
                wb_num+=1;
                txv_word.setText(word_arr[wb_index]);
                txv_mean.setText(mean_arr[wb_index]);
                txv_wordNum.setText(String.valueOf(wb_num));

            }else {
                AlertDialog.Builder alertBox = new AlertDialog.Builder(this);
                alertBox.setMessage("                         마지막 단어입니다!\n" +
                        "               시험 또는 다시공부 하시겠습니까?");

                alertBox.setNegativeButton("다시 공부하기", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        wb_index = minValue;
                        wb_num = 1;
                        txv_word.setText(word_arr[wb_index]);
                        txv_mean.setText(mean_arr[wb_index]);
                        txv_wordNum.setText(String.valueOf(wb_num));
                    }
                });
                alertBox.setPositiveButton("시험 보기", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent wbIntent = new Intent(WordbookActivity.this, TestActivity.class);
                        wbIntent.putExtra("word",word_arr);
                        wbIntent.putExtra("mean",mean_arr);
                        wbIntent.putExtra("days",day);
                        wbIntent.putExtra("mode",mode);
                        startActivity(wbIntent);
                    }
                });
                AlertDialog alert = alertBox.create();
                alert.show();
            }
        }
    }

    @Override
    public void onBackPressed(){
        AlertDialog.Builder alertBox = new AlertDialog.Builder(this);
        alertBox.setMessage("                       학습이 진행중입니다!\n" +
                "                   학습을 종료하시겠습니까?");

        alertBox.setNegativeButton("이어서 학습하기", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alertBox.setPositiveButton("그만하기", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent homeIntent = new Intent(WordbookActivity.this, StudyActivity.class);
                homeIntent.putExtra("mode",mode);
                startActivity(homeIntent);
            }
        });
        AlertDialog alert = alertBox.create();
        alert.show();
    }


}