/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ratingengine;

import java.sql.Time;
import java.sql.Date;

/**
 *
 * @author Acer
 */
public class Cdr {
  private int cdr_id;
  private String dial_a;
  private String dial_b;
  private int service_id;
  private float dur_vol_msg;
  private Time start_time;
  private Date start_date;

    public int getCdr_id() {
        return cdr_id;
    }

    public void setCdr_id(int cdr_id) {
        this.cdr_id = cdr_id;
    }

    public void setDial_a(String dial_a) {
        this.dial_a = dial_a;
    }

    public void setDial_b(String dial_b) {
        this.dial_b = dial_b;
    }

    public void setService_id(int service_id) {
        this.service_id = service_id;
    }

    public void setDur_vol_msg(float dur_vol_msg) {
        this.dur_vol_msg = dur_vol_msg;
    }

    public void setStart_time(Time start_time) {
        this.start_time = start_time;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public String getDial_a() {
        return dial_a;
    }

    public String getDial_b() {
        return dial_b;
    }

    public int getService_id() {
        return service_id;
    }

    public float getDur_vol_msg() {
        return dur_vol_msg;
    }

    public Time getStart_time() {
        return start_time;
    }

    public Date getStart_date() {
        return start_date;
    }
  
  
  





}
