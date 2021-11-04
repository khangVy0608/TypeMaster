package org.SpecikMan.Entity;

public class FilePath {
    private static String LOGIN_ACC = "C:\\Users\\PC\\IdeaProjects\\TypeMaster\\target\\classes\\org\\SpecikMan\\data\\loginAcc.txt";
    private static String NOT_TYPED = "C:\\Users\\PC\\IdeaProjects\\TypeMaster\\target\\classes\\org\\SpecikMan\\data\\notTyped.txt";
    private static String ORIGINAL = "C:\\Users\\PC\\IdeaProjects\\TypeMaster\\target\\classes\\org\\SpecikMan\\data\\origin.txt";
    private static String PLAY_LEVEL = "C:\\Users\\PC\\IdeaProjects\\TypeMaster\\target\\classes\\org\\SpecikMan\\data\\playLevel.txt";
    private static String TYPED = "C:\\Users\\PC\\IdeaProjects\\TypeMaster\\target\\classes\\org\\SpecikMan\\data\\typed.txt";
    private static String FORGOT_ID = "C:\\Users\\PC\\IdeaProjects\\TypeMaster\\target\\classes\\org\\SpecikMan\\data\\ForgotPassword_id.txt";
    private static String CHOOSE_PROFILE = "C:\\Users\\PC\\IdeaProjects\\TypeMaster\\target\\classes\\org\\SpecikMan\\data\\chooseProfile.txt";
    private static String PLAY_RESULT = "C:\\Users\\PC\\IdeaProjects\\TypeMaster\\src\\main\\java\\org\\SpecikMan\\data\\PracticeDatas\\PlayResult.txt";
    private static String RETRY_OR_MENU = "C:\\Users\\PC\\IdeaProjects\\TypeMaster\\src\\main\\java\\org\\SpecikMan\\data\\PracticeDatas\\RetryOrMenu.txt";
    private static String CHART_DATA = "C:\\Users\\PC\\IdeaProjects\\TypeMaster\\src\\main\\java\\org\\SpecikMan\\data\\PracticeDatas\\chartData.txt";

    public static String getChartData() {
        return CHART_DATA;
    }

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
