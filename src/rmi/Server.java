/**
 * 
 */
package rmi;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import sun.misc.Signal;
import sun.misc.SignalHandler;

/**
 * @author daniel
 */
public class Server implements Hello {

	public Server() {
	}
	
	/* (non-Javadoc)
	 * @see rmi.Hello#sayHello()
	 */
	@Override
	public String sayHello() throws RemoteException {
		return "Hello, world!";
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Server obj = new Server();
		try {
			Hello stub = (Hello) UnicastRemoteObject.exportObject(obj, 0);
			
			// Bind the remote object's stub in the registry
			final Registry registry = LocateRegistry.getRegistry();
			registry.bind("Hello", stub);
			
			System.out.println("Server ready.");
			
			Signal.handle(new Signal("INT"), new SignalHandler() {
				@Override
				public void handle(Signal sig) {
					try {
						registry.unbind("Hello");
					} catch (RemoteException | NotBoundException e) {
						System.err.println("Unbind exception: " + e.toString());
						e.printStackTrace();
					}
					System.out.println("Server exit.");
					System.exit(0);
				}
			});
		} catch (RemoteException | AlreadyBoundException e) {
			System.err.println("Server exception: " + e.toString());
			e.printStackTrace();
		}
	}

}
