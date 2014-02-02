package app.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import app.donation.R;

public class Splash extends Activity {
    
    private boolean             mIsBackButtonPressed;
    private static final int    SPLASH_DURATION = 2000; // 2 seconds
 
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
 
        Handler handler = new Handler();
        // run a thread after 2 seconds to start the home screen
        handler.postDelayed(new Runnable() {
 
            @Override
            public void run() {
                finish();
                if (!mIsBackButtonPressed) {
                    Intent intent = new Intent(Splash.this, Welcome.class);
                    Splash.this.startActivity(intent);
               }       
            }
        }, SPLASH_DURATION);
    }
 
    @Override
   public void onBackPressed() {
        mIsBackButtonPressed = true;
        super.onBackPressed();
    }
}
