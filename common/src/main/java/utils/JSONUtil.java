package utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * JSON工具
 */
@SuppressWarnings("all")
public class JSONUtil {

    private static final Logger logger = LoggerFactory.getLogger(JsonUtils.class);

    private static final String INCLUDE = "JSON_INCLUDE";
    private static final String EXCEPT = "JSON_EXCEPT";

    @com.fasterxml.jackson.annotation.JsonFilter(INCLUDE)
    interface MyJsonInclude {
    }

    @com.fasterxml.jackson.annotation.JsonFilter(EXCEPT)
    interface MyJsonExcept {
    }

    /**
     * 将Object对象转成JSON数据格式
     * 时间的处理需要使用注解 @JsonSerialize、@JsonFormat
     *
     * @param obj Object对象
     * @return JSON数据格式
     */
    public static String toJson(Object obj) {
        if (obj == null) {
            return "";
        }
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage(), e);
            return "";
        }
    }

    /**
     * 将Object对象转成JSON数据格式,过滤null和EMPTY
     * 时间的处理需要使用注解 @JsonSerialize、@JsonFormat
     *
     * @param dest Object对象
     * @return JSON数据格式
     */
    public static String toJsonNonEmpty(Object dest) {
        if (dest == null) {
            return "";
        }
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        try {
            return mapper.writeValueAsString(dest);
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage(), e);
        }
        return "";
    }

    /**
     * 将Object对象转成JSON数据格式,过滤null
     * 时间的处理需要使用注解 @JsonSerialize、@JsonFormat
     *
     * @param dest Object对象
     * @return JSON数据格式
     */
    public static String toJsonNonNull(Object dest) {
        if (dest == null) {
            return "";
        }
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        try {
            return mapper.writeValueAsString(dest);
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage(), e);
        }
        return "";
    }

    /**
     * 将Object对象转成JSON数据格式,null转换为空字符串，整数也转换成空字符串
     * 时间的处理需要使用注解 @JsonSerialize、@JsonFormat
     *
     * @param obj Object对象
     * @return JSON数据格式
     */
    public static String toJsonNullTurnEmpty(Object obj) {
        ObjectMapper mapper = new ObjectMapper();
        // null替换为""
        mapper.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>() {
            @Override
            public void serialize(Object arg0, JsonGenerator arg1, SerializerProvider arg2) throws IOException {
                arg1.writeString("");
            }
        });
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage(), e);
        }
        return "";
    }

    /**
     * 带localData 序列化 转Jason
     * 时间的处理不需要注解，存在注解，则注解的优先级高
     *
     * @param dest Object对象
     * @return JSON数据格式
     */
    public static String toJsonParseDate(Object dest) {
        if (dest == null) {
            return "";
        }
        ObjectMapper objectMapper = getLocalDateSerializerMapper();

        try {
            return objectMapper.writeValueAsString(dest);
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }


    /**
     * JSON字符串转实体
     *
     * @param jsonContent json字符串
     * @param clazz       实体对象
     * @param <T>         泛型
     * @return 泛型对象
     */
    public static <T> T jsonToObject(String jsonContent, Class<T> clazz) {
        if (jsonContent == null || "".equals(jsonContent.trim())) {
            return null;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        //反序列化的时候，忽略多余的字段
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        //允许单引号
        objectMapper.configure(com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        //允许不带引号的字段名
        objectMapper.configure(com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        try {
            return objectMapper.readValue(jsonContent, clazz);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * JSON字符串转实体，也可转换成集合，集合参数例：new TypeReference<List<User>>(){}
     *
     * @param jsonString json字符串
     * @param valueType  TypeReference
     * @param <T>        泛型
     * @return 泛型对象
     */
    public static <T> T jsonToObject(String jsonString, TypeReference valueType) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        mapper.configure(com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        try {
            if (StringUtils.isEmpty(jsonString)){
                return null;
            }
            return mapper.readValue(jsonString, valueType);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }


    /**
     * 带localData 序列化解析Jason
     *
     * @param jsonContent json数据
     * @param clazz       实体
     * @param <T>         泛型
     * @return 解析成对应的实体
     */
    public static <T> T jsonToObjectParseDate(String jsonContent, Class<T> clazz) {
        ObjectMapper objectMapper = getLocalDateSerializerMapper();
        try {
            return objectMapper.readValue(jsonContent, clazz);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }


    /**
     * LocalDateTime时间的处理
     *
     * @return ObjectMapper
     */
    private static ObjectMapper getLocalDateSerializerMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        SimpleModule module = new SimpleModule();
        module.addSerializer(
                LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        objectMapper.registerModule(module);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper;
    }

    /**
     * json转List集合
     *
     * @param jsonString     json字符串
     * @param elementClasses List元素类型
     * @param <T>            泛型
     * @return List元素
     */
    public static <T> List<T> jsonToList(String jsonString, Class<T> elementClasses) {
        ObjectMapper objectMapper = new ObjectMapper();
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(List.class, elementClasses);
        try {
            return objectMapper.readValue(jsonString, javaType);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * JSON转HashMap
     *
     * @param jsonContent json内容
     * @param classT      Map.class
     * @param <K>         map的key
     * @param <V>         map的value
     * @return hashMap
     */
    public static <K, V> HashMap<K, V> jsonToMap(String jsonContent, Class<?> classT) {
        if (StringUtils.isEmpty(jsonContent)) {
            return null;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.configure(com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        objectMapper.configure(com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        try {
            HashMap<K, V> object = (HashMap<K, V>) objectMapper.readValue(jsonContent, classT);
            return object;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * JSON转LinkedHashMap
     *
     * @param jsonContent json内容
     * @param classT      Map.class
     * @param <K>         map的key
     * @param <V>         map的value
     * @return hashMap
     */
    public static <K, V> LinkedHashMap<K, V> jsonToLinkedMap(String jsonContent, Class<?> classT) {
        if (StringUtils.isEmpty(jsonContent)) {
            return null;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.configure(com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        objectMapper.configure(com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        try {
            LinkedHashMap<K, V> object = (LinkedHashMap<K, V>) objectMapper.readValue(jsonContent, classT);
            return object;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    public static String toJsonInclude(Object dest, String... includeFields) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        try {
            if (includeFields != null && includeFields.length > 0) {
                objectMapper.setFilterProvider(new SimpleFilterProvider().addFilter(INCLUDE, SimpleBeanPropertyFilter.filterOutAllExcept(includeFields)));
                objectMapper.addMixIn(dest.getClass(), JsonUtils.MyJsonInclude.class);
            }

            return objectMapper.writeValueAsString(dest);
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    public static String toJsonExclude(Object dest, String... excludeFields) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        try {
            if (excludeFields != null && excludeFields.length > 0) {
                objectMapper.setFilterProvider(new SimpleFilterProvider().addFilter(EXCEPT, SimpleBeanPropertyFilter.serializeAllExcept(excludeFields)));

                objectMapper.addMixIn(dest.getClass(), JsonUtils.MyJsonExcept.class);
            }

            return objectMapper.writeValueAsString(dest);
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 比较两个JSON串的内容是否相同
     * 直接比较两个字符串的编码是不是一样的
     *
     * @param str1 JSON串1
     * @param str2 JSON串2
     * @return 是否相同
     */
    @Deprecated
    public static boolean jsonAreEqual(String str1, String str2) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode node1 = mapper.readTree(str1);
            JsonNode node2 = mapper.readTree(str2);
            return compareJsonNode(node1, node2);
        } catch (IOException e) {
            return false;
        }
    }

    private static boolean compareJsonNode(JsonNode node1, JsonNode node2) {
        if (node1.isObject()) {
            if (!node2.isObject()) return false;
            if (!compareFieldNames(node1.fieldNames(), node2.fieldNames()))
                return false;
            Iterator<Map.Entry<String, JsonNode>> fields1 = node2.fields();
            Map<String, JsonNode> fields2 = getFields(node1);
            boolean flag = true;
            while (fields1.hasNext()) {
                Map.Entry<String, JsonNode> field1 = fields1.next();
                JsonNode field2 = fields2.get(field1.getKey());
                if (!compareJsonNode(field1.getValue(), field2))
                    flag = false;
            }
            return flag;
        } else if (node1.isArray()) {
            if (!node2.isArray()) return false;
            return compareArrayNode(node1, node2);
        } else {
            return node1.toString().equals(node2.toString());
        }
    }

    private static boolean compareArrayNode(JsonNode node1, JsonNode node2) {
        if (node1.size() != node2.size()) {
            return false;
        }
        Iterator<JsonNode> it1 = node1.elements();
        while (it1.hasNext()) {
            boolean flag = false;
            JsonNode node = it1.next();
            Iterator<JsonNode> it2 = node2.elements();
            while (it2.hasNext()) {
                if (compareJsonNode(node, it2.next())) {
                    flag = true;
                    break;
                }
            }
            if (!flag)
                return false;
        }
        return true;
    }

    private static boolean compareFieldNames(Iterator<String> it1, Iterator<String> it2) {
        List<String> nameList1 = new ArrayList<>();
        List<String> nameList2 = new ArrayList<>();
        while (it1.hasNext()) {
            nameList1.add(it1.next());
        }
        while (it2.hasNext()) {
            nameList2.add(it2.next());
        }
        return nameList1.containsAll(nameList2) && nameList2.containsAll(nameList1);
    }

    private static Map<String, JsonNode> getFields(JsonNode node) {
        Iterator<Map.Entry<String, JsonNode>> fields = node.fields();
        Map<String, JsonNode> fieldMap = new HashMap<>();
        while (fields.hasNext()) {
            Map.Entry<String, JsonNode> field = fields.next();
            fieldMap.put(field.getKey(), field.getValue());
        }
        return fieldMap;
    }


}
