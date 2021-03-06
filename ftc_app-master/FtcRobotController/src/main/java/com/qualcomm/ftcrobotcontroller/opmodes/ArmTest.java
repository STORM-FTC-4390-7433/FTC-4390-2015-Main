/* Copyright (c) 2014 Qualcomm Technologies Inc

All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted (subject to the limitations in the disclaimer below) provided that
the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list
of conditions and the following disclaimer.

Redistributions in binary form must reproduce the above copyright notice, this
list of conditions and the following disclaimer in the documentation and/or
other materials provided with the distribution.

Neither the name of Qualcomm Technologies Inc nor the names of its contributors
may be used to endorse or promote products derived from this software without
specific prior written permission.

NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE. */

package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

/**
 * TeleOp Mode
 * <p>
 * Enables control of the robot via the gamepad
 */
public class ArmTest extends OpMode {

	/*
	 * Note: the configuration of the servos is such that
	 * as the arm servo approaches 0, the arm position moves up (away from the floor).
	 * Also, as the claw servo approaches 0, the claw opens up (drops the game element).
	 */
    // TETRIX VALUES.
    //Arm values
//	final static double ARM_MIN_RANGE  = 0.20;
//	final static double ARM_MAX_RANGE  = 0.90;

//	final static double CLICK_PER_TURN = 10;

    final static double ARM_MIN_RANGE  = 0;  //Ground Level
    final static double ARM_MAX_RANGE  = 160;  //Represents how many encoder clicks.

    // position of the arm servo.
    double armPosition = 0;

    // amount to change the arm motor  position.
    double armDelta = 1.0;


    /*
    // position of the claw servo
    double clawPosition;

    // amount to change the claw servo position by
    double clawDelta = 0.1;

    DcMotor motorRight1;
    DcMotor motorRight2;
    DcMotor motorLeft1;
    DcMotor motorLeft2;

    */

    DcMotor motorArm;
    DcMotor motorWinch;

    //Servo climberSwitch;

    /**
     * Constructor
     */
    public ArmTest() {

    }

    /*
     * Code to run when the op mode is first enabled goes here
     *
     * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#start()
     */
    @Override
    public void init() {
		/*
		 * Use the hardwareMap to get the dc motors and servos by name. Note
		 * that the names of the devices must match the names used when you
		 * configured your robot and created the configuration file.
		 */

		/*
		 * For the demo Tetrix K9 bot we assume the following,
		 *   There are two motors "motor_1" and "motor_2"
		 *   "motor_1" is on the right side of the bot.
		 *   "motor_2" is on the left side of the bot and reversed.
		 *
		 * We also assume that there are two servos "servo_1" and "servo_6"
		 *    "servo_1" controls the arm joint of the manipulator.
		 *    "servo_6" controls the claw joint of the manipulator.
		 */
        // motorRight1 = hardwareMap.dcMotor.get("motor_1");
        //motorRight2 = hardwareMap.dcMotor.get("motor_2");
        //motorLeft1 = hardwareMap.dcMotor.get("motor_3");
        //motorLeft2 = hardwareMap.dcMotor.get("motor_4");
        motorArm = hardwareMap.dcMotor.get("motor_5");
        motorWinch = hardwareMap.dcMotor.get("motor_6");
        //motorLeft.setDirection(DcMotor.Direction.REVERSE);
    }

    /*
     * This method will be called repeatedly in a loop
     *
     * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#run()
     */
    @Override
    public void loop() {

        PushBotHardware encoder = new PushBotHardware();
        encoder.reset_arm_encoder();
        encoder.run_using_arm_encoder();
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
        //float throttleLeft = -gamepad1.left_stick_y;
        //float throttleRight = -gamepad1.right_stick_y;

        // clip the right/left values so that the values never exceed +/- 1
        //throttleRight = Range.clip(throttleRight, -1, 1);
        // throttleLeft = Range.clip(throttleLeft, -1, 1);

        // scale the joystick value to make it easier to control
        // the robot more precisely at slower speeds.
        //throttleRight = (float)scaleInput(throttleRight);
        // throttleLeft =  (float)scaleInput(throttleLeft);

        /* // write the values to the motors
        motorRight1.setPower(-throttleRight);
        motorRight2.setPower(-throttleRight);
        motorLeft1.setPower(throttleLeft);
        motorLeft2.setPower(throttleLeft); */
        double armPower = 0;
        double winchPower = 0;

        if (gamepad1.a) {
            // if the A button is pushed on gamepad1, increment the position of
            // the arm servo.
            armPosition += armDelta;
            armPower = 0.166;  //changed from .083
            winchPower = 2;  //from 1
        }

        if (gamepad1.y) {
            // if the Y button is pushed on gamepad1, decrease the position of
            // the arm servo.
            armPosition -= armDelta;
            armPower = -0.166;
            winchPower = -2;
        }

        motorArm.setPower(armPower);
        motorWinch.setPower(winchPower);

        // update the position of the claw
        //if (gamepad1.x) {
        //clawPosition += clawDelta;
        //}

        //if (gamepad1.b) {
        //clawPosition -= clawDelta;
        //}

        // clip the position values so that they never exceed their allowed range.
        //armPosition = Range.clip(armPosition, ARM_MIN_RANGE, ARM_MAX_RANGE);
        //clawPosition = Range.clip(clawPosition, CLAW_MIN_RANGE, CLAW_MAX_RANGE);

        // write position values to the wrist and claw servo
        //arm.setPosition(armPosition);
        //claw.setPosition(clawPosition);




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
    double scaleInput(double dVal)  {
        double[] scaleArray = { 0.0, 0.05, 0.09, 0.10, 0.12, 0.15, 0.18, 0.24,
                0.30, 0.36, 0.43, 0.50, 0.60, 0.72, 0.85, 1.00, 1.00 };

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

