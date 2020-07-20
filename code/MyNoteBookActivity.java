package com.example.wordstudy;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.renderscript.ScriptGroup;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;

import static com.example.wordstudy.R.id.btn_dialog_Input;
import static com.example.wordstudy.R.id.icon;
import static com.example.wordstudy.R.id.switch4;
import static com.example.wordstudy.R.id.word;


class DBActivity extends SQLiteOpenHelper {
    MyNoteBookActivity mynote = new MyNoteBookActivity();
    public DBActivity(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE '"+mynote.databasename+"'" + "(num Integer primary key autoincrement, word Text, mean Text);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        onCreate(db);
    }
}

public class MyNoteBookActivity extends Activity {
    String databasename = "Test";
    Button bt_MyNote_Input, bt_MyNote_Serch, bt_MyNote_Delete, bt_MyNote_Show, bt_MyNote_Update;
    ListView listview;
    ListAdapter adapter, adapter1;
    ArrayList<HashMap<String,String>> wordlist;
    public static Context Mcontext;
    int mode ;
    void TableShow() {
        final DBActivity WordDB = new DBActivity(this,databasename, null , 1);
        SQLiteDatabase database = WordDB.getReadableDatabase();
        String sql = "select * from '"+databasename+"'";

        Cursor iCursor = database.rawQuery(sql, null);
        if (iCursor.moveToFirst()) {
            do {
                String dbword = iCursor.getString(1);
                String dbmean = iCursor.getString(2);

                HashMap<String,String> worddata = new HashMap<String,String>();
                worddata.put("word",dbword);
                worddata.put("mean",dbmean);
                wordlist.add(worddata);
                adapter = new SimpleAdapter(this,wordlist,R.layout.list_item,new String[]{"word","mean"},new int[]{R.id.word,R.id.mean});
                listview.setAdapter(adapter);
            }
            while (iCursor.moveToNext());
        }
        WordDB.close();
        database.close();
        iCursor.close();
    }

    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_word_note);
        wordlist = new ArrayList<HashMap<String, String>>();
        listview = (ListView)findViewById(R.id.listview);
        bt_MyNote_Input = (Button)findViewById(R.id.btn_MyNote_Input);
        bt_MyNote_Serch = (Button)findViewById(R.id.btn_MyNote_Serch);
        bt_MyNote_Delete = (Button)findViewById(R.id.btn_MyNote_Delete);
        bt_MyNote_Show = (Button)findViewById(R.id.btn_MyNote_Show);
        bt_MyNote_Update = (Button)findViewById(R.id.btn_MyNote_Update);
        Intent gMode = getIntent();
        mode = gMode.getIntExtra("mode",0);
        Mcontext = this;
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        final DBActivity WordDB = new DBActivity(this,databasename, null , 1);
        try {
            SQLiteDatabase database = WordDB.getWritableDatabase();
        } catch (SQLiteException e) {
            e.printStackTrace();
            Log.e("SQLite","데이터 베이스를 열수 없음");
            finish();
        }

        TableShow();

        bt_MyNote_Show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wordlist.clear();
                TableShow();
            }
        });
        bt_MyNote_Input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = getLayoutInflater();
                View view = inflater.inflate(R.layout.dialog, null);
                alert.setView(view);
                final EditText ed_Alert_Word = (EditText)view.findViewById(R.id.edt_dialog_Word);
                final EditText ed_Alert_Mean = (EditText)view.findViewById(R.id.edt_dialog_Mean);
                final Button bt_alert_Input = (Button)view.findViewById(R.id.btn_dialog_Input);
                final Button bt_alert_Can = (Button)view.findViewById(R.id.btn_dialog_Can);
                final AlertDialog dialog = alert.create();
                bt_alert_Input.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SQLiteDatabase database = WordDB.getWritableDatabase();
                        String inword = ed_Alert_Word.getText().toString();
                        String inmean = ed_Alert_Mean.getText().toString();
                        inword = inword.trim();
                        if(inword.equals("")){
                            Toast.makeText(getApplicationContext(), "단어를 입력 해주세요.", Toast.LENGTH_SHORT).show();
                            dialog.cancel();
                            return;
                        }
                        String sql = "select * from '"+databasename+"' where word = '" + inword + "' ";
                        Cursor iCursor = database.rawQuery(sql,null);
                        if(iCursor!= null && iCursor.moveToFirst());
                        if(iCursor.getCount() == 0) {
                            sql = "Insert into '"+databasename+"' (word, mean) values('" + inword + "','" + inmean + "')";
                            database.execSQL(sql);
                        }else {
                            Toast.makeText(getApplicationContext(), "같은 단어가 있습니다.", Toast.LENGTH_SHORT).show();
                        }
                        wordlist.clear();
                        TableShow();
                        WordDB.close();
                        database.close();
                        dialog.cancel();
                    }
                });
                bt_alert_Can.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });
                dialog.show();
            }
        });

        final EditText ed_Alert_FindWord = new EditText(this);
        final AlertDialog.Builder alert2 = new AlertDialog.Builder(this);
        adapter1 = new SimpleAdapter(this,wordlist,R.layout.list_item,new String[]{"word","mean"},new int[]{R.id.word,R.id.mean});

        bt_MyNote_Serch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //select
                alert2.setTitle("검색할 단어를 입력하세요");
                alert2.setMessage("단어");
                ed_Alert_FindWord.setText("");
                ed_Alert_FindWord.setSingleLine();
                alert2.setView(ed_Alert_FindWord);
                alert2.setPositiveButton("입력", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SQLiteDatabase database = WordDB.getReadableDatabase();
                        String sql = "select * from '"+databasename+"'";
                        Cursor iCursor = database.rawQuery(sql,null);
                        String dbword = ed_Alert_FindWord.getText().toString();
                        String dbmean = null ;
                        iCursor.moveToFirst();
                        wordlist.clear();
                        do {
                            if(iCursor.getString(1).startsWith(dbword) == true){
                                HashMap<String,String> worddata = new HashMap<String,String>();
                                worddata.put("word",iCursor.getString(1));
                                worddata.put("mean",iCursor.getString(2));
                                wordlist.add(worddata);
                                listview.setAdapter(adapter1);
                            }
                        }
                        while(iCursor.moveToNext());
                        WordDB.close();
                        database.close();
                        iCursor.close();
                    }
                });
                alert2.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alert2.setCancelable(false);
                    }
                });
                if(ed_Alert_FindWord.getParent() != null){
                    ((ViewGroup)ed_Alert_FindWord.getParent()).removeView(ed_Alert_FindWord);
                }
                alert2.show();
            }
        });

        final EditText ed_Alert_DeleteWord = new EditText(this);
        final AlertDialog.Builder alert3 = new AlertDialog.Builder(this);

        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                //delete
                alert3.setTitle("삭제하시겠습니까?");
                alert3.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SQLiteDatabase database = WordDB.getReadableDatabase();
                        String dbword = listview.getAdapter().getItem(position).toString();
                        int i = dbword.indexOf(',');
                        dbword = dbword.substring(i);
                        dbword = dbword.replace(", word=","");
                        dbword = dbword.replace("}","");
                        String dbmean = null;
                        String sql = "delete from '"+databasename+"' where word = '" + dbword + "' ";
                        database.execSQL(sql);
                        WordDB.close();
                        database.close();
                        wordlist.clear();
                        listview.setAdapter(adapter1);
                        TableShow();
                    }
                });
                alert3.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alert3.setCancelable(false);
                    }
                });
                if (ed_Alert_DeleteWord.getParent() != null) {
                    ((ViewGroup) ed_Alert_DeleteWord.getParent()).removeView(ed_Alert_DeleteWord);
                }
                alert3.show();

                return false;
            }
        });
        final AlertDialog.Builder alert4 = new AlertDialog.Builder(this);

        bt_MyNote_Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                //delete
                alert4.setTitle("단어장을 초기화하시겠습니까?");
                alert4.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SQLiteDatabase database = WordDB.getReadableDatabase();
                        String sql = "delete from '"+databasename+"'";
                        database.execSQL(sql);
                        WordDB.close();
                        database.close();
                        wordlist.clear();
                        listview.setAdapter(adapter1);
                        TableShow();
                    }
                });
                alert4.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alert4.setCancelable(false);
                    }
                });
                alert4.show();
            }
        });
        bt_MyNote_Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = getLayoutInflater();
                View view = inflater.inflate(R.layout.dialog, null);
                alert.setView(view);
                final EditText ed_Alert_Word = (EditText)view.findViewById(R.id.edt_dialog_Word);
                final EditText ed_Alert_Mean = (EditText)view.findViewById(R.id.edt_dialog_Mean);
                final Button bt_alert_Input = (Button)view.findViewById(R.id.btn_dialog_Input);
                final Button bt_alert_Can = (Button)view.findViewById(R.id.btn_dialog_Can);
                final AlertDialog dialog = alert.create();
                bt_alert_Input.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SQLiteDatabase database = WordDB.getWritableDatabase();
                        String inword = ed_Alert_Word.getText().toString();
                        String inmean = ed_Alert_Mean.getText().toString();
                        inword = inword.trim();
                        if(inword.equals("")){
                            Toast.makeText(getApplicationContext(), "수정할 단어와 뜻을 입력 해주세요.", Toast.LENGTH_SHORT).show();
                            dialog.cancel();
                            return;
                        }
                        String sql = "select * from '"+databasename+"' where word = '" + inword + "' ";
                        Cursor iCursor = database.rawQuery(sql,null);
                        if(iCursor!= null && iCursor.moveToFirst());
                        if(iCursor.getCount() == 0) {
                            Toast.makeText(getApplicationContext(), "해당하는 단어가 없습니다.", Toast.LENGTH_SHORT).show();
                        }else {
                            sql = "UPDATE '"+databasename+"' set mean = '"+inmean+"' where word = '" + inword + "' ";
                            database.execSQL(sql);
                        }
                        wordlist.clear();
                        TableShow();
                        WordDB.close();
                        database.close();
                        dialog.cancel();
                    }
                });
                bt_alert_Can.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });
                dialog.show();
            }
        });
    }

    @Override
    public void onBackPressed(){
        Intent homeIntent = new Intent(MyNoteBookActivity.this, MainActivity.class);
        homeIntent.putExtra("mode",mode);
        startActivity(homeIntent);
    }
}
