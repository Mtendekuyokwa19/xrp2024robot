// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import edu.wpi.first.wpilibj.xrp.XRPMotor;
import edu.wpi.first.wpilibj.XboxController; //teleop
import edu.wpi.first.wpilibj.xrp.XRPServo;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.Timer;
/**
 * The methods in this class are called automatically corresponding to each mode, as described in
 * the TimedRobot documentation. If you change the name of this class or the package after creating
 * this project, you must also update the Main.java file in the project.
 */
public class Robot extends TimedRobot {
  private static final String kDefaultAuto = "Default" ;
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();

private XRPMotor leftdrive=new XRPMotor(0);
private XRPMotor rightDrive=new XRPMotor(1);
private DifferentialDrive mDrive=new DifferentialDrive(rightDrive, leftdrive);
private Timer autotime=new Timer();
private XRPServo theservo=new XRPServo(5);
private double drivespeed=1;
private XboxController xbox=new XboxController(0);
  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  public Robot() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);
    rightDrive.setInverted(true);;
  }

  /**
   * This function is called every 20 ms, no matter the mode. Use this for items like diagnostics
   * that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {}

  /**
   * This autonomous (along with the chooser code above) shows how to select between different
   * autonomous modes using the dashboard. The sendable chooser code works with the Java
   * SmartDashboard. If you prefer the LabVIEW Dashboard, remove all of the chooser code and
   * uncomment the getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to the switch structure
   * below with additional strings. If using the SendableChooser make sure to add them to the
   * chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
    autotime.start();
    autotime.reset();


  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    switch (m_autoSelected) {
      case kCustomAuto:
        // Put custom auto code here
        break;
      case kDefaultAuto:
      default:
        // Put default auto code here
        if(autotime.get()<2.6){

          this.mDrive.tankDrive(.6,.6);
       theservo.setPosition(0.6); 
        }
        else if(autotime.get()<4){
          this.mDrive.tankDrive(-0.6, 0.6);
       theservo.setPosition(0.8); 
        }
        else if(autotime.get()<7){
          this.mDrive.tankDrive(-0.6, -0.6);
       theservo.setPosition(0.0); 
        }
                
        break;
    }
  }

  /** This function is called once when teleop is enabled. */
  @Override
  public void teleopInit() {}

  /** This function is called periodically during operator control. */
  @SuppressWarnings("removal")
  @Override
  public void teleopPeriodic() {
//mDrive.tankDrive(-xbox.getLeftY(),-xbox.getRightY());
if(xbox.getLeftBumper()){
  drivespeed=4;
}
else if(xbox.getRightBumper()){
  drivespeed=0.7;
}
mDrive.arcadeDrive(-xbox.getLeftY()*drivespeed, xbox.getRightX()*drivespeed);
if (xbox.getYButton()) {
this.theservo.setPosition(3);

  
}
else if(xbox.getAButton()){
  this.theservo.setPosition(0.01);
}

  }

  /** This function is called once when the robot is disabled. */
  @Override
  public void disabledInit() {}

  /** This function is called periodically when disabled. */
  @Override
  public void disabledPeriodic() {}

  /** This function is called once when test mode is enabled. */
  @Override
  public void testInit() {}

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}
}
