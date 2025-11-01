/*
 * This file is part of P10-Soft.
 *
 * P10-Soft is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful
 * as a companion tool for the Tectuinno P10-Link chip, enabling users
 * to design, visualize and transmit frames to P10 LED panels in real time.
 * However, WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 *
 * As a special exception, you may use this file as part of a free software
 * library without restriction. Specifically, if other files instantiate
 * templates or use macros or inline functions from this file, or you compile
 * this file and link it with other files to produce an executable, this
 * file does not by itself cause the resulting executable to be covered by
 * the GNU General Public License. This exception does not however
 * invalidate any other reasons why the executable file might be covered by
 * the GNU General Public License.
 *
 * Copyright 2025 Tectuinno Team (https://github.com/tectuinno)
 */

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
