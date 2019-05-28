package com.yuemai.game34999.data.bean;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/12/29  10:50
 * @email : 923080261@qq.com
 * @description :配置信息：行业、婚姻、资金
 */
public class AppConfiguration {

    public static class Configuration{
        /**
         * 行业配置
         */
        public static final String USER_INDUSTRY = "getuserindustry";
        /**
         * 婚姻配置
         */
        public static final String MARITAL_STATUS = "getmaritalstatus";
        /**
         * 资金配置
         */
        public static final String CAPITALPOSITION = "getcapitalposition";
        /**
         * 学历配置
         */
        public static final String EDUCATION = "geteducation";
    }




    private int id;
    private String dname;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDname() {
        return dname;
    }

    public void setDname(String dname) {
        this.dname = dname;
    }

    @Override
    public String toString() {
        return "AppConfiguration{" +
                "id=" + id +
                ", dname='" + dname + '\'' +
                '}';
    }
}
