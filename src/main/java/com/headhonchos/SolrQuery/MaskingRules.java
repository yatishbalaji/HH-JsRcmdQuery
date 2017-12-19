/*    */ package com.headhonchos.SolrQuery;
/*    */ 
/*    */ import com.headhonchos.Exceptions.QueryBuildException;
/*    */ import com.headhonchos.queryUtil.QueryJoiner;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ public class MaskingRules
/*    */ {
/*    */   public static String getQuery(String companyID)
/*    */     throws QueryBuildException
/*    */   {
/* 14 */     List<String> rulesList = new ArrayList();
/* 15 */     rulesList.add("mask_complete_profile:1");
/* 16 */     rulesList.add("blocked_compaines:" + companyID);
/* 17 */     rulesList.add("current_company:" + companyID);
/*    */     
/* 19 */     return QueryJoiner.not(QueryJoiner.or(rulesList));
/*    */   }
/*    */ }


/* Location:              /home/yatish/Downloads/ClientRcmdWebService.war!/WEB-INF/lib/JsRcmdQuery-1.0.jar!/com/headhonchos/SolrQuery/MaskingRules.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */