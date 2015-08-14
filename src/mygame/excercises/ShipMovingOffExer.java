
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
    BitmapText TimerText;
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
     
     private float speedFactor=0.2F;
     
     private boolean KEY_M_pressed=false;
     private boolean KEY_D_pressed=false;
     private boolean KEY_E_pressed=false;
     private boolean KEY_I_pressed=false;
     
     private MainShipMotionControl ship_motion;
             
     boolean attached=false;

    public float getSpeedFactor() {
        return speedFactor;
        
    }

    public void setSpeedFactor(float speedFactor) {
        this.speedFactor = speedFactor;
    }

    
    // private enum excerciseState {DIESEL,ENGINE,IGNITIION,MOTOR};
    // private excerciseState state;
     private Timer MyTimer;
    
     public ShipMovingOffExer(AssetManager mngr,InputManager io_manager,Node root_node,Node gui_node,Timer _Timer,Spatial sp)
     {  
     
        System.out.println("ship moving Excercise class!!");
       
        //state=excerciseState.DIESEL; 
        
        
        this.MyTimer=_Timer; 
        
        
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
        
        guiNode.attachChild(DieselText);
        
        
        //==================== EngineText ========================================
        
        EngineText = new BitmapText(myFont, true); 
        EngineText.setSize(30);
        EngineText.setColor(ColorRGBA.Green);                                       
        EngineText.setQueueBucket(RenderQueue.Bucket.Gui);
        EngineText.setLocalTranslation(70,700,1);  
        guiNode.attachChild(EngineText);
    
       //=================== TimerText =========================================
        
        TimerText = new BitmapText(myFont, true); 
        TimerText.setSize(30);
        TimerText.setColor(ColorRGBA.Red);                                       
        TimerText.setQueueBucket(RenderQueue.Bucket.Gui);
        TimerText.setLocalTranslation(50,500,1);  
        guiNode.attachChild(TimerText);
        
        //=================IgnitionText========================================
        
        IgnitionText = new BitmapText(myFont, true); 
        IgnitionText.setSize(30);
        IgnitionText.setColor(ColorRGBA.Green);                                       
        IgnitionText.setQueueBucket(RenderQueue.Bucket.Gui);
        IgnitionText.setLocalTranslation(70,400,1);  
        guiNode.attachChild(IgnitionText);
        
        //===============MotorText==============================================
        
        MotorText = new BitmapText(myFont, true); 
        MotorText.setSize(30);
        MotorText.setColor(ColorRGBA.Green);                                       
        MotorText.setQueueBucket(RenderQueue.Bucket.Gui);
        MotorText.setLocalTranslation(70,400,1);  
        guiNode.attachChild(MotorText);
       
        System.out.println("about to init keys ");
        
        //initKeys();
        
        System.out.println("initkeys done ");
      }
   
     
      //=======================update method====================================
    
       public void update(float tpf)
       {
        
         switch(MainShipMotionControl.state){
         
         case ZERO_OFF:
         {
             
              DieselText.setText("Turn ON Dissel SWITCH!!");
              DieselText.setLocalTranslation(20,700,1); 
         
         }
                
         case DISSEL: //========================= DISSEL STATE ==================
         { 
            //System.out.println("state : "+MainShipMotionControl.state );
          
           if(ship_motion.isDisselSwitches())
           {  
                guiNode.detachChild(DieselText);
                rootNode.detachChild(guiNode);
                MainShipMotionControl.state=MainShipMotionControl.ShipState.ENGINE;
           }
           else
           {
               System.out.println(" KEY D NOT PRESSSED");
               DieselText.setText("PRESS DISSEL SWITCH");
               DieselText.setLocalTranslation(20,700,1); 
               
              if(!attached) 
              {
                  guiNode.attachChild(DieselText);
                  attached=true;
              }
           }
         }
         break;
             
         case ENGINE:  //================== IGNITION STATE===================
         {
            System.out.println("state : "+MainShipMotionControl.state );
            
            if(KEY_E_pressed)
             {
               guiNode.detachChild(EngineText);
               rootNode.detachChild(guiNode);
                MainShipMotionControl.state=MainShipMotionControl.ShipState.IGNITIION;
             }
             else
             { 
               EngineText.setText("TURN ON ENGINE FOR 10 sec");
               guiNode.attachChild(EngineText);
               EngineText.setLocalTranslation(20,700,1); 
               rootNode.attachChild(guiNode);
             }
          }
         break;
           case IGNITIION:  //================== IGNITION STATE===================
         {
               
            //System.out.println("state : "+MainShipMotionControl.state );
            
            if(KEY_I_pressed)
             {
                 guiNode.detachChild(IgnitionText);
                 rootNode.detachChild(guiNode);
                 //MainShipMotionControl.state=MainShipMotionControl.ShipState.MOTOR;
             }
             else
             { 
               IgnitionText.setText("TURN ON IGNITION");
               guiNode.attachChild(IgnitionText);
               IgnitionText.setLocalTranslation(20,700,1); 
               rootNode.attachChild(guiNode);
             }
          }
         break;
  
        default:
         {
         
         }
        break;
       }
     }

 
}
     
  
   
             
              


