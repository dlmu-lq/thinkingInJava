package top.itlq.tools.poitlword;

import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFHeader;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.junit.Test;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;

import java.io.*;
import java.math.BigInteger;

/**
 * 有时用来合并的主模板也可能需要编程形式动态生成
 * 这里测试了生成主模板，每页可能不同的纸张方向和边距
 */
public class CreateMainTemplate {
    @Test
    public void test1() throws Exception{
        XWPFDocument document = new XWPFDocument(new BufferedInputStream(new FileInputStream("src/main/resources/tools/poi/base.docx")));
        createOrientationSec(document,"landscape","{{+moudle1}}","{{+moudle1.5}}");
        createOrientationSec(document,"landscape","{{+moudle2}}");
        createOrientationSec(document,null,"{{+moudle3}}");
        createOrientationSec(document,"landscape","{{+moudle4}}");
        createOrientationSec(document,null,"{{+moudle5}}");
        FileOutputStream fos = new FileOutputStream(new File("src/main/resources/tools/poi/main.docx"));
        document.write(fos);
        fos.close();
    }

    public static void createOrientationSec(XWPFDocument document,String orientation,String...texts){
        CTDocument1 doc = document.getDocument();
        XWPFParagraph para = document.createParagraph();
        XWPFRun run = para.createRun();
        run.setText(texts[0]);
        for(int i=1;i<texts.length;i++){
            run.addBreak();
            run.setText(texts[i]);
        }
        run.addCarriageReturn();
        CTP ctp = para.getCTP();
        CTPPr br = ctp.addNewPPr();
        CTSectPr section = br.addNewSectPr();
        br.setSectPr(section);

        // 设置边距 1440 2.54厘米 1083 1.91厘米
        CTPageMar pageMar = section.addNewPgMar();
        pageMar.setLeft(BigInteger.valueOf(1083L));
        pageMar.setRight(BigInteger.valueOf(1083L));
        pageMar.setTop(BigInteger.valueOf(1440L));
        pageMar.setBottom(BigInteger.valueOf(1440L));

        CTPageSz pageSize = section.isSetPgSz()? section.getPgSz() : section.addNewPgSz();
        if("landscape".equals(orientation)){
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
