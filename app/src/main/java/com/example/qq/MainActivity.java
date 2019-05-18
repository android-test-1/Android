package com.example.qq;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    MyHelper myHelper;
    private Button myBtn_one;
    private Button myBtn_one2;
    private Button myBtn_one3;
    private TextView textView;
    private EditText number;
    private EditText password;


    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myHelper = new MyHelper(this);

        myBtn_one = (Button) findViewById(R.id.btn_login);
        myBtn_one2 = (Button) findViewById(R.id.btn_zhuce);
        myBtn_one3 = (Button) findViewById(R.id.btn_zhaohui);

        number = (EditText) findViewById(R.id.et_number);
        password = (EditText) findViewById(R.id.et_password);

        myBtn_one.setOnClickListener(this);
        myBtn_one2.setOnClickListener(this);
        myBtn_one3.setOnClickListener(this);

        Toast.makeText(this,"☼腾讯云提供云计算服务",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                SQLiteDatabase db = myHelper.getReadableDatabase();
                String Enumber = number.getText().toString();
                String Epassword = password.getText().toString();
                Cursor cursor = db.query("information",null,"number='"+Enumber+"'",null,null,null,null);

                cursor.moveToFirst();
                if (cursor.getCount() == 0) {
                    Toast.makeText(this,"用户不存在",Toast.LENGTH_SHORT).show();
                }else{
                        String num = cursor.getString(1);

                        if (num.equals(Epassword)){
                        Toast.makeText(this,"登录成功",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(this,Main3Activity.class);
                            startActivity(intent);
                            break;
                        }else {
                            Toast.makeText(this,"密码错误",Toast.LENGTH_SHORT).show();
                        }
                    }

                cursor.close();
                db.close();
                break;
            case R.id.btn_zhuce:
                Intent intent = new Intent(this,Main2Activity.class);
                startActivity(intent);
                break;

            case R.id.btn_zhaohui:
                Intent intent1 = new Intent(this,Main4Activity.class);
                startActivity(intent1);
                break;
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

