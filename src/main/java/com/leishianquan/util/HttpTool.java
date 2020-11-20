package com.leishianquan.util;

import javax.net.ssl.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.security.SecureRandom;

public class HttpTool {
        private static int Timeout = 10000;
        private static String DefalutEncoding = "UTF-8";

        public static String httpRequest(String requestUrl, int timeOut, String requestMethod, String contentType, String postString,String encoding, String Disposttion,String UserAgent,String X_Forwarded_For) throws Exception {
            if ("".equals(encoding) || encoding == null) {
            encoding = DefalutEncoding;
            }

            URLConnection httpUrlConn = null;
            HttpsURLConnection hsc = null;
            HttpURLConnection hc = null;
            InputStream inputStream = null;

            try {
                URL url = new URL(requestUrl);
                if (requestUrl.startsWith("https")) {
                    //创建SSLContext
                    SSLContext sslContext = SSLContext.getInstance("SSL");
                    TrustManager[] tm = {new MyCERT()};
                    //初始化
                    sslContext.init(null, tm, new SecureRandom());;
                    //获取SSLSocketFactory对象
                    SSLSocketFactory ssf = sslContext.getSocketFactory();
                    hsc = (HttpsURLConnection) url.openConnection();


                    hsc.setSSLSocketFactory(ssf);
                    hsc.setHostnameVerifier(allHostsValid);
                    hsc.setRequestMethod(requestMethod);
                    httpUrlConn = hsc;
                } else {
                    hc = (HttpURLConnection) url.openConnection();
                    // 设置请求方式（GET/POST）
                    hc.setRequestMethod(requestMethod);
                    httpUrlConn = hc;
                }

                httpUrlConn.setConnectTimeout(timeOut);
                httpUrlConn.setReadTimeout(timeOut);
                if (contentType != null && !"".equals(contentType)) {
                    httpUrlConn.setRequestProperty("Content-Type", contentType);
                }
                if (Disposttion != null && !"".equals(Disposttion)) {
                    httpUrlConn.setRequestProperty("Content-Disposition", Disposttion);
                }
                if (UserAgent != null && !"".equals(UserAgent)) {
                    httpUrlConn.setRequestProperty("User-Agent", UserAgent);
                }
                if (X_Forwarded_For != null && !"".equals(X_Forwarded_For)) {
                    httpUrlConn.setRequestProperty("X-Forwarded-For", X_Forwarded_For);
                }


                httpUrlConn.setConnectTimeout(timeOut);
//                httpUrlConn.setRequestProperty("User-Agent", "Mozilla/5.0 (compatible; Baiduspider/2.0; +http://www.baidu.com/search/spider.html)");
//                if (Authorization != null && !"".equals(Authorization)) {
//                    httpUrlConn.setRequestProperty("Authorization", Authorization);
//                }

                httpUrlConn.setDoOutput(true);
                httpUrlConn.setDoInput(true);
                httpUrlConn.connect();

                // 当有数据需要提交时
                if (null != postString && !"".equals(postString)) {
                    OutputStream outputStream = httpUrlConn.getOutputStream();
                    // 注意编码格式，防止中文乱码
                    outputStream.write(postString.getBytes(encoding));
                    outputStream.flush();
                    outputStream.close();
                }

                // 将返回的输入流转换成字符串
                inputStream = httpUrlConn.getInputStream();
                String result=readString(inputStream, encoding);
                return result;
            }catch(IOException ie) {
                if (hsc != null) {
                    return readString(hsc.getErrorStream(), encoding);
                }
                if (hc != null) {
                    return readString(hc.getErrorStream(), encoding);
                }
                else{
                    return "";
                }
            }catch (Exception e) {
                throw e;
            } finally {
                if (hsc != null) {
                    hsc.disconnect();
                }
                if (hc != null) {
                    hc.disconnect();
                }
            }

        }

    public static HostnameVerifier allHostsValid = new HostnameVerifier() {
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    };

    public static String readString(InputStream inputStream, String encoding) throws IOException {
        BufferedInputStream bis = null;
        ByteArrayOutputStream baos = null;
        try {
            bis = new BufferedInputStream(inputStream);
            baos = new ByteArrayOutputStream();
            int len = 0;
            byte[] arr = new byte[1];

            while ((len = bis.read(arr)) != -1) {
                baos.write(arr, 0, len);
            }
            return baos.toString(encoding);
        } catch (IOException e) {
            throw e;
        } finally {
            if (baos != null) {
                baos.flush();
                baos.close();
            }
            if (bis != null) {
                bis.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }


    public static String getHttpReuest(String requestUrl,String encoding) throws Exception {

        return  httpRequest(requestUrl, Timeout,"GET", null, "", encoding,"",null,null);
    }

    public static String get_xforward_HttpReuest(String requestUrl,String encoding,String X_Forwarded_For) throws Exception {

        return  httpRequest(requestUrl, Timeout,"GET", null, "", encoding,"",null,null);
    }

    public static String postHttpRequest(String requestUrl,String encoding,String contentType,String postString) throws Exception {

        return  httpRequest(requestUrl, Timeout,"POST", contentType, postString, encoding,"",null,null);
    }



}
