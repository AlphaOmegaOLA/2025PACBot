// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.util.sendable.SendableRegistry; 
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;
import edu.wpi.first.cameraserver.CameraServer;
// USB Cameras
import edu.wpi.first.cscore.UsbCamera;

/** This is a demo program showing how to use Mecanum control with the MecanumDrive class. */
public class Robot extends TimedRobot {
  // Top Camera
  UsbCamera topcam;
  private static final int kFrontLeftChannel = 4;
  private static final int kRearLeftChannel = 3;
  private static final int kFrontRightChannel = 1;
  private static final int kRearRightChannel = 2;

  private static final int kJoystickChannel = 0;

  private MecanumDrive m_robotDrive;
  private XboxController m_stick;

  @Override
  public void robotInit() {
    CANSparkMax frontLeft = new CANSparkMax(kFrontLeftChannel, MotorType.kBrushless);
    CANSparkMax rearLeft = new CANSparkMax(kRearLeftChannel, MotorType.kBrushless);
    CANSparkMax frontRight = new CANSparkMax(kFrontRightChannel, MotorType.kBrushless);
    CANSparkMax rearRight = new CANSparkMax(kRearRightChannel, MotorType.kBrushless);

    SendableRegistry.addChild(m_robotDrive, frontLeft);
    SendableRegistry.addChild(m_robotDrive, rearLeft);
    SendableRegistry.addChild(m_robotDrive, frontRight);
    SendableRegistry.addChild(m_robotDrive, rearRight);

    // Invert the right side motors.
    // You may need to change or remove this to match your robot.
    frontRight.setInverted(true);
    rearRight.setInverted(true);
    frontLeft.setInverted(false);

    topcam = CameraServer.startAutomaticCapture(0);


    m_robotDrive = new MecanumDrive(frontLeft::set, rearLeft::set, frontRight::set, rearRight::set);

    m_stick = new XboxController(kJoystickChannel);
  }

  @Override
  public void teleopPeriodic() {
    // Use the joystick Y axis for forward movement, X axis for lateral
    // movement, and Z axis for rotation.
    m_robotDrive.driveCartesian(-m_stick.getLeftY(), m_stick.getLeftX(), m_stick.getRightX());
    // Slow Version for Testing
    //m_robotDrive.driveCartesian(-m_stick.getLeftY()*.2, m_stick.getLeftX()*.2, m_stick.getRightX()*.2);
    //m_robotDrive.driveCartesian(-m_stick.getLeftY()*.5, m_stick.getLeftX()*.5, m_stick.getRightX()*.5);
    //m_robotDrive.driveCartesian(-m_stick.getLeftY()*.7, m_stick.getLeftX()*.7, m_stick.getRightX()*.7);
    //m_robotDrive.driveCartesian(-m_stick.getLeftY()*.8, m_stick.getLeftX()*.8, m_stick.getRightX()*.8);
  }
}