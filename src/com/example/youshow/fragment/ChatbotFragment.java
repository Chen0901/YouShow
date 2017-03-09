package com.example.youshow.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.youshow.R;
import com.example.youshow.bean.Message;
import com.example.youshow.common.CommonFunction;
import com.example.youshow.common.MessageAdapter;
import com.example.youshow.common.MyApplication;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

/**
 * ���������
 * 
 * @author cyl
 *
 */
public class ChatbotFragment extends Fragment {
	private View chatbotLayout;
	private ListView mChatView; // չʾ��Ϣ��listview
	/* �ı��� */
	private Message message = new Message();
	private EditText mMsg;
	private Button send;
	private String sendmsg;
	private List<Message> mDatas = new ArrayList<Message>();
	private MessageAdapter mAdapter;
	private final String API_KEY = "689934a6a4954419a0092d19c1b86fc0";
	private final String URL = "http://www.tuling123.com/openapi/api";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		chatbotLayout = inflater.inflate(R.layout.chatbot_layout, container,
				false);
		initView();
		mAdapter = new MessageAdapter(getActivity(), mDatas);
		mChatView.setAdapter(mAdapter);
		send.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				sendMsg(v);
			}
		});
		return chatbotLayout;
	}

	/**
	 * ������Ϣ
	 * 
	 * @param v
	 *            - ������Ϣ��view�Ķ���
	 */
	protected void sendMsg(View v) {
		sendmsg = mMsg.getText().toString();
		if (TextUtils.isEmpty(sendmsg)) {
			CommonFunction.toastShow("���벻��Ϊ��Ŷ~", getActivity());
			return;
		}

		Message to = new Message(sendmsg, 0);
		mDatas.add(to);

		mAdapter.notifyDataSetChanged();
		mChatView.setSelection(mDatas.size() - 1);

		mMsg.setText("");

		// �ر������
		InputMethodManager imm = (InputMethodManager) getActivity()
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		// �õ�InputMethodManager��ʵ��
		if (imm.isActive()) {
			// �������
			imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT,
					InputMethodManager.HIDE_NOT_ALWAYS);
			// �ر�����̣�����������ͬ������������л�������ر�״̬��
		}

		new Thread() {
			public void run() {
				getResault(sendmsg);
			};
		}.start();
	}

	/**
	 * ��ȡ��IRobot���ص���Ϣ
	 * 
	 * @param msg
	 *            - ���͵���Ϣ
	 */
	protected void getResault(final String msg) {
		StringRequest request = new StringRequest(Request.Method.POST, URL,
				new Response.Listener<String>() {

					@Override
					public void onResponse(String s) {
						try {
							JSONObject object = new JSONObject(s);
							int code = object.getInt("code");
							String text = object.getString("text");
							if (code > 400000 || text == null
									|| text.trim().equals("")) {
								message = new Message("�Ҽ�װ��̫�������ӡ�����", 1);
							} else {
								message = new Message(text, 1);
							}
							mDatas.add(message);
							mAdapter.notifyDataSetChanged();
							mChatView.setSelection(mDatas.size() - 1);
						} catch (JSONException e) {
							CommonFunction.toastShow("�źŲ�̫�á�����", getActivity());
						}
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError volleyError) {
						CommonFunction.toastShow("�����������������Ƿ�������",
								getActivity());
					}
				}) {

			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("key", API_KEY);
				map.put("info", msg);
				map.put("Content-Type", "application/x-www-form-urldecoded");
				return map;
			}
		};
		request.setRetryPolicy(new DefaultRetryPolicy(2000, 1, 1.0f));
		request.setTag("chat");
		MyApplication.getInstance().addToRequestQueue(request);
	}

	/**
	 * ��ʼ���ؼ�
	 */
	private void initView() {
		mChatView = (ListView) chatbotLayout
				.findViewById(R.id.id_chat_listView);
		mMsg = (EditText) chatbotLayout.findViewById(R.id.id_chat_msg);
		mDatas.add(new Message("��ã������������IRobot~��һ��������ɣ�", 1));
		send = (Button) chatbotLayout.findViewById(R.id.id_chat_send);
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	public void onStop() {
		super.onStop();
		MyApplication.getInstance().cancelPendingRequests("chat");
	}
}
