package com.trimble.ag;

/**
 * An east north up Cartesian position in a local tangent plane to the planet.
 */
public class EnuPosition {

	private double east;
	private double north;
	private double up;

	/**
	 * Position is initialized to zero in all dimensions.
	 */
	public EnuPosition() {
		east = 0.0;
		north = 0.0;
		up = 0.0;
	}

	/**
	 * Initialize a new position to given coordinates.
	 * 
	 * @param east
	 *            The east coordinate
	 * @param north
	 *            The north coordinate
	 * @param up
	 *            The up coordinate
	 */
	public EnuPosition(double east, double north, double up) {
		this.east = east;
		this.north = north;
		this.up = up;
	}

	/**
	 * Get the east coordinate.
	 * 
	 * @return The east position in metres.
	 */
	public double getEast() {
		return east;
	}

	/**
	 * Get the north coordinate.
	 * 
	 * @return The north position in metres.
	 */
	public double getNorth() {
		return north;
	}

	/**
	 * Get the up coordinate.
	 * 
	 * @return The up position in metres.
	 */
	public double getUp() {
		return up;
	}

	/**
	 * Get the coordinate by index.
	 * 
	 * 0 = east, 1 = north, 2 = up
	 * 
	 * @return The requested coordinate in metres.
	 */
	public double get(int i) {
		switch (i) {
		case 0:
			return east;
		case 1:
			return north;
		case 2:
			return up;
		default:
			return Double.NaN;
		}
	}

}
