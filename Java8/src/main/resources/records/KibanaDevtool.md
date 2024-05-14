# Click the Variables button, above, to create your own variables.
```http request
# Click the Variables button, above, to create your own variables.
GET /

# 查看分片
GET /_cat/shards

# 查看索引
GET /_cat/indices

# 查看健康程度
GET /_cat/health

GET _search
{
  "query": {
    "match_all": {}
  }
}

POST mdsd_test/_doc/1
{
  "user": "GB",
  "uid": 1,
  "city": "Beijing",
  "province": "Beijing",
  "country": "China"
}

GET mdsd_test/_doc/1

PUT mdsd_test/_doc/1
{
  "user": "GB",
  "uid": 1,
  "city": "北京",
  "province": "北京",
  "country": "中国",
  "location":{
    "lat": "29.084661",
    "lon": "111.335210"
  }
}

POST mdsd_test/_update_by_query
{
  "script": {
    "source": "ctx._source.city = params.city;ctx._source.province = params.province;ctx._source.country = params.country",
    "lang": "painless",
    "params": {
    "city": "上海",
    "province": "上海",
    "country": "中国"
    }
  },
  "query": {
    "match": {
      "user": "GB"
    }
  }
}

# 删除索引
DELETE mdsd_test

# 新建索引
PUT /mdsd_test

# 批量插入
POST _bulk
{"index": {"_index": "mdsd_test"}}
{"user": "双榆树-张三","message": "今天气不错，出去转转","uid":2,"age": 20,"city": "北京","province": "北京","country":"中国","address": "中国北京市海淀区","location": {"lat": "39.970718","lon":"116.325747"}}
{"index": {"_index": "mdsd_test"}}
{"user": "东城区-老刘","message": "出发，下一站云南","uid":3,"age": 30,"city": "北京","province": "北京","country":"中国","address": "中国北京市东城区台基厂三条3号","location": {"lat": "39.904313","lon":"116.412754"}}
{"index": {"_index": "mdsd_test"}}
{"user": "东城区-李四","message": "happy birthday!","uid":4,"age": 30,"city": "北京","province": "北京","country":"中国","address": "中国北京市东城区","location": {"lat": "39.893801","lon":"116.408986"}}
{"index": {"_index": "mdsd_test"}}
{"user": "朝阳区-老贾","message": "123，gogogo","uid":5,"age": 35,"city": "北京","province": "北京","country":"中国","address": "中国北京市朝阳区建国门","location": {"lat": "39.718256","lon":"116.367910"}}
{"index": {"_index": "mdsd_test"}}
{"user": "朝阳区-老王","message": "Happy Birthday My Firend!","uid":6,"age": 50,"city": "北京","province": "北京","country":"中国","address": "中国北京市朝阳区国贸","location": {"lat": "39.918256","lon":"116.467910"}}
{"index": {"_index": "mdsd_test"}}
{"user": "虹桥-老吴","message": "好友来了都今天我生日，好友来了，什么 birthday happy 就成!","uid":7,"age": 90,"city": "上海","province": "上海","country":"中国","address": "中国上海市闵行区","location": {"lat": "31.175927","lon":"121.383328"}}

GET /mdsd_test/_search

# type: text全文检索,type: keyword可用于聚合查询
GET /mdsd_test/_mapping

DELETE mdsd_test

GET mdsd_test

# "number_of_shards": 1设置一个分片,7以前都是5个分片
PUT mdsd_test
{
  "settings": {"number_of_shards": 1}
}

PUT mdsd_test/_mapping
{
  "properties":{
    "address":{
      "type": "text",
      "fields":{
        "keyword":{
          "type":"keyword",  
          "ignore_above": 256
        }
      }
    },
    "city":{
      "type": "keyword"
    },
    "country":{
      "type": "keyword"
    },
    "location":{
      "type": "geo_point"
    },
    "province":{
      "type": "keyword"
    },
    "uid":{
      "type":"long"
    },
    "user":{
      "type":"text",
      "fields":{
        "keyword":{
          "type":"keyword",
          "ignore_above": 256
        }
      }
    }
  }
}

GET mdsd_test/_mapping

GET mdsd_test/_search
{
  "query": {
    "match": {
      "city": "北京"
    }
  }
}

GET mdsd_test/_search
{
  "query": {
    "bool": {
      "must": [
        {"match": {
          "city": "北京"
        }},{
          "match": {
            "age": "30"
          }
        }
      ]
    }
  }
}

GET mdsd_test/_search
{
  "query": {
    "bool": {"must_not": [
      {"match": {
        "city": "北京"
      }}
    ]}
  }
}

GET mdsd_test/_search
{
  "query": {
    "bool": {"should": [
      {"match": {
        "city": "北京"
      }},{
        "match": {
          "city": "上海"
        }
      }
    ]}
  }
}

# 朝外soho 39.920086, 116.454182
GET mdsd_test/_search
{
  "query": {
    "bool": {
      "must": [
        {"match": {
          "address": "北京"
        }}
      ]
    }
  },
  "post_filter": {
    "geo_distance": {
      "distance": "3km",
      "location": {
        "lat": 39.920086,
        "lon": 116.454182
      }
    }
  }
}

# 5km 按距离
GET mdsd_test/_search
{
  "query": {
    "bool": {
      "must": [
        {"match": {
          "address": "北京"
        }}
      ]
    }
  },
  "post_filter": {
    "geo_distance": {
      "distance": "5km",
      "location": {
        "lat": 39.920086,
        "lon": 116.454182
      }
    }
  },
  "sort": [
    {
      "_geo_distance": {
        "location": "39.920086,116.454182",
        "order": "asc",
        "unit": "km"
      }
    }
  ]
}

GET mdsd_test/_search
{
  "query": {
    "range": {
      "age": {
        "gte": 30,
        "lte": 40
      }
    }
  }
}

GET mdsd_test/_search
{
  "query": {
    "range": {
      "age": {
        "gte": 30,
        "lte": 40
      }
    }
  },"sort": [
    {
      "age": {
        "order": "desc"
      }
    }
  ]
}

GET mdsd_test/_search
{
  "query": {
    "match": {
      "message": "happy birthday"
    }
  }
}

# 注意大小写自动处理
GET mdsd_test/_search
{
  "query": {
    "match_phrase": {
      "message": "Happy birthday"
    }
  }
}

GET mdsd_test/_search
{
  "query": {
    "match_phrase": {
      "message": "Happy birthday"
    }
  },
  "highlight": {
    "fields": {
      "message":{}
    }
  }
}

# 聚合分析,size: 0不展示详细的命中数据
GET mdsd_test/_search
{
  "size": 0,
  "aggs": {
    "age": {
      "range": {
        "field": "age",
        "ranges": [
          {
            "from": 20,
            "to": 30
          },{
            "from": 30,
            "to": 40
          },{
            "from": 40,
            "to": 50
          }
        ]
      }
    }
  }
}

# terms类似group by
GET mdsd_test/_search
{
  "query": {
    "match": {
      "message": "happy birthday"
    }
  },
  "size": 0,
  "aggs": {
    "city": {
      "terms": {
        "field": "city",
        "size": 10
      }
    }
  }
}

# "analyzer": "standard" 标准分词
GET mdsd_test/_analyze
{
  "text":["Happy Birthday"],
  "analyzer": "standard"
}

# "analyzer": "simple"
GET mdsd_test/_analyze
{
  "text":["Happy.Birthday"]
}

# "analyzer": "simple" 以中间的点分词
GET mdsd_test/_analyze
{
  "text":["Happy.Birthday"],
  "analyzer": "simple"
}

# "filter": ["lowercase"]
GET mdsd_test/_analyze
{
  "text": ["Happy Birthday"],
  "tokenizer": "keyword"
}

GET mdsd_test/_analyze
{
  "text": ["Happy Birthday"],
  "tokenizer": "standard",
  "filter": ["lowercase"]
}
```
