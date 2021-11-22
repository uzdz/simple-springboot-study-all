package com.uzdz.study.module.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.uzdz.study.module.enums.EventStatus;
import com.uzdz.study.module.enums.YesNoEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DynamicInsert
@DynamicUpdate
@EntityListeners(AuditingEntityListener.class)
@Table(name = "t_member")
@ApiModel("会员表")
public class Member {

    @Id
    @Column(name = "id", columnDefinition = "bigint COMMENT '主键，自动生成'")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty("自增主键")
    private Long id;

    @Column(name = "one_id", columnDefinition = "VARCHAR(255) NOT NULL COMMENT '多渠道会员唯一OneId'")
    @ApiModelProperty("多渠道会员唯一OneId")
    private String oneId;

    @Column(name = "channel", columnDefinition = "varchar(50) NOT NULL comment '平台渠道码'")
    @ApiModelProperty("平台渠道码")
    private String channel;

    @Column(name = "channel_union_id", columnDefinition = "varchar(50) NOT NULL comment '平台渠道用户唯一Id'")
    @ApiModelProperty("平台渠道用户唯一Id")
    private String channelUnionId;

    @Column(name = "avatar", columnDefinition = "varchar(200) NOT NULL default '' comment '会员头像'")
    @ApiModelProperty("会员头像")
    private String avatar;

    @Column(name = "mobile", columnDefinition = "varchar(20) NOT NULL default '' comment '会员手机号'")
    @ApiModelProperty("会员手机号")
    private String mobile;

    @Column(name = "real_name", columnDefinition = "varchar(50) NOT NULL default '' comment '会员真实姓名'")
    @ApiModelProperty("会员真实姓名")
    private String realName;

    @Column(name = "nickname", columnDefinition = "varchar(50) NOT NULL default '' comment '会员昵称'")
    @ApiModelProperty("会员昵称")
    private String nickname;

    @Column(name = "birthday", columnDefinition = "date COMMENT '会员生日'")
    @ApiModelProperty("会员生日")
    private LocalDate birthday;

    @Column(name = "gender", columnDefinition = "int NOT NULL default 0 comment '性别 0:女 1:男'")
    @ApiModelProperty("性别；0:女；1:男")
    private Integer gender;

    @Column(name = "share_role", columnDefinition = "int NOT NULL default 0 COMMENT '分销员角色；0：未设置；1：内部分销员；2：外部分销员'")
    @ApiModelProperty("分销员角色；0：未设置；1：内部分销员；2：外部分销员")
    private Integer shareRole;

//    @Column(name = "role", columnDefinition = "int NOT NULL default 0 COMMENT '0：普通会员（无手机号）；1：普通会员（有手机号）；2：元气会员（付费会员）；3：元气会员Pro（超级付费会员）'")
//    @ApiModelProperty("0：普通会员（无手机号）；1：普通会员（有手机号）；2：元气会员（付费会员）；3：元气会员Pro（超级付费会员）")
//    private Integer role;

    @Column(name = "id_card", columnDefinition = "varchar(20) NOT NULL default '' comment '会员身份证号'")
    @ApiModelProperty("会员身份证号")
    private String idCard;

    @Column(name = "country", columnDefinition = "varchar(80) NOT NULL default '' comment '会员地理位置 - 国家'")
    @ApiModelProperty("会员地理位置 - 国家")
    private String country;

    @Column(name = "province", columnDefinition = "varchar(80) NOT NULL default '' comment '会员地理位置 - 省'")
    @ApiModelProperty("会员地理位置 - 省")
    private String province;

    @Column(name = "city", columnDefinition = "varchar(80) NOT NULL default '' comment '会员地理位置 - 城市'")
    @ApiModelProperty("会员地理位置 - 城市")
    private String city;

    @Column(name = "county", columnDefinition = "varchar(80) NOT NULL default '' comment '会员地理位置 - 县'")
    @ApiModelProperty("会员地理位置 - 县")
    private String county;

    @Column(name = "longitude", columnDefinition = "decimal(10,7) COMMENT '会员地理位置 - 经度'")
    @ApiModelProperty("会员地理位置 - 经度")
    private BigDecimal longitude;

    @Column(name = "latitude", columnDefinition = "decimal(10,7) COMMENT '会员地理位置 - 纬度'")
    @ApiModelProperty("会员地理位置 - 纬度")
    private BigDecimal latitude;

    @Column(name = "password", columnDefinition = "varchar(50) comment '会员密码（可选）'")
    @ApiModelProperty("会员密码（可选）")
    private String password;

    @Column(name = "private_share_code", columnDefinition = "varchar(128) comment '分销人分享码（可以解码得到userId和memberId）'")
    @ApiModelProperty("分销人分享码（可以解码得到userId和memberId）")
    private String privateShareCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "identity_status", columnDefinition = "varchar(50) NOT NULL default 'NEW' comment '会员状态；NEW:新用户；Other:老用户'")
    @ApiModelProperty("会员状态；NEW:新用户；Other:老用户")
    private EventStatus identityStatus;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(10) NOT NULL default 'NO' comment '会员状态；N:启用；Y:禁用'")
    @ApiModelProperty("会员状态；N:启用；Y:禁用")
    private YesNoEnum status;

    @Column(name = "remark", columnDefinition = "varchar(200) NOT NULL default '' comment '会员备注'")
    @ApiModelProperty("备注")
    private String remark;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "last_login_time", columnDefinition = "TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP comment '会员最后登录时间'")
    @ApiModelProperty("会员最后登录时间")
    private LocalDateTime lastLoginTime;

    @Column(name = "last_login_ip", columnDefinition = "varchar(20) NOT NULL default '' null comment '最后登录IP'")
    @ApiModelProperty("会员最后登录IP")
    private String lastLoginIp;

    @LastModifiedDate
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "modify_time", columnDefinition = "TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP comment '会员最近修改时间'")
    @ApiModelProperty("会员最近修改时间")
    private LocalDateTime modifyTime;

    @CreatedDate
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "create_time", columnDefinition = "TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP comment '会员创建时间'")
    @ApiModelProperty("会员创建时间")
    private LocalDateTime createTime;

    @Transient
    private LocalDateTime startModifyTime;

    @Transient
    private LocalDateTime endModifyTime;

    @Transient
    private LocalDateTime startCreateTime;

    @Transient
    private LocalDateTime endCreateTime;
}