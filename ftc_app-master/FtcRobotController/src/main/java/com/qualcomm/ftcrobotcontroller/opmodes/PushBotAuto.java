package com.qualcomm.ftcrobotcontroller.opmodes;
import com.qualcomm.robotcore.hardware.DcMotor;
public class PushBotAuto extends PushBotTelemetry
{
    DcMotor motorRight;
    DcMotor motorLeft;
    public long start, currentTime;

    final static int milliPerTile = 750;
    public PushBotAuto ()
    {
        start();
    }
    @Override public void start ()
    {
        super.start ();
    }
    @Override public void loop ()
    {
        motorRight = hardwareMap.dcMotor.get("RIGHT");
        motorLeft = hardwareMap.dcMotor.get("LEFT");

        switch (v_state)
        {
            case 0:
                v_state++;
                start = System.currentTimeMillis();
                break;
            case 1:
                motorRight.setPower(-1.0);
                motorLeft.setPower(1.0);
                if (System.currentTimeMillis() - start >= 2 * milliPerTile)
                {
                    v_state ++;
                }
                currentTime = System.currentTimeMillis() - start;
                telemetry.addData("19", "The Current Time is: " + currentTime);
                break;


            case 2:
                motorRight.setPower(1.0);
                motorLeft.setPower(1.0);
                if(System.currentTimeMillis() - start >= 3 * milliPerTile)
                {
                    v_state++;
                }
                break;

            case 3:
                motorRight.setPower(-1.0);
                motorLeft.setPower(1.0);
                if (System.currentTimeMillis() - start >= 5 * milliPerTile)
                {
                    motorRight.setPower(0);
                    motorLeft.setPower(0);
                    v_state ++;
                }
                break;

            default:
                break;
        }

        update_telemetry (); // Update common telemetry
        telemetry.addData ("18", "State: " + v_state);
    }
    private int v_state = 0;
}