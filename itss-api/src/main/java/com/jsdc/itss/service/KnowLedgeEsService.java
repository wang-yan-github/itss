package com.jsdc.itss.service;

import cn.hutool.core.lang.Console;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.jsdc.core.base.BaseService;
import com.jsdc.itss.common.utils.StringUtils;
import com.jsdc.itss.model.KnowledgeConfig;
import com.jsdc.itss.model.KnowledgeInfo;
import com.jsdc.itss.vo.KnowledgeEsVo;
import com.jsdc.itss.vo.ResultInfo;
import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.json.JsonXContent;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.transform.Result;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @ClassName KnowLedgeEsService
 * @Description TODO ????????????????????????
 * @Author xujian
 * @Date 2022/3/10 11:27
 * @Version 1.0
 */
@Service
@Transactional
public class KnowLedgeEsService {
    @Autowired
    private RestHighLevelClient restHighLevelClient;
    @Autowired
    private KnowledgeConfigService knowledgeConfigService;
    /**
     * ????????????
     */
    public  ResultInfo createIndex() {
        XContentBuilder mappingBuilder = null;
        CreateIndexResponse response = null;
        try {
            CreateIndexRequest request = new CreateIndexRequest("knowledge_info");//????????????
            mappingBuilder = JsonXContent.contentBuilder()
                    .startObject()
                    .startObject("properties")
                    .startObject("id").field("type", "integer").field("index", "true").endObject()
                    .startObject("dept_id").field("type", "integer").field("index", "true").endObject()
                    .startObject("knowledge_category_id").field("type", "integer").field("index", "true").endObject()
                    .startObject("is_full_search").field("type", "integer").field("index", "true").endObject()
                    .startObject("is_recommend").field("type", "integer").field("index", "true").endObject()
//                    .startObject("sort_num").field("type", "integer").field("index", "true").endObject()
                    .startObject("knowledge_range_id").field("type", "integer").field("index", "true").endObject()
                    .startObject("title").field("type", "text").field("index", "true").endObject()
                    .startObject("content").field("type", "text").field("index", "true").field("analyzer", "ik_max_word").endObject()
                    .startObject("keyword").field("type", "text").field("index", "true").endObject()
//                    .startObject("status").field("type", "keyword").field("index", "true").endObject()
                    .endObject()
                    .endObject();
            request.mapping(mappingBuilder);
            Console.log(response);
            response = restHighLevelClient.indices().create(request, RequestOptions.DEFAULT);
            return ResultInfo.success(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResultInfo.error("?????????????????????");
    }

    public ResultInfo deleteIndex() {
        try {
            DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest("knowledge_info");
            AcknowledgedResponse deleteResponse = restHighLevelClient.indices().delete(deleteIndexRequest, RequestOptions.DEFAULT);
            boolean acknowledged = deleteResponse.isAcknowledged();
            Console.log(acknowledged);
            return ResultInfo.success(deleteResponse);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultInfo.error("??????????????????????????????"+e.getMessage());
        }
    }


    /**
     * ????????????????????????
     * @param knowledgeInfo
     * @return
     */
    public ResultInfo addOrUpdateDoc(KnowledgeInfo knowledgeInfo) {
        try {
            XContentBuilder builder = XContentFactory.jsonBuilder();
            builder.startObject();
            {
                builder.field("id", knowledgeInfo.getId());
                builder.field("dept_id", knowledgeInfo.getDept_id());
                builder.field("knowledge_category_id", knowledgeInfo.getKnowledge_category_id());
                builder.field("is_full_search", knowledgeInfo.getIs_full_search());
                builder.field("is_recommend", knowledgeInfo.getIs_recommend());
//                builder.field("sort_num", 1);
                builder.field("knowledge_range_id", knowledgeInfo.getKnowledge_range_id());
                builder.field("title", knowledgeInfo.getTitle());
                builder.field("content", knowledgeInfo.getContent());
                builder.field("keyword", knowledgeInfo.getKeyword());
            }
            builder.endObject();
            IndexRequest request = new IndexRequest("knowledge_info", "_doc", knowledgeInfo.getId().toString()).source(builder);
            IndexResponse index = restHighLevelClient.index(request, RequestOptions.DEFAULT);
            RestStatus status = index.status();
            Console.log(status);
            return ResultInfo.success(index);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultInfo.error("???????????????????????????");
    }


    /**
     *  ??????ES??????????????????
     * @param knowledgeEsVo
     * @return
     */
    public ResultInfo queryDoc(KnowledgeEsVo knowledgeEsVo) {
        JSONObject jsonObject = new JSONObject();
        //????????????
        List<KnowledgeConfig> configs = knowledgeConfigService.selectList(null);
        KnowledgeConfig knowledgeConfig = new KnowledgeConfig(1,1,1);
        if(configs != null && configs.size() >0){
            knowledgeConfig = configs.get(0);
        }
        try {
            //????????????????????????????????????knowledge_info
            SearchRequest request = new SearchRequest("knowledge_info");

            //??????BoolQueryBuilder???????????????????????????????????????
            BoolQueryBuilder booleanQueryBuilder = QueryBuilders.boolQuery();
            //??????
//            booleanQueryBuilder.must(QueryBuilders.termQuery("dept_id", 1));
            booleanQueryBuilder.should(QueryBuilders.matchQuery("content", knowledgeEsVo.getKeyWord()).boost(knowledgeConfig.getDesc_weight()).analyzer("ik_max_word"));
            booleanQueryBuilder.should(QueryBuilders.matchQuery("title", knowledgeEsVo.getKeyWord()).boost(knowledgeConfig.getTitle_weight()));
            booleanQueryBuilder.should(QueryBuilders.matchQuery("keyword", knowledgeEsVo.getKeyWord()).boost(knowledgeConfig.getKeyword_weight()));
            if(knowledgeEsVo.getIs_full_search() != null){
                booleanQueryBuilder.filter(QueryBuilders.termQuery("is_full_search",knowledgeEsVo.getIs_full_search()));
            }
            //??????SearchSourceBuilder???????????????????????????????????????????????????
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            //???Boolean????????????searchSourceBuilder
            searchSourceBuilder.query(booleanQueryBuilder);
            //?????????????????????0???
            searchSourceBuilder.from(knowledgeEsVo.getPageIndex());
            //?????????10???
            searchSourceBuilder.size(knowledgeEsVo.getPageSize());
            //??????????????????????????????????????????????????????????????????true???????????????ES???????????????????????? 10000
            searchSourceBuilder.trackTotalHits(true);
//            searchSourceBuilder.sort("AvgTicketPrice", SortOrder.ASC);
            //????????????
            HighlightBuilder highlightBuilder = new HighlightBuilder();
            highlightBuilder.preTags("<span style=\"color:red\">");
            highlightBuilder.postTags("</span>");
            highlightBuilder.field("title");
            highlightBuilder.field("content");
            searchSourceBuilder.highlighter(highlightBuilder);

            request.source(searchSourceBuilder);
            //********************Part3 ????????????????????????????????? ********************
            // ???????????????????????????????????????
            SearchResponse response = restHighLevelClient.search(request, RequestOptions.DEFAULT);
            //?????????????????????????????????
            SearchHit[] hits = response.getHits().getHits();
            //????????????????????????JSON??????
            //????????????
            List<JSONObject> highLights = new ArrayList<>();
            for(SearchHit hit : response.getHits()) {
                JSONObject highlight = new JSONObject();
                System.out.println(hit.toString());
                Map<String, HighlightField> highlightFields = hit.getHighlightFields();
                JSONObject model = JSONObject.parseObject(hit.getSourceAsString());
                highlight.put("id",model.get("id"));
                if(highlightFields.containsKey("title")){
                    highlight.put("title",highlightFields.get("title").getFragments()[0].toString());
                }else{
                    highlight.put("title",model.get("title"));
                }
                if(highlightFields.containsKey("content")){
                    highlight.put("content",highlightFields.get("content").getFragments()[0].toString());
                }else{
                    highlight.put("content",model.get("content"));
                }
                System.out.println(highlightFields.toString());
                highLights.add(highlight);
//                Map<String, Object> source = hit.getSourceAsMap();
//                //??????????????????
//                Map<String, HighlightField> highlightFields = hit.getHighlightFields();
//                HighlightField nameField = highlightFields.get("content");
//                if (nameField != null) {
//                    Text[] fragments = nameField.fragments();
//                    StringBuilder nameTmp = new StringBuilder();
//                    for (Text text : fragments) {
//                        nameTmp.append(text);
//                    }
//                    //????????????????????????????????????
//                    source.put("content", nameTmp.toString());
//                }
            }
            jsonObject.put("highLights",highLights);
            long count = response.getHits().getTotalHits();
            jsonObject.put("totalCount",count);
            System.out.println("??????????????????????????????" + count);
            return ResultInfo.success(jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultInfo.error("??????");
    }



    /**
     *  ??????ES??????????????????
     * @param knowledgeEsVo
     * @return
     */
    public ResultInfo queryDoc1(KnowledgeEsVo knowledgeEsVo) {
        JSONObject jsonObject = new JSONObject();
        //????????????
        List<KnowledgeConfig> configs = knowledgeConfigService.selectList(null);
        KnowledgeConfig knowledgeConfig = new KnowledgeConfig(1,1,1);
        if(configs != null && configs.size() >0){
            knowledgeConfig = configs.get(0);
        }
        try {
            //????????????????????????????????????knowledge_info
            SearchRequest request = new SearchRequest("knowledge_info");

            //??????BoolQueryBuilder???????????????????????????????????????
            BoolQueryBuilder booleanQueryBuilder = QueryBuilders.boolQuery();
            //??????
//            booleanQueryBuilder.must(QueryBuilders.termQuery("dept_id", 1));
            booleanQueryBuilder.should(QueryBuilders.matchQuery("content", knowledgeEsVo.getKeyWord()).boost(knowledgeConfig.getDesc_weight()).analyzer("ik_max_word"));
            booleanQueryBuilder.should(QueryBuilders.matchQuery("title", knowledgeEsVo.getKeyWord()).boost(knowledgeConfig.getTitle_weight()));
            booleanQueryBuilder.should(QueryBuilders.matchQuery("keyword", knowledgeEsVo.getKeyWord()).boost(knowledgeConfig.getKeyword_weight()));
            if(knowledgeEsVo.getIs_full_search() != null){
                booleanQueryBuilder.filter(QueryBuilders.termQuery("is_full_search",knowledgeEsVo.getIs_full_search()));
            }
            //??????SearchSourceBuilder???????????????????????????????????????????????????
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            //???Boolean????????????searchSourceBuilder
            searchSourceBuilder.query(booleanQueryBuilder);
            //?????????????????????0???
            searchSourceBuilder.from(knowledgeEsVo.getPageIndex());
            //?????????10???
            searchSourceBuilder.size(knowledgeEsVo.getPageSize());
            //??????????????????????????????????????????????????????????????????true???????????????ES???????????????????????? 10000
            searchSourceBuilder.trackTotalHits(true);
//            searchSourceBuilder.sort("AvgTicketPrice", SortOrder.ASC);
            //????????????
            HighlightBuilder highlightBuilder = new HighlightBuilder();
            highlightBuilder.preTags("");
            highlightBuilder.postTags("");
            highlightBuilder.field("title");
            highlightBuilder.field("content");
            searchSourceBuilder.highlighter(highlightBuilder);

            request.source(searchSourceBuilder);
            //********************Part3 ????????????????????????????????? ********************
            // ???????????????????????????????????????
            SearchResponse response = restHighLevelClient.search(request, RequestOptions.DEFAULT);
            //?????????????????????????????????
            SearchHit[] hits = response.getHits().getHits();
            //????????????????????????JSON??????
            //????????????
            List<JSONObject> highLights = new ArrayList<>();
            for(SearchHit hit : response.getHits()) {
                JSONObject highlight = new JSONObject();
                System.out.println(hit.toString());
                Map<String, HighlightField> highlightFields = hit.getHighlightFields();
                JSONObject model = JSONObject.parseObject(hit.getSourceAsString());
                highlight.put("id",model.get("id"));
                if(highlightFields.containsKey("title")){
                    highlight.put("title",highlightFields.get("title").getFragments()[0].toString());
                }else{
                    highlight.put("title",model.get("title"));
                }
                if(highlightFields.containsKey("content")){
                    highlight.put("content",highlightFields.get("content").getFragments()[0].toString());
                }else{
                    highlight.put("content",model.get("content"));
                }
                System.out.println(highlightFields.toString());
                highLights.add(highlight);
//                Map<String, Object> source = hit.getSourceAsMap();
//                //??????????????????
//                Map<String, HighlightField> highlightFields = hit.getHighlightFields();
//                HighlightField nameField = highlightFields.get("content");
//                if (nameField != null) {
//                    Text[] fragments = nameField.fragments();
//                    StringBuilder nameTmp = new StringBuilder();
//                    for (Text text : fragments) {
//                        nameTmp.append(text);
//                    }
//                    //????????????????????????????????????
//                    source.put("content", nameTmp.toString());
//                }
            }
            jsonObject.put("highLights",highLights);
            long count = response.getHits().getTotalHits();
            jsonObject.put("totalCount",count);
            System.out.println("??????????????????????????????" + count);
            return ResultInfo.success(jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultInfo.error("??????");
    }


    public ResultInfo deleteDoc(Integer id) {
        try {
            DeleteRequest deleteRequest = new DeleteRequest("knowledge_info","_doc",id.toString());
            DeleteResponse deleteResponse = restHighLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);
            RestStatus restStatus = deleteResponse .status();
            Console.log(restStatus);
            return ResultInfo.success(deleteResponse);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultInfo.error("???????????????");
    }
//    public static void main(String[] args) {
////        KnowLedgeEsService.createIndex();
////        KnowLedgeEsService.deleteIndex();
////        KnowLedgeEsService.addDoc();
////        KnowLedgeEsService.queryDoc();
////        KnowLedgeEsService.deleteDoc();
//    }
}
