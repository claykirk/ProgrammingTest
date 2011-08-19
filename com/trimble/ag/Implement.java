package com.trimble.ag;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a mechanical piece of machinery for performing a field operation.
 */
public abstract class Implement implements TractorPositionListener {
        private static final int NOZZLE_CNT = 12;
	/** The width of the implement in metres. */
	private double width;
	/** The distance behind the tractor the implement travels. */
	private double distanceToTractor;
	/** The heading (in degrees) of the implement */
	private double heading;
	/** If the implement is currently applying material to the field. */
	private boolean isSpraying;
	/** The current position of the implement. */
	private EnuPosition position;
	/** The left hand edge of the implement (width*0.5 away from center. */
	private EnuPosition leftEdgePosition;
	/** The right hand edge of the implement (width*0.5 away from center. */
	private EnuPosition rightEdgePosition;
	/** The area this implement has applied. */
	private AppliedArea appliedArea; 
        
        /**the nozzles the implement holds **/
        private Nozzle[] nozzles;

	/** 
	 * Initialize an implement with the width and distance to tractor.
	 * 
	 * @param width
	 *            The width of the implement.
	 * @param distanceToTractor
	 *            The distance to the tractor.
	 */
	public Implement(double width, double distanceToTractor) {
		this.width = width;
		this.distanceToTractor = distanceToTractor;
                initializeNozzles();
		heading = 0.0;
		isSpraying = false;
	}
        /**
         * Creates the array of nozzles that the implement holds.  Nozzle positions
         * will be calculated based on implement pos;
         * 
         */
        private void initializeNozzles(){
            nozzles = new Nozzle[NOZZLE_CNT];
            for(int i = 0; i < nozzles.length; i++)
                nozzles[i] = new Nozzle(i);
            
            //sets the nozzle positions based on current implement position
            updateNozzlePositions();
        }
        /**
         * Updates the implement position and the applied area due to tractor 
         * movement event.
         * 
         * @param tractorPos
         * @param heading 
         */
	public void handleNewTractorPosition(EnuPosition tractorPos, double heading) {
		updateAppliedArea(generateImplementPos(tractorPos, heading), heading);
	}

	/**
	 * Update the applied area by generating and adding a polygon.
	 * 
	 * @param newImplementPos
	 *            The new implement position.
	 * @param heading
	 *            The new implement heading
	 */
	public void updateAppliedArea(EnuPosition newImplementPos, double heading) {
		
                EnuPosition newLPos = getNewLeftPos(newImplementPos);
		EnuPosition newRPos = getNewRightPos(newImplementPos);
                
                
                //turn on nozzles according to new position of imnplement
                turnOnNozzles(newImplementPos);
             
		//update position and heading
                position = newImplementPos;
		this.heading = heading;
		rightEdgePosition = newRPos;
		leftEdgePosition = newLPos;
                
                //update the nozzle positions since implement position has changed
                updateNozzlePositions();
	}
        /**
         * Turns off nozzles that have a generated polygon overlapping the applied
         * area.  Turns on nozzle if generated nozzle polgyon doesn't overlap and
         * adds generated nozzle polygon to applied area.
         * @param applied area 
         **/
        private void turnOnNozzles(EnuPosition newImplementPos){
            for(Nozzle nozzle : nozzles){
                //generate nozzle polygon
                Polygon nozzlePoly = generateNozzlePolygon(nozzle, newImplementPos);
                
                //determine if nozzle polygon overlaps applied area        
                boolean overlap = appliedArea.checkOverlap(nozzlePoly);
                
                //if overlaps turn off and don't add to applied area
                if(overlap){
                    nozzle.turnNozzleOff();
                }
                else{
                    nozzle.turnNozzleOn();
                    appliedArea.addPolygon(nozzlePoly);  //update applied area
                }
            }
        }
	/**
	 * Get the implement width.
	 * 
	 * @return The width of the implement in metres.
	 */
	public double getWidth() {
		return width;
	}

	/**
	 * Get the distance to the tractor.
	 * 
	 * @return The distance to the tractor in metres.
	 */
	public double getDistanceToTractor() {
		return distanceToTractor;
	}

	/**
	 * Get the center point of the implement.
	 * 
	 * @return The implement position.
	 */
	public EnuPosition getPosition() {
		return position;
	}

	/**
	 * Get the area that this implement has applied.
	 * 
	 * @return The applied area.
	 */
	public AppliedArea getAppliedArea() {
		return appliedArea;
	}

	/**
	 * Returns true if the implement is currently applying material.
	 * 
	 * @return isSpraying - True if the implement is spraying, false otherwise.
	 */
	public boolean isSpraying() {
		return isSpraying;
	}

	/**
	 * Generates a new position for the implement based on the position of the
	 * tractor and updates nozzle positions accordingly.
	 * 
	 * @param tractorPos
	 *            The position of the tractor.
	 * @param heading
	 *            The heading of the tractor.
	 * @return A new position of the implement
	 */
	public abstract EnuPosition generateImplementPos(EnuPosition tractorPos,
			double heading);

        /**
         * Updates the implement's nozzles positions when the implement position 
         * has been changed.  The implement position is a class field so it doesn't
         * need to be passed in as a parameter.
         * 
         */
        protected abstract void updateNozzlePositions();
        
        /**
         * Calculates the new left position of the implement based on the 
         * new implement position.
         * 
         * */
	public abstract EnuPosition getNewLeftPos(EnuPosition newImplementPos);

	/**
         * Calculates the new right position of the implement based on the 
         * new implement position.
         * 
         **/
	public abstract EnuPosition getNewRightPos(EnuPosition newImplementPos);

	/**
	 * Generates a new polygon based on the 4 corners represented by the left
	 * and right edges of the implement at two different positions.
	 * 
	 * @param backLeft
	 *            The first position of the implement, left edge.
	 * @param backRight
	 *            The first position of the implement, right edge.
	 * @param frontLeft
	 *            The second position of the implement, left edge.
	 * @param frontRight
	 *            The second position of the implement, right edge.
	 * @return The Polygon area that has been covered between the two positions.
	 */
	public abstract Polygon generatePolygon(EnuPosition backLeft,
			EnuPosition backRight, EnuPosition frontLeft, EnuPosition frontRight);

        
        /**
         * This method generates a nozzle polygon given the new implement positon. 
         * The nozzle polygon can be calculated based on the new implement position, 
         * the nozzles current position, nozzle number, and nozzle width.
         * 
         **/
        
        public abstract Polygon generateNozzlePolygon(Nozzle nozzle, EnuPosition newImplementPos);
        
}
