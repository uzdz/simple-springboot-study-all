//package com.uzdz.study.jpa;
//
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.Table;
//import java.math.BigDecimal;
//
///**
// * {@link } for .
// *
// * @author uzdz
// * @date: 2019/6/11 14:06
// * @since 0.1.0
// */
//@Data
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
//@Entity
//@Table(name = "basic_city")
//@org.hibernate.annotations.Table(appliesTo = "basic_city", comment = "城市基本信息")
//public class CityDO {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "CITY_ID", length = 12)
//    private Integer cityId;
//
//    @Column(name = "CITY_NAME_CN", columnDefinition = "VARCHAR(255) NOT NULL COMMENT '名称（中文）'")
//    private String cityNameCN;
//
//    @Column(name = "CITY_NAME_EN", columnDefinition = "VARCHAR(255) NOT NULL COMMENT '名称（英文）'")
//    private String cityNameEN;
//
//    @Column(name = "LONGITUDE", precision = 10, scale = 7)
//    private BigDecimal longitude;
//
//    @Column(name = "LATITUDE", precision = 10, scale = 7)
//    private BigDecimal latitude;
//
//    @Column(name = "ELEVATION", precision = 5)
//    private Integer elevation;
//
//    @Column(name = "CITY_DESCRIPTION", length = 500)
//    private String cityDescription;
//
//
//}
