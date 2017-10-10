package io.itit.androidlibrary.ui;

import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.hwangjr.rxbus.thread.EventThread;

import io.itit.androidlibrary.Consts;
import io.itit.androidlibrary.ITITApplication;
import me.yokeyword.fragmentation_swipeback.SwipeBackActivity;

/**
 * Created by Lee_3do on 2017/9/24.
 */

public class BaseActivity extends SwipeBackActivity {
    public MaterialDialog loadingDialog;

    @Override
    protected void onResume() {
        super.onResume();
        ITITApplication.currActivity = this;
        RxBus.get().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        RxBus.get().unregister(this);
    }

    @Subscribe(thread = EventThread.MAIN_THREAD, tags = {@Tag(Consts.BusAction.TOAST)})
    public void toast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}
