package org.tectuinno.P10Soft.app.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import java.awt.Dimension;

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
	private final JPanel jPanelInferiorConsola = new JPanel();
	private final JButton btnConvertir = new JButton("Convertir");
	private final JButton btnEnviar = new JButton("Enviar");
	private final JMenuItem jMenuItemEscanear = new JMenuItem("Escanear COM");
	private final JComboBox<String> cmbDispositivosCOMDisponibles = new JComboBox<String>();
	private final JButton btnEscanear = new JButton("Escanear dispositivos");

	/**
	 * Create the frame.
	 */
	public StartingWindow() {
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
			contentPane.add(jPanelInferiorConsola, BorderLayout.SOUTH);
		}

	}

}
