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
        
        
        public boolean isPositionInsidePolygon(EnuPosition position);
        
        /**
         * Calculates the intersection of two overlapping polygons.
         * @param overlappedPoly
         * @param newPoly
         * @return - the polygon representing the intersection of the two polygons.
         */
        public Polygon getIntersection(Polygon overlappedPolygon);
        
        /**
         * Subtracts the subPolygon from the polygon
         * @param fullPolygon
         * @param subPolygon
         * @return - a polygon representing the full polygon with the subPolygon
         * taken out of it.
         */
        public Polygon subtractPolygon(Polygon subPolygon);
}
