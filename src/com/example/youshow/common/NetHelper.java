package com.example.youshow.common;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;

/**
 * 针对网络的各种方法
 * @author cyl
 *
 */
public class NetHelper {

	/**
	 * 判断网络连接状态
	 * @param context - activity上下文
	 * @return - boolean类型的网络连接状态
	 */
    public static boolean NetWorkStatus(Context context) {
        //获取系统的连接服务
        ConnectivityManager connect = (ConnectivityManager) context.getSystemService(Context
                .CONNECTIVITY_SERVICE);
        if (connect == null) {
            return false;
        } else {
            // 获取代表联网状态的NetWorkInfo对象,获取网络的连接情况
            NetworkInfo[] infos = connect.getAllNetworkInfo();
            if (infos != null) {
                for (NetworkInfo network : infos) {
                    if (network.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    /**
     * 检查并设置网络
     * @param context - activity的上下文
     */
    public static boolean checkNetWork(final Context context) {
        if (!NetWorkStatus(context)) {
        	CustomDialog.Builder builder = new CustomDialog.Builder(context);
    		builder.setMessage("没有可用的网络,是否对网络进行设置？");
    		builder.setTitle("网络设置提示");
    		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
    			public void onClick(DialogInterface dialog, int which) {
    				//跳转到设置网络界面
                    context.startActivity(new Intent(Settings.ACTION_SETTINGS));
    			}
    		});

    		builder.setNegativeButton("取消",
    				new android.content.DialogInterface.OnClickListener() {
    					public void onClick(DialogInterface dialog, int which) {
    						dialog.dismiss();
    					}
    				});

    		builder.create().show();
    		return false;
        }else{
        	return true;
        }
    }

}
