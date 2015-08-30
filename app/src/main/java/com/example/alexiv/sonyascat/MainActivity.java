package com.example.alexiv.sonyascat;


import android.content.Intent;

import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

    int[] myPics = {R.drawable.sonyatest, R.drawable.sonyatest1,
            R.drawable.sonyatest2, R.drawable.sonyatest3};

    private final int previewSize = 320;
    private ImageConverter myConverter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myConverter = new ImageConverter(getWindowManager());

        Typeface type = Typeface.createFromAsset(getAssets(), "fonts/CaviarDreams.ttf");
        TextView myTextView = (TextView) findViewById(R.id.mainTextBox);
        myTextView.setTypeface(type);

        addImagesToScroll();


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    private void addImagesToScroll()
    {
        LinearLayout imageGal = (LinearLayout) findViewById(R.id.imageGallery);
        for (int id : myPics)
            imageGal.addView(getImView(id));
    }

    private View getImView(int id)
    {
        ImageView imageView = new ImageView(getApplicationContext());
        LinearLayout.LayoutParams lp
                = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        lp.setMargins(0, 0, 20, 0);
        imageView.setLayoutParams(lp);
        imageView.setImageBitmap(myConverter.decodeSampledBitmapFromResource(getResources(), id,
                previewSize, previewSize));
        imageView.setTag(id);
        imageView.setAdjustViewBounds(true);
        imageView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startImagePickerActivity((int) v.getTag());
                    }
                }
        );
        return imageView;
    }
    private void startImagePickerActivity(int numberOfPic)
    {
        Intent myIntention = new Intent(MainActivity.this, ImagePicker.class);
        Bundle b = new Bundle();
        b.putInt("numberOfPic", numberOfPic);
        myIntention.putExtras(b);
        finish();
        startActivity(myIntention);

    }

}
