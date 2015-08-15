
package mygame;

import com.jme3.asset.AssetManager;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import com.jme3.ui.Picture;


public class MainShipMotionControl extends AbstractControl {
    
    
    private ModelResourceManager resourceManager;
    
    private Spatial ship;
    private float speedFactor=0.12f;
    private float rotateFactor=0.15f;
    private Object throtal;
    private Spatial steer;
    private AssetManager assetManager;
    private InputManager inputManager;
    private Node guiNode;
    private Node rootNode;  
    private BitmapText GearUpText;
    private BitmapFont myFont;
    
    private Picture throttle_handle;
    private Picture knots_Needle;
    private Node needle_pivotKnots;
    
    private int[] throttleLevels = new int[]{-1,0,1,2,3,4,5};
    
    
    //==== Main ship control motion paprameters AND properties.
    private final float ThrottleMax=5.0f;
    private final float ThrottleMin=-1.0f;
    private float throttle;
    private float speed; 
    private boolean horn;
    private boolean disselSwitches;
    //===============================================
    
    
    //============ Engine parameters ================
    private float  heat;
    
   
    
    
    //==== propellent motor parameters======
    private int motorRMP;
    
    //=======================================
    
    
    private float bhp;
    private float C;
    private float DISPLACEMENT=3000.0f;
    

    /**
     * @return the disselSwitches
     */
    public boolean isDisselSwitches() {
        return disselSwitches;
    }

    /**
     * @param disselSwitches the disselSwitches to set
     */
    public void setDisselSwitches(boolean disselSwitches) {
        this.disselSwitches = disselSwitches;
    }

    /**
     * @return the heat
     */
    public float getIgnitionHeatTime() {
        return heat;
    }

    /**
     * @param heat the heat to set
     */
    public void setIgnitionHeatTime(float heat) {
        this.heat = heat;
    }
    
    //====== boat states
    public static enum ShipState {ZERO_OFF,DISSEL,ENGINE,HEAT,IGNITIION,MOTOR};
    
    //====== current state
    public static ShipState state;
    
   
    public MainShipMotionControl(AssetManager mngr,InputManager io_manager,Spatial ship_node,Node root_node,Node gui_node) 
    {       
        System.out.println("motioncontrol constructor");
        
        this.resourceManager=ModelResourceManager.getInstance();
        
        this.ship=ship_node;
        this.assetManager=mngr; 
        this.inputManager=io_manager;
        this.guiNode=gui_node;
        this.rootNode=root_node;
        
        myFont = assetManager.loadFont("Interface/Fonts/Verdana.fnt");
       
        GearUpText = new BitmapText(myFont, true); 
        GearUpText.setSize(30);
        GearUpText.setColor(ColorRGBA.Green);   
        GearUpText.setQueueBucket(RenderQueue.Bucket.Gui);
        guiNode.attachChild(GearUpText);
        rootNode.attachChild(guiNode);
         
        
        initMainShip();
        
        throttle_handle=(Picture)this.resourceManager.getResource("Throttle_handle");
        
        needle_pivotKnots=new Node("KnotsNeedle_pivot");
        knots_Needle=(Picture)this.resourceManager.getResource("knots_needle");
        needle_pivotKnots.getLocalTranslation().set(knots_Needle.getLocalTranslation().getX()+5,knots_Needle.getLocalTranslation().getY()+50,knots_Needle.getLocalTranslation().getZ());
        needle_pivotKnots.attachChild(knots_Needle);
        knots_Needle.center();
        knots_Needle.getLocalTranslation().set(knots_Needle.getLocalTranslation().getX(),knots_Needle.getLocalTranslation().getY()-18,knots_Needle.getLocalTranslation().getZ());
        guiNode.attachChild(needle_pivotKnots);
        
        
        initKeys();
         
        state=ShipState.ZERO_OFF;
        
        System.out.println("motioncontrol constructor END");
        
    }
    
    /** 
     *  This function initialize boat with initial parameters and properties.
     */
    private void initMainShip()
    {
        throttle=0;
        bhp=0.0f;
        C=240.0f;
        
        setIgnitionHeatTime(0.0f);
    }
    
    protected void controlUpdate(float tpf) {
        
      switch(state){
         
         case DISSEL: //========================= DISSEL STATE==================
         { 
           
             
         }
         break;
             
         case ENGINE:  //================== IGNITION STATE===================
         {
            //System.out.println("state : "+state );
            
         }
         break;
             
         case IGNITIION:  //================== IGNITION STATE===================
         {
            initKeys_motion();
            MainShipMotionControl.state=MainShipMotionControl.ShipState.MOTOR;  
         }
         break;
             
         case MOTOR:  //======================= MOTOR STATE ====================
         {  
               
              Quaternion rotation=ship.getLocalRotation();
              Vector3f front=new Vector3f(getSpeedFactor(tpf),0,0);
              Vector3f Heading=rotation.mult(front);
              ship.move(Heading);           
                  
              //System.out.println("speedfactor before :"+speedFactor);
                  
               //GearUpText.setText("gear UP");              
         }
        break;
             
        default:
        {
            
        }
        
        break;
            
       }
        
    }
    
    //================ initKeys() =============================================
    public void  initKeys()
    {
             inputManager.addMapping("DISSEL_ON",new KeyTrigger(KeyInput.KEY_D));
             inputManager.addMapping("ENGINE",new KeyTrigger(KeyInput.KEY_E));
             inputManager.addMapping("IGNITIION",new KeyTrigger(KeyInput.KEY_I));
             inputManager.addListener(gettingstartexcercise,"DISSEL_ON","ENGINE","IGNITIION");
             
    }
    
     private AnalogListener gettingstartexcercise=new AnalogListener() {
         
     public void onAnalog(String name, float value, float tpf) throws UnsupportedOperationException{
    
        if(name.equals("DISSEL_ON"))
       { 
           if(state==ShipState.ZERO_OFF)  
          {  
               setDisselSwitches(true);
               
               state=ShipState.DISSEL;
               
          }
           
       } else if(name.equals("ENGINE"))
         { 
              
            if(state==ShipState.DISSEL)   
            {    
               state=ShipState.ENGINE;
            }
                  
         }
        
        else if(name.equals("IGNITIION"))
       {
         
          if(state==ShipState.ENGINE) 
          {  
              
             heat+=tpf;
                
             if(heat>=6) 
            {
                System.out.println("enough heat");
                System.out.println("ship is in IGINITION State Now.");
                state=MainShipMotionControl.ShipState.IGNITIION;
                
            }else
             {
                
             }
             
          }
          
       } 
        
      }
        
     
     };
    

     public void initKeys_motion()
     {
            
            inputManager.addMapping("left",new KeyTrigger(KeyInput.KEY_LEFT));
            inputManager.addMapping("right",new KeyTrigger(KeyInput.KEY_RIGHT));
            inputManager.addMapping("THROTTLE_UP",new KeyTrigger(KeyInput.KEY_UP));
            inputManager.addMapping("THROTTLE_DOWN",new KeyTrigger(KeyInput.KEY_DOWN));  
            inputManager.addListener(moveListener,"left","right","THROTTLE_UP","THROTTLE_DOWN");
            
     }
     
    private AnalogListener moveListener=new AnalogListener()
    {
       public void onAnalog(String name, float value, float tpf) throws UnsupportedOperationException{
            
       if(name.equals("left"))
       {     
             
             ship.rotate(0.0f,0.4f*tpf, 0.0f);
       }
        else if(name.equals("right"))
           {
            
             ship.rotate(0.0f,-0.4f*tpf, 0.0f);   
             
           }
           else if(name.equals("THROTTLE_UP"))
               {
                  
                    if(throttle<=ThrottleMax) 
                   {  
                       throttle=throttle+0.01f;
                     
                       throttle_handle.move(0,0.14f,0);
                     
                       needle_pivotKnots.rotate(0.0f,0.0f,-0.009866f);
                   }
                 
               }
           else if(name.equals("THROTTLE_DOWN"))
           {
               
             if(throttle>=ThrottleMin) 
             {
                 
                throttle=throttle-0.01f;
             
                throttle_handle.move(0,-0.14f,0);
                
                needle_pivotKnots.rotate(0.0f,0.0f,0.009866f);
                
             }
             
           }
       
        }
     };
    
    
  public float getSpeedFactor(float tpf)
  {       
     bhp=40*(throttle<0?throttle*-1.0f:throttle)+1;
    
     //System.out.println("tpf : "+tpf);
    
     speed=C*FastMath.sqrt(bhp/DISPLACEMENT);
  
     System.out.println("speed(mph)  "+speed+" speed(m/s): "+speed*0.44704f);
     
     // System.out.println(getSpeedFactor(tpf));
     
     speedFactor=speed*tpf*0.44704f;
     
     
     //System.out.println("speedFactor : "+speedFactor*0.44704f);
     
     return throttle<0?speedFactor*-1:speedFactor;
    
  }
      
   public float getSpeed() {
        return speedFactor;
    }

    public void setSpeed(float speedFactor) {
        
        this.speedFactor = speedFactor;
        
    }

   
    public float getRotateFactor() {
        return rotateFactor;
    }

    public void setRotateFactor(float rotateFactor) {
        this.rotateFactor = rotateFactor;
    }

    protected void controlRender(RenderManager rm, ViewPort vp) {
    
    }

}

