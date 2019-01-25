package top.itlq.tools.poitlword.policy;

import com.deepoove.poi.data.RowRenderData;
import com.deepoove.poi.data.TextRenderData;
import com.deepoove.poi.policy.DynamicTableRenderPolicy;
import com.deepoove.poi.policy.MiniTableRenderPolicy;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

import java.util.ArrayList;

public class TableStyleTestPolicy extends DynamicTableRenderPolicy {
    @Override
    public void render(XWPFTable table, Object data) {
        for(int i=0;i<5;i++){
            RowRenderData rowData = getTestRowData(i);
            XWPFTableRow row = table.insertNewTableRow(1);
            row.setHeight(500);
            for (int j = 0; j < 7; j++) {
                XWPFTableCell cell = row.createCell();
                cell.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
            }
            MiniTableRenderPolicy.renderRow(table,1,rowData);
        }
//        table.removeRow(0);
    }

    private RowRenderData getTestRowData(int i){
        RowRenderData re = new RowRenderData();
        re.setRowData(new ArrayList<TextRenderData>(){{
            for(int j=0;j<7;j++){
                add(new TextRenderData(String.valueOf(i) + String.valueOf(j)));
            }
        }});
        return re;
    }
}
