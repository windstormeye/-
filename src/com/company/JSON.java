package com.company;

/**
 * Created by pjpjpj on 2017/6/4.
 */
public class JSON {

    private String jsonString;

    public String getJsonString() {
        return jsonString;
    }

    public void setJsonString(String jsonString) {
        this.jsonString = jsonString;
    }

    public JSON(String jsonString) {
        this.jsonString = jsonString;
    }

    public String resultString() {
        StringBuffer buf = new StringBuffer();

        int index = jsonString.indexOf("text") + 7;
        while (index < jsonString.length() - 1) {
            char xxx = jsonString.charAt(index++);
            if (xxx != '"') {
                buf.append(xxx);
            }
        }
        return buf.toString();
    }
}
