import org.springframework.util.StringUtils;

import java.sql.Time;
import java.util.*;

public class CustomMap extends HashMap<String, Object> {

    public Object getPropertyValue(String key) {
        if (this.containsKey(key)) {
            return this.get(key);
        }
        return null;
    }

    public Object getCharPropertyValue(String key) {
        if (this.containsKey(key)) {
            return this.get(key).toString().charAt(0);
        }
        return null;
    }

    public Long getLongPropertyValue(String key) {
        Long returnValue = null;
        if (this.containsKey(key)) {
            returnValue = Long.valueOf(this.get(key).toString());
        }
        return returnValue;
    }

    public Integer getIntPropertyValue(String key) {
        Integer returnValue = null;
        if (this.containsKey(key)) {
            returnValue = Integer.valueOf(this.get(key).toString());
        }
        return returnValue;
    }

    public Byte getBytePropertyValue(String key) {
        Byte returnValue = null;
        if (this.containsKey(key)) {
            returnValue = Byte.valueOf(this.get(key).toString());
        }
        return returnValue;
    }

    public Double getDoublePropertyValue(String key) {
        Double returnValue = null;
        if (this.containsKey(key)) {
            returnValue = Double.valueOf(this.get(key).toString());
        }
        return returnValue;
    }

    public Boolean getBooleanPropertyValue(String key) {
        Boolean returnValue = new Boolean(false);
        if (this.containsKey(key)) {
            returnValue = Boolean.parseBoolean(this.get(key).toString());
        }
        return returnValue;
    }

    public String getStringPropertyValue(String key) {
        String propertyValue = null;
        if (this.containsKey(key)) {
            propertyValue = this.get(key).toString();
        }
        return propertyValue;
    }

    public Time getTimePropertyValue(String key) {
        Time propertyValue = null;
        if (this.containsKey(key)) {
            propertyValue = Time.valueOf(this.get(key).toString());
        }
        return propertyValue;
    }

    public Date getDatePropertyValue(String key) {
        Date propertyValue = null;
        if (this.containsKey(key)) {
            propertyValue = SMSDateTimeUtils.getDateFromString((String) this.get(key));
        }
        return propertyValue;
    }

    public Set<Long> getLongSetFromString(String key) {
        Set<Long> propertyValue = new HashSet<>();
        if (this.containsKey(key) && !StringUtils.isEmpty(this.getStringPropertyValue(key))) {
            String value = this.getStringPropertyValue(key);
            String[] arr = value.split(",");
            for (String a : arr) {
                propertyValue.add(Long.parseLong(a));
            }
        }
        return propertyValue;
    }
}
