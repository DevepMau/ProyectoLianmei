package objetos;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import principal.PanelDeJuego;
import principal.Utilidades;

public class ObjetoBase {

	public BufferedImage imagen;
	public String nombre;
	public boolean colision = false;
	public int xMundo, yMundo;
	public Rectangle areaSolida = new Rectangle(0, 0, 48, 48);
	public int areaSolidaDefaultX = 0;
	public int areaSolidaDefaultY = 0;
	Utilidades uTool = new Utilidades();

	public void dibujar(Graphics2D g2, PanelDeJuego pdj) {

		int xPantalla = xMundo - pdj.jugador.xMundo + pdj.jugador.xPantalla;
		int yPantalla = yMundo - pdj.jugador.yMundo + pdj.jugador.yPantalla;

		if(xMundo + pdj.tamañoDeBaldosa > pdj.jugador.xMundo - pdj.jugador.xPantalla &&
		   xMundo - pdj.tamañoDeBaldosa < pdj.jugador.xMundo + pdj.jugador.xPantalla &&
		   yMundo + pdj.tamañoDeBaldosa > pdj.jugador.yMundo - pdj.jugador.yMundo &&
		   yMundo - (pdj.tamañoDeBaldosa + (pdj.tamañoDeBaldosa / 2)) < pdj.jugador.yMundo + pdj.jugador.yPantalla) {

			g2.drawImage(imagen, xPantalla, yPantalla, pdj.tamañoDeBaldosa, pdj.tamañoDeBaldosa, null);

		}

	}

}