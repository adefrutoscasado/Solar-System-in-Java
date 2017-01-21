import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
 
public class QuadExample {
	float px0 = 1000;
	float py0 = 0;
	float pz0 = 300;
	float t= 0;
	String imagentextura = "texturasol.jpg";
	Texture textura;
	TextureLoader Cargadordetextura;
	

	ByteBuffer buffer = ByteBuffer.allocateDirect(16).order(ByteOrder.nativeOrder());
	
	public void cargarTextura() {
		 try { 
			 textura = Cargadordetextura.getTexture(imagentextura); 
		 } 
		catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
			}
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		textura.bind();
	}
	
    public void start() {
        try {
	    Display.setDisplayMode(new DisplayMode(800,600));
	    Display.create();
	} catch (LWJGLException e) {
	    e.printStackTrace();
	    System.exit(0);
	}
 
	// init OpenGL
        GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GLU.gluPerspective(50, 800f/600f, 1f, 1000);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
		GLU.gluLookAt(px0,py0,pz0,
						0,0,0,
						0,0,1);
		
	while (!Display.isCloseRequested()) {
		
		textura= null;
		Cargadordetextura = new TextureLoader();
		this.imagentextura = imagentextura;
		
		
		 cargarTextura();
	    // Clear the screen and depth buffer
	    GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);	
	    GL11.glPushMatrix();
	    t++;
	    GL11.glRotated(1*t, 0.0f, 0.0f, 1.0f);
	    // set the color of the quad (R,G,B,A)
	   
	    
	    
	    
	    
	    
	    
	    float px0 = 1000;
		float py0 = 0;
		float pz0 = 300;
		float dx0 = 0;
		float dy0 = 0;
		float dz0 = 0;
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	   
	    // draw quad
	    int i=0;
        int j=0;
        float radio=200f;
        float teta;
        float phi;
        float FinalPhi=2*(float)Math.PI;
        float FinalTeta=(float)Math.PI;
        float porcentajex=0;
        float porcentajey=0;
      
        float incrementox=(float)(1/((float)2*10)); //1/20
             float incrementoy=(float)(-1/((float)10)); //1/10
        float increRad=(float)(Math.PI/10);
             float Vertice1x,Vertice1y, Vertice1z=0;
             float Vertice2x,Vertice2y, Vertice2z=0;
             float Vertice3x,Vertice3y, Vertice3z=0;
             float Vertice4x,Vertice4y, Vertice4z=0;
             //-----------\/---------Construcción de la esfera poco a poco---------------
             if(FinalPhi<2*Math.PI)FinalPhi+=increRad/50;/*FinalPhi=2*PI;*/
             if(FinalTeta<Math.PI)FinalTeta+=increRad/100;/*FinalTeta=(float)PI;*/
             //-----------/\---------Construcción de la esfera poco a poco---------------
        for(teta=0;teta<FinalTeta;teta+=increRad){
        	System.out.println("TETA");
                for(phi=0;phi<FinalPhi;phi+=increRad){
                	System.out.println("PHI");
                                  //VERTICE 1
                    Vertice1z=(radio)*((float) Math.sin(teta))*((float)Math.cos(phi));
                    Vertice1x=(radio)*((float) Math.sin(teta))*((float)Math.sin(phi));
                    Vertice1y=(radio)*((float) Math.cos(teta));
                                  //VERTICE 2
                    Vertice2z=(radio)*((float) Math.sin(teta+increRad))*((float)Math.cos(phi));
                    Vertice2x=(radio)*((float) Math.sin(teta+increRad))*((float)Math.sin(phi));
                    Vertice2y=(radio)*((float) Math.cos(teta+increRad));
                                  //VERTICE 3
                   Vertice3z=(radio)*((float) Math.sin(teta+increRad))*((float)Math.cos(phi+increRad));
                   Vertice3x=(radio)*((float) Math.sin(teta+increRad))*((float)Math.sin(phi+increRad));
                   Vertice3y=(radio)*((float) Math.cos(teta+increRad));
                                  //VERTICE 4
                    Vertice4z=(radio)*((float) Math.sin(teta))*((float)Math.cos(phi+increRad));
                    Vertice4x=(radio)*((float) Math.sin(teta))*((float)Math.sin(phi+increRad));
                    Vertice4y=(radio)*((float) Math.cos(teta));

GL11.glNormal3d((float)1.5*Math.sin(teta)*Math.sin(phi),(float)1.5*Math.cos(teta),(float)1.5*Math.sin(teta)*Math.cos(phi));
        //     GL11.glPushMatrix();                  


GL11.glBegin(GL11.GL_TRIANGLES);

          //TRIANGULO 1

GL11.glTexCoord2f(porcentajex, porcentajey);
GL11.glVertex3d(Vertice1x, Vertice1y,Vertice1z);
GL11.glTexCoord2f(porcentajex,porcentajey+incrementoy);
GL11.glVertex3d(Vertice2x, Vertice2y,Vertice2z);
GL11.glTexCoord2f(porcentajex+incrementox,porcentajey+incrementoy);
GL11.glVertex3d(Vertice3x,Vertice3y,Vertice3z);
          //TRIANGULO
GL11.glTexCoord2f(porcentajex, porcentajey);
GL11.glVertex3d(Vertice1x, Vertice1y,Vertice1z);
GL11.glTexCoord2f(porcentajex+incrementox,porcentajey+incrementoy);
GL11.glVertex3d(Vertice3x, Vertice3y,Vertice3z);
GL11.glTexCoord2f(porcentajex+incrementox,porcentajey);
GL11.glVertex3d(Vertice4x, Vertice4y,Vertice4z);

float amarillo[] = {1f, 1f, 0f, 1.0f };
GL11.glMaterial( GL11.GL_FRONT, GL11.GL_EMISSION, (FloatBuffer) buffer.asFloatBuffer().put(amarillo).flip());


GL11.glEnd();



porcentajex+=incrementox;
}
 porcentajey+=incrementoy;
 porcentajex=0;
 
}
 GL11.glPopMatrix();
	    Display.update();
	}
 
	Display.destroy();
    }
 
    public static void main(String[] argv) {
        QuadExample quadExample = new QuadExample();
        quadExample.start();
    }
}