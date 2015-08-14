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

/**
 *
 * @author Berzee
 */
public class ThirdPersonCamera
{
    //The "pivot" Node allows for easy third-person mouselook! It's actually
    //just an empty Node that gets attached to the center of the Player.
    //
    //The CameraNode is set up to always position itself behind the *pivot*
    //instead of behind the Player. So when we want to mouselook around the
    //Player, we simply need to spin the pivot! The camera will orbit behind it
    //while the Player object remains still.
    //
    //NOTE: Currently only vertical mouselook (around the X axis) is working.
    //The other two axes could be added fairly easily, once you have an idea
    //for how they should actually behave (min and max angles, et cetera).
 
    private CameraNode cameraNode;
 
    //Change these as you desire. Lower verticalAngle values will put the camera
    //closer to the ground.
    public float followDistance = 2;
    public float verticalAngle = 0 * FastMath.DEG_TO_RAD;
 
    //These bounds keep the camera from spinning too far and clipping through
    //the floor or turning upside-down. You can change them as needed but it is
    //recommended to keep the values in the (-90,90) range.
    public float maxVerticalAngle = 85 * FastMath.DEG_TO_RAD;
    public float minVerticalAngle = 8 * FastMath.DEG_TO_RAD;
 
    public ThirdPersonCamera(String name, Camera cam, Node player)
    {
	//pivot = new Node("CamTrack");	
	//player.attachChild(pivot);
        
	cameraNode = new CameraNode(name, cam);
        cameraNode.setControlDir(CameraControl.ControlDirection.SpatialToCamera);
        
	//cameraNode.setLocalTranslation(new Vector3f(0, 0, followDistance));
	//cameraNode.lookAt(pivot.getLocalTranslation(), Vector3f.UNIT_Y);
        
        //pivot.getLocalRotation().fromAngleAxis(-FastMath.PI,Vector3f.UNIT_Y);
        
        //Vector3f driverseat=new Vector3f(0.0f,0.2f,0.5f);
        //Vector3f cameraposition=new Vector3f(0.0f,0.2f,0.2f);
      
        
        //Vector3f lookatVector=new Vector3f(pivotPos.getX(), pivotPos.getY()+0.2f, pivotPos.getZ()+1.5f);
        
        //Quaternion rotation=player.getWorldRotation();
        //Vector3f rawvector=new Vector3f(player.getWorldTranslation().getX(),player.getWorldTranslation().getY(),player.getWorldTranslation().getZ()-2.0f);
        //Vector3f drivereye=rotation.mult(rawvector);
        
           
        //cameraNode.getWorldTranslation().set(player.getWorldTranslation());
        //cameraNode.getWorldRotation().set(player.getWorldRotation());
        
        cameraNode.move(-0.20f,0.40f,0.0f);
        
        Vector3f lookatvector=new Vector3f(2.0f,0.40f,0.0f);

        cameraNode.lookAt(lookatvector, Vector3f.UNIT_Y);
        
        cam.setFrustumPerspective(60.0f,(float)1274/(float)996, 0.1f,1000.0f);
      
	//pivot.getLocalRotation().fromAngleAxis(-verticalAngle, Vector3f.UNIT_X);
        
        //System.out.println("ship Pos : "+player.getWorldTranslation().getX()+", "+player.getWorldTranslation().getY()+", "+player.getWorldTranslation().getZ());
        //System.out.println("pivot Pos : "+pivot.getLocalTranslation().getX()+", "+pivot.getLocalTranslation().getY()+", "+pivot.getLocalTranslation().getZ());
        //System.out.println("cameraNode Pos : "+cameraNode.getWorldTranslation().getX()+", "+cameraNode.getWorldTranslation().getY()+", "+cameraNode.getWorldTranslation().getZ());
        
        player.attachChild(cameraNode);
        
    }
 
    public void verticalRotate(float angle)
    {
	verticalAngle += angle;
 
	if(verticalAngle > maxVerticalAngle)
	{
	    verticalAngle = maxVerticalAngle;
	}
	else if(verticalAngle < minVerticalAngle)
	{
	    verticalAngle = minVerticalAngle;
	}
 
	//pivot.getLocalRotation().fromAngleAxis(-verticalAngle, Vector3f.UNIT_X);
    }
 
    public CameraNode getCameraNode()
    {
	return cameraNode;
    }
 
    
     
}