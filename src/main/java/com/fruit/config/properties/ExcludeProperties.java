package com.fruit.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@Data
@ConfigurationProperties(prefix = "fruit.exclude")
public class ExcludeProperties {
    private List<String> swagger; // Swagger相关的路径
    private List<String> user; // 用户相关的路径
    private List<String> admin; // 管理员相关的路径
    private List<String> publicApi; // 公共API路径
}
