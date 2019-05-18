package com.example.qq;

import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearSnapHelper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Main4Activity extends AppCompatActivity implements View.OnClickListener {

    private Button mUpdate;
    MyHelper myHelper;
    private EditText Etnumber;
    private EditText Etpassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        mUpdate = (Button) findViewById(R.id.btn_yes);
        mUpdate.setOnClickListener(this);
        myHelper = new MyHelper(this);
        Etnumber = (EditText) findViewById(R.id.et_number);
        Etpassword = (EditText) findViewById(R.id.et_password);

    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_yes:
                String password;
                String Enumber = Etnumber.getText().toString();
                SQLiteDatabase db = myHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                Cursor cursor = db.query("information",null,"number='"+Enumber+"'",null,null,null,null);

                cursor.moveToFirst();
                if (cursor.getCount() == 0) {
                    Toast.makeText(this,"用户不存在",Toast.LENGTH_SHORT).show();
                }else {
                    values.put("password", password = Etpassword.getText().toString());
                    db.update("information", values, "number=?", new String[]{Etnumber.getText().toString()});
                    db.close();
                    Toast.makeText(this, "修改成功", Toast.LENGTH_SHORT).show();
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
