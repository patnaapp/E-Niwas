package bih.in.e_niwas.entity;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import java.io.Serializable;
import java.util.Hashtable;

public class NiwasInspectionEntity implements KvmSerializable, Serializable {

    public static Class<NiwasInspectionEntity> Asset_CLASS = NiwasInspectionEntity.class;

    private String Id;
    private String Asset_Id;
    private String Div_code;
    private String Div_name;
    private String Sub_Div_code;
    private String property_type;
    private String area_type;
    private String dist_code;
    private String dist_name;
    private String blk_name;
    private String blk_code;
    private String panchayat_code;
    private String panchayat_name;
    private String ward_id;
    private String ward_name;
    private String pincode;
    private String thana_no;
    private String kahta_no;
    private String khesra_no;
    private String chauhaddi_north;
    private String chauhaddi_south;
    private String chauhaddi_east;
    private String chauhaddi_west;
    private String land_area;
    private String no_of_trees;
    private String tree_details;
    private String is_there_building;
    private String admin_dept;
    private String building_name;
    private String building_type;
    private String building_is;
    private String gazeted_nongazeted;
    private String building_type_class;
    private String pool_building;
    private String plinth_area;
    private String builtup_area;
    private String office_details;
    private String year_of_completion;
    private String building_status;
    private String remarks;
    private String image1;
    private String image2;
    private String Lat1;
    private String Long1;
    private String Lat2;
    private String Long2;
    private String entryby;

    public NiwasInspectionEntity() {


    }

    public NiwasInspectionEntity(SoapObject res1) {

        if (res1.getProperty("id").toString().equalsIgnoreCase("anyType{}")){
            this.Asset_Id="";
        }
        else {
            this.Asset_Id = res1.getProperty("id").toString();
        }

        if (res1.getProperty("Division").toString().equalsIgnoreCase("anyType{}")){
            this.Div_code="";
        }
        else {
            this.Div_code = res1.getProperty("Division").toString();
        }

        this.Sub_Div_code=res1.getProperty("SubDivision").toString();
        this.property_type=res1.getProperty("Land_Type").toString();
        this.area_type=res1.getProperty("AreaType").toString();
        this.dist_code=res1.getProperty("District").toString();
      //  this.dist_name=res1.getProperty("DistName").toString();
        //this.blk_name=res1.getProperty("AssetBlock").toString();
        this.blk_code=res1.getProperty("AssetBlock").toString();
        this.panchayat_code=res1.getProperty("Panchayat").toString();
  //      this.panchayat_name=res1.getProperty("Panchayat").toString();
        this.ward_id=res1.getProperty("Ward").toString();
        //this.ward_name=res1.getProperty("Ward").toString();
        this.pincode=res1.getProperty("PINCode").toString();
        if (res1.getProperty("ThanaNo").toString().equalsIgnoreCase("anyType{}")){
            this.thana_no="";
        }
        else {
            this.thana_no = res1.getProperty("ThanaNo").toString();
        }

        if (res1.getProperty("Khata").toString().equalsIgnoreCase("anyType{}")){
            this.kahta_no="";
        }
        else {
            this.kahta_no = res1.getProperty("Khata").toString();
        }

        if (res1.getProperty("Khesra").toString().equalsIgnoreCase("anyType{}")){
            this.khesra_no="";
        }
        else {
            this.khesra_no = res1.getProperty("Khesra").toString();
        }

        if (res1.getProperty("Chauhaddi_N").toString().equalsIgnoreCase("anyType{}")){
            this.chauhaddi_north="";
        }
        else {
            this.chauhaddi_north = res1.getProperty("Chauhaddi_N").toString();
        }
        if (res1.getProperty("Chauhaddi_S").toString().equalsIgnoreCase("anyType{}"))
        {
            this.chauhaddi_south="";
        }
        else
            {
            this.chauhaddi_south = res1.getProperty("Chauhaddi_S").toString();
        }
        if (res1.getProperty("Chauhaddi_E").toString().equalsIgnoreCase("anyType{}"))
        {
            this.chauhaddi_east="";
        }
        else
            {
            this.chauhaddi_east = res1.getProperty("Chauhaddi_E").toString();
        }

        if (res1.getProperty("Chauhaddi_W").toString().equalsIgnoreCase("anyType{}"))
        {
            this.chauhaddi_west="";
        }
        else
            {
            this.chauhaddi_west = res1.getProperty("Chauhaddi_W").toString();
        }
        if (res1.getProperty("AreaInSqMeter").toString().equalsIgnoreCase("anyType{}"))
        {
            this.land_area="";
        }
        else {
            this.land_area = res1.getProperty("AreaInSqMeter").toString();
        }

        if (res1.getProperty("NoOfBigTree").toString().equalsIgnoreCase("anyType{}")){
            this.no_of_trees="";
        }
        else {
            this.no_of_trees = res1.getProperty("NoOfBigTree").toString();
        }

        if (res1.getProperty("DetailOfTree").toString().equalsIgnoreCase("anyType{}")){
            this.tree_details="";
        }
        else {
            this.tree_details = res1.getProperty("DetailOfTree").toString();
        }

        if (res1.getProperty("Boundary").toString().equalsIgnoreCase("anyType{}")){
            this.is_there_building="";
        }
        else {
            this.is_there_building = res1.getProperty("Boundary").toString();
        }

        if (res1.getProperty("Admin_Dept").toString().equalsIgnoreCase("anyType{}")){
            this.admin_dept="";
        }
        else {
            this.admin_dept = res1.getProperty("Admin_Dept").toString();
        }

        if (res1.getProperty("Building_Name").toString().equalsIgnoreCase("anyType{}")){
            this.building_name="";
        }
        else {
            this.building_name = res1.getProperty("Building_Name").toString();
        }
        if (res1.getProperty("BuildingType").toString().equalsIgnoreCase("anyType{}")){
            this.building_type="";
        }
        else {
            this.building_type = res1.getProperty("BuildingType").toString();
        }

        this.building_is=res1.getProperty("Building_Is").toString();
        this.gazeted_nongazeted=res1.getProperty("OfficeType").toString();
        this.building_type_class=res1.getProperty("Building_Type").toString();
        this.pool_building=res1.getProperty("Pool").toString();

        if (res1.getProperty("PlinthArea").toString().equalsIgnoreCase("anyType{}")){
            this.plinth_area="";
        }
        else {
            this.plinth_area = res1.getProperty("PlinthArea").toString();
        }

        if (res1.getProperty("OfficeDetail").toString().equalsIgnoreCase("anyType{}")){
            this.office_details="";
        }
        else {
            this.office_details = res1.getProperty("OfficeDetail").toString();
        }

        if (res1.getProperty("YearofCompl").toString().equalsIgnoreCase("anyType{}")){
            this.year_of_completion="";
        }
        else {
            this.year_of_completion = res1.getProperty("YearofCompl").toString();
        }


        this.building_status=res1.getProperty("Building_Status").toString();

        if (res1.getProperty("Remark").toString().equalsIgnoreCase("anyType{}")){
            this.remarks="";
        }
        else {
            this.remarks = res1.getProperty("Remark").toString();
        }

        this.image1=res1.getProperty("Image1").toString();
        this.image2=res1.getProperty("Image2").toString();
        this.Lat1=res1.getProperty("LatitudeImage1").toString();
        this.Lat2=res1.getProperty("LatitudeImage2").toString();
        this.Long1=res1.getProperty("LongitudeImage1").toString();
        this.Long2=res1.getProperty("LongitudeImage2").toString();

        if (res1.getProperty("BuilpupArea").toString().equalsIgnoreCase("anyType{}")){
            this.builtup_area="";
        }
        else {
            this.builtup_area = res1.getProperty("BuilpupArea").toString();
        }


    }

    public static Class<NiwasInspectionEntity> getAsset_CLASS() {
        return Asset_CLASS;
    }

    public static void setAsset_CLASS(Class<NiwasInspectionEntity> asset_CLASS) {
        Asset_CLASS = asset_CLASS;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getAsset_Id() {
        return Asset_Id;
    }

    public void setAsset_Id(String asset_Id) {
        Asset_Id = asset_Id;
    }

    public String getDiv_code() {
        return Div_code;
    }

    public void setDiv_code(String div_code) {
        Div_code = div_code;
    }

    public String getDiv_name() {
        return Div_name;
    }

    public void setDiv_name(String div_name) {
        Div_name = div_name;
    }

    public String getSub_Div_code() {
        return Sub_Div_code;
    }

    public void setSub_Div_code(String sub_Div_code) {
        Sub_Div_code = sub_Div_code;
    }

    public String getProperty_type() {
        return property_type;
    }

    public void setProperty_type(String property_type) {
        this.property_type = property_type;
    }

    public String getArea_type() {
        return area_type;
    }

    public void setArea_type(String area_type) {
        this.area_type = area_type;
    }

    public String getDist_code() {
        return dist_code;
    }

    public void setDist_code(String dist_code) {
        this.dist_code = dist_code;
    }

    public String getDist_name() {
        return dist_name;
    }

    public void setDist_name(String dist_name) {
        this.dist_name = dist_name;
    }

    public String getBlk_name() {
        return blk_name;
    }

    public void setBlk_name(String blk_name) {
        this.blk_name = blk_name;
    }

    public String getBlk_code() {
        return blk_code;
    }

    public void setBlk_code(String blk_code) {
        this.blk_code = blk_code;
    }

    public String getPanchayat_code() {
        return panchayat_code;
    }

    public void setPanchayat_code(String panchayat_code) {
        this.panchayat_code = panchayat_code;
    }

    public String getPanchayat_name() {
        return panchayat_name;
    }

    public void setPanchayat_name(String panchayat_name) {
        this.panchayat_name = panchayat_name;
    }

    public String getWard_id() {
        return ward_id;
    }

    public void setWard_id(String ward_id) {
        this.ward_id = ward_id;
    }

    public String getWard_name() {
        return ward_name;
    }

    public void setWard_name(String ward_name) {
        this.ward_name = ward_name;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getThana_no() {
        return thana_no;
    }

    public void setThana_no(String thana_no) {
        this.thana_no = thana_no;
    }

    public String getKahta_no() {
        return kahta_no;
    }

    public void setKahta_no(String kahta_no) {
        this.kahta_no = kahta_no;
    }

    public String getKhesra_no() {
        return khesra_no;
    }

    public void setKhesra_no(String khesra_no) {
        this.khesra_no = khesra_no;
    }

    public String getChauhaddi_north() {
        return chauhaddi_north;
    }

    public void setChauhaddi_north(String chauhaddi_north) {
        this.chauhaddi_north = chauhaddi_north;
    }

    public String getChauhaddi_south() {
        return chauhaddi_south;
    }

    public void setChauhaddi_south(String chauhaddi_south) {
        this.chauhaddi_south = chauhaddi_south;
    }

    public String getChauhaddi_east() {
        return chauhaddi_east;
    }

    public void setChauhaddi_east(String chauhaddi_east) {
        this.chauhaddi_east = chauhaddi_east;
    }

    public String getChauhaddi_west() {
        return chauhaddi_west;
    }

    public void setChauhaddi_west(String chauhaddi_west) {
        this.chauhaddi_west = chauhaddi_west;
    }

    public String getLand_area() {
        return land_area;
    }

    public void setLand_area(String land_area) {
        this.land_area = land_area;
    }

    public String getNo_of_trees() {
        return no_of_trees;
    }

    public void setNo_of_trees(String no_of_trees) {
        this.no_of_trees = no_of_trees;
    }

    public String getTree_details() {
        return tree_details;
    }

    public void setTree_details(String tree_details) {
        this.tree_details = tree_details;
    }

    public String getIs_there_building() {
        return is_there_building;
    }

    public void setIs_there_building(String is_there_building) {
        this.is_there_building = is_there_building;
    }

    public String getAdmin_dept() {
        return admin_dept;
    }

    public void setAdmin_dept(String admin_dept) {
        this.admin_dept = admin_dept;
    }

    public String getBuilding_name() {
        return building_name;
    }

    public void setBuilding_name(String building_name) {
        this.building_name = building_name;
    }

    public String getBuilding_type() {
        return building_type;
    }

    public void setBuilding_type(String building_type) {
        this.building_type = building_type;
    }

    public String getBuilding_is() {
        return building_is;
    }

    public void setBuilding_is(String building_is) {
        this.building_is = building_is;
    }

    public String getGazeted_nongazeted() {
        return gazeted_nongazeted;
    }

    public void setGazeted_nongazeted(String gazeted_nongazeted) {
        this.gazeted_nongazeted = gazeted_nongazeted;
    }

    public String getBuilding_type_class() {
        return building_type_class;
    }

    public void setBuilding_type_class(String building_type_class) {
        this.building_type_class = building_type_class;
    }

    public String getPool_building() {
        return pool_building;
    }

    public void setPool_building(String pool_building) {
        this.pool_building = pool_building;
    }

    public String getPlinth_area() {
        return plinth_area;
    }

    public void setPlinth_area(String plinth_area) {
        this.plinth_area = plinth_area;
    }

    public String getBuiltup_area() {
        return builtup_area;
    }

    public void setBuiltup_area(String builtup_area) {
        this.builtup_area = builtup_area;
    }

    public String getOffice_details() {
        return office_details;
    }

    public void setOffice_details(String office_details) {
        this.office_details = office_details;
    }

    public String getYear_of_completion() {
        return year_of_completion;
    }

    public void setYear_of_completion(String year_of_completion) {
        this.year_of_completion = year_of_completion;
    }

    public String getBuilding_status() {
        return building_status;
    }

    public void setBuilding_status(String building_status) {
        this.building_status = building_status;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getImage1() {
        return image1;
    }

    public void setImage1(String image1) {
        this.image1 = image1;
    }

    public String getImage2() {
        return image2;
    }

    public void setImage2(String image2) {
        this.image2 = image2;
    }

    public String getLat1() {
        return Lat1;
    }

    public void setLat1(String lat1) {
        Lat1 = lat1;
    }

    public String getLong1() {
        return Long1;
    }

    public void setLong1(String long1) {
        Long1 = long1;
    }

    public String getLat2() {
        return Lat2;
    }

    public void setLat2(String lat2) {
        Lat2 = lat2;
    }

    public String getLong2() {
        return Long2;
    }

    public void setLong2(String long2) {
        Long2 = long2;
    }

    public String getEntryby() {
        return entryby;
    }

    public void setEntryby(String entryby) {
        this.entryby = entryby;
    }

    @Override
    public Object getProperty(int i) {
        return null;
    }

    @Override
    public int getPropertyCount() {
        return 0;
    }

    @Override
    public void setProperty(int i, Object o) {

    }

    @Override
    public void getPropertyInfo(int i, Hashtable hashtable, PropertyInfo propertyInfo) {

    }
}
