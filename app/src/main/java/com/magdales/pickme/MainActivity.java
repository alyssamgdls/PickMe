package com.magdales.pickme;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static int PICK_PHOTO_REQUEST = 1;

    ImageView viewedImg;
    TextView pickText;
    Button shareBtn;
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();
    }

    public void findViews() {
        viewedImg = (ImageView) findViewById(R.id.viewed_photo);
        pickText = (TextView) findViewById(R.id.camera_text);
        shareBtn = (Button) findViewById(R.id.share_button);
    }

    public void pickPhoto(View v) {
        Intent gallery = new Intent(Intent.ACTION_PICK);
        gallery.setType("image/*");
        startActivityForResult(gallery, PICK_PHOTO_REQUEST);
    }

    public void sharePhoto(View v) {
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("image/*");
        share.setData(uri);
        share.putExtra(Intent.EXTRA_STREAM, uri);
        startActivity(Intent.createChooser(share, getString(R.string.share_text_)));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_PHOTO_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            uri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                viewedImg.setImageBitmap(bitmap);
                viewedImg.setVisibility(View.VISIBLE);
                shareBtn.setVisibility(View.VISIBLE);
                pickText.setVisibility(View.INVISIBLE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
