package com.company;

import com.sun.xml.internal.fastinfoset.util.StringArray;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by pjpjpj on 2017/6/4.
 */
public class ChineseParticiple {
    public String beginString;
    public String finalString;
    StringArray stringArr;
    StringArray finalStringArr;

    public StringArray getFinalStringArr() {
        return finalStringArr;
    }

    public void setFinalStringArr(StringArray finalStringArr) {
        this.finalStringArr = finalStringArr;
    }

    public String getBeginString() {
        return beginString;
    }

    public void setBeginString(String beginString) {
        this.beginString = beginString;
    }

    public String getFinalString() {
        return finalString;
    }

    public void setFinalString(String finalString) {
        this.finalString = finalString;
    }

    public StringArray getStringArr() {
        return stringArr;
    }

    public void setStringArr(StringArray stringArr) {
        this.stringArr = stringArr;
    }

    public ChineseParticiple() {
        stringArr = new StringArray();
        finalStringArr = new StringArray();
        participleManage();
    }

    private void participleManage() {
        System.out.println(beginString);


        String text=null;
        FileReader fileReader=null;
        BufferedReader bufferedReader=null;
        try{
            // 词典文件路径
            fileReader=new FileReader("/Users/incloud/Desktop/中文字典匹配.txt");
            bufferedReader=new BufferedReader(fileReader);
            try{
                String read=null;
                while((read=bufferedReader.readLine())!=null){
                    text=text+read + " ";
                    // 把读到的每一个字符串都存到数组中
                    stringArr.add(read);
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(bufferedReader!=null){
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(fileReader!=null){
                try {
                    fileReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // 接受用户输入字符串
    public int acceptString(String accString) {
        // 循环取
        for (int i = 0; i < accString.length(); i ++) {
            String nowString = accString.substring(i);
            // 调用匹配词典方法
//            System.out.println(nowString);
            if (dealString(nowString)) {
                int len = nowString.length();
//                System.out.println(nowString + ",");
                finalStringArr.add(nowString);
                return accString.length() - len;
            }
        }
        return 0;
    }

    // 匹配词典方法
    private boolean dealString(String dealString) {
        for (int i = 0; i < stringArr.getSize(); i ++){
            String tempString = stringArr.get(i);
            // 如果匹配成功,返回真
            if (dealString.equals(tempString)) {
                return true;
            }
        }
        return false;
    }
}
