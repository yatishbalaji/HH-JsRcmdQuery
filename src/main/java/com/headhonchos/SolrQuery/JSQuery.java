/*    */ package com.headhonchos.SolrQuery;
/*    */ 
/*    */ import javax.xml.bind.annotation.XmlRootElement;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @XmlRootElement
/*    */ public class JSQuery
/*    */ {
/*    */   String query;
/*    */   
/*    */   public String getQuery()
/*    */   {
/* 15 */     return this.query;
/*    */   }
/*    */   
/*    */   public void setQuery(String query) {
/* 19 */     this.query = query;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public String toString()
/*    */   {
/* 26 */     return "{\"query\":\"" + this.query + "\"" + "}";
/*    */   }
/*    */ }


/* Location:              /home/yatish/Downloads/ClientRcmdWebService.war!/WEB-INF/lib/JsRcmdQuery-1.0.jar!/com/headhonchos/SolrQuery/JSQuery.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */