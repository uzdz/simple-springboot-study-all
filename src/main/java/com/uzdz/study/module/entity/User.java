package com.uzdz.study.module.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "my_user")
@org.hibernate.annotations.Table(appliesTo = "my_user",comment = "用户表")
public class User {
    /**
     * 主键id
     */
    @Id
    @Column(name = "ids", columnDefinition = "bigint COMMENT '主键，自动生成'")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 名称
     */
    @Column(name = "name", columnDefinition = "varchar(255) NOT NULL COMMENT '参与拉新活动id'")
    private String name;

    @Column(name = "likes", columnDefinition = "varchar(255) NOT NULL COMMENT '喜欢'")
    private String like;

    /**
     * 创建时间
     */
    @Column(name = "`create_time`")
    private LocalDateTime createTime;

}