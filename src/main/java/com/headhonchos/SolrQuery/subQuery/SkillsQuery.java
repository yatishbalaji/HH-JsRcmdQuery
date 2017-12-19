/*    */ package com.headhonchos.SolrQuery.subQuery;
/*    */ 
/*    */ import com.headhonchos.Exceptions.QueryBuildException;
/*    */ import com.headhonchos.queryUtil.QueryBuilder;
/*    */ import com.headhonchos.skill.EnrichedSkill;
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import java.util.Map.Entry;
/*    */ import org.slf4j.Logger;
/*    */ import org.slf4j.LoggerFactory;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SkillsQuery
/*    */ {
/* 18 */   public static final Logger logger = LoggerFactory.getLogger(SkillsQuery.class);
/*    */   private static final String SKILL_FIELD_NAME = "skills";
/*    */   
/*    */   public static String getQuery(List<EnrichedSkill> systemSkills, List<EnrichedSkill> manualSkills) throws QueryBuildException {
/* 22 */     logger.debug("System Skills: {}", systemSkills);
/* 23 */     logger.debug("Keywords: {}", manualSkills);
/*    */     
/* 25 */     Map<String, Double> weightedSkillsList = new HashMap();
/*    */     
/* 27 */     for (EnrichedSkill enrichedskill : systemSkills) {
/* 28 */       weightedSkillsList.put(enrichedskill.getName(), Double.valueOf(enrichedskill.getWeight()));
/*    */     }
/*    */     
/* 31 */     Double maxvalue = Double.valueOf(7.0D);
/* 32 */     Double minvalue = Double.valueOf(100000.0D);
/* 33 */     for (Entry<String, Double> entry : weightedSkillsList.entrySet()) {
/* 34 */       if (((Double)entry.getValue()).doubleValue() > maxvalue.doubleValue()) {
/* 35 */         maxvalue = (Double)entry.getValue();
/*    */       }
/* 37 */       if (((Double)entry.getValue()).doubleValue() < minvalue.doubleValue()) {
/* 38 */         minvalue = (Double)entry.getValue();
/*    */       }
/*    */     }
/*    */     
/* 42 */     Double manualSkillsWeight = Double.valueOf(minvalue.doubleValue() + maxvalue.doubleValue());
/* 43 */     for (EnrichedSkill enrichedskill : manualSkills) {
/* 44 */       weightedSkillsList.put(enrichedskill.getName(), manualSkillsWeight);
/*    */     }
/* 46 */     String weightedSkillQuery = QueryBuilder.getWeightedQuery("skills", weightedSkillsList);
/* 47 */     logger.debug("Query : {}", weightedSkillQuery);
/* 48 */     return weightedSkillQuery;
/*    */   }
/*    */ }


/* Location:              /home/yatish/Downloads/ClientRcmdWebService.war!/WEB-INF/lib/JsRcmdQuery-1.0.jar!/com/headhonchos/SolrQuery/subQuery/SkillsQuery.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */