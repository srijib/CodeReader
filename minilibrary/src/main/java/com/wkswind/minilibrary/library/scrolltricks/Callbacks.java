package com.wkswind.minilibrary.library.scrolltricks;

public interface Callbacks {
	public void onScrollChanged(int scrollY);

	public void onDownMotionEvent();

	public void onUpOrCancelMotionEvent();
}