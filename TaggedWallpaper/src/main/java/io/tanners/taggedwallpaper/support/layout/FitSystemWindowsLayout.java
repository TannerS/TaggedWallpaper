package io.tanners.taggedwallpaper.support.layout;

        import android.annotation.TargetApi;
        import android.content.Context;
        import android.graphics.Rect;
        import android.os.Build;
        import android.util.AttributeSet;
        import android.view.WindowInsets;

/**
 * ALL CREDIT GOES TOO
 * https://stackoverflow.com/questions/32394135/how-to-recover-from-system-ui-flag-layout-hide-navigation
 * TODO try to use without this
 */
//public class FitSystemWindowsLayout extends FrameLayout {
public class FitSystemWindowsLayout extends android.support.design.widget.CoordinatorLayout {
    private boolean mFit = false;

    public FitSystemWindowsLayout(final Context context) {
        super(context);
    }

    public FitSystemWindowsLayout(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public FitSystemWindowsLayout(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public boolean isFit() {
        return mFit;
    }

    public void setFit(final boolean fit) {
        if (mFit == fit) {
            return;
        }

        mFit = fit;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
            requestApplyInsets();
        } else {
            //noinspection deprecation
            requestFitSystemWindows();
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    protected boolean fitSystemWindows(final Rect insets) {
        if (mFit) {
            setPadding(
                    insets.left,
                    insets.top,
                    insets.right,
                    insets.bottom
            );
            return true;
        } else {
            setPadding(0, 0, 0, 0);
            return false;
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT_WATCH)
    @Override
    public WindowInsets onApplyWindowInsets(final WindowInsets insets) {
        if (mFit) {
            setPadding(
                    insets.getSystemWindowInsetLeft(),
                    insets.getSystemWindowInsetTop(),
                    insets.getSystemWindowInsetRight(),
                    insets.getSystemWindowInsetBottom()
            );
            return insets.consumeSystemWindowInsets();
        } else {
            setPadding(0, 0, 0, 0);
            return insets;
        }
    }
}