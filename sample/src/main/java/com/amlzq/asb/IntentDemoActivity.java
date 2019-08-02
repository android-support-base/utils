package com.amlzq.asb;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

/**
 * 意图样例
 */
public class IntentDemoActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent_demo);
    }

    public void onImagePicker(View view) {
    }

}
