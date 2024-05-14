# resilience4j 熔断组件

---

```text
resilience4j:
  circuitbreaker: # 短路器
    configs:
      default:
        registerHealthIndicator: true # 健康监测
        slidingWindowSize: 10 # 滑动窗口大小
        minimumNumberOfCalls: 5 # 最小调用数
        permittedNumberOfCallsInHalfOpenState: 3 # 半开状态下允许的调用数
        #        ringBufferSizelnCloseState: 5 # 熔断器关闭时的缓冲区大小
        #        ringBufferSizeInHalfOpenState: 2 # 熔断器半开时的缓冲区大小
        #        recordFailurePredicate: com.example.resilience4j.exceptions.RecordFailurePredicate # 谓词设置异常是否为失败
        automaticTransitionFromOpenToHalfOpenEnabled: true # 是否自动从打开到半开,不需要触发
        waitDurationInOpenState: 10000 # 熔断器从打开到半开需要的时间
        failureRateThreshold: 60 # 熔断器打开的失败阈值
        eventConsumerBufferSize: 10 # 事件缓冲区大小
        recordExceptions: # 记录的异常
          - org.springframework.web.client.HttpServerErrorException
          - java.util.concurrent.TimeoutException
          - java.io.IOException
        ignoreExceptions: # 忽略的异常
          - com.example.demo.response.BusinessException
    instances: # 可以配置多个熔断器实例,使用不同配置或者覆盖配置
      service1:
        registerHealthIndicator: true # 健康监测
        slidingWindowSize: 10 # 滑动窗口大小
        minimumNumberOfCalls: 10 # 最小通过次数
        permittedNumberOfCallsInHalfOpenState: 3
        waitDurationInOpenState: 5s # 熔断器从打开到半开需要的时间
        failureRateThreshold: 50 # 熔断器打开的失败阈值
        eventConsumerBufferSize: 10 # 事件缓冲区大小
        recordFailurePredicate: com.example.demo.response.RecordFailurePredicate  # 谓词设置异常是否为失败
      service2:
        baseConfig: default
  retry: # 重试
    configs:
      default:
        maxAttempts: 3 # 最大重试次数
        waitDuration: 100 # 等待时间
        retryExceptions: # 记录的异常
          - org.springframework.web.client.HttpServerErrorException
          - java.util.concurrent.TimeoutException
          - java.io.IOException
        ignoreExceptions: # 忽略的异常
          - com.example.demo.response.BusinessException
    instances:
      service1:
        baseConfig: default
      service2:
        baseConfig: default
  bulkhead: # 隔离
    configs:
      default:
        maxConcurrentCalls: 100 # 最大并发调用数
    instances:
      service1:
        maxConcurrentCalls: 10 # 最大并发调用数
      service2:
        maxWaitDuration: 10ms # 最大等待时间
        maxConcurrentCalls: 20 # 最大并发调用数
  thread-pool-bulkhead: # 线程池隔离
    configs:
      default:
        maxThreadPoolSize: 4 # 最大线程池大小
        coreThreadPoolSize: 2 # 核心线程池大小
        queueCapacity: 2 # 队列容量
    instances:
      service1:
        baseConfig: default
      service2:
        maxThreadPoolSize: 1 # 最大线程池大小
        coreThreadPoolSize: 1 # 核心线程池大小
        queueCapacity: 1 # 队列容量
  ratelimiter: # 比率
    configs:
      default:
        registerHealthIndicator: false #注册健康指示器
        limitForPeriod: 10 # 期限
        limitRefreshPeriod: 15 # 限制刷新周期
        timeoutDuration: 0 # 超时持续时间
        eventConsumerBufferSize: 100 # 事件使用者缓冲区大小
    instances:
      service1:
        baseConfig: default
      service2:
        limitForPeriod: 6
        limitRefreshPeriod: 500ms
        timeoutDuration: 3s

```