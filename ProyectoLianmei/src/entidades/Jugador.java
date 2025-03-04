package entidades;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import principal.PanelDeJuego;
import principal.Teclado;

public class Jugador extends Entidad {

	Teclado teclado;
	public final int xPantalla;
	public final int yPantalla;

	public Jugador(PanelDeJuego pdj, Teclado teclado) {

		super(pdj);
		this.teclado = teclado;
		
		areaSolida = new Rectangle(16, 24, 64, 64);
		areaSolidaDefaultX = areaSolida.x;
		areaSolidaDefaultY = areaSolida.y;
		
		setValoresPorDefecto();
		obtenerImagenDelJugador();
		
		xPantalla = pdj.anchoDePantalla / 2 - (pdj.tamañoDeBaldosa/2);
		yPantalla = pdj.altoDePantalla / 2 - ((pdj.tamañoDeBaldosa + (pdj.tamañoDeBaldosa/2)) /2);
		

	}

	public void setValoresPorDefecto() {

		xMundo = pdj.tamañoDeBaldosa * 5;
		yMundo = pdj.tamañoDeBaldosa * 5;
		velocidad = 3;
		direccion = "izquierda";
		orientacion = direccion;
	}

	public void obtenerImagenDelJugador() {
		
		standR1 = configurarImagen("/lianmei/lianmei-stand-R-1");
		standR2 = configurarImagen("/lianmei/lianmei-stand-R-2");
		standR3 = configurarImagen("/lianmei/lianmei-stand-R-3");
		standR4 = configurarImagen("/lianmei/lianmei-stand-R-4");
		standL1 = configurarImagen("/lianmei/lianmei-stand-L-1");
		standL2 = configurarImagen("/lianmei/lianmei-stand-L-2");
		standL3 = configurarImagen("/lianmei/lianmei-stand-L-3");
		standL4 = configurarImagen("/lianmei/lianmei-stand-L-4");
		walkR1 = configurarImagen("/lianmei/lianmei-walk-R-1");
		walkR2 = configurarImagen("/lianmei/lianmei-walk-R-2");
		walkR3 = configurarImagen("/lianmei/lianmei-walk-R-3");
		walkR4 = configurarImagen("/lianmei/lianmei-walk-R-4");
		walkL1 = configurarImagen("/lianmei/lianmei-walk-L-1");
		walkL2 = configurarImagen("/lianmei/lianmei-walk-L-2");
		walkL3 = configurarImagen("/lianmei/lianmei-walk-L-3");
		walkL4 = configurarImagen("/lianmei/lianmei-walk-L-4");


	}

	public void actualizar() {

		if(teclado.W == true || teclado.S == true || teclado.A == true || teclado.D == true) {
			
			if(teclado.W == true) {
				direccion = "arriba";
			}
			if(teclado.S == true) {
				direccion = "abajo";
			}
			if(teclado.A == true) {
				direccion = "izquierda";
				orientacion = direccion;
			}
			if(teclado.D == true) {
				direccion = "derecha";
				orientacion = direccion;
			}
			
			//COMPROBAR COLISIONES CON LAS BALDOSAS
			colisionActivada = false;
			pdj.comprobadorDeColisiones.verificarBaldosa(this);
			
			//COMPROBAR COLISIONES CON OBJETOS
			int objIndex = pdj.comprobadorDeColisiones.verificarObjeto(this, true);
			recogerObjeto(objIndex);
			
			//COMPROBAR COLISION CON NPC
			int npcIndex = pdj.comprobadorDeColisiones.verificarEntidad(this, pdj.npc);
			interactuarConNPC(npcIndex);
			
			//SI LA COLISION ESTA DESACTIVADA, EL JUGADOR SE PODRA MOVER
			if(colisionActivada == false) {
				
				switch(direccion) {
				case "arriba": yMundo -= velocidad; break;
				case "abajo": yMundo += velocidad; break;
				case "izquierda": xMundo -= velocidad; break;
				case "derecha": xMundo += velocidad; break;
				}
			}
			
			contadorDeSprites++;
			if(contadorDeSprites > 6) {
				if(numeroDeSprite == 1) {
					numeroDeSprite = 2;
				}
				else if(numeroDeSprite == 2) {
					numeroDeSprite = 3;
				}
				else if(numeroDeSprite == 3) {
					numeroDeSprite = 4;
				}
				else if(numeroDeSprite == 4) {
					numeroDeSprite = 1;
				}
				contadorDeSprites = 0;
			}
			
		}
		else {
			
			contadorDeSprites++;
			if(contadorDeSprites > 6) {
				if(numeroDeSprite == 1) {
					numeroDeSprite = 2;
				}
				else if(numeroDeSprite == 2) {
					numeroDeSprite = 3;
				}
				else if(numeroDeSprite == 3) {
					numeroDeSprite = 4;
				}
				else if(numeroDeSprite == 4) {
					numeroDeSprite = 1;
				}
				contadorDeSprites = 0;
			}
			
			//switch(direccion) {
			//case "izquierda":
			//	numeroDeSprite = 2;
			//	break;
			//case "derecha":
			//	numeroDeSprite = 2;
			//	break;
			//}
		}

	}
	
	public void recogerObjeto(int i) {

	    if (i != 999) {

	        String nombreObjeto = pdj.obj[i].nombre;

	        switch (nombreObjeto) {
	            case "Llave":
	            	if(pdj.obj[i].colision == true) {
	            		pdj.ReproducirSE(0);
	            		pdj.obj[i] = null;
	            	} 
	                break;
	        }
	    }
	}
	
	public void interactuarConNPC(int i) {

		if(i != 999) {
			if(pdj.teclado.ENTER == true) {
				pdj.estadoDeJuego = pdj.modoDialogo;
				pdj.npc[0].hablar();
			}
		}
	}
	
	public boolean noSeMueve() {
		return teclado.W == false && teclado.S == false && teclado.A == false && teclado.D == false;
	}


	public void dibujar(Graphics2D g2) {

		BufferedImage imagen = null;
		
		if(noSeMueve()) {
			switch(orientacion) {
			case "izquierda":
				if(numeroDeSprite == 1) {
					imagen = standL1;
				}
				if(numeroDeSprite == 2) {
					imagen = standL2;
				}
				if(numeroDeSprite == 3) {
					imagen = standL3;
				}
				if(numeroDeSprite == 4) {
					imagen = standL4;
				}
				break;
			case "derecha":
				if(numeroDeSprite == 1) {
					imagen = standR1;
				}
				if(numeroDeSprite == 2) {
					imagen = standR2;
				}
				if(numeroDeSprite == 3) {
					imagen = standR3;
				}
				if(numeroDeSprite == 4) {
					imagen = standR4;
				}
				break;
			}
		}
		else {
			switch(orientacion) {
			case "izquierda":
				if(numeroDeSprite == 1) {
					imagen = walkL1;
				}
				if(numeroDeSprite == 2) {
					imagen = walkL2;
				}
				if(numeroDeSprite == 3) {
					imagen = walkL3;
				}
				if(numeroDeSprite == 4) {
					imagen = walkL4;
				}
				break;
			case "derecha":
				if(numeroDeSprite == 1) {
					imagen = walkR1;
				}
				if(numeroDeSprite == 2) {
					imagen = walkR2;
				}
				if(numeroDeSprite == 3) {
					imagen = walkR3;
				}
				if(numeroDeSprite == 4) {
					imagen = walkR4;
				}
				break;
			}
		}
		g2.drawImage(imagen, xPantalla, yPantalla, null);

	}

}
