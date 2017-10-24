package com.btcc.build.sql;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by peiyou on 2016/11/18.
 */
public class SqlContext {

    public StringBuilder sqlBuilder;
    public Map<String,Object> paramentMap;
    List<SqlNode> sqlNodes;
    private String oldSqlString;

    public SqlContext(String sqlString,Map<String,Object> paramentMap){
        oldSqlString = sqlString;
        this.paramentMap = paramentMap;
        sqlBuilder = new StringBuilder();
    }

    public SqlContext(String sqlString){
        oldSqlString = sqlString;
        sqlBuilder = new StringBuilder();
    }


    public String getSql(){
        //解析sql
        sqlNodes = analyseSql();
        Iterator<SqlNode> iterator = sqlNodes.iterator();
        while(iterator.hasNext()){
            iterator.next().apply(this);
        }

        while(analyse(0).getEndIndex() != sqlBuilder.toString().length()){
            oldSqlString = sqlBuilder.toString();
            sqlBuilder = new StringBuilder();
            getSql();
        }
        return sqlBuilder.toString();

    }

    private List<SqlNode> analyseSql(){
        List<SqlNode> sqlNodes1 = new ArrayList<>();
        int num = 0;
        while(num < oldSqlString.length()){
            SqlNode sqlNode = analyse(num);
            num = sqlNode.getEndIndex();
            sqlNodes1.add(sqlNode);
        }
        return sqlNodes1;
    }

    /**
     * 将sql解析成SqlNode对象
     * @param beginIndex
     * @return
     */
    private SqlNode analyse(int beginIndex){
        //IF
        int newBeginIndex = oldSqlString.indexOf(IFSqlNode.beginOperator,beginIndex);
        int newEndIndex = oldSqlString.indexOf(IFSqlNode.endOperator,beginIndex);
        //FOREACH
        int newBeginForeach = oldSqlString.indexOf(ForeachNode.beginOperator,beginIndex);
        int newEndForeach = oldSqlString.indexOf(ForeachNode.endOperator,beginIndex);

        if(newBeginIndex != -1 && (newBeginForeach== -1||newBeginIndex < newBeginForeach)){
            //IF
            if(beginIndex < newBeginIndex){
                return new TextNode(oldSqlString.substring(beginIndex,newBeginIndex),beginIndex,newBeginIndex);
            }
            if(newEndIndex != -1)
                return this.ifNode(newBeginIndex,newEndIndex);
        }else{
            //For
            if(beginIndex < newBeginForeach){
                return new TextNode(oldSqlString.substring(beginIndex,newBeginForeach),beginIndex,newBeginForeach);
            }
            if(newEndForeach != -1)
                return this.forNode(newBeginForeach,newEndForeach);
        }
        return new TextNode(oldSqlString.substring(beginIndex),beginIndex,oldSqlString.length());
    }

    private String firstSql(){
        int newBeginIndex = oldSqlString.indexOf(IFSqlNode.beginOperator);
        if(newBeginIndex == -1){
            return oldSqlString;
        }else{
            return oldSqlString.substring(0,newBeginIndex);
        }
    }


    private SqlNode ifNode(int newBeginIndex,int newEndIndex){
        if (newEndIndex <= newBeginIndex) {
            new RuntimeException("analysis SQL error，please validate expression！SQL:" + oldSqlString);
        }
        String text = oldSqlString.substring(newBeginIndex, newEndIndex + IFSqlNode.endOperator.length());
        return new IFSqlNode(text,newBeginIndex,newEndIndex + IFSqlNode.endOperator.length());
    }

    private SqlNode forNode(int newBeginIndex,int newEndIndex){
        if (newEndIndex <= newBeginIndex) {
            new RuntimeException("analysis SQL error，please validate expression！SQL:" + oldSqlString);
        }
        String text = oldSqlString.substring(newBeginIndex, newEndIndex + ForeachNode.endOperator.length());
        return new ForeachNode(text,newBeginIndex,newEndIndex + ForeachNode.endOperator.length());
    }
}
