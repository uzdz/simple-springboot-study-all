package com.uzdz.study.jpa.utils;

import com.uzdz.study.jpa.utils.dto.Condition;
import com.uzdz.study.jpa.utils.dto.Range;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.convert.QueryByExamplePredicateBuilder;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.*;

/**
 * SQL拼接工具类
 */
public class QueryUtil {

    private final Specification spec;

    private QueryUtil(Specification spec) {
        this.spec = spec;
    }

    public static QueryUtil where(Specification spec) {
        return new QueryUtil(spec);
    }

    @SuppressWarnings("unchecked")
    public QueryUtil and(Specification other) {
        this.spec.and(other);
        return this;
    }


    @SuppressWarnings("unchecked")
    public QueryUtil or(Specification other) {
        this.spec.or(other);
        return this;
    }

    public Specification build() {
        return this.spec;
    }

    /**
     * 单where条件
     *
     * @param p
     * @return
     */
    public static Specification where(Condition p) {
        List<Condition> ps = new ArrayList<>();
        ps.add(p);
        return where(ps);
    }

//    public static Specification groupBy(Specification specification,GroupBy groupBy){
//        return (root, query, cb) ->
//                cb.count(root.get(groupBy.getField()))
//    }

    /**
     * 多where条件and连接
     *
     * @param ps
     * @param <T>
     * @return
     */
    public static <T> Specification<T> where(List<Condition> ps) {
        return (Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) ->
                builder.and(getPredicateList(root, builder, ps));
    }

    /**
     * 多where条件or连接
     *
     * @param ps
     * @param <T>
     * @return
     */
    public static <T> Specification<T> or(List<Condition> ps) {
        return (Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) ->
                builder.or(getPredicateList(root, builder, ps));
    }



    /**
     * 获取查询条件数组
     *
     * @param root
     * @param builder
     * @param ps
     * @return
     */
    private static Predicate[] getPredicateList(Root<?> root, CriteriaBuilder builder, List<Condition> ps) {
        List<Predicate> predicateList = new ArrayList<>();
        ps.forEach(p -> {
            Predicate predicate = buildPredicate(builder, root, p);
            predicateList.add(predicate);
        });
        return predicateList.toArray(new Predicate[predicateList.size()]);
    }

    /**
     * 选取查询方式
     *
     * @param cb
     * @param p
     * @return
     */
    private static Predicate buildPredicate(CriteriaBuilder cb, Root<?> root, Condition p) {
        String fieldName = p.getName();
        Path path;
        if (fieldName.contains(".")) {
            String[] names = StringUtils.split(fieldName, ".");
            //获取该属性的类型，Set？List？Map？
            path = root.get(names[0]);
            Class clazz = path.getJavaType();
            if (clazz.equals(Set.class)) {
                SetJoin setJoin = root.joinSet(names[0]);
                path = setJoin.get(names[1]);
            } else if (clazz.equals(List.class)) {
                ListJoin listJoin = root.joinList(names[0]);
                path = listJoin.get(names[1]);
            } else if (clazz.equals(Map.class)) {
                MapJoin mapJoin = root.joinMap(names[0]);
                path = mapJoin.get(names[1]);
            } else {
                //是many to one时
                path = path.get(names[1]);
            }

        } else {
            //单表查询
            path = root.get(fieldName);
        }
//        Path path=root.get(p.getName());
        Predicate predicate;
        switch (p.getOperator()) {
            case LIKE:
                predicate = cb.like(path, "%" + p.getValue().toString() + "%");
                break;
            case NOTLIKE:
                predicate = cb.notLike(path, "%" + p.getValue().toString() + "%");
                break;
            case EQ:
                predicate = cb.equal(path, p.getValue());
                break;
            case NOTEQ:
                predicate = cb.notEqual(path, p.getValue());
                break;
            case GT:
                predicate = cb.greaterThan(path, (Comparable) p.getValue());
                break;
            case GTEQ:
                predicate = cb.greaterThanOrEqualTo(path, (Comparable) p.getValue());
                break;
            case LT:
                predicate = cb.lessThan(path, (Comparable) p.getValue());
                break;
            case LTEQ:
                predicate = cb.lessThanOrEqualTo(path, (Comparable) p.getValue());
                break;
            case NULL:
                predicate = cb.isNull(path);
                break;
            case NOTNULL:
                predicate = cb.isNotNull(path);
                break;
            case IN:
                predicate = getIn(path, p.getValue());
                break;
            case NOTIN:
                predicate = getIn(path, p.getValue()).not();
                break;
            default:
                throw new IllegalArgumentException("非法的操作符");
        }
        return predicate;
    }

    /**
     * 创建in操作
     *
     * @param path
     * @param value
     * @param <T>
     * @return
     */
    private static <T> Predicate getIn(Path path, T value) {
        if (value instanceof Object[]) {
            return path.in((Object[]) value);
        } else if (value instanceof Collection) {
            return path.in((Collection) value);
        } else {
            throw new IllegalArgumentException("非法的IN操作");
        }
    }

    /***********************************************单where条件查询********************************************************/

    // like
    public static Specification like(String name, String value) {
        return (root, query, cb) ->
                cb.like(root.get(name), value);
    }

    // notlike
    public static Specification notLike(String name, String value) {
        return (root, query, cb) ->
                cb.like(root.get(name), value).not();
    }

    // =
    public static Specification eq(String name, Object value) {
        return (root, query, cb) ->
                cb.equal(root.get(name), value);
    }

    // !=
    public static Specification notEq(String name, Object value) {
        return (root, query, cb) ->
                cb.notEqual(root.get(name), value);
    }

    // >
    public static Specification gt(String name, Object value) {
        return (root, query, cb) ->
                cb.greaterThan(root.get(name), (Comparable) value);
    }

    // >=
    public static Specification gtEq(String name, Object value) {
        return (root, query, cb) ->
                cb.greaterThanOrEqualTo(root.get(name), (Comparable) value);
    }

    // <
    public static Specification lt(String name, Object value) {
        return (root, query, cb) ->
                cb.lessThan(root.get(name), (Comparable) value);
    }

    // <=
    public static Specification ltEq(String name, Object value) {
        return (root, query, cb) ->
                cb.lessThanOrEqualTo(root.get(name), (Comparable) value);
    }

    // is null
    public static Specification isNull(String name) {
        return (root, query, cb) ->
                cb.isNull(root.get(name));
    }

    // is not null
    public static Specification notNull(String name) {
        return (root, query, cb) ->
                cb.isNotNull(root.get(name));
    }

    // in
    public static Specification in(String name, Object... value) {
        return (root, query, cb) ->
                root.get(name).in(value);
    }

    public static Specification in(String name, Collection value) {
        return (root, query, cb) ->
                root.get(name).in(value);
    }
    // not in
    public static Specification notIn(String name, Object value) {
        return (root, query, cb) ->
                root.get(name).in(value).not();
    }

//    public static <T> Specification exampleRange(Example<T> example, List<Range<T>> ranges) {
//        Specification<T> byExample = new ByExampleSpecification<T>(example);
//        Specification<T> byRanges = new ByRangeSpecification<T>(ranges);
//        return Specification.where(byExample).and(byRanges);
//    }

    public static <T> Specification<T> example(Example<T> example) {
        return (root, query, cb) -> QueryByExamplePredicateBuilder.getPredicate(root, cb, example);
    }

    public static <T> Specification<T> range(Range range) {

        return (root, query, cb) -> cb.between(root.get(range.getField()), range.getFrom(), range.getTo());
    }

    public static <T> QueryUtilBuilder builder(Specification<T> specification){
        return new QueryUtilBuilder(specification);
    }
    public static class QueryUtilBuilder {
        final QueryUtil queryUtil;

        public <T> QueryUtilBuilder(Specification<T> specification) {
            this.queryUtil = new QueryUtil(specification);
        }

        public <T> QueryUtil and(Specification<T> specification) {
            return this.queryUtil.and(specification);
        }

        public <T> QueryUtil or(Specification<T> specification) {
            return this.queryUtil.or(specification);
        }
        public Specification build(){
            return this.queryUtil.spec;
        }

    }

//    public List<Game> fetchAllFor(Platform platform) {
//        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
//        CriteriaQuery<Game> query = criteriaBuilder.createQuery(Game.class);
//
//        Root<Game> fromGames = query.from(Game.class);
//        Join<Game, Platform> platformsOfGame = fromGames.join(Game_.supportedPlatforms);
//        Predicate givenPlatfromIsAssigned = criteriaBuilder.equal(platformsOfGame.get(Platform_.name), platform.getName());
//
//        CriteriaQuery<Game> allGamesForGivenPlatfrom = query.select(fromGames)
//                .where(givenPlatfromIsAssigned);
//        return em.createQuery(allGamesForGivenPlatfrom).getResultList();
//    }
}