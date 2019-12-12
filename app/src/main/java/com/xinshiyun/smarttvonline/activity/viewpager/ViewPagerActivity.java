package com.xinshiyun.smarttvonline.activity.viewpager;

import android.animation.ValueAnimator;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;

import com.xinshiyun.smarttvonline.R;
import com.xinshiyun.smarttvonline.activity.viewpager.fragment.ArticleFragment;
import com.xinshiyun.smarttvonline.activity.viewpager.fragment.HomeFragment;
import com.xinshiyun.smarttvonline.activity.viewpager.fragment.MessageFragment;
import com.xinshiyun.smarttvonline.activity.viewpager.fragment.SettingFragment;
import com.xinshiyun.smarttvonline.widget.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;


public class ViewPagerActivity extends AppCompatActivity implements View.OnFocusChangeListener, View.OnClickListener {
    private static final String TAG = "ViewPagerActivity";
    private FragmentManager fragmentManager;
    private TabFragmentPagerAdapter adapter;

    private List<Fragment> list;
    private NoScrollViewPager viewPager;
    private RelativeLayout layoutMain;

    //    private FocusMenuView btnMessage;
    private CheckBox btnArticle, btnSetting;
    private CheckBox btnHome, btnMessage;
    private int currentId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);

        initView();
    }

    private void initView() {
        layoutMain = findViewById(R.id.rl_view_pager);
        viewPager = findViewById(R.id.viewPager);
        btnHome = findViewById(R.id.menu_home);
        btnHome.setOnClickListener(this);
        btnHome.setOnFocusChangeListener(this);
        btnMessage = findViewById(R.id.menu_message);
        btnMessage.setOnClickListener(this);
        btnMessage.setOnFocusChangeListener(this);
        btnArticle = findViewById(R.id.menu_article);
        btnArticle.setOnClickListener(this);
        btnArticle.setOnFocusChangeListener(this);
        btnSetting = findViewById(R.id.menu_setting);
        btnSetting.setOnClickListener(this);
        btnSetting.setOnFocusChangeListener(this);

        list = new ArrayList<>();
        list.add(new HomeFragment());
        list.add(new MessageFragment());
        list.add(new ArticleFragment());
        list.add(new SettingFragment());
        fragmentManager = getSupportFragmentManager();
        adapter = new TabFragmentPagerAdapter(fragmentManager, list);
        viewPager.setAdapter(adapter);

    }

    private void setAllFicus(Boolean focus) {
        btnHome.setChecked(focus);
        btnMessage.setChecked(focus);
        btnSetting.setChecked(focus);
        btnArticle.setChecked(focus);
    }


    private void doAnimation() {
        final ValueAnimator animator = ValueAnimator.ofInt(255, 100);
        //animator.setRepeatCount(ValueAnimator.INFINITE);
        //animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(10000L);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int curValue = (int) animation.getAnimatedValue();
                Drawable background = layoutMain.getBackground();
                background.setAlpha(curValue);
            }
        });
        animator.start();
    }


    @Override
    public void onClick(View v) {
        Log.d(TAG, "onClick() called with: v = [" + v + "]");
        setAllFicus(false);
        v.requestFocus();
        onFocusChange(v, true);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {

       /* int viewId;
        if(currentId == 0){
            viewId = v.getId();
        }else {
            viewId = currentId;
        }*/

        switch (v.getId()) {
            case R.id.menu_home:
                if (hasFocus) {
                    btnHome.setChecked(hasFocus);
                    viewPager.setCurrentItem(0);
                    layoutMain.setBackgroundResource(R.mipmap.bg);
                }
                break;
            case R.id.menu_message:
                if (hasFocus) {
                    btnMessage.setChecked(hasFocus);
                    viewPager.setCurrentItem(1);
                    layoutMain.setBackgroundResource(R.mipmap.bg8);
                }
                break;
            case R.id.menu_article:
                if (hasFocus) {
                    btnArticle.setChecked(hasFocus);
                    viewPager.setCurrentItem(2);
                    layoutMain.setBackgroundResource(R.mipmap.bg9);
                }
                break;
            case R.id.menu_setting:
                if (hasFocus) {
                    btnSetting.setChecked(hasFocus);
                    viewPager.setCurrentItem(3);
                    layoutMain.setBackgroundResource(R.mipmap.bg4);
                }
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + v.getId());
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        currentId = 0;
        switch (keyCode) {
            //模拟器测试时键盘中的的Enter键，模拟ok键（推荐TV开发中使用蓝叠模拟器）
            case KeyEvent.KEYCODE_ENTER:
                Log.e("key", "你按下Enter/OK键");
                break;
            case KeyEvent.KEYCODE_DPAD_CENTER:
                break;
            case KeyEvent.KEYCODE_DPAD_DOWN:
                Log.e("key", "你按下下方向键");
                if (null == getCurrentFocus()) {
                    break;
                }
                currentId = getCurrentFocus().getId();
                Log.e("key", "你按下下方向键----has focus");

                if (getCurrentFocus().getId() == R.id.menu_home) {
                    btnHome.setChecked(true);
                } else if (getCurrentFocus().getId() == R.id.menu_message) {
                    btnMessage.setChecked(true);
                } else if (getCurrentFocus().getId() == R.id.menu_article) {
                    btnArticle.setChecked(true);
                } else if (getCurrentFocus().getId() == R.id.menu_setting) {
                    btnSetting.setChecked(true);
                }
                break;

            case KeyEvent.KEYCODE_DPAD_LEFT:

                if (getCurrentFocus().getId() == R.id.menu_article) {
                    btnArticle.setChecked(false);
                } else if (getCurrentFocus().getId() == R.id.menu_message) {
                    btnMessage.setChecked(false);
                } else if (getCurrentFocus().getId() == R.id.menu_setting) {
                    btnSetting.setChecked(false);
                }
                break;

            case KeyEvent.KEYCODE_DPAD_RIGHT:

                if (getCurrentFocus().getId() == R.id.menu_home) {
                    btnHome.setChecked(false);
                } else if (getCurrentFocus().getId() == R.id.menu_message) {
                    btnMessage.setChecked(false);
                } else if (getCurrentFocus().getId() == R.id.menu_article) {
                    btnArticle.setChecked(false);
                } /*else if(getCurrentFocus().getId() == R.id.item_right){
                    Toast.makeText(this, "按下了右侧右键", Toast.LENGTH_SHORT).show();
                    break;
                }
*/
                break;

            case KeyEvent.KEYCODE_DPAD_UP:
                Log.e("key", R.id.menu_home + "-----" + R.id.menu_message + "----你按下上方向键  currentId = " + currentId);

                if (currentId == R.id.menu_home) {
                    btnHome.requestFocus();
                } else if (currentId == R.id.menu_message) {
                    btnMessage.requestFocus();
                } else if (currentId == R.id.menu_article) {
                    btnArticle.requestFocus();
                } else if (currentId == R.id.menu_setting) {
                    btnSetting.requestFocus();
                }

                break;
            case KeyEvent.KEYCODE_BACK:
                Log.e("key", "你按下了返回键");
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + keyCode);
        }


        return super.onKeyDown(keyCode, event);
    }


    public class TabFragmentPagerAdapter extends FragmentPagerAdapter {
        private FragmentManager mfragmentManager;
        private List<Fragment> mlist;

        public TabFragmentPagerAdapter(FragmentManager fm, List<Fragment> list) {
            super(fm);
            this.mlist = list;
        }

        @Override
        public Fragment getItem(int arg0) {
            return mlist.get(arg0);//显示第几个页面
        }

        @Override
        public int getCount() {
            return mlist.size();//有几个页面
        }
    }

}
