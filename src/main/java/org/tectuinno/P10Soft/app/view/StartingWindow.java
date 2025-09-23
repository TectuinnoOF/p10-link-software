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

package org.tectuinno.P10Soft.app.view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import org.tectuinno.P10Soft.app.view.components.CellPixelPanel;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import java.awt.Dimension;
import javax.swing.JSplitPane;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class StartingWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private final JMenuBar menuBar = new JMenuBar();
	private final JMenu jMenuArchivo = new JMenu("Archivo");
	private final JMenuItem jMenuItemAbrir = new JMenuItem("Cargar diseño");
	private final JMenuItem jMenuItemNuevo = new JMenuItem("Nuevo diseño");
	private final JMenuItem jMenuItemGuardar = new JMenuItem("Guardar");
	private final JMenu jMenuHerramientas = new JMenu("Herramientas");
	private final JMenuItem jMenuItemConvertir = new JMenuItem("Convertir");
	private final JMenuItem jMenuItemEnviar = new JMenuItem("Enviar");
	private final JSeparator separator = new JSeparator();
	private final JMenuItem jMenuItemLimpiar = new JMenuItem("Limpiar tabla");
	private final JPanel jPanelSuperiorBotones = new JPanel();
	private final JButton btnConvertir = new JButton("Convertir");
	private final JButton btnEnviar = new JButton("Enviar");
	private final JMenuItem jMenuItemEscanear = new JMenuItem("Escanear COM");
	private final JComboBox<String> cmbDispositivosCOMDisponibles = new JComboBox<String>();
	private final JButton btnEscanear = new JButton("Escanear dispositivos");
	private final JSplitPane splitPaneTablaContenedor = new JSplitPane();
	private final JPanel panelContenedorTablaBinaria = new JPanel();
	private final JPanel panelGridLayOutTablaBinaria = new JPanel();
	private boolean[][] binaryTable = new boolean[16][32];
	private CellPixelPanel[][] cellsPixelPanels = new CellPixelPanel[16][32];

	/**
	 * Create the frame.
	 */
	public StartingWindow() {
		setTitle("P10 Link Software");
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				setTableDividerLocationEvent();
			}
		});
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 700);
		{
			setJMenuBar(menuBar);
		}
		{
			menuBar.add(jMenuArchivo);
		}
		{
			jMenuArchivo.add(jMenuItemAbrir);
		}
		{
			jMenuArchivo.add(jMenuItemNuevo);
		}
		{
			jMenuArchivo.add(jMenuItemGuardar);
		}
		{
			menuBar.add(jMenuHerramientas);
		}
		{
			jMenuHerramientas.add(jMenuItemConvertir);
		}
		{
			jMenuHerramientas.add(jMenuItemEnviar);
		}
		{
			jMenuHerramientas.add(jMenuItemEscanear);
		}
		{
			jMenuHerramientas.add(separator);
		}
		{
			jMenuItemLimpiar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					clearAllPixels();
				}
			});
			jMenuHerramientas.add(jMenuItemLimpiar);
		}
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		{
			FlowLayout flowLayout = (FlowLayout) jPanelSuperiorBotones.getLayout();
			flowLayout.setAlignment(FlowLayout.LEFT);

			contentPane.add(jPanelSuperiorBotones, BorderLayout.NORTH);
		}
		{
			btnConvertir.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					convertFrame();
				}
			});
			jPanelSuperiorBotones.add(btnConvertir);
		}
		{
			jPanelSuperiorBotones.add(btnEnviar);
		}
		{
			cmbDispositivosCOMDisponibles.setPreferredSize(new Dimension(190, 22));
			jPanelSuperiorBotones.add(cmbDispositivosCOMDisponibles);
		}
		{
			jPanelSuperiorBotones.add(btnEscanear);
		}
		{
			splitPaneTablaContenedor.setOrientation(JSplitPane.VERTICAL_SPLIT);
			this.splitPaneTablaContenedor.setDividerLocation(410);
			contentPane.add(splitPaneTablaContenedor, BorderLayout.CENTER);
		}
		{
			splitPaneTablaContenedor.setLeftComponent(panelContenedorTablaBinaria);
		}
		panelContenedorTablaBinaria.setLayout(new BorderLayout(0, 0));
		{
			panelContenedorTablaBinaria.add(panelGridLayOutTablaBinaria, BorderLayout.CENTER);
		}
		panelGridLayOutTablaBinaria.setLayout(new GridLayout(16, 32, 0, 0));

		this.buildTable();

	}

	private void setTableDividerLocationEvent() {
		this.splitPaneTablaContenedor.setDividerLocation(this.getHeight() - 310);
	}

	private void buildTable() {

		try {			
			if (this.panelGridLayOutTablaBinaria == null)
				throw new Exception("El componente es null");

			for (int i = 0; i < 16; i++) {
				for (int j = 0; j < 32; j++) {
					CellPixelPanel cellPanel = new CellPixelPanel("panel_" + i + "_" + j, new LineBorder(new Color(0, 0, 0)),j,i);
					cellPanel.setOn(false);
					cellPanel.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent e) { 
							super.mouseClicked(e);
							cellClickedEvent(cellPanel);
						}
					});
					this.panelGridLayOutTablaBinaria.add(cellPanel);
					
					
					this.binaryTable[i][j] = cellPanel.isPixelOn() ? true : false;
					this.cellsPixelPanels[i][j] = cellPanel;
					// jpanelList.add(cellPanel);
				}
			}

		} catch (Exception e) {

			JOptionPane.showMessageDialog(this, "Ha ocurrido un error:" + e.getMessage(), "Error de UI",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();

		}

	}
	
	public boolean[][] getBinaryTable() {
		return this.binaryTable;
	}		

	private void cellClickedEvent(CellPixelPanel panel) {
		
		panel.setOn(!panel.isPixelOn());
		
		if(panel.isPixelOn()) {		
			this.binaryTable[panel.getRow()][panel.getColumn()] = true;
			return;
		}
		
		if(!panel.isPixelOn()) {			
			this.binaryTable[panel.getRow()][panel.getColumn()] = false;
			return;
		}
	}
	
	private void convertFrame() {
		
		/*for(int i = 0 ; i < this.binaryTable.length; i++) {						
			
			for(int j = 0; j < this.binaryTable[i].length; j++) {
				
				System.out.print(" " + this.binaryTable[i][j]);
				
			}
			
			System.out.println();
			
		}*/
		
	}
	
	private void clearAllPixels() {
		for(int i = 0; i < cellsPixelPanels.length; i++) {
			for(int j = 0; j < cellsPixelPanels[i].length; j++) {
				this.cellsPixelPanels[i][j].setOn(false);				
			}
		}
	}

}
