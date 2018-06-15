package nkucs1416.simpbook.network;

import com.android.volley.Request;
import com.android.volley.Request.Method;
import com.android.volley.Request.Priority;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.android.volley.Response;

import android.content.Context;


import org.json.JSONArray;  
import org.json.JSONException;  
import org.json.JSONObject;

/**
 *         网络请求处理类
 */
public class RequestManager {
    private static RequestQueue mRequestQueue;
    private static ImageLoader mImageLoader;


    /**
     * 初始化请求队列
     * @param context 调用者运行环境
     */
    private synchronized static void initRequestQueue(Context context) {
        if (mRequestQueue == null) {
            //创建一个请求队列
            mRequestQueue = Volley.newRequestQueue(context);
        }
    }


    /**
     * 添加请求到请求队列中
     * @param request
     * @param tag
     */
    private static void addRequest(Request<?> request, Object tag) {
        if (tag != null) {
            request.setTag(tag);
        }
        mRequestQueue.add(request);
    }

    /**
     * post 请求数据
     *
     * @param app_url     公共的接口前缀
     * @param tag_url     接口名称
     * @param parameter  请求参数封装对象
     * @param callback   接口回调监听
     * @param context 调用者运行环境
     */

    public static <T> void post(final String app_url, final String tag_url, final JSONObject parameter,
                                final HttpResponeCallBack callback, Context context) {
        //发送post请求服务器
        post(app_url, tag_url, parameter, callback, Priority.NORMAL, context);
    }


    /**
     * post 请求数据
     *
     * @param app_url    路径
     * @param url        接口名称
     * @param parameter  请求参数封装对象
     * @param callback   接口回调监听
     * @param priority   指定接口请求线程优先级
     * @param context 调用者运行环境
     */


    public static <T> void post(final String app_url, final String url, final JSONObject parameter,
                                final HttpResponeCallBack callback, Priority priority, Context context) {
        if (callback != null) {
            callback.onResponeStart(url);//回调请求开始
        }

        initRequestQueue(context);

        //将公共的接口前缀和接口名称拼接
        StringBuilder builder = new StringBuilder(app_url);
        builder.append(url);

        {// 检查当前网络是否可用
            final NetworkUtils networkUtils = new NetworkUtils(context);
            if (!networkUtils.isNetworkConnected() && android.os.Build.VERSION.SDK_INT > 10) {
                if (callback != null) {
                    callback.onFailure(url, null, 0, "网络出错");//回调请求失败
                    return;
                }
            }
        }

        /**
         * 使用Volley框架真正去请求服务器
         * Method.POST：请求方式为post
         * builder.toString()：请求的链接
         * Listener<JsonObject>：监听
         */
        JsonObjectRequest request = new JsonObjectRequest(Method.POST, builder.toString(), parameter,
                new Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response != null && callback != null) {
                                callback.onSuccess(url, response);

                            }

                        } catch (Exception e) {
                            // TODO: handle exception
                            if (callback != null) {
                                //回调请求失败--解析异常
                                callback.onFailure(url, e, 0, "解析异常");
                                return;
                            }
                        }
                    }
                }, new ErrorListener() {
                    //请求出错的监听
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (callback != null) {
                            if (error != null) {
                                callback.onFailure(url, error.getCause(), 0,
                                        error.getMessage());
                            } else {
                                callback.onFailure(url, null, 0, "");
                            }
                        }
                    }
        });

        //添加请求到请求队列中
        addRequest(request, url);
    }


}
