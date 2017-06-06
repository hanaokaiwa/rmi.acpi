/**
 * 
 */
package acpi;

import java.awt.AWTException;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.TimerTask;

import javax.imageio.ImageIO;

/**
 * @author daniel
 *
 */
public class AcpiTimerTask extends TimerTask {

	private Acpi acpi;

	private PopupMenu menu;

	private String filename;

	/** 直前取得した値 */
	// private int oldCapacity = -1;
	private boolean oldOnAcPower = false;
	// private boolean oldCharging = false;
	/** 直前警告を表示した時刻 */
	private long lastWarningTime = 0L;

	public AcpiTimerTask(Acpi acpi, PopupMenu menu) {
		super();
		this.acpi = acpi;
		this.menu = menu;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.TimerTask#run()
	 */
	@Override
	public void run() {
		try {
			int capacity = acpi.getBatteryCapacity();
			boolean onAcPower = acpi.onAcPower();
			boolean charging = acpi.isCharging();
			String iconFileName = AcpiUtils.getIconFileName(capacity, onAcPower, charging);
			String message = AcpiUtils.getMessage(capacity, onAcPower, charging);

			SystemTray systemTray = SystemTray.getSystemTray();
			TrayIcon icon = null;
			if ((null == systemTray.getTrayIcons()) || (0 == systemTray.getTrayIcons().length)) {
				try (InputStream is = getClass().getResourceAsStream(iconFileName)) {
					BufferedImage image = ImageIO.read(is);
					icon = new TrayIcon(image, message, menu);
					icon.setImageAutoSize(true);
					systemTray.add(icon);
				}
				// ファイル名を保存
				filename = iconFileName;
			} else {
				icon = systemTray.getTrayIcons()[0];
				icon.setToolTip(message);
				// アイコンファイルが変更された場合
				if (!iconFileName.equals(filename)) {
					try (InputStream is = getClass().getResourceAsStream(iconFileName)) {
						icon.setImage(ImageIO.read(is));
					}
					filename = iconFileName;
				}
			}
			if (null != icon) {
				if (oldOnAcPower && !onAcPower) {
					icon.displayMessage("", "バッテリーが放電中、残り" + capacity + "%。", TrayIcon.MessageType.INFO);
				}
				if ((capacity <= AcpiConstants.WARNING_BATTERY_CAPACITY)
						&& (System.currentTimeMillis() - lastWarningTime > AcpiConstants.DISPLAY_WARNING_INTERVAL)
						&& !onAcPower) {
					icon.displayMessage("警告", "バッテリーの残量が少なくなっています(" + capacity + "%)。", TrayIcon.MessageType.WARNING);
					lastWarningTime = System.currentTimeMillis();
				}
				if (onAcPower) {
					// ACが入っている場合は警告時刻をクリア
					lastWarningTime = 0L;
				}
			}
			// oldCapacity = capacity;
			oldOnAcPower = onAcPower;
			// oldCharging = charging;
		} catch (IOException | AWTException e) {
			this.cancel();
			e.printStackTrace();
		}
	}
}
