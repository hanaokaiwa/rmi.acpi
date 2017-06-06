/**
 * 
 */
package acpi;

import java.awt.PopupMenu;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Timer;
import java.util.TimerTask;

/**
 * ACPIの状況を取得するクライアント
 */
public class AcpiClient {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String hostname = getHostname();

		try {
			Registry registry = LocateRegistry.getRegistry(hostname);
			Acpi acpi = (Acpi) registry.lookup(AcpiConstants.BIND_NAME);

			PopupMenu menu = AcpiUtils.createPopupMenu();
			TimerTask timerTask = new AcpiTimerTask(acpi, menu);
			Timer timer = new Timer();
			timer.schedule(timerTask, 0L, AcpiConstants.REFRESH_INTERVAL);
		} catch (RemoteException | NotBoundException e) {
			System.err.println("Client exception: " + e.toString());
			e.printStackTrace();
		}
	}

	/**
	 * 定義ファイルにあるホスト名を取得する
	 * 
	 * @return 定義ファイルにあるホスト名
	 */
	private static String getHostname() {
		String hostname = AcpiUtils.readLineFromFile(AcpiConstants.HOSTNAME_FILE);
		if (null == hostname) {
			hostname = AcpiConstants.HOSTNAME_DEFAUTL_VALUE;
		}
		System.out.println("hostname=" + hostname);
		return hostname.trim();
	}
}
