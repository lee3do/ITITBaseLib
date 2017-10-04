package io.itit.androidlibrary.utils;

import android.databinding.BindingAdapter;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.kelin.mvvmlight.command.ReplyCommand;
import com.orhanobut.logger.Logger;
import com.squareup.picasso.Picasso;

import java.io.File;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.header.StoreHouseHeader;

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

    @BindingAdapter("refresh_label")
    public static void setRefreshLabel(PtrClassicFrameLayout rotateHeaderWebViewFrame, String label) {
        StoreHouseHeader header = new StoreHouseHeader(rotateHeaderWebViewFrame.getContext());
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
