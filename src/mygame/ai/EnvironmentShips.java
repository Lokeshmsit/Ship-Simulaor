package mygame.ai;

import com.jme3.asset.AssetManager;
import com.jme3.cinematic.MotionPath;
import com.jme3.cinematic.MotionPathListener;
import com.jme3.cinematic.events.MotionEvent;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import mygame.ShipSimulatorWindow;
import mygame.ai.AutonomousAgent;

public class EnvironmentShips {
    
    private Spatial ship1,ship2,ship3,ship4,ship5,ship6;
    private AssetManager assetManager;
    private Node rootNode;
    private Spatial main_ship;
    private ShipSimulatorWindow sim;
    
    public EnvironmentShips(AssetManager mngr,Node root_node,Spatial ship)
    {  
        this.assetManager=mngr;
        this.rootNode=root_node;
        main_ship=ship;
       
    }
      
    public void createScene() {
        
        DirectionalLight light = new DirectionalLight();
        light.setDirection(new Vector3f(0, -1, 0).normalizeLocal());
        light.setColor(ColorRGBA.White.mult(1.5f));
        rootNode.addLight(light);
       
        //==============================path1===================================
         ship1=assetManager.loadModel("Models/ShipModels/10/JME/OBJ/fastboatobj.j3o");
         ship1.scale(3.0f, 3.0f, 3.0f);
         ship1.rotate(0.0f,FastMath.PI,0.0f);
         rootNode.attachChild(ship1);  
         
         ship1.addControl(new AutonomousAgent(ship1,main_ship));
         
    }}