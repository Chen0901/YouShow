package com.example.youshow.common;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.Volley;

public class MyApplication extends Application {
	private static RequestQueue queues;
	private static MyApplication sInstance;
	public static final String TAG = "VolleyPatterns";

	@Override
	public void onCreate() {
		super.onCreate();
		sInstance = this;
	}

	public static synchronized MyApplication getInstance() {
		return sInstance;
	}

	public RequestQueue getHttpQueues() {
		if (queues == null) {
			queues = Volley.newRequestQueue(getApplicationContext());
		}
		return queues;
	}

	public <T> void addToRequestQueue(Request<T> req, String tag) {
		// set the default tag if tag is empty
		req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);

		VolleyLog.d("Adding request to queue: %s", req.getUrl());

		getHttpQueues().add(req);
	}

	public <T> void addToRequestQueue(Request<T> req) {
		// set the default tag if tag is empty
		req.setTag(TAG);

		getHttpQueues().add(req);
	}

	public void cancelPendingRequests(Object tag) {
		if (queues != null) {
			queues.cancelAll(tag);
		}
	}
}
