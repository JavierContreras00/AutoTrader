package ventanas;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Pattern;

import javax.swing.JTextField;

import bbdd.GestorBD;

import clasesBasicas.Hash;
import clasesBasicas.TextPrompt;
import clasesBasicas.Usuario;

public class VentanaRegistrarse extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4377029028392917164L;

	public static Pattern patCorreo = Pattern
			.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

	private JPanel panelContenidos;
	private JLabel lbNombre;
	private JLabel lbApellido;
	private JLabel lbNick;
	private JLabel lbEmail;
	private JLabel lbContrasenia;
	private JLabel lbConfirmarContrasenia;
	private JTextField tfNombre;
	private JTextField tfApellido;
	private JTextField tfNick;
	private JTextField tfEmail;
	private JPasswordField pfContrasenia;
	private JPasswordField pfConfirmarContrasenia;
	private JButton btnRegistrarse;
	private JButton btnAtras;
	private TextPrompt ph;

	public VentanaRegistrarse() {
		this.setTitle("AutoTrader");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(100, 100, 500, 100);
		this.setMinimumSize(new Dimension(250, 300));

//Panel principal	
		panelContenidos = new JPanel();
		panelContenidos.setBackground(Color.WHITE);
		setContentPane(panelContenidos);
		panelContenidos.setLayout(new BorderLayout(15, 15));

//Panel Central(Va a ir todo )
		JPanel panelCentral = new JPanel();
		panelCentral.setBackground(Color.WHITE);
		panelCentral.setLayout(new BorderLayout(0, 0));
		panelContenidos.add(panelCentral, BorderLayout.CENTER);

		// Panel Central Superior
		JPanel panelCentralSup = new JPanel();
		panelCentralSup.setBackground(Color.WHITE);
		panelCentralSup.setLayout(new GridLayout(6, 2, 5, 15));

		panelCentral.add(panelCentralSup, BorderLayout.NORTH);

		lbNombre = new JLabel("Nombre: ");
		tfNombre = new JTextField();
		ph = new TextPrompt("nombre", tfNombre);

		lbApellido = new JLabel("Apellido: ");
		tfApellido = new JTextField();
		ph = new TextPrompt("apellido", tfApellido);

		lbNick = new JLabel("Nick: ");
		tfNick = new JTextField();
		ph = new TextPrompt("nick", tfNick);

		lbEmail = new JLabel("Email: ");
		tfEmail = new JTextField();
		ph = new TextPrompt("email", tfEmail);

		lbContrasenia = new JLabel("Contrasenia: ");
		pfContrasenia = new JPasswordField();
		ph = new TextPrompt("contrasenia", pfContrasenia);

		lbConfirmarContrasenia = new JLabel("");
		pfConfirmarContrasenia = new JPasswordField();
		ph = new TextPrompt("confirmar contrasenia", pfConfirmarContrasenia);

		panelCentralSup.add(lbNombre);
		panelCentralSup.add(tfNombre);
		panelCentralSup.add(lbApellido);
		panelCentralSup.add(tfApellido);
		panelCentralSup.add(lbNick);
		panelCentralSup.add(tfNick);
		panelCentralSup.add(lbEmail);
		panelCentralSup.add(tfEmail);
		panelCentralSup.add(lbContrasenia);
		panelCentralSup.add(pfContrasenia);
		// panelCentralSup.add(lbConfirmarContrasenia);
		// panelCentralSup.add(pfConfirmarContrasenia);

		// Panel Inferior
		JPanel panelInferior = new JPanel();
		panelInferior.setBackground(Color.WHITE);
		panelInferior.setLayout(new BorderLayout(0, 0));
		panelCentral.add(panelInferior, BorderLayout.SOUTH);

		JPanel panelInferiorCentral = new JPanel();
		panelInferiorCentral.setBackground(Color.WHITE);
		panelInferiorCentral.setLayout(new GridLayout(1, 2, 5, 0));
		panelInferior.add(panelInferiorCentral, BorderLayout.CENTER);

		btnAtras = new JButton("Volver");
		btnAtras.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				VentanaInicio vi = new VentanaInicio();
				vi.setVisible(true);
				dispose();

			}
		});

		panelInferiorCentral.add(btnAtras);

		btnRegistrarse = new JButton("Registrarse");
		btnRegistrarse.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Object boton = e.getSource();

				if (boton == btnRegistrarse) {
					if (tfNombre.getText().equals("") || tfApellido.getText().equals("")
							|| tfNick.getText().equals("")) {
						JOptionPane.showMessageDialog(null, "No debes dejar campos vacios");
					}

					if (!comprobarCorreo(tfEmail.getText(), false)) {
						comprobarCorreo(tfEmail.getText(), true);

					} else {

						// Conexion con la base de datos
						GestorBD modSql = new GestorBD();
						Usuario mod = new Usuario();
						mod.setNick(tfNick.getText());
						mod.setNombre(tfNombre.getText());
						mod.setApellido(tfApellido.getText());
						mod.setEmail(tfEmail.getText());

						String nuevaContrasenia = Hash.sha1(String.valueOf(pfContrasenia.getPassword()));
						mod.setContrasenia(nuevaContrasenia);

						if (modSql.registrar(mod)) {
							JOptionPane.showMessageDialog(null, "Registro realizado con exito");
							// Mandar email de bienvenida
							/*Thread hilo = new Thread(new Runnable() {

								@Override
								public void run() {
									String mensaje = "Estimado " + tfNombre.getText() + " " + tfApellido.getText()
											+ ". " + "<i>Gracias por registrarte en AutoTrader como " + tfNick.getText()
											+ ". �Nos gustaria darte la bienvenida a nuestra pagina.!</i><br>";
									mensaje += "<font color=red>Para cualquier duda, por favor contactar con autotrader.correo@gmail.com</font>";
									 try {
										EmailBienvenida.mandarMail("smtp.gmail.com", "587", "autotrader.correo@gmail.com",
												"Santialfaro", tfEmail.getText(), "Correo de bienvenida a AutoTrader", mensaje);
										System.out.println("El email se ha enviado");
									} catch (Exception ex) {
										System.out.println("No se ha podido enviar el email " + ex.getMessage());
									}
								}
							});
							hilo.start();*/

						} else {
							JOptionPane.showMessageDialog(null, "No se ha podido registrar");
						}
						modSql.dissconect();
						VentanaInicio vi = new VentanaInicio();
						vi.setVisible(true);

					}

					dispose();
				}

			}

		});
		panelInferiorCentral.add(btnRegistrarse);

	}

	public static boolean comprobarCorreo(String correo, boolean showErrorWindow) {
		if (patCorreo.matcher(correo).matches()) {
			System.out.println(correo + " cumple el patron");
			return patCorreo.matcher(correo).matches();
		} else {
			if (showErrorWindow) { // boolean showErrorWindow para que no muestre la ventana en el test
				JOptionPane.showMessageDialog(null, correo + " no cumple el patron");
				System.out.println(correo + " no cumple el patron" + patCorreo);

			}
			return false;
		}
	}
}
