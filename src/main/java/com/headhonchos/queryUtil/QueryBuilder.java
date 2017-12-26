/*    */ package com.headhonchos.queryUtil;
/*    */ 
/*    */ import com.headhonchos.Exceptions.QueryBuildException;
/*    */ import java.util.ArrayList;
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import java.util.Map.Entry;
/*    */ import org.apache.commons.collections.CollectionUtils;
/*    */ import org.apache.commons.collections.Predicate;
/*    */ import org.slf4j.Logger;
/*    */ import org.slf4j.LoggerFactory;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class QueryBuilder
/*    */ {
/*    */   static final String whiteSpace = " ";
/*    */   static final String doubleQuotes = "\"";
/* 20 */   static final Logger logger = LoggerFactory.getLogger(QueryBuilder.class);
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public static String getSimpleQuery(String fieldName, List<String> terms)
/*    */     throws QueryBuildException
/*    */   {
/* 31 */     ArrayList<String> newArrayList = new ArrayList(terms);
/* 32 */     CollectionUtils.filter(newArrayList, new NonEmptyFilter());
/*    */     
/*    */ 
/* 35 */     if (CollectionUtils.isEmpty(newArrayList)) {
/* 36 */       logger.error("No query terms to build query");
/* 37 */       throw new QueryBuildException("No query terms to build query");
/*    */     }
/*    */     
/*    */ 
/* 41 */     if (newArrayList.get(0) == "*") {
/* 42 */       return fieldName + ":(*)";
/*    */     }
/*    */     
/*    */ 
/* 46 */     StringBuilder sb = new StringBuilder();
/* 47 */     for (String value : newArrayList) {
/* 48 */       sb.append("\"").append(value).append("\"").append(" ");
/*    */     }
/* 50 */     String queryTerms = sb.toString().trim();
/*    */     
/* 52 */     return fieldName + ":(" + queryTerms + ")";
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public static String getWeightedQuery(String fieldName, Map<String, Double> boostedTerms)
/*    */     throws QueryBuildException
/*    */   {
/* 64 */     Map<String, Double> tempBoostedTerms = new HashMap(boostedTerms);
/* 65 */     tempBoostedTerms.remove(" ");tempBoostedTerms.remove("");
/* 66 */     if (tempBoostedTerms.isEmpty()) {
/* 67 */       throw new QueryBuildException("boosted terms empty.");
/*    */     }
/*    */     
/* 70 */     StringBuilder skillQueryBuilder = new StringBuilder();
/* 71 */     for (Entry<String, Double> entry : tempBoostedTerms.entrySet()) {
/* 72 */       String key = (String)entry.getKey();
/* 73 */       Double value = (Double)entry.getValue();
/* 74 */       skillQueryBuilder.append("\"").append(key).append("\"").append("^").append(value).append(" ");
/*    */     }
/* 76 */     String skills = skillQueryBuilder.toString().trim();
/* 77 */     return fieldName + ":(" + skills + ")";
/*    */   }
/*    */   
/*    */   public static String getRangeQuery(String fieldName, String min, String max) {
/* 81 */     return fieldName + ":[" + min + " TO " + max + "]";
/*    */   }
/*    */   
/*    */   private static class NonEmptyFilter implements Predicate
/*    */   {
/*    */     public boolean evaluate(Object object) {
/* 87 */       String value = (String)object;
/*    */       
/* 89 */       boolean isNotEmpty = !value.trim().isEmpty();
/* 90 */       return isNotEmpty;
/*    */     }
/*    */   }
/*    */ }


/* Location:              /home/yatish/Downloads/ClientRcmdWebService.war!/WEB-INF/lib/JsRcmdQuery-1.0.jar!/com/headhonchos/queryUtil/QueryBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */