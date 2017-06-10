package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by pjpjpj on 2017/6/9.
 */
public class Machine {

    private String userString;
    private static boolean isChat;

    //申请的APIKey
    String APIKEY = "e7a1447ed2182d57758ca845e5a0f36e";

    public Machine(String userString) {
        this.userString = userString;
    }

    public String rutrunString () throws Exception {
        return  dealString();
    }

    private String dealString() throws Exception{

        if (this.userString.indexOf("?") != -1) {
            String returnString = tulingQA(APIKEY,this.userString);
            return returnString;
        }
        String finalString = this.userString;
        ChineseParticiple chineseParticiple = new ChineseParticiple();
        chineseParticiple.setBeginString(finalString);
        int len = chineseParticiple.acceptString(chineseParticiple.getBeginString());
        while (len != 0) {
            String nowString = chineseParticiple.getBeginString().substring(0, len);
            len = chineseParticiple.acceptString(nowString);
        }
        int size = chineseParticiple.getFinalStringArr().getSize() - 1;
        String lastString = new String();
        while (size > -1) {
            lastString = chineseParticiple.getFinalStringArr().get(size) ;
            System.out.println(lastString);
            String tempString = "";
            tempString = dealOtherString(lastString);
            if (tempString.equals("")) {
                size --;
                continue;
            } else {
                return tempString;
            }
        }
        finalString = finalString.replaceAll("你","丽萨");
        finalString = finalString.replaceAll("因为", "");
        finalString = finalString.replaceAll("我", "你");
        return "为什么" + finalString;
    }

    private String dealOtherString (String nowString) {
        String returnString = "";

        switch (nowString) {
            case "你好":
                returnString = "你好呀,小朋友";break;
            case "想我":
                returnString = "为什么要丽萨想你?";break;
            case "你是不是傻":
                returnString = "滚!!!";break;
        }

        return returnString;
    }

    public static String tulingQA(String APIKEY,String question) throws UnsupportedEncodingException, MalformedURLException, IOException {

        String INFO = URLEncoder.encode(question, "utf-8");
        String getURL = "http://www.tuling123.com/openapi/api?key=" + APIKEY
                + "&info=" + INFO;
        URL getUrl = new URL(getURL);
        HttpURLConnection connection = (HttpURLConnection) getUrl
                .openConnection();
        connection.connect();

        // 取得输入流，并使用Reader读取
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                connection.getInputStream(), "utf-8"));
        StringBuffer sb = new StringBuffer();
        String line = "";
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        JSON json = new JSON(sb.toString());
        String str = json.resultString();
        reader.close();
        // 断开连接
        connection.disconnect();
        return str;
    }
}
