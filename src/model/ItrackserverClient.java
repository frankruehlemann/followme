package model;

public interface ItrackserverClient
{
	// Verbindung zum Server
	// Inklusive aller sinnvollen Intialisierungsanweisungen
	public boolean connect(
		String host,
		int port
		);
	// TrackServer-Doku: CM_GETTRACKERS
	public String[] getMarkers();
	// TrackServer-Doku: Auswahl eines Trackers (Markers)
	public boolean chooseMarker(
		String marker
		);
	// TrackServer-Doku: Ausgabeformate der Trackerdaten
	public boolean chooseOutputFormat(
		String format
		);
	// TrackServer-Doku: CM_NEXTVALUE
	public float[] getNextValues();
	// TrackServer-Doku: CM PING
	public boolean ping();
	// TrackServer-Doku: CM SETLOGLEVEL
	public boolean setLogLevel(
		String level
		);
	// Sauberer Abbau der Verbindung
	public boolean quit();
	// TrackServer-Doku: CM GETREVISION
	public String getVersion();
}
