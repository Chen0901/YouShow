package com.example.youshow.activity;

import com.example.youshow.R;
import com.example.youshow.common.CommonFunction;

import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.app.Activity;
import android.content.Intent;

/**
 * ���δ�Ӧ��ʱ����ҳ
 * @author cyl
 *
 */
public class MainActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ImageView splashimage = (ImageView) findViewById(R.id.splashimage);
		//͸���Ƚ��䶯��
        AlphaAnimation animation = new AlphaAnimation(0.1f, 1.0f);
        //���ö���ʱ��
        animation.setDuration(3000);
        //������Ͷ������й���
        splashimage.setAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            //��������ִ��
            @Override
            public void onAnimationStart(Animation animation) {
                
            }

            //��������ִ��
            @Override
            public void onAnimationEnd(Animation animation) {
            	// �ж��Ƿ����û���¼��
            	if(CommonFunction.loginStatus(MainActivity.this)){// ��¼��
            		// ��ת��������ҳ
            		Intent intent = new Intent(MainActivity.this, IndexActivity.class);
                    startActivity(intent);
                    MainActivity.this.finish();
                    // ���뵭��
                    overridePendingTransition(android.R.anim.slide_in_left,
    						android.R.anim.slide_out_right);
            	}else{// û�е�¼��
        			Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                    MainActivity.this.finish();
                    // ���뵭��
                    overridePendingTransition(android.R.anim.slide_in_left,
    						android.R.anim.slide_out_right);
        		}
            }

            //�����ظ�ִ��
            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
	}
}
