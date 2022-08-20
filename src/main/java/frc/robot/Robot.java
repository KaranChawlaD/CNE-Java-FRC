// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.cameraserver.CameraServer;


public class Robot extends TimedRobot {

  // Auto routes
  private static final String kDefaultAuto = "2 Ball Auto";
  private static final String kCustomAuto = "2 Ball Auto Side";
  private static final String kCustomAuto1 = "Do Nothing";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();

  // Joysticks
  private final Joystick m_driver = new Joystick(0);
  private final Joystick m_operator = new Joystick(1);

  // CAN motors
  private final CANSparkMax m_storage = new CANSparkMax(1, MotorType.kBrushless);
  private final CANSparkMax m_shooter = new CANSparkMax(2, MotorType.kBrushless);
  private final CANSparkMax m_intake = new CANSparkMax(3, MotorType.kBrushed);
  private final WPI_VictorSPX m_frontleft = new WPI_VictorSPX(4);
  private final WPI_VictorSPX m_frontright = new WPI_VictorSPX(5);
  private final WPI_VictorSPX m_backleft = new WPI_VictorSPX(6);
  private final WPI_VictorSPX m_backright = new WPI_VictorSPX(7);
  private final WPI_VictorSPX m_outerleftclimber = new WPI_VictorSPX(10);
  private final WPI_VictorSPX m_outerrightclimber = new WPI_VictorSPX(11);
  private final WPI_VictorSPX m_innerclimberlateral = new WPI_VictorSPX(12);
  private final WPI_VictorSPX m_outerclimberlateral = new WPI_VictorSPX(13);

  // Differential Drive
  private final MotorControllerGroup m_left = new MotorControllerGroup(m_frontleft, m_backleft);
  private final MotorControllerGroup m_right = new MotorControllerGroup(m_frontright, m_backright);
  private final DifferentialDrive m_robotDrive = new DifferentialDrive(m_left, m_right);

  // Timer
  private final Timer m_timer = new Timer();

  // Count
  private int count = 0;

  @Override
  public void robotInit() {

    // Auto Routes
    m_chooser.setDefaultOption("2 Ball Auto", kDefaultAuto);
    m_chooser.addOption("2 Ball Auto Side", kCustomAuto);
    m_chooser.addOption("Do Nothing", kCustomAuto);

    SmartDashboard.putData("Auto choices", m_chooser);

    // Reset Motors
    m_shooter.restoreFactoryDefaults();
    m_storage.restoreFactoryDefaults();
    m_intake.restoreFactoryDefaults();

    // Inverting Right motors
    m_right.setInverted(true);

    // Camera
    CameraServer.startAutomaticCapture("Front Camera", 0);
    CameraServer.startAutomaticCapture("Back Camera", 1);

  }

  @Override
  public void robotPeriodic() {}

  @Override
  public void autonomousInit() {

    // Auto routes
    m_autoSelected = m_chooser.getSelected();
    System.out.println("Auto selected: " + m_autoSelected);

    // Timer reset/start
    m_timer.reset();
    m_timer.start();

  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    switch (m_autoSelected) {

      case kCustomAuto:
        if (count == 0) {
          if (m_timer.get() < 1.0) {
            m_intake.set(0.90);
          }
          else {
            count ++ ;
          }
        }

        else if (count == 1) {
          if (m_timer.get() < 2.3) {
            m_robotDrive.arcadeDrive(0.55, 0.0);
          }
          else {
            count ++ ;
          }
        }

        else if (count == 2) {
          if (m_timer.get() < 3.7) {
            m_shooter.set(0.575);
          }
          else {
            count ++ ;
          }
        }

        else if (count == 3) {
          if (m_timer.get() < 4.1) {
            m_robotDrive.arcadeDrive(-0.55, 0.0);
          }
          else {
            m_intake.stopMotor();
            m_robotDrive.arcadeDrive(0.0, 0.0);
            count ++ ;
          }
        }

        else if (count == 4) {
          if (m_timer.get() < 7.1) {
            if (m_timer.get() > 5.1) {
              m_storage.set(-0.95);
            }
          }
          else {
            count ++ ;
            m_shooter.stopMotor();
            m_storage.stopMotor();
          }
        }
        break;


      case kCustomAuto1:
        if (count == 0) {
          if (m_timer.get() < 2.3) {
            m_robotDrive.arcadeDrive(0.55, 0.0);
          }
          else {
            count ++ ;
          }
        }
        break;


      case kDefaultAuto:
      default:
        
        if (count == 0) {
          if (m_timer.get() < 1.0) {
            m_intake.set(0.90);
          }
          else {
            count ++ ;
          }
        }

        else if (count == 1) {
          if (m_timer.get() < 3.3) {
            m_robotDrive.arcadeDrive(0.55, 0.0);
          }
          else {
            count ++ ;
          }
        }

        else if (count == 2) {
          if (m_timer.get() < 4.3) {
            m_shooter.set(0.575);
          }
          else {
            count ++ ;
          }
        }

        else if (count == 3) {
          if (m_timer.get() < 5.6) {
            m_robotDrive.arcadeDrive(-0.55, 0.0);
          }
          else {
            m_intake.stopMotor();
            m_robotDrive.arcadeDrive(0.0, 0.0);
            count ++ ;
          }
        }

        else if (count == 4) {
          if (m_timer.get() < 8.6) {
            if (m_timer.get() > 6.6) {
              m_storage.set(-0.95);
            }
          }
          else {
            count ++ ;
            m_shooter.stopMotor();
            m_storage.stopMotor();
          }
        }
        break;


    }
  }

  @Override
  public void teleopInit() {}

  @Override
  public void teleopPeriodic() {

    // Intake
    if (m_operator.getRawButton(3)) {
      m_intake.stopMotor();
    }
    
    if (m_operator.getRawButton(1)) {
      m_intake.set(0.90);
    }

    if (m_operator.getRawButton(9)) {
      m_intake.set(-0.90);
    }


    // Hanging
    m_outerleftclimber.set(m_operator.getRawAxis(1));
    m_outerrightclimber.set(m_operator.getRawAxis(1));

    if (m_operator.getRawButton(10)) {
      m_innerclimberlateral.set(-0.40);
      m_outerclimberlateral.set(-0.40);
    }

    if (m_operator.getRawButton(11)) {
      m_innerclimberlateral.set(0.40);
      m_outerclimberlateral.set(0.40);
    }

    if (m_operator.getRawButton(12)) {
      m_innerclimberlateral.stopMotor();
      m_outerclimberlateral.stopMotor();
    }
    

    // Movement
    m_robotDrive.arcadeDrive(m_driver.getRawAxis(4), m_driver.getRawAxis(1));


    // Storage
    if (m_operator.getRawButton(6)) {
      m_storage.set(-0.95); 
    } 
  
    if (m_operator.getRawButton(8)) { 
      m_storage.set(-0.65); 
    }
    
    if (m_operator.getRawButton(2)) {
      m_storage.set(0.0);
    }


    // Shooter
    if (m_operator.getRawButton(5)) {
//       m_shooter.set(0.585); 
      m_shooter.set(0.7); 
    } 
  
    if (m_operator.getRawButton(7)) { 
      m_shooter.set(0.50); 
    }
  
    if (m_operator.getRawButton(4)) {
      m_shooter.set(0.0);
    }
  }

  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  @Override
  public void testInit() {}

  @Override
  public void testPeriodic() {}
}
