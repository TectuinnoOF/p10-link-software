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

package org.tectuinno.P10Soft.app.core;

import javax.management.RuntimeErrorException;

public class FrameConverter {

	private static final int ROWS = 16;
	private static final int COLS = 32;
	
	/**
	 * Function to get the new hex matriz from the binary table
	 * @param matrix
	 * @return
	 * @throws Exception
	 */
	public static String[][] convertBinaryMatrixToHex(boolean[][] matrix) throws Exception, RuntimeException{
		
		if(matrix.length > ROWS) throw new RuntimeException("Matrix data to loong");
		if(matrix[0].length > COLS) throw new RuntimeException("Matrix data to loong");
		
		String[][] hexMatrix = new String[16][4];
		
        for (int block = 0; block < 4; block++) {
            int colStart = block * 8;
            int colEnd = colStart + 8;

            for (int row = ROWS - 1; row >= 0; row--) {
                boolean[] byteArray = new boolean[8];


                for (int col = 0; col < 8; col++) {
                    byteArray[col] = matrix[row][colStart + col];
                }

                String hex;				
				hex = fromBinaryToHex(byteArray);
				
                hexMatrix[ROWS - 1 - row][block] = hex;
            }
        }
		
		return hexMatrix;
		
	}
	
	/**
	 * To convert every binary byte into a hex byte
	 * @param bitArray
	 * @return
	 * @throws RuntimeException
	 */
	private static String fromBinaryToHex(boolean[] bitArray) throws RuntimeException{
		
		if(bitArray.length > 8) {
			throw new RuntimeException("Byte a procesar mayor a 8");
		}
		
		int value = 0;
		
		for(boolean bit : bitArray) {
			value = (value << 1) | (bit ? 1 : 0);
		}				
				
		
		return String.format("%02X", value);
	}
	
	/**
	 * convert the hex matrix to a byte array with the frem prepared to send
	 * @param hexMatrix
	 * @return
	 */
	public static String getRamArrayFrame(String[][] hexMatrix) {
		
		if (hexMatrix.length != 16 || hexMatrix[0].length != 4) {
	        throw new RuntimeException("La matriz hex debe ser de 16x4");
	    }

		StringBuilder trama = new StringBuilder();
		String[] ramArray = new String[16];
		
		ramArray[0] = hexMatrix[15][3] + hexMatrix[11][3] + hexMatrix[7][3] + hexMatrix[3][3];
		ramArray[1] = hexMatrix[15][2] + hexMatrix[11][2] + hexMatrix[7][2] + hexMatrix[3][2];
		ramArray[2] = hexMatrix[15][1] + hexMatrix[11][1] + hexMatrix[7][1] + hexMatrix[3][1];
		ramArray[3] = hexMatrix[15][0] + hexMatrix[11][0] + hexMatrix[7][0] + hexMatrix[3][0];
		
		ramArray[4] = hexMatrix[14][3] + hexMatrix[10][3] + hexMatrix[6][3] + hexMatrix[2][3];
		ramArray[5] = hexMatrix[14][2] + hexMatrix[10][2] + hexMatrix[6][2] + hexMatrix[2][2];
		ramArray[6] = hexMatrix[14][1] + hexMatrix[10][1] + hexMatrix[6][1] + hexMatrix[2][1];
		ramArray[7] = hexMatrix[14][0] + hexMatrix[10][0] + hexMatrix[6][0] + hexMatrix[2][0];
		
		ramArray[8] = hexMatrix[13][3] + hexMatrix[9][3] + hexMatrix[5][3] + hexMatrix[1][3];
		ramArray[9] = hexMatrix[13][2] + hexMatrix[9][2] + hexMatrix[5][2] + hexMatrix[1][2];
		ramArray[10] = hexMatrix[13][1] + hexMatrix[9][1] + hexMatrix[5][1] + hexMatrix[1][1];
		ramArray[11] = hexMatrix[13][0] + hexMatrix[9][0] + hexMatrix[5][0] + hexMatrix[1][0];
		
		ramArray[12] = hexMatrix[12][3] + hexMatrix[8][3] + hexMatrix[4][3] + hexMatrix[0][3];
		ramArray[13] = hexMatrix[12][2] + hexMatrix[8][2] + hexMatrix[4][2] + hexMatrix[0][2];
		ramArray[14] = hexMatrix[12][1] + hexMatrix[8][1] + hexMatrix[4][1] + hexMatrix[0][1];
		ramArray[15] = hexMatrix[12][0] + hexMatrix[8][0] + hexMatrix[4][0] + hexMatrix[0][0];
		
		for(String h : ramArray) {
			trama.append(h);
		}					

	    return trama.toString();

	}

	public static String convertToHexString(boolean[][] matrix) throws Exception {

		StringBuilder trama = new StringBuilder();

	    

	    return trama.toString();

	}

}
