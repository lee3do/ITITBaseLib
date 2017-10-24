package io.itit.androidlibrary.utils;

import android.databinding.BindingAdapter;
import android.databinding.ObservableList;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import com.kelin.mvvmlight.command.ReplyCommand;
import com.orhanobut.logger.Logger;
import com.squareup.picasso.Picasso;

import java.io.File;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.StoreHouseHeader;
import io.itit.androidlibrary.ITITApplication;
import io.itit.androidlibrary.R;

import static io.itit.androidlibrary.utils.CommonUtil.dipToPixel;

/**
 * Created by Lee_3do on 2017/9/19.
 */

public class MyBindingAdapter {
    @BindingAdapter("layout_width")
    public static void setLayoutWidth(View view, int width) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = dipToPixel(width);
        view.setLayoutParams(layoutParams);
    }

    @BindingAdapter("background_color")
    public static void backgroundColor(View view, int color) {
      view.setBackgroundColor(color);
    }



    @BindingAdapter("layout_height")
    public static void setLayoutHeight(View view, int width) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = dipToPixel(width);
        view.setLayoutParams(layoutParams);
    }

    @BindingAdapter({"spinner_items"})
    public static void spinnerItems(Spinner spinner, final ObservableList<String> viewModelList) {
        if (viewModelList != null && !viewModelList.isEmpty()) {
            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(spinner.getContext(), R.layout.myspinner);
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            for (String name : viewModelList) {
                spinnerArrayAdapter.add(name);
            }
            spinner.setAdapter(spinnerArrayAdapter);
        }
    }

    @BindingAdapter({"after_select"})
    public static void afterSelect(Spinner spinner,final ReplyCommand<String> command) {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                command.execute(spinner.getAdapter().getItem(i).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                command.execute("");
            }
        });
    }

    @BindingAdapter("refresh_label")
    public static void setRefreshLabel(PtrClassicFrameLayout rotateHeaderWebViewFrame, String label) {
        StoreHouseHeader header = new StoreHouseHeader(ITITApplication.appContext);
        header.setPadding(0, CommonUtil.dipToPixel(15), 0, 0);
        header.initWithString(label);
        header.setTextColor(Color.rgb(20, 54, 95));
        rotateHeaderWebViewFrame.setHeaderView(header);
        rotateHeaderWebViewFrame.addPtrUIHandler(header);
        rotateHeaderWebViewFrame.disableWhenHorizontalMove(true);
    }

    @BindingAdapter("img_src")
    public static void setImageResource(ImageView imageView, int resource){
        imageView.setImageResource(resource);
    }

    @BindingAdapter("animatedVisibility")
    public static void setVisibility(final ImageView view, final boolean playAnimation) {
        try {
            AnimationDrawable animation = (AnimationDrawable) view.getDrawable();
            if (playAnimation) {
                animation.start();
            } else {
                animation.stop();
            }
        }catch (Exception e){
          //  Logger.e(e,"显示语音");
        }
    }

    @BindingAdapter({"longClick"})
    public static void longClick(View view, final ReplyCommand clickCommand) {
        view.setOnLongClickListener(view1 -> {
            clickCommand.execute();
            return false;
        });
    }

    @BindingAdapter("after_refresh")
    public static void afterRefresh(PtrClassicFrameLayout rotateHeaderWebViewFrame,final ReplyCommand command) {
        rotateHeaderWebViewFrame.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                boolean canRefresh = PtrDefaultHandler.checkContentCanBePulledDown(frame,
                        content, header);
                return canRefresh;
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                if (command != null) {
                    command.execute(frame);
                }
            }
        });
    }

    @BindingAdapter({"local_uri"})
    public static void setImageUri(ImageView imageView, String path) {
        if (!TextUtils.isEmpty(path)) {
            try {
                File file = new File(path);
                if (file.exists()) {
                    Picasso.with(imageView.getContext())//
                            .load(Uri.fromFile(new File(path)))//
//                        .resize(dipToPixel(width),dipToPixel(height))
                            .into(imageView);
                }else {
                    Picasso.with(imageView.getContext())//
                            .load(path)//
                            .into(imageView);
                }

            }catch (Exception e){
                Logger.e(e,"显示图片");
            }

        }
    }
}
