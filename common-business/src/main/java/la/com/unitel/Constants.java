package la.com.unitel;

/**
 * @author : Tungct
 * @since : 3/14/2023, Tue
 **/
public class Constants {
    public static String MYTEL_PHONE_PATTERN = "^((0|)(96)\\d{8}|[+]?95(96)\\d{8})$";
    public static String OOREDOO_PHONE_PATTERN = "^((0|)(995|996|997)\\d{7}|[+]?95(995|996|997)\\d{7})$";
    public static String TELENOR_PHONE_PATTERN = "^((0|)(975|976|977|978|979)\\d{7}|[+]?95(975|976|977|978|979)\\d{7})$";
    public static String MECTEL_PHONE_PATTERN = "^((0|)(930|931|932|933|936)\\d{6}|[+]?95(930|931|932|933|936)\\d{6}|(0|)(934)\\d{7}|[+]?95(934)\\d{7})$";
    public static String MPT_PHONE_PATTERN = "^((0|)(920|921|922|923|924|981|983|984|985|986|987)\\d{5}|[+]?95(920|921|922|923|924|981|983|984|985|986|987)\\d{5}|(0|)(941|943|95|973|991|949|947|9890|9891|9892|9893|9894|9895|9896|9897|9898|9899|939)\\d{6}|[+]?95(941|943|95|973|991|949|947|9890|9891|9892|9893|9894|9895|9896|9897|9898|9899|939)\\d{6}|(0|)(925|926|940|942|944|945|9447|9266|9267|9268)\\d{7}|[+]?95(925|926|940|942|944|945|9447|9266|9267|9268)\\d{7})$";
    public static String PHONE_NUMBER_PATTERN = "((0|)(9)\\d{7,9}|[+]?95(9)\\d{7,9})$";
    public static String PIN_REGEX = "^[0-9]{4,6}$";
    public static String OTP_REGEX = "^[0-9]{4,6}$";
    public static String FULL_NAME = "^.{1,50}$";
    public static String CONTENT = "^[a-zA-Z0-9 .,_\\\\-]{1,300}$";
    public static String ADDRESS = "^[a-zA-Z0-9 .,]{1,50}$";
    public static String PAPER_NUMBER = "^.{1,30}$";
    public static String MONEY = "[0-9]{1,10}";
    public static String CURRENCY = "104";
    public static String ACCOUNT_NO = "[0-9]{13,14}";
    public static String TRANSACTION_ID = "\\b[0-9a-f]{8}\\b-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-\\b[0-9a-f]{12}\\b";
    public static String DATE_OF_BIRTH = "([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))";
    public static String CODE_PATTERN = "^[A-Z0-9_\\\\-\\_]{2,60}$";
    public static String ENDUSER = "end-user";
    public static String READER = "edl-reader";
    public static String CASHIER = "edl-cashier";
    public static String ELECTRIC = "electric";
    public static String AVATAR_MALE_DEFAULT = "https://s3.mytel.com.mm/electric/fda4a624-7314-4a94-87cd-d82a8d3f2ce7-male-anonymous.png";
    public static String AVATAR_FEMALE_DEFAULT = "https://s3.mytel.com.mm/electric/b42b4a98-d3ee-4de6-8516-c80b5cf0c448-female-anonymous.png";

    public Constants() {
    }
}
