import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import org.lwjgl.opengl.GL11;



public class Anillo {
	
	float radiorotacionmaestro;
	float velocidadmaestro;
	float radioIn;
	float radioOut;
	float t = 0;
	float q = 0;
	float w = 0;
	
	String imagentextura;
	Texture textura;
	TextureLoader Cargadordetextura;

	ByteBuffer buffer = ByteBuffer.allocateDirect(16).order(ByteOrder.nativeOrder());
	
	
	public Anillo(float radiorotaciondelplanetamaestro,
					float velocidaddelplanetamaestro,
					float radiointerior,
					float radioexterior,
					String imagentextura){
		
		radiorotacionmaestro = radiorotaciondelplanetamaestro;
		velocidadmaestro = 1/velocidaddelplanetamaestro;
		radioIn = radiointerior;
		radioOut = radioexterior;
		
		
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
	}
	
	
	
	public void dibujarAnillo() {

			GL11.glPushMatrix();
			cargarTextura();
			
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(1, 1);

			t++;
			if (t == 360*velocidadmaestro) {t = 0;}
			
			q = radiorotacionmaestro*(float) Math.cos(t* 2 * Math.PI / (360 * velocidadmaestro));
			w = radiorotacionmaestro*(float) Math.sin(t* 2 * Math.PI / (360 * velocidadmaestro));
			
			GL11.glTranslatef(q,w,0);
	
		    float coloranillo[] = {0.3f,0.3f,0.3f,0.0f};
			GL11.glMaterial(GL11.GL_FRONT_AND_BACK, GL11.GL_EMISSION,(FloatBuffer) buffer.asFloatBuffer().put(coloranillo).flip());
			
			
			   GL11.glBegin(GL11.GL_QUAD_STRIP);
			   float segmentos = 25;
			   float inner = radioIn;
			   float outer = radioOut;
			    for( int i = 0; i <= segmentos; ++i )
			    {
			        float angle = (i/(float)segmentos) * 3.14159f * 2.0f;
		
			        GL11.glTexCoord2f( (float)(0), (float)(1)) ;
			        GL11.glVertex2f( (float)(inner*Math.cos(angle)), (float)(inner*Math.sin(angle)) );
			        GL11.glTexCoord2f( (float)(1f), (float)(1f)) ;
			        GL11.glVertex2f( (float)(outer*Math.cos(angle)), (float)(outer*Math.sin(angle)) );
			        
			    }
			  
			    GL11.glEnd();
			    GL11.glDisable(GL11.GL_BLEND);
			    GL11.glPopMatrix();
	}
}
