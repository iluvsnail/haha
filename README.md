## 数据资源分类微服务

### 一、evaluateResource
数据资源分类

url：/gdp/api/resource/classifier/{serverId}/evaluate/{resourceId}

method：post

参数说明：
1. serverId：必填，暂无作用
2. resourceId：资源id


返回：
1. resourceId:最可能分类id
2. prediction:属于该分类的概率（分数表示）
3. predictions:该资源属于所有分类的概率数组

### 二、trainMLModel
训练模型

url：/gdp/api/resource/classifier/{serverId}/train/{resourceId}/{standardTypeId}

method：post

参数说明：
1. serverId：必填，暂无作用
2. resourceId：资源id
3. standardTypeId：标准分类id


返回：
1. true:成功
2. false:失败

### 三、reloadModel
重新加载lr模型

url：/gdp/api/resource/classifier/{serverId}/train/model/reload

method：post

参数说明：
1. serverId：必填，暂无作用


返回：
1. true:成功
2. false:失败

### 四、访问/swagger-ui.html可以访问接口说明

### 五、其他
1. 需要配置redis
2. 需要配置spark
3. 需要配置hadoop
4.