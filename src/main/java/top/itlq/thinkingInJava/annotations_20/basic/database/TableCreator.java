package top.itlq.thinkingInJava.annotations_20.basic.database;

import org.junit.Test;

import java.lang.annotation.Annotation;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class TableCreator {
    public String resolveClassTableSql(Class<?> cl){
        DBTable dbTable = cl.getAnnotation(DBTable.class);
        if(dbTable == null){
            throw new RuntimeException("No DBTable annotations in class" + cl.getName());
        }
        String tableName = dbTable.name().length() > 0 ? dbTable.name() : cl.getSimpleName().toUpperCase();
        Field[] fields = cl.getDeclaredFields();
        List<String> columnRefs = new ArrayList<String>();
        for(Field field:fields){
            Annotation[] annotations = field.getDeclaredAnnotations();
            if(annotations.length < 1){
                continue;
            }
            if(annotations[0] instanceof SQLInteger){
                SQLInteger sqlInteger = (SQLInteger) annotations[0];
                columnRefs.add(sqlInteger.name().length() > 0?sqlInteger.name():field.getName().toUpperCase() + " INT" +
                        getConstrains(sqlInteger.constraints()));
            }
            if(annotations[0] instanceof SQLString){
                SQLString sqlString = (SQLString) annotations[0];
                columnRefs.add((sqlString.name().length() > 0?sqlString.name():field.getName().toUpperCase()) +
                        " VARCHAR(" + sqlString.value() + ")" +
                        getConstrains(sqlString.constraints()));
            }
        }
        if(columnRefs.size() == 0){
            throw new RuntimeException("No SQLField in class" + cl.getName());
        }
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE ").append(tableName).append(" (\r\n    ");
        for(int i=0;i<columnRefs.size();i++){
            sb.append(columnRefs.get(i));
            if(i < columnRefs.size() - 1)
            sb.append(",\r\n    ");
        }
        sb.append(")");
        return sb.toString();
    }

    private String getConstrains(Constraints constraints){
        StringBuilder sb = new StringBuilder();
        if(constraints.notNull())
            sb.append(" NOT NULL");
        if(constraints.primaryKey())
            sb.append(" PRIMARY KEY");
        if(constraints.unique())
            sb.append(" UNIQUE");
        return sb.toString();
    }

    @Test
    public void test(){
        System.out.println(resolveClassTableSql(Member.class));
    }
}
