package com.tarun.photoshare;

/**
 * Created by tarun on 17-08-2017.
 */

import android.app.Application;

import com.parse.*;


public class ParseAWSSetup extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        // Add your initialization code here
	  // Add your aws server details here
        Parse.initialize(new Parse.Configuration.Builder(getApplicationContext())
                .applicationId("")
                .clientKey("")
                .server("")
                .build()
        );

        //ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();
        defaultACL.setPublicReadAccess(true);
        defaultACL.setPublicWriteAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);

    }
}
