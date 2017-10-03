package io.itit.androidlibrary.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.hwangjr.rxbus.thread.EventThread;

import io.itit.androidlibrary.Consts;
import io.itit.androidlibrary.ITITApplication;
import io.itit.androidlibrary.R;
import me.yokeyword.fragmentation_swipeback.SwipeBackFragment;

/**
 */
public class BaseBackFragment extends SwipeBackFragment {
    private static final String TAG = "Fragmentation";
    public MaterialDialog loadingDialog;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setParallaxOffset(0.5f);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RxBus.get().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister(this);
    }

    protected void initToolbarNav(Toolbar toolbar) {
        toolbar.setNavigationIcon(R.drawable.backicon);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _mActivity.onBackPressed();
            }
        });
    }


    @Subscribe(thread = EventThread.MAIN_THREAD, tags = {@Tag(Consts.BusAction.SHOW_LOADING)})
    public void showLoading(Boolean show) {
        if (show) {
            loadingDialog = new MaterialDialog.Builder(ITITApplication.currActivity)
                    .progress(true, 0)
                    .content("请稍候")
                    .progressIndeterminateStyle(false)
                    .show();
        } else {
            if (loadingDialog!=null) {
                loadingDialog.dismiss();
            }
        }
    }

}
