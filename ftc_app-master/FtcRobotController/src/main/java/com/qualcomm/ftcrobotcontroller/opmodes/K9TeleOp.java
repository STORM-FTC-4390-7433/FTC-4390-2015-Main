package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;
public class K9TeleOp extends OpMode {

	final static double ARM_MIN_RANGE=0;
	final static double ARM_MAX_RANGE=100;
	final private double ARM_SPEED_MULTIPLIER = 1;
	final private double WINCH_SPEED_MULTIPLIER = 1;

	DcMotor motorRight;
	DcMotor motorLeft;
	double armDelta = 1.0;
	DcMotor motorArm;
	DcMotor motorWinch;
	public K9TeleOp() {

	}

	@Override
	public void init()
	{

		motorRight = hardwareMap.dcMotor.get("RIGHT");
		motorLeft = hardwareMap.dcMotor.get("LEFT");
		motorArm = hardwareMap.dcMotor.get("ARM");
		motorWinch = hardwareMap.dcMotor.get("WINCH");
		//motorLeft.setDirection(DcMotor.Direction.REVERSE);
	}
	@Override
	public void loop()
	{

		PushBotHardware encoder = new PushBotHardware();
		float throttleLeft = -gamepad1.left_stick_y;
		float throttleRight = -gamepad1.right_stick_y;

		throttleRight = Range.clip(throttleRight, -1, 1);
		throttleLeft = Range.clip(throttleLeft, -1, 1);

		throttleRight = (float)scaleInput(throttleRight);
		throttleLeft =  (float)scaleInput(throttleLeft);

		motorRight.setPower(-throttleRight);
		motorLeft.setPower(throttleLeft);

		float throttleArm = -gamepad2.right_stick_y;
		float throttleWinch = -gamepad2.left_stick_y;

		float armPower = 0;
		float winchPower = 0;

		throttleArm = Range.clip(throttleArm, -.5, .5);
		throttleWinch = Range.clip(throttleWinch, -.5, .5);

		throttleArm = (float)scaleInput(throttleArm);
		throttleWinch =  (float)scaleInput(throttleWinch);

		motorArm.setPower(throttleArm);
		motorWinch.setPower(throttleWinch);

		telemetry.addData("Text", "*** Robot Data***");
		telemetry.addData("left tgt pwr",  "left  pwr: " + String.format("%.2f", throttleLeft));
		telemetry.addData("right tgt pwr", "right pwr: " + String.format("%.2f", throttleRight));

	}

	@Override
	public void stop() {

	}

	double scaleInput(double dVal)  {
		double[] scaleArray = { 0.0, 0.05, 0.09, 0.10, 0.12, 0.15, 0.18, 0.24,
				0.30, 0.36, 0.43, 0.50, 0.60, 0.72, 0.85, 1.00, 1.00 };

		int index = (int) (dVal * 16.0);

		if (index < 0) {
			index = -index;
		}

		if (index > 16) {
			index = 16;
		}

		double dScale = 0.0;
		if (dVal < 0) {
			dScale = -scaleArray[index];
		} else {
			dScale = scaleArray[index];
		}

		return dScale;
	}
}
