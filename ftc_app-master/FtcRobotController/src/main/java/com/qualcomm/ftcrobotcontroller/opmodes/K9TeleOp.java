package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;
public class K9TeleOp extends OpMode {



	DcMotor motorRight;
	DcMotor motorLeft;
	DcMotor motorArm;
	DcMotor motorWinch;
	public K9TeleOp() {

	}

	@Override
	public void init() {

		motorRight = hardwareMap.dcMotor.get("RIGHT");
		motorLeft = hardwareMap.dcMotor.get("LEFT");
		motorArm = hardwareMap.dcMotor.get("ARM");
		motorWinch = hardwareMap.dcMotor.get("WINCH");
		//motorLeft.setDirection(DcMotodevr.Direction.REVERSE);
	}

	/*
	 * This method will be called repeatedly in a loop
	 * 
	 * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#run()
	 */
	@Override
	public void loop() {

		//PushBotHardware encoder = new PushBotHardware();
		//encoder.reset_arm_encoder();
		//encoder.run_using_arm_encoder();
		/*
		 * Gamepad 1
		 *
		 * Gamepad 1 controls the motors via the left stick, and it controls the
		 * wrist/claw via the a,b, x, y buttons
		 */

		// throttle: left_stick_y ranges from -1 to 1, where -1 is full up, and
		// 1 is full down
		// direction: left_stick_x ranges from -1 to 1, where -1 is full left
		// and 1 is full right


		float throttleLeft = -gamepad1.left_stick_y;
		float throttleRight = -gamepad1.right_stick_y;

		throttleRight = Range.clip(throttleRight, -1, 1);
		throttleLeft = Range.clip(throttleLeft, -1, 1);

		throttleRight = (float)scaleInput(throttleRight);
		throttleLeft =  (float)scaleInput(throttleLeft);

		// write the values to the motors
		motorRight.setPower(-throttleRight);
		motorLeft.setPower(throttleLeft);


		float throttleArm =	-gamepad2.right_stick_y;
		float throttleWinch = -gamepad2.left_stick_y;

		throttleArm = Range.clip(throttleArm, -1, 1);
		throttleWinch = Range.clip(throttleWinch, -1, 1);

		throttleArm = (float)scaleInput(throttleArm);
		throttleWinch =  (float)scaleInput(throttleWinch);

		motorArm.setPower(-throttleArm * 0.25);
		motorWinch.setPower(throttleWinch * 0.25);


		/*
		 * Send telemetry data back to driver station. Note that if we are using
		 * a legacy NXT-compatible motor controller, then the getPower() method
		 * will return a null value. The legacy NXT-compatible motor controllers
		 * are currently write only.
		 */
//		telemetry.addData("Text", "*** Robot Data***");
//		telemetry.addData("left tgt pwr",  "left  pwr: " + String.format("%.2f", throttleLeft));
//		telemetry.addData("right tgt pwr", "right pwr: " + String.format("%.2f", throttleRight));
		//some useless comment
	}

	/*
	 * Code to run when the op mode is first disabled goes here
	 * 
	 * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#stop()
	 */
	@Override
	public void stop() {

	}


	/*
	 * This method scales the joystick input so for low joystick values, the 
	 * scaled value is less than linear.  This is to make it easier to drive
	 * the robot more precisely at slower speeds.
	 */
	double scaleInput(double dVal) {
		double[] scaleArray = {0.0, 0.05, 0.09, 0.10, 0.12, 0.15, 0.18, 0.24,
				0.30, 0.36, 0.43, 0.50, 0.60, 0.72, 0.85, 1.00, 1.00};

		// get the corresponding index for the scaleInput array.
		int index = (int) (dVal * 16.0);

		// index should be positive.
		if (index < 0) {
			index = -index;
		}

		// index cannot exceed size of array minus 1.
		if (index > 16) {
			index = 16;
		}

		// get value from the array.
		double dScale = 0.0;
		if (dVal < 0) {
			dScale = -scaleArray[index];
		} else {
			dScale = scaleArray[index];
		}

		// return scaled value.
		return dScale;
	}

}
