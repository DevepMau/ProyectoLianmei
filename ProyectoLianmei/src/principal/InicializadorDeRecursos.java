package principal;

import entidades.NPC_Niña;
import objetos.OBJ_Llave;

public class InicializadorDeRecursos {

	PanelDeJuego pdj;

	public InicializadorDeRecursos(PanelDeJuego pdj) {
		this.pdj = pdj;
	}

	public void establecerObjetos() {

		pdj.obj[0] = new OBJ_Llave(pdj);
		pdj.obj[0].xMundo = 13 * pdj.tamañoDeBaldosa;
		pdj.obj[0].yMundo = 6 * pdj.tamañoDeBaldosa;
		pdj.obj[0].colision = true;

	}
	
	public void establecerNPCs() {

		pdj.npc[0] = new NPC_Niña(pdj);
		pdj.npc[0].xMundo = pdj.tamañoDeBaldosa*10;
		pdj.npc[0].yMundo = pdj.tamañoDeBaldosa*8;

	}
	
	public void establecerPersonajes() 	{
		
	}

}
