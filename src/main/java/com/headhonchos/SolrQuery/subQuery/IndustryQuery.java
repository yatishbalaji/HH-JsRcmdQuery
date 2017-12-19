/*    */ package com.headhonchos.SolrQuery.subQuery;
/*    */ 
/*    */ import com.headhonchos.Exceptions.NoJobDataException;
/*    */ import com.headhonchos.Exceptions.QueryBuildException;
/*    */ import com.headhonchos.IndustryStat;
/*    */ import com.headhonchos.jobPosting.Industry;
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
/*    */ 
/*    */ public class IndustryQuery
/*    */ {
/* 21 */   private static Logger logger = LoggerFactory.getLogger(IndustryQuery.class);
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public static String getQuery(List<Industry> masterIndustryAreas)
/*    */     throws NoJobDataException, QueryBuildException
/*    */   {
/* 31 */     logger.debug("Industry : {}", masterIndustryAreas);
/*    */     
/* 33 */     if (CollectionUtils.isEmpty(masterIndustryAreas)) {
/* 34 */       throw new NoJobDataException("masterIndustryAreasList is either Empty or null.");
/*    */     }
/*    */     
/* 37 */     List<String> industrySubQueryList = new ArrayList();
/* 38 */     for (Industry industry : masterIndustryAreas) {
/* 39 */       List<String> industryFungibility = IndustryStat.getIndustryFungibility(industry.getName());
/* 40 */       String industryQuery = QueryBuilder.getSimpleQuery("actual_last_js_employments_industry", industryFungibility);
/* 41 */       industrySubQueryList.add(industryQuery);
/*    */     }
/*    */     
/* 44 */     String industrySubQuery = QueryJoiner.or(industrySubQueryList);
/* 45 */     logger.debug("SubQuery : {}", industrySubQuery);
/* 46 */     return industrySubQuery;
/*    */   }
/*    */ }


/* Location:              /home/yatish/Downloads/ClientRcmdWebService.war!/WEB-INF/lib/JsRcmdQuery-1.0.jar!/com/headhonchos/SolrQuery/subQuery/IndustryQuery.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */