package com.uzdz.study.jpa.utils;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class SqlHelper {

    public static int countBySql(EntityManager entityManager, String sql, List<Object> params) {
        Query query = entityManager.createNativeQuery(sql);
        setParams(query, params);
        Object c = query.getSingleResult();
        return c == null ? 0 : (c instanceof Long ? ((Long) c).intValue() : (c instanceof BigInteger ? ((BigInteger) c).intValue() : ((BigDecimal) c).intValue()));
    }

    public static List<Map<String, Object>> listBySql(EntityManager entityManager, String sql, List<Object> params) {
        Query query = entityManager.createNativeQuery(sql);
        setParams(query, params);
        return query.getResultList();
    }

    public static List<Object[]> listBySql(EntityManager entityManager, String sql, Map<String, Object> params) {
        Query query = entityManager.createNativeQuery(sql);
        setParams(query, params);
        return query.getResultList();
    }

    public static List<Map<String, Object>> listBySql(EntityManager entityManager, String sql, List<Object> params, int offset, int size) {
        Query query = entityManager.createNativeQuery(sql);
        setParams(query, params);
        query.setFirstResult(offset);
        query.setMaxResults(size);
        return query.getResultList();
    }

    public static int count(EntityManager entityManager, String sql, List<Object> params) {
        Query query = entityManager.createQuery(sql);
        setParams(query, params);
        Object c = query.getSingleResult();
        return c == null ? 0 : (c instanceof Long ? ((Long) c).intValue() : ((BigDecimal) c).intValue());
    }

    public static int countNative(EntityManager entityManager, String sql, List<Object> params) {
        Query query = entityManager.createNativeQuery(sql);
        setParams(query, params);
        Object c = query.getSingleResult();
        return c == null ? 0 : (c instanceof Long ? ((Long) c).intValue() : ((BigInteger) c).intValue());
    }

    public static <E> List<E> list(EntityManager entityManager, String sql, List<Object> params) {
        Query query = entityManager.createQuery(sql);
        setParams(query, params);
        return query.getResultList();
    }

    public static <E> List<E> list(EntityManager entityManager, String sql, List<Object> params, int offset, int size) {
        Query query = entityManager.createQuery(sql);
        setParams(query, params);
        query.setFirstResult(offset);
        query.setMaxResults(size);
        return query.getResultList();
    }

    public static <E> List<E> listNative(EntityManager entityManager, String sql, List<Object> params, int offset, int size, Class clazz) {
        Query query = entityManager.createNativeQuery(sql, clazz);
        setParams(query, params);
        query.setFirstResult(offset);
        query.setMaxResults(size);

        return query.getResultList();
    }

    public static int updateBySql(EntityManager entityManager, String sql, List<Object> params) {
        Query query = entityManager.createNativeQuery(sql);
        setParams(query, params);
        return query.executeUpdate();
    }

    private static void setParams(Query query, List<Object> params) {
        Iterator<Object> iter = params.iterator();
        for (int i = 1, c = params.size(); i <= c; ++i) {
            query.setParameter(i, iter.next());
        }
    }

    private static void setParams(Query query, Map<String, Object> params) {
        if (params != null) {
            for (String key : params.keySet()) {
                query.setParameter(key, params.get(key));
            }
        }
    }
}