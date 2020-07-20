package com.example.wordstudy;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Random;

public class TestActivity extends Activity {

    public static Context testContext;

    TextView txv_q_num,txv_question ,txv_qname;
    RadioButton rb_answer_1,rb_answer_2,rb_answer_3,rb_answer_4,rb_test;
    Button bt_next, bt_test_result;
    boolean rb_checked;
    int arr[] = new int[4] ;
    String correct_str="", select_str="", day;
    int q_num =1, maxValue, wb_index, correct, seed, tmp, mode;
    Random rd = new Random();
    String word_arr[], mean_arr[], correct_arr[], select_arr[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_test);

        txv_q_num = (TextView)findViewById(R.id.txv_question_num);
        txv_question = (TextView)findViewById(R.id.txv_question);
        txv_qname = (TextView)findViewById(R.id.txv_qname);
        rb_answer_1 = (RadioButton)findViewById(R.id.rdbt_answer_1);
        rb_answer_2 = (RadioButton)findViewById(R.id.rdbt_answer_2);
        rb_answer_3 = (RadioButton)findViewById(R.id.rdbt_answer_3);
        rb_answer_4 = (RadioButton)findViewById(R.id.rdbt_answer_4);
        bt_next = (Button)findViewById(R.id.btn_next_test);
        bt_test_result = (Button)findViewById(R.id.btn_test_result);
        rb_test = (RadioButton)findViewById(R.id.rb_test);

        Intent testIntent = getIntent();

        day = testIntent.getStringExtra("days");
        word_arr = testIntent.getStringArrayExtra("word");
        mean_arr = testIntent.getStringArrayExtra("mean");
        mode = testIntent.getIntExtra("mode",0);

        wb_index = ((Integer.parseInt(day))-1)*10;
        correct = wb_index;
        maxValue = wb_index+9;

        qnum_question_setText();
        mixed_index(correct);
        rb_setText();

        bt_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rb_checked = rb_answer_1.isChecked() || rb_answer_2.isChecked() || rb_answer_3.isChecked() || rb_answer_4.isChecked();
                if (rb_checked == false) {
                    Toast.makeText(TestActivity.this, "정답을 체크해주세요.", Toast.LENGTH_SHORT).show();

                } else if(correct < maxValue){

                    set_select_str();
                    if(mode == 0){
                        correct_str+= mean_arr[correct]+",";
                    }else if(mode == 1){
                        correct_str+= word_arr[correct]+",";
                    }

                    q_num += 1;
                    correct+=1;

                    qnum_question_setText();
                    mixed_index(correct);
                    rb_setText();

                } else if( correct == maxValue){

                    if(mode == 0){
                        correct_str+= mean_arr[correct]+",";
                    }else if(mode == 1){
                        correct_str+= word_arr[correct]+",";
                    }

                    set_select_str();
                    bt_next.setVisibility(View.GONE);
                    bt_test_result.setVisibility(View.VISIBLE);
                    txv_question.setText("          문제 끝 \n"+"    수고하셨습니다.");
                    txv_qname.setVisibility(View.INVISIBLE);
                    txv_q_num.setVisibility(View.INVISIBLE);
                    set_rb_Invisible();

                }
                rb_test.setChecked(true);
                rb_test.setChecked(false);
            }
        });
        bt_test_result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                correct_arr = correct_str.split(",");
                select_arr = select_str.split(",");

                Intent myIntent = new Intent(TestActivity.this, ResultActivity.class);
                myIntent.putExtra("correct", correct_arr);
                myIntent.putExtra("select", select_arr);
                myIntent.putExtra("word", word_arr);
                myIntent.putExtra("mean", mean_arr);
                myIntent.putExtra("days", day);
                myIntent.putExtra("mode",mode);
                startActivity(myIntent);
            }
        });
    }

    public void mixed_index(int pCorrect){
        arr[3] = correct;
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

    public void qnum_question_setText(){
        txv_q_num.setText(""+q_num);
        if(mode ==0){
            txv_question.setText(""+word_arr[correct]);
        }else if(mode ==1){
            txv_question.setText(""+mean_arr[correct]);
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
                Intent homeIntent = new Intent(TestActivity.this, MainActivity.class);
                homeIntent.putExtra("mode",mode);
                startActivity(homeIntent);
            }
        });
        AlertDialog alert = alertBox.create();
        alert.show();
    }
}
