package com.myself.show.show.net.responceBean;

/**
 * @项目名: Jitu
 * @包名: com.itzwf.jitu.bean
 * @创建者: zhangwenfeng
 * @创建时间: 2015/11/23	13:49
 * @描述: 登陆返回的json
 */
public class Type {

    String industry;
    String id;

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Type{" +
                "industry='" + industry + '\'' +
                ", id='" + id + '\'' +
                '}';
    }


}
