
package mygame;
import com.jme3.animation.LoopMode;
import com.jme3.app.SimpleApplication; 
import com.jme3.cinematic.MotionPath;
import com.jme3.cinematic.MotionPathListener; 
import com.jme3.cinematic.events.MotionEvent; 
import com.jme3.cinematic.events.MotionEvent;
import com.jme3.font.BitmapText; 
import com.jme3.input.ChaseCamera; 
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger; 
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material; 
import com.jme3.math.ColorRGBA; 
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion; 
import com.jme3.math.Spline.SplineType; 
import com.jme3.math.Vector3f; 
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial; 
import com.jme3.app.SimpleApplication;



 
public class TestMotionPath extends SimpleApplication {
   private Spatial ship;
   private boolean active = true;
   private boolean playing = false; 
   private MotionPath path;
   private MotionEvent motionControl; 
 
   public static void main(String arg[])
    { 
        System.out.println("TestMotionPath class");
        TestMotionPath app = new TestMotionPath(); app.start(); 
          app.start();
    } 

  
    public void simpleInitApp() {
        System.out.println("SimpleInit app");
        createScene();
        
        cam.setLocation(new Vector3f(8.4399185f, 11.189463f, 14.267577f));
        path = new MotionPath();
        path.addWayPoint(new Vector3f(8, 0, -10));
        path.addWayPoint(new Vector3f(8, 0, -85));
         path.addWayPoint(new Vector3f(8, 0,-100));
     // path.setCycle(true);
        path.enableDebugShape(assetManager,rootNode);

  
        
        
        motionControl = new MotionEvent(ship,path);
        motionControl.setDirectionType(MotionEvent.Direction.PathAndRotation);
        motionControl.setRotation(new Quaternion().fromAngleNormalAxis(-FastMath.HALF_PI, Vector3f.UNIT_Y));
        motionControl.setInitialDuration(10f);
        motionControl.setSpeed(1.5f);   
        motionControl.play();
        motionControl.setLoopMode(LoopMode.Loop);
        
     /*  path.addListener( new MotionPathListener() {
         public void onWayPointReach(MotionEvent motionControl, int wayPointIndex) {
       if(path.getNbWayPoints()==2)
       {  path.getWayPoint(1).add(new Vector3f(8, 0, -110));
       // path.getWayPoint(1).addWayPoint(new Vector3f(8, 0,-100));
          path.addWayPoint(new Vector3f(8, 0,-100));
      //  path.addWayPoint(new Vector3f(7, 0, -110));
         path.addListener(this);
    //     path.getWayPoint(1).
       }
      

        flyCam.setEnabled(false);
  //     ChaseCamera chaser = new ChaseCamera(cam, ship);
         });}
         * 
         */ }
       private void createScene() {
        ship=assetManager.loadModel("Models/NewShipModels/OBJ/fastboatobj.j3o");
        ship.scale(3.0f, 3.0f, 3.0f);
        ship.rotate(0.0f,FastMath.PI,0.0f);
        ship.setLocalTranslation(0.0f,0.0f,0.0f);
        rootNode.attachChild(ship);   
        DirectionalLight light = new DirectionalLight();
        light.setDirection(new Vector3f(0, -1, 0).normalizeLocal());
        light.setColor(ColorRGBA.White.mult(1.5f));
        rootNode.addLight(light);
       /* if(path.getWayPoint()==)
        {
            motionControl.play();
        }
 */
    }
         
  
}
