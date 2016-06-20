package com.example.youdaonote.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Toast包装类
 * 
 * @author Administrator
 *
 */
public class T {

	public static void showShort(Context context, String content) {
		Toast.makeText(context, content, Toast.LENGTH_SHORT).show();
	}

}
