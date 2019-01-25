package top.itlq.tools.poitlword;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.data.DocxRenderData;
import org.junit.jupiter.api.Test;
import top.itlq.tools.poitlword.policy.TableStyleTestPolicy;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;

/**
 * 一些word导出时格式的测试，
 * 使用poi-tl，poi 插件
 */
public class StyleTest {
    static final String outPath;
    static final String tableTemplatePath;
    static final String mainTemplatePath;
    static {
        String docPath = "src/main/resources/tools/poi/doc";
        outPath = docPath + "/style_test_re.docx";
        tableTemplatePath = docPath + "/styleTest/table_template_test.docx";
        mainTemplatePath = docPath + "/style_template.docx";
    }

    /**
     * 表格行高设置测试
     * 注意导出结果在编译完的目录中
     * 页面边距测试，主模板边距、子模版边距均改，可以实现；
     * 其实依赖主模板，因此如果主模板要生成，需在主模板中设置
     *
     */
    @Test
    void test(){
        Configure config = Configure.newBuilder()
                .customPolicy("tableStyleTest",new TableStyleTestPolicy())
                .build();
        XWPFTemplate mainTemplate = XWPFTemplate.compile(mainTemplatePath,config);
        mainTemplate.render(new HashMap<>(){{
            put("template1",getTableSubDoc());
        }});
        try {
            File outFile = new File(outPath);
            outFile.createNewFile();
            BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(outFile));
            mainTemplate.write(outputStream);
            outputStream.flush();
            outputStream.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    static DocxRenderData getTableSubDoc(){
        HashMap<String,Object> map = new HashMap<>(){{
           put("tableStyleTest",new Object());
        }};
        return new DocxRenderData(new File(tableTemplatePath),map);
    }
}
