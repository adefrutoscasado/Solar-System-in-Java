import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.*;

public class Esfera {

	Sphere esfera = new Sphere();
	float radioplaneta;
	float radiorotacion;
	float velocidadplaneta;
	float velocidadrotacion;
	float radioIn = 0;
	float radioOut = 0;
	int paralelos = 20;
	int meridianos = 45;
	int t = 0;
	int t2 = 0;
	float q = 0;
	float w = 0;
	boolean texturacargada = false;
	
	
	String imagentextura;
	Texture textura;
	TextureLoader Cargadordetextura;

	ByteBuffer buffer = ByteBuffer.allocateDirect(64).order(ByteOrder.nativeOrder());
	
	
	public Esfera(float radiorotacionplaneta, float velocidaddelplaneta, float radiodelplaneta, float velocidadderotacion, String imagentextura){
		radiorotacion = radiorotacionplaneta;
		velocidadplaneta = 1/velocidaddelplaneta;
		radioplaneta = radiodelplaneta;
		velocidadrotacion = velocidadderotacion;
		textura= null;
		Cargadordetextura = new TextureLoader();
		this.imagentextura = imagentextura;
		cargarTextura();
		}
	
	
	
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
	
	
	public void dibujarSol() {
		
		int resolucion = 15;
		
		float amarillo[] = {1f, 1f, 0f, 1.0f };
		GL11.glMaterial( GL11.GL_FRONT, GL11.GL_EMISSION, (FloatBuffer) buffer.asFloatBuffer().put(amarillo).flip());
		
		t++;
		if (t == 360) {t=0;};
		
		GL11.glPushMatrix();

		GL11.glTranslated(0, 0, 0);
		GL11.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);

		GL11.glRotatef(velocidadrotacion*t, 0.0f, 1.0f, 0.0f);
			
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
		                    Vertice1z=(radioplaneta)*((float) Math.sin(teta))*((float)Math.cos(phi));
		                    Vertice1x=(radioplaneta)*((float) Math.sin(teta))*((float)Math.sin(phi));
		                    Vertice1y=(radioplaneta)*((float) Math.cos(teta));
		                                  //VERTICE 2
		                    Vertice2z=(radioplaneta)*((float) Math.sin(teta+increRad))*((float)Math.cos(phi));
		                    Vertice2x=(radioplaneta)*((float) Math.sin(teta+increRad))*((float)Math.sin(phi));
		                    Vertice2y=(radioplaneta)*((float) Math.cos(teta+increRad));
		                                  //VERTICE 3
		                   Vertice3z=(radioplaneta)*((float) Math.sin(teta+increRad))*((float)Math.cos(phi+increRad));
		                   Vertice3x=(radioplaneta)*((float) Math.sin(teta+increRad))*((float)Math.sin(phi+increRad));
		                   Vertice3y=(radioplaneta)*((float) Math.cos(teta+increRad));
		                                  //VERTICE 4
		                    Vertice4z=(radioplaneta)*((float) Math.sin(teta))*((float)Math.cos(phi+increRad));
		                    Vertice4x=(radioplaneta)*((float) Math.sin(teta))*((float)Math.sin(phi+increRad));
		                    Vertice4y=(radioplaneta)*((float) Math.cos(teta));
		                   
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
	

	
	
public void dibujarFondo(float posx,float posy,float posz) {
		
		float efectomovimiento = 0.8f;
		int resolucion = 15;
			
		t++;
		GL11.glPushMatrix();

		GL11.glTranslated(efectomovimiento*posx, efectomovimiento*posy, efectomovimiento*posz);
		GL11.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);

		GL11.glRotatef(velocidadrotacion*t, 0.0f, 1.0f, 0.0f);
			
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
		                    Vertice1z=(radioplaneta)*((float) Math.sin(teta))*((float)Math.cos(phi));
		                    Vertice1x=(radioplaneta)*((float) Math.sin(teta))*((float)Math.sin(phi));
		                    Vertice1y=(radioplaneta)*((float) Math.cos(teta));
		                                  //VERTICE 2
		                    Vertice2z=(radioplaneta)*((float) Math.sin(teta+increRad))*((float)Math.cos(phi));
		                    Vertice2x=(radioplaneta)*((float) Math.sin(teta+increRad))*((float)Math.sin(phi));
		                    Vertice2y=(radioplaneta)*((float) Math.cos(teta+increRad));
		                                  //VERTICE 3
		                   Vertice3z=(radioplaneta)*((float) Math.sin(teta+increRad))*((float)Math.cos(phi+increRad));
		                   Vertice3x=(radioplaneta)*((float) Math.sin(teta+increRad))*((float)Math.sin(phi+increRad));
		                   Vertice3y=(radioplaneta)*((float) Math.cos(teta+increRad));
		                                  //VERTICE 4
		                    Vertice4z=(radioplaneta)*((float) Math.sin(teta))*((float)Math.cos(phi+increRad));
		                    Vertice4x=(radioplaneta)*((float) Math.sin(teta))*((float)Math.sin(phi+increRad));
		                    Vertice4y=(radioplaneta)*((float) Math.cos(teta));
		                   
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
	
	
	
	
	
	
	public void dibujarEsfera() {
		
		int resolucion = 20;
		t++;
		if (t == 360*velocidadplaneta) {t = 0;}
		
		q = radiorotacion*(float) Math.cos(t* 2 * Math.PI / (360 * velocidadplaneta));
		w = radiorotacion*(float) Math.sin(t* 2 * Math.PI / (360 * velocidadplaneta));
		
		
		
	    float colornulo[] = {0.0f,0.0f,0.0f,0.0f};
		GL11.glMaterial(GL11.GL_FRONT_AND_BACK, GL11.GL_AMBIENT,(FloatBuffer) buffer.asFloatBuffer().put(colornulo).flip());
		
		
		GL11.glPushMatrix();

		GL11.glTranslatef(q,w,0);
		GL11.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);

		GL11.glRotatef(velocidadrotacion*t/velocidadplaneta, 0.0f, 1.0f, 0.0f);
			
		//if (texturacargada == false){
		    cargarTextura();
	//		    texturacargada = true;
	//	}
		
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
		                    Vertice1z=(radioplaneta)*((float) Math.sin(teta))*((float)Math.cos(phi));
		                    Vertice1x=(radioplaneta)*((float) Math.sin(teta))*((float)Math.sin(phi));
		                    Vertice1y=(radioplaneta)*((float) Math.cos(teta));
		                                  //VERTICE 2
		                    Vertice2z=(radioplaneta)*((float) Math.sin(teta+increRad))*((float)Math.cos(phi));
		                    Vertice2x=(radioplaneta)*((float) Math.sin(teta+increRad))*((float)Math.sin(phi));
		                    Vertice2y=(radioplaneta)*((float) Math.cos(teta+increRad));
		                                  //VERTICE 3
		                   Vertice3z=(radioplaneta)*((float) Math.sin(teta+increRad))*((float)Math.cos(phi+increRad));
		                   Vertice3x=(radioplaneta)*((float) Math.sin(teta+increRad))*((float)Math.sin(phi+increRad));
		                   Vertice3y=(radioplaneta)*((float) Math.cos(teta+increRad));
		                                  //VERTICE 4
		                    Vertice4z=(radioplaneta)*((float) Math.sin(teta))*((float)Math.cos(phi+increRad));
		                    Vertice4x=(radioplaneta)*((float) Math.sin(teta))*((float)Math.sin(phi+increRad));
		                    Vertice4y=(radioplaneta)*((float) Math.cos(teta));
		                   
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
