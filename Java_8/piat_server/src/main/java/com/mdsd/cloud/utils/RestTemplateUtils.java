package com.mdsd.cloud.utils;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author WangYunwei [2021-10-08]
 */
@Component
public class RestTemplateUtils {

    @Resource
    RestTemplate restTemplate;

    /**
     * GET请求调用方式
     *
     * @param url          请求URL
     * @param responseType 返回对象类型
     * @return ResponseEntity 响应对象封装类
     */
    public <T> ResponseEntity<T> get(final String url, final Class<T> responseType) throws RestClientException {

        return this.restTemplate.getForEntity(url, responseType);
    }

    /**
     * GET请求调用方式
     *
     * @param url          请求URL
     * @param responseType 返回对象类型
     * @param uriVariables URL中的变量，按顺序依次对应
     * @return ResponseEntity 响应对象封装类
     */
    public <T> ResponseEntity<T> get(final String url, final Class<T> responseType, final Object... uriVariables)
            throws RestClientException {

        return this.restTemplate.getForEntity(url, responseType, uriVariables);
    }

    /**
     * GET请求调用方式
     *
     * @param url          请求URL
     * @param responseType 返回对象类型
     * @param uriVariables URL中的变量，与Map中的key对应
     * @return ResponseEntity 响应对象封装类
     */
    public <T> ResponseEntity<T> get(final String url, final Class<T> responseType, final Map<String, ?> uriVariables)
            throws RestClientException {

        return this.restTemplate.getForEntity(url, responseType, uriVariables);
    }

    /**
     * 带请求头的GET请求调用方式
     *
     * @param url          请求URL
     * @param headers      请求头参数
     * @param responseType 返回对象类型
     * @param uriVariables URL中的变量，按顺序依次对应
     * @return ResponseEntity 响应对象封装类
     */
    public <T> ResponseEntity<T> get(final String url, final Map<String, String> headers, final Class<T> responseType,
                                     final Object... uriVariables) throws RestClientException {

        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAll(headers);
        return this.get(url, httpHeaders, responseType, uriVariables);
    }

    /**
     * 带请求头的GET请求调用方式
     *
     * @param url          请求URL
     * @param headers      请求头参数
     * @param responseType 返回对象类型
     * @param uriVariables URL中的变量，按顺序依次对应
     * @return ResponseEntity 响应对象封装类
     */
    public <T> ResponseEntity<T> get(final String url, final HttpHeaders headers, final Class<T> responseType, final Object... uriVariables)
            throws RestClientException {

        final HttpEntity<?> requestEntity = new HttpEntity<>(headers);
        return this.exchange(url, HttpMethod.GET, requestEntity, responseType, uriVariables);
    }

    /**
     * 带请求头的GET请求调用方式
     *
     * @param url          请求URL
     * @param headers      请求头参数
     * @param responseType 返回对象类型
     * @param uriVariables URL中的变量，与Map中的key对应
     * @return ResponseEntity 响应对象封装类
     */
    public <T> ResponseEntity<T> get(final String url, final Map<String, String> headers, final Class<T> responseType,
                                     final Map<String, ?> uriVariables) throws RestClientException {

        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAll(headers);
        return this.get(url, httpHeaders, responseType, uriVariables);
    }

    /**
     * 带请求头的GET请求调用方式
     *
     * @param url          请求URL
     * @param headers      请求头参数
     * @param responseType 返回对象类型
     * @param uriVariables URL中的变量，与Map中的key对应
     * @return ResponseEntity 响应对象封装类
     */
    public <T> ResponseEntity<T> get(final String url, final HttpHeaders headers, final Class<T> responseType,
                                     final Map<String, ?> uriVariables) throws RestClientException {

        final HttpEntity<?> requestEntity = new HttpEntity<>(headers);
        return this.exchange(url, HttpMethod.GET, requestEntity, responseType, uriVariables);
    }

    // ----------------------------------POST-------------------------------------------------------

    /**
     * POST请求调用方式
     *
     * @param url          请求URL
     * @param responseType 返回对象类型
     * @return
     */
    public <T> ResponseEntity<T> post(final String url, final Class<T> responseType) throws RestClientException {

        return this.restTemplate.postForEntity(url, HttpEntity.EMPTY, responseType);
    }

    /**
     * POST请求调用方式
     *
     * @param url          请求URL
     * @param requestBody  请求参数体
     * @param responseType 返回对象类型
     * @return ResponseEntity 响应对象封装类
     */
    public <T> ResponseEntity<T> post(final String url, final Object requestBody, final Class<T> responseType)
            throws RestClientException {

        return this.restTemplate.postForEntity(url, requestBody, responseType);
    }

    /**
     * POST请求调用方式
     *
     * @param url          请求URL
     * @param requestBody  请求参数体
     * @param responseType 返回对象类型
     * @param uriVariables URL中的变量，按顺序依次对应
     * @return ResponseEntity 响应对象封装类
     */
    public <T> ResponseEntity<T> post(final String url, final Object requestBody, final Class<T> responseType, final Object... uriVariables)
            throws RestClientException {

        return this.restTemplate.postForEntity(url, requestBody, responseType, uriVariables);
    }

    /**
     * POST请求调用方式
     *
     * @param url          请求URL
     * @param requestBody  请求参数体
     * @param responseType 返回对象类型
     * @param uriVariables URL中的变量，与Map中的key对应
     * @return ResponseEntity 响应对象封装类
     */
    public <T> ResponseEntity<T> post(final String url, final Object requestBody, final Class<T> responseType,
                                      final Map<String, ?> uriVariables) throws RestClientException {

        return this.restTemplate.postForEntity(url, requestBody, responseType, uriVariables);
    }

    /**
     * 带请求头的POST请求调用方式
     *
     * @param url          请求URL
     * @param headers      请求头参数
     * @param requestBody  请求参数体
     * @param responseType 返回对象类型
     * @param uriVariables URL中的变量，按顺序依次对应
     * @return ResponseEntity 响应对象封装类
     */
    public <T> ResponseEntity<T> post(final String url, final Map<String, String> headers, final Object requestBody,
                                      final Class<T> responseType, final Object... uriVariables) throws RestClientException {

        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAll(headers);
        return this.post(url, httpHeaders, requestBody, responseType, uriVariables);
    }

    /**
     * 带请求头的POST请求调用方式
     *
     * @param url          请求URL
     * @param headers      请求头参数
     * @param requestBody  请求参数体
     * @param responseType 返回对象类型
     * @param uriVariables URL中的变量，按顺序依次对应
     * @return ResponseEntity 响应对象封装类
     */
    public <T> ResponseEntity<T> post(final String url, final HttpHeaders headers, final Object requestBody, final Class<T> responseType,
                                      final Object... uriVariables) throws RestClientException {

        final HttpEntity<Object> requestEntity = new HttpEntity<Object>(requestBody, headers);
        return this.post(url, requestEntity, responseType, uriVariables);
    }

    /**
     * 带请求头的POST请求调用方式
     *
     * @param url          请求URL
     * @param headers      请求头参数
     * @param requestBody  请求参数体
     * @param responseType 返回对象类型
     * @param uriVariables URL中的变量，与Map中的key对应
     * @return ResponseEntity 响应对象封装类
     */
    public <T> ResponseEntity<T> post(final String url, final Map<String, String> headers, final Object requestBody,
                                      final Class<T> responseType, final Map<String, ?> uriVariables) throws RestClientException {

        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAll(headers);
        return this.post(url, httpHeaders, requestBody, responseType, uriVariables);
    }

    /**
     * 带请求头的POST请求调用方式
     *
     * @param url          请求URL
     * @param headers      请求头参数
     * @param requestBody  请求参数体
     * @param responseType 返回对象类型
     * @param uriVariables URL中的变量，与Map中的key对应
     * @return ResponseEntity 响应对象封装类
     */
    public <T> ResponseEntity<T> post(final String url, final HttpHeaders headers, final Object requestBody, final Class<T> responseType,
                                      final Map<String, ?> uriVariables) throws RestClientException {

        final HttpEntity<Object> requestEntity = new HttpEntity<Object>(requestBody, headers);
        return this.post(url, requestEntity, responseType, uriVariables);
    }

    /**
     * 自定义请求头和请求体的POST请求调用方式
     *
     * @param url           请求URL
     * @param requestEntity 请求头和请求体封装对象
     * @param responseType  返回对象类型
     * @param uriVariables  URL中的变量，按顺序依次对应
     * @return ResponseEntity 响应对象封装类
     */
    public <T> ResponseEntity<T> post(final String url, final HttpEntity<?> requestEntity, final Class<T> responseType,
                                      final Object... uriVariables) throws RestClientException {

        return this.restTemplate.exchange(url, HttpMethod.POST, requestEntity, responseType, uriVariables);
    }

    /**
     * 自定义请求头和请求体的POST请求调用方式
     *
     * @param url           请求URL
     * @param requestEntity 请求头和请求体封装对象
     * @param responseType  返回对象类型
     * @param uriVariables  URL中的变量，与Map中的key对应
     * @return ResponseEntity 响应对象封装类
     */
    public <T> ResponseEntity<T> post(final String url, final HttpEntity<?> requestEntity, final Class<T> responseType,
                                      final Map<String, ?> uriVariables) throws RestClientException {

        return this.restTemplate.exchange(url, HttpMethod.POST, requestEntity, responseType, uriVariables);
    }

    // ----------------------------------PUT-------------------------------------------------------

    /**
     * PUT请求调用方式
     *
     * @param url          请求URL
     * @param responseType 返回对象类型
     * @param uriVariables URL中的变量，按顺序依次对应
     * @return ResponseEntity 响应对象封装类
     */
    public <T> ResponseEntity<T> put(final String url, final Class<T> responseType, final Object... uriVariables)
            throws RestClientException {

        return this.put(url, HttpEntity.EMPTY, responseType, uriVariables);
    }

    /**
     * PUT请求调用方式
     *
     * @param url          请求URL
     * @param requestBody  请求参数体
     * @param responseType 返回对象类型
     * @param uriVariables URL中的变量，按顺序依次对应
     * @return ResponseEntity 响应对象封装类
     */
    public <T> ResponseEntity<T> put(final String url, final Object requestBody, final Class<T> responseType, final Object... uriVariables)
            throws RestClientException {

        final HttpEntity<Object> requestEntity = new HttpEntity<Object>(requestBody);
        return this.put(url, requestEntity, responseType, uriVariables);
    }

    /**
     * PUT请求调用方式
     *
     * @param url          请求URL
     * @param requestBody  请求参数体
     * @param responseType 返回对象类型
     * @param uriVariables URL中的变量，与Map中的key对应
     * @return ResponseEntity 响应对象封装类
     */
    public <T> ResponseEntity<T> put(final String url, final Object requestBody, final Class<T> responseType, final Map<String, ?> uriVariables)
            throws RestClientException {

        final HttpEntity<Object> requestEntity = new HttpEntity<Object>(requestBody);
        return this.put(url, requestEntity, responseType, uriVariables);
    }

    /**
     * 带请求头的PUT请求调用方式
     *
     * @param url          请求URL
     * @param headers      请求头参数
     * @param requestBody  请求参数体
     * @param responseType 返回对象类型
     * @param uriVariables URL中的变量，按顺序依次对应
     * @return ResponseEntity 响应对象封装类
     */
    public <T> ResponseEntity<T> put(final String url, final Map<String, String> headers, final Object requestBody, final Class<T> responseType,
                                     final Object... uriVariables) throws RestClientException {

        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAll(headers);
        return this.put(url, httpHeaders, requestBody, responseType, uriVariables);
    }

    /**
     * 带请求头的PUT请求调用方式
     *
     * @param url          请求URL
     * @param headers      请求头参数
     * @param requestBody  请求参数体
     * @param responseType 返回对象类型
     * @param uriVariables URL中的变量，按顺序依次对应
     * @return ResponseEntity 响应对象封装类
     */
    public <T> ResponseEntity<T> put(final String url, final HttpHeaders headers, final Object requestBody, final Class<T> responseType,
                                     final Object... uriVariables) throws RestClientException {

        final HttpEntity<Object> requestEntity = new HttpEntity<Object>(requestBody, headers);
        return this.put(url, requestEntity, responseType, uriVariables);
    }

    /**
     * 带请求头的PUT请求调用方式
     *
     * @param url          请求URL
     * @param headers      请求头参数
     * @param requestBody  请求参数体
     * @param responseType 返回对象类型
     * @param uriVariables URL中的变量，与Map中的key对应
     * @return ResponseEntity 响应对象封装类
     */
    public <T> ResponseEntity<T> put(final String url, final Map<String, String> headers, final Object requestBody, final Class<T> responseType,
                                     final Map<String, ?> uriVariables) throws RestClientException {

        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAll(headers);
        return this.put(url, httpHeaders, requestBody, responseType, uriVariables);
    }

    /**
     * 带请求头的PUT请求调用方式
     *
     * @param url          请求URL
     * @param headers      请求头参数
     * @param requestBody  请求参数体
     * @param responseType 返回对象类型
     * @param uriVariables URL中的变量，与Map中的key对应
     * @return ResponseEntity 响应对象封装类
     */
    public <T> ResponseEntity<T> put(final String url, final HttpHeaders headers, final Object requestBody, final Class<T> responseType,
                                     final Map<String, ?> uriVariables) throws RestClientException {

        final HttpEntity<Object> requestEntity = new HttpEntity<Object>(requestBody, headers);
        return this.put(url, requestEntity, responseType, uriVariables);
    }

    /**
     * 自定义请求头和请求体的PUT请求调用方式
     *
     * @param url           请求URL
     * @param requestEntity 请求头和请求体封装对象
     * @param responseType  返回对象类型
     * @param uriVariables  URL中的变量，按顺序依次对应
     * @return ResponseEntity 响应对象封装类
     */
    public <T> ResponseEntity<T> put(final String url, final HttpEntity<?> requestEntity, final Class<T> responseType,
                                     final Object... uriVariables) throws RestClientException {

        return this.restTemplate.exchange(url, HttpMethod.PUT, requestEntity, responseType, uriVariables);
    }

    /**
     * 自定义请求头和请求体的PUT请求调用方式
     *
     * @param url           请求URL
     * @param requestEntity 请求头和请求体封装对象
     * @param responseType  返回对象类型
     * @param uriVariables  URL中的变量，与Map中的key对应
     * @return ResponseEntity 响应对象封装类
     */
    public <T> ResponseEntity<T> put(final String url, final HttpEntity<?> requestEntity, final Class<T> responseType,
                                     final Map<String, ?> uriVariables) throws RestClientException {

        return this.restTemplate.exchange(url, HttpMethod.PUT, requestEntity, responseType, uriVariables);
    }

    // ----------------------------------DELETE-------------------------------------------------------

    /**
     * DELETE请求调用方式
     *
     * @param url          请求URL
     * @param responseType 返回对象类型
     * @param uriVariables URL中的变量，按顺序依次对应
     * @return ResponseEntity 响应对象封装类
     */
    public <T> ResponseEntity<T> delete(final String url, final Class<T> responseType, final Object... uriVariables)
            throws RestClientException {

        return this.delete(url, HttpEntity.EMPTY, responseType, uriVariables);
    }

    /**
     * DELETE请求调用方式
     *
     * @param url          请求URL
     * @param responseType 返回对象类型
     * @param uriVariables URL中的变量，与Map中的key对应
     * @return ResponseEntity 响应对象封装类
     */
    public <T> ResponseEntity<T> delete(final String url, final Class<T> responseType, final Map<String, ?> uriVariables)
            throws RestClientException {

        return this.delete(url, HttpEntity.EMPTY, responseType, uriVariables);
    }

    /**
     * DELETE请求调用方式
     *
     * @param url          请求URL
     * @param requestBody  请求参数体
     * @param responseType 返回对象类型
     * @param uriVariables URL中的变量，按顺序依次对应
     * @return ResponseEntity 响应对象封装类
     */
    public <T> ResponseEntity<T> delete(final String url, final Object requestBody, final Class<T> responseType, final Object... uriVariables)
            throws RestClientException {

        final HttpEntity<Object> requestEntity = new HttpEntity<Object>(requestBody);
        return this.delete(url, requestEntity, responseType, uriVariables);
    }

    /**
     * DELETE请求调用方式
     *
     * @param url          请求URL
     * @param requestBody  请求参数体
     * @param responseType 返回对象类型
     * @param uriVariables URL中的变量，与Map中的key对应
     * @return ResponseEntity 响应对象封装类
     */
    public <T> ResponseEntity<T> delete(final String url, final Object requestBody, final Class<T> responseType,
                                        final Map<String, ?> uriVariables) throws RestClientException {

        final HttpEntity<Object> requestEntity = new HttpEntity<Object>(requestBody);
        return this.delete(url, requestEntity, responseType, uriVariables);
    }

    /**
     * 带请求头的DELETE请求调用方式
     *
     * @param url          请求URL
     * @param headers      请求头参数
     * @param responseType 返回对象类型
     * @param uriVariables URL中的变量，按顺序依次对应
     * @return ResponseEntity 响应对象封装类
     */
    public <T> ResponseEntity<T> delete(final String url, final Map<String, String> headers, final Class<T> responseType,
                                        final Object... uriVariables) throws RestClientException {

        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAll(headers);
        return this.delete(url, httpHeaders, responseType, uriVariables);
    }

    /**
     * 带请求头的DELETE请求调用方式
     *
     * @param url          请求URL
     * @param headers      请求头参数
     * @param responseType 返回对象类型
     * @param uriVariables URL中的变量，按顺序依次对应
     * @return ResponseEntity 响应对象封装类
     */
    public <T> ResponseEntity<T> delete(final String url, final HttpHeaders headers, final Class<T> responseType, final Object... uriVariables)
            throws RestClientException {

        final HttpEntity<Object> requestEntity = new HttpEntity<Object>(headers);
        return this.delete(url, requestEntity, responseType, uriVariables);
    }

    /**
     * 带请求头的DELETE请求调用方式
     *
     * @param url          请求URL
     * @param headers      请求头参数
     * @param responseType 返回对象类型
     * @param uriVariables URL中的变量，与Map中的key对应
     * @return ResponseEntity 响应对象封装类
     */
    public <T> ResponseEntity<T> delete(final String url, final Map<String, String> headers, final Class<T> responseType,
                                        final Map<String, ?> uriVariables) throws RestClientException {

        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAll(headers);
        return this.delete(url, httpHeaders, responseType, uriVariables);
    }

    /**
     * 带请求头的DELETE请求调用方式
     *
     * @param url          请求URL
     * @param headers      请求头参数
     * @param responseType 返回对象类型
     * @param uriVariables URL中的变量，与Map中的key对应
     * @return ResponseEntity 响应对象封装类
     */
    public <T> ResponseEntity<T> delete(final String url, final HttpHeaders headers, final Class<T> responseType,
                                        final Map<String, ?> uriVariables) throws RestClientException {

        final HttpEntity<Object> requestEntity = new HttpEntity<Object>(headers);
        return this.delete(url, requestEntity, responseType, uriVariables);
    }

    /**
     * 带请求头的DELETE请求调用方式
     *
     * @param url          请求URL
     * @param headers      请求头参数
     * @param requestBody  请求参数体
     * @param responseType 返回对象类型
     * @param uriVariables URL中的变量，按顺序依次对应
     * @return ResponseEntity 响应对象封装类
     */
    public <T> ResponseEntity<T> delete(final String url, final Map<String, String> headers, final Object requestBody,
                                        final Class<T> responseType, final Object... uriVariables) throws RestClientException {

        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAll(headers);
        return this.delete(url, httpHeaders, requestBody, responseType, uriVariables);
    }

    /**
     * 带请求头的DELETE请求调用方式
     *
     * @param url          请求URL
     * @param headers      请求头参数
     * @param requestBody  请求参数体
     * @param responseType 返回对象类型
     * @param uriVariables URL中的变量，按顺序依次对应
     * @return ResponseEntity 响应对象封装类
     */
    public <T> ResponseEntity<T> delete(final String url, final HttpHeaders headers, final Object requestBody, final Class<T> responseType,
                                        final Object... uriVariables) throws RestClientException {

        final HttpEntity<Object> requestEntity = new HttpEntity<Object>(requestBody, headers);
        return this.delete(url, requestEntity, responseType, uriVariables);
    }

    /**
     * 带请求头的DELETE请求调用方式
     *
     * @param url          请求URL
     * @param headers      请求头参数
     * @param requestBody  请求参数体
     * @param responseType 返回对象类型
     * @param uriVariables URL中的变量，与Map中的key对应
     * @return ResponseEntity 响应对象封装类
     */
    public <T> ResponseEntity<T> delete(final String url, final Map<String, String> headers, final Object requestBody,
                                        final Class<T> responseType, final Map<String, ?> uriVariables) throws RestClientException {

        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAll(headers);
        return this.delete(url, httpHeaders, requestBody, responseType, uriVariables);
    }

    /**
     * 带请求头的DELETE请求调用方式
     *
     * @param url          请求URL
     * @param headers      请求头参数
     * @param requestBody  请求参数体
     * @param responseType 返回对象类型
     * @param uriVariables URL中的变量，与Map中的key对应
     * @return ResponseEntity 响应对象封装类
     */
    public <T> ResponseEntity<T> delete(final String url, final HttpHeaders headers, final Object requestBody, final Class<T> responseType,
                                        final Map<String, ?> uriVariables) throws RestClientException {

        final HttpEntity<Object> requestEntity = new HttpEntity<Object>(requestBody, headers);
        return this.delete(url, requestEntity, responseType, uriVariables);
    }

    /**
     * 自定义请求头和请求体的DELETE请求调用方式
     *
     * @param url           请求URL
     * @param requestEntity 请求头和请求体封装对象
     * @param responseType  返回对象类型
     * @param uriVariables  URL中的变量，按顺序依次对应
     * @return ResponseEntity 响应对象封装类
     */
    public <T> ResponseEntity<T> delete(final String url, final HttpEntity<?> requestEntity, final Class<T> responseType,
                                        final Object... uriVariables) throws RestClientException {

        return this.restTemplate.exchange(url, HttpMethod.DELETE, requestEntity, responseType, uriVariables);
    }

    /**
     * 自定义请求头和请求体的DELETE请求调用方式
     *
     * @param url           请求URL
     * @param requestEntity 请求头和请求体封装对象
     * @param responseType  返回对象类型
     * @param uriVariables  URL中的变量，与Map中的key对应
     * @return ResponseEntity 响应对象封装类
     */
    public <T> ResponseEntity<T> delete(final String url, final HttpEntity<?> requestEntity, final Class<T> responseType,
                                        final Map<String, ?> uriVariables) throws RestClientException {

        return this.restTemplate.exchange(url, HttpMethod.DELETE, requestEntity, responseType, uriVariables);
    }

    // ----------------------------------通用方法-------------------------------------------------------

    /**
     * 通用调用方式
     *
     * @param url           请求URL
     * @param method        请求方法类型
     * @param requestEntity 请求头和请求体封装对象
     * @param responseType  返回对象类型
     * @param uriVariables  URL中的变量，按顺序依次对应
     * @return ResponseEntity 响应对象封装类
     */
    public <T> ResponseEntity<T> exchange(final String url, final HttpMethod method, final HttpEntity<?> requestEntity,
                                          final Class<T> responseType, final Object... uriVariables) throws RestClientException {

        return this.restTemplate.exchange(url, method, requestEntity, responseType, uriVariables);
    }

    /**
     * 通用调用方式
     *
     * @param url           请求URL
     * @param method        请求方法类型
     * @param requestEntity 请求头和请求体封装对象
     * @param responseType  返回对象类型
     * @param uriVariables  URL中的变量，与Map中的key对应
     * @return ResponseEntity 响应对象封装类
     */
    public <T> ResponseEntity<T> exchange(final String url, final HttpMethod method, final HttpEntity<?> requestEntity,
                                          final Class<T> responseType, final Map<String, ?> uriVariables) throws RestClientException {

        return this.restTemplate.exchange(url, method, requestEntity, responseType, uriVariables);
    }

}
