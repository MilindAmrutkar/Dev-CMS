package com.devwellupsolution.dev_cms.Model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by OnesTech on 24/08/2017.
 */

public class Customer implements Parcelable{
    private String name, address, phone, aadhaar, pancard, license, email, joinDate, dob, city, state, pincode, marriageStatus, anniversaryDate, religion, remark;

    public Customer() {

    }

    protected Customer(Parcel in) {
        name = in.readString();
        address = in.readString();
        phone = in.readString();
        aadhaar = in.readString();
        pancard = in.readString();
        license = in.readString();
        email = in.readString();
        joinDate = in.readString();
        dob = in.readString();
        city = in.readString();
        state = in.readString();
        pincode = in.readString();
        marriageStatus = in.readString();
        anniversaryDate = in.readString();
        religion = in.readString();
        remark = in.readString();
    }

    public static final Creator<Customer> CREATOR = new Creator<Customer>() {
        @Override
        public Customer createFromParcel(Parcel in) {
            return new Customer(in);
        }

        @Override
        public Customer[] newArray(int size) {
            return new Customer[size];
        }
    };

    public String getJSON() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.accumulate("name", name);
        jsonObject.accumulate("address", address);
        jsonObject.accumulate("phone", phone);
        jsonObject.accumulate("aadhaar", aadhaar);
        jsonObject.accumulate("pancard", pancard);
        jsonObject.accumulate("license", license);
        jsonObject.accumulate("email", email);
        jsonObject.accumulate("join_date", joinDate);
        jsonObject.accumulate("dob", dob);
        jsonObject.accumulate("city", city);
        jsonObject.accumulate("state", state);
        jsonObject.accumulate("pincode", pincode);
        jsonObject.accumulate("marriage_status", marriageStatus);
        jsonObject.accumulate("anniversary_date", anniversaryDate);
        jsonObject.accumulate("religion", religion);
        jsonObject.accumulate("remark", remark);

        JSONObject rootObject = new JSONObject();
        rootObject.accumulate("customer", jsonObject);
        return rootObject.toString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAadhaar() {
        return aadhaar;
    }

    public void setAadhaar(String aadhaar) {
        this.aadhaar = aadhaar;
    }

    public String getPancard() {
        return pancard;
    }

    public void setPancard(String pancard) {
        this.pancard = pancard;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(String joinDate) {
        this.joinDate = joinDate;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getMarriageStatus() {
        return marriageStatus;
    }

    public void setMarriageStatus(String marriageStatus) {
        this.marriageStatus = marriageStatus;
    }

    public String getAnniversaryDate() {
        return anniversaryDate;
    }

    public void setAnniversaryDate(String anniversaryDate) {
        this.anniversaryDate = anniversaryDate;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(address);
        parcel.writeString(phone);
        parcel.writeString(aadhaar);
        parcel.writeString(pancard);
        parcel.writeString(license);
        parcel.writeString(email);
        parcel.writeString(joinDate);
        parcel.writeString(dob);
        parcel.writeString(city);
        parcel.writeString(state);
        parcel.writeString(pincode);
        parcel.writeString(marriageStatus);
        parcel.writeString(anniversaryDate);
        parcel.writeString(religion);
        parcel.writeString(remark);
    }
}
