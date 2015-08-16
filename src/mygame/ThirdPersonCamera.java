/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

 /**
 *
 * @author lokesh
 */
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.CameraNode;
import com.jme3.scene.Node;
import com.jme3.scene.control.CameraControl;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

public class ThirdPersonCamera
{

    private CameraNode cameraNode;
 

    public ThirdPersonCamera(String name, Camera cam, Node player)
    {
  
	cameraNode = new CameraNode(name, cam);
        
        cameraNode.setControlDir(CameraControl.ControlDirection.SpatialToCamera);
        
        cameraNode.move(-0.18f,0.40f,0.0f);
        
        Vector3f lookatvector=new Vector3f(2.0f,0.40f,0.0f);

        cameraNode.lookAt(lookatvector, Vector3f.UNIT_Y);
        
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int width = gd.getDisplayMode().getWidth();
        int height = gd.getDisplayMode().getHeight();
        
        cam.setFrustumPerspective(70.0f,(float)width/(float)height, 0.1f,1000.0f);
      
        player.attachChild(cameraNode);
        
    }
 
 
    public CameraNode getCameraNode()
    {
	return cameraNode;
    }
 
    
     
}