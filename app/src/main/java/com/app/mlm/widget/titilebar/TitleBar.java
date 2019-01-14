package com.app.mlm.widget.titilebar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.mlm.R;

public class TitleBar extends RelativeLayout implements OnClickListener {
	
	protected View mLeft;
	protected View mRight;
	protected TextView mTitle;
	protected View mAction;
	protected ImageView mLeftIV;
	protected TextView mLeftTV;
	protected ImageView mActionIV;
	protected TextView mActionTV;
	protected ITitleBar mITitleBar;
	private View root;
	
	public TitleBar(Context context) {
		super(context);
		init(null, 0);
	}
	
	public TitleBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(attrs, 0);
	}
	
	public TitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(attrs, defStyleAttr);
	}

	public void init(AttributeSet attrs, int defStyleAttr){
		root = inflate(getContext(), R.layout.widget_titlebar_layout, this);
		mLeft =  findViewById(R.id.titlebar_left);
		mRight =  findViewById(R.id.titlebar_right);
		mTitle = (TextView) findViewById(R.id.titlebar_title);
		mAction =  findViewById(R.id.titlebar_action);
		mActionIV = (ImageView) findViewById(R.id.titlebar_actionIV);
		mActionTV = (TextView) findViewById(R.id.titlebar_actionTV);
		mLeftIV = (ImageView) findViewById(R.id.titlebar_leftIV);
		mLeftTV = (TextView) findViewById(R.id.titlebar_leftTV);

		TypedArray tArray = getContext().obtainStyledAttributes(attrs, R.styleable.TitleBar, defStyleAttr, 0);
		String title = tArray.getString(R.styleable.TitleBar_mTitle);
		String action = tArray.getString(R.styleable.TitleBar_action);
		int bgColor = tArray.getColor(R.styleable.TitleBar_bgColor, getResources().getColor(R.color.colorPrimary));
		Drawable leftIcon = tArray.getDrawable(R.styleable.TitleBar_leftIcon);
		Drawable rightIcon = tArray.getDrawable(R.styleable.TitleBar_rightIcon);
		Drawable actionIcon = tArray.getDrawable(R.styleable.TitleBar_actionIcon);

		root.setBackgroundColor(bgColor);

		if(!TextUtils.isEmpty(title)){
			mTitle.setText(title);
		}

		if(leftIcon != null) mLeftIV.setImageDrawable(leftIcon);

		if(TextUtils.isEmpty(action)){
			mAction.setVisibility(View.GONE);
		}else{
			mAction.setVisibility(View.VISIBLE);
			mRight.setVisibility(View.GONE);

			mActionTV.setText(action);
			if(actionIcon != null) mActionIV.setImageDrawable(actionIcon);
		}

		mLeft.setOnClickListener(this);
		mRight.setOnClickListener(this);
		mAction.setOnClickListener(this);

		tArray.recycle();
	}
	
	 public void setTitlebarListener(ITitleBar listener) {
	        mITitleBar = listener;
	    }
	
	@Override
	public void onClick(View v) {
		if(mITitleBar == null){
			return;
		}
		if(v == mAction){
			mITitleBar.onActionClicked();
		}else if(v == mLeft){
			mITitleBar.onLeftClicked();
		}else if(v == mRight){
			mITitleBar.onRightClicked();
		}
	}

	public void setTitle(String title){
		mTitle.setText(title);
	}

	public void setRightViewVisibility(int visibility){
		mRight.setVisibility(visibility);
	}

	public void setActionViewVisibility(int visibility){
		mAction.setVisibility(visibility);
	}

	public void setLeftViewVisibility(int visibility){
		mLeft.setVisibility(visibility);
	}

	public void setTitleBarBackgroudColor(int colorId){
		root.setBackgroundResource(colorId);
	}

	public void setBlackStyle(){
		mLeftTV.setTextColor(Color.parseColor("#000000"));
		mActionTV.setTextColor(Color.parseColor("#000000"));
	}

	public void setActionText(String action){
		mActionTV.setText(action);
	}

	public String getActionString(){
		return mActionTV.getText().toString();
	}

	public View getRightView(){
		return mRight;
	}
}
