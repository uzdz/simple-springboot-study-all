package com.uzdz.study.module.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
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
import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "t_channel_member")
@Data
@org.hibernate.annotations.Table(appliesTo = "t_channel_member", comment = "渠道会员")
@EntityListeners(AuditingEntityListener.class)
@ApiModel("渠道会员表")
public class ChannelMember {

    @Id
    @Column(name = "id", columnDefinition = "bigint COMMENT '主键，自动生成'")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty("自增主键")
    private Long id;

    @Column(name = "member_id", columnDefinition = "bigint NOT NULL COMMENT '会员主键Id'")
    @ApiModelProperty("会员主键Id")
    private Long memberId;

    @Column(name = "channel", columnDefinition = "varchar(50) NOT NULL comment '平台渠道码'")
    @ApiModelProperty("平台渠道码")
    private String channel;

    @Column(name = "channel_union_id", columnDefinition = "varchar(50) NOT NULL comment '平台渠道用户唯一Id'")
    @ApiModelProperty("平台渠道用户唯一Id")
    private String channelUnionId;

    @Column(name = "app_id", columnDefinition = "varchar(80) NOT NULL comment '应用渠道码'")
    @ApiModelProperty("应用渠道码")
    private String appId;

    @Column(name = "open_id", columnDefinition = "varchar(50) NOT NULL comment '应用渠道用户唯一Id'")
    @ApiModelProperty("应用渠道用户唯一Id")
    private String openId;

    @Column(name = "remark", columnDefinition = "varchar(200) NOT NULL default '' comment '渠道会员备注'")
    @ApiModelProperty("备注")
    private String remark;

    @LastModifiedDate
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "modify_time", columnDefinition = "TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP comment '渠道会员最近修改时间'")
    @ApiModelProperty("渠道会员最近修改时间")
    private LocalDateTime modifyTime;

    @CreatedDate
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "create_time", columnDefinition = "TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP comment '渠道会员创建时间'")
    @ApiModelProperty("渠道会员创建时间")
    private LocalDateTime createTime;

    @Column(name = "share_code", columnDefinition = "varchar(128) comment '渠道追踪码（旧）'")
    @ApiModelProperty("渠道追踪码（旧）")
    private String shareCode;

    @Column(name = "mc", columnDefinition = "varchar(128) comment '渠道追踪码（新）'")
    @ApiModelProperty("渠道追踪码（新）")
    private String mc;

    @Column(name = "attention_wxmp", length = 1, columnDefinition = "bit(1) default 1 comment '微信渠道 - 是否关注当前公众号（服务号/订阅号）'")
    @ApiModelProperty("微信渠道 - 是否关注当前公众号（服务号/订阅号）")
    private Boolean attentionWxmp;

    @Column(name = "scene", columnDefinition = "varchar(128) comment '微信渠道 - 打开小程序的场景编码'")
    @ApiModelProperty(value = "微信渠道 - 打开小程序的场景编码")
    private String scene;

    @Column(name = "gdt_vid", columnDefinition = "varchar(128) comment '微信渠道 - 营销广告id'")
    @ApiModelProperty("微信渠道 - 营销广告id")
    private String gdtVid;

    @Column(name = "subscribe_time", columnDefinition = "bigint comment '微信渠道 - 用户关注时间，时间戳。如果用户曾多次关注，则取最后关注时间'")
    @ApiModelProperty("微信渠道 - 用户关注时间，时间戳。如果用户曾多次关注，则取最后关注时间")
    private Long subscribeTime;

    @Column(name = "subscribe_scene", columnDefinition = "varchar(128) comment '微信渠道 - 用户关注的渠道来源'")
    @ApiModelProperty("微信渠道 - 用户关注的渠道来源")
    private String subscribeScene;

    @Column(name = "qr_scene", columnDefinition = "varchar(128) comment '微信渠道 - 二维码扫码场景（开发者自定义）'")
    @ApiModelProperty("微信渠道 - 二维码扫码场景（开发者自定义）")
    private String qrScene;

    @Column(name = "qr_scene_str", columnDefinition = "varchar(128) comment '微信渠道 - 二维码扫码场景描述（开发者自定义）'")
    @ApiModelProperty("微信渠道 - 二维码扫码场景描述（开发者自定义）")
    private String qrSceneStr;

    @Transient
    private Long userId;

    @Transient
    private String sid;

    @Transient
    private String storeName;

    @Transient
    private String sourceOpenId;

    @Transient
    private String membershipNumber;

    @Transient
    private String roles;

    @Transient
    private String verifyCode;

    @Transient
    private Long ts;

    @Transient
    private String ds;

    @Transient
    private String aType;

    @Transient
    private String currentEvent;

    @Transient
    LocalDateTime startCreateTime;

    @Transient
    LocalDateTime endCreateTime;

    @Transient
    private boolean newer;
}