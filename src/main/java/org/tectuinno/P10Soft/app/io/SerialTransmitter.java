package org.tectuinno.P10Soft.app.io;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fazecast.jSerialComm.SerialPort;

public class SerialTransmitter implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5943981126324312270L;

	private SerialPort serialPort;
	
	public static List<String> listAviablePorts(){
		List<String> portNames = new ArrayList<String>();
	    for (SerialPort p : SerialPort.getCommPorts()) {
	        portNames.add(p.getSystemPortName());
	    }
	    return portNames;
	}
	
	public boolean openPort(String portName) {
		serialPort = SerialPort.getCommPort(portName);
        serialPort.setComPortParameters(115200, 8, SerialPort.ONE_STOP_BIT, SerialPort.NO_PARITY);
        serialPort.setComPortTimeouts(SerialPort.TIMEOUT_WRITE_BLOCKING, 0, 0);
        return serialPort.openPort();
	}
	
	public boolean sendHexFrame(String hexFrame) {
        if (serialPort == null || !serialPort.isOpen()) {
            System.err.println("El puerto no está abierto.");
            return false;
        }

        // Convertir String Hex → bytes reales
        byte[] data = hexStringToByteArray(hexFrame);

        int bytesWritten = serialPort.writeBytes(data, data.length);
        return bytesWritten == data.length;
    }
	
	private byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                                 + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }
	
	public void closePort() {
        if (serialPort != null && serialPort.isOpen()) {
            serialPort.closePort();
        }
    }
	
}
