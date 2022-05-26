package com.codejie.pms.entity;

import java.sql.Date;

public class Penetr_check {
    private String userName;
    private String phone;
    private String terminalName;
    private String terminalType;
    private String APPID;
    private String counter;
//    private String macAddress;
    private String  currdate;

    public void setUserName(String userName){
        this.userName = userName;
    }

    public String getUserName(){
        return userName;
    }

    public void setPhone(String phone){
        this.phone = phone;
    }

    public String getPhone(){
        return phone;
    }

    public void setTerminalName(String terminalName){
        this.terminalName = terminalName;
    }

    public String getTerminalName(){
        return terminalName;
    }

    public void setTerminalType(String terminalType){
        this.terminalType = terminalType;
    }

    public String getTerminalType(){
        return terminalType;
    }

    public void setAPPID(String APPID){
        this.APPID = APPID;
    }

    public String getAPPID(){
        return APPID;
    }

    public void setCounter(String counter){
        this.counter = counter;
    }

    public String getCounter(){
        return counter;
    }

//    public void setMacAddress(String macAddress){
//        this.macAddress = macAddress;
//    }
//
//    public String getMacAddress(){
//        return macAddress;
//    }

    public void setCurrdate(String currdate){
        this.currdate = currdate;
    }

    public String getCurrdate(){
        return currdate;
    }


}
