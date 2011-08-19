package com.trimble.ag;

import java.util.ArrayList;

/**
 * Represents a collection of polygons that make up an applied area.
 */
public class AppliedArea {

	/**
	 * The list of individual polygons.
	 */
	private ArrayList<Polygon> polygons;

	/**
	 * Constructor initializes an empty area.
	 */
	public AppliedArea() {
		polygons = new ArrayList<Polygon>();
	}

	/**
	 * Add a polygon to the area. The polygon shouldn't be modified after it has
	 * been added.
	 * 
	 * @param poly
	 *            The polygon to add.
	 */
	public void addPolygon(Polygon poly) {
		polygons.add(poly);
	}

	/**
	 * Checks if the given polygon at least partially overlaps the area.
	 * 
	 * @param poly
	 *            The polygon to check.
	 * @return true if the polygon overlaps this area, false otherwise.
	 */
	public boolean checkOverlap(Polygon poly) {
		for (Polygon polyToCheck : polygons) {
			if (polyToCheck.checkOverlap(poly)) {
				return true;
			}
		}
		return false;
	}

}
