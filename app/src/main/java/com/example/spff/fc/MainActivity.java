package com.example.spff.fc;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity  {

    private FragmentManager manager;
    private FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//透過下方程式碼，取得Activity中執行的個體。
        manager = getSupportFragmentManager();


    }
    public  void  switchFragment(int destFrag){
        transaction = manager.beginTransaction();

        switch (destFrag) {
            case 1:
                Fragment1 fragment1 = new Fragment1();
                transaction.replace(R.id.center, fragment1, "fragment1");

                break;
            case 2:
                Fragment2 fragment2 = new Fragment2();
                transaction.replace(R.id.center, fragment2, "fragment2");
                break;
            case 3:
                Fragment3 fragment3 = new Fragment3();
                transaction.replace(R.id.center, fragment3, "fragment3");

                break;

        }
//呼叫commit讓變更生效。
        transaction.commit();
    }


    //換頁的按鈕設定
    public void onClick(View view) {
//透過下方程式碼，取得Activity中執行的個體。
        transaction = manager.beginTransaction();
        switch (view.getId()) {
            case R.id.Button1:
                Fragment1 fragment1 = new Fragment1();
                transaction.replace(R.id.center, fragment1, "fragment1");

                break;
            case R.id.Button2:
                Fragment2 fragment2 = new Fragment2();
                transaction.replace(R.id.center, fragment2, "fragment2");
                break;

        }
//呼叫commit讓變更生效。
        transaction.commit();
    }
}