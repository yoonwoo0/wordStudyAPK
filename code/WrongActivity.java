package com.example.wordstudy;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class WrongActivity extends Activity {

    TextView txv_q_num,txv_question, txv_qname;
    RadioButton rb_answer_1,rb_answer_2,rb_answer_3,rb_answer_4,rb_test;
    String word_arr[], mean_arr[], correct_arr[], select_arr[], day;
    Button bt_next, bt_result;
    int arr[] = new int[4] ;
    int wrong_arr[];
    Random rd = new Random();
    boolean rb_checked;
    String correct_str="", select_str="";
    int w_index=0, seed, tmp, answer_len, mode;
    ArrayList chkW_arylist = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wrong);

        txv_q_num = (TextView)findViewById(R.id.txv_wrong_qnum);
        txv_question = (TextView)findViewById(R.id.txv_wrong_question);
        txv_qname = (TextView)findViewById(R.id.txv_qname);
        rb_test = (RadioButton)findViewById(R.id.rb_test_wrong);
        rb_answer_1 = (RadioButton)findViewById(R.id.rdbt_answer_wrong_1);
        rb_answer_2 = (RadioButton)findViewById(R.id.rdbt_answer_wrong_2);
        rb_answer_3 = (RadioButton)findViewById(R.id.rdbt_answer_wrong_3);
        rb_answer_4 = (RadioButton)findViewById(R.id.rdbt_answer_wrong_4);
        bt_next = (Button)findViewById(R.id.btn_next_wrong);
        bt_result = (Button)findViewById(R.id.btn_wrong_result);

        Intent myIntent = getIntent();
        wrong_arr = myIntent.getIntArrayExtra("wrong");
        word_arr = myIntent.getStringArrayExtra("word");
        mean_arr = myIntent.getStringArrayExtra("mean");
        correct_arr = myIntent.getStringArrayExtra("correct");
        select_arr = myIntent.getStringArrayExtra("select");
        day = myIntent.getStringExtra("days");
        mode = myIntent.getIntExtra("mode",0);

        for(int i = 0; i<wrong_arr.length; i++){
            if( i == 0 && wrong_arr[i]>=0){
                answer_len+=1;
            } else if(i > 0 && wrong_arr[i]!=0){
                answer_len+=1;
            }
        }
        qnum_question_setText(w_index);
        mixed_index(wrong_arr[0]);
        rb_setText();

        bt_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rb_checked = rb_answer_1.isChecked() || rb_answer_2.isChecked() || rb_answer_3.isChecked() || rb_answer_4.isChecked();
                if (rb_checked == false) {
                    Toast.makeText(WrongActivity.this, "정답을 체크해주세요.", Toast.LENGTH_SHORT).show();

                } else if(w_index < answer_len-1){

                    set_select_str();
                    if(mode == 0){
                        correct_str+= mean_arr[wrong_arr[w_index]]+",";
                    }else if(mode == 1){
                        correct_str+= word_arr[wrong_arr[w_index]]+",";
                    }

                    w_index+=1;
                    qnum_question_setText(w_index);
                    mixed_index(wrong_arr[w_index]);
                    rb_setText();

                } else if( w_index == answer_len-1){
                    if(mode == 0){
                        correct_str+= mean_arr[wrong_arr[w_index]]+",";
                    }else if(mode == 1){
                        correct_str+= word_arr[wrong_arr[w_index]]+",";
                    }


                    set_select_str();
                    set_rb_Invisible();
                    txv_question.setText("오답을 다시 체크하세요");
                    txv_qname.setVisibility(View.INVISIBLE);
                    txv_q_num.setVisibility(View.INVISIBLE);
                    bt_next.setVisibility(View.GONE);
                    bt_result.setVisibility(View.VISIBLE);
                }
                rb_test.setChecked(true);
                rb_test.setChecked(false);
            }
        });

        bt_result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                correct_arr = correct_str.split(",");
                select_arr = select_str.split(",");

                for(int i=0; i<correct_arr.length; i++){
                    correct_arr[i] = correct_arr[i].trim();
                    select_arr[i] = select_arr[i].trim();
                    if(correct_arr[i].equals(select_arr[i])){
                    } else {
                        chkW_arylist.add(wrong_arr[i]);
                    }
                }


                Intent myIntent = new Intent(WrongActivity.this, CorrectActivity.class);
                myIntent.putExtra("correct", correct_arr);
                myIntent.putExtra("select", select_arr);
                myIntent.putExtra("word", word_arr);
                myIntent.putExtra("mean", mean_arr);
                myIntent.putExtra("days", day);
                myIntent.putParcelableArrayListExtra("a",chkW_arylist);
                myIntent.putExtra("mode", mode);
                startActivity(myIntent);
            }
        });
    }

    public void qnum_question_setText(int pIndex){
        txv_q_num.setText(""+(pIndex+1));
        if(mode == 0){
            txv_question.setText(""+word_arr[wrong_arr[pIndex]]);
        }else if(mode == 1){
            txv_question.setText(""+mean_arr[wrong_arr[pIndex]]);
        }

    }

    public void mixed_index(int pCorrect){
        arr[3] = pCorrect;
        for(int i = 0; i < 3; i++) {
            arr[i] = rd.nextInt(mean_arr.length) ;
            while(arr[i]== arr[3]){
                arr[i] = rd.nextInt(mean_arr.length);
            }
            for(int j = 0; j < i; j++) {
                if(arr[j] == arr[i])
                    i--;
            }
        }
        for(int i = 0; i<4; i++){
            tmp = arr[i];
            seed = rd.nextInt(3)+1;
            arr[i] = arr[seed];
            arr[seed] = tmp;
        }
    }

    public void rb_setText(){
        if(mode == 0){
            rb_answer_1.setText(""+mean_arr[arr[0]]);
            rb_answer_2.setText(""+mean_arr[arr[1]]);
            rb_answer_3.setText(""+mean_arr[arr[2]]);
            rb_answer_4.setText(""+mean_arr[arr[3]]);
        }else if(mode == 1){
            rb_answer_1.setText(""+word_arr[arr[0]]);
            rb_answer_2.setText(""+word_arr[arr[1]]);
            rb_answer_3.setText(""+word_arr[arr[2]]);
            rb_answer_4.setText(""+word_arr[arr[3]]);
        }
    }

    public void set_select_str(){
        if (rb_answer_1.isChecked()) {
            select_str += rb_answer_1.getText().toString()+",";
        } else if (rb_answer_2.isChecked()) {
            select_str += rb_answer_2.getText().toString()+",";
        } else if (rb_answer_3.isChecked()) {
            select_str += rb_answer_3.getText().toString()+",";
        } else {
            select_str += rb_answer_4.getText().toString()+",";
        }
    }

    public void set_rb_Invisible(){
        rb_answer_1.setVisibility(View.INVISIBLE);
        rb_answer_2.setVisibility(View.INVISIBLE);
        rb_answer_3.setVisibility(View.INVISIBLE);
        rb_answer_4.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onBackPressed(){
        AlertDialog.Builder alertBox = new AlertDialog.Builder(this);
        alertBox.setMessage("                       시험이 진행중입니다.\n" +
                "                   시험을 종료하시겠습니까?");

        alertBox.setNegativeButton("계속 시험보기", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alertBox.setPositiveButton("홈으로 가기", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent homeIntent = new Intent(WrongActivity.this, MainActivity.class);
                homeIntent.putExtra("mode",mode);
                startActivity(homeIntent);
            }
        });
        AlertDialog alert = alertBox.create();
        alert.show();
    }
}
