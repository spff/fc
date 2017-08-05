package com.example.spff.fc;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private FragmentManager manager;

    public Fragment1 fragment1;
    public Fragment2 fragment2;
    public Fragment3 fragment3;
    public FragmentItemDetail fragmentItemDetail;
    public CropFragment cropFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        manager = getSupportFragmentManager();
        fragment1 = new Fragment1();
        fragment2 = new Fragment2();
        fragment3 = new Fragment3();

        manager.beginTransaction()
                .replace(R.id.center, fragment1, "fragment1")
                .commit();

    }




    public void switchFragment(int destFrag) {

        switch (destFrag) {
            case 1:
                manager.beginTransaction()
                        .replace(R.id.center, fragment1, "fragment1")
                        .commit();
                break;
            case 2:
                manager.beginTransaction()
                        .replace(R.id.center, fragment2, "fragment2")
                        .commit();
                break;
            case 3:
                manager.beginTransaction()
                        .replace(R.id.center, fragment3, "fragment3")
                        .commit();
                break;

        }

    }

    private int fragment1EditPosition;

    public void editFragment1List(int position, String string, Uri editURI) {
        fragment1EditPosition = position;

        fragmentItemDetail = new FragmentItemDetail();
        fragmentItemDetail.text = string;
        fragmentItemDetail.editURI = editURI;
        manager.beginTransaction()
                .replace(R.id.center, fragmentItemDetail, "fragmentItemDetail").addToBackStack("fragmentItemDetail")
                .commit();

    }

    public void updateFragment1List(String string) {
        fragment1.updateList(fragment1EditPosition, string);
    }

    public void updateFragment1List(Bitmap thumbnail) {
        fragment1.updateList(fragment1EditPosition, thumbnail);
    }


    public void cropPhoto(Uri photoURI) {

        cropFragment = new CropFragment();
        cropFragment.photoURI = photoURI;
        manager.beginTransaction()
                .replace(R.id.center, cropFragment, "cropFragment").addToBackStack("cropFragment")
                .commit();

    }

    public void updateFragmentItemDetailURI(Uri editURI){
        fragmentItemDetail.editURI = editURI;
    }


}