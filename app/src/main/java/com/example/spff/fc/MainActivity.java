package com.example.spff.fc;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private FragmentManager manager;
    private FragmentTransaction transaction;

    public Fragment1 fragment1;
    public Fragment2 fragment2;
    public Fragment3 fragment3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        fragment1 = new Fragment1();
        fragment2 = new Fragment2();
        fragment3 = new Fragment3();

        transaction.replace(R.id.center, fragment1, "fragment1");
        transaction.commit();

    }

    public void switchFragment(int destFrag) {
//透過下方程式碼，取得Activity中執行的個體。
        transaction = manager.beginTransaction();

        switch (destFrag) {
            case 1:
                transaction.replace(R.id.center, fragment1, "fragment1");

                break;
            case 2:
                transaction.replace(R.id.center, fragment2, "fragment2");
                break;
            case 3:
                transaction.replace(R.id.center, fragment3, "fragment3");

                break;

        }
//呼叫commit讓變更生效。
        transaction.commit();
    }

    private int fragment1EditPosition;
    public void editFragment1List(int position, String string){
        fragment1EditPosition = position;
        transaction = manager.beginTransaction();
        Fragment11 fragment11 = new Fragment11();
        fragment11.text = string;
        transaction.replace(R.id.center, fragment11, "fragment11").addToBackStack("fragment11");
        transaction.commit();
    }

    public void updateFragment1List(String string){
        fragment1.updateList(fragment1EditPosition, string);
    }

}