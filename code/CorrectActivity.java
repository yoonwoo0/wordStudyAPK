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

import java.util.ArrayList;

public class CorrectActivity extends Activity {

    TextView txv_word, txv_mean, txv_wrong_num;
    Button bt_prev,bt_next, bt_nStep;
    String correct_arr[],select_arr[], word_arr[], mean_arr[], day;
    int wrong_arr[] = new int[10];
    boolean f_RL;
    int wb_index, w_len=0, w_index=0, w_num = 1, pDay, mode;
    ArrayList chkW_arylist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_correct);

        bt_prev = (Button)findViewById(R.id.btn_prev_wrong);
        bt_next = (Button)findViewById(R.id.btn_next_wrong);
        bt_nStep = (Button)findViewById(R.id.btn_nStep);
        txv_word = (TextView)findViewById(R.id.txv_word_wrong);
        txv_mean = (TextView)findViewById(R.id.txv_mean_wrong);
        txv_wrong_num = (TextView)findViewById(R.id.txv_wrong_num);


        Intent myIntent = getIntent();
        word_arr = myIntent.getStringArrayExtra("word");
        mean_arr = myIntent.getStringArrayExtra("mean");
        correct_arr = myIntent.getStringArrayExtra("correct");
        select_arr = myIntent.getStringArrayExtra("select");
        day = myIntent.getStringExtra("days");
        f_RL = myIntent.getBooleanExtra("f_RL",false);
        chkW_arylist = myIntent.getParcelableArrayListExtra("a");
        mode = myIntent.getIntExtra("mode",0);

        if(f_RL){
            wb_index = (Integer.parseInt(day)-1) *10;
            for(int i=0; i<10; i++){
                correct_arr[i] = correct_arr[i].trim();
                select_arr[i] = select_arr[i].trim();
                if(correct_arr[i].equals(select_arr[i])){
                } else {
                    wrong_arr[w_len] = wb_index + i;
                    w_len+=1;
                }
            }
            word_mean_setText();
        } else{
            for(int i=0; i<chkW_arylist.size(); i++){
                Object a = chkW_arylist.get(i);
                wrong_arr[i] = Integer.parseInt(a.toString());
            }
            w_len = chkW_arylist.size();
            word_mean_setText();

            if(w_len == 0){
                txv_wrong_num.setVisibility(View.INVISIBLE);
                bt_next.setVisibility(View.GONE);
                bt_prev.setVisibility(View.GONE);
                bt_nStep.setVisibility(View.VISIBLE);

                txv_word.setText("        오답이 없습니다!!   ");
                txv_mean.setText("          고생했습니다!   ");
            }
        }

        bt_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(w_index < wrong_arr.length-1){
                    if(wrong_arr[w_index+1]==0){
                        AlertDialog.Builder alertBox = new AlertDialog.Builder(CorrectActivity.this);
                        alertBox.setMessage("                         마지막 오답입니다.\n" +
                                "   오답을 그만보고 오답문제를 푸시겠습니까?");
                        alertBox.setNegativeButton("오답 계속", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                        alertBox.setPositiveButton("오답문제 다시풀기", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Intent myIntent = new Intent(CorrectActivity.this, WrongActivity.class);
                                myIntent.putExtra("wrong", wrong_arr);
                                myIntent.putExtra("word", word_arr);
                                myIntent.putExtra("mean",mean_arr);
                                myIntent.putExtra("correct",correct_arr);
                                myIntent.putExtra("select",select_arr);
                                myIntent.putExtra("days",day);
                                myIntent.putExtra("mode",mode);

                                startActivity(myIntent);
                            }
                        });
                        AlertDialog alert = alertBox.create();
                        alert.show();
                    } else{
                        next_word_att();
                        word_mean_setText();
                        if(wrong_arr[w_index+1] == 0){
                            bt_next.setText("오답 다시풀기");
                        }

                    }
                }
            }
        });
        bt_prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(w_index > 0){
                    prev_word_att();
                    word_mean_setText();
                    if(w_index != wrong_arr.length-1){
                        bt_next.setText("다음 오답");
                    }
                } else{
                    Toast.makeText(CorrectActivity.this, "첫 번째 오답입니다.",Toast.LENGTH_SHORT).show();
                }
            }
        });
        bt_nStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertBox = new AlertDialog.Builder(CorrectActivity.this);
                alertBox.setMessage("                    모든 학습이 끝났습니다..\n" +
                        "                 홈 또는 다음 단계를 진행하세요!");

                alertBox.setNegativeButton("다음 단계로", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        pDay = Integer.parseInt(day) + 1;
                        if(pDay > 30){
                            Toast.makeText(CorrectActivity.this, "마지막 일차였습니다. 홈으로 갑니다.", Toast.LENGTH_SHORT).show();
                            Intent myIntent = new Intent(CorrectActivity.this, MainActivity.class);
                            myIntent.putExtra("mode",mode);
                            startActivity(myIntent);
                        } else {
                            String tmpDay = String.valueOf(pDay);
                            Intent myIntent = new Intent(CorrectActivity.this, WordbookActivity.class);

                            myIntent.putExtra("word", word_arr);
                            myIntent.putExtra("mean",mean_arr);
                            myIntent.putExtra("days",tmpDay);
                            myIntent.putExtra("mode",mode);
                            startActivity(myIntent);
                        }


                    }
                });
                alertBox.setPositiveButton("홈으로 가기", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent homeIntent = new Intent(CorrectActivity.this, MainActivity.class);
                        homeIntent.putExtra("mode",mode);
                        startActivity(homeIntent);
                    }
                });
                AlertDialog alert = alertBox.create();
                alert.show();
            }
        });
    }

    public void next_word_att(){
        w_index+=1;
        w_num+=1;
    }
    public void prev_word_att(){
        w_index-=1;
        w_num-=1;
    }

    public void word_mean_setText(){
        txv_wrong_num.setText(w_num+" / "+w_len);
        if(mode == 0){
            txv_word.setText(""+word_arr[wrong_arr[w_index]]);
            txv_mean.setText(""+mean_arr[wrong_arr[w_index]]);
        }else if(mode == 1){
            txv_word.setText(""+mean_arr[wrong_arr[w_index]]);
            txv_mean.setText(""+word_arr[wrong_arr[w_index]]);
        }
    }

    @Override
    public void onBackPressed(){
        AlertDialog.Builder alertBox = new AlertDialog.Builder(this);
        alertBox.setMessage("                      오답을 확인중입니다.\n" +
                "                 오답확인을 종료하시겠습니까??");

        alertBox.setNegativeButton("오답 계속보기", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alertBox.setPositiveButton("홈으로 가기", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent homeIntent = new Intent(CorrectActivity.this, MainActivity.class);
                homeIntent.putExtra("mode",mode);
                startActivity(homeIntent);
            }
        });
        AlertDialog alert = alertBox.create();
        alert.show();
    }
}
