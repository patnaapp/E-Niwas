package bih.in.e_niwas.entity;

import org.ksoap2.serialization.SoapObject;

public class DivisionList {

    private String DivId;
    private String DivName;
    private String CircleId;
    private String WingID;
    public static Class<DivisionList> DIVISION_LIST=DivisionList.class;
    public DivisionList(SoapObject final_object) {
       // this.CircleId=final_object.getProperty("CircleId").toString();
        this.DivId=final_object.getProperty("DivId").toString();
        this.DivName=final_object.getProperty("DivName").toString();
       // this.WingID=final_object.getProperty("WingID").toString();
    }

    public DivisionList() {

    }

    public String getDivId() {
        return DivId;
    }

    public void setDivId(String divId) {
        DivId = divId;
    }

    public String getDivName() {
        return DivName;
    }

    public void setDivName(String divName) {
        DivName = divName;
    }

    public String getCircleId() {
        return CircleId;
    }

    public void setCircleId(String circleId) {
        CircleId = circleId;
    }

    public String getWingID() {
        return WingID;
    }

    public void setWingID(String wingID) {
        WingID = wingID;
    }
}
