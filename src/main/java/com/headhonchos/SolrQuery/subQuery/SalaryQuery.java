/*    */ package com.headhonchos.SolrQuery.subQuery;
/*    */ 
/*    */ import com.headhonchos.Exceptions.QueryBuildException;
/*    */ import com.headhonchos.Exceptions.WrongJobDataException;
/*    */ import com.headhonchos.SalaryStat;
/*    */ import com.headhonchos.queryUtil.QueryBuilder;
/*    */ import com.headhonchos.queryUtil.QueryJoiner;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Arrays;
/*    */ import java.util.List;
/*    */ import org.apache.commons.lang3.tuple.Pair;
/*    */ import org.slf4j.Logger;
/*    */ import org.slf4j.LoggerFactory;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SalaryQuery
/*    */ {
/* 21 */   private static Logger logger = LoggerFactory.getLogger(SalaryQuery.class);
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public static String getQuery(int minSalary, int maxSalary, boolean flagForeign)
/*    */     throws WrongJobDataException, QueryBuildException
/*    */   {
/* 34 */     logger.debug("minSalary {} , maxSalary {}", Integer.valueOf(minSalary), Integer.valueOf(maxSalary));
/* 35 */     if (minSalary > maxSalary) {
/* 36 */       throw new WrongJobDataException("MinSalary more than MaxSalary");
/*    */     }
/*    */     
/*    */ 
/*    */ 
/* 41 */     Pair<String, String> salaryRange = SalaryStat.getSalaryRangeIndian(minSalary, maxSalary);
/*    */     
/* 43 */     String all_salaries = QueryBuilder.getRangeQuery("all_salaries", (String)salaryRange.getLeft(), (String)salaryRange.getRight());
/* 44 */     String salaryDataIncompleteQuery = QueryBuilder.getSimpleQuery("all_salaries", new ArrayList(Arrays.asList(new String[] { "0" })));
/* 45 */     String salaryQuery = QueryJoiner.or(all_salaries, salaryDataIncompleteQuery).replaceAll("\"", "");
/*    */     String finalQuery;
/* 47 */     String finalQuery; if (flagForeign) {
/* 48 */       List<String> countryName = new ArrayList(Arrays.asList(new String[] { "India" }));
/* 49 */       Pair<String, String> salaryRangeNonIndian = SalaryStat.getSalaryRangeNonIndian(minSalary, maxSalary);
/* 50 */       String all_salaries1 = QueryBuilder.getRangeQuery("all_salaries", (String)salaryRangeNonIndian.getLeft(), (String)salaryRangeNonIndian.getRight());
/* 51 */       String notIndianCountryQuery = QueryJoiner.not(QueryBuilder.getSimpleQuery("actual_js_profiles_country", countryName));
/* 52 */       String notIndianCountryQuery2 = QueryJoiner.not(QueryBuilder.getSimpleQuery("js_profiles_country", countryName));
/* 53 */       String foreignSalary = all_salaries1 + " " + notIndianCountryQuery + " " + notIndianCountryQuery2;
/* 54 */       finalQuery = QueryJoiner.or(salaryQuery, foreignSalary);
/*    */     } else {
/* 56 */       finalQuery = salaryQuery;
/*    */     }
/*    */     
/* 59 */     logger.debug("Query : {}", finalQuery);
/* 60 */     return finalQuery;
/*    */   }
/*    */ }


/* Location:              /home/yatish/Downloads/ClientRcmdWebService.war!/WEB-INF/lib/JsRcmdQuery-1.0.jar!/com/headhonchos/SolrQuery/subQuery/SalaryQuery.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */