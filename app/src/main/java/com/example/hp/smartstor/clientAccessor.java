package com.example.hp.smartstor;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Created by hp on 5/26/2016.
 */
public class clientAccessor {
        public   String BASE_URL=null;
        public   AsyncHttpClient client = new AsyncHttpClient();


    public clientAccessor(String URL)
    {
       this.BASE_URL=URL;
        client.setMaxRetriesAndTimeout(0,100);
        client.setTimeout(100);
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
