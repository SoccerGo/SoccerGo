package heracles.soccergo.Tools;

import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 * Created by 10539 on 2016/9/13.
 */
public class HttpConnectionUtil
{

    static String BOUNDARY = java.util.UUID.randomUUID().toString();
    static String PREFIX = "--", LINEND = "\r\n";
    static String MULTIPART_FROM_DATA = "multipart/form-data";
    static String CHARSET = "UTF-8";

    public static boolean doPostPicture(String urlStr, Map<String, Object> paramMap, File pictureFile)
            throws Exception
    {

        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setDoInput(true);// 允许输入
        conn.setDoOutput(true);// 允许输出
        conn.setUseCaches(false);
        conn.setReadTimeout(10 * 1000); // 缓存的最长时间
        conn.setRequestMethod("POST");

        conn.setRequestProperty("Charset", "UTF-8");
        conn.setRequestProperty("Connection", "Keep-Alive");
        conn.setRequestProperty("Content-Type", MULTIPART_FROM_DATA + ";boundary=" + BOUNDARY);

        DataOutputStream os = new DataOutputStream(conn.getOutputStream());
        if(pictureFile!=null)
        {
            StringBuilder sb = new StringBuilder(); //用StringBuilder拼接报文，用于上传图片数据
            sb.append(PREFIX);
            sb.append(BOUNDARY);
            sb.append(LINEND);
            sb.append("Content-Disposition: form-data; name=\"picture\"; filename=\"" + pictureFile.getName() + "\"" + LINEND);
            sb.append("Content-Type: image/jpg; charset=" + CHARSET + LINEND);
            sb.append(LINEND);
            os.write(sb.toString().getBytes());
            InputStream is = new FileInputStream(pictureFile);

            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = is.read(buffer)) != -1)
            {
                os.write(buffer, 0, len); //写入图片数据
            }
            is.close();
            os.write(LINEND.getBytes());
        }

        StringBuilder text = new StringBuilder();
        for (Map.Entry<String, Object> entry : paramMap.entrySet())
        { //在for循环中拼接报文，上传文本数据
            text.append("--");
            text.append(BOUNDARY);
            text.append("\r\n");
            text.append("Content-Disposition: form-data; name=\"" + entry.getKey() + "\"\r\n\r\n");
            text.append(entry.getValue());
            text.append("\r\n");
        }
        os.write(text.toString().getBytes("utf-8")); //写入文本数据

        // 请求结束标志
        byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINEND).getBytes();
        os.write(end_data);
        os.flush();
        os.close();

        // 得到响应码
        int res = conn.getResponseCode();
        System.out.println("asdf code " + res);
        System.out.println("asdf " + conn.getResponseMessage());
        conn.disconnect();

        if(res == 200)
        {
            //读取服务器返回结果
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuffer result = new StringBuffer();
            String str;
            while ((str = reader.readLine()) != null)
            {
                result.append(str);
            }
            if (Test.flag)
                Log.d("result", result.toString());
            //解析返回值，判断是否登入成功
            final JSONObject jsonObject = new JSONObject(result.toString());
            int ret = jsonObject.getInt("success");

            switch (ret)
            {
                case CONSTANT.SUCCESS:
                    Log.d("success","ok");
                    return true;
                case CONSTANT.ERROR:
                    Log.d("error",jsonObject.getString("error"));
                    return false;
            }
        }

        return false;
    }

}
