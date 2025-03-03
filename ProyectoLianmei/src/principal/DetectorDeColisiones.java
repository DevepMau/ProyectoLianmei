package principal;

import entidades.Entidad;

public class DetectorDeColisiones {

	PanelDeJuego pdj;

	public DetectorDeColisiones(PanelDeJuego pdj) {
		this.pdj = pdj;
	}

	public void verificarBaldosa(Entidad entidad) {

		int entidadIzquierdaX = entidad.xMundo + entidad.areaSolida.x;
		int entidadDerechaX = entidad.xMundo + entidad.areaSolida.x + entidad.areaSolida.width;
		int entidadArribaY = entidad.yMundo + entidad.areaSolida.y;
		int entidadAbajoY = entidad.yMundo + entidad.areaSolida.y + entidad.areaSolida.height;

		int columnaIzquierda = entidadIzquierdaX / pdj.tamañoDeBaldosa;
		int columnaDerecha = entidadDerechaX / pdj.tamañoDeBaldosa;
		int filaArriba = entidadArribaY / pdj.tamañoDeBaldosa;
		int filaAbajo = entidadAbajoY / pdj.tamañoDeBaldosa;

		int numBaldosa1, numBaldosa2;

		switch(entidad.direccion) {
		//COLISION HACIA ARRIBA
		case "arriba":
			
			filaArriba = (entidadArribaY - entidad.velocidad) / pdj.tamañoDeBaldosa;
			numBaldosa1 = pdj.gestorDeBaldosas.mapaDeBaldosas[columnaIzquierda][filaArriba];
			numBaldosa2 = pdj.gestorDeBaldosas.mapaDeBaldosas[columnaDerecha][filaArriba];

			if(pdj.gestorDeBaldosas.baldosa[numBaldosa1].colision == true ||
			   pdj.gestorDeBaldosas.baldosa[numBaldosa2].colision == true) {
				entidad.colisionActivada = true;
			}
			break;
		//COLISION HACIA ABAJO
		case "abajo":
			
			filaAbajo = (entidadAbajoY + entidad.velocidad) / pdj.tamañoDeBaldosa;
			numBaldosa1 = pdj.gestorDeBaldosas.mapaDeBaldosas[columnaIzquierda][filaAbajo];
			numBaldosa2 = pdj.gestorDeBaldosas.mapaDeBaldosas[columnaDerecha][filaAbajo];
			
			if(pdj.gestorDeBaldosas.baldosa[numBaldosa1].colision == true ||
			   pdj.gestorDeBaldosas.baldosa[numBaldosa2].colision == true) {
				entidad.colisionActivada = true;
			}
			break;
		//COLISION HACIA LA IZQUIERDA
		case "izquierda":
			
			columnaIzquierda = (entidadIzquierdaX - entidad.velocidad) / pdj.tamañoDeBaldosa;
			numBaldosa1 = pdj.gestorDeBaldosas.mapaDeBaldosas[columnaIzquierda][filaArriba];
			numBaldosa2 = pdj.gestorDeBaldosas.mapaDeBaldosas[columnaIzquierda][filaAbajo];
			
			if(pdj.gestorDeBaldosas.baldosa[numBaldosa1].colision == true ||
			   pdj.gestorDeBaldosas.baldosa[numBaldosa2].colision == true) {
				entidad.colisionActivada = true;
			}
			break;
		//COLISION HACIA LA DERECHA
		case "derecha":
			
			columnaDerecha = (entidadDerechaX + entidad.velocidad) / pdj.tamañoDeBaldosa;
			numBaldosa1 = pdj.gestorDeBaldosas.mapaDeBaldosas[columnaDerecha][filaArriba];
			numBaldosa2 = pdj.gestorDeBaldosas.mapaDeBaldosas[columnaDerecha][filaAbajo];
			
			if(pdj.gestorDeBaldosas.baldosa[numBaldosa1].colision == true ||
			   pdj.gestorDeBaldosas.baldosa[numBaldosa2].colision == true) {
				entidad.colisionActivada = true;
			}
			break;

		}

	}
	
	public int verificarObjeto(Entidad entidad, boolean jugador) {

	    int indice = 999;

	    for (int i = 0; i < pdj.obj.length; i++) {

	        if (pdj.obj[i] != null) {

	            // Obtener la posición del área sólida de la entidad
	            entidad.areaSolida.x = entidad.xMundo + entidad.areaSolida.x;
	            entidad.areaSolida.y = entidad.yMundo + entidad.areaSolida.y;
	            // Obtener la posición del área sólida del objeto
	            pdj.obj[i].areaSolida.x = pdj.obj[i].xMundo + pdj.obj[i].areaSolida.x;
	            pdj.obj[i].areaSolida.y = pdj.obj[i].yMundo + pdj.obj[i].areaSolida.y;

	            switch (entidad.direccion) {
	                case "arriba":
	                    entidad.areaSolida.y -= entidad.velocidad;
	                    if (entidad.areaSolida.intersects(pdj.obj[i].areaSolida)) {
	                        if (pdj.obj[i].colision == true) {
	                            entidad.colisionActivada = true;
	                        }
	                        if (jugador == true) {
	                            indice = i;
	                        }
	                    }
	                    break;
	                case "abajo":
	                    entidad.areaSolida.y += entidad.velocidad;
	                    if (entidad.areaSolida.intersects(pdj.obj[i].areaSolida)) {
	                        if (pdj.obj[i].colision == true) {
	                            entidad.colisionActivada = true;
	                        }
	                        if (jugador == true) {
	                            indice = i;
	                        }
	                    }
	                    break;
	                case "izquierda":
	                    entidad.areaSolida.x -= entidad.velocidad;
	                    if (entidad.areaSolida.intersects(pdj.obj[i].areaSolida)) {
	                        if (pdj.obj[i].colision == true) {
	                            entidad.colisionActivada = true;
	                        }
	                        if (jugador == true) {
	                            indice = i;
	                        }
	                    }
	                    break;
	                case "derecha":
	                    entidad.areaSolida.x += entidad.velocidad;
	                    if (entidad.areaSolida.intersects(pdj.obj[i].areaSolida)) {
	                        if (pdj.obj[i].colision == true) {
	                            entidad.colisionActivada = true;
	                        }
	                        if (jugador == true) {
	                            indice = i;
	                        }
	                    }
	                    break;
	            }

	            // Restaurar las posiciones predeterminadas del área sólida de la entidad y del objeto
	            entidad.areaSolida.x = entidad.areaSolidaDefaultX;
	            entidad.areaSolida.y = entidad.areaSolidaDefaultY;
	            pdj.obj[i].areaSolida.x = pdj.obj[i].areaSolidaDefaultX;
	            pdj.obj[i].areaSolida.y = pdj.obj[i].areaSolidaDefaultY;

	        }

	    }

	    return indice;

	}
	
	public int verificarEntidad(Entidad entidad, Entidad[] objetivo) {

		int indice = 999;

		for (int i = 0; i < objetivo.length; i++) {

			if (objetivo[i] != null) {

				// Obtener la posicion del area solida de la entidad
				entidad.areaSolida.x = entidad.xMundo + entidad.areaSolida.x;
				entidad.areaSolida.y = entidad.yMundo + entidad.areaSolida.y;
				// Obtener la posicion del area solida del objeto
				objetivo[i].areaSolida.x = objetivo[i].xMundo + objetivo[i].areaSolida.x;
				objetivo[i].areaSolida.y = objetivo[i].yMundo + objetivo[i].areaSolida.y;

				switch (entidad.direccion) {
				case "arriba":
					entidad.areaSolida.y -= entidad.velocidad;
					if (entidad.areaSolida.intersects(objetivo[i].areaSolida)) {
						entidad.colisionActivada = true;
						indice = i;
					}
					break;
				case "abajo":
					entidad.areaSolida.y += entidad.velocidad;
					if (entidad.areaSolida.intersects(objetivo[i].areaSolida)) {
						entidad.colisionActivada = true;
						indice = i;
					}
					break;
				case "izquierda":
					entidad.areaSolida.x -= entidad.velocidad;
					if (entidad.areaSolida.intersects(objetivo[i].areaSolida)) {
						entidad.colisionActivada = true;
						indice = i;
					}
					break;
				case "derecha":
					entidad.areaSolida.x += entidad.velocidad;
					if (entidad.areaSolida.intersects(objetivo[i].areaSolida)) {
						entidad.colisionActivada = true;
						indice = i;
					}
					break;
				}

				entidad.areaSolida.x = entidad.areaSolidaDefaultX;
				entidad.areaSolida.y = entidad.areaSolidaDefaultY;
				objetivo[i].areaSolida.x = objetivo[i].areaSolidaDefaultX;
				objetivo[i].areaSolida.y = objetivo[i].areaSolidaDefaultY;

			}

		}

		return indice;
	}

	public void verificarJugador(Entidad entidad) {

		// Obtener la posicion del area solida de la entidad
		entidad.areaSolida.x = entidad.xMundo + entidad.areaSolida.x;
		entidad.areaSolida.y = entidad.yMundo + entidad.areaSolida.y;
		// Obtener la posicion del area solida del objeto
		pdj.jugador.areaSolida.x = pdj.jugador.xMundo + pdj.jugador.areaSolida.x;
		pdj.jugador.areaSolida.y = pdj.jugador.yMundo + pdj.jugador.areaSolida.y;

		switch (entidad.direccion) {
		case "arriba":
			entidad.areaSolida.y -= entidad.velocidad;
			if (entidad.areaSolida.intersects(pdj.jugador.areaSolida)) {
				entidad.colisionActivada = true;
			}
			break;
		case "abajo":
			entidad.areaSolida.y += entidad.velocidad;
			if (entidad.areaSolida.intersects(pdj.jugador.areaSolida)) {
				entidad.colisionActivada = true;
			}
			break;
		case "izquierda":
			entidad.areaSolida.x -= entidad.velocidad;
			if (entidad.areaSolida.intersects(pdj.jugador.areaSolida)) {
				entidad.colisionActivada = true;
			}
			break;
		case "derecha":
			entidad.areaSolida.x += entidad.velocidad;
			if (entidad.areaSolida.intersects(pdj.jugador.areaSolida)) {
				entidad.colisionActivada = true;
			}
			break;
		}

		entidad.areaSolida.x = entidad.areaSolidaDefaultX;
		entidad.areaSolida.y = entidad.areaSolidaDefaultY;
		pdj.jugador.areaSolida.x = pdj.jugador.areaSolidaDefaultX;
		pdj.jugador.areaSolida.y = pdj.jugador.areaSolidaDefaultY;

	}



}
