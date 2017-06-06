package acpi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Acpi extends Remote {

	/**
	 * バッテリーの残量%を取得する
	 * 
	 * @return バッテリーの残量%
	 * @throws RemoteException
	 */
	int getBatteryCapacity() throws RemoteException;

	/**
	 * AC電源がONかどうかを取得する
	 * 
	 * @return AC電源がONかどうか
	 * @throws RemoteException
	 */
	boolean onAcPower() throws RemoteException;

	/**
	 * バッテリーが充電中かどうかを取得する
	 * 
	 * @return バッテリーが充電中かどうか
	 * @throws RemoteException
	 */
	boolean isCharging() throws RemoteException;
}
