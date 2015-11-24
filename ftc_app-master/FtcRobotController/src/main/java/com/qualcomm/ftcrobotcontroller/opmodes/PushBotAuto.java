package com.qualcomm.ftcrobotcontroller.opmodes;
import com.qualcomm.robotcore.hardware.DcMotor;
public class PushBotAuto extends PushBotTelemetry
{
    DcMotor motorRight;
    DcMotor motorLeft;
    public long start, elapsed;
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
                break;
            case 1:
                start = System.currentTimeMillis();
                motorRight.setPower(1.0);
                motorLeft.setPower(1.0);
                if (System.currentTimeMillis() - start >= 1000)
                {
                    motorRight.setPower(-1.0);
                    motorLeft.setPower(1.0);

                }

                if (System.currentTimeMillis() - start >= 2000)
                {
                    motorRight.setPower(1.0);
                    motorLeft.setPower(1.0);
                }

                if (System.currentTimeMillis() - start >= 3000)
                {
                    break;
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