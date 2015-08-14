/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.bounding.BoundingBox;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.post.FilterPostProcessor;
import com.jme3.post.filters.TranslucentBucketFilter;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.system.AppSettings;
import com.jme3.texture.Texture2D;
import com.jme3.ui.Picture;
import com.jme3.util.SkyFactory;
import com.jme3.water.WaterFilter;


/**
 *
 * @author lokesh
 */
public class SceneBuilder {
    
  
    private Spatial ship,terrain,sky;
 
    private FilterPostProcessor fpp;
    private WaterFilter w;
    private Vector3f lightDir=new Vector3f(-4.9f,-1.3f,5.9f);
    private ThirdPersonCamera playercam;
    private EnvironmentShips environment_ships;
    
    private ShipSimulatorWindow simwindow;
    
    private AppSettings settings;
    
    private ModelResourceManager resourceManager;
    
    public SceneBuilder(ShipSimulatorWindow sim_window,AppSettings settins)
    {
        
        this.simwindow=sim_window;
        this.settings=settins;
        this.resourceManager=ModelResourceManager.getInstance();
        
    }
    
    
  public void initShip()
  {   
        //ship=this.simwindow.getAssetManager().loadModel("Models/ship2/obj_uvmapped/untitled.j3o");
      
        System.out.println("Mainship loading...");
      
        ship=this.simwindow.getAssetManager().loadModel("Models/ShipModels/10/JME/OBJ/fastboatobj.j3o");
        ship.scale(8.0f, 8.0f, 8.0f);
        ship.rotate(0.0f,FastMath.PI,0.0f);
        ship.setLocalTranslation(0.0f,0.0f,0.0f);
        this.resourceManager.addResource("Main_ship", ship);
        simwindow.getRootNode().attachChild(ship);
        
        System.out.println("Mainship loaded");
        
        BoundingBox b=(BoundingBox)ship.getWorldBound();
        
        System.out.println("Breadth : "+b.getXExtent()+" height : "+b.getYExtent());
        
  }
  
   public void initSky()
   { 
       Spatial spsky=SkyFactory.createSky(this.simwindow.getAssetManager(), "Textures/Sky/Bright/BrightSky.dds", false);
       this.resourceManager.addResource("sky", spsky);
       simwindow.getRootNode().attachChild(spsky);
    
    }
   
    
    public void initTerrain()
    {
        //terrain=this.simwindow.getAssetManager().loadModel("Scenes/terrain.j3o");
        //terrain.setLocalTranslation(ship.getLocalTranslation());
       // simwindow.getRootNode().attachChild(terrain);
    }
    public void initWater()
    {
        FilterPostProcessor fpp=new FilterPostProcessor(this.simwindow.getAssetManager());
        WaterFilter w=new WaterFilter(simwindow.getRootNode(),lightDir);
        w.setWaterHeight(-2.00f);
        
        w.setSpeed(0.5f);
        w.setLightColor(ColorRGBA.White);
        w.setWaterColor(ColorRGBA.Brown.mult(2.0f));
        fpp.addFilter(w);
        
        //TranslucentBucketFilter transbucketfilter=new TranslucentBucketFilter();
       // transbucketfilter.setEnabled(true);
       
       // fpp.addFilter(transbucketfilter);
     
        this.simwindow.getViewPort().addProcessor(fpp);
        
   }
    
    public void createDashBoard()
    {
        createSpeedoMeter();
        createThrottle();
        createDisselSwitches();
        
    }
    
    public void createThrottle()
    {
          Picture pic = new Picture("Throttle");
          pic.setWidth(this.settings.getWidth()/16);
          pic.setHeight(settings.getHeight()/8);
          pic.setPosition(settings.getWidth()/2+settings.getWidth()/5, settings.getHeight()/30);
      
          Texture2D tex=(Texture2D)this.simwindow.getAssetManager().loadTexture("Textures/dash_board_base_1.png");
          pic.setTexture(this.simwindow.getAssetManager(), tex, true);
          
          this.simwindow.getGuiNode().attachChild(pic);
          
          
          Picture pic2 = new Picture("Throttle_handle");
          pic2.setWidth(settings.getWidth()/24);
          pic2.setHeight(settings.getHeight()/24);
          pic2.setPosition(settings.getWidth()/2+settings.getWidth()/5+settings.getWidth()/80, settings.getHeight()/20);
      
          Texture2D tex2=(Texture2D)this.simwindow.getAssetManager().loadTexture("Textures/glossy-black-circle-button-md.png");
          pic2.setTexture(this.simwindow.getAssetManager(), tex2, true);
          this.simwindow.getGuiNode().attachChild(pic2);
          this.resourceManager.addResource("Throttle_handle", pic2);
          
    }

     public void createSpeedoMeter()
    {
        
      Picture pic = new Picture("knots meter");
      //pic.setImage(this.simwindow.getAssetManager(), "Textures/new_knots_text_meter.png", true);
      pic.setWidth(settings.getWidth()/6);
      pic.setHeight(settings.getHeight()/6);
      pic.setPosition(settings.getWidth()*33/100, settings.getHeight()*1/100);
      
      Texture2D tex=(Texture2D)this.simwindow.getAssetManager().loadTexture("Textures/new_knots_text_meter.png");
      pic.setTexture(this.simwindow.getAssetManager(), tex, true);
      //pic.setQueueBucket(RenderQueue.Bucket.Translucent);
      
      this.simwindow.getGuiNode().attachChild(pic);
      
      Picture pic_needle1 = new Picture("knots meter needle");
      //pic.setImage(this.simwindow.getAssetManager(), "Textures/new_knots_text_meter.png", true);
      pic_needle1.setWidth(settings.getWidth()/70);
      pic_needle1.setHeight(settings.getHeight()/18);
      pic_needle1.setPosition(settings.getWidth()/3+settings.getWidth()/13, 24);
      
      Texture2D tex_needle=(Texture2D)this.simwindow.getAssetManager().loadTexture("Textures/white_needle.png");
      pic_needle1.setTexture(this.simwindow.getAssetManager(), tex_needle, true);
      
      this.simwindow.getGuiNode().attachChild(pic_needle1);
      
      this.resourceManager.addResource("knots_needle", pic_needle1);
      
      
      Picture pic2 = new Picture("rpm motor");
      //pic.setImage(this.simwindow.getAssetManager(), "Textures/new_knots_text_meter.png", true);
      pic2.setWidth(settings.getWidth()/6);
      pic2.setHeight(settings.getHeight()/6);
      pic2.setPosition(settings.getWidth()/2, 5);                     //settings.getWidth()/3+settings.getWidth()/6
      
      Texture2D tex2=(Texture2D)this.simwindow.getAssetManager().loadTexture("Textures/new_RPM_meter.png");
      pic2.setTexture(this.simwindow.getAssetManager(), tex2, true);
      
       this.simwindow.getGuiNode().attachChild(pic2);
    
    }
    
     
    public void createDisselSwitches()
    {
      Picture pic = new Picture("dissel switch");
      //pic.setImage(this.simwindow.getAssetManager(), "Textures/new_knots_text_meter.png", true);
      pic.setWidth(settings.getWidth()/34);
      pic.setHeight(settings.getHeight()/24);
      pic.setPosition(settings.getWidth()/4, 24);
      
      Texture2D tex=(Texture2D)this.simwindow.getAssetManager().loadTexture("Textures/Transparent_red.png");
      pic.setTexture(this.simwindow.getAssetManager(), tex, true);
    
      this.simwindow.getGuiNode().attachChild(pic);
      
      Picture pic2 = new Picture("dissel switch 2");
      //pic.setImage(this.simwindow.getAssetManager(), "Textures/new_knots_text_meter.png", true);
      pic2.setWidth(settings.getWidth()/34);
      pic2.setHeight(settings.getHeight()/24);
      pic2.setPosition(settings.getWidth()/4+settings.getWidth()/24, 24);
      
      Texture2D tex2=(Texture2D)this.simwindow.getAssetManager().loadTexture("Textures/Transparent_red.png");
      pic2.setTexture(this.simwindow.getAssetManager(), tex2, true);
    
      this.simwindow.getGuiNode().attachChild(pic2);
      
    }
    
    public void initCamera()
    {
        //flyCam.setEnabled(true);
        //flyCam.setDragToRotate(true);
        //flyCam.setEnabled(false);
       
        playercam= new ThirdPersonCamera("shipCamNode",this.simwindow.getCamera(),(Node)ship);
        
       
    }
    
    public void initLights()
    {
        DirectionalLight dl = new DirectionalLight();
        dl.setColor(ColorRGBA.White);
        dl.setDirection(new Vector3f(2.8f, -2.8f, -2.8f).normalizeLocal());
       
       
        simwindow.getRootNode().addLight(dl);
    }
    
    
    
    
    public void iniEnvironmentShips()
    {
       environment_ships=new EnvironmentShips(this.simwindow.getAssetManager(),this.simwindow.getRootNode(),ship);
       environment_ships.createScene();
      
    }
   
    
}
