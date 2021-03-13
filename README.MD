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
log_format main '$remote_addr #$http_x_forwarded_for #$time_local #$status '
                '#$ssl_protocol #$ssl_cipher #$upstream_addr #$upstream_response_time '
                '#$body_bytes_sent #$request_time #$remote_user #$request '
                '#$http_referer #$http_user_agent';
```

### 环境要求
> oracle jdk 1.8 或 open jdk 1.8

### 使用方法
```
[root@linux ~]# java -jar nginx-log-analyzer.jar --log_dir={nginx 日志目录路径} --timezone=Asia/Shanghai --server.port=8080
```
> 注意事项：  
> log_dir 参数必须指定，否则报错  
> timezone 参数非必须指定，默认为 Asia/Shanghai  
> ip_query_api 参数非必须指定，默认为空  
> country_lang 参数非必须指定，默认为 zh-CN  
> server.port 参数非必须指定，默认启动在 8080 端口

> 参数说明：  
> timezone 呈现在浏览器中的时间所在的时区  
> ip_query_api 列表中点击 IP 地址会调用 API 查询 IP 信息，并打印 response 在 console 中（免费接口 http(s)://ip.zxinc.org/api.php?type=json&ip=）  
> country_lang 图表中国家块的横坐标显示的语言  
```json
{
  "country_lang": "GeoIP 语言(文化)代码与国家地区对照表",
  
  "de": "USA",
  "ru": "США",
  "pt-BR": "Estados Unidos",
  "ja": "アメリカ合衆国",
  "en": "United States",
  "fr": "États-Unis",
  "zh-CN": "美国",
  "es": "Estados Unidos"
}
```

### 使用指定路径访问设置
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
