package com.tarun.photoshare;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class UserPhotos extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_photos);

        Intent intent = getIntent();
        String currUser = intent.getStringExtra("username");
        setTitle(currUser+"'s Photos");

        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Image");
        query.whereEqualTo("username",currUser);
        query.orderByDescending("createdAt");

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(e==null){
                    if(objects.size()>0){

                        final LinearLayout layout = (LinearLayout) findViewById(R.id.userPhotos);
                        for(ParseObject object : objects){

                            ParseFile file = (ParseFile) object.get("image");
                            file.getDataInBackground(new GetDataCallback() {
                                @Override
                                public void done(byte[] data, ParseException e) {
                                    if(e==null && data!=null){
                                        Bitmap image = BitmapFactory.decodeByteArray(data,0,data.length);
                                        ImageView photos = new ImageView(getApplicationContext());
                                        photos.setLayoutParams(new ViewGroup.LayoutParams(
                                                ViewGroup.LayoutParams.MATCH_PARENT,
                                                ViewGroup.LayoutParams.WRAP_CONTENT
                                        ));
                                        photos.setPadding(10,10,10,10);
                                        photos.setImageBitmap(image);
                                        layout.addView(photos);
                                    }
                                    else{
                                        e.printStackTrace();
                                    }
                                }
                            });

                        }


                    }
                }
                else{
                    Toast.makeText(UserPhotos.this, "Currently no photos", Toast.LENGTH_LONG).show();
                }

            }
        });




    }
}
