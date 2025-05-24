package com.fruit.entity;


import java.io.Serializable;
import java.util.Date;
import lombok.Data;

@Data

/**
* 存储水果详细信息的表，包含其生活化属性和趣味知识
* @TableName fruits
*/
public class Fruits implements Serializable {

    /**
    * 唯一标识符，水果ID
    */

    private Integer id;
    /**
    * 水果名称，例如“苹果”、“香蕉”
    */

    private String name;
    /**
    * 水果的简要描述
    */

    private String description;
    /**
    * 简要营养概览，例如“富含维生素C和膳食纤维”
    */

    private String nutritionSummary;
    /**
    * 口味特征，例如“酸甜”、“清爽”、“醇厚”
    */

    private String flavorProfile;
    /**
    * 水果图片的URL地址
    */

    private String imageUrl;
    /**
    * 应季信息，例如“春季”、“夏季”、“全年”
    */

    private String seasonInfo;
    /**
    * 挑选水果的技巧，例如“颜色鲜亮，表皮无斑点”
    */

    private String selectionTips;
    /**
    * 水果的储存方法，例如“常温保存，避免阳光直射”
    */

    private String storageTips;
    /**
    * 食用禁忌或不宜搭配的食物，例如“不宜与海鲜同食”
    */

    private String eatingTaboos;
    /**
    * 水果的文化寓意或历史背景，例如“苹果在中国文化中象征平安”
    */

    private String culturalSignificance;
    /**
    * 生活属性标签，JSON数组存储，例如 ["解暑", "清热", "适合甜点", "促进食欲"]
    */
    private Object lifeProperties;

    private Date createdAt;
    /**
    * 记录最后更新时间
    */

    private Date updatedAt;



}
