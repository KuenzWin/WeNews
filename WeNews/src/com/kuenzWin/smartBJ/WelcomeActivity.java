package com.kuenzWin.smartBJ;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;

import com.kuenzWin.smartBJ.utils.Constant;
import com.kuenzWin.smartBJ.utils.SharePreferencesUtils;

public class WelcomeActivity extends Activity implements AnimationListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);

		View view_welcome = (View) this.findViewById(R.id.view_welcome);
		AnimationSet as = new AnimationSet(false);

		Animation ra = new RotateAnimation(330, 360,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		ra.setDuration(1 * 1000);
		ra.setFillAfter(true);

		Animation sa = new ScaleAnimation(0, 1, 0, 1,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		sa.setDuration(1 * 1000);
		sa.setFillAfter(true);

		Animation aa = new AlphaAnimation(0, 1);
		aa.setDuration(2 * 1000);
		aa.setFillAfter(true);

		as.addAnimation(ra);
		as.addAnimation(sa);
		as.addAnimation(aa);
		as.setAnimationListener(this);

		view_welcome.setAnimation(as);

	}

	@Override
	public void onAnimationStart(Animation animation) {

	}

	@Override
	public void onAnimationEnd(Animation animation) {
		boolean isStarted = SharePreferencesUtils.getBoolean(this,
				Constant.sp_started, false);
		Intent intent = new Intent();
		if (isStarted) {
			intent.setClass(this, MainActivity.class);
		} else {
			SharePreferencesUtils.putBoolean(this, Constant.sp_started, true);
			intent.setClass(this, GuideActivity.class);
		}
		this.startActivity(intent);
		this.finish();
	}

	@Override
	public void onAnimationRepeat(Animation animation) {

	}

}
