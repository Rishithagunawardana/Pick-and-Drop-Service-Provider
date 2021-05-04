package com.example.pickanddropsp;

public class set {
    String nme, STATUS, TYPE, FEE, DRNAME, DRMOBILE;

    public set(String nme, String status, String type, String fee, String drname, String drmobile) {

        this.nme = nme;
        this.STATUS = status;
        this.TYPE = type;
        this.FEE = fee;
        this.DRNAME = drname;
        this.DRMOBILE = drmobile;


    }

    public String getNme() {
        return nme;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public String getTYPE() {
        return TYPE;
    }

    public String getFEE() {
        return FEE;
    }

    public String getDRNAME() {
        return DRNAME;
    }

    public String getDRMOBILE() {
        return DRMOBILE;
    }


}