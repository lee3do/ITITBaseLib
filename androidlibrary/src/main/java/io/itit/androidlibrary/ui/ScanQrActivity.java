package io.itit.androidlibrary.ui;

import android.os.Bundle;
import android.view.View;

import com.hwangjr.rxbus.RxBus;

import androidx.appcompat.app.AppCompatActivity;
import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.ZXingView;
import io.itit.androidlibrary.Consts;
import io.itit.androidlibrary.R;
import io.itit.androidlibrary.utils.BeepManager;

public class ScanQrActivity extends AppCompatActivity {
    ZXingView zXingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_qr);
        zXingView = findViewById(R.id.zxingview);
        View back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        zXingView.setDelegate(new QRCodeView.Delegate() {
            @Override
            public void onScanQRCodeSuccess(String result) {
                RxBus.get().post(Consts.BusAction.SCAN_SUCCESS, result);
                new BeepManager(ScanQrActivity.this).playBeepSoundAndVibrate();
                finish();
            }

            @Override
            public void onScanQRCodeOpenCameraError() {

            }
        });
        zXingView.startSpotDelay(300);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        zXingView.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
        zXingView.stopCamera();
    }


}
