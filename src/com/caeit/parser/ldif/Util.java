package com.caeit.parser.ldif;//package com.caeit.parser.ldif;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class Util {

    /**
     * JSON����
     * @param jsonStr
     * @return
     */


    public static String formatJson(String jsonStr){
        try {
            ByteArrayInputStream in = new ByteArrayInputStream(jsonStr.getBytes());
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            char ch;
            int read;
            int space=0;
            while((read = in.read()) > 0){
                ch = (char)read;
                switch (ch){
                    case '{': {
                        space = outputAndRightMove(8, ch, out);
                        break;
                    }

                    case '}': {
                        space = outputAndLeftMove(10, ch, out);
                        break;
                    }

                    case ',': {
                        out.write(ch);
                        outputNewline(out);
                        out.write(getBlankingStringBytes(space));
                        break;
                    }
                    default: {
                        out.write(ch);
                        break;
                    }
                }
            }
            return out.toString();
        } catch (IOException e){
            e.printStackTrace();
        }

        return null;
    }


    public static int outputAndRightMove(int space, char ch, ByteArrayOutputStream out) throws IOException {
        //����
        outputNewline(out);
        //��������
        out.write(getBlankingStringBytes(space));
        out.write(ch);
        outputNewline(out);
        space += 4;
        //�����������������ַ�
        out.write(getBlankingStringBytes(space));
        return space;
    }
    public static int outputAndLeftMove(int space, char ch, ByteArrayOutputStream out) throws IOException {
        outputNewline(out);
        space -= 4;
        out.write(getBlankingStringBytes(space));
        out.write(ch);
        return space;
    }
    public static byte[] getBlankingStringBytes(int space){
        StringBuilder sb = new StringBuilder("");
        for (int i = 0; i < space; i++) {
            sb.append(" ");
        }
        return sb.toString().getBytes();
    }

    public static void outputNewline(ByteArrayOutputStream out){
        out.write('\n');
    }
}




