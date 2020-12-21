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

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import bbdd.GestorBD;
import clasesBasicas.Coche;
import clasesBasicas.EmailBienvenida;
import clasesBasicas.Marca;
import clasesBasicas.TextPrompt;
import clasesBasicas.Usuario;
import interfaces.LeeDeFichero;

public class VentanaAddVehiculo extends JFrame implements LeeDeFichero {

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

	public VentanaAddVehiculo(Usuario u) {
		this.setTitle("VVS");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(100, 100, 500, 300);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("img/vvs.png"));

//CREACION DE PANELES

		// Panel donde estarán todos los componentes
		panelContenidos = new JPanel();
		panelContenidos.setBackground(Color.WHITE);
		setContentPane(panelContenidos);
		setLayout(new BorderLayout(10, 10));
		

		// Panel Izquierdo (nombre de ususario y todos los campos para aniadir un
		// vehiculo)
		JPanel panelIzquierdo = new JPanel();
		panelIzquierdo.setBackground(Color.WHITE);
		panelIzquierdo.setLayout(new BorderLayout());
		panelContenidos.add(panelIzquierdo, BorderLayout.WEST);

		// Panel izquierdo superior (nombre del usuario)
		JPanel panelIzquierdoSuperior = new JPanel();
		panelIzquierdoSuperior.setBackground(Color.WHITE);
		panelIzquierdoSuperior.setLayout(new GridLayout(1, 1));
		panelIzquierdo.add(panelIzquierdoSuperior, BorderLayout.NORTH);

		// Panel izquierdo central (campos para aniadir un vehiculo)
		JPanel panelIzquierdoCentral = new JPanel();
		panelIzquierdoCentral.setBackground(Color.WHITE);
		panelIzquierdoCentral.setLayout(new GridLayout(8, 2));
		panelIzquierdo.add(panelIzquierdoCentral, BorderLayout.CENTER);

		// Panel Inferior(Boton aniadir vehiculo y Cancelar)
		JPanel panelInferior = new JPanel();
		panelInferior.setBackground(Color.WHITE);
		panelInferior.setLayout(new GridLayout(1, 2));
		panelContenidos.add(panelInferior, BorderLayout.SOUTH);

		// Panel Central(Descripcion del vehiculo y rutas de las imagenes)
		JPanel panelDerecho = new JPanel();
		panelDerecho.setBackground(Color.WHITE);
		panelDerecho.setLayout(new BorderLayout());
		panelContenidos.add(panelDerecho, BorderLayout.CENTER);

		// Panel Central central(RUtas de las imagenes);
		JPanel panelDerechoCentral = new JPanel();
		panelDerechoCentral.setBackground(Color.WHITE);
		panelDerechoCentral.setLayout(new GridLayout(3, 1));
		panelDerecho.add(panelDerechoCentral, BorderLayout.CENTER);

		// Condiciones para los campos
		cbMarca = new JComboBox<String>();

		// Lectura de las marcas y modelos del fichero
		cbMarca.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {

			}
		});
		cbMarca.setModel(new DefaultComboBoxModel<String>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			private boolean seleccionPermitida = true;

			@Override
			public void setSelectedItem(Object objeto) {
				if (!opcionNoSeleccionableMarca.equals(objeto)) {
					super.setSelectedItem(objeto);

				} else if (seleccionPermitida) {
					seleccionPermitida = false;
					super.setSelectedItem(objeto);
				}

			}

		});
		cbMarca.addItem(opcionNoSeleccionableMarca);
		cbMarca.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String marcaSeleccionada = (String) cbMarca.getSelectedItem();
				cbModelo.removeAllItems();
				for (Entry<String, Marca> marca : mapaMarcas.entrySet()) {

					if (marcaSeleccionada.equals(marca.getValue().getNombre())) {
						for (String modelos : marca.getValue().getModelos()) {
							cbModelo.addItem(modelos);

						}

					}

				}

			}
		});

		cbModelo = new JComboBox<String>();
		cbModelo.setModel(new DefaultComboBoxModel<String>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			private boolean seleccionPermitida = true;

			@Override
			public void setSelectedItem(Object objeto) {
				if (!opcionNoSeleccionableModelo.equals(objeto)) {
					super.setSelectedItem(objeto);
				} else if (seleccionPermitida) {
					seleccionPermitida = false;
					super.setSelectedItem(objeto);
				}

			}
		});
		cbModelo.addItem(opcionNoSeleccionableModelo);

		cbCombustible = new JComboBox<String>();
		cbCombustible.setModel(new DefaultComboBoxModel<String>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			private boolean seleccionPermitida = true;

			@Override
			public void setSelectedItem(Object objeto) {
				if (!opcionNoSeleccionableCombustible.equals(objeto)) {
					super.setSelectedItem(objeto);

				} else if (seleccionPermitida) {
					seleccionPermitida = false;
					super.setSelectedItem(objeto);
				}

			}

		});
		cbCombustible.addItem(opcionNoSeleccionableCombustible);
		cbCombustible.addItem("Gasolina");
		cbCombustible.addItem("Diesel");
		cbCombustible.addItem("Electrico");

		cbCarroceria = new JComboBox<String>();
		cbCarroceria.setModel(new DefaultComboBoxModel<String>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			private boolean seleccionPermitida = true;

			@Override
			public void setSelectedItem(Object objeto) {
				if (!opcionNoSeleccionableCarroceria.equals(objeto)) {
					super.setSelectedItem(objeto);

				} else if (seleccionPermitida) {
					seleccionPermitida = false;
					super.setSelectedItem(objeto);
				}

			}

		});
		cbCarroceria.addItem(opcionNoSeleccionableCarroceria);
		cbCarroceria.addItem("Coche pequeño");
		cbCarroceria.addItem("Cabrio");
		cbCarroceria.addItem("Coupe");
		cbCarroceria.addItem("4x4/Pickup");
		cbCarroceria.addItem("Sedan");
		cbCarroceria.addItem("Familiar");
		cbCarroceria.addItem("Monovolumen");

		cbAnio = new JComboBox<String>();
		cbAnio.setModel(new DefaultComboBoxModel<String>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			private boolean seleccionPermitida = true;

			@Override
			public void setSelectedItem(Object objeto) {
				if (!opcionNoSeleccionableAnio.equals(objeto)) {
					super.setSelectedItem(objeto);

				} else if (seleccionPermitida) {
					seleccionPermitida = false;
					super.setSelectedItem(objeto);
				}

			}

		});
		cbAnio.addItem(opcionNoSeleccionableAnio);
		Calendar cal = Calendar.getInstance();
		int anio = cal.get(Calendar.YEAR);
		for (int i = 1950; i <= anio; i++) {
			cbAnio.addItem(Integer.toString(i));
			cbAnio.addItem(Integer.toString(i));

		}


		tfPrecio = new JTextField();
		ph = new TextPrompt("Precio", tfPrecio);

		tfKms = new JTextField();
		ph = new TextPrompt("Kilometros", tfKms);

		tfPotencia = new JTextField();
		ph = new TextPrompt("Potencia", tfPotencia);

		cbTransmision = new JComboBox<String>();
		cbTransmision.setModel(new DefaultComboBoxModel<String>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			private boolean seleccionPermitida = true;

			@Override
			public void setSelectedItem(Object objeto) {
				if (!opcionNoSeleccionableTransmision.equals(objeto)) {
					super.setSelectedItem(objeto);

				} else if (seleccionPermitida) {
					seleccionPermitida = false;
					super.setSelectedItem(objeto);
				}

			}

		});
		cbTransmision.addItem(opcionNoSeleccionableTransmision);
		cbTransmision.addItem("Automatico");
		cbTransmision.addItem("Manual");
		cbTransmision.addItem("Semiautomatico");

		cbNPuertas = new JComboBox<String>();
		cbNPuertas.setModel(new DefaultComboBoxModel<String>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			private boolean seleccionPermitida = true;

			@Override
			public void setSelectedItem(Object objeto) {
				if (!opcionNoSeleccionableNPuertas.equals(objeto)) {
					super.setSelectedItem(objeto);

				} else if (seleccionPermitida) {
					seleccionPermitida = false;
					super.setSelectedItem(objeto);
				}

			}

		});
		cbNPuertas.addItem(opcionNoSeleccionableNPuertas);
		cbNPuertas.addItem("3");
		cbNPuertas.addItem("5");
		cbNPuertas.addItem("7");

		cbNPlazas = new JComboBox<String>();
		cbNPlazas.setModel(new DefaultComboBoxModel<String>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			private boolean seleccionPermitida = true;

			@Override
			public void setSelectedItem(Object objeto) {
				if (!opcionNoSeleccionableNPlazas.equals(objeto)) {
					super.setSelectedItem(objeto);

				} else if (seleccionPermitida) {
					seleccionPermitida = false;
					super.setSelectedItem(objeto);
				}

			}

		});
		cbNPlazas.addItem(opcionNoSeleccionableNPlazas);
		cbNPlazas.addItem("2");
		cbNPlazas.addItem("4");
		cbNPlazas.addItem("5");
		cbNPlazas.addItem("7");

		cbNRuedas = new JComboBox<String>();
		cbNRuedas.setModel(new DefaultComboBoxModel<String>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			private boolean seleccionPermitida = true;

			@Override
			public void setSelectedItem(Object objeto) {
				if (!opcionNoSeleccionableNRuedas.equals(objeto)) {
					super.setSelectedItem(objeto);

				} else if (seleccionPermitida) {
					seleccionPermitida = false;
					super.setSelectedItem(objeto);
				}

			}

		});
		cbNRuedas.addItem(opcionNoSeleccionableNRuedas);
		cbNRuedas.addItem("2");
		cbNPlazas.addItem("3");

		imagenes = new JList<File>();
		imagenes.setBackground(Color.WHITE);
		spI = new JScrollPane(imagenes);
		DefaultListModel<File> lista = new DefaultListModel<File>();
		imagenes.setModel(lista);

		descripcion = new JTextArea();
		descripcion.setMaximumSize(panelDerechoCentral.getSize());
		descripcion.setLineWrap(true); // Salta de linea cuando alzanza el final del ancho
		sp = new JScrollPane(descripcion);
		sp.setBounds(panelDerechoCentral.getBounds());
		ph = new TextPrompt("Descripcion del vehiculo", descripcion);
		btnAniadir = new JButton("Aniadir");
		btnImagenes = new JButton("Imagenes");
		btnImagenes.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				FileNameExtensionFilter filtro = new FileNameExtensionFilter("*.JPG", "jpg");
				FileNameExtensionFilter filtro2 = new FileNameExtensionFilter("*.PNG", "png");
				fc.setFileFilter(filtro);
				fc.setFileFilter(filtro2);

				int seleccion = fc.showOpenDialog(btnImagenes);

				File fichero = fc.getSelectedFile();

				if (seleccion == JFileChooser.APPROVE_OPTION) {
					lista.addElement(fichero);
					// Conexion con la base de datos
					GestorBD modSql = new GestorBD();

					Coche c = new Coche();
					c.setIdvehiculos(modSql.obtenerIdVehiculo());
					c.setMarca((String) cbMarca.getSelectedItem());
					c.setModelo((String) cbModelo.getSelectedItem());
					c.setCarroceria((String) cbCarroceria.getSelectedItem());
					c.setCombustible((String) cbCombustible.getSelectedItem());
					c.setAnio(Integer.parseInt((String) cbAnio.getSelectedItem()));
					c.setPrecio(Integer.parseInt((String) tfPrecio.getText()));
					c.setKilometros(Long.parseLong((String) tfKms.getText()));
					c.setPotencia(Integer.parseInt((String) tfPotencia.getText()));
					c.setTransmision((String) cbTransmision.getSelectedItem());
					c.setnPlazas(Integer.parseInt((String) cbNPlazas.getSelectedItem()));
					c.setnPuertas(Integer.parseInt((String) cbNPuertas.getSelectedItem()));
					c.setImagen(fichero);
	

					btnAniadir.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {

							if (modSql.aniadirCoche(c, u, fichero)) {

								JOptionPane.showMessageDialog(null, "Vehiculo anadido correctamente!!");

								try {
									Document document = new Document();
									try {
										pdf = "Recibo de tu vehiculo " + Integer.toString(c.getIdvehiculos()) + ".pdf";
										PdfWriter.getInstance(document, new FileOutputStream(new File(pdf)));
									} catch (FileNotFoundException e1) {
										e1.printStackTrace();
									}
									document.open();

									// AQUÍ COMPLETAREMOS NUESTRO CÓDIGO PARA GENERAR EL PDF

									document.addTitle("Table export to PDF (Exportamos la tabla a PDF)");
									document.addSubject("Using iText (usando iText)");
									document.addKeywords("Java, PDF, iText");
									document.addAuthor("AutoTrader");
									document.addCreator("AutoTrader");

									// Primera página
									Chunk chunk = new Chunk("¡Gracias por vender con AutoTrader!", chapterFont);

									// Let's create de first Chapter (Creemos el primer capítulo)
									Chapter chapter = new Chapter(new Paragraph(chunk), 1);
									chapter.setNumberDepth(0);
									chapter.add(new Paragraph("", paragraphFont));
									chapter.add(new Paragraph(
											"Recientemente acabamos de aniadir tu vehiculo a la venta, te mostramos"
													+ " los datos para asegurarte que sean correctos. \n "));

									chapter.add(new Paragraph("Datos personales:", titleFont));
									chapter.add(new Paragraph(" idUsuario: " + u.getIdusuarios()
											+ " \n Nombre y apellido: " + u.getNombre() + " " + u.getApellido()
											+ " \n Correo: " + u.getEmail() + " \n "));

									chapter.add(new Paragraph("Datos del vehiculo:", titleFont));
									chapter.add(new Paragraph(" idVehiculo: " + c.getIdvehiculos() + " \n Marca: "
											+ c.getMarca() + " \n Modelo: " + c.getModelo() + " \n Combustible: "
											+ c.getCombustible() + " \n Carroceria: " + c.getCarroceria() + " \n Anio: "
											+ c.getAnio() + " \n Precio: " + c.getPrecio() + " \n Kilometros: "
											+ c.getKilometros() + " \n Potencia: " + c.getPotencia()
											+ " \n Transmision: " + c.getTransmision() + " \n Puertas: "
											+ c.getnPuertas() + " \n Plazas: " + c.getnPlazas() + " \n"));

									chapter.add(new Paragraph("Descripcion del vehiculo: \n", titleFont));
									chapter.add(new Paragraph(descripcion.getText()));
									chapter.add(new Paragraph(""));
									Image imagen;
									try {
										imagen = Image.getInstance(fichero.getAbsolutePath());
										imagen.setAbsolutePosition(2, 150);
										chapter.add(imagen);
									}catch (BadElementException e1) {
										e1.printStackTrace();
									}catch(IOException e2) {
										e2.printStackTrace();
									}
		

									document.add(chapter);
									document.close();

									Thread hilo = new Thread(new Runnable() {

										@Override
										public void run() {
											// TODO Auto-generated method stub
											try {

												EmailBienvenida.mandarMailConArchivo("smtp.gmail.com", "587",
														"autotrader.informacion@gmail.com", "contraseña", u.getEmail(),
														"¡Vehiculo aniadido con exito!",
														"Estimad@ cliente, le mandamos la ficha del vehiculo", pdf);

											} catch (AddressException e1) {
												// TODO Auto-generated catch block
												e1.printStackTrace();
											} catch (MessagingException e1) {
												// TODO Auto-generated catch block
												e1.printStackTrace();
											}

										}
									});
									hilo.start();

								} catch (DocumentException e2) {
									e2.printStackTrace();
								}

							}

							modSql.dissconect();
						}

					});

				}

			}

		});

		btnVolver = new JButton("Volver");
		btnVolver.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				VentanaProgramaFs v = new VentanaProgramaFs(u);
				v.setVisible(true);
				dispose();

			}
		});
		repaint();

//Aniadimos cada elemento a su panel

		// Panel izquierdo --> superior
		menu = new JMenuBar();
		menuUsuarios = new JMenu(u.getEmail());
		menu.add(menuUsuarios);
		menuItem = new JMenuItem("Cerrar sesion");
		menuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				dispose();
				VentanaInicio vi = new VentanaInicio();
				vi.setVisible(true);
			}
		});

		menuUsuarios.add(menuItem);
		
		
		JMenuItem menuItem2 = new JMenuItem("Borrar cuenta");
		menuItem2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				GestorBD bd = new GestorBD();
				int id = u.getIdusuarios();
				bd.borrarUsuario(u, id);
				dispose();
				VentanaInicio v = new VentanaInicio();
				v.setVisible(true);
			}
		});
		menuUsuarios.add(menuItem2);
		
		JMenuItem menuItem3 = new JMenuItem("Eliminar vehiculo");
		menuItem3.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				VentanaVehiculosUsuario v = new VentanaVehiculosUsuario(u);
				v.setVisible(true);
				dispose();
			}
		});
		
		menuUsuarios.add(menuItem3);

		panelIzquierdoSuperior.add(menu);

		// Panel izquierdo --> central
		panelIzquierdoCentral.add(cbMarca);
		panelIzquierdoCentral.add(cbModelo);
		panelIzquierdoCentral.add(cbCombustible);
		panelIzquierdoCentral.add(cbCarroceria);
		panelIzquierdoCentral.add(cbAnio);
		panelIzquierdoCentral.add(tfPrecio);
		panelIzquierdoCentral.add(tfKms);
		panelIzquierdoCentral.add(tfPotencia);
		panelIzquierdoCentral.add(cbTransmision);
		panelIzquierdoCentral.add(cbNPlazas);
		panelIzquierdoCentral.add(btnImagenes);
		panelIzquierdoCentral.add(cbNPuertas);

		// Panel Central --> Central
		panelDerechoCentral.add(sp);

		// Panel inferior --> Botones
		panelInferior.add(btnAniadir);
		panelInferior.add(btnVolver);

		panelDerechoCentral.add(spI);

		leeMarcas();
		leeModelos();

	}

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
