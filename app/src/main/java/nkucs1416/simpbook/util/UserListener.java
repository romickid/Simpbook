package nkucs1416.simpbook.util;

/**
 * Adapter-Activity数据传递相关
 */
public interface UserListener {
    void OnUserSignInSuccess();
    void OnUserSignInFail();
    void OnUserDownloadSuccess();
    void OnUserDownloadFail();
}