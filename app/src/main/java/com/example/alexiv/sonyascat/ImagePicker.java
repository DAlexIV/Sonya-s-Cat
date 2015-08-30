package com.example.alexiv.sonyascat;

import android.content.Intent;
import android.graphics.Bitmap;

import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.ShareActionProvider;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

public class ImagePicker extends AppCompatActivity implements InputFragment.OnFragmentListener{

    InputFragment mInputFrag;
    MemeFrag mMemeFrag;

    private ShareActionProvider mShareActionProvider;
    private MenuItem shareMenu;
    private Toolbar mToolbar;
    private final int imageSize = 640;
    private ImageConverter mImageConverter;
    int resId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_picker);
        resId = getIntent().getExtras().getInt("numberOfPic");
        mImageConverter = new ImageConverter(getWindowManager());

        mInputFrag = (InputFragment) getFragmentManager().findFragmentById(R.id.fragment);
        mMemeFrag = (MemeFrag) getFragmentManager().findFragmentById(R.id.fragment2);
        mToolbar = (Toolbar) findViewById(R.id.myTools);

        mMemeFrag.setMyImageArgs(this, BitmapFactory.decodeResource(getResources(), resId));




        getSupportActionBar().setTitle(R.string.app_name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_image_picker, menu);
        shareMenu = menu.findItem(R.id.menu_item_share);
        mShareActionProvider =(ShareActionProvider) MenuItemCompat.getActionProvider(shareMenu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            finish();
            startActivity(new Intent(ImagePicker.this, MainActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setShareIntent(Intent shareIntent) {
        if (mShareActionProvider != null) {
            mShareActionProvider.setShareIntent(shareIntent);
        }
    }

    @Override
    public void onFragmentInteraction(String up, String down)
    {
        mMemeFrag.setMyTextArgs(up, down);

        Bitmap imageForShare = mMemeFrag.getUpdatedImage();
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("image/jpeg");
        mImageConverter.saveBitmapTo(imageForShare, getString(R.string.nameOfStoredImageFile));

        Uri myUri = Uri.parse("file://"
                        + Environment.getExternalStorageDirectory().getPath() +  "/"
                    + getString(R.string.nameOfStoredImageFile) + ".jpg");

        share.putExtra(Intent.EXTRA_STREAM, myUri);
        shareMenu.setEnabled(true);
        setShareIntent(share);
    }
    @Override
    public void onBackPressed() {
        finish();
        startActivity(new Intent(ImagePicker.this, MainActivity.class));
    }
}
