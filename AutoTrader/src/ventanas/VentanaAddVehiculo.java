package ventanas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.logging.FileHandler;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;


import bbdd.GestorBD;
import clasesBasicas.Coche;
import clasesBasicas.EmailBienvenida;
import clasesBasicas.Marca;
import clasesBasicas.TextPrompt;
import clasesBasicas.Usuario;
import interfaces.LeeDeFichero;

public class VentanaAddVehiculo extends JFrame implements LeeDeFichero {
	public VentanaAddVehiculo() {
	}

	/**
	 * 
	 */

	private static final Font chapterFont = FontFactory.getFont(FontFactory.HELVETICA, 26, Font.NORMAL);
	private static final Font titleFont = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL);
	private static final Font paragraphFont = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL);
	private static final long serialVersionUID = -161687001194396366L;
	private String pdf;

	private HashMap<String, Marca> mapaMarcas = new HashMap<>();
	// private String fichero;

	private GestorBD con = null;
	private PreparedStatement ps;

	// Componentes de la ventana

	private JMenuBar menu;
	private JMenu menuUsuarios;
	private JMenuItem menuItem;
	private JScrollPane spI;
	private JScrollPane sp;
	private TextPrompt ph;
	private JPanel panelContenidos;

	private JComboBox<String> cbMarca;
	private String opcionNoSeleccionableMarca = "Marca";

	private JComboBox<String> cbModelo; /// Hasta quen no se eliga la marca va a estar en enable = false
	private String opcionNoSeleccionableModelo = "Modelo";

	private JComboBox<String> cbCombustible;
	private String opcionNoSeleccionableCombustible = "Tipo Combustible";

	private JComboBox<String> cbCarroceria;
	private String opcionNoSeleccionableCarroceria = "Carroceria";

	private JComboBox<String> cbAnio;
	private String opcionNoSeleccionableAnio = "Anio";

	private JTextField tfPrecio;
	private JTextField tfKms;
	private JTextField tfPotencia;

	private JComboBox<String> cbTransmision;
	private String opcionNoSeleccionableTransmision = "Transmision";

	private JComboBox<String> cbNPuertas;
	private String opcionNoSeleccionableNPuertas = "Puertas";

	private JComboBox<String> cbNPlazas;
	private String opcionNoSeleccionableNPlazas = "Plazas";

	private JComboBox<String> cbNRuedas;
	private String opcionNoSeleccionableNRuedas = "Ruedas";

	private JTextArea descripcion;
	private JList<File> imagenes;

	private JButton btnAniadir;
	private JButton btnVolver;
	private JButton btnImagenes;
	@Override
	public void leeMarcas() {
		BufferedReader br = null;
		FileReader ficheroMarca = null;

		try {
			ficheroMarca = new FileReader("data/marcas.csv");
			br = new BufferedReader(ficheroMarca);
			String fila = null;
			while ((fila = br.readLine()) != null) {
				String[] valores = fila.split(";");
				cbMarca.addItem(valores[1]);
				mapaMarcas.put(valores[0], new Marca(valores[0], valores[1]));
			}
			ficheroMarca.close();
			br.close();
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	@Override
	public void leeModelos() {
		BufferedReader br = null;
		FileReader ficheroModelos = null;

		try {
			ficheroModelos = new FileReader("data/modelos.csv");
			br = new BufferedReader(ficheroModelos);
			String fila = null;
			while ((fila = br.readLine()) != null) {
				String[] valores = fila.split(";");
				mapaMarcas.get(valores[0]).addModelo(valores[2]);
			}
			ficheroModelos.close();
			br.close();
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}		
	}
	}
