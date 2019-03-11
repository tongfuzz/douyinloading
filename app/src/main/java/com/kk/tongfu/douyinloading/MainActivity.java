package com.kk.tongfu.douyinloading;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.kk.tongfu.douyinloading.view.LoadingView;

public class MainActivity extends AppCompatActivity {

    private LoadingView mLoadingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLoadingView=findViewById(R.id.loadingView);
        mLoadingView.setTimePeriod(100);
    }
}
