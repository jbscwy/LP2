package com.caeit.parser.ldif;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LdifParser {


    //返回参数信息
    public static String getParmsInfo() {
        String parmsInfo = "[{\"File\":\"待解析文件\"}]";
        return parmsInfo;
    }

    //文件解析-public接口
    public void parseLdif(File file1, File file2) throws IOException {

        JSONObject jo = new JSONObject();
        String dataline;

        RandomAccessFile raf = null;

            try {
//            可选为可读、可写
                raf = new RandomAccessFile(file1, "rw");
                AppendToFile(file2,"{\r\n");
                AppendToFile(file2,"   \"ldap\":[");
                while ((dataline = raf.readLine()) != null) {
                    //跳过空行
                    if ("".equals(dataline))
                        continue;
                    //过滤注释行
                    if (dataline.trim().startsWith("#"))
                        continue;
                    //将数据拆分成键值对
//                匹配验证时，在首部和尾部加上^和$
                    Pattern p = Pattern.compile("^(.*?):(.*)");
                    Matcher m = p.matcher(dataline);

                    if (m.matches()) {
                        String elem1 = m.group(1);
                        String elem2 = m.group(2);
                        elem2 = elem2.trim();
                        if (elem1.equals("dn")) {
                            if (jo.size() != 0) {

                                    AppendToFile(file2, Util.formatJson(toJsonTop(jo)));
                                    AppendToFile(file2,",");
                                    System.out.println(Util.formatJson(toJsonTop(jo)));
//                                }
                            }
                            jo = new JSONObject();
                            jo.accumulate(elem1, elem2);
                        } else {
                            jo.accumulate(elem1, elem2);
                        }
                    }
                }
                //处理最后一条记录
                AppendToFile(file2,Util.formatJson(toJsonTop(jo)));
                AppendToFile(file2,"\r\n    ]\r\n");
                AppendToFile(file2,"}");

            } catch (IOException ex) {
                ex.printStackTrace();
            }
            raf.close();
        }


    /**
     * 将内容追加到文件尾部
     */
    public static void AppendToFile(File file,String content){

        try{
//            文件可读写
            RandomAccessFile randomFile=new RandomAccessFile(file,"rw");
//            文件长度
            long fileLength=randomFile.length();
            //将写文件指针移动到文件尾部
            randomFile.seek(fileLength);
            randomFile.writeBytes(content);
            randomFile.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }


    /**
     * 将JSON转化为首层格式
     * @param jsonObject
     * @return
     */
    private String toJsonTop(JSONObject jsonObject) {

        JSONObject jo=new JSONObject();
        Iterator iterator = jsonObject.keys();
        while (iterator.hasNext()) {
            String key = (String) iterator.next();
            String value = jsonObject.getString(key);
            if(value.contains("[")&&value.contains("]")){
                value = value.replace("[\"","");
                value = value.replace("\"]","");
                value = value.replace("\",\"",";");
            }
            jo.accumulate(key,value);
        }
        return jo.toString();
    }


}

