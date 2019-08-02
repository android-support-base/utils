package com.amlzq.asb.security;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.amlzq.asb.R;

public class SecurityActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security);
    }

    public void onMessageDigest(View view) {
    }
}
