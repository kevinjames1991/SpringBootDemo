package com.mySBoot.thread.controller;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;

public class SolrServerClient {

	private String SOLR_ADMIN_URL = "http://118.178.128.200:8983/solr";
	private static HttpSolrClient server = null;
	private volatile static SolrServerClient solrServiceClient = null;

	private SolrServerClient() {
		this.getServer();
	}

	/**
	 * SolrServerClient 是线程安全的 需要采用单例模式 此处实现方法适用于高频率调用查询
	 * 
	 * @return SolrServerClient
	 */
	public static SolrServerClient getInstance() {
		if (solrServiceClient == null) {
			synchronized (SolrServerClient.class) {
				if (solrServiceClient == null) {
					solrServiceClient = new SolrServerClient();
				}
			}
		}
		return solrServiceClient;
	}

	/**
	 * 初始化的HttpSolrServer 对象,并获取此唯一对象 配置按需调整
	 * 
	 * @return 此server对象
	 */
	private HttpSolrClient getServer() {
		if (server == null) {
			server = new HttpSolrClient(SOLR_ADMIN_URL);
			server.setConnectionTimeout(3000);
			server.setDefaultMaxConnectionsPerHost(100);
			server.setMaxTotalConnections(100);
		}
		return server;
	}
	
	public static void main(String[] args) throws Exception {
        SolrQuery query = new SolrQuery("*:*");
        query.addFilterQuery("status:1");
        query.setRows(2);
        String[] params = new String[] { "id", "brandName" };
        query.setFields(params);
		QueryResponse response = SolrServerClient.getInstance().getServer().query("brand",query);
		System.out.println(response.getResults());
	}
}
