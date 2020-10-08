package bih.in.e_niwas.entity;

import org.ksoap2.serialization.SoapObject;

public class Sub_DivisionList {

    private String Dist_code;
    private String Sub_div_code;
    private String Sub_div_name;
    private String Sub_div_unique_code;

    public static Class<Sub_DivisionList> SUBDIVISION_LIST= Sub_DivisionList.class;
    public Sub_DivisionList(SoapObject final_object) {
       // this.CircleId=final_object.getProperty("CircleId").toString();
//        this.DivId=final_object.getProperty("DivId").toString();
//        this.DivName=final_object.getProperty("DivName").toString();
       // this.WingID=final_object.getProperty("WingID").toString();
    }

    public Sub_DivisionList() {

    }

    public String getDist_code() {
        return Dist_code;
    }

    public void setDist_code(String dist_code) {
        Dist_code = dist_code;
    }

    public String getSub_div_code() {
        return Sub_div_code;
    }

    public void setSub_div_code(String sub_div_code) {
        Sub_div_code = sub_div_code;
    }

    public String getSub_div_name() {
        return Sub_div_name;
    }

    public void setSub_div_name(String sub_div_name) {
        Sub_div_name = sub_div_name;
    }

    public String getSub_div_unique_code() {
        return Sub_div_unique_code;
    }

    public void setSub_div_unique_code(String sub_div_unique_code) {
        Sub_div_unique_code = sub_div_unique_code;
    }

    public static Class<Sub_DivisionList> getSubdivisionList() {
        return SUBDIVISION_LIST;
    }

    public static void setSubdivisionList(Class<Sub_DivisionList> subdivisionList) {
        SUBDIVISION_LIST = subdivisionList;
    }
}
