
package mygame;

import com.jme3.app.state.AbstractAppState;
import com.jme3.asset.AssetManager;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.math.ColorRGBA;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Node;
//import mygame.HUDMessageManager;

import com.jme3.audio.AudioNode;
import com.jme3.input.InputManager;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;


public class Excercise1_ extends AbstractAppState {
    
     BitmapText topLeft;
     BitmapText topCenter;
     BitmapText topRight;
     BitmapFont myFont;
     
     private AssetManager assetManager;
     private HUDMessageManager hud_mngr;
     private Node rootNode,guiNode;
     private InputManager inputManager;
     private AudioNode motorsound,motorsound1;
     
     
     private boolean KEY_M_pressed=false;
     private boolean KEY_D_pressed=false;
     private boolean KEY_I_pressed=false;
     
     boolean attached=false;
    
     private enum excerciseState {DISSEL,IGNITIION,MOTOR};
     private excerciseState state;
    
     private Spatial ship;
           
     
     public Excercise1_(AssetManager mngr,InputManager io_manager,Node root_node,Node gui_node,Node main_ship)
     {  
        state=excerciseState.DISSEL; 
        
        this.ship=main_ship;
        
        System.out.println("current State is "+state);
        this.assetManager=mngr; 
        this.inputManager=io_manager;
        
        this.guiNode=gui_node;
        this.rootNode=root_node;
      
        this.rootNode.attachChild(this.guiNode);
        
        myFont = assetManager.loadFont("Interface/Fonts/Verdana.fnt");
        
        //=====================topLeft panel=============================
        
        topLeft = new BitmapText(myFont, true); 
        topLeft.setSize(30);
        topLeft.setColor(ColorRGBA.Blue);   
       // topLeft.setText("okkkkk");
        topLeft.setQueueBucket(RenderQueue.Bucket.Gui);
        topLeft.setLocalTranslation(20,700,1);  
        guiNode.attachChild(topLeft);
        
        //=====================topCenter panel============================
        
        topRight = new BitmapText(myFont, true); 
        topRight.setSize(30);
        topRight.setColor(ColorRGBA.Blue);                                       
        topRight.setQueueBucket(RenderQueue.Bucket.Gui);
        topRight.setLocalTranslation(80,700,1);  
        
        
        //=====================topCenter panel============================
        
        topCenter = new BitmapText(myFont, true); 
        topCenter.setSize(30);
        topCenter.setColor(ColorRGBA.Blue);                                       
                  
        topCenter.setQueueBucket(RenderQueue.Bucket.Gui);
        topCenter.setLocalTranslation(50,900,1);  
        
        this.state=excerciseState.MOTOR;
        
        initKeys();
    
     }
   
      //=======================update method=================================
    
       public void update(float tpf)
       {
           
         //System.out.println("excercise update method");  
        
         switch(state){
         
         case DISSEL: //========================= DISSEL STATE======================================
         { 
             
            System.out.println("state : "+state );
          
           if(KEY_D_pressed==true)
           {  
               // System.out.println(" KEY D PRESSSED");
                //hud_mngr.removeHUDMessage("PRESS DISSEL SWITCH", guiNode);
                guiNode.detachChild(topLeft);
               
                state=excerciseState.IGNITIION;
                
               
           }
           else
           {
               System.out.println(" KEY D NOT PRESSSED");
               
               //hud_mngr.createHUDMessage("PRESS DISSEL SWITCH",40,700, 25, guiNode);
               topLeft.setText("PRESS DISSEL SWITCH");
               
              if(!attached) 
              {
                  guiNode.attachChild(topLeft);
                  attached=true;
              }
               
            
            }
         }
          break;
             
         case IGNITIION:  //================== IGNITION STATE==========================================
         {
            System.out.println("state : "+state );
           //  state=excerciseState.IGNITIION;
            if(KEY_I_pressed)
             {
                // guiNode.detachChild(topRight);
                 state=excerciseState.MOTOR;
             }
             else
             { 
               topRight.setText("PRESS I for 5 sec");
               guiNode.attachChild(topLeft);
               rootNode.attachChild(guiNode);
               
              }
          }
            break;
         case MOTOR:  //======================= MOTOR STATE ===============================================
         {  
             System.out.println("state : "+state );
          //      state=excerciseState.MOTOR;
             if(KEY_M_pressed)
             {
                  guiNode.detachChild(topCenter);
                
             }
             else
              {
               topCenter.setText("PRESS I for 5 sec");
               guiNode.attachChild(topLeft);
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
     
     public void  initKeys()
       {
          
            inputManager.addMapping("DISSEL",new KeyTrigger(KeyInput.KEY_D));
            inputManager.addMapping("IGNITIION",new KeyTrigger(KeyInput.KEY_I));
            inputManager.addMapping("MOTOR",new KeyTrigger(KeyInput.KEY_M));
            inputManager.addListener(gettingstartexcercise,"DISSEL","IGNITIION","MOTOR");
       }
      
     private ActionListener gettingstartexcercise=new ActionListener() {
         
       
         public void onAction(String name, boolean isPressed, float tpf) {
       
          if(name.equals("DISSEL"))
         {
           //guiNode.detachChild(topRight);
           
           KEY_M_pressed=true;
           
         }else if(name.equals("IGNITIION"))
          {
           
           
          }else if(name.equals("MOTOR"))
           {
             //motorsound=new AudioNode(assetManager, "Sounds/motor_iginition.wav", true);
             
             inputManager.addMapping("left",new KeyTrigger(KeyInput.KEY_LEFT));
             inputManager.addMapping("right",new KeyTrigger(KeyInput.KEY_RIGHT));
             inputManager.addListener(shipkeyslistener,"left","right");
            
             motorsound.play();
             
             motorsound1=new AudioNode(assetManager, "Sounds/boat_motor_continous.wav", true); 
             motorsound1.play();
           }
        }
     };
     
     
     private AnalogListener shipkeyslistener=new AnalogListener()
    {
       public void onAnalog(String name, float value, float tpf) throws UnsupportedOperationException{
            
       if(name.equals("left"))
       {
         ship.rotate(0.0f,0.2f*tpf, 0.0f);
       }
        else if(name.equals("right"))
           {
             ship.rotate(0.0f,-0.2f*tpf, 0.0f);   
           }
           else if(name.equals("forward"))
               {
                  
                    Quaternion rotation = ship.getLocalRotation();
                    Vector3f front = new Vector3f(0.2f, 0, 0.0f);
                    Vector3f heading = rotation.mult(front);
                    ship.move(heading);
                 
               }
           else if(name.equals("backward"))
           {
                Quaternion rotation = ship.getLocalRotation();
                Vector3f front = new Vector3f(-0.2f, 0, 0.0f);
                Vector3f heading = rotation.mult(front);
                ship.move(heading);

           }
           
        }
               
        };
     
     
}
              


