package com.trimble.ag;

import java.util.Collection;

/**
 * Class for representing a farm vehicle.
 * 
 */
public abstract class Tractor {

	/** The current speed, in metres per second. */
	private double speed;
	/** The current heading in degrees. */
	private double heading;
	/** The current position of the vehicle. */
	private EnuPosition pos;

	/**
	 * A list of listeners to be notified when the tractor position changes.
	 */
	private Collection<TractorPositionListener> listeners;

	/**
	 * Tractor is initialized to position (0,0,0) with zero speed and headed
	 * north.
	 */
	public Tractor() {
		pos = new EnuPosition();
	}

	/**
	 * Get the speed of the vehicle in metres per second.
	 * 
	 * @return
	 */
	public double getSpeed() {
		return speed;
	}

	/**
	 * Heading of the tractor in degrees.
	 * 
	 * @return the tractor heading in degrees.
	 */
	public double getHeading() {
		return heading;
	}

	/**
	 * Get the current tractor position.
	 * 
	 * @return The tractor position.
	 */
	public EnuPosition getPosition() {
		return pos;
	}

	/**
	 * Add a new position listener to be notified when the tractor position
	 * changes.
	 * 
	 * @param listener
	 *            The listener to add.
	 */
	public void registerListener(TractorPositionListener listener) {
		listeners.add(listener);
	}

	/**
	 * Removes the given listener.
	 * 
	 * @param listener
	 *            The listener to remove.
	 */
	public void unregisterListener(TractorPositionListener listener) {
		listeners.remove(listener);
	}

	/**
	 * Called at a regular interval
	 */
	public abstract void periodicUpdate();

	public void notifyListeners(EnuPosition tractorPos, double heading) {
		for (TractorPositionListener listener : listeners) {
			listener.handleNewTractorPosition(tractorPos, heading);
		}
	}

}
