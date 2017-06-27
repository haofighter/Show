package com.myself.show.show.net.responceBean;

/**
 * Created by Administrator on 2017/6/27.
 */

public class MusicPath {


    /**
     * data : {"id":28445467,"url":"http://m10.music.126.net/20170627104127/acfda5c33afc016d6efc09d367e40483/ymusic/fe64/c17f/54cc/64c15c1430e55bf2c8093f0b47dc39f4.mp3","br":320000,"size":6732382,"md5":"64c15c1430e55bf2c8093f0b47dc39f4","code":200,"expi":1200,"type":"mp3","gain":-1.34,"fee":0,"uf":null,"payed":0,"flag":0,"canExtend":false}
     * code : 200
     */

    private DataBean data;
    private int code;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public static class DataBean {
        /**
         * id : 28445467
         * url : http://m10.music.126.net/20170627104127/acfda5c33afc016d6efc09d367e40483/ymusic/fe64/c17f/54cc/64c15c1430e55bf2c8093f0b47dc39f4.mp3
         * br : 320000
         * size : 6732382
         * md5 : 64c15c1430e55bf2c8093f0b47dc39f4
         * code : 200
         * expi : 1200
         * type : mp3
         * gain : -1.34
         * fee : 0
         * uf : null
         * payed : 0
         * flag : 0
         * canExtend : false
         */

        private int id;
        private String url;
        private int br;
        private int size;
        private String md5;
        private int code;
        private int expi;
        private String type;
        private double gain;
        private int fee;
        private Object uf;
        private int payed;
        private int flag;
        private boolean canExtend;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getBr() {
            return br;
        }

        public void setBr(int br) {
            this.br = br;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public String getMd5() {
            return md5;
        }

        public void setMd5(String md5) {
            this.md5 = md5;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public int getExpi() {
            return expi;
        }

        public void setExpi(int expi) {
            this.expi = expi;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public double getGain() {
            return gain;
        }

        public void setGain(double gain) {
            this.gain = gain;
        }

        public int getFee() {
            return fee;
        }

        public void setFee(int fee) {
            this.fee = fee;
        }

        public Object getUf() {
            return uf;
        }

        public void setUf(Object uf) {
            this.uf = uf;
        }

        public int getPayed() {
            return payed;
        }

        public void setPayed(int payed) {
            this.payed = payed;
        }

        public int getFlag() {
            return flag;
        }

        public void setFlag(int flag) {
            this.flag = flag;
        }

        public boolean isCanExtend() {
            return canExtend;
        }

        public void setCanExtend(boolean canExtend) {
            this.canExtend = canExtend;
        }
    }
}
