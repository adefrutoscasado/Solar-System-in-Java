//Alejandro de Frutos Casado

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.*;
import org.lwjgl.util.glu.*;




public class ObjetoCeleste {

	float x = 0;
	float y = 0;
	float z = 0;
	float radio;
	float desplazamiento;
	float rotacion;
	float color1;
	float color2;
	float color3;
	float t = 0;
	
	
	
	long lastFrame;
	
	ByteBuffer buffer = ByteBuffer.allocateDirect(16).order(ByteOrder.nativeOrder());

	
	public ObjetoCeleste(float x1, float y1, float z1, float r, float d, float rot, float c1, float c2, float c3){
		x = x1;
		y = y1;
		z = z1;
		radio = r;
		desplazamiento = d;
		rotacion = rot;
		color1 = c1;
		color2 = c2;
		color3 = c3;
	}
	
	
	public void dibujarObjetoCeleste() {

		float q = 0;
		float w = 0;
	
		//Si es el sol, no gira
		if (x != 0) {t = t+1;}
		//System.out.println("t: " + t);
		if (t == 10000) {t = 0;}
		
	
		
		q = (desplazamiento)*(float) Math.cos(t * rotacion * 2 * Math.PI / 10000);
		w = (desplazamiento)*(float) Math.sin(t * rotacion * 2 * Math.PI / 10000);
	
	
		GL11.glPushMatrix();
		Sphere sphere = new Sphere();
		
		float material_Ka[] = {0.11f,0.06f,0.21f,1.00f};
		float material_Kd[] = {0.21f,0.16f,0.21f,1.00f};
		float material_Ks[] = {0.31f,0.46f,0.31f,1.00f};
		float material_Ke[] = {0.41f,0.86f,0.81f,1.00f};
		float material_SE = 10;
		
		float color[] = { color1, color2, color3, 1.0f };
		float white[] = { 1f, 1f, 1f, 1.0f };
		float red[] = { 1f, 0f, 0f, 1.0f };
		float nulo[] = { 0f, 0f, 0f, 1.0f };
		
		//GL11.glMaterial( GL11.GL_FRONT, GL11.GL_DIFFUSE, (FloatBuffer) buffer.asFloatBuffer().put(white).flip());
		//GL11.glMaterial( GL11.GL_FRONT, GL11.GL_SPECULAR, (FloatBuffer) buffer.asFloatBuffer().put(color).flip());
		if (x == 0) {
			GL11.glMaterial( GL11.GL_FRONT, GL11.GL_EMISSION, (FloatBuffer) buffer.asFloatBuffer().put(color).flip());}
		else{
			GL11.glMaterial( GL11.GL_FRONT, GL11.GL_EMISSION, (FloatBuffer) buffer.asFloatBuffer().put(nulo).flip());
			GL11.glMaterial( GL11.GL_FRONT, GL11.GL_AMBIENT, (FloatBuffer) buffer.asFloatBuffer().put(color).flip());}
		
		//GL11.glMaterial( GL11.GL_FRONT, GL11.GL_SHININESS, (FloatBuffer) buffer.asFloatBuffer().put(red).flip());
		
		
		//GL11.glColor3f(color1,color2,color3);
		//GL11.glRotatef(1, 0f, 0f, 1f);
		GL11.glTranslatef(q,w,0);
		//radio,numerodelados,
		sphere.draw(radio,100,10);
		GL11.glPopMatrix();
}
}