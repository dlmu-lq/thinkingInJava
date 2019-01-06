package top.itlq.tools.poitlword;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.apache.poi.xwpf.usermodel.*;
import org.junit.Test;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;

import java.io.*;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;

public class HeaderAndFooter {
//    public void test1() throws Exception{
//        HashMap mainMap = new HashMap();
//        File outFile = new File("src/main/resources/poi/word/template/main.docx");
//        Configure config = Configure.newBuilder()
//                .build();
//        XWPFTemplate template = XWPFTemplate.compile("src/main/resources/template/moudles/main.docx",config);
//        template.render(mainMap);
//        OutputStream outLoacl = new BufferedOutputStream(new FileOutputStream(outFile));
//        template.write(outLoacl);
//        outLoacl.flush();
//        outLoacl.close();
//    }
    private static XWPFParagraph[] parsHeader;

    @Test
    public void test1() throws Exception{
        XWPFDocument document = new XWPFDocument(new BufferedInputStream(new FileInputStream("src/main/resources/poi/word/template/base.docx")));
        List<XWPFHeader> headers = document.getHeaderList();
//        parsHeader = headers.get(2).getParagraphs().toArray(new XWPFParagraph[]{});
//        CTHdrFtr ctHdrFtr = new CTHdrFtr() {
//        }
//        headers.get(0).setHeaderFooter(parsHeader);
        //write footer content
//        createOrientationSec(document,"","test");
        createHeader(document,"test","");

//        for (int i = 1; i < 3; i++) {
//            String text = "{{+moudle" + (i + 1) + "}}";
//            createOrientationSec(document,"",text);
//            createOrientationSec(document,"landscape",text);
//        }
        File mainDoc = new File("src/main/resources/poi/word/template/main.docx");
        if(!mainDoc.exists())
            mainDoc.createNewFile();
        BufferedOutputStream fos = new BufferedOutputStream(new FileOutputStream(mainDoc));
        document.write(fos);
        fos.close();
    }

    /**
     * 插入一节，可设置横纵向
     * @param document
     * @param orientation
     * @param text
     */
    private void createOrientationSec(XWPFDocument document,String orientation,String text){
        CTDocument1 doc = document.getDocument();
        XWPFParagraph para = document.createParagraph();
        XWPFRun run = para.createRun();
        run.setText(text);
        run.addCarriageReturn();
        CTP ctp = para.getCTP();
        CTPPr br = ctp.addNewPPr();
        CTSectPr section = br.addNewSectPr();
        br.setSectPr(section);

        CTP ctpFooter = CTP.Factory.newInstance();
        CTR ctrFooter = ctpFooter.addNewR();
        CTText ctFooter = ctrFooter.addNewT();
        String footerText = "This is footer";
        ctFooter.setStringValue(footerText);
        XWPFParagraph footerParagraph = new XWPFParagraph(ctpFooter, document);
        XWPFParagraph[] parsFooter = new XWPFParagraph[1];
        parsFooter[0] = footerParagraph;
        XWPFHeaderFooterPolicy policy = new XWPFHeaderFooterPolicy(document , section);
        policy.createFooter(XWPFHeaderFooterPolicy.DEFAULT, parsFooter);

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

    public static void  createHeader(XWPFDocument doc, String orgFullName, String logoFilePath) throws Exception {
        /*
         * 对页眉段落作处理，使公司logo图片在页眉左边，公司全称在页眉右边
         * */
        CTSectPr sectPr = doc.getDocument().getBody().addNewSectPr();
        XWPFHeaderFooterPolicy headerFooterPolicy = new XWPFHeaderFooterPolicy(doc,     sectPr);
        XWPFHeader header = headerFooterPolicy.createHeader(XWPFHeaderFooterPolicy.DEFAULT);

        XWPFParagraph paragraph = header.getParagraphArray(0);
        paragraph.setAlignment(ParagraphAlignment.LEFT);
        paragraph.setBorderBottom(Borders.THICK);

        CTTabStop tabStop = paragraph.getCTP().getPPr().addNewTabs().addNewTab();
        tabStop.setVal(STTabJc.RIGHT);
        int twipsPerInch =  1440;
        tabStop.setPos(BigInteger.valueOf(6 * twipsPerInch));

        XWPFRun run = paragraph.createRun();
        setXWPFRunStyle(run,"新宋体",10);

        /*
         * 根据公司logo在ftp上的路径获取到公司到图片字节流
         * 添加公司logo到页眉，logo在左边
         * */
//        if (StringUtils.isNotEmptyOrNull(logoFilePath)) {
//            String imgFile = FileUploadUtil.getLogoFilePath(logoFilePath);
//            byte[] bs = FtpUtil.downloadFileToIo(imgFile);
//            InputStream is = new ByteArrayInputStream(bs);
//
//            XWPFPicture picture = run.addPicture(is, XWPFDocument.PICTURE_TYPE_JPEG, imgFile, Units.toEMU(80), Units.toEMU(45));
//
//            String blipID = "";
//            for(XWPFPictureData picturedata : header.getAllPackagePictures()) { //这段必须有，不然打开的logo图片不显示
//                blipID = header.getRelationId(picturedata);
//            }
//            picture.getCTPicture().getBlipFill().getBlip().setEmbed(blipID);
//            run.addTab();
//            is.close();
//        }

        /*
         * 添加字体页眉，公司全称
         * 公司全称在右边
         * */
//        if (StringUtils.isNotEmptyOrNull(orgFullName)) {
            run = paragraph.createRun();
            run.setText(orgFullName);
            setXWPFRunStyle(run,"新宋体",10);
//        }
    }

    /**
     * 设置页脚的字体样式
     *
     * @param r1 段落元素
     */
    private static void setXWPFRunStyle(XWPFRun r1,String font,int fontSize) {
        r1.setFontSize(fontSize);
        CTRPr rpr = r1.getCTR().isSetRPr() ? r1.getCTR().getRPr() : r1.getCTR().addNewRPr();
        CTFonts fonts = rpr.isSetRFonts() ? rpr.getRFonts() : rpr.addNewRFonts();
        fonts.setAscii(font);
        fonts.setEastAsia(font);
        fonts.setHAnsi(font);
    }

}
