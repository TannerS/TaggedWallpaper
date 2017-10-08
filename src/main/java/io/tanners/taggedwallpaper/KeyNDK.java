package io.tanners.taggedwallpaper;

// http://kn-gloryo.github.io/Build_NDK_AndroidStudio_detail/
public class KeyNDK {
    static {
        System.loadLibrary("TaggedKeys");
    }

    public native String getApiKey();
}
