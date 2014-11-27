/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.archwood.frc2607;

import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.Timer;

/**
 *
 * @author Ron
 */
public class LogomotionDriveTrain implements LogomotionConstants {

    private CANJaguar   frontLeftMotor = null, frontRightMotor = null,
                        rearLeftMotor = null, rearRightMotor = null;

    private double      frontLeftMotorSpeed, frontRightMotorSpeed,
                        rearLeftMotorSpeed, rearRightMotorSpeed;

    private double      signFrontLeft, signFrontRight,
                        signRearLeft, signRearRight;

    private void initMotors() {
        while (frontLeftMotor == null) {
            try {
                frontLeftMotor = new CANJaguar(frontLeftCANID);
                System.out.println("frontLeft Jag created successfully");
            } catch (Exception e) {
                System.out.println("frontLeft Jag exception, retrying...");
                Timer.delay(.25);
            }
        }
        while (rearLeftMotor == null) {
            try {
                rearLeftMotor = new CANJaguar(rearLeftCANID);
                System.out.println("rearLeft Jag created successfully");
            } catch (Exception e) {
                System.out.println("rearLeft Jag exception, retrying...");
                Timer.delay(.25);
            }
        }
        while (frontRightMotor == null) {
            try {
                frontRightMotor = new CANJaguar(frontRightCANID);
                System.out.println("frontRight Jag created successfully");
            } catch (Exception e) {
                System.out.println("frontRight Jag exception, retrying...");
                Timer.delay(.25);
            }
        }
        while (rearRightMotor == null) {
            try {
                rearRightMotor = new CANJaguar(rearRightCANID);
                System.out.println("rearRight Jag created successfully");
            } catch (Exception e) {
                System.out.println("rearRight Jag exception, retrying...");
                Timer.delay(.25);
            }
        }
    }

    private void configMotors() {
        boolean keepTrying = true;

        while (keepTrying) {
            try {
               frontLeftMotor.changeControlMode(CANJaguar.ControlMode.kSpeed);
               frontLeftMotor.setSpeedReference(CANJaguar.SpeedReference.kEncoder);
               frontRightMotor.changeControlMode(CANJaguar.ControlMode.kSpeed);
               frontRightMotor.setSpeedReference(CANJaguar.SpeedReference.kEncoder);
               rearLeftMotor.changeControlMode(CANJaguar.ControlMode.kSpeed);
               rearLeftMotor.setSpeedReference(CANJaguar.SpeedReference.kEncoder);
               rearRightMotor.changeControlMode(CANJaguar.ControlMode.kSpeed);
               rearRightMotor.setSpeedReference(CANJaguar.SpeedReference.kEncoder);

               frontLeftMotor.configEncoderCodesPerRev(frontLeftEncoderLines);
               frontRightMotor.configEncoderCodesPerRev(frontRightEncoderLines);
               rearLeftMotor.configEncoderCodesPerRev(rearLeftEncoderLines);
               rearRightMotor.configEncoderCodesPerRev(rearRightEncoderLines);

               frontLeftMotor.setPID(.350, .004, .001);
               frontRightMotor.setPID(.350, .004, .001);
               rearLeftMotor.setPID(.350, .004, .001);
               rearRightMotor.setPID(.350, .004, .001);

               frontLeftMotor.enableControl();
               frontRightMotor.enableControl();
               rearLeftMotor.enableControl();
               rearRightMotor.enableControl();
               keepTrying = false;
            } catch (Exception e){
                System.out.println("LogomotionDriveTrain.configMotors() exception, retrying...");
                Timer.delay(.1);
            }
        }
    }

    public void mecanumDrive(double forward, double turn, double rotate) {

        if (forward > -0.02 && forward < 0.02) forward = 0;
        if (turn > -0.02 && turn < 0.02) turn = 0;
        if (rotate > -0.02 && rotate < 0.02) rotate = 0;

        frontLeftMotorSpeed = forward + rotate + turn;
        if (frontLeftMotorSpeed < 0.0) signFrontLeft = -1.0;
        else signFrontLeft = 1.0;
        frontLeftMotorSpeed = Math.abs(frontLeftMotorSpeed);

        frontRightMotorSpeed = forward - rotate - turn;
        if (frontRightMotorSpeed < 0.0) signFrontRight = -1.0;
        else signFrontRight = 1.0;
        frontRightMotorSpeed = Math.abs(frontRightMotorSpeed);

	rearLeftMotorSpeed = forward + rotate - turn;
        if (rearLeftMotorSpeed < 0.0) signRearLeft = -1.0;
        else signRearLeft = 1.0;
        rearLeftMotorSpeed = Math.abs(rearLeftMotorSpeed);

	rearRightMotorSpeed = forward - rotate + turn;
        if (rearRightMotorSpeed < 0.0) signRearRight = -1.0;
        else signRearRight = 1.0;
        rearRightMotorSpeed = Math.abs(rearRightMotorSpeed);

        double max = frontLeftMotorSpeed;
	if (frontRightMotorSpeed > max) max = frontRightMotorSpeed;
	if (rearRightMotorSpeed > max) max = rearRightMotorSpeed;
	if (rearLeftMotorSpeed > max) max = rearLeftMotorSpeed;

	if (max > 1.0) {
		frontLeftMotorSpeed /= max;
		frontRightMotorSpeed /= max;
		rearLeftMotorSpeed /= max;
		rearRightMotorSpeed /= max;
	}

        if (frontLeftMotorSpeed > .01)
            frontLeftMotorSpeed = signFrontLeft * (minRPM + (frontLeftMotorSpeed * rangeRPM));
        else frontLeftMotorSpeed = 0;
        if (frontRightMotorSpeed > .01)
            frontRightMotorSpeed = signFrontRight * (minRPM + (frontRightMotorSpeed * rangeRPM));
        else frontRightMotorSpeed = 0;
        if (rearLeftMotorSpeed > .01)
            rearLeftMotorSpeed = signRearLeft * (minRPM + (rearLeftMotorSpeed * rangeRPM));
        else rearLeftMotorSpeed = 0;
        if (rearRightMotorSpeed > .01)
            rearRightMotorSpeed = signRearRight * (minRPM + (rearRightMotorSpeed * rangeRPM));
        else rearRightMotorSpeed = 0;

        // TODO:  determine which motors need to be reversed (left? right?)
        //          think it should be left....
        try {
            byte syncGroup = 0x20;
            frontLeftMotor.setX(-frontLeftMotorSpeed, syncGroup);
            frontRightMotor.setX(frontRightMotorSpeed, syncGroup);
            rearLeftMotor.setX(-rearLeftMotorSpeed, syncGroup);
            rearRightMotor.setX(rearRightMotorSpeed, syncGroup);
            Thread.sleep(5);
            CANJaguar.updateSyncGroup(syncGroup);
        } catch (Exception e) {
            System.out.println("mecanumDrive exception setting motor speeds...");
        }

    }

    public LogomotionDriveTrain() {

        initMotors();
        configMotors();
    }

}
