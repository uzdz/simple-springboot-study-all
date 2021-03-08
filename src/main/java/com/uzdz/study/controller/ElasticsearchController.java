package com.uzdz.study.controller;

import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author ：uzdz
 */
public class ElasticsearchController {

    @Autowired
    RestHighLevelClient highLevelClient;

    /**
     * @Description 查看索引是否存在
     * @date 2019/11/19 11:07
     */
    @RequestMapping(value = "/checkIndex",method = RequestMethod.GET)
    public Boolean createIndex(@RequestParam("indexName") String indexName) throws Exception {

        return highLevelClient.indices().exists(new GetIndexRequest(indexName), RequestOptions.DEFAULT);
    }
}
