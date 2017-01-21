//Alejandro de Frutos Casado

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;


public class MiPlaneta {

	
	float px0 = 1000;
	float py0 = 0;
	float pz0 = 0;
	float dx0 = -180;
	float dy0 = 0;
	float dz0 = 0;
	
	float px = px0;
	float py = py0;
	float pz = pz0;
	float dx = dx0;
	float dy = dy0;
	float dz = dz0;
	
	ByteBuffer buffer = ByteBuffer.allocateDirect(16).order(ByteOrder.nativeOrder());
	
	/** time at last frame */
	long lastFrame;
	
	/** frames per second */
	int fps;
	
	/** last fps time */
	long lastFPS;

	float radiosol = (float)(10.5);
	
	//public ObjetoCeleste(float x1, float y1, float z1, float r, float d, float rot, float c1, float c2, float c3)
	
	ObjetoCeleste sol = new ObjetoCeleste     (0,0,0,        120,        (float)(0),1, 1f,1f,0f);
	ObjetoCeleste mercurio = new ObjetoCeleste(400,300,300, (float)(12.4) ,(float)(radiosol + 200.4),8,0.5f,0.6f,0.7f);
	ObjetoCeleste venus = new ObjetoCeleste   (400,300,300, (float)(12.9) ,(float)(radiosol + 270.7),7, 0f,0.5f,1f);
	ObjetoCeleste tierra = new ObjetoCeleste  (400,300,300, (float)(12)   ,(float)(radiosol + 301),6,1f,0f,0f);
	ObjetoCeleste marte = new ObjetoCeleste   (400,300,300, (float)(40.5) ,(float)(radiosol + 401.5),5, 1f,0f,1f);
	ObjetoCeleste jupiter = new ObjetoCeleste (400,300,300, (float)(31.2),(float)(radiosol + 505.2),4,0.5f,0f,0.7f);
	ObjetoCeleste saturno = new ObjetoCeleste (400,300,300, (float)(19.4 ),(float)(radiosol + 609.5),3, 0.2f,0.3f,0.4f);
	ObjetoCeleste urano = new ObjetoCeleste   (400,300,300, (float)(14)   ,(float)(radiosol + 919.2),2,0.5f,0.6f,0.7f);
	ObjetoCeleste neptuno = new ObjetoCeleste (400,300,300, (float)(13.8) ,(float)(radiosol + 230.1),1, 0.2f,0.3f,0.4f);
	
	public void camara() {
		
		float dxmouse = 0;
		float dymouse = 0;
		
		if (Keyboard.isKeyDown(Keyboard.KEY_UP)) pz += 10;
		if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) pz -= 10;
		
		if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) py += 10;
		if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) py -= 10;
		
		if (Keyboard.isKeyDown(Keyboard.KEY_W)) px -= 10;
		if (Keyboard.isKeyDown(Keyboard.KEY_S)) px += 10;
		
	
		if (Mouse.isButtonDown(0)){
			dxmouse = Mouse.getDX();
			dymouse = Mouse.getDY();
				dz += dymouse/5;
				dy += dxmouse/5;
		}
			
	
		if (Keyboard.isKeyDown(Keyboard.KEY_R)){px = px0; py = py0; pz = pz0; dx = dx0; dy = dy0; dz = dz0;}
		
		//GL11.glLoadIdentity();
		
		
		System.out.println("dx " + dx + "dy " + dy + "dz " + dz);
		System.out.println("px " + px + "py " + py + "pz " + pz);
		GLU.gluLookAt(px,py,pz,px+dx,py+dy,pz+dz,0,0,1);
	}

	
	
	
	public void start() {
		try {
			Display.setDisplayMode(new DisplayMode(800, 600));
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(0);
		}

		initGL(); // init OpenGL
		getDelta(); // call once before loop to initialise lastFrame
		lastFPS = getTime(); // call before loop to initialise fps timer

	
		while (!Display.isCloseRequested()) {
			int delta = getDelta();
			
			update(delta);
			renderGL();

			Display.update();
			Display.sync(100); // cap fps to 60fps
		}

		Display.destroy();
	}
	
	
	public void update(int delta) {

			
		updateFPS(); // update FPS Counter
		
	}
	
	/** 
	 * Calculate how many milliseconds have passed 
	 * since last frame.
	 * 
	 * @return milliseconds passed since last frame 
	 */
	public int getDelta() {
	    long time = getTime();
	    int delta = (int) (time - lastFrame);
	    lastFrame = time;
	 
	    return delta;
	}
	
	/**
	 * Get the accurate system time
	 * 
	 * @return The system time in milliseconds
	 */
	public long getTime() {
	    return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}
	
	/**
	 * Calculate the FPS and set it in the title bar
	 */
	public void updateFPS() {
		if (getTime() - lastFPS > 1000) {
			Display.setTitle("FPS: " + fps);
			fps = 0;
			lastFPS += 1000;
		}
		fps++;
	}
	
	
	
	
	public void initGL() {
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GLU.gluPerspective(50, 800f/600f, 1f, 10000);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		
		float light_ambient[]={1.0f, 0.0f, 1.0f, 1.0f};
		float light_diffuse[]={0.0f, 1.0f, 0.0f, 1.0f};
		float light_specular[]={1.0f, 0.0f, 0.0f, 1.0f};		
		float light_position[]={0.0f, 0.0f, 0.0f, 1.0f};
		
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_LIGHT0);

		GL11.glLight(GL11.GL_LIGHT0, GL11.GL_AMBIENT, (FloatBuffer) buffer.asFloatBuffer().put(light_ambient).flip());
		GL11.glLight(GL11.GL_LIGHT0, GL11.GL_DIFFUSE, (FloatBuffer) buffer.asFloatBuffer().put(light_diffuse).flip());
		GL11.glLight(GL11.GL_LIGHT0, GL11.GL_SPECULAR, (FloatBuffer) buffer.asFloatBuffer().put(light_specular).flip());
		GL11.glLight(GL11.GL_LIGHT0, GL11.GL_POSITION, (FloatBuffer) buffer.asFloatBuffer().put(light_position).flip());
		
		
	}

	
	
	
	
	public void renderGL() {
		
		// Clear The Screen And The Depth Buffer
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		camara();
		sol.dibujarObjetoCeleste();
		mercurio.dibujarObjetoCeleste();
		venus.dibujarObjetoCeleste();
		tierra.dibujarObjetoCeleste();
		marte.dibujarObjetoCeleste();
		jupiter.dibujarObjetoCeleste();
		saturno.dibujarObjetoCeleste();
		urano.dibujarObjetoCeleste();
		neptuno.dibujarObjetoCeleste();
		GL11.glLoadIdentity();
		
	}
	
	public static void main(String[] argv) {
		MiPlaneta miplaneta = new MiPlaneta();
		miplaneta.start();
	}
}