package com.sug.minifinal;

public class DB {
    private String name;
    private String email;
    private String address;
    private String password;
    private String bloodtype;
    private String id;
    private String bday;
    private String aadhaar;
    private String phone;
    private String emergency;

    public DB() {
    }

    public DB(String id, String name, String email, String address, String password, String bloodtype, String aadhaar, String phone, String emergency, String bday) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.address = address;
        this.password = password;
        this.bday = bday;
        this.bloodtype = bloodtype;
        this.aadhaar = aadhaar;
        this.phone = phone;
        this.emergency = emergency;
    }
//
//    public DB(String name, String email, String bloodtype, String id, String bday, String aadhaar, String phone) {
//        this.name = name;
//        this.email = email;
//        this.bloodtype = bloodtype;
//        this.id = id;
//        this.bday = bday;
//        this.aadhaar = aadhaar;
//        this.phone = phone;
//    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String getPassword() {
        return password;
    }

    public String getBloodtype() {
        return bloodtype;
    }

    public String getAadhaar() {
        return aadhaar;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmergency() {
        return emergency;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setBloodtype(String bloodtype) {
        this.bloodtype = bloodtype;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setBday(String bday) {
        this.bday = bday;
    }

    public void setAadhaar(String aadhaar) {
        this.aadhaar = aadhaar;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmergency(String emergency) {
        this.emergency = emergency;
    }
}
