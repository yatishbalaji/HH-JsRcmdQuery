/*    */ package com.headhonchos.SolrQuery.subQuery;
/*    */ 
/*    */ import com.headhonchos.Exceptions.WrongJobDataException;
/*    */ import com.headhonchos.ExperienceStat;
/*    */ import com.headhonchos.queryUtil.QueryBuilder;
/*    */ import org.apache.commons.lang3.tuple.Pair;
/*    */ import org.slf4j.Logger;
/*    */ import org.slf4j.LoggerFactory;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ExperienceQuery
/*    */ {
/* 14 */   private static Logger logger = LoggerFactory.getLogger(ExperienceQuery.class);
/*    */   
/*    */ 
/*    */ 
/*    */   private static final String EXP_FIELD_NAME = "actual_master_year_id";
/*    */   
/*    */ 
/*    */ 
/*    */   public static String getQuery(int minExp, int maxExp)
/*    */     throws WrongJobDataException
/*    */   {
/* 25 */     logger.debug("min exp: {} , maxExp: {}", Integer.valueOf(minExp), Integer.valueOf(maxExp));
/* 26 */     if (minExp > maxExp) {
/* 27 */       throw new WrongJobDataException("MinExp more than MaxExp");
/*    */     }
/*    */     
/* 30 */     Pair<String, String> expRange = ExperienceStat.getExpRange(minExp, maxExp);
/* 31 */     String minExperience = (String)expRange.getLeft();
/* 32 */     String maxExperience = (String)expRange.getRight();
/*    */     
/* 34 */     String experienceSubQuery = QueryBuilder.getRangeQuery("actual_master_year_id", minExperience, maxExperience);
/* 35 */     logger.debug("SubQuery - " + experienceSubQuery);
/* 36 */     return experienceSubQuery;
/*    */   }
/*    */ }


/* Location:              /home/yatish/Downloads/ClientRcmdWebService.war!/WEB-INF/lib/JsRcmdQuery-1.0.jar!/com/headhonchos/SolrQuery/subQuery/ExperienceQuery.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */