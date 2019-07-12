package com.caeit.parser.ldif;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

public class LdifParserTest {
    //ָ���ļ�����
    static String SOURCE_FILE_TYPE = ".ldif";
    /**
     *����.json��ʽ�ļ�
     */
    public static File createJsonFile(String filePath,String fileName) {
        String fullPath = filePath + File.separator + fileName + ".json";
        File file = new File(fullPath);
        //����json��ʽ�ļ�
        try{
            if (!file.getParentFile().exists()) { // �����Ŀ¼�����ڣ�������Ŀ¼
                file.getParentFile().mkdirs();
            }
            if (file.exists()) { // ����Ѵ���,ɾ�����ļ�
                file.delete();
            }
            file.createNewFile();
        }catch (IOException ex){
            ex.printStackTrace();
        }
        return file;
    }
    /**
     * ����������
     * 2019-02-23 by hjhu
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        while (true){
//            ����������ļ��ľ���·��
            System.out.println("������������ļ��ľ���·�������س�������������˳�����");
            Scanner in =new Scanner(System.in);
            String filePath1 = in.nextLine();
            if(null == filePath1 || "".equals(filePath1)){
                System.out.println("�˳�������");
//                �˳�
                System.exit(0);
            }
            //����·���е�б��
            File file1 = new File(filePath1);
            if(!file1.exists()){
                System.out.println("�Ҳ���ָ���ļ���·����������������");
                continue;
            }
            if (!filePath1.toLowerCase().endsWith(SOURCE_FILE_TYPE)){
                System.out.println("�ļ�������������������");
                continue;
            }
//         �������ļ��ľ���·��
            System.out.println("���������ļ��ľ���·�������س�������������˳�����");
            in =new Scanner(System.in);
            String filePath2 =in.nextLine();
            if(null == filePath2 || "".equals(filePath2)){
                System.out.println("�˳�������");
                System.exit(0);
            }
//          ����json�ļ�
            File file2 = createJsonFile(filePath2,"testFile");
            LdifParser ldifParser = new LdifParser();
            ldifParser.parseLdif(file1,file2);

            System.out.println("���������");
            System.out.println("parmsInfo=======================================================");
            System.out.println(ldifParser.getParmsInfo());
        }
    }
}
