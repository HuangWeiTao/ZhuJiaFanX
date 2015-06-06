package zhujiafanx.demo;

import android.app.Activity;
import android.os.Bundle;

import java.io.IOException;
import java.io.InputStream;


public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        InputStream stream = null;
        try {
            stream = getAssets().open("preloader.gif");
        } catch (IOException e) {
            e.printStackTrace();
        }

        GifMovieView view = new GifMovieView(this, stream);
//        GifDecoderView view = new GifDecoderView(this, stream);
        //GifWebView view = new GifWebView(this, "file:///android_asset/piggy.gif");

        view.setMinimumHeight(48);
        view.setMinimumWidth(48);
        setContentView(view);
    }
}
