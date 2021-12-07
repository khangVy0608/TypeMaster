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
    private static String CHART_DATA = "D:\\Learning\\TypeMaster\\src\\main\\java\\org\\SpecikMan\\data\\PracticeDatas\\chartData.txt";
    private static String USER_GROUP = "D:\\Learning\\TypeMaster\\src\\main\\java\\org\\SpecikMan\\data\\RankDatas\\Group.txt";
    private static String ROUND = "D:\\Learning\\TypeMaster\\src\\main\\java\\org\\SpecikMan\\data\\RankDatas\\Round.txt";
    private static String NOT_TYPED_RANK = "D:\\Learning\\TypeMaster\\src\\main\\java\\org\\SpecikMan\\data\\RankDatas\\notTyped.txt";
    private static String ORIGINAL_RANK = "D:\\Learning\\TypeMaster\\src\\main\\java\\org\\SpecikMan\\data\\RankDatas\\origin.txt";
    private static String TYPED_RANK = "D:\\Learning\\TypeMaster\\src\\main\\java\\org\\SpecikMan\\data\\RankDatas\\typed.txt";
    private static String PLAY_RESULT_RANK = "D:\\Learning\\TypeMaster\\src\\main\\java\\org\\SpecikMan\\data\\RankDatas\\PlayResult.txt";
    private static String CHART_DATA_RANK = "D:\\Learning\\TypeMaster\\src\\main\\java\\org\\SpecikMan\\data\\RankDatas\\chartData.txt";

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

    public static String getUserGroup(){
        return USER_GROUP;
    }

    public static String getRound(){ return ROUND; }

    public static String getNotTypedRank() {
        return NOT_TYPED_RANK;
    }

    public static String getOriginalRank() {
        return ORIGINAL_RANK;
    }

    public static String getTypedRank() {
        return TYPED_RANK;
    }

    public static String getPlayResultRank() {
        return PLAY_RESULT_RANK;
    }

    public static String getChartDataRank() {
        return CHART_DATA_RANK;
    }

    public FilePath() {
    }
}
