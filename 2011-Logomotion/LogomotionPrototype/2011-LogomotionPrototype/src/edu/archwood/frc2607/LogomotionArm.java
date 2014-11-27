/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.archwood.frc2607;

import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;

/**
 *
 * @author Ron
 */
public class LogomotionArm implements LogomotionConstants {

    private AnalogChannel elbowPot = null, shoulderPot = null;
    private CANJaguar elbowLeftMotor = null, elbowRightMotor = null,
                     shoulderLeftMotor = null, shoulderRightMotor = null;
    private Solenoid theClaw = null;
    
    public LogomotionArm() {

        initMotors();
        elbowPot = new AnalogChannel(4);
        shoulderPot = new AnalogChannel(6);

    }

    public void setElbowSpeed(double speed) {
        try {
            byte syncGroup = 0x05;
            elbowLeftMotor.setX(speed, syncGroup);
            elbowRightMotor.setX(speed, syncGroup);
            CANJaguar.updateSyncGroup(syncGroup);
            Thread.sleep(5);
        } catch (Exception e) {
            System.out.println("setElbowSpeed() exception...");
        }
    }

    public void setShoulderSpeed(double speed) {
        try {
            byte syncGroup = 0x06;
            shoulderLeftMotor.setX(speed, syncGroup);
            shoulderRightMotor.setX(speed, syncGroup);
            CANJaguar.updateSyncGroup(syncGroup);
            Thread.sleep(5);
        } catch (Exception e) {
            System.out.println("setShoulderSpeed() exception...");
        }
    }

    public String getPotValues() {
        return "elbowPot: " + elbowPot.getAverageValue() +
               " shoulderPot: " + shoulderPot.getAverageValue();
    }

    private void initMotors() {
        while (elbowLeftMotor == null) {
            try {
                elbowLeftMotor = new CANJaguar(elbowLeftCANID);
                System.out.println("elbowLeft Jag created successfully");
            } catch (Exception e) {
                System.out.println("elbowLeft Jag exception, retrying...");
                Timer.delay(.25);
            }
        }
        while (shoulderLeftMotor == null) {
            try {
                shoulderLeftMotor = new CANJaguar(shoulderLeftCANID);
                System.out.println("shoulderLeft Jag created successfully");
            } catch (Exception e) {
                System.out.println("shoulderLeft Jag exception, retrying...");
                Timer.delay(.25);
            }
        }
        while (elbowRightMotor == null) {
            try {
                elbowRightMotor = new CANJaguar(elbowRightCANID);
                System.out.println("elbowRight Jag created successfully");
            } catch (Exception e) {
                System.out.println("elbowRight Jag exception, retrying...");
                Timer.delay(.25);
            }
        }
        while (shoulderRightMotor == null) {
            try {
                shoulderRightMotor = new CANJaguar(shoulderRightCANID);
                System.out.println("shoulderRight Jag created successfully");
            } catch (Exception e) {
                System.out.println("shoulderRight Jag exception, retrying...");
                Timer.delay(.25);
            }
        }
    }

}
