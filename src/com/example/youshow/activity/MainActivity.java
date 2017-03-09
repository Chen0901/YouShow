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
 * 初次打开应用时的首页
 * @author cyl
 *
 */
public class MainActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ImageView splashimage = (ImageView) findViewById(R.id.splashimage);
		//透明度渐变动画
        AlphaAnimation animation = new AlphaAnimation(0.1f, 1.0f);
        //设置动画时长
        animation.setDuration(3000);
        //将组件和动画进行关联
        splashimage.setAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            //动画启动执行
            @Override
            public void onAnimationStart(Animation animation) {
                
            }

            //动画结束执行
            @Override
            public void onAnimationEnd(Animation animation) {
            	// 判断是否有用户登录过
            	if(CommonFunction.loginStatus(MainActivity.this)){// 登录过
            		// 跳转至个人首页
            		Intent intent = new Intent(MainActivity.this, IndexActivity.class);
                    startActivity(intent);
                    MainActivity.this.finish();
                    // 淡入淡出
                    overridePendingTransition(android.R.anim.slide_in_left,
    						android.R.anim.slide_out_right);
            	}else{// 没有登录过
        			Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                    MainActivity.this.finish();
                    // 淡入淡出
                    overridePendingTransition(android.R.anim.slide_in_left,
    						android.R.anim.slide_out_right);
        		}
            }

            //动画重复执行
            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
	}
}
