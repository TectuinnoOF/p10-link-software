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

public class FrameConverter {
	
	private static final int ROWS = 16;
	private static final int COLS = 32;
	
	public static String[] convertToHexFrame(boolean[][] binaryTable) throws Exception {
		
		if(binaryTable.length != ROWS || binaryTable[0].length != COLS) {
			throw new IllegalArgumentException("La matriz debe ser de 16x32");
		}
		
		String[] frame = new String[128];
		int index = 0;
		
		// Recorrer por bloques de 4 filas (submatrices 4x4)
        for (int blockRow = 0; blockRow < ROWS; blockRow += 4) {
            // Cada bloque de 4 filas se procesa por columnas de 8 bits
            for (int col = 0; col < COLS; col++) {
                int value = 0;
                // Construimos el byte fila por fila
                for (int bit = 0; bit < 4; bit++) {
                    int row = blockRow + bit;
                    int bitValue = binaryTable[row][col] ? 1 : 0;
                    value |= (bitValue << bit); // cada fila se desplaza
                }
                frame[index++] = String.format("%02X", value);
            }
        }

        return frame;
		
	}
	
	public static String convertToHexString(boolean[][] binaryTable) throws Exception {
        String[] frame = convertToHexFrame(binaryTable);
        StringBuilder sb = new StringBuilder();
        for (String hex : frame) {
            sb.append(hex);
        }
        return sb.toString();
    }
	
}
