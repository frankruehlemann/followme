public interface ItrackserverClient
{
	public boolean connect(String host; int port);
	public String[] getMarkers();
	public boolean chooseMarker(String marker);
	public boolean chooseOutputFormat(String format);
	public int[] getNextValues();
	public boolean ping();
	public boolean setLogLevel(String level);
	public boolean quit();
	public String getVersion();
}
