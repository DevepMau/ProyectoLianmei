package entidades;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import principal.PanelDeJuego;
import principal.Utilidades;

public class Entidad {

	public int xMundo, yMundo;
	public int velocidad;

	public BufferedImage standL1, standL2, standL3, standL4, standR1, standR2, standR3, standR4;
	public BufferedImage walkL1, walkL2, walkL3, walkL4, walkR1, walkR2, walkR3, walkR4;
	public String direccion;
	public String orientacion;

	public int contadorDeSprites = 0;
	public int numeroDeSprite = 2;
	
	public Rectangle areaSolida = new Rectangle(0, 0, 48, 48);
	public int areaSolidaDefaultX, areaSolidaDefaultY;
	public boolean colisionActivada = false;
	
	public int contadorDeAccion = 0;
	String dialogos[] = new String[70];
	int dialogoIndice = 0;
	
	PanelDeJuego pdj;
	
	public Entidad(PanelDeJuego pdj) {
		this.pdj = pdj;
	}
	
	public void establecerAccion() {}
	
	public void hablar() {
		if(dialogos[dialogoIndice] == null) {
			dialogoIndice = 0; 
		}

		pdj.ui.dialogoActual = dialogos[dialogoIndice];
		dialogoIndice++;

		switch(pdj.jugador.direccion) {
		case "izquierda":
			direccion = "derecha";
			break;
		case "derecha":
			direccion = "izquierda";
			break;
		}
	}
	
	public void actualizar() {

	    establecerAccion();
	    colisionActivada = false;
	    
	    pdj.comprobadorDeColisiones.verificarBaldosa(this);
	    pdj.comprobadorDeColisiones.verificarObjeto(this, false);
	    pdj.comprobadorDeColisiones.verificarJugador(this);

	    // SI LA COLISIÓN ES FALSA, EL JUGADOR PUEDE MOVERSE
	    if (colisionActivada == false) {

	        switch (direccion) {
	            case "arriba": yMundo -= velocidad; break;
	            case "abajo": yMundo += velocidad; break;
	            case "izquierda": xMundo -= velocidad; break;
	            case "derecha": xMundo += velocidad; break;
	        }
	    }

	    contadorDeSprites++;
	    if (contadorDeSprites > 10) {
	        if (numeroDeSprite == 1) {
	            numeroDeSprite = 2;
	        } else if (numeroDeSprite == 2) {
	            numeroDeSprite = 3;
	        } else if (numeroDeSprite == 3) {
	            numeroDeSprite = 4;
	        } else if (numeroDeSprite == 4) {
	            numeroDeSprite = 1;
	        }
	        contadorDeSprites = 0;
	    }

	}

	public void dibujar(Graphics2D g2) {

	    int pantallaX = xMundo - pdj.jugador.xMundo + pdj.jugador.xPantalla;
	    int pantallaY = yMundo - pdj.jugador.yMundo + pdj.jugador.yPantalla;

	    if (xMundo + pdj.tamañoDeBaldosa > pdj.jugador.xMundo - pdj.jugador.xPantalla &&
	        xMundo - pdj.tamañoDeBaldosa < pdj.jugador.xMundo + pdj.jugador.xPantalla &&
	        yMundo + pdj.tamañoDeBaldosa > pdj.jugador.yMundo - pdj.jugador.yPantalla &&
	        yMundo - (pdj.tamañoDeBaldosa + (pdj.tamañoDeBaldosa/2)) < pdj.jugador.yMundo + pdj.jugador.yPantalla) {

	        BufferedImage imagen = null;

	        switch (direccion) { 
	            case "izquierda":
	                if (numeroDeSprite == 1) {
	                    imagen = standL1;
	                }
	                if (numeroDeSprite == 2) {
	                    imagen = standL2;
	                }
	                if (numeroDeSprite == 3) {
	                    imagen = standL3;
	                }
	                if (numeroDeSprite == 4) {
	                    imagen = standL4;
	                }
	                break;
	            case "derecha":
	                if (numeroDeSprite == 1) {
	                    imagen = walkR1;
	                }
	                if (numeroDeSprite == 2) {
	                    imagen = walkR2;
	                }
	                if (numeroDeSprite == 3) {
	                    imagen = walkR3;
	                }
	                if (numeroDeSprite == 4) {
	                    imagen = walkR4;
	                }
	                break;
	        }
	        g2.drawImage(imagen, pantallaX, pantallaY, pdj.tamañoDeBaldosa, pdj.tamañoDeBaldosa + (pdj.tamañoDeBaldosa / 2), null);

	    }

	}

	public BufferedImage configurarImagen(String rutaImagen) {

	    Utilidades uTool = new Utilidades();
	    BufferedImage imagen = null;

	    try {
	        imagen = ImageIO.read(getClass().getResourceAsStream(rutaImagen + ".png"));
	        imagen = uTool.escalarImagen(imagen, pdj.tamañoDeBaldosa * 2, pdj.tamañoDeBaldosa * 2);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    return imagen;

	}


}
