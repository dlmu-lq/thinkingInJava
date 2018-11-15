package top.itlq.tools.poitlword.poi;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.data.DocxRenderData;
import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.junit.Test;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;

import java.io.*;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class PoiBaseFunctions {
    /**
     * 生成一篇文档，测试分节，按节调整页面版式
     */
    @Test
    public void createMain() throws Exception{
        XWPFDocument document = new XWPFDocument(new BufferedInputStream(new FileInputStream("src/main/resources/tools/poi/base.docx")));
        document.getFooterList();
        XWPFHeaderFooterPolicy headerFooterPolicy = document.getHeaderFooterPolicy();
        createOrientationSec(document,"landscape","{{+moudle1}}");
        createOrientationSec(document,"landscape","{{+moudle2}}");
        createOrientationSec(document,"a","{{+moudle3}}");
        createOrientationSec(document,"landscape","{{+moudle4}}");
        createOrientationSec(document,"a","{{+moudle5}}");
        document.getHeaderList();
        headerFooterPolicy.createFooter(STHdrFtr.DEFAULT,document.getParagraphs().toArray(new XWPFParagraph[]{}));
        FileOutputStream fos = new FileOutputStream(new File("src/main/resources/tools/poi/main.docx"));
        document.write(fos);
        fos.close();


//            渲染主模板
        Map<String,Object> mainMap = new HashMap<>();
        Configure config = Configure.newBuilder()
                .build();
        XWPFTemplate template = XWPFTemplate.compile("src\\main\\resources\\tools\\poi\\main.docx",config);
        mainMap.put("moudle1",new DocxRenderData(new File("src\\main\\resources\\tools\\poi\\childTemplates\\child1.docx"),new HashMap<>(){{
            put("test","aaa");
        }}));
        mainMap.put("moudle2",new DocxRenderData(new File("src\\main\\resources\\tools\\poi\\childTemplates\\child2.docx"),new HashMap<>(){{
            put("test2","aaa");
        }}));
        mainMap.put("moudle3",new DocxRenderData(new File("src\\main\\resources\\tools\\poi\\childTemplates\\child1.docx"),new HashMap<>(){{
            put("test","aaa");
        }}));
        template.render(mainMap);
        File outFile = new File("src\\main\\resources\\tools\\poi\\re.docx");

        OutputStream outLoacl = new BufferedOutputStream(new FileOutputStream(outFile));
        template.write(outLoacl);
        outLoacl.flush();
        outLoacl.close();
    }

    @Test
    public void fillMain() throws Exception{

    }

    public static void createOrientationSec(XWPFDocument document,String orientation,String text){
        CTDocument1 doc = document.getDocument();
        XWPFParagraph para = document.createParagraph();
        XWPFRun run = para.createRun();
        run.setText(text);
        run.addCarriageReturn();
        CTP ctp = para.getCTP();
        CTPPr br = ctp.addNewPPr();
        CTSectPr section = br.addNewSectPr();
        br.setSectPr(section);
        CTPageSz pageSize = section.isSetPgSz()? section.getPgSz() : section.addNewPgSz();
        if(orientation.equals("landscape")){
            pageSize.setOrient(STPageOrientation.LANDSCAPE);
            pageSize.setW(BigInteger.valueOf(842 * 20));
            pageSize.setH(BigInteger.valueOf(595 * 20));
        }
        else{
            pageSize.setOrient(STPageOrientation.PORTRAIT);
            pageSize.setH(BigInteger.valueOf(842 * 20));
            pageSize.setW(BigInteger.valueOf(595 * 20));
        }
    }
}
