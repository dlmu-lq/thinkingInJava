package top.itlq.tools.image;

import org.junit.Test;

import java.io.*;
import java.util.Base64;

public class Base64Convert {
    @Test
    public void test(){
        Base64.Decoder decoder = Base64.getDecoder();
        try {
            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream("src/main/resources/tools/test.png"));
            BufferedReader br = new BufferedReader(new FileReader("src/main/resources/tools/image/base64string"));

            String a;
            StringBuffer sb = new StringBuffer();
            while ((a = br.readLine()) != null){
                sb.append(a);
            }
            try{
                out.write(decoder.decode(sb.toString()));
            }finally {
                out.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
