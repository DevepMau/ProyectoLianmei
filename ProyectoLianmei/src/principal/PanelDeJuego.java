package principal;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import baldosa.GestorDeBaldosas;
import entidades.Entidad;
import entidades.Jugador;
import objetos.ObjetoBase;

public class PanelDeJuego extends JPanel implements Runnable {

	private static final long serialVersionUID = 1L;
	
	// CONFIGURACIÓN DE PANTALLA
	final int tamañoOriginalDeBaldosa = 16;
	final int escala = 3;

	public final int tamañoDeBaldosa = tamañoOriginalDeBaldosa * escala;
	public final int maxColDePantalla = 16;
	public final int maxFilaDePantalla = 12;
	public final int anchoDePantalla = tamañoDeBaldosa * maxColDePantalla;
	public final int altoDePantalla = tamañoDeBaldosa * maxFilaDePantalla;
	
	//CONFIGURACION DEL MUNDO
	public final int maxColDeMundo = 16;
	public final int maxFilaDeMundo = 12;
	public final int anchoMundo = tamañoDeBaldosa * maxColDeMundo;
	public final int altoMundo = tamañoDeBaldosa * maxFilaDeMundo;

	//SISTEMA
	GestorDeBaldosas gestorDeBaldosas = new GestorDeBaldosas(this);
	public Teclado teclado = new Teclado(this);
	Sonido musica = new Sonido();
	Sonido se = new Sonido();
	public DetectorDeColisiones comprobadorDeColisiones = new DetectorDeColisiones(this);
	public InicializadorDeRecursos inicializadorDeRecursos = new InicializadorDeRecursos(this);
	public UI ui = new UI(this);
	Thread hiloDeJuego;
	
	//ENTIDADES Y OBJETOS
	public Jugador jugador = new Jugador(this, teclado);
	public ObjetoBase obj[] = new ObjetoBase[10];
	public Entidad npc[] = new Entidad[10];

	//ESTADO DE JUEGO
	public int estadoDeJuego;
	
	public final int modoTitulo = 0;
	public final int modoJuego = 1;
	public final int modoPausa = 2;
	public final int modoDialogo = 3;
	public final int modoCombate = 4;
	
	// FPS
	int FPS = 60;

	public PanelDeJuego() {

		this.setPreferredSize(new Dimension(anchoDePantalla, altoDePantalla));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(teclado);
		this.setFocusable(true);

	}
	
	public void configuracionDeJuego() {
		
		inicializadorDeRecursos.establecerObjetos();
		inicializadorDeRecursos.establecerNPCs();

		estadoDeJuego = modoJuego;
	}

	public void iniciarHiloDeJuego() {
		hiloDeJuego = new Thread(this);
		hiloDeJuego.start();

	}
	
	public void reproducirMusica(int i) {

		musica.cargarArchivo(i);
		musica.reproducir();
		musica.repetir();

	}

	public void detenerMusica() {

		musica.detener();

	}

	public void ReproducirSE(int i) {

		se.cargarArchivo(i);
		se.reproducir();

	}

	@Override
	public void run() {

		double intervaloDeDibujo = 1000000000 / FPS;
		double delta = 0;
		long ultimoTiempo = System.nanoTime();
		long tiempoActual;

		while(hiloDeJuego != null) {

			tiempoActual = System.nanoTime();
			delta += (tiempoActual - ultimoTiempo) / intervaloDeDibujo;
			ultimoTiempo = tiempoActual;

			if(delta >= 1) {
				actualizar();
				repaint();
				delta--;
			}
		}

	}

	public void actualizar() {

		if(estadoDeJuego == modoJuego) {
			
			//JUGADR
			jugador.actualizar();
			
			//NPC
			for(int i = 0; i < npc.length; i++) {
				if(npc[i] != null) {
					npc[i].actualizar();
				}
			}
		}
		if(estadoDeJuego == modoPausa) {
			
		}
		if(estadoDeJuego == modoCombate) {
			
		}
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g;
		
		//DEBUG
		long drawStart = 0;
		if(teclado.comprobarTiempoDeDibujado == true) {
			drawStart = System.nanoTime();
		}
		//COMBATE
		if(estadoDeJuego == modoCombate) {

		}
		//PANTALLA DE TITULO
		if(estadoDeJuego == modoTitulo) {
			ui.dibujar(g2);	
		}
		//OTROS
		else {
			//BALDOSAS
			gestorDeBaldosas.dibujar(g2);
			
			//OBJETOS
			for(int i = 0; i < obj.length; i++) {
				if(obj[i] != null) {
					obj[i].dibujar(g2, this);
				}
			}
			
			jugador.dibujar(g2);

			//JUGADOR Y NPC
			for(int i = 0; i < npc.length; i++) {
				if(npc[i] != null) {
					if(npc[i].yMundo < jugador.yMundo) {
						npc[i].dibujar(g2);
						jugador.dibujar(g2);
					}
					else {
						jugador.dibujar(g2);
						npc[i].dibujar(g2);
					}	
				}
			}
			
			//UI
			ui.dibujar(g2);
			
		}

		//DEBUG
		if(teclado.comprobarTiempoDeDibujado == true) {
			long drawEnd = System.nanoTime();
			long passed = drawEnd - drawStart;
			g2.setColor(Color.white);
			g2.drawString("Draw Time: " + passed, 10, 400);
		}

		g2.dispose();

	}

}