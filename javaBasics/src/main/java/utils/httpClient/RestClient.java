//package utils.httpClient;
//
//
//import org.apache.commons.beanutils.BeanUtils;
//import org.springframework.core.ParameterizedTypeReference;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.MediaType;
//import org.springframework.util.LinkedMultiValueMap;
//import org.springframework.util.MultiValueMap;
//import org.springframework.web.client.RestTemplate;
//
//import java.lang.reflect.Field;
//import java.util.Collection;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * httpclient工具类,基于httpclient 4.x
// * 不需要设置header的情况：
// * 1.普通的非校验型请求
// * 2.普通的表单请求
// * <p/>
// * 需要设置header的情况：
// * 1.头部带token校验的请求
// * 2.提交json数据的请求
// *
// * @version V1.0
// */
//public class RestClient {
//
//    /**
//     * 执行请求
//     *
//     * @param url          请求地址
//     * @param method       请求方式
//     * @param responseType 返回的数据类型
//     * @param uriVariables url自动匹配替换的参数，如url为api/{a}/{b},参数为["1","2"],则解析的url为api/1/2，使用Map参数时，遵循按key匹配
//     * @return 结果对象
//     * @throws RestClientException RestClient异常，包含状态码和非200的返回内容
//     */
//    public static <T> T exchange(String url, HttpMethod method, Class<T> responseType, Object... uriVariables) throws RestClientException {
//        return exchange(url, method, null, null, responseType, uriVariables);
//    }
//
//    /**
//     * 执行请求
//     *
//     * @param url          请求地址
//     * @param method       请求方式
//     * @param headers      设置的头信息
//     * @param responseType 返回的数据类型
//     * @param uriVariables url自动匹配替换的参数，如url为api/{a}/{b},参数为["1","2"],则解析的url为api/1/2，使用Map参数时，遵循按key匹配
//     * @return 结果对象
//     * @throws RestClientException RestClient异常，包含状态码和非200的返回内容
//     */
//    public static <T> T exchange(String url, HttpMethod method, HttpHeaders headers, Class<T> responseType, Object... uriVariables) throws RestClientException {
//        return exchange(url, method, headers, null, responseType, uriVariables);
//    }
//
//    /**
//     * 执行请求
//     *
//     * @param url          请求地址
//     * @param method       请求方式
//     * @param body         要提交的数据
//     * @param responseType 返回数据类型
//     *                     返回bean时指定Class
//     * @param uriVariables url自动匹配替换的参数，如url为api/{a}/{b},参数为["1","2"],则解析的url为api/1/2，使用Map参数时，遵循按key匹配
//     * @return 结果对象
//     * @throws RestClientException RestClient异常，包含状态码和非200的返回内容
//     */
//    public static <T> T exchange(String url, HttpMethod method, Object body, Class<T> responseType, Object... uriVariables) throws RestClientException {
//        return exchange(url, method, null, body, responseType, uriVariables);
//    }
//
//    /**
//     * 执行请求
//     *
//     * @param url          请求地址
//     * @param method       请求方式
//     * @param httpHeaders  请求头
//     * @param body         要提交的数据
//     * @param responseType 返回数据类型
//     *                     返回bean时指定Class
//     * @param uriVariables url自动匹配替换的参数，如url为api/{a}/{b},参数为["1","2"],则解析的url为api/1/2，使用Map参数时，遵循按key匹配
//     * @return 结果对象
//     * @throws RestClientException RestClient异常，包含状态码和非200的返回内容
//     */
//    public static <T> T exchange(String url, HttpMethod method, HttpHeaders httpHeaders, Object body, Class<T> responseType, Object... uriVariables) throws RestClientException {
//        try {
//            HttpEntity<?> requestEntity = new HttpEntity(body, httpHeaders);
//            requestEntity = convert(requestEntity);
//
//            if (uriVariables.length == 1 && uriVariables[0] instanceof Map) {
//                Map<String, ?> _uriVariables = (Map<String, ?>) uriVariables[0];
//                return getClient().exchange(url, method, requestEntity, responseType, _uriVariables).getBody();
//            }
//
//            return getClient().exchange(url, method, requestEntity, responseType, uriVariables).getBody();
//        } catch (Exception e) {
//            throw new RestClientException(e);
//        }
//    }
//
//    /**
//     * 执行请求
//     *
//     * @param url          请求地址
//     * @param method       请求方式
//     * @param responseType 返回的数据类型，例：new ParameterizedTypeReference<List<Bean>>(){}
//     * @param uriVariables url自动匹配替换的参数，如url为api/{a}/{b},参数为["1","2"],则解析的url为api/1/2，使用Map参数时，遵循按key匹配
//     * @return 结果对象
//     * @throws RestClientException RestClient异常，包含状态码和非200的返回内容
//     */
//    public static <T> T exchange(String url, HttpMethod method, ParameterizedTypeReference<T> responseType, Object... uriVariables) throws RestClientException {
//        return exchange(url, method, null, null, responseType, uriVariables);
//    }
//
//    /**
//     * 执行请求
//     *
//     * @param url          请求地址
//     * @param method       请求方式
//     * @param headers      设置的头信息
//     * @param responseType 返回的数据类型，例：new ParameterizedTypeReference<List<Bean>>(){}
//     * @param uriVariables url自动匹配替换的参数，如url为api/{a}/{b},参数为["1","2"],则解析的url为api/1/2，使用Map参数时，遵循按key匹配
//     * @return 结果对象
//     * @throws RestClientException RestClient异常，包含状态码和非200的返回内容
//     */
//    public static <T> T exchange(String url, HttpMethod method, HttpHeaders headers, ParameterizedTypeReference<T> responseType, Object... uriVariables) throws RestClientException {
//        return exchange(url, method, headers, null, responseType, uriVariables);
//    }
//
//    /**
//     * 执行请求
//     *
//     * @param url          请求地址
//     * @param method       请求方式
//     * @param body         要提交的数据
//     * @param responseType 返回数据类型，例：new ParameterizedTypeReference<List<Bean>>(){}
//     *                     返回bean时指定Class
//     * @param uriVariables url自动匹配替换的参数，如url为api/{a}/{b},参数为["1","2"],则解析的url为api/1/2，使用Map参数时，遵循按key匹配
//     * @return 结果对象
//     * @throws RestClientException RestClient异常，包含状态码和非200的返回内容
//     */
//    public static <T> T exchange(String url, HttpMethod method, Object body, ParameterizedTypeReference<T> responseType, Object... uriVariables) throws RestClientException {
//        return exchange(url, method, null, body, responseType, uriVariables);
//    }
//
//    /**
//     * 执行请求
//     *
//     * @param url          请求地址
//     * @param method       请求方式
//     * @param httpHeaders  请求头
//     * @param body         要提交的数据
//     * @param responseType 返回数据类型，例：new ParameterizedTypeReference<List<Bean>>(){}
//     *                     返回bean时指定Class
//     * @param uriVariables url自动匹配替换的参数，如url为api/{a}/{b},参数为["1","2"],则解析的url为api/1/2，使用Map参数时，遵循按key匹配
//     * @return 结果对象
//     * @throws RestClientException RestClient异常，包含状态码和非200的返回内容
//     */
//    public static <T> T exchange(String url, HttpMethod method, HttpHeaders httpHeaders, Object body, ParameterizedTypeReference<T> responseType, Object... uriVariables) throws RestClientException {
//        try {
//            HttpEntity<?> requestEntity = new HttpEntity(body, httpHeaders);
//            requestEntity = convert(requestEntity);
//
//            if (uriVariables.length == 1 && uriVariables[0] instanceof Map) {
//                Map<String, ?> _uriVariables = (Map<String, ?>) uriVariables[0];
//                return getClient().exchange(url, method, requestEntity, responseType, _uriVariables).getBody();
//            }
//
//            return getClient().exchange(url, method, requestEntity, responseType, uriVariables).getBody();
//        } catch (Exception e) {
//            throw new RestClientException(e);
//        }
//    }
//
//    /**
//     * 获得一个RestTemplate客户端
//     *
//     * @return
//     */
//    public static RestTemplate getClient() {
//        return new RestTemplate();
//    }
//
//    /**
//     * 获取一个application/x-www-form-urlencoded头
//     *
//     * @return
//     */
//    public static HttpHeaders buildBasicFORMHeaders() {
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//        return headers;
//    }
//
//    /**
//     * 获取一个application/json头
//     *
//     * @return
//     */
//    public static HttpHeaders buildBasicJSONHeaders() {
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        return headers;
//    }
//
//    /**
//     * 获取一个text/html头
//     *
//     * @return
//     */
//    public static HttpHeaders buildBasicHTMLHeaders() {
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.TEXT_HTML);
//        return headers;
//    }
//
//    /**
//     * 构建一个json头
//     *
//     * @param arrays
//     * @return
//     */
//    public static HttpHeaders buildJSONHeaders(Object... arrays) {
//        if (arrays.length % 2 != 0) {
//            throw new RuntimeException("arrays 长度 必须为偶数");
//        }
//
//        HttpHeaders headers = buildBasicJSONHeaders();
//
//        for (int i = 0; i < arrays.length; i++) {
//            headers.add(defaultEmptyStr(arrays[i]), defaultEmptyStr(arrays[++i]));
//        }
//
//        return headers;
//    }
//
//    private static String defaultEmptyStr(Object obj) {
//        return obj == null ? "" : obj.toString();
//
//    }
//
//    /**
//     * 对bean对象转表单模型做处理
//     *
//     * @param requestEntity
//     * @return
//     */
//    private static HttpEntity<?> convert(HttpEntity<?> requestEntity) {
//        Object body = requestEntity.getBody();
//        HttpHeaders headers = requestEntity.getHeaders();
//
//        if (body == null) {
//            return requestEntity;
//        }
//
//        if (body instanceof Map) {
//            MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
//            Map<String, ?> _body = (Map<String, ?>) body;
//            for (String key : _body.keySet()) {
//                String val = _body.get(key) == null ? "" : _body.get(key).toString();
//                multiValueMap.add(key, val);
//            }
//
//            requestEntity = new HttpEntity<>(multiValueMap, headers);
//        }
//
//        if (headers == null || !MediaType.APPLICATION_FORM_URLENCODED.equals(headers.getContentType())) {
//            return requestEntity;
//        }
//
//        if (body instanceof String) {
//            return requestEntity;
//        }
//
//        if (body instanceof Collection) {
//            return requestEntity;
//        }
//
//        if (body instanceof Map) {
//            return requestEntity;
//        }
//
//        MultiValueMap<String, Object> formEntity = new LinkedMultiValueMap<>();
//
//        Field[] fields = body.getClass().getDeclaredFields();
//        for (int i = 0; i < fields.length; i++) {
//            String name = fields[i].getName();
//            String value = null;
//
//            try {
//                value = BeanUtils.getProperty(body, name);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            formEntity.add(name, value);
//        }
//
//        return new HttpEntity<>(formEntity, headers);
//    }
//
//    public final static Object[] EMPTY_URI_VARIABLES = new Object[]{};
//
//    public final static HttpHeaders EMPTY_HEADERS = new HttpHeaders();
//
//    public final static Map<String, ?> EMPTY_BODY = new HashMap<>(1);
//
//    public final static HttpEntity EMPTY_ENTITY = new HttpEntity(EMPTY_HEADERS);
//
//}