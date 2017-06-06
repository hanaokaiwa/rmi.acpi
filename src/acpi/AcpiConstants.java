/**
 * 
 */
package acpi;

import java.text.DecimalFormat;

/**
 * 定数定義
 */
public class AcpiConstants {

	/** BIND名 */
	static final String BIND_NAME = "ACPI";
	/** バッテリーの残量%ファイルのパス */
	static final String BATTERY_CAPACITY_PATH = "/sys/class/power_supply/BAT0/capacity";
	/** バッテリーのステータスファイルのパス */
	static final String BATTERY_STATUS_PATH = "/sys/class/power_supply/BAT0/status";
	/** バッテリーの残量数値ファイルのパス */
	static final String BATTERY_ENERGY_NOW_PATH = "/sys/class/power_supply/BAT0/energy_now";
	/** バッテリーの全量数値ファイルのパス */
	static final String BATTERY_ENERGY_FULL_PATH = "/sys/class/power_supply/BAT0/energy_full";
	/** AC電源が入っているかどうかのパス */
	static final String AC_POWER_PATH = "/sys/class/power_supply/AC/online";

	/** AC電源がONのときの値 */
	static final String AC_POWER_ON = "1";
	/** バッテリーが充電中のときの値 */
	static final String BATTERY_STATUS_CHARGING = "Charging";

	/** サーバーのホスト名を記述するファイル */
	static final String HOSTNAME_FILE = "hostname";
	/** サーバーのホスト名のデフォルト値 */
	static final String HOSTNAME_DEFAUTL_VALUE = "smbhost";

	/** アイコンファイル名の前半部分 */
	static final String ICON_FILENAME_PREFIX = "/gpm-battery-";
	/** アイコンファイル名の拡張子 */
	static final String ICON_FILENAME_EXTENSION = ".png";
	/** 充電中のアイコンファイル名 */
	static final String ICON_FILENAME_CHARGING = "-charging";
	/** アイコンファイル名の数字フォーマット */
	static final String ICON_FILENAME_NUMBER_FORMAT = "000";
	/** 数字フォーマッター */
	static final ThreadLocal<DecimalFormat> ICON_FILENAME_NUMBER_FORMATTER = new ThreadLocal<DecimalFormat>() {
		@Override
		public DecimalFormat get() {
			return new DecimalFormat(ICON_FILENAME_NUMBER_FORMAT);
		}
	};
	/** バッテリーがないときのファイル名 */
	static final String ICON_FILENAME_NO_BATTERY = "/gpm-ac-adapter.png";
	/** バッテリーがフルのときのファイル名 */
	static final String ICON_FILENAME_FULL_BATTERY = "/gpm-battery-charged.png";

	/** バッテリーがないときの値 */
	static final int BATTERY_CAPACITY_NO_BATTERY = -1;

	/** 更新の時間間隔（ms） */
	static final long REFRESH_INTERVAL = 3000L;

	/** 警告を表示するバッテリーの残量% */
	static final int WARNING_BATTERY_CAPACITY = 10;

	/** 警告を表示する間隔（ms） */
	static final long DISPLAY_WARNING_INTERVAL = 300000L;

	/** サーバー側のポート番号 */
	static final int ACPI_SERVER_PORT_NUMBER = 40065;
}