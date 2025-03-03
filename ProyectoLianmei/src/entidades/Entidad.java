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

	public BufferedImage abajo1, abajo2, abajo3, abajo4, arriba1, arriba2, arriba3, arriba4, izquierda1, izquierda2, derecha1, derecha2;
	public String direccion;

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
		case "arriba":
			direccion = "abajo";
			break;
		case "abajo":
			direccion = "arriba";
			break;
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
	            case "arriba":
	                if (numeroDeSprite == 1) {
	                    imagen = arriba1;
	                }
	                if (numeroDeSprite == 2) {
	                    imagen = arriba2;
	                }
	                if (numeroDeSprite == 3) {
	                    imagen = arriba3;
	                }
	                if (numeroDeSprite == 4) {
	                    imagen = arriba4;
	                }
	                break;
	            case "abajo":
	                if (numeroDeSprite == 1) {
	                    imagen = abajo1;
	                }
	                if (numeroDeSprite == 2) {
	                    imagen = abajo2;
	                }
	                if (numeroDeSprite == 3) {
	                    imagen = abajo3;
	                }
	                if (numeroDeSprite == 4) {
	                    imagen = abajo4;
	                }
	                break;
	            case "izquierda":
	                if (numeroDeSprite == 1) {
	                    imagen = izquierda1;
	                }
	                if (numeroDeSprite == 2) {
	                    imagen = izquierda2;
	                }
	                if (numeroDeSprite == 3) {
	                    imagen = izquierda1;
	                }
	                if (numeroDeSprite == 4) {
	                    imagen = izquierda2;
	                }
	                break;
	            case "derecha":
	                if (numeroDeSprite == 1) {
	                    imagen = derecha1;
	                }
	                if (numeroDeSprite == 2) {
	                    imagen = derecha2;
	                }
	                if (numeroDeSprite == 3) {
	                    imagen = derecha1;
	                }
	                if (numeroDeSprite == 4) {
	                    imagen = derecha2;
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
	        imagen = uTool.escalarImagen(imagen, pdj.tamañoDeBaldosa, pdj.tamañoDeBaldosa + (pdj.tamañoDeBaldosa / 2));
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    return imagen;

	}


}
