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
public class Nozzle {
    private EnuPosition position;
    private boolean isSpraying;
    private EnuPosition leftEdge;
    private EnuPosition rightEdge;
    private double sprayWidth;
    private int nozzleNumber; //nozzle number from left edge of implement
    
    //creates nozzle based on relative position number
    public Nozzle(int nozzleNumber){
        this.nozzleNumber = nozzleNumber;
        this.position = new EnuPosition();
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
     * 
     * Calculates left edge position based on width and current position of nozzle
     * @return leftEdge position
     **/
    public EnuPosition getLeftEdge(){
        return leftEdge;
    }
    
    /**
     * Calculates right edge position based on width and current position of nozzle
     * @return rightEdge Position
     **/
    public EnuPosition getRightEdge(){
        return rightEdge;
    }

}
