package me.aravind.blupespace;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.CirclePageIndicator;
import com.synnapps.carouselview.ImageListener;

import org.jitsi.meet.sdk.JitsiMeetActivity;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;

import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    CarouselView carouselView;
    int[] sampleImages = {R.drawable.rocket, R.drawable.webandapp, R.drawable.youtube, R.drawable.safeandsecure, R.drawable.opensource, R.drawable.hands, R.drawable.link};

    private FirebaseAnalytics mFirebaseAnalytics;
    private Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //statubar color code
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.parseColor("#3E50B3"));
        //end of statusbar color code

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //navigate to info page
        button = (Button) findViewById(R.id.infoButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, InfoActivity.class);
                startActivity(intent);
            }
        });


        //carousel view
        carouselView = (CarouselView) findViewById(R.id.carouselView);
        carouselView.setPageCount(sampleImages.length);
        carouselView.setImageListener(imageListener);

        //hiding the carousel stroke
        CarouselView customCarouselView = (CarouselView) findViewById(R.id.carouselView);
        CirclePageIndicator indicator = (CirclePageIndicator) customCarouselView.findViewById(R.id.indicator);
        if (indicator != null) {
            indicator.setVisibility(View.GONE);
        }

        //jitsi code
        // using try catch block to handle exceptions
        // object creation of JitsiMeetConferenceOptions
        // class by the name of options
        URL serverURL;
        try {
            // When using JaaS, replace "https://meet.jit.si" with the proper serverURL
            serverURL = new URL("https://blupe.westus.cloudapp.azure.com/");
        } catch (MalformedURLException e) {
            e.printStackTrace();
            throw new RuntimeException("Invalid server URL!");
        }

        JitsiMeetConferenceOptions options = new JitsiMeetConferenceOptions.Builder()
                .setServerURL(serverURL)
                .setWelcomePageEnabled(false)
                .setAudioMuted(true)
                .setVideoMuted(true)
                .build();
    }

    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            imageView.setImageResource(sampleImages[position]);
        }
    };

    //jitsi code

    // we have declared the name of onButtonClick() method
    // in our xml file now we are going to define it.
    public void onButtonClick(View v) {

        URL serverURL;
        try {
            // When using JaaS, replace "https://meet.jit.si" with the proper serverURL
            serverURL = new URL("https://blupe.westus.cloudapp.azure.com/");
        } catch (MalformedURLException e) {
            e.printStackTrace();
            throw new RuntimeException("Invalid server URL!");
        }

        // initialize editText with method findViewById()
        // here editText will hold the name of room which is given by user
        EditText editText = findViewById(R.id.conferenceName);

        // store the string input by user in editText in
        // an local variable named text of string type
        String text = editText.getText().toString();

        // if user has typed some text in
        // EditText then only room will create
        if (text.length() > 0) {
            // creating a room using JitsiMeetConferenceOptions class
            // here .setRoom() method will set the text in room name
            // here launch method with launch a new room to user where
            // they can invite others too.
            JitsiMeetConferenceOptions options
                    = new JitsiMeetConferenceOptions.Builder()
                    .setRoom(text)
                    .setServerURL(serverURL)
                    .setWelcomePageEnabled(false)
                    .setAudioMuted(true)
                    .setVideoMuted(true)
                    .build();
            JitsiMeetActivity.launch(this, options);
        }
    }


}