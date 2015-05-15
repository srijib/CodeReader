package com.wkswind.money;

import android.opengl.GLES10;
import android.test.AndroidTestCase;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Administrator on 2015/5/14.
 */
public class BitmapUtilsTest extends AndroidTestCase {
    public void testGetMaxtureSize(){
        assertEquals(true, getMaxTextureSize()>0);
    }

    public static int getMaxTextureSize(){
        int[] maxTextureSize = new int[1];
        GLES10.glGetIntegerv(GL10.GL_MAX_TEXTURE_SIZE, maxTextureSize, 0);
        if (maxTextureSize[0]<=0) {
            GLES10.glGetIntegerv(GL10.GL_MAX_TEXTURE_SIZE,maxTextureSize, 0);
        }

        return maxTextureSize[0];
    }
}
