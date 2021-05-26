# HQSMS
华全智慧灯杆管理系统（Hua Quan Smartpole Management System，HQSMS）

## 准备
### 运行环境
- Docker v20.10.6 及以上版本
- 硬件设备与运行机器处于同一局域网

### 开源工具
- [Leaf](https://github.com/Meituan-Dianping/Leaf)
- [SRS](https://github.com/ossrs/srs)
- [face_recognition](https://github.com/ageitgey/face_recognition)
- [ffmpeg](https://git.ffmpeg.org/gitweb/ffmpeg.git)

### 支持硬件
- 摄像头 iDS-2DF7C425IXR-A/JM/T3
- 气象传感器 FRT FWS500
- 广播 RT-WA05
- 屏幕 太龙 400x600
- 报警器 DS-PEAP-CV1
- 无线网络 RG-WS6108 + RG-AP630
- 充电桩 JAW1-7C09
- 电能表 ADL3000-KLH
- 边缘服务器 ARK-3500
- 灯控 GC8828

### 执行命令
启动时需要指定配置文件，以 `.\zomco.env` 为例按需修改
####启动
如果只是为了演示运行，建议用该命令

    docker-compose --env-file .\zomco.env up -d --build

####启动开发环境
如果时为了开发运行，建议用该命令

    docker-compose --env-file .\zomco.env -f .\docker-compose-dev.yml up -d --build

####启动开发环境中边缘服务器服务
开发过程中修改代码后，需要重启边缘服务器服务的话，建议用该命令

    docker-compose --env-file .\zomco.env -f .\docker-compose-dev.yml up -d --build edge-app

####启动开发环境中云端服务器服务
开发过程中修改代码后，需要重启云端服务器服务的话，建议用该命令

    docker-compose --env-file .\zomco.env -f .\docker-compose-dev.yml up -d --build cloud-app

### 配置

Docker配置是指以Docker方式运行时的配置信息，模板请参考`./zomco.env`

| Docker配置                        | 云端服务器 | 边缘服务器 | 说明                   |
|-----------------------------|------------|------------|------------------------|
| CLOUD_DATA   | /d/docker/hqsms/cloud       |  N/A     | 云端服务器数据目录，必须自行修改 |
| EDGE_DATA   |  N/A      | /d/docker/hqsms/edge      | 边缘服务器数据目录，必须自行修改 |
| CLOUD_IP   | 47.115.144.65       | 47.115.144.65     | 云端服务器IP地址 |
| CLOUD_APP_PORT     | 48080    | 48080    | 云端服务器应用端口          |
| CLOUD_MYSQL_PORT    | 43306      | 43306      | 本地数据库端口                       |
| CLOUD_RABBITMQ_PORT    | 45672      | 45672      | 消息队列服务端口                       |
| CLOUD_SRS_RTMP_PORT   | 41935      | 41935       | 流媒体服务RTMP端口                         |
| CLOUD_SRS_API_PORT | 41985      | 41985      | 流媒体服务API端口                       |
| CLOUD_SRS_RTC_PORT    | 48000      | 48000      | 流媒体服务RTC端口                          |
| CLOUD_SRS_HLS_PORT      | 48081     | 48081      | 流媒体服务HLS端口                          |
| CLOUD_SRS_SIP_PORT     | 5060      | 5060       | 流媒体服务SIP端口                       |
| CLOUD_SRS_RTP_PORT     | 9000      | 9000       | 流媒体服务RTP端口                    |


### 接口描述
应用启动后，访问 [http://localhost:48080/api/swagger-ui/index.html](http://localhost:48080/api/swagger-ui/index.html) 获取相关接口详情


## 注意事项

* 海康威视SDK限制视频保存路径必须是**英文或数字组成**
* 海康威视SDK和华为SDK在Linux环境需要配置SDK的环境变量 `export LD_LIBRARY_PATH=$LD_LIBRARY_PATH:/path/to/hikvision_sdk:/path/to/hikvision_sdk/HCNetSDKCom:/path/to/huawei_sdk/:/path/to/huawei_sdk/lib64`
* 云端服务器防火墙必须打开端口1935(RTMP)，5060(SIP)，8000(RTC)，8081(HLS，默认是8080，因端口冲突配置成8081)
* 云端服务器持续监听消息队列，消息一旦被云端服务器消费就会消失，所以暂时不支持多云端服务器部署
* 清空 Redis 队列的命令 `redis-cli FLUSHDB`
* 创建代理 `[Environment]::SetEnvironmentVariable("HTTP_PROXY", "http://username:password@proxy:port/", [EnvironmentVariableTarget]::Machine)`
* 清空目录 `Remove-Item -Path ".\tmp" -Recurse`
* Windows下需要设置Python别名 `ALIAS python to python3`
* 端口占用问题 `netstat -aon|findstr "8080"`
* 启动脚本问题:
  
      : set ff=unix
      : set ff
      : wq
  