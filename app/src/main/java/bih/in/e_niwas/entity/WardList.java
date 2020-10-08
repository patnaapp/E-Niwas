package bih.in.e_niwas.entity;

import org.ksoap2.serialization.SoapObject;

public class WardList {

    private String Ward_code;

    private String Ward_name;


    public static Class<WardList> WARD_LIST= WardList.class;
    public WardList(SoapObject final_object) {
        // this.CircleId=final_object.getProperty("CircleId").toString();
//        this.DivId=final_object.getProperty("DivId").toString();
//        this.DivName=final_object.getProperty("DivName").toString();
        // this.WingID=final_object.getProperty("WingID").toString();
    }

    public WardList() {
        // this.CircleId=final_object.getProperty("CircleId").toString();
//        this.DivId=final_object.getProperty("DivId").toString();
//        this.DivName=final_object.getProperty("DivName").toString();
        // this.WingID=final_object.getProperty("WingID").toString();
    }


    public String getWard_code() {
        return Ward_code;
    }

    public void setWard_code(String ward_code) {
        Ward_code = ward_code;
    }

    public String getWard_name() {
        return Ward_name;
    }

    public void setWard_name(String ward_name) {
        Ward_name = ward_name;
    }

    public static Class<WardList> getWardList() {
        return WARD_LIST;
    }

    public static void setWardList(Class<WardList> wardList) {
        WARD_LIST = wardList;
    }
}
