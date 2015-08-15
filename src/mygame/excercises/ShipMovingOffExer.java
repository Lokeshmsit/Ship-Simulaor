
package mygame.excercises;

import com.jme3.app.state.AbstractAppState;
import com.jme3.asset.AssetManager;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.math.ColorRGBA;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Node;
import com.jme3.audio.AudioNode;
import com.jme3.input.ChaseCamera;
import com.jme3.input.InputManager;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.post.FilterPostProcessor;
import com.jme3.scene.Spatial;
import com.jme3.system.Timer;
import com.jme3.water.WaterFilter;
import mygame.MainShipMotionControl;


public class ShipMovingOffExer extends AbstractAppState {
  
    BitmapText DieselText;
    BitmapText EngineText;
    BitmapText heatEngineText;
    BitmapText heatEngineTimeText;
    BitmapText IgnitionText;
    BitmapText MotorText;
    BitmapFont myFont;
 
 /*  BitmapText topLeft;
     BitmapText topCenter;
     BitmapText topRight;
     BitmapText topTimer;*/
     
     
     private AssetManager assetManager;
     //private HUDMessageManager hud_mngr;
     private Node rootNode,guiNode;
     private InputManager inputManager;
     private AudioNode motorsound;
     private Spatial ship;
     
     private MainShipMotionControl ship_motion;
       
   
     public ShipMovingOffExer(AssetManager mngr,InputManager io_manager,Node root_node,Node gui_node,Spatial sp)
     {  
     
        System.out.println("ship moving Excercise class..constructor!!");
       
        System.out.println("current State is "+MainShipMotionControl.state);
        
        this.assetManager=mngr; 
        this.inputManager=io_manager;
        this.guiNode=gui_node;
        this.rootNode=root_node;
        this.ship=sp;
        
        this.ship_motion=new MainShipMotionControl(assetManager,inputManager,this.ship,rootNode,guiNode);
        
        sp.addControl(ship_motion);
        
        this.rootNode.attachChild(this.guiNode);
        myFont = assetManager.loadFont("Interface/Fonts/Verdana.fnt");
        
        //=================== DisselText =========================================
        
        DieselText = new BitmapText(myFont, true); 
        DieselText.setSize(30);
        DieselText.setColor(ColorRGBA.Green);   
        DieselText.setQueueBucket(RenderQueue.Bucket.Gui);
        
       
        
        //==================== EngineText ========================================
        
        EngineText = new BitmapText(myFont, true); 
        EngineText.setSize(30);
        EngineText.setColor(ColorRGBA.Green);                                       
        EngineText.setQueueBucket(RenderQueue.Bucket.Gui);
        EngineText.setLocalTranslation(70,700,1);  
       
    
       //=================== heatEngineText =========================================
        
        heatEngineText = new BitmapText(myFont, true); 
        heatEngineText.setSize(30);
        heatEngineText.setColor(ColorRGBA.Green);                                       
        heatEngineText.setQueueBucket(RenderQueue.Bucket.Gui);
        heatEngineText.setLocalTranslation(50,500,1);  
      
        
        //================== heatEngineTimeText ========================================= 
        heatEngineTimeText= new BitmapText(myFont, true); 
        heatEngineTimeText.setSize(30);
        heatEngineTimeText.setColor(ColorRGBA.Red);                                       
        heatEngineTimeText.setQueueBucket(RenderQueue.Bucket.Gui);
        heatEngineTimeText.setLocalTranslation(20,100,1);  
        
        
        //=================IgnitionText========================================
        
        IgnitionText = new BitmapText(myFont, true); 
        IgnitionText.setSize(30);
        IgnitionText.setColor(ColorRGBA.Green);                                       
        IgnitionText.setQueueBucket(RenderQueue.Bucket.Gui);
        IgnitionText.setLocalTranslation(70,400,1);  
       // guiNode.attachChild(IgnitionText);
        
        //===============MotorText==============================================
        
        MotorText = new BitmapText(myFont, true); 
        MotorText.setSize(30);
        MotorText.setColor(ColorRGBA.Green);                                       
        MotorText.setQueueBucket(RenderQueue.Bucket.Gui);
        MotorText.setLocalTranslation(70,400,1);  
        //guiNode.attachChild(MotorText);
       
        System.out.println("ShipMovingOffExer exercise constructor end... ");
        
      }
   
     
      //=======================update method====================================
    
       public void update(float tpf)
       {
        
         switch(MainShipMotionControl.state){
         
         case ZERO_OFF:
         {
             
              DieselText.setText("Turn ON Dissel SWITCHES..!!");
              DieselText.setLocalTranslation(20,700,1);
              guiNode.attachChild(DieselText);
         
         }
                
         case DISSEL: //========================= DISSEL STATE ==================
         { 
          
           if(ship_motion.isDisselSwitches())
           {  
                guiNode.detachChild(DieselText);
                EngineText.setText("Insert keys..Turn ON Engine..!!");
                EngineText.setLocalTranslation(20,700,1);
                guiNode.attachChild(EngineText);
           }
           
         }
         break;
             
         case ENGINE:  //================== IGNITION STATE===================
         {
             guiNode.detachChild(EngineText);
             heatEngineText.setText("Turn keys clockwise to Engine combustion mode.. and hold for 5 seconds..!!");
             heatEngineText.setLocalTranslation(20,700,1);
             guiNode.attachChild(heatEngineText); 
             
             heatEngineTimeText.setText((int)ship_motion.getIgnitionHeatTime()+"");
             heatEngineTimeText.setLocalTranslation(20,100,1);
             guiNode.attachChild(heatEngineTimeText); 
            
          }
         break;
          
          case IGNITIION:  //================== IGNITION STATE===================
         {
            
              guiNode.detachChild(heatEngineText);  
              guiNode.detachChild(heatEngineTimeText);  
              
             
            //System.out.println("state : "+MainShipMotionControl.state );
            
          }
         break;
  
        default:
         {
         
         }
        break;
       }
     }

 
}
     
  
   
             
              


