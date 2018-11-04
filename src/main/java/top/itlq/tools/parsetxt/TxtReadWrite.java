/**
 * 读写文件用
 * 读一个文件夹内一些特定格式的txt文件，生成一个js中的一个json对象
 */
package top.itlq.tools.parsetxt;

import org.junit.Test;

import java.io.*;

public class TxtReadWrite {
    private final String readPath="";
    private final String writePath="";
    private final String [] types={"one","two","three"};

    @Test
    public void test1(){
        writeStringToTxt(readFilesToString());
    }

    public String readFilesToString(){
        StringBuilder sb = new StringBuilder();
        File file=new File(readPath);
        File[]files=file.listFiles();
        try {
            sb.append("{");
//            遍历文件
            for(int i=0;i<files.length;i++){
                if(files[i].isFile()){
                    String fileName=files[i].getName().replace(".txt","");
                    sb.append(fileName).append(":{");
                    StringBuilder temp = new StringBuilder();
                    InputStreamReader reader=new InputStreamReader(new FileInputStream(files[i]));
                    BufferedReader bf = new BufferedReader(reader);
                    String line="";
                    while ((line=bf.readLine())!=null) {
                        temp.append(line);
                    }
                    String [] strings=temp.toString().split("]");
                    for(int j=0;j<strings.length;j++){
                        sb.append(types[j]).append(":").append(strings[j]).append("]");
                        if(j!=strings.length-1){
                            sb.append(",");
                        }
                    }
                    sb.append("}");
                    if(i!=files.length-1){
                        sb.append(",");
                    }
                }
            }
            sb.append("}");
            System.out.println("read Done!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public void writeStringToTxt(String content){
        File file=new File(writePath);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            if(!file.exists()){
                file.createNewFile();
            }
            byte[] contentBytes=content.getBytes();
            fos.write(contentBytes);
            fos.flush();
            fos.close();
            System.out.println("write Done!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
