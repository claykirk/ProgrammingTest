package com.trimble.ag;

public interface TractorPositionListener {

	/**
	 * Handle a new tractor position.
	 * 
	 * @param tractorPos
	 *            The new tractor position.
	 * @param heading
	 *            The tractor heading, in degrees.
	 */
	void handleNewTractorPosition(EnuPosition tractorPos, double heading);

}
