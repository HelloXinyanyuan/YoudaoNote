package com.example.youdaonote;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
/**
 * UI考试-主activity
 * @author Administrator
 *
 */
public class MainActivity extends FragmentActivity implements OnClickListener {

	private ViewPager mViewPager;
	private List<Fragment> fragments = new ArrayList<Fragment>();
	/**
	 * 给每个tab的id定一个位置，数组装
	 */
	private int[] resIds = { R.id.tab_note, R.id.tab_teamwork, R.id.tab_setting };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
	}

	private void initView() {
		mViewPager = (ViewPager) findViewById(R.id.vp);
		findViewById(R.id.tab_note).setOnClickListener(this);
		findViewById(R.id.tab_teamwork).setOnClickListener(this);
		findViewById(R.id.tab_setting).setOnClickListener(this);
		// 创建和初始化所有的fragment
		fragments.add(new NoteFragment());
		fragments.add(new TeamworkFragment());
		fragments.add(new SettingFragment());
		// 创建适配器
		MyViewPagerAdapter viewPagerAdapter = new MyViewPagerAdapter(
				getSupportFragmentManager());
		// 将适配器与ViewPager关联
		mViewPager.setAdapter(viewPagerAdapter);
		// 设置ViewPager的选中监听
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int pos) {
				doViewSelect(resIds[pos]);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});

		//默认选中第一个tab，与ViewPager中的fragment对应
		doViewSelect(resIds[0]);
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tab_note:
			doViewSelect(R.id.tab_note);
			mViewPager.setCurrentItem(0);
			break;
		case R.id.tab_teamwork:
			doViewSelect(R.id.tab_teamwork);
			mViewPager.setCurrentItem(1);
			break;
		case R.id.tab_setting:
			doViewSelect(R.id.tab_setting);
			mViewPager.setCurrentItem(2);
			break;

		default:
			break;
		}

	}

	/**
	 * 执行选中某个view
	 * 
	 * @param resId
	 */
	private void doViewSelect(int resId) {
		View findViewById = findViewById(resId);
		ViewGroup parent = (ViewGroup) findViewById.getParent();
		for (int i = 0; i < parent.getChildCount(); i++) {
			View v = parent.getChildAt(i);
			if (v == findViewById) {
				v.setSelected(true);
			} else {
				v.setSelected(false);
			}

		}

	}

	class MyViewPagerAdapter extends FragmentPagerAdapter {

		public MyViewPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int pos) {
			return fragments.get(pos);
		}

		@Override
		public int getCount() {
			return fragments.size();
		}

	}

}
