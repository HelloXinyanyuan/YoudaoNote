package com.example.youdaonote;

import java.util.ArrayList;
import java.util.List;

import me.maxwin.view.XListView;
import me.maxwin.view.XListView.IXListViewListener;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.PopupWindow;

import com.example.youdaonote.util.T;

public class NoteFragment extends Fragment implements OnClickListener {

	protected static final int UPDATE_NEWEST = 0;
	protected static final int UPDATE_ALL = 1;
	// 旋转动画效果的相关视图
	private View menuSyncImv;
	private View menuCancelSyncImv;
	private PopupWindow popupWindow;
	private ViewPager mViewPager;

	private Handler mHandler = new Handler() {

		public void handleMessage(Message msg) {

			switch (msg.what) {
			case UPDATE_NEWEST:
//				mNewestAdapter.clear();//
				mNewestAdapter.addAll(genData(0));
				break;
			case UPDATE_ALL:
//				mAllAdapter.clear();//
				mAllAdapter.addAll(genData(1));
				break;

			default:
				break;
			}
		};
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View inflate = inflater.inflate(R.layout.fragment_note_main, null);

		// 加载动画效果相关的控件
		menuSyncImv = inflate.findViewById(R.id.menu_sync);
		menuSyncImv.setOnClickListener(this);
		menuCancelSyncImv = inflate.findViewById(R.id.menu_cancel_sync);
		// 悬浮按钮视图
		inflate.findViewById(R.id.add_note).setOnClickListener(this);
		inflate.findViewById(R.id.add_more_note).setOnClickListener(this);
		// 更多按钮
		inflate.findViewById(R.id.menu_overflow).setOnClickListener(this);
		// 核心内容ViewPager
		mViewPager = (ViewPager) inflate.findViewById(R.id.note_vp);

		// 初始化listview视图集合
		List<View> views = new ArrayList<View>();
		views.add(genListView(0));
		views.add(genListView(1));

		// 初始化ViewPager的适配器
		MyPagerAdapter pagerAdapter = new MyPagerAdapter(views);
		// 将适配器与ViewPager关联
		mViewPager.setAdapter(pagerAdapter);
		// 设置ViewPager缓存视图的数量
		mViewPager.setOffscreenPageLimit(views.size());
		return inflate;
	}

	private ArrayAdapter<String> mNewestAdapter;
	private ArrayAdapter<String> mAllAdapter;

	private View genListView(int pos) {

		View header = null;
		if (pos == 0) {// 第一个位置的视图
			header = View.inflate(getActivity(), R.layout.fragment_note_newest,
					null);
		} else {
			header = View.inflate(getActivity(), R.layout.fragment_note_all,
					null);
		}

		// ListView view = (ListView)View.inflate(getActivity(),
		// R.layout.layout_listview, null);
		XListView view = (XListView) View.inflate(getActivity(),
				R.layout.layout_listview, null);

		final int id = pos;
		view.setXListViewListener(new IXListViewListener() {

			@Override
			public void onRefresh() {
				if (id == 0) {
					mNewestAdapter.clear();
					Message msg = new Message();
					msg.what = UPDATE_NEWEST;
					mHandler.sendMessageDelayed(msg, 2000);
				} else {
					mAllAdapter.clear();//清除以前数据
					Message msg = new Message();
					msg.what = UPDATE_ALL;
					mHandler.sendMessageDelayed(msg, 1000);
				}
			}

			@Override
			public void onLoadMore() {
			}
		});

		// 造数据
		String[] objects = genData(pos);
		// 添加头部，第一个头布局,第二个参数是头的数据一般给null，第三个是否可以点击
		view.addHeaderView(header, null, false);
		// view.addHeaderView(header);
		// 将适配器与ViewPager关联，切记：添加头部必须在此步骤之前做，不然报错

		if (pos == 0) {
			mNewestAdapter = new ArrayAdapter<String>(getActivity(),
					android.R.layout.simple_list_item_1, objects);
			view.setAdapter(mNewestAdapter);
		} else {
			mAllAdapter = new ArrayAdapter<String>(getActivity(),
					android.R.layout.simple_list_item_1, objects);
			view.setAdapter(mAllAdapter);
		}

		return view;
	}

	private String[] genData(int pos) {
		String[] objects = new String[50];
		for (int i = 0; i < objects.length; i++) {
			objects[i] = pos + "note item" + i;
		}
		return objects;
	}

	class MyPagerAdapter extends PagerAdapter {

		private List<View> views;

		public MyPagerAdapter(List<View> views) {
			this.views = views;
		}

		@Override
		public int getCount() {
			return views == null ? 0 : views.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			View view = views.get(position);// 根据位置找到视图
			container.addView(view);// 将此视图添加到container
			return view;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {// 当达到缓存数量时，会调用此方法
			View view = views.get(position);// 根据位置找到视图
			container.removeView(view);// 移除此视图
		}

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.menu_sync:
			// 加载旋转动画
			Animation animation = AnimationUtils.loadAnimation(getActivity(),
					R.anim.sync_anim);
			// 设置动画的监听，当动画结束时，将“取消同步”图片隐藏
			animation.setAnimationListener(new AnimationListener() {
				@Override
				public void onAnimationStart(Animation animation) {
				}

				@Override
				public void onAnimationRepeat(Animation animation) {
				}

				@Override
				public void onAnimationEnd(Animation animation) {
					menuCancelSyncImv.setVisibility(View.INVISIBLE);
				}
			});
			// 启动动画
			menuSyncImv.startAnimation(animation);
			// 显示“取消同步”图片
			menuCancelSyncImv.setVisibility(View.VISIBLE);

			mHandler.postDelayed(new Runnable() {

				@Override
				public void run() {
					// 将此控件上的动画移除
					menuSyncImv.clearAnimation();
				}
			}, 6000);
			break;
		case R.id.add_more_note:
			T.showShort(getActivity(), "add_more_note");
			break;
		case R.id.add_note:
			T.showShort(getActivity(), "add_note");
			break;
		case R.id.menu_overflow:
			if (popupWindow == null) {
				// popupwindow具体的视图内容
				View contentView = View.inflate(getActivity(),
						R.layout.list_more_menu1, null);
				// 创建pupupwindow对象
				popupWindow = new PopupWindow(contentView,
						WindowManager.LayoutParams.WRAP_CONTENT,
						WindowManager.LayoutParams.WRAP_CONTENT, true);
				// 此代码一定要写，不然无法再外面点击关闭该窗口
				popupWindow.setBackgroundDrawable(new ColorDrawable(
						Color.TRANSPARENT));
			}
			// 在视图v下面显示这个popupwindow
			popupWindow.showAsDropDown(v, -150, 50);

			break;

		default:
			break;
		}
	}

}
