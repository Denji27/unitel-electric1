package la.com.unitel.entity.constant;

import java.util.Arrays;

/**
 * @author : Tungct
 * @since : 12/23/2022, Fri
 **/
public enum Gender {
    MALE, FEMALE;

    public static Boolean isValid(String value){
        return Arrays.stream(Gender.values()).anyMatch(gender -> gender.name().equals(value));
    }

    public static Gender findByName(String value) {
        return Arrays.stream(Gender.values()).filter(gender -> gender.name().equals(value)).findFirst().orElse(null);
    }
}
