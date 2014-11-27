
package edu.archwood.frc2607;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DriverStationLCD;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;

public class LogomotionPrototype extends IterativeRobot 
                                 implements LogomotionConstants {

    LogomotionDriveTrain driveTrain = null;
    LogomotionArm theArm = null;
    Joystick driveStick = null;
    int loopCount = 0;
    Compressor theCompressor = null;

    public void robotInit() {

        System.out.println("Entering robotInit()");
        driveTrain = new LogomotionDriveTrain();
        theArm = new LogomotionArm();
        theCompressor = new Compressor(compressorSwitch, compressorRelay);
        driveStick = new Joystick(1);
        System.out.println("Done robotInit()");
    }

    public void autonomousPeriodic() {

    }

    public void disabledInit() {
        theCompressor.stop();
    }

    public void teleopInit() {
        loopCount = 0;
        driveTrain.mecanumDrive(0.0,0.0,0.0);
        theCompressor.start();
    }

    public void teleopPeriodic() {
        double forward=0.0, turn=0.0, rotate=0.0;
        if (loopCount % 25 == 0) {
        }
/*
             if (driveStick.getRawButton(1))
                turn = .5;
             else if (driveStick.getRawButton(3))
                 turn = -.5;

             if (driveStick.getRawButton(2))
                 forward = .5;
             else if (driveStick.getRawButton(4))
                 forward = -.5;
*/
        if (driveStick.getRawButton(4)) theArm.setShoulderSpeed(-.5);
        else if (driveStick.getRawButton(2)) theArm.setShoulderSpeed(.5);
	else theArm.setShoulderSpeed(0.0);

        if (driveStick.getRawButton(1)) theArm.setElbowSpeed(-.4);
	else if (driveStick.getRawButton(3)) theArm.setElbowSpeed(.4);
	else theArm.setElbowSpeed(0.0);

        driveTrain.mecanumDrive(driveStick.getY(), driveStick.getX(),
                                driveStick.getRawAxis(4));

//            driveTrain.mecanumDrive(driveStick.getY(), driveStick.getX(),
//                                        driveStick.getZ());

         DriverStationLCD.getInstance().println(DriverStationLCD.Line.kMain6, 1,
                                                theArm.getPotValues());
         DriverStationLCD.getInstance().updateLCD();

         if (++loopCount >=50) {
            System.out.print("in teleopPeriodic(), ");
            System.out.print("Y: " + driveStick.getY());
            System.out.print(" X: " + driveStick.getX());
            System.out.print(" #4: " + driveStick.getRawAxis(4));
            System.out.println(" #5: " + driveStick.getRawAxis(5));
            loopCount = 0;
        }
    }
    
}
