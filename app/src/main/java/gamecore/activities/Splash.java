package gamecore.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import gamecore.R;

public class Splash extends Activity {

    ImageView image;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.splashscreen);
        image = (ImageView) findViewById(R.id.splashImage);
        image.setScaleType(ImageView.ScaleType.FIT_XY);
        // Duration of wait
        int SPLASH_DISPLAY_LENGTH = 4000;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainIntent = new Intent(Splash.this, GameCatalog.class);
                Splash.this.startActivity(mainIntent);
                Splash.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
