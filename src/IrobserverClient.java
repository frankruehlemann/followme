public interface IrobserverClient
{
	public boolean connect(String host; int port);
	public String getVersion();
	public boolean quit();
	public string getTimestamp();
	public int[] pingRobot(int num; int wait);
	public int ping();
	public boolean setVerbosity(int verbosity);
	public boolean setAdeptSpeed(int speed);
	public boolean setAdeptAccel(int accelerate; int brake);
	public int[] getJointsMaxChange();
	public boolean setJointsMaxChange(int alpha1; int alpha2; int alpha3; int alpha4; int alpha5; int alpha6);
// TODO:	public boolean moveMinChangeRowWiseStatus()
}
