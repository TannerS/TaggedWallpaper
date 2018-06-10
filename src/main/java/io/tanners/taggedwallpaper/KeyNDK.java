package io.tanners.taggedwallpaper;

// http://kn-gloryo.github.io/Build_NDK_AndroidStudio_detail/
public class KeyNDK {
    static {
        System.loadLibrary("TaggedKeys");
    }
    // get api key in ndk c++ file, not included with this source
    public native String getApiKey();
}
