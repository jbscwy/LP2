package com.caeit.parser.ldif;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

public class LdifParserTest {
    //指定文件类型
    static String SOURCE_FILE_TYPE = ".ldif";
    /**
     *生成.json格式文件
     */
    public static File createJsonFile(String filePath,String fileName) {
        String fullPath = filePath + File.separator + fileName + ".json";
        File file = new File(fullPath);
        //生成json格式文件
        try{
            if (!file.getParentFile().exists()) { // 如果父目录不存在，创建父目录
                file.getParentFile().mkdirs();
            }
            if (file.exists()) { // 如果已存在,删除旧文件
                file.delete();
            }
            file.createNewFile();
        }catch (IOException ex){
            ex.printStackTrace();
        }
        return file;
    }
    /**
     * 命令行运行
     * 2019-02-23 by hjhu
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        while (true){
//            输入待处理文件的绝对路径
            System.out.println("请输入待处理文件的绝对路径（按回车键结束输入或退出）：");
            Scanner in =new Scanner(System.in);
            String filePath1 = in.nextLine();
            if(null == filePath1 || "".equals(filePath1)){
                System.out.println("退出解析器");
//                退出
                System.exit(0);
            }
            //处理路径中的斜杆
            File file1 = new File(filePath1);
            if(!file1.exists()){
                System.out.println("找不到指定文件或路径错误，请重新输入");
                continue;
            }
            if (!filePath1.toLowerCase().endsWith(SOURCE_FILE_TYPE)){
                System.out.println("文件类型有误，请重新输入");
                continue;
            }
//         输入结果文件的绝对路径
            System.out.println("请输入结果文件的绝对路径（按回车键结束输入或退出）：");
            in =new Scanner(System.in);
            String filePath2 =in.nextLine();
            if(null == filePath2 || "".equals(filePath2)){
                System.out.println("退出解析器");
                System.exit(0);
            }
//          生成json文件
            File file2 = createJsonFile(filePath2,"testFile");
            LdifParser ldifParser = new LdifParser();
            ldifParser.parseLdif(file1,file2);

            System.out.println("解析结果：");
            System.out.println("parmsInfo=======================================================");
            System.out.println(ldifParser.getParmsInfo());
        }
    }
}
