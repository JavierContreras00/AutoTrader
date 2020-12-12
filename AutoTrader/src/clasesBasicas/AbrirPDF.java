package clasesBasicas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class AbrirPDF extends JFrame {

	public AbrirPDF() {
		this.setTitle("AutoTrader");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(100, 100, 500, 500);
		this.setMinimumSize(new Dimension(250, 250));

		JPanel panelContenidos = new JPanel();
		panelContenidos.setBackground(Color.WHITE);
		setContentPane(panelContenidos);
		panelContenidos.setLayout(new BorderLayout(15, 15));

		JButton ayuda = new JButton("Ayuda");
		// EVENTO DEL BOTON
				ayuda.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evento) {

						try {
							Process p = Runtime.getRuntime().exec("rundll32 SHELL32.DLL,ShellExec_RunDLL " + "src/prueba.pdf");
							System.out.println("He llegado");
						} catch (Exception evvv) {
							JOptionPane.showMessageDialog(null,
									"No se puede abrir el archivo de ayuda, probablemente fue borrado", "ERROR",
									JOptionPane.ERROR_MESSAGE);

						}
					}
				});

				panelContenidos.add(ayuda);
			}

			public static void main(String[] args) {
				AbrirPDF v = new AbrirPDF();
				v.setVisible(true);
}}