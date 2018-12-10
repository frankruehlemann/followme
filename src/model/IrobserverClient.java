package model;
public interface IrobserverClient
{
	public boolean connect(String host, String port);
	public String getVersion();
	public boolean quit();
	public String getTimestamp();
	public double[] pingRobot(int num, int wait);
	public boolean ping();
	public boolean setVerbosity(int verbosity);
	public boolean setAdeptSpeed(int speed);
	public boolean setAdeptAccel(int accelerate, int brake);
	public double[] getJointsMaxChange();
	public boolean setJointsMaxChange(double alpha1, double alpha2, double alpha3, double alpha4, double alpha5, double alpha6);
// TODO:	public boolean moveMinChangeRowWiseStatus()
}
