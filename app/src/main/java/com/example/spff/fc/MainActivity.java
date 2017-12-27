package com.example.spff.fc;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private FragmentManager manager;

    public Fragment1 fragment1;
    public Fragment2 fragment2;
    public Fragment3 fragment3;
    public FragmentItemDetail fragmentItemDetail;
    public CropFragment cropFragment;

    private ArrayList<String> photoToDelete = new ArrayList<>();
    //^^^no need to store to SQL bcuz the time transaction between cropFragment
    // to fragmentItemDetail.OnViewCreated is short, and the same error might
    //also occur bcuz those action before writing stuffs into SQL is not atomic.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        manager = getSupportFragmentManager();
        fragment1 = Fragment1.newInstance();
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

    public void editFragment1List(int position, String string, Object cropURI) {
        fragment1EditPosition = position;

        fragmentItemDetail = FragmentItemDetail.newInstance(cropURI, string);

        manager.beginTransaction()
                .replace(R.id.center, fragmentItemDetail, "fragmentItemDetail").addToBackStack("fragmentItemDetail")
                .commit();

    }

    public void cropPhoto(Uri photoURI) {

        cropFragment = CropFragment.newInstance(photoURI);
        manager.beginTransaction()
                .replace(R.id.center, cropFragment, "cropFragment").addToBackStack("cropFragment")
                .commit();

    }

    public void updateFragment1List(String string) {
        fragment1.updateList(fragment1EditPosition, string);
    }

    public void updateFragment1List(Uri uri, String toPut) {//toPut should be "thumbnailURI" or "cropURI"
        fragment1.updateList(fragment1EditPosition, uri, toPut);
    }

    public void updateFragmentItemDetailURI(Uri cropURI){
        fragmentItemDetail.setCropURI(cropURI);
    }
    

    public void addPhotoToDelete(String s){
        photoToDelete.add(s);
    }
    public void removePhotoToDelete(){
        while (!photoToDelete.isEmpty()){
            new File(photoToDelete.get(0)).delete();
            photoToDelete.remove(0);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        return fragment3.onKeyDown(keyCode, event);
    }


}