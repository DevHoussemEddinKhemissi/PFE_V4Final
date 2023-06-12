package com.tevah.pfe_v4final.Adapters;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import com.squareup.picasso.Transformation;
public class RoundedCornersTransformation implements Transformation {

    private final int radius;
    private final int margin;

    public RoundedCornersTransformation(int radius, int margin) {
        this.radius = radius;
        this.margin = margin;
    }

    @Override
    public Bitmap transform(Bitmap source) {
        int width = source.getWidth();
        int height = source.getHeight();

        Bitmap output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(0xFFFFFFFF);
        canvas.drawARGB(0, 0, 0, 0);
        canvas.drawRoundRect(
                margin,
                margin,
                width - margin,
                height - margin,
                radius,
                radius,
                paint
        );

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(source, 0, 0, paint);

        source.recycle();

        return output;
    }

    @Override
    public String key() {
        return "rounded_corners_transformation";
    }
}
