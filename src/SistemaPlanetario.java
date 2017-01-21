import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import javax.sound.sampled.*;
import java.io.*;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

import javax.sound.sampled.*;
import java.io.*;

import java.io.File;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;


public class SistemaPlanetario {

	float px0 = 1280;
	float py0 = -10;
	float pz0 = 300;
	float dx0 = -500;
	float dy0 = 12;
	float dz0 = -95;
	
	float px = px0;
	float py = py0;
	float pz = pz0;
	float dx = dx0;
	float dy = dy0;
	float dz = dz0;
	
	ByteBuffer buffer = ByteBuffer.allocateDirect(16).order(ByteOrder.nativeOrder());
	
	public void start() {
		try {
			Display.setDisplayMode(new DisplayMode(1200,600));
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	public void initGL() {
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GLU.gluPerspective(50, 1200f/600f, 1f, 10000);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
		GLU.gluLookAt(px0,py0,pz0,
						0,0,0,
						0,0,1);
	//	GL11.glMatrixMode(GL11.GL_MODELVIEW);
		
	}
	
	
public void camara() {
		
	if (Keyboard.isKeyDown(Keyboard.KEY_A)) py -= 10;
	if (Keyboard.isKeyDown(Keyboard.KEY_D)) py += 10; 
	if (Keyboard.isKeyDown(Keyboard.KEY_S)) pz -= 10;
	if (Keyboard.isKeyDown(Keyboard.KEY_W)) pz += 10;
	if (Keyboard.isKeyDown(Keyboard.KEY_Z)) px += 10;
	if (Keyboard.isKeyDown(Keyboard.KEY_X)) px -= 10;
	if (Keyboard.isKeyDown(Keyboard.KEY_R)) {px = px0; py=py0; pz=pz0; dx = dx0; dy=dy0; dz=dz0;};
	
	
	float desx = Mouse.getDX();
	float desy = Mouse.getDY();
	if (Mouse.isButtonDown(0)){
		dz += desy/1;
		dy += desx/1;
	}
		

	GL11.glLoadIdentity();
	GLU.gluLookAt(px,py,pz,
			px+dx,py+dy,pz+dz,
			0,0,1);

//	System.out.println(px +" " + py +" " +pz +" " +dx +" " +dy +" " +dz);
	
	}
	
	
	public void dibujar(){

		start();
		initGL();
		
		
		
		
		
		
		try {
            
            // Se obtiene un Clip de sonido
            Clip sonido = AudioSystem.getClip();
            
            // Se carga con un fichero wav
            sonido.open(AudioSystem.getAudioInputStream(new File("bin\\wav3.wav")));
            
            // Comienza la reproducción
            sonido.start();
  
            sonido.loop(Clip.LOOP_CONTINUOUSLY);
            
        } catch (Exception e) {
            System.out.println("" + e);
        }
		
		
		
		
		
		
//radiorotacionplaneta, velocidaddelplaneta(de 0 a 100), radiodelplaneta, velocidadderotacion, imagentextura
		
		Esfera sol = new Esfera(0,0,100,1f, "texturasol.jpg");
		Esfera mercurio = new Esfera(120,1.0f,8,1, "texturamercurio.jpg");
		Esfera venus = new Esfera(170,1.2f,15,2, "texturavenus.jpg");
		Esfera tierra = new Esfera(300,0.4f,30,3, "texturatierradia.jpg");
		Esfera marte = new Esfera(400,0.3f,25,2, "texturamarte.jpg");
		Esfera jupiter = new Esfera(500,0.6f,50,1, "texturajupiter.jpg");
		Esfera saturno = new Esfera(650,1.1f,45,5, "texturasaturno.jpg");
		Esfera urano = new Esfera(800,0.8f,30,3, "texturaurano.jpg");
		Esfera neptuno = new Esfera(900,0.5f,8,1, "texturaneptuno.jpg");
		Esfera pluton = new Esfera(1060,0.4f,6,2, "texturapluton.jpg");
		
		Esfera fondo = new Esfera (0,0.000001f,2500,0,"texturafondo.jpg");
		
//radiorotaciondelplanetamaestro,velocidaddelplanetamaestro,radioorbitadelsatelite,velocidadorbitadelsatelite(de 0 a 100),radiodelsatelite,velocidadrotaciondelsatelite,String imagentextura)
	
		Satelite luna = new Satelite(300,0.4f,50,2.5f,10,3.5f, "texturaluna.jpg");
		
//radiorotaciondelplanetamaestro, velocidaddelplanetamaestro, radiointerior, radioexterior, imagentextura
		
		Anillo anillosaturno = new Anillo (650,1.1f,60,95,"texturaanillo.jpg");
	
		while (!Display.isCloseRequested()) {
			
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
			
			camara();
			
			float light_ambient[]={1.0f, 1.0f, 1.0f, 1.0f};
			float light_diffuse[]={1.0f, 1.0f, 0.3f, 1.0f};
			//float light_specular[]={1.0f, 0.0f, 0.0f, 1.0f};		
			float light_position[]={0.0f, 0.0f, 0.0f, 1.0f};
			
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glEnable(GL11.GL_LIGHT0);

			GL11.glLight(GL11.GL_LIGHT0, GL11.GL_AMBIENT, (FloatBuffer) buffer.asFloatBuffer().put(light_ambient).flip());
			GL11.glLight(GL11.GL_LIGHT0, GL11.GL_DIFFUSE, (FloatBuffer) buffer.asFloatBuffer().put(light_diffuse).flip());
			//GL11.glLight(GL11.GL_LIGHT0, GL11.GL_SPECULAR, (FloatBuffer) buffer.asFloatBuffer().put(light_specular).flip());
			GL11.glLight(GL11.GL_LIGHT0, GL11.GL_POSITION, (FloatBuffer) buffer.asFloatBuffer().put(light_position).flip());
			
			
			mercurio.dibujarEsfera();
			venus.dibujarEsfera();
			tierra.dibujarEsfera();
			marte.dibujarEsfera();
			jupiter.dibujarEsfera();
			saturno.dibujarEsfera();
			urano.dibujarEsfera();
			neptuno.dibujarEsfera();
			pluton.dibujarEsfera();
			
			fondo.dibujarFondo(px,py,pz);
			luna.dibujarSatelite();
			sol.dibujarSol();
			anillosaturno.dibujarAnillo();
			
			Display.update();
		}
	Display.destroy();
}
	
	public static void main(String[] argv) {
		SistemaPlanetario sistemaplanetario = new SistemaPlanetario();
		sistemaplanetario.dibujar();
	}
   }