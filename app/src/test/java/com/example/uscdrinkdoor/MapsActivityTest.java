package com.example.uscdrinkdoor;


import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;


@RunWith(JUnit4.class)
public class MapsActivityTest {

    String clicked = null;
    String traveloption = null;

    public void Marker_click(String s) { // first click, single
        if (clicked == null) {
            clicked = s;
        }
        else if (clicked.equals(s)) { // double click
            clicked = null;
        }
        else { // single click
            clicked = s;
        }
    }

    public void SelectWalking() { traveloption = "walking"; }
    public void SelectDriving(){ traveloption = "driving"; }

    @Test
    public void Test_On_Marker_Click_Logic(){

        // Simulate markers
        String a = "a";
        String b = "b";
        String c = "c";

        // Simulate single click on stores
        Marker_click(a);
        assertEquals(clicked, a);
        Marker_click(b);
        assertEquals(clicked, b);
        Marker_click(c);
        assertEquals(clicked, c);

        // Simulate double click on stores
        Marker_click(a);
        Marker_click(a);
        assertEquals(clicked, null);
        Marker_click(b);
        Marker_click(b);
        assertEquals(clicked, null);
        Marker_click(c);
        Marker_click(c);
        assertEquals(clicked, null);
    }

    @Test
    public void Test_Select_Different_Travel_Options(){
        SelectWalking();
        assertEquals(traveloption, "walking");
        SelectWalking();
        assertEquals(traveloption, "walking");
        SelectDriving();
        assertEquals(traveloption, "driving");
        SelectDriving();
        assertEquals(traveloption, "driving");

    }
}