package model;
public interface IrobserverClient
{
	// Verbinde zu Kombination aus Host & Port
	// inklusive aller notwendiger Schritte
	public boolean connect(
		String host,
		int port
		);
	// RobServer-Doku: GetRobot
	public String getRobot();
	// RobServer-Doku: GetVersion
	public String getVersion();
	// Verbindung sauber beenden
	public boolean quit();
	// RobServer-Doku: GetTimestamp
	public String getTimestamp();
	// RobServer-Doku: PingRobot
	public double[] pingRobot(
		int num,
		int wait
		);
	// RobServer-Doku: CM_PING
	public double ping();
	// RobServer-Doku: SetVerbosity
	public boolean setVerbosity(
		int verbosity
		);
	// RobServer-Doku: SetAdeptSpeed
	public boolean setAdeptSpeed(
		int speed
		);
	// RobServer-Doku: SetAdeptAccel
	public boolean setAdeptAccel(
		int accelerate,
		int brake
		);
	// RobServer-Doku: GetJointsMaxSpeed
	public double[] getJointsMaxChange();
	// RobServer-Doku: SetJointsMaxSpeed
	public boolean setJointsMaxChange(
		double alpha1,
		double alpha2,
		double alpha3,
		double alpha4,
		double alpha5,
		double alpha6
		);
	// RobServer-Doku: MoveRTHomRowWise
	public boolean moveRTHomRowWise(
		matrix matrix
		);
	// RobServer-Doku: MoveRTHomRowWiseStatus
	public boolean moveRTHomRowWiseStatus (
		matrix matrix,
		boolean flip,
		boolean toggleHand,
		boolean up,
		boolean toggleElbow,
		boolean lefty,
		boolean toggleArm
		);
	// RobServer-Doku: GetPositionHomRowWise
	public matrix getPositionHomRowWise();
	// RobServer-Doku: GetStatus
	public String[] getStatus();
}
