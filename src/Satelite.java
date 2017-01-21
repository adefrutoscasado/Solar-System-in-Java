import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.Sphere;


public class Satelite {

	Sphere esfera = new Sphere();
	float radiorotacionmaestro;
	float velocidadmaestro;
	float radiorotacion;
	float velocidadorbitasatelite;
	float radiosatelite;
	float velocidadrotacionsatelite;
	int t = 0;
	int t2 = 0;
	float q = 0;
	float w = 0;
	
	String imagentextura;
	Texture textura;
	TextureLoader Cargadordetextura;

	ByteBuffer buffer = ByteBuffer.allocateDirect(16).order(ByteOrder.nativeOrder());
	
	
	public Satelite(float radiorotaciondelplanetamaestro,
					float velocidaddelplanetamaestro,
					float radioorbitadelsatelite,
					float velocidadorbitadelsatelite,
					float radiodelsatelite,
					float velocidadrotaciondelsatelite,
					String imagentextura){
		
		radiorotacionmaestro = radiorotaciondelplanetamaestro;
		velocidadmaestro = 1/velocidaddelplanetamaestro;
		radiorotacion = radioorbitadelsatelite;
		velocidadorbitasatelite = 1/velocidadorbitadelsatelite;
		radiosatelite = radiodelsatelite;
		velocidadrotacionsatelite = velocidadrotaciondelsatelite;
		textura= null;
		Cargadordetextura = new TextureLoader();
		this.imagentextura = imagentextura;}
	
		
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
		esfera.setTextureFlag(true);
	}
	
	
	public void dibujarSatelite() {
		
		int resolucion = 15;
		
		t++;
		if (t == 360*velocidadmaestro) {t = 0;}
		
		q = radiorotacionmaestro*(float) Math.cos(t* 2 * Math.PI / (360 * velocidadmaestro));
		w = radiorotacionmaestro*(float) Math.sin(t* 2 * Math.PI / (360 * velocidadmaestro));
		
		t2++;
		
		if (t2 == 360*velocidadorbitasatelite) {t2 = 0;}
	
		float q2 = 0;
		float w2 = 0;
		float e2 = 0;
		
		q2 = q + radiorotacion*(float) Math.cos(t2 * 2 * Math.PI / (360 * velocidadorbitasatelite));
		w2 = w + radiorotacion*(float) Math.sin(t2 * 2 * Math.PI / (360 * velocidadorbitasatelite));
		e2 = radiorotacion*(float) Math.sin(t2 * 2 * Math.PI / (360 * velocidadorbitasatelite));
	

GL11.glPushMatrix();
GL11.glTranslated(q2, w2, e2);

GL11.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);

GL11.glRotatef(velocidadrotacionsatelite*t/velocidadorbitasatelite, 0.0f, 1.0f, 0.0f);
	
	    cargarTextura();
	   
        float teta;
        float phi;
        float FinalPhi=2*(float)Math.PI;
        float FinalTeta=(float)Math.PI;
        float porcentajex=0;
        float porcentajey=0;
        float incrementox=(float)(1/((float)2*resolucion));
             float incrementoy=(float)(-1/((float)resolucion));
        float increRad=(float)(Math.PI/resolucion);
             float Vertice1x,Vertice1y, Vertice1z=0;
             float Vertice2x,Vertice2y, Vertice2z=0;
             float Vertice3x,Vertice3y, Vertice3z=0;
             float Vertice4x,Vertice4y, Vertice4z=0;
             //-----------\/---------Construcción de la esfera poco a poco---------------
             if(FinalPhi<2*Math.PI)FinalPhi+=increRad/50;/*FinalPhi=2*PI;*/
             if(FinalTeta<Math.PI)FinalTeta+=increRad/100;/*FinalTeta=(float)PI;*/
             //-----------/\---------Construcción de la esfera poco a poco---------------
        for(teta=0;teta<FinalTeta;teta+=increRad){
                for(phi=0;phi<FinalPhi;phi+=increRad){
                	 
                                  //VERTICE 1
                    Vertice1z=(radiosatelite)*((float) Math.sin(teta))*((float)Math.cos(phi));
                    Vertice1x=(radiosatelite)*((float) Math.sin(teta))*((float)Math.sin(phi));
                    Vertice1y=(radiosatelite)*((float) Math.cos(teta));
                                  //VERTICE 2
                    Vertice2z=(radiosatelite)*((float) Math.sin(teta+increRad))*((float)Math.cos(phi));
                    Vertice2x=(radiosatelite)*((float) Math.sin(teta+increRad))*((float)Math.sin(phi));
                    Vertice2y=(radiosatelite)*((float) Math.cos(teta+increRad));
                                  //VERTICE 3
                   Vertice3z=(radiosatelite)*((float) Math.sin(teta+increRad))*((float)Math.cos(phi+increRad));
                   Vertice3x=(radiosatelite)*((float) Math.sin(teta+increRad))*((float)Math.sin(phi+increRad));
                   Vertice3y=(radiosatelite)*((float) Math.cos(teta+increRad));
                                  //VERTICE 4
                    Vertice4z=(radiosatelite)*((float) Math.sin(teta))*((float)Math.cos(phi+increRad));
                    Vertice4x=(radiosatelite)*((float) Math.sin(teta))*((float)Math.sin(phi+increRad));
                    Vertice4y=(radiosatelite)*((float) Math.cos(teta));
                   
GL11.glNormal3d((float)1.5*Math.sin(teta)*Math.sin(phi),(float)1.5*Math.cos(teta),(float)1.5*Math.sin(teta)*Math.cos(phi));

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

GL11.glEnd();

porcentajex+=incrementox;
}
 porcentajey+=incrementoy;
 porcentajex=0;
 
}  
        
   GL11.glPopMatrix();       
	    
	}	
	

}
