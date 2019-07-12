package com.caeit.parser.ldif;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LdifParser {


    //���ز�����Ϣ
    public static String getParmsInfo() {
        String parmsInfo = "[{\"File\":\"�������ļ�\"}]";
        return parmsInfo;
    }

    //�ļ�����-public�ӿ�
    public void parseLdif(File file1, File file2) throws IOException {

        JSONObject jo = new JSONObject();
        String dataline;

        RandomAccessFile raf = null;

            try {
//            ��ѡΪ�ɶ�����д
                raf = new RandomAccessFile(file1, "rw");
                AppendToFile(file2,"{\r\n");
                AppendToFile(file2,"   \"ldap\":[");
                while ((dataline = raf.readLine()) != null) {
                    //��������
                    if ("".equals(dataline))
                        continue;
                    //����ע����
                    if (dataline.trim().startsWith("#"))
                        continue;
                    //�����ݲ�ֳɼ�ֵ��
//                ƥ����֤ʱ�����ײ���β������^��$
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
                //�������һ����¼
                AppendToFile(file2,Util.formatJson(toJsonTop(jo)));
                AppendToFile(file2,"\r\n    ]\r\n");
                AppendToFile(file2,"}");

            } catch (IOException ex) {
                ex.printStackTrace();
            }
            raf.close();
        }


    /**
     * ������׷�ӵ��ļ�β��
     */
    public static void AppendToFile(File file,String content){

        try{
//            �ļ��ɶ�д
            RandomAccessFile randomFile=new RandomAccessFile(file,"rw");
//            �ļ�����
            long fileLength=randomFile.length();
            //��д�ļ�ָ���ƶ����ļ�β��
            randomFile.seek(fileLength);
            randomFile.writeBytes(content);
            randomFile.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }


    /**
     * ��JSONת��Ϊ�ײ��ʽ
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

