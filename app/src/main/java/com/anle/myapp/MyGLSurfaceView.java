

package com.anle.myapp;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Surface view that renders our scene.
 */
public class MyGLSurfaceView extends GLSurfaceView {
  // The renderer responsible for rendering the contents of this view.
  private final MyGLRenderer renderer;

  public MyGLSurfaceView(Context context) {
    this(context, null);
  }

  public MyGLSurfaceView(Context context, AttributeSet attributeSet) {
    super(context, attributeSet);
    // We want OpenGL ES 2.
    setEGLContextClientVersion(2);
    renderer = new MyGLRenderer();
    setRenderer(renderer);
  }

  public MyGLRenderer getRenderer() {
    return renderer;
  }
  private final float TOUCH_SCALE_FACTOR = 180.0f / 320;
  private float mPreviousX;
  private float mPreviousY;

  @Override
  public boolean onTouchEvent(MotionEvent e) {
    // MotionEvent reports input details from the touch screen
    // and other input controls. In this case, you are only
    // interested in events where the touch position changed.

    float x = e.getX();
    float y = e.getY();

    switch (e.getAction()) {
      case MotionEvent.ACTION_MOVE:

        float dx = x - mPreviousX;
        float dy = y - mPreviousY;

        // reverse direction of rotation above the mid-line
        if (y > getHeight() / 2) {
          dx = dx * -1 ;
        }

        // reverse direction of rotation to left of the mid-line
        if (x < getWidth() / 2) {
          dy = dy * -1 ;
        }

        renderer.setAngle(
                renderer.getAngle() +
                        ((dx + dy) * TOUCH_SCALE_FACTOR));
        requestRender();
    }

    mPreviousX = x;
    mPreviousY = y;
    return true;
  }

}
