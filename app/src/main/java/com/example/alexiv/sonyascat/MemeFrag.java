package com.example.alexiv.sonyascat;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;


public class MemeFrag extends Fragment {

    Bitmap originalImage;
    Bitmap updatedImage;
    String upText;
    String downText;
    private Context mContext;
    private ImageView mImage;
    private final double MAGIC_SCALE = 0.8;


    // TODO: Rename and change types and number of parameters
    public static MemeFrag newInstance() {
        MemeFrag fragment = new MemeFrag();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public MemeFrag() {
        // Required empty public constructor
    }
    public void setMyImageArgs(Context cont, Bitmap image)
    {
        mContext = cont;
        originalImage = image;
        mImage.setImageBitmap(originalImage);
    }

    public void setMyTextArgs(String up, String down)
    {
        upText = up;
        downText = down;
        drawToLayout();
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void drawToLayout() {

        Bitmap curBit = drawTextToBitmap(originalImage, upText, downText);
        if (curBit == null)
            return;
        mImage.setImageBitmap(curBit);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mView = inflater.inflate(R.layout.fragment_meme, container, false);
        mImage = (ImageView) mView.findViewById(R.id.myImage);
        return mView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
    private Bitmap drawTextToBitmap(Bitmap myPic, String upText, String downText)
    {
        Resources resources = mContext.getResources();
        float scale = resources.getDisplayMetrics().density;

        if (myPic == null)
            return null;

        android.graphics.Bitmap.Config bitmapConfig = myPic.getConfig();
        // set default bitmap config if none
        if(bitmapConfig == null) {
            bitmapConfig = android.graphics.Bitmap.Config.ARGB_8888;
        }
        // resource bitmaps are imutable,
        // so we need to convert it to mutable one
        myPic = myPic.copy(bitmapConfig, true);

        Canvas canvas = new Canvas(myPic);
        // new antialised Paint
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        // text color - #3D3D3D
        paint.setColor(Color.rgb(255, 255, 255));
        paint = setTextSuitableSize(paint, upText, downText, myPic.getWidth());
        Typeface font = Typeface.createFromAsset(getActivity().getAssets(),
                "fonts/Impact.ttf");
        paint.setTypeface(font);
        // text shadow
        paint.setShadowLayer(50f, 50f, 50f, Color.BLACK);
        DrawTextOnCanvas(scale, canvas, paint, myPic, upText, true);
        DrawTextOnCanvas(scale, canvas, paint, myPic, downText, false);

        updatedImage = myPic;
        return myPic;
    }

    private void DrawTextOnCanvas(float scale, Canvas canvas, Paint paint, Bitmap bitmap, String mText, boolean isUp) {
        // draw text to the Canvas center
        Rect bounds = new Rect();
        if (isUp) {
            paint.getTextBounds(mText, 0, mText.length(), bounds);
            int x = (bitmap.getWidth() - bounds.width()) / 2 ;
            int y = bounds.height() ;

            canvas.drawText(mText, x, y + 40, paint);
        }
        else
        {
            paint.getTextBounds(mText, 0, mText.length(), bounds);
            int x = (bitmap.getWidth() - bounds.width()) / 2 ;
            int y = bitmap.getHeight() - bounds.height();

            canvas.drawText(mText, x, y + 30, paint);
        }
    }

    private Paint setTextSuitableSize(Paint paint, String firstText, String secondText, int targetWidth)
    {
        int maxWidth;
        int curTextSize = 500;
        do {
            paint.setTextSize(curTextSize);
            Rect bounds1 = new Rect();
            paint.getTextBounds(firstText, 0, firstText.length(), bounds1);
            Rect bounds2 = new Rect();
            paint.getTextBounds(secondText, 0, secondText.length(), bounds2);

            maxWidth = Math.max(bounds1.width(), bounds2.width());
            --curTextSize;
        }
        while (maxWidth > MAGIC_SCALE * targetWidth);

        ++curTextSize;
        paint.setTextSize(curTextSize);
        return paint;

    }

    public Bitmap getUpdatedImage()
    {
        return updatedImage;
    }

}
