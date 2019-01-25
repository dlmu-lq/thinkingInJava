package top.itlq.tools.poitlword;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.*;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class BaseTest {
    /**
     * 基本用法，模板编译--》数据绑定--》产生流--》输出
     */
    @Test
    public void test1(){
        XWPFTemplate template = XWPFTemplate.compile("src/main/java/top/itlq/tools/poitlword/doc/template1.docx");
        template.render(new HashMap<String,Object>(){{
            put("testTitle","测试标题");
        }});

        try {
            FileOutputStream out = new FileOutputStream("src/main/java/top/itlq/tools/poitlword/doc/re.docx");
            try {
                template.write(out);
            }finally {
                out.close();
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    /**
     * 文档模板
     */
    @Test
    public void test2(){
        XWPFTemplate template = XWPFTemplate.compile("src/main/java/top/itlq/tools/poitlword/doc/main_template.docx");
        template.render(new HashMap<>(){{
            put("template1",new DocxRenderData(new File("src/main/java/top/itlq/tools/poitlword/doc/template3.docx"),new HashMap<>(){{
                put("testTable",new MiniTableRenderData(
                        new RowRenderData(
                                Arrays.asList(new TextRenderData("姓名"), new TextRenderData("性别"), new TextRenderData("年龄"))
                        ),
                        Arrays.asList(
                                new RowRenderData(Arrays.asList(new TextRenderData("张三"), new TextRenderData("男"), new TextRenderData("100"))),
                                new RowRenderData(Arrays.asList(new TextRenderData("李四"), new TextRenderData("女"), new TextRenderData("99"))),
                                new RowRenderData(Arrays.asList(new TextRenderData("王五"), new TextRenderData("男"), new TextRenderData("101")))
                            )
                ));
            }}));
            put("template2",new DocxRenderData(new File("src/main/java/top/itlq/tools/poitlword/doc/template2.docx"),new HashMap<>(){{
                put("testImage",new PictureRenderData(400,400,"src/main/java/top/itlq/tools/poitlword/doc/The DarkSide Of The Moon.jpg"));
            }}));

            put("template3",new DocxRenderData(new File("src/main/java/top/itlq/tools/poitlword/doc/template1.docx"),new HashMap<>(){{
                put("testTitle","测试标题");
            }}));

            put("template4",new DocxRenderData(new File("src/main/java/top/itlq/tools/poitlword/doc/template4.docx"),new HashMap<>(){{
                put("testPage","测试分页");
            }}));
        }});
        try {
            FileOutputStream out = new FileOutputStream("src/main/java/top/itlq/tools/poitlword/doc/re_all.docx");
            try {
                template.write(out);
            }finally {
                out.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test3(){
        XWPFTemplate template = XWPFTemplate.compile("src/main/java/top/itlq/tools/poitlword/analysis.docx");
        Map<String,Object> oneVoyageMap = new HashMap<>();
        oneVoyageMap.put("fd",12);
        oneVoyageMap.put("fgps","aa");
        oneVoyageMap.put("fgdc","aa");
        oneVoyageMap.put("fcgp","aa");
        oneVoyageMap.put("fcs","aa");
        oneVoyageMap.put("fcca","aa");
        oneVoyageMap.put("fccs","aa");
        oneVoyageMap.put("fmac","aa");
        oneVoyageMap.put("fmic","aa");

        template.render(oneVoyageMap);
        try {
            FileOutputStream out = new FileOutputStream("src/main/java/top/itlq/tools/poitlword/re.docx");
            try {
                template.write(out);
            }finally {
                out.close();
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

}
