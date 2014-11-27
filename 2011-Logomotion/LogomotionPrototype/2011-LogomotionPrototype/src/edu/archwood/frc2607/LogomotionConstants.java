/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.archwood.frc2607;

/**
 *
 * @author Ron
 */
public interface LogomotionConstants {

    public static final int frontLeftCANID = 5, frontRightCANID = 11,
                            rearLeftCANID = 10, rearRightCANID = 12;

    public static final int elbowLeftCANID = 14, elbowRightCANID = 6;
    public static final int shoulderLeftCANID = 8, shoulderRightCANID = 20;

    public static final int frontLeftEncoderLines = 350,
                            frontRightEncoderLines = 360,
                            rearLeftEncoderLines = 250,
                            rearRightEncoderLines = 250;

    public static final double minRPM = 30.0, maxRPM = 800.0;
    public static final double rangeRPM = (maxRPM - minRPM);

    public static final int compressorRelay = 1, compressorSwitch = 5;
}
