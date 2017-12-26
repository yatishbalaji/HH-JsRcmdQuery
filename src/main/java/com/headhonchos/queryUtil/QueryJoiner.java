/*    */ package com.headhonchos.queryUtil;
/*    */ 
/*    */ import com.headhonchos.Exceptions.QueryBuildException;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import org.apache.commons.collections.CollectionUtils;
/*    */ import org.apache.commons.collections.Predicate;
/*    */ import org.apache.commons.collections.Transformer;
/*    */ import org.apache.commons.lang3.StringUtils;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class QueryJoiner
/*    */ {
/*    */   public static final String AND_CONNECTOR = " AND ";
/*    */   public static final String OR_CONNECTOR = " OR ";
/*    */   public static final String NOT_CONNECTOR = "NOT";
/*    */   
/*    */   public static String and(String subquery1, String subquery2)
/*    */   {
/* 23 */     return "(" + subquery1 + ")" + " AND " + "(" + subquery2 + ")";
/*    */   }
/*    */   
/*    */   public static String or(String subquery1, String subquery2) {
/* 27 */     return "(" + subquery1 + ")" + " OR " + "(" + subquery2 + ")";
/*    */   }
/*    */   
/*    */   public static String and(List<String> subQueries) throws QueryBuildException {
/* 31 */     ArrayList<String> copiedList = getFilteredQueries(subQueries);
/* 32 */     return StringUtils.join(copiedList, " AND ");
/*    */   }
/*    */   
/*    */   public static String or(List<String> subQueries) throws QueryBuildException {
/* 36 */     ArrayList<String> copiedList = getFilteredQueries(subQueries);
/* 37 */     return StringUtils.join(copiedList, " OR ");
/*    */   }
/*    */   
/*    */   public static String not(String subquery) {
/* 41 */     return "NOT(" + subquery + ")";
/*    */   }
/*    */   
/*    */   private static ArrayList<String> getFilteredQueries(List<String> subQueries) throws QueryBuildException {
/* 45 */     ArrayList<String> copiedList = new ArrayList(subQueries);
/* 46 */     CollectionUtils.filter(copiedList, new NonEmptyFilter());
/* 47 */     if (copiedList.isEmpty()) {
/* 48 */       throw new QueryBuildException("Sub Queries Empty");
/*    */     }
/* 50 */     CollectionUtils.transform(copiedList, new ParenthesesFormat());
/* 51 */     return copiedList;
/*    */   }
/*    */   
/*    */   private static class NonEmptyFilter implements Predicate
/*    */   {
/*    */     public boolean evaluate(Object object) {
/* 57 */       String query = (String)object;
/*    */       
/* 59 */       boolean isNotEmpty = !query.trim().isEmpty();
/* 60 */       return isNotEmpty;
/*    */     }
/*    */   }
/*    */   
/*    */   private static class ParenthesesFormat implements Transformer
/*    */   {
/*    */     public Object transform(Object input) {
/* 67 */       String subquery = (String)input;
/* 68 */       return "(" + subquery + ")";
/*    */     }
/*    */   }
/*    */ }


/* Location:              /home/yatish/Downloads/ClientRcmdWebService.war!/WEB-INF/lib/JsRcmdQuery-1.0.jar!/com/headhonchos/queryUtil/QueryJoiner.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */