package org.SpecikMan.Entity;

public class FilePath {
    private static String LOGIN_ACC = "D:\\Learning\\TypeMaster\\src\\main\\java\\org\\SpecikMan\\data\\loginAcc.txt";
    private static String NOT_TYPED = "D:\\Learning\\TypeMaster\\src\\main\\java\\org\\SpecikMan\\data\\notTyped.txt";
    private static String ORIGINAL = "D:\\Learning\\TypeMaster\\src\\main\\java\\org\\SpecikMan\\data\\origin.txt";
    private static String PLAY_LEVEL = "D:\\Learning\\TypeMaster\\src\\main\\java\\org\\SpecikMan\\data\\playLevel.txt";
    private static String TYPED = "D:\\Learning\\TypeMaster\\src\\main\\java\\org\\SpecikMan\\data\\typed.txt";
    private static String FORGOT_ID = "D:\\Learning\\TypeMaster\\src\\main\\java\\org\\SpecikMan\\data\\ForgotPassword_id.txt";
    private static String CHOOSE_PROFILE = "D:\\Learning\\TypeMaster\\src\\main\\java\\org\\SpecikMan\\data\\chooseProfile.txt";

    public static String getChooseProfile() {
        return CHOOSE_PROFILE;
    }

    public static String getForgotId() {
        return FORGOT_ID;
    }

    public static String getLoginAcc() {
        return LOGIN_ACC;
    }

    public static String getNotTyped() {
        return NOT_TYPED;
    }

    public static String getORIGINAL() {
        return ORIGINAL;
    }

    public static String getPlayLevel() {
        return PLAY_LEVEL;
    }

    public static String getTYPED() {
        return TYPED;
    }

    public FilePath(){

    }
}
