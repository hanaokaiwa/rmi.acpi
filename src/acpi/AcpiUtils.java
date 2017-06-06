/**
 * 
 */
package acpi;

import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * ACPI用ユーティリティー
 */
public class AcpiUtils {

	/**
	 * アイコンのファイル名を取得する
	 * 
	 * @param capacity
	 *            バッテリー残量
	 * @param onAcPower
	 *            電源ONかどうか
	 * @param charging
	 *            充電中かどうか
	 * @return アイコンのファイル名
	 */
	static String getIconFileName(int capacity, boolean onAcPower, boolean charging) {
		if (AcpiConstants.BATTERY_CAPACITY_NO_BATTERY == capacity) {
			// バッテリーがないとき、電源のみのアイコンを表示
			return AcpiConstants.ICON_FILENAME_NO_BATTERY;
		} else if (onAcPower && !charging) {
			// バッテリーがフルのとき
			return AcpiConstants.ICON_FILENAME_FULL_BATTERY;
		} else {
			StringBuilder sb = new StringBuilder();
			sb.append(AcpiConstants.ICON_FILENAME_PREFIX);
			sb.append(getFileNameNumber(capacity));
			if (charging) {
				sb.append(AcpiConstants.ICON_FILENAME_CHARGING);
			}
			sb.append(AcpiConstants.ICON_FILENAME_EXTENSION);
			return sb.toString();
		}
	}

	/**
	 * バッテリー残量からファイル名の数字を取得する
	 * 
	 * @param capacity
	 * @return ファイル名の数字
	 */
	static String getFileNameNumber(int capacity) {
		int number = (capacity + 10) / 20 * 20;
		return AcpiConstants.ICON_FILENAME_NUMBER_FORMATTER.get().format(number);
	}

	/**
	 * メッセージを取得する
	 * 
	 * @param capacity
	 *            バッテリー残量
	 * @param onAcPower
	 *            電源ONかどうか
	 * @param charging
	 *            充電中かどうか
	 * @return メッセージ
	 */
	static String getMessage(int capacity, boolean onAcPower, boolean charging) {
		if (AcpiConstants.BATTERY_CAPACITY_NO_BATTERY == capacity) {
			return "バッテリーがありません";
		} else if (onAcPower && !charging) {
			return "バッテリーは充電完了";
		} else {
			return "バッテリー残量" + capacity + "%";
		}
	}

	/**
	 * メニューを作成
	 * 
	 * @return 作成されたメニュー
	 */
	static PopupMenu createPopupMenu() {
		PopupMenu menu = new PopupMenu();
		MenuItem menuItem = new MenuItem("閉じる");
		menu.add(menuItem);
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		return menu;
	}

	/**
	 * ファイルから1行を読み取る
	 * 
	 * @param file
	 * @return ファイルの中身データ
	 */
	static String readLineFromFile(String filePath) {
		File file = new File(filePath);
		if (file.exists() && file.isFile() && file.canRead()) {
			try (FileReader fr = new FileReader(file); BufferedReader br = new BufferedReader(fr)) {
				return br.readLine();
			} catch (IOException | NumberFormatException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}
