package com.uzdz.study.mongodb.domain;

//import org.springframework.data.annotation.Id;
//import org.springframework.data.annotation.Transient;
//import org.springframework.data.mongodb.core.index.IndexDirection;
//import org.springframework.data.mongodb.core.index.Indexed;
//import org.springframework.data.mongodb.core.mapping.Document;
//import org.springframework.data.mongodb.core.mapping.Field;
//
//@Document(collection = "java")
//public class MongoUser {
//
//    @Id
//    private String id;
//
//    private String name;
//
//    //创建单字段索引（默认ASCENDING 升序、DESCENDING 降序）
//    @Indexed(direction = IndexDirection.DESCENDING)
//    private Integer age;
//
//    @Field("user_net_name")
//    private String netName;
//
//    @Transient
//    private String other;
//
//    public String getOther() {
//        return other;
//    }
//
//    public void setOther(String other) {
//        this.other = other;
//    }
//
//    public String getNetName() {
//        return netName;
//    }
//
//    public void setNetName(String netName) {
//        this.netName = netName;
//    }
//
//    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public Integer getAge() {
//        return age;
//    }
//
//    public void setAge(Integer age) {
//        this.age = age;
//    }
//}
