package cn.edu.mydotabuff.common.http;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import cn.edu.mydotabuff.util.Debug;

public class HttpRequestImpl {

    /**
     * 默认编码utf-8"
     */
    public static final String DEFAULT_ENCODING = "utf-8";
    private static String encoding = DEFAULT_ENCODING;

    public static String getEncoding() {
        return encoding;
    }

    public static void setEncoding(String encoding) {
        HttpRequestImpl.encoding = encoding;
    }

    private static final int DEFAULT_CONNECTION_TIMEOUT = 10 * 1000;
    private static final int DEFAULT_READ_TIMEOUT = 10 * 1000;

    /**
     * 连接超时
     */
    public static int CONNECTION_TIMEOUT = DEFAULT_CONNECTION_TIMEOUT;
    /**
     * 读取超时
     */
    public static int READ_TIMEOUT = DEFAULT_READ_TIMEOUT;


    /**
     * 供外部调用的GET接口
     */
    public static byte[] httpGetFromServer(String path) {
        return httpGetFromServer(path, encoding);
    }

    /**
     * HTTP get方式请求服务器的资源
     *
     * @param path :服务器URL地址
     *
     * @return byte[]
     */
    private static byte[] httpGetFromServer(String path, String encoding) {
        byte[] result = null;
        try {
            InputStream is = null;
            Debug.d("hao", path);
            URL url;
            url = new URL(path);
            HttpURLConnection conn;
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(CONNECTION_TIMEOUT);// 5000
            conn.setReadTimeout(READ_TIMEOUT); // 3000
            conn.setRequestMethod("GET");
            conn.connect();
            int code;
            code = conn.getResponseCode();
            switch (code) {
                case 200: {// 服务器成功返回网�?
                    is = conn.getInputStream();
                    int len = 0;
                    byte[] buff = new byte[1024];
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    while ((len = is.read(buff)) != -1) {
                        baos.write(buff, 0, len);
                        baos.flush();
                    }
                    // String temp = new String(baos.toByteArray(), encoding);
                    // result = temp.getBytes();
                    result = baos.toByteArray();
                    baos.close();
                    is.close();

                }
                break;
                case 404: {// 请求的网页不存在
                    String temp = "{\"data\":\"\",\"info\":\"网络问题!\",\"status\":404}";
                    result = temp.getBytes();
                    break;
                }
                case 503: {// 服务器超�?
                    String temp = "{\"data\":\"\",\"info\":\"连接服务器超时?\",\"status\":503}";
                    result = temp.getBytes();
                    break;
                }
            }
            conn.disconnect();
        } catch (MalformedURLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            String temp = "{\"data\":\"\",\"info\":\"连接服务器超时?\",\"status\":503}";
            result = temp.getBytes();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            String temp = "{\"data\":\"\",\"info\":\"连接服务器超时?\",\"status\":503}";
            result = temp.getBytes();
        }
        return result;
    }
}
