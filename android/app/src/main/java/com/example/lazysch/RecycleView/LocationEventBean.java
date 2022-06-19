package com.example.lazysch.RecycleView;

public class LocationEventBean {
    private String locationname;
    private String addr;

    public LocationEventBean(String locationname, String addr) {
        this.locationname = locationname;
        this.addr = addr;
    }

    public String getLocationname() {
        return locationname;
    }

    public void setLocationname(String locationname) {
        this.locationname = locationname;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }
}
