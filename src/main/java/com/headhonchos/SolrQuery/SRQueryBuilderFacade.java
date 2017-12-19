/*     */ package com.headhonchos.SolrQuery;
/*     */ 
/*     */ import com.headhonchos.Exceptions.NoJobDataException;
/*     */ import com.headhonchos.Exceptions.QueryBuildException;
/*     */ import com.headhonchos.Exceptions.WrongJobDataException;
/*     */ import com.headhonchos.SolrQuery.subQuery.ExperienceQuery;
/*     */ import com.headhonchos.SolrQuery.subQuery.FunctionQuery;
/*     */ import com.headhonchos.SolrQuery.subQuery.IndustryQuery;
/*     */ import com.headhonchos.SolrQuery.subQuery.LocationQuery;
/*     */ import com.headhonchos.SolrQuery.subQuery.SalaryQuery;
/*     */ import com.headhonchos.SolrQuery.subQuery.SkillsQuery;
/*     */ import com.headhonchos.jobPosting.Function;
/*     */ import com.headhonchos.jobPosting.Industry;
/*     */ import com.headhonchos.jobPosting.Job;
/*     */ import com.headhonchos.jobPosting.Location;
/*     */ import com.headhonchos.queryUtil.QueryJoiner;
/*     */ import com.headhonchos.skill.EnrichedSkill;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ 
/*     */ public class SRQueryBuilderFacade
/*     */ {
/*  25 */   private static final Logger logger = LoggerFactory.getLogger(SRQueryBuilderFacade.class);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static final JSQuery buildQuery(Job job)
/*     */   {
/*  36 */     logger.debug("Building Query.", job);
/*  37 */     List<String> subQueries = new ArrayList();
/*     */     
/*     */ 
/*  40 */     List<EnrichedSkill> systemSkills = job.getSystemSkills();
/*  41 */     List<EnrichedSkill> manualSkills = job.getManualskills();
/*     */     try {
/*  43 */       String skillQuery = SkillsQuery.getQuery(systemSkills, manualSkills);
/*  44 */       subQueries.add(skillQuery);
/*     */     } catch (QueryBuildException e) {
/*  46 */       logger.warn("Error in Building Skill Query", e);
/*     */     }
/*     */     
/*     */ 
/*  50 */     List<Function> master_functional_areas = job.getMasterFunctionalAreas();
/*     */     try {
/*  52 */       String functionQuery = FunctionQuery.getQuery(master_functional_areas);
/*  53 */       subQueries.add(functionQuery);
/*     */     } catch (QueryBuildException e) {
/*  55 */       logger.warn("Error in Building Function Query", e);
/*     */     } catch (NoJobDataException e) {
/*  57 */       logger.warn("Error in Building Function Query", e);
/*     */     }
/*     */     
/*     */ 
/*  61 */     List<Industry> master_industries = job.getMasterIndustries();
/*     */     try {
/*  63 */       String industryQuery = IndustryQuery.getQuery(master_industries);
/*  64 */       subQueries.add(industryQuery);
/*     */     } catch (QueryBuildException e) {
/*  66 */       logger.warn("Error in Building Industry Query", e);
/*     */     } catch (NoJobDataException e) {
/*  68 */       logger.warn("Error in Building Industry Query", e);
/*     */     }
/*     */     
/*     */ 
/*  72 */     int maxExperience = job.getMaxExperience();
/*  73 */     int minExperience = job.getMinExperience();
/*     */     try {
/*  75 */       String experienceQuery = ExperienceQuery.getQuery(minExperience, maxExperience);
/*  76 */       subQueries.add(experienceQuery);
/*     */     } catch (WrongJobDataException e) {
/*  78 */       logger.warn("Error in Building Experience Query", e);
/*     */     }
/*     */     
/*     */ 
/*  82 */     int maxSalary = job.getMaxSalary();
/*  83 */     int minSalary = job.getMinSalary();
/*  84 */     boolean locationPreference = job.isLocationPreference();
/*     */     try {
/*  86 */       String salaryQuery = SalaryQuery.getQuery(minSalary, maxSalary, locationPreference);
/*  87 */       subQueries.add(salaryQuery);
/*     */     } catch (WrongJobDataException e) {
/*  89 */       logger.warn("Error in Building Salary Query", e);
/*     */     } catch (QueryBuildException e) {
/*  91 */       logger.warn("Error in Building Salary Query", e);
/*     */     }
/*     */     
/*     */ 
/*  95 */     List<Location> locationList = job.getLocations();
/*     */     try {
/*  97 */       String locationQuery = LocationQuery.getQuery(locationList, true);
/*  98 */       subQueries.add(locationQuery);
/*     */     } catch (NoJobDataException e) {
/* 100 */       logger.warn("Error in Building Location Query", e);
/*     */     } catch (QueryBuildException e) {
/* 102 */       logger.warn("Error in Building Location Query", e);
/*     */     }
/*     */     
/*     */ 
/* 106 */     String finalQuery = null;
/*     */     try {
/* 108 */       finalQuery = QueryJoiner.and(subQueries);
/*     */     } catch (QueryBuildException e) {
/* 110 */       e.printStackTrace();
/*     */     }
/* 112 */     JSQuery jsQuery = new JSQuery();
/* 113 */     jsQuery.setQuery(finalQuery);
/*     */     
/* 115 */     return jsQuery;
/*     */   }
/*     */ }


/* Location:              /home/yatish/Downloads/ClientRcmdWebService.war!/WEB-INF/lib/JsRcmdQuery-1.0.jar!/com/headhonchos/SolrQuery/SRQueryBuilderFacade.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */