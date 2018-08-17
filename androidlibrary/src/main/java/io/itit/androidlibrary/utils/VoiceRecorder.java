package io.itit.androidlibrary.utils;

import android.media.MediaRecorder;

import com.hwangjr.rxbus.RxBus;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.util.Date;
import java.util.UUID;

import io.itit.androidlibrary.Consts;


public class VoiceRecorder {

    /**
     * Recorder time define, unit 1s
     */
    public static final int RECORDER_MAX_TIME = 250;
    public static final int RECORDER_WARNING_TIME = 200;
    public static int UPDATE_GAP_TIME = 100;
    public static int RECORD_STATUS_RECORD_STOP = 705;
    public static int RECORD_STATUS_RECORDING = 706;
    public static int DISMISS_RECORDER = 707;
    public static int MSG_UPDATE_RECORD_DURATION = 704;
    private static VoiceRecorder mVoiceRecorder = new VoiceRecorder();
    public MediaRecorder mRecorder;
    public int mRecordStatus;
    private File mVoiceFile;
    private long mStartTime;
    private int mRecordTime;

    public static VoiceRecorder getInstance() {
        return mVoiceRecorder;
    }

    /**
     * 开始录音
     */
    public void startRecording(int maxTime) {
        if (mRecordStatus == RECORD_STATUS_RECORDING) {
            discardRecording();
        }
        mRecordStatus = RECORD_STATUS_RECORDING;
        String fileName = UUID.randomUUID().toString();

        try {
            mVoiceFile = File.createTempFile(fileName, ".amr", AppUtils.getRecPath());
            mRecorder = new MediaRecorder();
            mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mRecorder.setOutputFormat(MediaRecorder.OutputFormat.RAW_AMR);
            mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            // TODO check the rate
            // mRecorder.setAudioSamplingRate(44100);
            mRecorder.setOutputFile(mVoiceFile.getAbsolutePath());
            if (maxTime > 0) {
                mRecorder.setMaxDuration(maxTime * 1000);
            }
            mRecorder.setOnInfoListener((mr, what, extra) -> {
                if (what==MediaRecorder.MEDIA_RECORDER_INFO_MAX_DURATION_REACHED) {
                    Logger.d("已经达到最长录制时间");
                    if (mRecorder!=null) {
                        mRecorder.stop();
                        mRecorder.release();
                        mRecorder=null;
                        RxBus.get().post(Consts.BusAction.MAX_RECORDED,this.getVoiceFilePath());
                    }
                }
            });



            mRecorder.prepare();

            mStartTime = new Date().getTime();

            mRecorder.start();
        } catch (Exception e) {
            Logger.e("录音错误:" + e.getLocalizedMessage());
        }
        RxBus.get().post(Consts.BusAction.START_RECORD,mVoiceFile.getAbsolutePath());

    }

    public void discardRecording() {
        if (mRecorder == null || !isRecording()) {
            return;
        }
        mRecordStatus = RECORD_STATUS_RECORD_STOP;
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;
        if (mVoiceFile != null) {
            mVoiceFile.delete();
        }
        RxBus.get().post(Consts.BusAction.DISCARD_RECORD,mVoiceFile.getAbsolutePath());
    }

    /**
     * 结束录音
     *
     * @return 录音时长
     */
    public String stopRecoding() {
        if (mRecorder == null || !isRecording()) {
            return "";
        }
        mRecordTime = (int) ((new Date().getTime() - mStartTime) / 1000);
        mRecordStatus = RECORD_STATUS_RECORD_STOP;
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;
        RxBus.get().post(Consts.BusAction.STOP_RECORD,mVoiceFile.getAbsolutePath());
        return mVoiceFile.getAbsolutePath();
    }

    public String getVoiceFilePath() {
        // TODO Auto-generated method stub
        return mVoiceFile.getPath();
    }

    public String getVoiceFileName() {
        return mVoiceFile.getName();
    }

    public boolean isRecording() {
        // TODO Auto-generated method stub
        return mRecordStatus == RECORD_STATUS_RECORDING;
    }


    public int getRecordTime() {
        mRecordTime = (int) ((new Date().getTime() - mStartTime) / 1000);
        return mRecordTime;
    }


}
