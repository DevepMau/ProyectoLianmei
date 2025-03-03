package entidades;

import java.awt.Rectangle;
import java.util.Random;

import principal.PanelDeJuego;

public class NPC_Niña extends Entidad {

	public NPC_Niña(PanelDeJuego pdj) {
		super(pdj);

		direccion = "abajo";
		velocidad = 1;

		areaSolida = new Rectangle(8, 32, 32, 40);
		areaSolidaDefaultX = areaSolida.x;
		areaSolidaDefaultY = areaSolida.y;

		obtenerImagen();
		obtenerDialogo();
	}

	public void obtenerImagen() {

		standR1 = configurarImagen("/lianmei/lianmei-stand-R_1");
		standR2 = configurarImagen("/lianmei/lianmei-stand-R_2");
		standR3 = configurarImagen("/lianmei/lianmei-stand-R_3");
		standR4 = configurarImagen("/lianmei/lianmei-stand-R_4");
		standL1 = configurarImagen("/lianmei/lianmei-stand-L_1");
		standL2 = configurarImagen("/lianmei/lianmei-stand-L_2");
		standL3 = configurarImagen("/lianmei/lianmei-stand-L_3");
		standL4 = configurarImagen("/lianmei/lianmei-stand-L_4");
		walkR1 = configurarImagen("/lianmei/lianmei-walk-R_1");
		walkR2 = configurarImagen("/lianmei/lianmei-walk-R_2");
		walkR3 = configurarImagen("/lianmei/lianmei-walk-R_3");
		walkR4 = configurarImagen("/lianmei/lianmei-walk-R_4");
		walkL1 = configurarImagen("/lianmei/lianmei-walk-L_1");
		walkL2 = configurarImagen("/lianmei/lianmei-walk-L_2");
		walkL3 = configurarImagen("/lianmei/lianmei-walk-L_3");
		walkL4 = configurarImagen("/lianmei/lianmei-walk-L_4");

	}
	
	public void obtenerDialogo() {
		dialogos[0] = "primer ejempo de dialogo";
		dialogos[1] = "segundo ejempo de dialogo mas largo \npara probar el el salto de linea";
		dialogos[2] = "tercer ejempo de dialogo";
		dialogos[3] = "cuarto ejempo de dialogo";
	}

	public void establecerAccion() {

		contadorDeAccion++;

		if (contadorDeAccion == 120) {

			Random aleatorio = new Random();
			int i = aleatorio.nextInt(100) + 1;

			if (i <= 25) {
				direccion = "arriba";
			}
			if (i > 25 && i <= 50) {
				direccion = "abajo";
			}
			if (i > 50 && i <= 75) {
				direccion = "izquierda";
			}
			if (i > 75 && i <= 100) {
				direccion = "derecha";
			}

			contadorDeAccion = 0;

		}

	}
	
	public void hablar() {

		super.hablar();
	}

}
