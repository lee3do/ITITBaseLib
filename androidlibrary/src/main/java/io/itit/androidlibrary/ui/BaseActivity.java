package io.itit.androidlibrary.ui;

import io.itit.androidlibrary.ITITApplication;
import me.yokeyword.fragmentation_swipeback.SwipeBackActivity;

/**
 * Created by Lee_3do on 2017/9/24.
 */

public class BaseActivity extends SwipeBackActivity {
    @Override
    protected void onResume() {
        super.onResume();
        ITITApplication.currActivity = this;
    }
}
