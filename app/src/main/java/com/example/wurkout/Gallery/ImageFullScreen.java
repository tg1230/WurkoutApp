package com.example.wurkout.Gallery;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wurkout.R;
import com.squareup.picasso.Picasso;

import java.io.IOException;


public class ImageFullScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_fullscreen);
        String imgSrc = getIntent().getStringExtra("img");
        getIntent().removeExtra("img");
        Uri contentUri = Uri.parse(imgSrc);

        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentUri);
            Drawable d = new BitmapDrawable(getResources(), bitmap);

            // deals with scaling issue but not rotating issue
            /*

            DisplayMetrics dm = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(dm);

            int w = MeasureSpec.getSize(d.getIntrinsicWidth());
            int h = w * d.getIntrinsicHeight() / d.getIntrinsicWidth();

            d = new ScaleDrawable(d, 0, w, h).getDrawable();

            d.setBounds(0, 0, w, h);

             */

            // deals with any scaling and rotating issue but a bit slower

            ImageView imageView = findViewById(R.id.fullscreen);
            Picasso.with(this).load(contentUri).into(imageView);

        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }


}
