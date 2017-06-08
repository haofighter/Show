package com.myself.show.show.net.responceBean;

import java.util.List;

/**
 * @项目名: Jitu
 * @包名: com.itzwf.jitu.bean
 * @创建者: zhangwenfeng
 * @创建时间: 2015/11/23	13:49
 * @描述: 登陆返回的json
 */
public class HangYeBean {


    //    /**
//     * headAttr : http://api.bihaohuo.com.cn/Uploads/Image/20160521/5740126d1ace9.png
//     * token : a47dd5c8412163f5228087822820c4a6
//     * aliasname : TNqT5875
//     * resCode : SUCCESS
//     * resMsg : 登录成功
//     */
//
//    private String headAttr;
//    private String token;
//    private String aliasname;
//    private String resCode;
//    private String resMsg;
//
//    public String getHeadAttr() {
//        return headAttr;
//    }
//
//    public void setHeadAttr(String headAttr) {
//        this.headAttr = headAttr;
//    }
//
//    public String getToken() {
//        return token;
//    }
//
//    public void setToken(String token) {
//        this.token = token;
//    }
//
//    public String getAliasname() {
//        return aliasname;
//    }
//
//    public void setAliasname(String aliasname) {
//        this.aliasname = aliasname;
//    }
//
//    public String getResCode() {
//        return resCode;
//    }
//
//    public void setResCode(String resCode) {
//        this.resCode = resCode;
//    }
//
//    public String getResMsg() {
//        return resMsg;
//    }
//
//    public void setResMsg(String resMsg) {
//        this.resMsg = resMsg;
//    }
    String ErrCode;
    String ErrMsg;
    List<Type> Response;

    public String getErrCode() {
        return ErrCode;
    }

    public void setErrCode(String errCode) {
        ErrCode = errCode;
    }

    public String getErrMsg() {
        return ErrMsg;
    }

    public void setErrMsg(String errMsg) {
        ErrMsg = errMsg;
    }

    public List<Type> getResponse() {
        return Response;
    }

    public void setResponse(List<Type> response) {
        Response = response;
    }

    public HangYeBean() {
        super();
    }

    @Override
    public String toString() {
        return "HangYeBean{" +
                "ErrCode='" + ErrCode + '\'' +
                ", ErrMsg='" + ErrMsg + '\'' +
                ", Response=" + Response +
                '}';
    }


//
//    String mobile;
//    String password;
//    String sex;
//    String age;
//    String city;
//    String industry_id;
//    String head_portrait;
//    String user_name;
//
//    public String getUser_name() {
//        return user_name;
//    }
//
//    public void setUser_name(String user_name) {
//        this.user_name = user_name;
//    }
//
//    public String getHead_portrait() {
//        return head_portrait;
//    }
//
//    public void setHead_portrait(String head_portrait) {
//        this.head_portrait = head_portrait;
//    }
//
//    public String getIndustry_id() {
//        return industry_id;
//    }
//
//    public void setIndustry_id(String industry_id) {
//        this.industry_id = industry_id;
//    }
//
//    public String getCity() {
//        return city;
//    }
//
//    public void setCity(String city) {
//        this.city = city;
//    }
//
//    public String getAge() {
//        return age;
//    }
//
//    public void setAge(String age) {
//        this.age = age;
//    }
//
//    public String getSex() {
//        return sex;
//    }
//
//    public void setSex(String sex) {
//        this.sex = sex;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public String getMobile() {
//        return mobile;
//    }
//
//    public void setMobile(String mobile) {
//        this.mobile = mobile;
//    }
}
