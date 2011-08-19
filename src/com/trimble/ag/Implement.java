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
         * Creates the array of nozzles that the implement holds.  The initial 
         * nozzle position will be calculated based on the implement position.  
         * 
         */
        private void initializeNozzles(){
            nozzles = new Nozzle[NOZZLE_CNT];
            for(int i = 0; i < nozzles.length; i++)
                nozzles[i] = new Nozzle(getNozzlePosition(i));
        }
        /**
         * Updates the implement position and the applied area due to tractor 
         * movement event.
         * 
         * @param tractorPos
         * @param heading 
         */
	public void handleNewTractorPosition(EnuPosition tractorPos, double heading) {
		updateAppliedArea(updateImplementPosition(tractorPos, heading), heading);
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
		
                EnuPosition newLPos = getLeftPos();
		EnuPosition newRPos = getRightPos();
		Polygon newPoly = generatePolygon(leftEdgePosition, rightEdgePosition, newLPos, newRPos);

                
                Polygon overlappedPolygon = appliedArea.getOverlappedPolygon(newPoly);
                
                //There is an overlap.  Turn on nozzles accordingly
                if(overlappedPolygon != null){
                    turnOnNozzles(overlappedPolygon);
                    //only add the polygon that's being sprayed
                    appliedArea.addPolygon(newPoly.subtractPolygon(newPoly.getIntersection(overlappedPolygon)));
                }
                //There is no overlap so turn on all nozzles
                else{
                    turnOnAllNozzles();
                    appliedArea.addPolygon(newPoly);
                }
                
             
		//update position and heading
                position = newImplementPos;
		this.heading = heading;
		rightEdgePosition = newRPos;
		leftEdgePosition = newLPos;
	}
        /**
         * Turns off nozzles that are inside the overlapping polygon
         * @param overlappedPolygon 
         */
        private void turnOnNozzles(Polygon overlappedPolygon){
            for(Nozzle nozzle : nozzles){
                if(overlappedPolygon.isPositionInsidePolygon(nozzle.getPosition()))
                    nozzle.turnNozzleOff();
                else
                    nozzle.turnNozzleOn();
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
         * has been changed using the generateImplementPos method.  This method 
         * should be called inside the generateImplementPos method after the 
         * implement position has been updated.
         * 
         */
        protected abstract void updateNozzlePositions();
        
        
        /**
         * Wrapper method that updates the implement position as well as the 
         * nozzle positions.
         * @param tractorPos
         * @param heading 
         */
        public EnuPosition updateImplementPosition(EnuPosition tractorPos, double heading){
            EnuPosition implementPosition = generateImplementPos(tractorPos, heading);
            updateNozzlePositions();
            return implementPosition;
        }
	/**
	 * Get the left hand edge position of the implement.
	 * 
	 * @return The left hand edge position.
	 */
	public EnuPosition getLeftPos() {
		return leftEdgePosition;
	}

	/**
	 * Get the right hand edge position of the implement.
	 * 
	 * @return The right hand edge position.
	 */
	public EnuPosition getRightPos() {
		return rightEdgePosition;
	}

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
	 * Turns on all nozzles.  
	 * 
	 */
	protected void turnOnAllNozzles() {
            for(Nozzle nozzle : nozzles){
                nozzle.turnNozzleOn();
            }
	}

        /**
         * Calculates nozzle position based on nozzle number.  The width of the 
         * implement and the nozzle number will determine the exact position of
         * the nozzle.
         * 
         *@param nozzleNumber
         * @return EnuPosition - the position of the nozzle
         */ 
        public EnuPosition getNozzlePosition(int nozzleNumber){
            return nozzles[nozzleNumber].getPosition();
        }
}
