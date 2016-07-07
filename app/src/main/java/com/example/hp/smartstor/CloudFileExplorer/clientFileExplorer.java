package com.example.hp.smartstor.CloudFileExplorer;

/**

 * Created by hp on 7/7/2016.
 */
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.SyncHttpClient;
import com.loopj.android.http.AsyncHttpClient;

/**
 * Created by hp on 6/9/2016.
 */
public class clientFileExplorer{

    public String BASE_URL=null;
    public AsyncHttpClient client = new AsyncHttpClient();


    public clientFileExplorer(String URL)
    {
        this.BASE_URL=URL;
        client.setUserAgent("sid");
    }

    public  void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public  void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    private  String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }

}