
package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.audio.AudioNode;
import com.jme3.input.ChaseCamera;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.post.FilterPostProcessor;
import com.jme3.post.filters.TranslucentBucketFilter;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.system.AppSettings;
import com.jme3.texture.Texture2D;
import com.jme3.ui.Picture;
import com.jme3.util.SkyFactory;
import com.jme3.water.WaterFilter;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import mygame.excercises.ShipMovingOffExer;


public class ShipSimulatorWindow extends SimpleApplication{
    
   
    private SimulationScreensConstroller screenController;
    
    private ModelResourceManager modelResourceManager;
    
    private AbstractAppState selectedExercise;
    
    private SceneBuilder sceneBuilder;
    
    public static void main(String[] args) {
        
        ShipSimulatorWindow app = new ShipSimulatorWindow();
        AppSettings gameSettings=new AppSettings(false);
        gameSettings.setUseInput(true);
        gameSettings.setVSync(true);
        gameSettings.setBitsPerPixel(32);
        gameSettings.setRenderer(AppSettings.LWJGL_OPENGL_ANY);
        
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int width = gd.getDisplayMode().getWidth();
        int height = gd.getDisplayMode().getHeight();
        
        gameSettings.setResolution(width,height);
        gameSettings.setFullscreen(true);
        app.setShowSettings(false);
        app.setSettings(gameSettings);
        app.start();
         
    }
    

    public void simpleInitApp() {
     
         System.out.println("simpleInitApp()....");
         initSimulation();
         
                                                                                  //  initExcercise();
    }

    
    public void simpleUpdate(float tpf)
    {
        
    }
 
    private void initSound()
    {


      System.out.println("sound activated");
      
    }

    @Override
    public void simpleRender(RenderManager rm) {
     
     
    }
    
    public void initSimulation()
    {    
        
         //---- disable defaults----
         setDisplayFps(false);
         setDisplayStatView(false);
         flyCam.setEnabled(false);
         
         
         // singleton class, only one instance (exactly "new" once in static getInstance() function)
         this.modelResourceManager=ModelResourceManager.getInstance();
         this.modelResourceManager.setAssetManager(assetManager);
         
         //scene builder class
         this.sceneBuilder=new SceneBuilder(this,settings);
         
         
         //---- init screen controller----
         screenController=new SimulationScreensConstroller(assetManager,inputManager,audioRenderer,viewPort,this);
         screenController.initScreens();
         stateManager.attach(screenController);
         
         
         GameState.state=GameState.gameState.LOADING; 
         
    }
     
    public void startSimulation(String exercise_name)
    {    
        
       System.out.println("game started");
    
     
       this.sceneBuilder.createDashBoard();

       
       if(exercise_name.equals("getting_started"))
       {
         
          selectedExercise=new ShipMovingOffExer(assetManager,inputManager,rootNode,guiNode,this.modelResourceManager.getResource("Main_ship"));
       
          stateManager.attach(selectedExercise);
          
          System.out.println("finshed getting started called!!");
          
       }
       
       if(exercise_name.equals("advanced_traing"))
       {
       
         //selectedExercise= new 
       
       }
       
     }

    public void endSimulation()
    {
    
        
    }

    
    /**
     * @return the sceneBuilder
     */
    public SceneBuilder getSceneBuilder() {
        return sceneBuilder;
    }

    /**
     * @param sceneBuilder the sceneBuilder to set
     */
    public void setSceneBuilder(SceneBuilder sceneBuilder) {
        this.sceneBuilder = sceneBuilder;
    }
    
   
    
}

