package com.example.wurkout.Gallery;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.lang.reflect.Type;
import java.util.Date;

import java.text.SimpleDateFormat;

import androidx.core.content.FileProvider;

import com.example.wurkout.Custom_RecyclerView.RV_Fragment;
import com.example.wurkout.Custom_RecyclerView.RecyclerView_Adapter;
import com.example.wurkout.Custom_RecyclerView.RecyclerView_Items;

import com.example.wurkout.R;

import com.google.gson.Gson;

import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

public class Gallery_Main extends RV_Fragment {

    /*
    Most of below follows android documentaion for taking and saving images from camera and gallery
    Displaying the images is done through a custom gallery adapter - no longer implemented but can be found
    here: https://www.androidauthority.com/how-to-build-an-image-gallery-app-718976/
    This was taken out because displaying images dynamically takes up alot of memory and optimizing it
    wasn't straightforward.
    Creating a list of images ended up being more straightforward and just as useful
    For future reference, refer to any sort of data on the phone by its URI, not by trying to find a path
    URI "unambiguously identifies a particular resource" :)
     */

    private ImageView zAddItem;

    private String mCurrentPhotoPath;
    private String imageData;

    private int imagePosition;
    
    private int CAMERA = 1, GALLERY = 2;

    private Context context;

    public static Gallery_Main newInstance() {

        return new Gallery_Main();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = super.onCreateView(inflater, container, savedInstanceState);
        context = view.getContext();
        checkData();
        super.buildRecyclerView(view);
        zAddItem = view.findViewById(R.id.imageView3);
        OnItemClickListener();

        return view;
    }

    // figure out how to tie image_src and zList because when zlist changes image_src doesn't
    public void OnItemClickListener() {

        zListAdapter.OnItemClickListener(new RecyclerView_Adapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

                // item doesn't have a picture yet
                if (zList.get(position).getImage().equals("")) {
                    imagePosition = position;

                    showPictureDialog();
                }
                else {
                    String src = zList.get(position).getImage();
                    Intent intent = new Intent(context, ImageFullScreen.class);
                    intent.putExtra("img", src);
                    context.startActivity(intent);
                }
            }


            @Override
            public void onDeleteClick(int position) {
                removeItem(position);
            }

        });

        zAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getNameAddItem(v);
            }
        });

    }

    // Now just need to save data properly
    public void onPause() {
        super.onPause();

        String list = new Gson().toJson(zList);

        saveData("Images", "Images.txt", list);

    }

    public void checkData() {
        String data = super.checkData("Images", "Images.txt");
        if (!data.equals("[]")) {
            Gson gson = new Gson();
            Type typeToken = new TypeToken<ArrayList<RecyclerView_Items>>() {
            }.getType();
            zList = gson.fromJson(data, typeToken);
        }
    }

    private void showPictureDialog() {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(context);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {"Select photo from gallery", "Capture photo from camera"};
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallary();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    public void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, GALLERY);
    }

    private void takePhotoFromCamera() {
        // create file, take photo
        dispatchTakePictureIntent();

        // add to phone gallery - onActivityResult saves to app gallery
        galleryAddPic();

    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(context.getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(context,
                        "com.example.wurkout.Gallery",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, CAMERA);
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        imageData = contentUri.toString();
        context.sendBroadcast(mediaScanIntent);
    }

    // called after startActivityForResult has been called and the photo has been selected or captured
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.getActivity().RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();

                imageData = contentURI.toString();

                zList.get(imagePosition).setImage(imageData);

                setupImage(imagePosition);

            }

        } else if (requestCode == CAMERA) {

            zList.get(imagePosition).setImage(imageData);

            setupImage(imagePosition);

            Toast.makeText(context, "Image Saved!", Toast.LENGTH_SHORT).show();
        }

    }

    public void getNameAddItem(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        builder.setTitle("Image Name");
        // Set up the input - interesting creation of EditText without file_paths
        final EditText input = new EditText(v.getContext());
        // Specify the type of input expected
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String itemName = input.getText().toString();
                addItem(itemName);

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();

            }
        });

        builder.show();
    }

    public void addItem(String userInput) {
        RecyclerView_Items item = new RecyclerView_Items(R.drawable.ic_collections_black_24dp,
                R.drawable.ic_remove, userInput, "");
        item.setIdentifier(zList.size());
        item.setImage("");
        zList.add(item);
        zListAdapter.notifyDataSetChanged();
    }

    public void setupImage(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Enter description for image");
        LayoutInflater inflater = this.getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.image_description, null));
        String date = new SimpleDateFormat("dd-MM-yyyy", Locale.US).format(new Date());

        builder.setMessage(date);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EditText description = ((AlertDialog) dialog).findViewById(R.id.imageDescription);
                if (description.getText() != null) {
                    zList.get(position).changeText2(description.getText().toString());
                    zListAdapter.notifyDataSetChanged();

                }

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();

            }
        });

        builder.show();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

}