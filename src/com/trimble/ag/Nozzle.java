/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trimble.ag;
import com.trimble.ag.EnuPosition;
/**
 *
 * @author clay
 */
public  class Nozzle {
    private EnuPosition position;
    private boolean isSpraying;
    
    
    /**
     * Create nozzle with initial EnuPosition.
     * @param position 
     */
    public Nozzle(EnuPosition position){
        this.position = position;
        this.isSpraying = false;
    }
    
    public void turnNozzleOn(){
        this.isSpraying = true;
    }
    public void turnNozzleOff(){
        this.isSpraying = false;
    }
    public boolean isSpraying(){
        return isSpraying;
    }
    public double getEast(){
        return position.getEast();
    }
    public double getUp(){
        return position.getUp();
    }
    public double getNorth(){
        return position.getNorth();
    }
    public EnuPosition getPosition(){
        return position;
    }
    public void setPosition(EnuPosition newPosition){
        this.position = newPosition;
    }
    
    /**
     * Determines if nozzle is inside given polygon.  The nozzle position will 
     * be used to determine if the nozzle is inside the given polygon
     * 
     * @param poly
     * @return boolean - is nozzle inside polygon
     */
    public boolean isNozzleInPolygon(Polygon poly){
        return true;
    }
}
