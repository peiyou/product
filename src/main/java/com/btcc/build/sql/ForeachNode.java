package com.btcc.build.sql;

import org.apache.ibatis.scripting.xmltags.ExpressionEvaluator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by peiyou on 17/5/4.
 */
public class ForeachNode implements SqlNode {
    private static final Logger logger = LoggerFactory.getLogger(ForeachNode.class);

    public final static String foreachNode = "_foreach_item_";
    public final static String split = "\\|";
    public final static String beginOperator = "#for";
    public final static String endOperator = "!#for";
    public final static String endCondition = "]";
    public final static String beginCondition = "[";
    private int beginIndex;
    private int endIndex;
    private String text;

    private String index = "index=";
    private String close = "close=";
    private String collection="collection=";
    private String open="open=";
    private String separator="separator=";
    private String item="item=";
    private String classType = "classType=";

    private String sqlItem;
    private String sqlIndex;

    private static int uniqueNumber = 0;
    public ForeachNode(String text, int beginIndex, int endIndex){
        this.text = text;
        this.beginIndex = beginIndex;
        this.endIndex = endIndex;
    }


    @Override
    public void apply(SqlContext sqlContext) {
        sqlContext.sqlBuilder.append(analyse(sqlContext));

    }


    private String analyse(SqlContext context){
        String expression = text.substring(text.indexOf(beginCondition) + 1,text.indexOf(endCondition));

        String[] conditions = expression.split(split);
        String in = "";
        String closeIn = "";
        String collectionIn = "";
        String openIn = "";
        String separatorIn = "";
        String itemIn = "";
        String classTypeIn = "";

        for(int i = 0; i< conditions.length; i++){
            int index_ = conditions[i].indexOf(index);
            if(index_ >= 0){
                if(index_ + index.length() == conditions[i].length()) {
                    logger.error("", "foreach of index value cannot empty!");
                    new NullPointerException("index cannot empty.");
                }
                in = conditions[i].substring(index_ + index.length()).trim();
                continue;
            }
            int open_ = conditions[i].indexOf(open);
            if(open_ >= 0){
                if(open_ + open.length() == conditions[i].length()) {
                    logger.error("", "foreach of open value cannot empty!");
                    new NullPointerException("open cannot empty.");
                }
                openIn = conditions[i].substring(open_ + open.length()).trim();
                continue;
            }
            int close_ = conditions[i].indexOf(close);
            if(close_ >= 0){
                if(close_ + close.length() == conditions[i].length()) {
                    logger.error("", "foreach of close value cannot empty!");
                    new NullPointerException("close cannot empty.");
                }
                closeIn = conditions[i].substring(close_ + close.length()).trim();
                continue;
            }

            int collection_ = conditions[i].indexOf(collection);
            if(collection_ >= 0){
                if(collection_ + collection.length() == conditions[i].length()) {
                    logger.error("", "foreach of collection value cannot empty!");
                    new NullPointerException("collection cannot empty.");
                }
                collectionIn = conditions[i].substring(collection_ + collection.length()).trim();
                continue;
            }

            int separator_ = conditions[i].indexOf(separator);
            if(separator_ >= 0){
                if(separator_ + separator.length() == conditions[i].length()) {
                    logger.error("", "foreach of separator value cannot empty!");
                    new NullPointerException("separator cannot empty.");
                }
                separatorIn = conditions[i].substring(separator_ + separator.length());
                continue;
            }
            int item_ = conditions[i].indexOf(item);
            if(item_ >= 0){
                if(item_ + item.length() == conditions[i].length()) {
                    logger.error("", "foreach of item value cannot empty!");
                    new NullPointerException("item cannot empty.");
                }
                itemIn = conditions[i].substring(item_ + item.length()).trim();
                continue;

            }

            int classType_ = conditions[i].indexOf(classType);
            if(classType_ >= 0){
                if(classType_ + classType.length() == conditions[i].length()) {
                    logger.error("", "foreach of classType value cannot empty!");
                    new NullPointerException("classType cannot empty.");
                }
                classTypeIn = conditions[i].substring(classType_ + classType.length()).trim();
                continue;

            }
        }
        sqlItem = itemIn;
        sqlIndex = in;
        return build(in,openIn,itemIn,separatorIn,closeIn,collectionIn,classTypeIn,context);
    }



    private String build(String in,String open,String item,String separator,String close,String collection,String classType,SqlContext sqlContext){
        ExpressionEvaluator evaluator = new ExpressionEvaluator();
        final Iterable<?> iterable = evaluator.evaluateIterable(collection, sqlContext.paramentMap);
        if (!iterable.iterator().hasNext()) {
            return "";
        }
        boolean first = true;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(open);
        int i = 0;
        for (Object o : iterable) {
            if(first){
                stringBuilder.append("");
            }else{
                stringBuilder.append(separator);
            }

            if(o instanceof Map.Entry){
                Map.Entry<Object, Object> mapEntry = (Map.Entry<Object, Object>) o;
                applyIndex(sqlContext,o,uniqueNumber);
                applyItem(sqlContext, mapEntry.getValue(), uniqueNumber);
            }else{
                applyIndex(sqlContext, i, uniqueNumber);
                applyItem(sqlContext, o, uniqueNumber);
            }
            stringBuilder.append((new ForeachSqlBuild("#{","}",in,item,uniqueNumber)).parse(
                    text.substring(text.indexOf(endCondition) + 1,text.indexOf(endOperator))
                    ));
            uniqueNumber++;
            i++;
            first = false;
        }

        stringBuilder.append(close);
        return stringBuilder.toString();
    }

    private static String itemizeItem(String item, int i) {
        return new StringBuilder(foreachNode).append(item).append("_").append(i).toString();
    }

    private void applyIndex(SqlContext sqlContext, Object o, int i) {
        if (sqlIndex != null) {
            sqlContext.paramentMap.put(sqlIndex, o);
            sqlContext.paramentMap.put(itemizeItem(sqlIndex, i), o);
        }
    }

    private void applyItem(SqlContext sqlContext, Object o, int i) {
        if (sqlItem != null) {
            sqlContext.paramentMap.put(sqlItem, o);
            sqlContext.paramentMap.put(itemizeItem(sqlItem, i), o);
        }
    }

    public int getEndIndex(){
        return endIndex;
    }



    private class ForeachSqlBuild{
        private String openToken;
        private String closeToken;
        private String index;
        private String item;
        private int uniqueNumber;

        public ForeachSqlBuild(String openToken,String closeToken,String index,String item,int uniqueNumber){
            this.openToken = openToken;
            this.closeToken = closeToken;
            this.index = index;
            this.item = item;
            this.uniqueNumber = uniqueNumber;
        }

        private String param(String text){
            String newContent = text.replaceFirst("^\\s*" + item + "(?![^.,:\\s])", itemizeItem(item, uniqueNumber));
            if (index != null && newContent.equals(text)) {
                newContent = text.replaceFirst("^\\s*" + index + "(?![^.,:\\s])", itemizeItem(index, uniqueNumber));
            }
            return new StringBuilder("#{").append(newContent).append("}").toString();
        }

        public String parse(String text) {
            final StringBuilder builder = new StringBuilder();
            final StringBuilder expression = new StringBuilder();
            if (text != null && text.length() > 0) {
                char[] src = text.toCharArray();
                int offset = 0;
                // search open token
                int start = text.indexOf(openToken, offset);
                while (start > -1) {
                    if (start > 0 && src[start - 1] == '\\') {
                        // this open token is escaped. remove the backslash and continue.
                        builder.append(src, offset, start - offset - 1).append(openToken);
                        offset = start + openToken.length();
                    } else {
                        // found open token. let's search close token.
                        expression.setLength(0);
                        builder.append(src, offset, start - offset);
                        offset = start + openToken.length();
                        int end = text.indexOf(closeToken, offset);
                        while (end > -1) {
                            if (end > offset && src[end - 1] == '\\') {
                                // this close token is escaped. remove the backslash and continue.
                                expression.append(src, offset, end - offset - 1).append(closeToken);
                                offset = end + closeToken.length();
                                end = text.indexOf(closeToken, offset);
                            } else {
                                expression.append(src, offset, end - offset);
                                offset = end + closeToken.length();
                                break;
                            }
                        }
                        if (end == -1) {
                            // close token was not found.
                            builder.append(src, start, src.length - start);
                            offset = src.length;
                        } else {
                            builder.append(param(expression.toString()));
                            offset = end + closeToken.length();
                        }
                    }
                    start = text.indexOf(openToken, offset);
                }
                if (offset < src.length) {
                    builder.append(src, offset, src.length - offset);
                }
            }
            return builder.toString();
        }
    }

    public static void main(String[] args) {
        List<Integer> users = new ArrayList<>();
        users.add(21);
        users.add(25);
        users.add(24);
        users.add(26);
        users.add(22);
        users.add(27);
        Map<String,Object> map = new HashMap<>();
        map.put("list",users);
        String sql  = "select * from userdata where 1=1 #if[list != null] user_id in #for[index= index|close= )|collection= list|open= (|separator= ,|item= item|classType= java.lang.String] #{item} !#for  !#if" +
                "#if[list != null] and user_id in  #for[index= index|close= )|collection= list|open= (|separator= ,|item= item|classType= java.lang.String] #{item} !#for  !#if";
        SqlContext sqlContext = new SqlContext(sql,map);
        String result = sqlContext.getSql();
        System.out.println(result);
        for (String key : map.keySet()){
            System.out.printf("parameter : %s,%s",key,map.get(key));
            System.out.println();
        }
    }
}
