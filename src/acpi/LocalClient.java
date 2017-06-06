/**
 * 
 */
package acpi;

import java.awt.PopupMenu;
import java.util.Timer;
import java.util.TimerTask;

/**
 * ローカル用のクライアント
 */
public class LocalClient {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Acpi acpi = new AcpiServer();

		PopupMenu menu = AcpiUtils.createPopupMenu();
		TimerTask timerTask = new AcpiTimerTask(acpi, menu);
		Timer timer = new Timer();
		timer.schedule(timerTask, 0L, AcpiConstants.REFRESH_INTERVAL);
	}
}
