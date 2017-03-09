package com.example.youshow.common;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;

/**
 * �������ĸ��ַ���
 * @author cyl
 *
 */
public class NetHelper {

	/**
	 * �ж���������״̬
	 * @param context - activity������
	 * @return - boolean���͵���������״̬
	 */
    public static boolean NetWorkStatus(Context context) {
        //��ȡϵͳ�����ӷ���
        ConnectivityManager connect = (ConnectivityManager) context.getSystemService(Context
                .CONNECTIVITY_SERVICE);
        if (connect == null) {
            return false;
        } else {
            // ��ȡ��������״̬��NetWorkInfo����,��ȡ������������
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
     * ��鲢��������
     * @param context - activity��������
     */
    public static boolean checkNetWork(final Context context) {
        if (!NetWorkStatus(context)) {
        	CustomDialog.Builder builder = new CustomDialog.Builder(context);
    		builder.setMessage("û�п��õ�����,�Ƿ������������ã�");
    		builder.setTitle("����������ʾ");
    		builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
    			public void onClick(DialogInterface dialog, int which) {
    				//��ת�������������
                    context.startActivity(new Intent(Settings.ACTION_SETTINGS));
    			}
    		});

    		builder.setNegativeButton("ȡ��",
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
