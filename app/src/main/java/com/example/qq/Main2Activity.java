package com.example.qq;

import android.content.ContentValues;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;


public class Main2Activity extends AppCompatActivity implements View.OnClickListener {

    private EditText mEtnumber;
    private EditText mEtpassword;
    private Button mAdd;
    MyHelper myHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        myHelper = new MyHelper(this);
        mEtnumber = (EditText) findViewById(R.id.et_number);
        mEtpassword = (EditText) findViewById(R.id.et_password);
        mAdd = (Button) findViewById(R.id.btn_zhuce);
        mAdd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_zhuce:
                String number;
                String password;
                SQLiteDatabase db;
                ContentValues values;
                number = mEtnumber.getText().toString();
                password = mEtpassword.getText().toString();
                db = myHelper.getWritableDatabase();
                Cursor cursor = db.query("information",null,"number='"+number+"'",null,null,null,null);

                cursor.moveToFirst();
                if (cursor.getCount() > 0) {
                    Toast.makeText(this,"用户已存在",Toast.LENGTH_SHORT).show();
                    finish();
                }else {
                    values = new ContentValues();
                    values.put("number", number);
                    values.put("password", password);
                    db.insert("information", null, values);
                    Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
                    db.close();
                    finish();
                }
        }
    }

    class MyHelper extends SQLiteOpenHelper {
        public MyHelper(Context context) {
            super(context,"qq.db",null,1);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("create table information(number varchar(20), password varchar(20))");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }

}
