/*    */ package com.headhonchos.SolrQuery.subQuery;
/*    */ 
/*    */ import com.headhonchos.Exceptions.NoJobDataException;
/*    */ import com.headhonchos.Exceptions.QueryBuildException;
/*    */ import com.headhonchos.FunctionStat;
/*    */ import com.headhonchos.jobPosting.Function;
/*    */ import com.headhonchos.queryUtil.QueryBuilder;
/*    */ import com.headhonchos.queryUtil.QueryJoiner;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import org.apache.commons.collections.CollectionUtils;
/*    */ import org.slf4j.Logger;
/*    */ import org.slf4j.LoggerFactory;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FunctionQuery
/*    */ {
/* 20 */   private static Logger logger = LoggerFactory.getLogger(FunctionQuery.class);
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public static String getQuery(List<Function> masterFunctionalAreas)
/*    */     throws NoJobDataException, QueryBuildException
/*    */   {
/* 30 */     logger.debug("Input : {}", masterFunctionalAreas);
/*    */     
/* 32 */     if (CollectionUtils.isEmpty(masterFunctionalAreas)) {
/* 33 */       throw new NoJobDataException("masterFunctionAreasList is either Empty or null.");
/*    */     }
/*    */     
/* 36 */     List<String> functionSubQueryList = new ArrayList();
/* 37 */     for (Function function : masterFunctionalAreas) {
/* 38 */       List<String> functionsFungibility = FunctionStat.getfunctionFungibility(function.getName());
/* 39 */       String functionQuery = QueryBuilder.getSimpleQuery("actual_last_js_employments_function", functionsFungibility);
/* 40 */       functionSubQueryList.add(functionQuery);
/*    */     }
/* 42 */     String functionSubQuery = QueryJoiner.or(functionSubQueryList);
/* 43 */     logger.debug("SubQuery : {}", functionSubQuery);
/* 44 */     return functionSubQuery;
/*    */   }
/*    */ }


/* Location:              /home/yatish/Downloads/ClientRcmdWebService.war!/WEB-INF/lib/JsRcmdQuery-1.0.jar!/com/headhonchos/SolrQuery/subQuery/FunctionQuery.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */