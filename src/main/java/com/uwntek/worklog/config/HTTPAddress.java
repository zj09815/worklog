package com.uwntek.worklog.config;

public enum HTTPAddress {
//    NeedAddress("http://172.16.3.196");
    NeedAddress("http://47.96.19.12");
    private String address;

    HTTPAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }
}
