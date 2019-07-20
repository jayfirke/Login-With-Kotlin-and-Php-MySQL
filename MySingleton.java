package com.digimva.epoi;

import android.content.Context;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class MySingleton {
        private static MySingleton minstance;
        private RequestQueue requestQueue;
        private static Context mctx;

        private MySingleton(Context context) {
            mctx = context;
            requestQueue = getRequestQueue();
        }

        public static synchronized MySingleton getInstance(Context context) {
            if (minstance == null) {
                minstance = new MySingleton(context);
            }
            return minstance;
        }

        public RequestQueue getRequestQueue() {
            if (requestQueue == null) {
                // getApplicationContext() is key, it keeps you from leaking the
                // Activity or BroadcastReceiver if someone passes one in.
                requestQueue = Volley.newRequestQueue(mctx.getApplicationContext());
            }
            return requestQueue;
        }

        public <T> void addToRequestQueue(Request<T> request) {
            getRequestQueue().add(request);
        }
 }


