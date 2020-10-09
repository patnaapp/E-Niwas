package bih.in.e_niwas.entity;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import java.util.Hashtable;

public class Item_MasterEntity implements KvmSerializable {

    public static Class<Item_MasterEntity> ITEMMASTER_CLASS= Item_MasterEntity.class;
    private String _Group_ID;
    private String _Item_ID;
    private String _Item_Name;
    private String _Item_Order;
    private String Group_ID;
    private String Item_ID;
    private String Item_Name;
    private String Item_Order;

    public Item_MasterEntity(SoapObject sobj) {
        this._Group_ID=sobj.getProperty("_Group_ID").toString();
        this._Item_ID=sobj.getProperty("_Item_ID").toString();
        this._Item_Name=sobj.getProperty("_Item_Name").toString();
        this._Item_Order=sobj.getProperty("_Item_Order").toString();
        this.Group_ID=sobj.getProperty("Group_ID").toString();
        this.Item_ID=sobj.getProperty("Item_ID").toString();
        this.Item_Name=sobj.getProperty("Item_Name").toString();
        this.Item_Order=sobj.getProperty("Item_Order").toString();

    }

    public Item_MasterEntity() {

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

    public static Class<Item_MasterEntity> getItemmasterClass() {
        return ITEMMASTER_CLASS;
    }

    public static void setItemmasterClass(Class<Item_MasterEntity> itemmasterClass) {
        ITEMMASTER_CLASS = itemmasterClass;
    }

    public String get_Group_ID() {
        return _Group_ID;
    }

    public void set_Group_ID(String _Group_ID) {
        this._Group_ID = _Group_ID;
    }

    public String get_Item_ID() {
        return _Item_ID;
    }

    public void set_Item_ID(String _Item_ID) {
        this._Item_ID = _Item_ID;
    }

    public String get_Item_Name() {
        return _Item_Name;
    }

    public void set_Item_Name(String _Item_Name) {
        this._Item_Name = _Item_Name;
    }

    public String get_Item_Order() {
        return _Item_Order;
    }

    public void set_Item_Order(String _Item_Order) {
        this._Item_Order = _Item_Order;
    }

    public String getGroup_ID() {
        return Group_ID;
    }

    public void setGroup_ID(String group_ID) {
        Group_ID = group_ID;
    }

    public String getItem_ID() {
        return Item_ID;
    }

    public void setItem_ID(String item_ID) {
        Item_ID = item_ID;
    }

    public String getItem_Name() {
        return Item_Name;
    }

    public void setItem_Name(String item_Name) {
        Item_Name = item_Name;
    }

    public String getItem_Order() {
        return Item_Order;
    }

    public void setItem_Order(String item_Order) {
        Item_Order = item_Order;
    }
}
