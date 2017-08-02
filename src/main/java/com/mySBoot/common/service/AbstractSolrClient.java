package com.mySBoot.common.service;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrRequest;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.Group;
import org.apache.solr.client.solrj.response.GroupCommand;
import org.apache.solr.client.solrj.response.GroupResponse;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.GroupParams;
import org.apache.solr.common.params.SolrParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mySBoot.common.util.ReflectUtil;
import com.mySBoot.common.util.SolrBeanUtil;
import com.mySBoot.common.vo.Page;

@Service
public abstract class AbstractSolrClient<T extends Serializable> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractSolrClient.class);

    protected Class<T> clazz;
    
    private static final String SOLR_ADMIN_URL = "http://118.178.128.200:8983/solr";

    private static HttpSolrClient solrServerClient = null;
	static {
		solrServerClient = new HttpSolrClient(SOLR_ADMIN_URL);
		solrServerClient.setConnectionTimeout(3000);
		solrServerClient.setDefaultMaxConnectionsPerHost(100);
		solrServerClient.setMaxTotalConnections(100);
	}
    
    public AbstractSolrClient() {
        this.clazz = ReflectUtil.getClassGenricType(getClass());
    }

    protected abstract String getCollectionName();

    protected T convert(SolrDocument solrDoc) {
        return SolrBeanUtil.getBean(clazz, solrDoc);
    }

    protected <T> List<T> convert(SolrDocumentList solrDocumentList, Class clazz) {
        return SolrBeanUtil.getBeans(clazz, solrDocumentList);
    }

    protected QueryResponse query(SolrParams query) {
        try {
            return solrServerClient.query(getCollectionName(), query, SolrRequest.METHOD.POST);
        } catch (SolrServerException e) {
            LOGGER.error(query.toString(), e);
        } catch (IOException e) {
            LOGGER.error(query.toString(), e);
        }
        return null;
    }

    public <E> List<E> queryList(SolrParams query, Class<E> clazz) {
        QueryResponse response = query(query);
        if (response != null) {
            if ("true".equals(query.get(GroupParams.GROUP))) {
                GroupResponse groupResponse = response.getGroupResponse();
                if (groupResponse != null) {
                    List<GroupCommand> groupCommands = groupResponse.getValues();
                    GroupCommand groupCommand = groupCommands.get(0);
                    List<Group> groups = groupCommand.getValues();
                    List<E> convert = Lists.newArrayList();
                    for (Group group : groups) {
                        SolrDocument doc = group.getResult().get(0);
                        E e = SolrBeanUtil.getBean(clazz, doc);
                        convert.add(e);
                    }
                    return convert;
                }
            } else if (response.getResults() != null) {
                List<E> convert = convert(response.getResults(), clazz);
                return convert;
            }
        }
        return Collections.emptyList();
    }
    
    
    public <E> List<List<E>> queryGroupCommandList(SolrQuery query, Class<E> clazz) {
        QueryResponse response = query(query);
        if (response != null) {
            if ("true".equals(query.get(GroupParams.GROUP))) {
                GroupResponse groupResponse = response.getGroupResponse();
                if (groupResponse != null) {
                    List<GroupCommand> groupCommands = groupResponse.getValues();
                    List<List<E>> groupList = Lists.newArrayList();
                    for(GroupCommand groupCommand : groupCommands){
                         List<Group> groups = groupCommand.getValues();
                         List<E> convert = Lists.newArrayList();
                         for (Group group : groups) {
                    	 	for(SolrDocument doc : group.getResult()){
                                E e = SolrBeanUtil.getBean(clazz, doc);
                                convert.add(e);
                    	 	}
                         }
                         groupList.add(convert);
                    }
                    return groupList;
                }
            } 
        }
        return Collections.emptyList();
    }
    
    public  Map<String,List<String>> queryGroupValueList(SolrQuery query) {
        QueryResponse response = query(query);
        if (response != null) {
            if ("true".equals(query.get(GroupParams.GROUP))) {
                GroupResponse groupResponse = response.getGroupResponse();
                if (groupResponse != null) {
                    List<GroupCommand> groupCommands = groupResponse.getValues();
                    Map<String,List<String>> groupList = Maps.newHashMap();
                    for(GroupCommand groupCommand : groupCommands){
                         List<Group> groups = groupCommand.getValues();
                         List<String> convert = Lists.newArrayList();
                         for (Group group : groups) {
                        	 String groupValue = group.getGroupValue();
                        	 if(StringUtils.isNoneBlank(groupValue)){
                        		 convert.add(groupValue);
                        	 }else{
                        		 if(groups.size() > 1){
                        			 convert.add("其它");
                        		 }
                        	 }
                         }
                         if(!convert.isEmpty()){
                        	 groupList.put(groupCommand.getName(), convert);
                         }
                         
                    }
                    return groupList;
                }
            } 
        }
        return Maps.newHashMap();
    }
    
    

    public <E> Page<E> queryPage(SolrQuery query, Page<E> page, Class<E> classType) {
        page = initPage(query, page);
        QueryResponse response = query(query);
        if (response != null) {
            page.setRows(new ArrayList<E>());
            if ("true".equals(query.get(GroupParams.GROUP))) {
                GroupResponse groupResponse = response.getGroupResponse();
                if (groupResponse != null) {
                    List<GroupCommand> groupCommands = groupResponse
                            .getValues();
                    GroupCommand groupCommand = groupCommands.get(0);
                    List<Group> groups = groupCommand.getValues();
                    for (Group group : groups) {
                        SolrDocument doc = group.getResult().get(0);
                        E e = SolrBeanUtil.getBean(classType, doc);
                        page.getRows().add(e);
                    }
                    page.setTotal(groupCommand.getNGroups());
                }
            } else if (response.getResults() != null) {
                List<E> convert = convert(response.getResults(), classType);
                page.setRows(convert);
                page.setTotal(response.getResults().getNumFound());
                return page;
            }
        }
        return page;
    }
    
	public <E> Page<E> queryPage(SolrQuery query, Page<E> page, Class<E> classType,String spu,List<Map<String,String>> conditions) {
        page = initPage(query, page);
        QueryResponse response = query(query);
        if (response != null) {
            page.setRows(new ArrayList<E>());
            if ("true".equals(query.get(GroupParams.GROUP))) {
                GroupResponse groupResponse = response.getGroupResponse();
                if (groupResponse != null) {
                    List<GroupCommand> groupCommands = groupResponse
                            .getValues();
                    GroupCommand groupCommand = groupCommands.get(0);
                    List<Group> groups = groupCommand.getValues();
                    Map<String,SolrDocument> groupMap = Maps.newHashMap();
                    List<String> groupList = Lists.newArrayList();
                    for (Group group : groups) {
                    	groupList.addAll(group.getResult().stream().map(p->{
                    		  SolrDocument doc = p;
                             // E e = SolrBeanUtil.getBean(classType, doc);
                              String key = (String) doc.get(spu);
                              if(CollectionUtils.isNotEmpty(conditions)){
                            	  int index = 0;
                            	  if(null != conditions.get(index).get("spu")){
                            		  key = key + "_" + doc.get(conditions.get(index).get("spu"));
                            		  index ++;
                            	  }
	                              if(null != groupMap.get(key)){
	                            	  SolrDocument currentDoc = groupMap.get(key);
                            		  for(;index< conditions.size(); index++){
                            			  for(Map.Entry<String, String> entry : conditions.get(index).entrySet()){
                            				  if(currentDoc.get(entry.getKey()) != null && doc.get(entry.getKey()) != null){
                            					  switch(entry.getValue()){
	                              				  	case "desc":
	                              				  		Integer currentValue = Integer.parseInt(String.valueOf(currentDoc.get(entry.getKey())));
	                              				  		Integer value = Integer.parseInt(String.valueOf(doc.get(entry.getKey())));
	                              				  		if(value > currentValue){
	                              				  			groupMap.put(key, doc);
	                              				  		}
	                              				  		break;
	                              				  	case "asc":
	                              				  		currentValue = Integer.parseInt(String.valueOf(currentDoc.get(entry.getKey())));
	                              				  		value = Integer.parseInt(String.valueOf(doc.get(entry.getKey())));
	                              				  		if(value > currentValue){
	                              				  			groupMap.put(key, currentDoc);
	                              				  		}
	                              				  		break;
                              				  		default:
                              				  			
                              				  			break;
                            					  }
                            				  }
                            			  }
                            		  }
	                              }else{
	                            	  groupMap.put(key, doc);
	                              }
                              }
                              return key;
                    	}).distinct().collect(Collectors.toList()));
                    	
                    	
                    	
                    }
                    for(int i =0 ;i<groupList.size();i++){
                    	E e = SolrBeanUtil.getBean(classType, groupMap.get(groupList.get(i)));
                		page.getRows().add(e);
                    }

                    page.setTotal(groupCommand.getNGroups());
                }
            } else if (response.getResults() != null) {
                List<E> convert = convert(response.getResults(), classType);
                page.setRows(convert);
                page.setTotal(response.getResults().getNumFound());
                return page;
            }
        }
        return page;
    }

    private <E> Page<E> initPage(SolrQuery query, Page<E> page) {
        if (page != null) {
            query.setRows(page.getPageSize());
            query.setStart(page.getOffset());
        } else {
            Assert.notNull(query.getRows());
            Assert.notNull(query.getStart());
            page = new Page<>();
            page.setRows(new ArrayList<E>());
            page.setPageSize(query.getRows());
            page.setPageNo(query.getStart() / page.getPageSize() + 1);
        }
        return page;
    }

}
