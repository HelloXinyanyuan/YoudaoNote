package com.example.youdaonote;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * 云协作碎片
 * 
 * @author Administrator
 *
 */
public class TeamworkFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View inflate = inflater.inflate(R.layout.fragment_teamwork, null);

		ListView listview = (ListView) inflate.findViewById(R.id.listview);

		// 构建数据
		String[] objects = new String[100];
		for (int i = 0; i < objects.length; i++) {
			objects[i] = "item" + i;
		}
		// 创建适配器
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1, objects);
		// 适配器与listview关联
		listview.setAdapter(adapter);

		return inflate;
	}
}
