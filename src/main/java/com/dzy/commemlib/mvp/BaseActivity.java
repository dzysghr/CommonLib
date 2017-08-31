package com.dzy.commemlib.mvp;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by dengziyue on 2017/8/31.
 */

public abstract class BaseActivity extends Activity {
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
    }
    
    abstract void initData(@Nullable Bundle savedInstanceState);
    
    
}
