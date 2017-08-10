package com.example.spff.fc;


import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;


/**
 * A simple {@link Fragment} subclass.
 */
public final class CropFragment extends Fragment {

    public Uri photoURI;
    CropImageView cropImageView;

    public CropFragment() {
        // Required empty public constructor
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_crop, container, false);

        cropImageView = (CropImageView) view.findViewById(R.id.cropImageView);


        cropImageView.setImageUriAsync(photoURI);

        cropImageView.setOnCropImageCompleteListener(new CropImageView.OnCropImageCompleteListener() {
            @Override
            public void onCropImageComplete(CropImageView view, CropImageView.CropResult result) {
                handleCropResult(result);
            }
        });
        cropImageView.getCroppedImageAsync();

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.main_action_crop) {
            cropImageView.getCroppedImageAsync();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    private void handleCropResult(CropImageView.CropResult result) {
        if (result.getError() == null) {

            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            if (photoFile != null) {
                FileOutputStream fout;
                try {
                    fout = new FileOutputStream(photoFile);
                    result.getBitmap().compress(Bitmap.CompressFormat.JPEG, 100, fout);
                    fout.flush();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                ((MainActivity) getActivity()).updateFragmentItemDetailURI(Uri.fromFile(photoFile));
                ((MainActivity) getActivity()).updateFragment1List(Uri.fromFile(photoFile), "cropURI");

            }

            File thumbnailFile = null;
            try {
                thumbnailFile = createImageFile("thumbnail");
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            if (thumbnailFile != null) {
                FileOutputStream fout;
                try {
                    fout = new FileOutputStream(thumbnailFile);
                    ThumbnailUtils.extractThumbnail(result.getBitmap(), 96, 96).compress(Bitmap.CompressFormat.JPEG, 100, fout);
                    fout.flush();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                ((MainActivity) getActivity()).updateFragment1List(Uri.fromFile(thumbnailFile), "thumbnailURI");
            }

            getActivity().getSupportFragmentManager().popBackStack();

        } else {
            Log.e("AIC", "Failed to crop image", result.getError());
            Toast.makeText(getActivity(), "Image crop failed: " + result.getError().getMessage(), Toast.LENGTH_LONG).show();
        }
    }


    String mCurrentPhotoPath;
        private File createImageFile() throws IOException {
            return createImageFile("");
        }

        private File createImageFile(String postfix) throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", java.util.Locale.getDefault())
                .format(new GregorianCalendar().getTime());
        String imageFileName = "JPEG_" + timeStamp + "_" + postfix;
        File storageDir = getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }
}
