package com.pitroq.kevinmobile;

import android.content.Context;

public class Database {
        private final String HOST = "http://192.168.1.40:8080/kevin/";
    private Context context;
//    public Database(Context context) {
//        this.context = context;
//    }
//
//    public void getRequest(String fileName) {
//        RequestQueue queue = Volley.newRequestQueue(context);
//        String url = HOST + fileName;
//
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
//            new Response.Listener<String>() {
//                @Override
//                public void onResponse(String response) {
//                    System.out.println(response);
//                }
//            },
//            new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    System.out.println(error);
//                }
//        });
//
//        queue.add(stringRequest);
//    }
//
}