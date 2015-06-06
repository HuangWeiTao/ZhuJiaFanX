package zhujiafanx.demo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Movie;
import android.os.SystemClock;
import android.view.View;

import java.io.InputStream;

/**
 * Created by Administrator on 2015/6/3.
 */
//public class GifMovieView extends View {
//
//    private Movie mMovie;
//
//    private long mMoviestart;
//
//    public GifMovieView(Context context, InputStream stream) {
//        super(context);
//
//        mMovie = Movie.decodeStream(stream);
//    }
//
//    @Override
//    protected void onDraw(Canvas canvas) {
//        canvas.drawColor(Color.TRANSPARENT);
//        super.onDraw(canvas);
//        final long now = SystemClock.uptimeMillis();
//       {
//            mMoviestart = now;
//        }
//
//        final int relTime = (int)((now - mMoviestart) % mMovie.duration());
//        mMovie.setTime(relTime);
//        mMovie.draw(canvas, 10, 10);
//        this.invalidate();
//    }
//
//
//}

public class GifMovieView extends View {

    private Movie mMovie;

    private long mMoviestart;

    public GifMovieView(Context context, InputStream stream) {
        super(context);

        mMovie = Movie.decodeStream(stream);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.TRANSPARENT);
        canvas.scale(3,3);
        super.onDraw(canvas);
        final long now = SystemClock.uptimeMillis();

        if (mMoviestart == 0) {
            mMoviestart = now;
        }

        final int relTime = (int)((now - mMoviestart) % mMovie.duration());
        mMovie.setTime(relTime);
        mMovie.draw(canvas, 20, 20);
        this.invalidate();
    }
}

