package com.example.wordstudy;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ResultActivity extends Activity {

    TextView txv_score;
    Button bt_check,bt_retry;
    String day, word_arr[],mean_arr[], correct_arr[], select_arr[];
    boolean f_RL = true;
    int score, mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_result);

        txv_score = (TextView)findViewById(R.id.txv_score);
        bt_check = (Button)findViewById(R.id.btn_check);
        bt_retry = (Button)findViewById(R.id.btn_retry);

        Intent resultIntent = getIntent();
        correct_arr = resultIntent.getStringArrayExtra("correct");
        select_arr = resultIntent.getStringArrayExtra("select");
        day = resultIntent.getStringExtra("days");
        word_arr = resultIntent.getStringArrayExtra("word");
        mean_arr = resultIntent.getStringArrayExtra("mean");
        mode = resultIntent.getIntExtra("mode",0);

        score = 0;
        for(int i = 0; i<10; i++){
            if(correct_arr[i].equals(select_arr[i])){
                score+=10;
            }
        }
        txv_score.setText(""+score+"점");

        bt_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(score == 100){
                    Toast.makeText(ResultActivity.this, "오답이 없습니다.",Toast.LENGTH_SHORT).show();

                    AlertDialog.Builder alertBox = new AlertDialog.Builder(ResultActivity.this);
                    alertBox.setMessage("            " + day+" 일차 시험점수는 "+score+"점 입니다.\n"+
                            "       오답이 없습니다. 홈으로 가시겠습니까?");

                    alertBox.setNegativeButton("홈으로 가기", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent myIntent = new Intent(ResultActivity.this, MainActivity.class);
                            myIntent.putExtra("mode",mode);
                            startActivity(myIntent);
                        }
                    });
                    alertBox.setPositiveButton("다음 단계 단어공부", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent myIntent = new Intent(ResultActivity.this, WordbookActivity.class);
                            if(Integer.parseInt(day) + 1 <= 30){
                                int pDay = Integer.parseInt(day)+1;
                                day = String.valueOf(pDay);
                            }
                            myIntent.putExtra("days", day);
                            myIntent.putExtra("word", word_arr);
                            myIntent.putExtra("mean", mean_arr);
                            myIntent.putExtra("mode", mode);
                            startActivity(myIntent);
                        }
                    });
                    AlertDialog alert = alertBox.create();
                    alert.show();

                }else {
                    Intent myIntent = new Intent(ResultActivity.this, CorrectActivity.class);
                    myIntent.putExtra("days", day);
                    myIntent.putExtra("word", word_arr);
                    myIntent.putExtra("mean", mean_arr);
                    myIntent.putExtra("correct", correct_arr);
                    myIntent.putExtra("select", select_arr);
                    myIntent.putExtra("f_RL",f_RL);
                    myIntent.putExtra("mode", mode);
                    startActivity(myIntent);
                }
            }
        });


        bt_retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(ResultActivity.this, TestActivity.class);
                myIntent.putExtra("days", day);
                myIntent.putExtra("word", word_arr);
                myIntent.putExtra("mean", mean_arr);
                myIntent.putExtra("mode", mode);
                startActivity(myIntent);
            }
        });
    }
    @Override
    public void onBackPressed(){
        AlertDialog.Builder alertBox = new AlertDialog.Builder(this);
        alertBox.setMessage("                 " + day+" 일차 시험점수는 "+score+"점 입니다.\n"+
                "       오답을 확인하지 않고 종료하시겠습니까?");

        alertBox.setNegativeButton("계속 학습하기", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alertBox.setPositiveButton("홈으로 가기", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent homeIntent = new Intent(ResultActivity.this, MainActivity.class);
                homeIntent.putExtra("mode", mode);
                startActivity(homeIntent);
            }
        });
        AlertDialog alert = alertBox.create();
        alert.show();
    }
}
