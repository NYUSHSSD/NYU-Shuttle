package com.shanghai.nyushuttle;

import android.text.Spanned;

/**
 * Created by Alexandru on 10/10/2014.
 */
public class RouteClass {
    public Spanned route_number;
    public Spanned going_from;
    public Spanned going_to;
    public Spanned days;
 //   public String departure;
//    public String arrival;

    public RouteClass(Spanned route_number, Spanned going_from, Spanned going_to, Spanned days) {
        this.route_number = route_number;
        this.going_from = going_from;
        this.going_to = going_to;
        this.days = days;
     //   this.departure = departure;
     //   this.arrival = arrival;
    }
}
