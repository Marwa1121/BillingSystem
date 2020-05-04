/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ratingengine;

/**
 *
 * @author Acer
 */
public class customer {
  private int customer_id;
  private int profile_id;
  private int nid;
  private String name;
  private String msisdn;
  private String address;
  private String email;

    public void setRest_units(float rest_units) {
        this.rest_units = rest_units;
    }

    public float getRest_units() {
        return rest_units;
    }
  private float rest_units ;
  


    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public void setProfile_id(int profile_id) {
        this.profile_id = profile_id;
    }

    public void setNid(int nid) {
        this.nid = nid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public int getProfile_id() {
        return profile_id;
    }

    public int getNid() {
        return nid;
    }

    public String getName() {
        return name;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

  




    
}
