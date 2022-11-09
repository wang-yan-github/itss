package com.jsdc.itss.common.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpHost;
import org.elasticsearch.client.Node;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @ClassName ElasticSearchConfig
 * @Description TODO
 * @Author xujian
 * @Date 2022/3/12 9:40
 * @Version 1.0
 */
@Configuration
public class ElasticSearchConfig {
    private static final Log logger = LogFactory.getLog(ElasticSearchConfig.class);
    @Value("${elasticsearch.host}")
    private String host;
    @Value("${elasticsearch.port}")
    private Integer port;


    @Bean
    public RestHighLevelClient initClient(){
        logger.info("ElasticSearch客户端初始化开始。。。");
        RestHighLevelClient client  = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost(host, port, "http")
                ).setFailureListener(new RestClient.FailureListener(){
                    @Override
                    public void onFailure(Node node) {
                        logger.error("ElasticSearch客户端初始化失败："+node.getHost());
                    }
                }));
        logger.info("ElasticSearch客户端初始化成功");
        return client;
    }
}
