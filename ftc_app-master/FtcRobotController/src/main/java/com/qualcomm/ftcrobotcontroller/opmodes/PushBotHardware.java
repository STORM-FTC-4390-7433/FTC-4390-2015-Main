package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.ftccommon.DbgLog;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

//------------------------------------------------------------------------------
//
// PushBotHardware
//
/**
 * Provides a single hardware access point between custom op-modes and the
 * OpMode class for the Push Bot.
 *
 * This class prevents the custom op-mode from throwing an exception at runtime.
 * If any hardware fails to map, a warning will be shown via telemetry data,
 * calls to methods will fail, but will not cause the application to crash.
 *
 * @author SSI Robotics
 * @version 2015-08-13-20-04
 */
public class PushBotHardware extends OpMode

{
    //--------------------------------------------------------------------------
    //
    // PushBotHardware
    //
    /**
     * Construct the class.
     *
     * The system calls this member when the class is instantiated.
     */
    public PushBotHardware ()

    {
        //
        // Initialize base classes.
        //
        // All via self-construction.

        //
        // Initialize class members.
        //
        // All via self-construction.

    } // PushBotHardware

    //--------------------------------------------------------------------------
    //
    // init
    //
    /**
     * Perform any actions that are necessary when the OpMode is enabled.
     *
     * The system calls this member once when the OpMode is enabled.
     */
    @Override public void init ()

    {

        try {
            v_motor_FrontLeft = hardwareMap.dcMotor.get("motorFL");
        } catch (Exception p_exeception) {
            m_warning_message("motorFL");
            DbgLog.msg(p_exeception.getLocalizedMessage());

            v_motor_FrontLeft = null;
        }


    }

    {

        try {
            v_motor_FrontRight = hardwareMap.dcMotor.get("motorFR");
        } catch (Exception p_exeception) {
            m_warning_message("motorFR");
            DbgLog.msg(p_exeception.getLocalizedMessage());

            v_motor_FrontRight = null;
        }


    }

    {

        try {
            v_motor_BackLeft = hardwareMap.dcMotor.get("motorBL");
        } catch (Exception p_exeception) {
            m_warning_message("motorBL");
            DbgLog.msg(p_exeception.getLocalizedMessage());

            v_motor_BackLeft = null;
        }


    }

    {

        try {
            v_motor_BackRight = hardwareMap.dcMotor.get("motorBR");
        } catch (Exception p_exeception) {
            m_warning_message("motorBR");
            DbgLog.msg(p_exeception.getLocalizedMessage());

            v_motor_BackRight = null;
        }


    }

    {

        try {
            v_motor_Arm = hardwareMap.dcMotor.get("motorArm");
        } catch (Exception p_exeception) {
            m_warning_message("motorArm");
            DbgLog.msg(p_exeception.getLocalizedMessage());

            v_motor_Arm = null;
        }


    }

    {

        try {
            v_motor_Winch = hardwareMap.dcMotor.get("motorWinch");
        } catch (Exception p_exeception) {
            m_warning_message("motorWinch");
            DbgLog.msg(p_exeception.getLocalizedMessage());

            v_motor_Winch = null;
        }


    }


    //Warnings (No idea what it does)

    boolean a_warning_generated ()

    {
        return v_warning_generated;

    }

    String a_warning_message ()

    {
        return v_warning_message;

    }

    void m_warning_message (String p_exception_message)

    {
        if (v_warning_generated)
        {
            v_warning_message += ", ";
        }
        v_warning_generated = true;
        v_warning_message += p_exception_message;

    }


    @Override public void start ()

    {
        //
        // Only actions that are common to all Op-Modes (i.e. both automatic and
        // manual) should be implemented here.
        //
        // This method is designed to be overridden.
        //

    }


    @Override public void loop ()

    {
        //
        // Only actions that are common to all OpModes (i.e. both auto and\
        // manual) should be implemented here.
        //
        // This method is designed to be overridden.
        //

    } // loop

    @Override public void stop ()
    {
        //
        // Nothing needs to be done for this method.
        //

    }


    //Power Scaler
    float scale_motor_power (float p_power)
    {

        float l_scale = 0.0f;


        float l_power = Range.clip (p_power, -1, 1);

        float[] l_array =
            { 0.00f, 0.05f, 0.09f, 0.10f, 0.12f
            , 0.15f, 0.18f, 0.24f, 0.30f, 0.36f
            , 0.43f, 0.50f, 0.60f, 0.72f, 0.85f
            , 1.00f, 1.00f
            };


        int l_index = (int)(l_power * 16.0);
        if (l_index < 0)
        {
            l_index = -l_index;
        }
        else if (l_index > 16)
        {
            l_index = 16;
        }

        if (l_power < 0)
        {
            l_scale = -l_array[l_index];
        }
        else
        {
            l_scale = l_array[l_index];
        }

        return l_scale;

    }



    public void run_using_front_right_drive_encoder ()

    {
        if (v_motor_FrontRight != null)
        {
            v_motor_FrontRight.setChannelMode
                ( DcMotorController.RunMode.RUN_USING_ENCODERS
                );
        }

    }

    public void run_using_front_left_drive_encoder ()

    {
        if (v_motor_FrontLeft != null)
        {
            v_motor_FrontLeft.setChannelMode
                ( DcMotorController.RunMode.RUN_USING_ENCODERS
                );
        }

    }

    public void run_using_back_right_drive_encoder ()

    {
        if (v_motor_BackRight != null)
        {
            v_motor_BackRight.setChannelMode
                    ( DcMotorController.RunMode.RUN_USING_ENCODERS
                    );
        }

    }

    public void run_using_back_left_drive_encoder ()

    {
        if (v_motor_BackLeft != null)
        {
            v_motor_BackLeft.setChannelMode
                    ( DcMotorController.RunMode.RUN_USING_ENCODERS
                    );
        }

    }


    /*
     * Set both drive wheel encoders to run, if the mode is appropriate.
     */
    public void run_using_drive_encoders ()

    {
        run_using_back_left_drive_encoder();
        run_using_back_right_drive_encoder();
        run_using_front_left_drive_encoder();
        run_using_front_right_drive_encoder();


    }



    public void run_using_arm_encoder ()

    {
        if (v_motor_Arm != null)
        {
            v_motor_Arm.setChannelMode
                    ( DcMotorController.RunMode.RUN_USING_ENCODERS
                    );
        }

    }

    public void run_using_winch_encoder ()

    {
        if (v_motor_Winch != null)
        {
            v_motor_Winch.setChannelMode
                    ( DcMotorController.RunMode.RUN_USING_ENCODERS
                    );
        }

    }


    public void run_using_arm_and_winch_encoders () {

        run_using_arm_encoder();
        run_using_winch_encoder();

    }

    public void reset_front_right_drive_encoder ()

    {
        if (v_motor_FrontRight != null)
        {
            v_motor_FrontRight.setChannelMode
                ( DcMotorController.RunMode.RESET_ENCODERS);
        }

    }

    public void reset_front_left_drive_encoder ()

    {
        if (v_motor_FrontLeft != null)
        {
            v_motor_FrontLeft.setChannelMode
                ( DcMotorController.RunMode.RESET_ENCODERS);
        }

    }

    public void reset_back_right_drive_encoder ()

    {
        if (v_motor_BackRight != null)
        {
            v_motor_BackRight.setChannelMode
                    ( DcMotorController.RunMode.RESET_ENCODERS);
        }

    }

    public void reset_back_left_drive_encoder ()

    {
        if (v_motor_BackLeft != null)
        {
            v_motor_BackLeft.setChannelMode
                    ( DcMotorController.RunMode.RESET_ENCODERS);
        }

    }

    public void reset_all_drive_encoders () {

        reset_front_left_drive_encoder();
        reset_front_right_drive_encoder();
        reset_back_left_drive_encoder();
        reset_back_right_drive_encoder();

    }

    public void reset_arm_encoder ()

    {
        if (v_motor_Arm != null)
        {
            v_motor_Arm.setChannelMode
                    ( DcMotorController.RunMode.RESET_ENCODERS
                    );
        }

    }

    public void reset_winch_encoder ()

    {
        if (v_motor_Winch != null)
        {
            v_motor_Winch.setChannelMode
                    ( DcMotorController.RunMode.RESET_ENCODERS
                    );
        }

    }

    public void reset_arm_and_winch_encoders () {

        reset_arm_encoder();
        reset_winch_encoder();

    }






    int FrontRightEncoderCount ()
    {
        int l_return = 0;

        if (v_motor_FrontRight != null)
        {
            l_return = v_motor_FrontRight.getCurrentPosition ();
        }

        return l_return;

    }

    int FrontLeftEncoderCount ()
    {
        int l_return = 0;

        if (v_motor_FrontLeft != null)
        {
            l_return = v_motor_FrontLeft.getCurrentPosition ();
        }

        return l_return;

    }

    int BackLeftEncoderCount ()
    {
        int l_return = 0;

        if (v_motor_BackLeft != null)
        {
            l_return = v_motor_BackLeft.getCurrentPosition ();
        }

        return l_return;

    }

    int BacktRightEncoderCount ()
    {
        int l_return = 0;

        if (v_motor_BackRight != null)
        {
            l_return = v_motor_BackRight.getCurrentPosition ();
        }

        return l_return;

    }

    int ArmEncoderCount ()
    {
        int l_return = 0;

        if (v_motor_Arm != null)
        {
            l_return = v_motor_Arm.getCurrentPosition ();
        }

        return l_return;

    }

    int WinchEncoderCount ()
    {
        int l_return = 0;

        if (v_motor_Winch != null)
        {
            l_return = v_motor_Winch.getCurrentPosition ();
        }

        return l_return;

    }



    private boolean v_warning_generated = false;


    private String v_warning_message;




    private DcMotor v_motor_FrontRight;


    private DcMotor v_motor_FrontLeft;


    private DcMotor v_motor_BackRight;


    private DcMotor v_motor_BackLeft;


    private DcMotor v_motor_Winch;


    private DcMotor v_motor_Arm;


} // PushBotHardware
