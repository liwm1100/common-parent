package com.my.common.util;

import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.params.HttpConnectionParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.io.IOUtils;
import org.apache.http.params.CoreProtocolPNames;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.SocketTimeoutException;
import java.util.*;


public class HttpUtil {

    private static final String DEFAULT_CHARSET = "UTF-8";

    private static final long DEFAULT_TIME_OUT = 100000;

    private static final MultiThreadedHttpConnectionManager connectonManager = new MultiThreadedHttpConnectionManager();

    static {
        HttpConnectionManagerParams params = new HttpConnectionManagerParams();
        params.setMaxTotalConnections(1000000);
        params.setBooleanParameter(CoreProtocolPNames.WAIT_FOR_CONTINUE, false);
        connectonManager.setParams(params);
    }

    public static void logParameters(HttpServletRequest request) {
        logParameters(request, false);
    }

    @SuppressWarnings("unchecked")
    public static void logParameters(HttpServletRequest request, boolean isError) {
        String uri = request.getRequestURI();
        Map<String, Object> parameterMap = request.getParameterMap();
        StringBuffer sb = new StringBuffer();
        sb.append("request uri = " + uri + " || ");
        Set<String> keySet = parameterMap.keySet();
        if (keySet != null) {
            for (String key : keySet) {
                if (key != null) {
                    sb.append(key + "=");
                    sb.append(request.getParameter(key));
                    sb.append(",");
                }
            }
        }
    }

    public static HttpClient getHttpClient() {
        HttpClient httpClient = new HttpClient(connectonManager);
        HttpClientParams params = new HttpClientParams();
        params.setConnectionManagerTimeout(DEFAULT_TIME_OUT);
        params.setParameter(HttpConnectionParams.CONNECTION_TIMEOUT,
                DEFAULT_TIME_OUT);
        params.setContentCharset(DEFAULT_CHARSET);
        params.setHttpElementCharset(DEFAULT_CHARSET);
        httpClient.setParams(params);
        return httpClient;
    }

    public static GetMethod getHttpGetMethod(String url) {
        HttpMethodParams params = new HttpMethodParams();
        params.setContentCharset(DEFAULT_CHARSET);
        params.setHttpElementCharset(DEFAULT_CHARSET);
        // params.setUriCharset(DEFAULT_CHARSET);
        GetMethod getMethod = new GetMethod(url);
        getMethod.setParams(params);
        return getMethod;
    }

    public static PostMethod getHttpPostMethod(String url) {
        HttpMethodParams params = new HttpMethodParams();
        params.setContentCharset(DEFAULT_CHARSET);
        params.setHttpElementCharset(DEFAULT_CHARSET);
        // params.setUriCharset(DEFAULT_CHARSET);
        PostMethod postMethod = new PostMethod(url);
        postMethod.setParams(params);
        return postMethod;
    }

    /**
     * get方法请求
     *
     * @param url
     * @param params
     * @return
     */
    public static String sendGet(String url, NameValuePair... params) {
        String result = "";
        HttpClient httpClient = getHttpClient();
        GetMethod getMethod = getHttpGetMethod(url);
        try {
            if (params != null && params.length > 0) {
                getMethod.setQueryString(params);
            }
            httpClient.executeMethod(getMethod);
            result = getMethod.getResponseBodyAsString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            getMethod.releaseConnection();
        }
        return result;
    }

    /**
     * get方法请求
     *
     * @param url
     * @param params
     * @return
     */
    public static String sendGet(String url, Map<String, Object> params) {
        String result = "";
        HttpClient httpClient = getHttpClient();
        GetMethod getMethod = getHttpGetMethod(url);
        try {
            if (params != null && params.size() > 0) {
                NameValuePair[] param = new NameValuePair[params.size()];
                int i = 0;
                for (Map.Entry<String, Object> entry : params.entrySet()) {
                    param[i++] = new NameValuePair(entry.getKey(), String.valueOf(entry.getValue()));
                }
                getMethod.setQueryString(param);
            }
            httpClient.executeMethod(getMethod);
            result = getMethod.getResponseBodyAsString();
        } catch(ConnectTimeoutException ce){
        	ce.printStackTrace();
        } catch(SocketTimeoutException ce){
        	ce.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch(Exception e){
        	e.printStackTrace();
        } finally {
            getMethod.releaseConnection();
        }
        return result;
    }

    public static String sendPost(String urlString) {
        return sendPost(urlString, null, null);
    }

    /**
     * post方法请求
     *
     * @param url
     * @param params
     * @return
     */
    public static String sendPost(String url, NameValuePair... params) {
        String result = "";
        HttpClient httpClient = getHttpClient();
        PostMethod postMethod = getHttpPostMethod(url);
        try {
            if (params != null && params.length > 0) {
                postMethod.setRequestBody(params);
            }
            httpClient.executeMethod(postMethod);
            InputStream resStream = postMethod.getResponseBodyAsStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(resStream));
            StringBuffer resBuffer = new StringBuffer();
            String resTemp = "";
            while ((resTemp = br.readLine()) != null) {
                resBuffer.append(resTemp);
            }
            result = resBuffer.toString();

        } catch(ConnectTimeoutException ce){
        	ce.printStackTrace();
        } catch(SocketTimeoutException se){
        	se.printStackTrace();
        } catch (IOException ie) {
            ie.printStackTrace();
        } catch(Exception e){
        	e.printStackTrace();
        } finally {
            postMethod.releaseConnection();
        }
        return result;
    }

    /**
     * post方法请求
     *
     * @param url
     * @param params
     * @return
     */
    public static String sendPost(String url, Map<String, Object> params) {
        String result = "";
        HttpClient httpClient = getHttpClient();
        PostMethod postMethod = getHttpPostMethod(url);
        try {
            if (params != null && params.size() > 0) {
                NameValuePair[] param = new NameValuePair[params.size()];
                int i = 0;
                for (Map.Entry<String, Object> entry : params.entrySet()) {
                    param[i++] = new NameValuePair(entry.getKey(), String.valueOf(entry.getValue()));
                }
                postMethod.setRequestBody(param);
            }
            httpClient.executeMethod(postMethod);
            InputStream resStream = postMethod.getResponseBodyAsStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(resStream));
            StringBuffer resBuffer = new StringBuffer();
            String resTemp = "";
            while ((resTemp = br.readLine()) != null) {
                resBuffer.append(resTemp);
            }
            result = resBuffer.toString();

        } catch(ConnectTimeoutException ce){
        	ce.printStackTrace();
        } catch(SocketTimeoutException se){
        	se.printStackTrace();
        } catch (IOException ie) {
            ie.printStackTrace();
        } catch(Exception e){
        	e.printStackTrace();
        } finally {
            postMethod.releaseConnection();
        }
        return result;
    }

    public static String sendJsonGet(String url, NameValuePair... params) {

        HttpClient httpClient = getHttpClient();
        GetMethod getMethod = getHttpGetMethod(url);
        return sendJson(httpClient,getMethod,params);
    }

    public static String sendJsonPost(String url, NameValuePair... params) {
        HttpClient httpClient = getHttpClient();
        PostMethod postMethod = getHttpPostMethod(url);
        return sendJson(httpClient,postMethod,params);
    }

    private static String sendJson(HttpClient httpClient ,HttpMethod method, NameValuePair... params){
        try {
            Map<String, Object> paramMap = new HashMap<String, Object>();
            for (NameValuePair param : params) {
                paramMap.put(param.getName(), param.getValue());
            }
            method.setQueryString(new NameValuePair[]{new NameValuePair(
                    "jsonStr", JsonUtil.json(paramMap))});
            httpClient.executeMethod(method);
            return method.getResponseBodyAsString();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            method.releaseConnection();
        }
        return "";
    }

    private static String sendJson(HttpClient httpClient ,HttpMethod method, Map<String, Object> params){
        try {
            method.setQueryString(new NameValuePair[]{new NameValuePair(
                    "jsonStr", JsonUtil.json(params))});
            httpClient.executeMethod(method);
            return method.getResponseBodyAsString();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            method.releaseConnection();
        }
        return "";
    }

    public static String sendJsonGet(String url, Map<String, Object> params) {
        HttpClient httpClient = getHttpClient();
        GetMethod getMethod = getHttpGetMethod(url);
        return sendJson(httpClient,getMethod,params);
    }

    public static String sendJsonPost(String url, Map<String, Object> params) {
        HttpClient httpClient = getHttpClient();
        PostMethod postMethod = getHttpPostMethod(url);
        return sendJson(httpClient,postMethod,params);
    }

    /**
     * 上传xml信息,获取返回信息
     *
     * @return
     */
    public static String postXml(String url, String xmlContent) {
        String result = "";
        HttpClient httpClient = getHttpClient();
        PostMethod postMethod = getHttpPostMethod(url);
        RequestEntity requestEntity = null;
        try {
            requestEntity = new StringRequestEntity(xmlContent,
                    "text/xml;charset=utf-8", DEFAULT_CHARSET);
            postMethod.setRequestEntity(requestEntity);
            httpClient.executeMethod(postMethod);
            result = postMethod.getResponseBodyAsString();
        } catch (HttpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            postMethod.releaseConnection();
        }
        return result;
    }

    public static String uploadPost(String targetURL, final File[] files,
                                    final NameValuePair... params) {
        String result = "";
        File targetFile = files[0];
        PostMethod postMethod = getHttpPostMethod(targetURL);
        try {
            // 通过以下方法可以模拟页面参数提交
            postMethod.setQueryString(params);
            Part[] parts = {new FilePart("media", targetFile)};
            postMethod.setRequestEntity(new MultipartRequestEntity(parts,
                    postMethod.getParams()));
            HttpClient client = new HttpClient();
            client.executeMethod(postMethod);
            result = postMethod.getResponseBodyAsString();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            postMethod.releaseConnection();
        }
        return result;
    }

    public static void downloadGet(String targetURL, String targetFile,
                                   final NameValuePair... params) {
        HttpClient httpClient = getHttpClient();
        GetMethod getMethod = getHttpGetMethod(targetURL);
        try {
            getMethod.setQueryString(params);
            httpClient.executeMethod(getMethod);
            File storeFile = new File("/Users/huqilong/Desktop/" + targetFile
                    + ".jpg");
            IOUtils.copy(new ByteArrayInputStream(getMethod.getResponseBody()),
                    new FileOutputStream(storeFile));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                System.out.println(getMethod.getResponseBodyAsString());
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } finally {
            getMethod.releaseConnection();
        }
    }

    public static String postJson(String url, String body,
                                  final NameValuePair... params) {
        String result = "";
        HttpClient httpClient = getHttpClient();
        PostMethod postMethod = getHttpPostMethod(url);
        RequestEntity requestEntity = null;
        try {
            postMethod.setQueryString(params);
            requestEntity = new StringRequestEntity(body,
                    "text/json;charset=utf-8", DEFAULT_CHARSET);
            postMethod.setRequestEntity(requestEntity);
            httpClient.executeMethod(postMethod);
            result = postMethod.getResponseBodyAsString();
        } catch (HttpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            postMethod.releaseConnection();
        }
        return result;
    }

    public static String sendGet(String url, String param) {
        String result = "";
        HttpClient httpClient = getHttpClient();
        GetMethod getMethod = getHttpGetMethod(url);
        try {
            getMethod.setQueryString(param);
            httpClient.executeMethod(getMethod);
            result = getMethod.getResponseBodyAsString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            getMethod.releaseConnection();
        }
        return result;
    }

    public static String paramToString(Map<String, Object> param) {
        String result = "";
        ArrayList<String> list = new ArrayList<String>();

        Iterator it = param.keySet().iterator();
        while (it.hasNext()) {
            String key = (String) it.next();
            if (it.hasNext()) {
                list.add(key + "=" + param.get(key) + "&");
            } else {
                list.add(key + "=" + param.get(key));
            }
        }

        int size = list.size();
        String[] arrayToSort = list.toArray(new String[size]);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            sb.append(arrayToSort[i]);
        }
        result = sb.toString();
        return result;
    }

    public static void main(String args[]) {
        // String result =
        // HttpUtil.sendGet("http://interface-test.ikongjian.com/interface/orders/detail",
        // new NameValuePair[]{new NameValuePair("ordersNo",
        // "150513101852536")});
//		String result = HttpUtil
//				.sendGet("http://interface-test.ikongjian.com/interface/orders/detail?ordersNo=150513101852536");
//		System.out.println(result);


    }

}