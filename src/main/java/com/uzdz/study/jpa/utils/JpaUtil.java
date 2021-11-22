package com.uzdz.study.jpa.utils;


import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.query.QueryUtils;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

//import com.yq.commons.exceptions.ExceptionFactory;

/**
 * @program:
 * @author: captain.ma
 * @date: 2018-11-20
 * @since: 1.0.0.0
 */
@Transactional(readOnly = true)
@Repository
@Slf4j
public class JpaUtil {
    @PersistenceContext
    @Getter
    private EntityManager em;


    /**
     * 获取分页数据
     *
     * @param sql
     * @param params
     * @param pageable
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> Page<T> page(String sql, Map<String, Object> params, Pageable pageable, boolean nativeQuery, String resultMapping) {
        log.info("sql :: {}, params {}", sql, JSON.toJSONString(params));

        Query query = parameters(nativeQuery ? resultMapping.isEmpty() ? em.createNativeQuery(sql) : em.createNativeQuery(sql, resultMapping) : em.createQuery(sql), params);
        return page(sql,params,pageable,query,nativeQuery);
    }


    private  <T> Page<T> page(String sql, Map<String, Object> params, Pageable pageable, Query query, boolean nativeQuery) {
        log.debug("sql :: {}, params {}", sql, JSON.toJSONString(params));

        if (pageable != null && pageable.isPaged()) {
            //分页
            query.setFirstResult((int) pageable.getOffset());
            query.setMaxResults(pageable.getPageSize());
        } else {
            return new PageImpl<>(query.getResultList());
        }

        /**
         * 生成获取总数的sql
         */
        if (!nativeQuery) {
            TypedQuery<Long> cQuery = em.createQuery(QueryUtils.createCountQueryFor(sql), Long.class);
            return PageableExecutionUtils.getPage(query.getResultList(), pageable, () -> executeCountQuery(parameters(cQuery, sql, params)));
        }

        String sql2 = QueryUtils.createCountQueryFor(sql);

        String countsql = "select count(1)  " + sql2.substring(sql2.indexOf("from"));
        Query countQuery = em.createNativeQuery(countsql);
        parameters(countQuery, countsql, params);


        return PageableExecutionUtils.getPage(query.getResultList(), pageable, () -> executeCountQuery(countQuery));
    }

    public Page<Map> pageMap(String sql, Map<String, Object> params, Pageable pageable, boolean nativeQuery) {
        Query query = parameters(nativeQuery ? em.createNativeQuery(sql, Map.class) : em.createQuery(sql, Map.class), params);
        return page(sql, params, pageable, query, nativeQuery);
    }

    public List<Map> map(String sql, Map<String, Object> params, boolean nativeQuery) {
        log.info("map:::{}-{}", sql, JSON.toJSON(params));

        Query query = parameters(nativeQuery ? em.createNativeQuery(sql, Map.class) : em.createQuery(sql, Map.class), params);
        return query.getResultList();
    }

    private Query parameters(Query query, String sql, Map<String, Object> params) {
        if (params != null) {
            for (String key : params.keySet()) {
                if (sql.contains(key)) {
                    query.setParameter(key, params.get(key));
                }

            }
        }
        return query;
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> findAll(String sql, Map<String, Object> params) {

        Query q = em.createQuery(sql);
        if (params != null) {
            for (String key : params.keySet()) {
                q.setParameter(key, params.get(key));
            }
        }
        return q.getResultList();
    }

    private static <T extends Query> T parameters(T query, Map<String, Object> params) {
        if (params != null) {
            for (String key : params.keySet()) {
                query.setParameter(key, params.get(key));
            }
        }
        return query;
    }


    @javax.transaction.Transactional
    public void executeSql(QueryVO queryVO, boolean nativeQuery) {
        String sql = queryVO.getQuery();
        Map<String, Object> resultMapping = queryVO.getParameters();
        Query query = parameters(nativeQuery ? em.createNativeQuery(sql) : em.createQuery(sql), resultMapping);
        query.executeUpdate();
    }

    /**
     * Executes a count query and transparently sums up all values returned.
     *
     * @param query must not be {@literal null}.
     * @return
     */
    private static Long executeCountQuery(Query query) {
        Assert.notNull(query, "TypedQuery must not be null!");
        Object result = query.getSingleResult();
        if (result instanceof BigInteger) {
            return ((BigInteger) result).longValue();
        } else
            return (Long) query.getSingleResult();
    }

    public Page page(String formatSql, Map<String, Object> distanceParams, Pageable pageable, boolean nativeq) {
        return page(formatSql, distanceParams, pageable, nativeq, "");
    }

    public Page pageHql(String formatSql, Map<String, Object> distanceParams, Pageable pageable) {
        return page(formatSql, distanceParams, pageable, false, "");
    }

    public <T> T findOne(String hql, Map<String, Object> params) {
        Query query = em.createQuery(hql);
        parameters(query, params);
        return (T) query.getSingleResult();
    }


}