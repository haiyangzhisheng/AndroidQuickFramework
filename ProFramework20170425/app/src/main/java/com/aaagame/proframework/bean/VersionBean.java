package com.aaagame.proframework.bean;

/**
 * Created by Administrator on 2017/2/22.
 */

public class VersionBean {
    private boolean compel;//": false,   --是否强制更新
    private String plat;//": "android",
    private String remark;//": "1.最新版安卓端",   --更新说明
    private String url;//": "/download/xinyiliwu.apk",  --下载地址，或appstore地址
    private String version;//": "1.1"   --最新版本号

    public boolean isCompel() {
        return compel;
    }

    public void setCompel(boolean compel) {
        this.compel = compel;
    }

    public String getPlat() {
        return plat;
    }

    public void setPlat(String plat) {
        this.plat = plat;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
