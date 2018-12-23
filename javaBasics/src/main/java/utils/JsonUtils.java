package utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * JSON工具
 */
@SuppressWarnings("all")
public class JsonUtils {

    private static final Logger logger = LoggerFactory.getLogger(JsonUtils.class);

    private static final String INCLUDE = "JSON_INCLUDE";
    private static final String EXCEPT = "JSON_EXCEPT";

    public static String toJson(Object dest) {
        return toJson(dest, "yyyy-MM-dd HH:mm:ss");
    }

    public static String toJson(Object dest, String dateFormat) {
        if (dest == null) {
            return null;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(Include.NON_EMPTY);
        if (StringUtils.hasText(dateFormat)) {
            objectMapper.setDateFormat(new SimpleDateFormat(dateFormat));
        }
        try {
            String value = objectMapper.writeValueAsString(dest);
            return value;
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    public static <T> T jsonToObject(String jsonContent, Class<T> classT) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(Include.NON_NULL);
        objectMapper.configure(com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        objectMapper.configure(com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        try {
            T object = objectMapper.readValue(jsonContent, classT);
            return object;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    public static <K, V> HashMap<K, V> jsonToMap(String jsonContent, Class<?> classT) {
        if (StringUtils.isEmpty(jsonContent)) {
            return null;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(Include.NON_NULL);
        objectMapper.configure(com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        objectMapper.configure(com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        try {
            HashMap<K, V> object = (HashMap<K, V>) objectMapper.readValue(jsonContent, classT);
            return object;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    public static <K, V> LinkedHashMap<K, V> jsonToLinkedMap(String jsonContent, Class<?> classT) {
        if (StringUtils.isEmpty(jsonContent)) {
            return null;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(Include.NON_NULL);
        objectMapper.configure(com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        objectMapper.configure(com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        try {
            LinkedHashMap<K, V> object = (LinkedHashMap<K, V>) objectMapper.readValue(jsonContent, classT);
            return object;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    public static String toJsonInclude(Object dest, String... includeFields) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(Include.NON_NULL);
        try {
            if (includeFields != null && includeFields.length > 0) {
                objectMapper.setFilterProvider(new SimpleFilterProvider().addFilter(INCLUDE, SimpleBeanPropertyFilter.filterOutAllExcept(includeFields)));
                objectMapper.addMixIn(dest.getClass(), MyJsonInclude.class);
            }

            return objectMapper.writeValueAsString(dest);
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    public static String toJsonExclude(Object dest, String... excludeFields) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(Include.NON_NULL);
        try {
            if (excludeFields != null && excludeFields.length > 0) {
                objectMapper.setFilterProvider(new SimpleFilterProvider().addFilter(EXCEPT, SimpleBeanPropertyFilter.serializeAllExcept(excludeFields)));

                objectMapper.addMixIn(dest.getClass(), MyJsonExcept.class);
            }

            return objectMapper.writeValueAsString(dest);
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

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
     * 带localData 序列化 解析Jason
     *
     * @param jsonContent
     * @param classT
     * @param <T>
     * @return
     */
    public static <T> T jsonToObjectParseDate(String jsonContent, Class<T> classT) {

        ObjectMapper objectMapper = getLocalDateSerializerMapper();

        try {
            T object = objectMapper.readValue(jsonContent, classT);
            return object;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    @com.fasterxml.jackson.annotation.JsonFilter(INCLUDE)
    interface MyJsonInclude {
    }

    @com.fasterxml.jackson.annotation.JsonFilter(EXCEPT)
    interface MyJsonExcept {
    }

    /**
     * 带localData 序列化 转Jason
     *
     * @param jsonContent
     * @param classT
     * @param <T>
     * @return
     */
    public static String toJsonParseDate(Object dest) {
        if (dest == null) {
            return null;
        }
        ObjectMapper objectMapper = getLocalDateSerializerMapper();

        try {
            String value = objectMapper.writeValueAsString(dest);
            return value;
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     *   获取 LocalDate 序列化的 ObjectMapper
     * @return todo
     */
    private static ObjectMapper getLocalDateSerializerMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        SimpleModule module = new SimpleModule();
//        module.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
//                .addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        objectMapper.registerModule(module);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper;
    }

}
