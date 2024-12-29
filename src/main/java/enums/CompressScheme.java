package enums;

public enum CompressScheme {
    GZIP;

    public static boolean isValid(String scheme) {
        for (CompressScheme cs: values()) {
            if (cs.name().equalsIgnoreCase(scheme)) {
                return true;
            }
        }
        return false;
    }
}
