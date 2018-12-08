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
	public string getTimestamp();
	// RobServer-Doku: PingRobot
	public int[] pingRobot(
		int num,
		int wait
		);
	// RobServer-Doku: CM_PING
	public int ping();
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
	public float[] getJointsMaxChange();
	// RobServer-Doku: SetJointsMaxSpeed
	public boolean setJointsMaxChange(
		int alpha1,
		int alpha2,
		int alpha3,
		int alpha4,
		int alpha5,
		int alpha6
		);
	// RobServer-Doku: MoveRTHomRowWise
	public boolean moveRTHomRowWise(
		Matrix matrix
		);
	// RobServer-Doku: MoveRTHomRowWiseStatus
	public boolean moveRTHomRowWiseStatus (
		Matrix matrix,
		boolean flip=true,
		boolean toggleHand=true,
		boolean up=true,
		boolean toggleElbow=true,
		boolean lefty=true,
		boolean toggleArm=true
		);
	// RobServer-Doku: GetPositionHomRowWise
	public Matrix getPositionHomRowWise();
	// RobServer-Doku: GetStatus
	public String[] getStatus();
}
