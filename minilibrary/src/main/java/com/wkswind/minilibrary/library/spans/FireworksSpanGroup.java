package com.wkswind.minilibrary.library.spans;

import java.util.ArrayList;
import java.util.Collections;
import android.annotation.TargetApi;
import android.os.Build;
import android.util.Log;
import android.util.Property;

@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
public class FireworksSpanGroup {

	private static final boolean DEBUG = false;
	private static final String TAG = "FireworksSpanGroup";

	private final float mProgress;
	private final ArrayList<MutableForegroundColorSpan> mSpans;
	private final ArrayList<Integer> mSpanIndexes;

	public FireworksSpanGroup() {
		mProgress = 0;
		mSpans = new ArrayList<MutableForegroundColorSpan>();
		mSpanIndexes = new ArrayList<Integer>();
	}

	public void addSpan(MutableForegroundColorSpan span) {
		span.setAlpha(0);
		mSpanIndexes.add(mSpans.size());
		mSpans.add(span);
	}

	public void init() {
		Collections.shuffle(mSpans);
	}

	public void setProgress(float progress) {
		int size = mSpans.size();
		float total = 1.0f * size * progress;

		if (DEBUG)
			Log.d(TAG, "progress " + progress + " * 1.0f * size => " + total);

		for (int index = 0; index < size; index++) {
			MutableForegroundColorSpan span = mSpans.get(index);

			if (total >= 1.0f) {
				span.setAlpha(255);
				total -= 1.0f;
			} else {
				span.setAlpha((int) (total * 255));
				total = 0.0f;
			}
		}
	}

	public float getProgress() {
		return mProgress;
	}

	public static final Property<FireworksSpanGroup, Float> FIREWORKS_GROUP_PROGRESS_PROPERTY = new Property<FireworksSpanGroup, Float>(
			Float.class, "FIREWORKS_GROUP_PROGRESS_PROPERTY") {

		@Override
		public void set(FireworksSpanGroup spanGroup, Float value) {
			spanGroup.setProgress(value);
		}

		@Override
		public Float get(FireworksSpanGroup spanGroup) {
			return spanGroup.getProgress();
		}
	};
}
