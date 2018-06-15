package nkucs1416.simpbook.network;
 
/**
 * 回调接口
 */
public interface HttpResponeCallBack {
	public void onResponeStart(String apiName);

	public void onSuccess(String apiName, Object object);

	public void onFailure(String apiName, Throwable t, int errorNo, String strMsg);
 
}
