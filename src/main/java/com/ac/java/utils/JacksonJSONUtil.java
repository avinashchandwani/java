/*
maven dependencies required
<dependency>
            <groupId>com.fasterxml.jackson.core</groupId>
            <artifactId>jackson-annotations</artifactId>
            <version>2.11.4</version>
            <scope>compile</scope>
        </dependency>
        <dependency>
            <groupId>com.fasterxml.jackson.core</groupId>
            <artifactId>jackson-databind</artifactId>
            <version>2.11.1</version>
        </dependency>
        <dependency>
            <groupId>com.fasterxml.jackson.core</groupId>
            <artifactId>jackson-core</artifactId>
            <version>2.11.1</version>
        </dependency>
*/
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.CollectionUtils;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;
import java.util.stream.Collectors;


public class SMSJsonJacksonUtil {

    static ObjectMapper mapper;

    static {
        mapper = new ObjectMapper();
    }

    public static Character[] getCharacterArray(Object objectMap) {
        return mapper.convertValue(objectMap, Character[].class);
    }

    public static Set getSetFromJsonArray(Object objectMap) {
        return mapper.convertValue(objectMap, Set.class);
    }

    public static Set getLongSetFromJsonArray(Object objectMap) {
        Set<Integer> integerSet = mapper.convertValue(objectMap, Set.class);
        if (CollectionUtils.isEmpty(integerSet)) {
            return null;
        }
        return integerSet.stream()
                .map(Long::valueOf)
                .collect(Collectors.toSet());
    }

    public static Object convertMapToObject(Object objectMap, Class objectClass) {
        return mapper.convertValue(objectMap, objectClass);
    }

    public static List convertObjectToMapList(Object object) {
        return mapper.convertValue(object, new TypeReference<List<HashMap>>() {
        });
    }

    public static Set convertObjectToMapSet(Object object) {
        return mapper.convertValue(object, new TypeReference<Set<HashMap>>() {
        });
    }

    public static Map convertObjectToMap(Object object) {
        return mapper.convertValue(object, new TypeReference<HashMap>() {
        });
    }

    public static String convertObjectToJsonString(Object object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Map convertJSONToObject(String data) {
        try {
            return mapper.readValue(data, HashMap.class);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    public static Object convertJSONToObject(String data, Class objectClass) {
        try {
            return mapper.readValue(data, objectClass);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    public static List convertFileJsonContentToList(InputStream inputStream) throws IOException {
        byte[] bData = FileCopyUtils.copyToByteArray(inputStream);
        String data = new String(bData, StandardCharsets.UTF_8);
        List items = mapper.readValue(data, List.class);
        inputStream.close();
        return items;
    }
}
