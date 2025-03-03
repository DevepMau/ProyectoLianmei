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

		abajo1 = configurarImagen("/npc/gg_down_1");
		abajo2 = configurarImagen("/npc/gg_down_2");
		abajo3 = configurarImagen("/npc/gg_down_3");
		abajo4 = configurarImagen("/npc/gg_down_4");
		izquierda1 = configurarImagen("/npc/gg_left_1");
		izquierda2 = configurarImagen("/npc/gg_left_2");
		derecha1 = configurarImagen("/npc/gg_right_1");
		derecha2 = configurarImagen("/npc/gg_right_2");
		arriba1 = configurarImagen("/npc/gg_up_1");
		arriba2 = configurarImagen("/npc/gg_up_2");
		arriba3 = configurarImagen("/npc/gg_up_3");
		arriba4 = configurarImagen("/npc/gg_up_4");

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
