package ventanas;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JFrame;

import clasesBasicas.Marca;
import interfaces.LeeDeFichero;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VentanaProgramaFs extends JFrame implements LeeDeFichero {
	public VentanaProgramaFs() {
		getContentPane().setLayout(null);
		
		JButton btnNewButton = new JButton("Buscar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton.setBounds(31, 215, 97, 25);
		getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Filtros avanzados");
		btnNewButton_1.setBounds(152, 215, 131, 25);
		getContentPane().add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Vender");
		btnNewButton_2.setBounds(308, 215, 97, 25);
		getContentPane().add(btnNewButton_2);
		
		JComboBox cbMarca = new JComboBox();
		cbMarca.setBounds(82, 49, 31, 22);
		getContentPane().add(cbMarca);
		
		JComboBox cbModelo = new JComboBox();
		cbModelo.setBounds(82, 94, 31, 22);
		getContentPane().add(cbModelo);
		
		JComboBox cbAnio = new JComboBox();
		cbAnio.setBounds(82, 129, 31, 22);
		getContentPane().add(cbAnio);
		
		JComboBox cbPrecio = new JComboBox();
		cbPrecio.setBounds(82, 164, 31, 22);
		getContentPane().add(cbPrecio);
	}

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
	
	public static void main(String[] args) {
		VentanaProgramaFs v = new VentanaProgramaFs();
		v.setVisible(true);
	}
}
