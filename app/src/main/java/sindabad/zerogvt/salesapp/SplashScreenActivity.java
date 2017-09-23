package sindabad.zerogvt.salesapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;


@ContentView(R.layout.activity_splash_screen)
public class SplashScreenActivity extends RoboActivity {
    private int SPLASH_DISPLAY_LENGTH=3000;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setActvityLifetime(getIntent().getExtras());
    }

    private void setActvityLifetime(final Bundle bundle)
    {
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent = new Intent(SplashScreenActivity.this,DashBoardActivity.class);
                if (bundle != null) {
                    mainIntent.putExtras(bundle);
                }

                SplashScreenActivity.this.startActivity(mainIntent);
                SplashScreenActivity.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }


}
