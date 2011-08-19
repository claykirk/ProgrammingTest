package com.trimble.ag;

/**
 * Represents a single contour area of coverage.
 * 
 */
public interface Polygon {

	/**
	 * Check if the passed polygon at least partially overlaps this polygon.
	 * 
	 * @param other
	 *            The polygon to check against.
	 * @return True if the polygon overlaps, false otherwise.
	 */
	public boolean checkOverlap(Polygon other);
        
}
