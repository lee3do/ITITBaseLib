package io.itit.androidlibrary.ui;

import android.widget.Toast;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * 懒加载
 */
public abstract class BaseMainFragment extends SupportFragment {
    // 再点一次退出程序时间设置
    private static final long WAIT_TIME = 2000L;
    private long TOUCH_TIME = 0;

    /**
     * 处理回退事件
     *
     * @return
     */
    @Override
    public boolean onBackPressedSupport() {
        if (System.currentTimeMillis() - TOUCH_TIME < WAIT_TIME) {
            _mActivity.finish();
        } else {
            TOUCH_TIME = System.currentTimeMillis();
            Toast.makeText(_mActivity, "再按一次返回后退出", Toast.LENGTH_SHORT).show();
        }
        return true;
    }
}