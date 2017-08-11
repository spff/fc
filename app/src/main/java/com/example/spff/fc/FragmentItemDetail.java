package com.example.spff.fc;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentItemDetail extends Fragment {

    public String text;
    private EditText editText;
    private ImageView imageView;

    private Uri cropURI;
    public void setCropURI(Uri cropURI){
        this.cropURI =cropURI;
    }

    Intent takePictureIntent;
    static final int REQUEST_IMAGE_CAPTURE = 1;

    public static final FragmentItemDetail newInstance(Object cropURI, String text) {
        FragmentItemDetail fragment = new FragmentItemDetail();

        final Bundle args = new Bundle(1);
        if(cropURI instanceof Uri) {
            args.putParcelable("cropURI", (Uri) cropURI);
        }else {
            args.putParcelable("cropURI", null);
        }
        args.putString("text", text);
        fragment.setArguments(args);

        return fragment;
    }

    public FragmentItemDetail() {
        // Required empty public constructor
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        cropURI = bundle.getParcelable("cropURI");
        text = bundle.getString("text");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_item_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((MainActivity)getActivity()).removePhotoToDelete();

        editText = (EditText) view.findViewById(R.id.edit_text);
        editText.setText(text);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ((MainActivity) getActivity()).updateFragment1List(editText.getText().toString());
                text = editText.getText().toString();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        imageView = (ImageView) view.findViewById(R.id.edit_img);



        if(cropURI != null && new File((cropURI).getPath()).exists()) {
            imageView.setImageURI(cropURI);
        }else {
            imageView.setImageResource(R.mipmap.ic_launcher);
        }

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestandTakePicture();
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == REQUEST_IMAGE_CAPTURE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Now user should be able to use camera
                dispatchTakePictureIntent();
            } else {
                // Your app will not have this permission. Turn off all functions
                // that require this permission
            }
        }
    }


    @Override
    public void onResume(){
        super.onResume();
        //OnResume Fragment
        editText.setText(text);//if someone update the value of the string text, the view of the editText
                                // won't be refresh via onCreateView, so I put it here.
    }

    private void requestandTakePicture() {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(new String[]{Manifest.permission.CAMERA},
                    REQUEST_IMAGE_CAPTURE);
        } else {
            dispatchTakePictureIntent();
        }

    }


    static final int REQUEST_TAKE_PHOTO = 1;
    public Uri photoURI;

    private void dispatchTakePictureIntent() {
        takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getContext().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                photoURI = FileProvider.getUriForFile(getContext(),
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);

            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_TAKE_PHOTO){
            if(resultCode == RESULT_OK){
                ((MainActivity) getActivity()).cropPhoto(photoURI);
            }
        }
    }


    String mCurrentPhotoPath;

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", java.util.Locale.getDefault())
                .format(new GregorianCalendar().getTime());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        ((MainActivity)getActivity()).addPhotoToDelete(mCurrentPhotoPath);

        return image;
    }


}