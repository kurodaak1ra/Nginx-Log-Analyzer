# Nginx Log Analyzer

一个简易的 nginx log 分析器，你值得拥有！  
本项目采用，瑞民族之光幸（Java）开发

![MIT](https://img.shields.io/badge/license-MIT-green)
![Java](https://img.shields.io/badge/Java-1.8-green)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-2.4.2-green)
![Maven](https://img.shields.io/badge/Maven-3.6.3-green)

### 硬性条件
> 请【务必，务必，务必】将 nginx 日志格式设置为如下格式
```
log_format main '$remote_addr #$remote_user #$time_local #$request '
                '#$status #$body_bytes_sent #$http_referer '
                '#$http_user_agent #$http_x_forwarded_for '
                '#$ssl_protocol #$ssl_cipher #$upstream_addr '
                '#$request_time #$upstream_response_time';
```

### 环境要求
> oracle jdk 1.8 或 open jdk 1.8

### 使用方法
```
[root@linux ~]# java -jar nginx-log-analyzer.jar --log_dir={nginx 日志目录路径} --server.port=8080
```
> 注意事项：  
> log_dir 参数必须指定，否则报错  
> server.port 参数非必须指定，默认启动在 8080 端口

### 使用指定路径访问
```
修改 nginx 配置文件，新增 location

location ~ ^/{你想要的路径}/ {
    proxy_pass http://127.0.0.1:8080;
    rewrite    /{你想要的路径}/(.*) /$1 break;
}
location ~ ^/analyzer/ {
    rewrite    /analyzer/(.*) /{你想要的路径}/analyzer/$1 redirect;
}
```

### 其他
> 图表标题中带有 * 的，点击柱状图均有详细展示页面  
> 图表中的《ip 地址》为实际到访服务器的 IP 地址（$remote_addr），《真实 ip 地址》为真实访问网站用户的 IP地址（$http_x_forwarded_for）  
