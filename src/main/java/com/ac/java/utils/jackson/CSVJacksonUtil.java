/*

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
        <dependency>
            <groupId>com.fasterxml.jackson.dataformat</groupId>
            <artifactId>jackson-dataformat-csv</artifactId>
            <version>2.11.1</version>
        </dependency>
       <dependency>
           <groupId>org.springframework</groupId>
           <artifactId>spring-core</artifactId>
           <version>2.5.6</version>
        </dependency>
*/

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.TimeZone;

/**
 * @author Avinash Chandwani
 */
public class SMSCsvJacksonUtil {

    static CsvMapper csvMapper;

    static {
        csvMapper = new CsvMapper();
    }

    public static List<HashMap> getRecords(InputStream is) {
        List<HashMap> records = new LinkedList<>();
        CsvSchema schema = CsvSchema.emptySchema().withHeader();
        ObjectReader oReader = csvMapper.readerWithSchemaFor(HashMap.class).with(schema);
        try (Reader reader = new InputStreamReader(is)) {
            MappingIterator<Object> mi = oReader.readValues(reader);
            while (mi.hasNext()) {
                Map HashMapRecord = (Map) mi.next();
                records.add(unFlatten(HashMapRecord, "."));
            }
        } catch (IOException e) {
            throw new Exception("Bad Request");
        }
        return records;
    }

    private static HashMap unFlatten(Map<String, Object> flattened,
                                    String delimiter) {
        HashMap unFlattened = new HashMap();
        for (String key : flattened.keySet()) {
            doUnFlatten(unFlattened, key, flattened.get(key), delimiter);
        }
        return unFlattened;
    }

    private static void doUnFlatten(HashMap current, String key,
                                    Object originalValue, String delimiter) {
        String[] parts = StringUtils.split(key, delimiter);
        for (int i = 0; i < parts.length; i++) {
            String part = parts[i];
            if (i == (parts.length - 1)) {
                current.put(part, originalValue);
                return;
            }
            HashMap nestedMap = (HashMap) current.get(part);
            if (nestedMap == null) {
                nestedMap = new HashMap();
                current.put(part, nestedMap);
            }
            current = nestedMap;
        }
    }
}
