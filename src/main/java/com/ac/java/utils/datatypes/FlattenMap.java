import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class FlattenMap {

    public static void main(String []args) {
        Map m = new HashMap();
        m.put("a.b",1);
        m.put("a.c",2);
        System.out.println(unFlatten(m, "."));
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
