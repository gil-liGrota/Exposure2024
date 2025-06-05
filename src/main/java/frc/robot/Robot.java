package frc.robot;

import edu.wpi.first.hal.FRCNetComm.tInstances;
import edu.wpi.first.hal.FRCNetComm.tResourceType;
import edu.wpi.first.hal.HAL;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.Constants.GameConstants;
import frc.robot.POM_lib.sensors.POMDigitalInput;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in 
 * the project.
 */
public class Robot extends TimedRobot {

    //POMDigitalInput sensor = new POMDigitalInput(0);
    
    

    private Command m_autonomousCommand;

    private RobotContainer m_robotContainer;

    GenericEntry autoTime;
    GenericEntry teleopTime;
    GenericEntry endGameTime;
    GenericEntry voltageEntry;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    @Override
    public void robotInit() {
        // Instantiate our RobotContainer.  This will perform all our button bindings, and put our
        // autonomous chooser on the dashboard.
        m_robotContainer = RobotContainer.getInstance();
        HAL.report(tResourceType.kResourceType_Framework, tInstances.kFramework_RobotBuilder);
        enableLiveWindowInTest(true);
        autoTime = Shuffleboard.getTab("Auto").add("Time", GameConstants.AUTO_TIME).getEntry();
       teleopTime = Shuffleboard.getTab("Teleop").add("Time", GameConstants.TELEOP_TIME).getEntry();
        endGameTime = Shuffleboard.getTab("EndGame").add("Time", GameConstants.ENDGAME_TIME).getEntry();
        voltageEntry = Shuffleboard.getTab("General").add("Voltage", RobotController.getBatteryVoltage()).getEntry();
    }
    /**
    * This function is called every robot packet, no matter the mode. Use this for items like
    * diagnostics that you want ran during disabled, autonomous, teleoperated and test.
    *
    * <p>This runs after the mode specific periodic functions, but before
    * LiveWindow and SmartDashboard integrated updating.
    */
    @Override
    public void robotPeriodic() {
        // LogFieldsTable.updateAllTables();//TODO tunebale
        // TuneablesManager.update();//TODO tunebale
        CommandScheduler.getInstance().run();
        voltageEntry.setDouble(RobotController.getBatteryVoltage());
        //SmartDashboard.putBoolean("sensor", sensor.get());
    }

    @Override
    public void disabledInit() {
    }

    @Override
    public void disabledPeriodic() {
    }

    @Override
    public void autonomousInit() {
        // autonomousCommand = robotContainer.getAutonomousCommand();

        // if (autonomousCommand != null) {
        //     autonomousCommand.schedule();
        // }
    }

    @Override
    public void autonomousPeriodic() {
    }

    @Override
    public void teleopInit() {
        // if (autonomousCommand != null) {
        //     autonomousCommand.cancel();
        // }
    }

    @Override
    public void teleopPeriodic() {
    }

    @Override
    public void testInit() {
        CommandScheduler.getInstance().disable();
        // TuneablesManager.enable(); //TODO tunebale
    }

    @Override
    public void testPeriodic() {
    }

    @Override
    public void testExit() {
        CommandScheduler.getInstance().enable();
    }

    @Override
    public void simulationInit() {
    }

    @Override
    public void simulationPeriodic() {
    }
}