package org.SpecikMan.Entity;

public class FilePath {
    private static String LOGIN_ACC = "D:\\Learning\\TypeMaster\\src\\main\\java\\org\\SpecikMan\\data\\LoginDatas\\loginAcc.txt";
    private static String NOT_TYPED = "D:\\Learning\\TypeMaster\\src\\main\\java\\org\\SpecikMan\\data\\PracticeDatas\\notTyped.txt";
    private static String ORIGINAL = "D:\\Learning\\TypeMaster\\src\\main\\java\\org\\SpecikMan\\data\\PracticeDatas\\origin.txt";
    private static String PLAY_LEVEL = "D:\\Learning\\TypeMaster\\src\\main\\java\\org\\SpecikMan\\data\\PracticeDatas\\playLevel.txt";
    private static String TYPED = "D:\\Learning\\TypeMaster\\src\\main\\java\\org\\SpecikMan\\data\\PracticeDatas\\typed.txt";
    private static String FORGOT_ID = "D:\\Learning\\TypeMaster\\src\\main\\java\\org\\SpecikMan\\data\\LoginDatas\\ForgotPassword_id.txt";
    private static String CHOOSE_PROFILE = "D:\\Learning\\TypeMaster\\src\\main\\java\\org\\SpecikMan\\data\\PracticeDatas\\chooseProfile.txt";
    private static String PLAY_RESULT = "D:\\Learning\\TypeMaster\\src\\main\\java\\org\\SpecikMan\\data\\PracticeDatas\\PlayResult.txt";
    private static String RETRY_OR_MENU = "D:\\Learning\\TypeMaster\\src\\main\\java\\org\\SpecikMan\\data\\PracticeDatas\\RetryOrMenu.txt";

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

    public static String getPlayResult() {
        return PLAY_RESULT;
    }

    public static String getRetryOrMenu() {
        return RETRY_OR_MENU;
    }

    public FilePath() {
    }
}
