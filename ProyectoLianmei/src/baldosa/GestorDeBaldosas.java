package baldosa;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import principal.PanelDeJuego;
import principal.Utilidades;

public class GestorDeBaldosas {

	PanelDeJuego pdj;
	public Baldosa[] baldosa;
	public int mapaDeBaldosas[][];

	public GestorDeBaldosas(PanelDeJuego pdj) {

		this.pdj = pdj;
		baldosa = new Baldosa[10];
		mapaDeBaldosas = new int[pdj.maxColDeMundo][pdj.maxFilaDeMundo];
		obtenerImagenDeBaldosa();
		cargarMapa("/mapas/mapa00.txt");
	}

	public void obtenerImagenDeBaldosa() {
		
		configurar(0, "grass", false);
		configurar(1, "water", true);
		configurar(2, "brick", true);
		
	}
	
	public void configurar(int indice, String nombreDeImagen, boolean colision) {
		
		Utilidades uTool = new Utilidades();
		
		try {
			baldosa[indice] = new Baldosa();
			baldosa[indice].imagen = ImageIO.read(getClass().getResourceAsStream("/baldosas/tiles_"+ nombreDeImagen +".png"));
			baldosa[indice].imagen = uTool.escalarImagen(baldosa[indice].imagen, pdj.tamañoDeBaldosa, pdj.tamañoDeBaldosa); 
			baldosa[indice].colision = colision; 

		}catch(IOException e) {
			e.printStackTrace();
		}
	}

	public void cargarMapa(String rutaArchivo) {

		try {

			InputStream is = getClass().getResourceAsStream(rutaArchivo);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));

			int col = 0;
			int fil = 0;

			while(col < pdj.maxColDeMundo && fil < pdj.maxFilaDeMundo) {

				String linea = br.readLine();
				
				while(col < pdj.maxColDeMundo) {
					String numeros[] = linea.split(" ");

					int num = Integer.parseInt(numeros[col]);

					mapaDeBaldosas[col][fil] = num;
					col++;
				}
				if(col == pdj.maxColDeMundo) {
					col = 0;
					fil++;
				}
			}
			br.close();

		}catch(Exception e){
			e.printStackTrace();
		}

	}

	public void dibujar(Graphics2D g2) {

		int colDeMundo = 0;
		int filaDeMundo = 0;

		while(colDeMundo < pdj.maxColDeMundo && filaDeMundo < pdj.maxFilaDeMundo) {

			int numeroDeBaldosa = mapaDeBaldosas[colDeMundo][filaDeMundo];

			int xMundo = colDeMundo * pdj.tamañoDeBaldosa;
			int yMundo = filaDeMundo * pdj.tamañoDeBaldosa;
			int xPantalla = xMundo - pdj.jugador.xMundo + pdj.jugador.xPantalla;
			int yPantalla = yMundo - pdj.jugador.yMundo + pdj.jugador.yPantalla;

			if(xMundo + pdj.tamañoDeBaldosa > pdj.jugador.xMundo - pdj.jugador.xPantalla &&
			   xMundo - pdj.tamañoDeBaldosa < pdj.jugador.xMundo + pdj.jugador.xPantalla &&
			   yMundo + pdj.tamañoDeBaldosa > pdj.jugador.yMundo - pdj.jugador.yPantalla &&
			   yMundo - (pdj.tamañoDeBaldosa + (pdj.tamañoDeBaldosa/2)) < pdj.jugador.yMundo + pdj.jugador.yPantalla) {

				g2.drawImage(baldosa[numeroDeBaldosa].imagen, xPantalla, yPantalla, pdj.tamañoDeBaldosa, pdj.tamañoDeBaldosa, null);

			}

			colDeMundo ++;

			if(colDeMundo == pdj.maxColDeMundo) {
				colDeMundo = 0;
				filaDeMundo++;
			}

		}

	}

}
