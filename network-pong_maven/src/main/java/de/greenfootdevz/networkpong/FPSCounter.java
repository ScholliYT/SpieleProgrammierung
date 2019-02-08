package de.greenfootdevz.networkpong;

import greenfoot.*;

/**
 * An actor class that show how many frames the scenario is running at the current speed.
 */
public class FPSCounter extends Actor {
    private int frames; // frame counter and speed setting
    private boolean set, go; // controls

    /**
     * Sets the initial scenario speed and creates the initial image
     */
    public FPSCounter() {
        updateImage(); // set initial image of fps text
    }

    /**
     * Track frames per second of scenario
     */
    public void act() {
        // get current fractional part of seconds of system time (0 to 999 milliseconds)
        int millis = (int) (System.currentTimeMillis() % 1000);
        // code to begin the timing
        if (!set && !go) { // time has not begun and we are not set to begin
            if (millis > 100) set = true; // we are set to begin if past first 1/10 of a second
            return;
        }
        if (set && !go) { // time has not begun, but we are set to begin
            if (millis < 100) {
                go = true;
                set = false;
            } // zero tick, unset and begin time
            return;
        }
        // code to run the timing
        frames++; // count this frame
        if (!set && go) { // must wait for 1/10 of a second before looking for first 1/10 of a second again
            if (millis > 100) set = true; // reset after 1/10 of a second past last tick
            return;
        }
        if (set && go) { // looking for next first 1/10 of a second
            if (millis < 100) { // next tick
                set = false; // not looking for tick
                updateImage(); // update text display of fps actor
                frames = 0; // reset the frames counter
            }
        }
    }

    /**
     * Updates the image
     */
    private void updateImage() {
        setImage(new GreenfootImage("FPS: " + frames, 30, Color.WHITE, Color.LIGHT_GRAY));
    }
}