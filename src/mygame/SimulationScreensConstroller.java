
package mygame;
import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioRenderer;
import com.jme3.input.InputManager;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.renderer.ViewPort;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.render.TextRenderer;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import de.lessvoid.nifty.tools.SizeValue;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import javazoom.jl.player.advanced.*;

public class SimulationScreensConstroller  extends AbstractAppState implements ScreenController{
     
    private ShipSimulatorWindow shipSimulatorWindow;
     
     private int frameCount = 1;
    
     private float load_counter = 0.0f;  
     
     private NiftyJmeDisplay niftyDisplay;
    
     private Nifty nifty;
    
     private boolean load=true;
    
     private Future loadFuture = null;
    
     private Object app;
    
     private SoundJLayer soundToPlay;
 
     private enum ScreenStates {LOADING,OPTIONS,SIMULATION};
    
     private ScreenStates screenState;
     
     private Element progressBarElement;
     private Element element;
     private TextRenderer textRenderer;
 
     boolean options_screen=false;
     
     private ScheduledThreadPoolExecutor exec = new ScheduledThreadPoolExecutor(2);
 
     public SimulationScreensConstroller(AssetManager assetmanager,InputManager inputmanager ,AudioRenderer audiorenderer,ViewPort viewport,Application app)
     {    
        //----- keep reference to the main parent ShipSimulatorWindows(extends SimpleApplication) in a memeber variable.
         this.shipSimulatorWindow=(ShipSimulatorWindow)app;
         
         //---------- create nifty display and register "this"(screenconstroller) with nifty----------
         niftyDisplay=new NiftyJmeDisplay(assetmanager,inputmanager,audiorenderer,viewport);
         nifty = niftyDisplay.getNifty();
         nifty.registerScreenController(this);
         this.shipSimulatorWindow.getGuiViewPort().addProcessor(niftyDisplay);
         System.out.println("simscreencontroller constrctr");
     
     }
    
    /**
     *  function try to add all the xml screen files in nifty.
     */
    public boolean initScreens()
    {
         boolean success=false;
        
         try
         {   
             nifty.addXml("Interface/Screens/Loading.xml");
             nifty.addXml("Interface/Screens/Options.xml");
             nifty.addXml("Interface/Screens/login.xml");
             nifty.addXml("Interface/Screens/ExercisesOptions.xml");
            // nifty.addXml("Interface/Screens/mysettings.xml");
           
             nifty.gotoScreen("start");
             
             screenState=ScreenStates.LOADING;
            System.out.println("go to next screen");
            success=true;
           
         }catch(RuntimeException e)
         {
             e.printStackTrace();
         }
              System.out.println("init screen");
       
              return success; 
     
    }
    /**
     *  function changes screen as supplied screen ID. 
     * @param screenid_text = screens's ID text
     */
    public void setCurrentScreen(String screenid)
    {
        System.out.println("switch to current screen : "+ screenid);
        
        
        if(screenid.equals("login"))
        {
            
          if(soundToPlay!=null)  
             PlayMusic();
          
        }else
          {
             if(soundToPlay!=null)  
                StopMusic();
          }
        
       
        nifty.gotoScreen(screenid);
    }
 
 public void startSimulation(String excersizeName)
{
   
    System.out.println("loading my game...");
    
    //nifty.closePopup("options");
    
    nifty.gotoScreen("blank");
    
    shipSimulatorWindow.startSimulation(excersizeName);
    
    shipSimulatorWindow.getStateManager().detach(this);
    
    nifty.exit();
    
}
 
    public void exit()
    {
      
        shipSimulatorWindow.stop();
        
    }
    
    /**
     * @return the shipSimulatorApp
     */
    public ShipSimulatorWindow getShipSimulationWindow() {
        return shipSimulatorWindow;
    }
    
    
    public void update(float tpf)
    {
      
    
              if(load)
               {     
                  if(loadFuture==null)
                    {   
                      loadFuture=exec.submit(loadingCallable);
                     }   
               }
            
             if(loadFuture.isDone()) 
               {
               
                    if(screenState!=ScreenStates.OPTIONS)
                    {
                        
                        
                         setCurrentScreen("login");
                         
                        // nifty.gotoScreen("options");
                         
                         screenState=ScreenStates.OPTIONS;
                         
                         System.out.println("Options screens has been updated!!");

                   }
                         
               }   
           
        }
    
    
 //=============================== Loading Bar Frame ===============================================================================================
   
    Callable<Void> loadingCallable = new Callable<Void>()
    {
       public Void call()
       {
            System.out.println("call method called");
            
            //load_counter=2.0f; 
             
           
            while(load_counter<1.0f)            
            {
               
             if (frameCount == 1) {
           //     getShipSimulationWindow().getModelResourceManager().loadModel("MainSip", "Models/NewShipModels/OBJ/fastboatobj.j3o");
                
                getShipSimulationWindow().getSceneBuilder().initShip(); 
                System.out.println("frame 1");
                setProgress(0.001f,"Loading MainShip");
           

            } else if (frameCount == 2) {
                System.out.println("frame 2");
                getShipSimulationWindow().getSceneBuilder().initSky();
                
                setProgress(0.004f, "Loading water");
 
            } else if (frameCount == 3) {
                System.out.println("frame 3");
                getShipSimulationWindow().getSceneBuilder().initWater();
                setProgress(0.006f, "Loading mountains");
 
            } else if (frameCount == 4) {
                System.out.println("frame 4");
                getShipSimulationWindow().getSceneBuilder().initCamera();
                setProgress(0.008f, "Creating terrain");
 
            } else if (frameCount == 5) {
                System.out.println("frame 5");
                getShipSimulationWindow().getSceneBuilder().initLights();
                setProgress(0.009f, "Loading cameras");
 
            } else if (frameCount == 6) {
                
                System.out.println("frame 6");
                
                
                setProgress(0.010f, "positioning camera");
 
            } else if (frameCount == 7) {
                System.out.println("frame 7");
                setProgress(0.011f, "Loading complete");
 
            } else if (frameCount == 8) {
                System.out.println("frame 8");
                setProgress(0.012f, "Loading complete");
                load_counter=0.012f;
               
                //nifty.closePopup("start");
                //nifty.gotoScreen("options");
               // nifty.exit();
                 
            }
           else if(frameCount>9)
            {   
                 load_counter=load_counter+0.08f;
        
                 setProgress(load_counter, "loading complete");
                
                  //nifty.closePopup("start");
                  //nifty.gotoScreen("options");
             }
             
             frameCount++;
            
           }
            
            
            if(soundToPlay==null)
            { 
                // soundToPlay = new SoundJLayer("/assets/Sounds/instrumental.mp3");A-ha - Velvet karaoke.mp3
                   soundToPlay = new SoundJLayer("/assets/Sounds/A-ha - Velvet karaoke.mp3");
            }
            
         
            options_screen=true;
            
           return null;
        }
    };
    
     
     public void setProgress(final float progress, String loadingText)
     {
         System.out.println("setProgress() called : "+progress+", "+loadingText);
         
         final int MIN_WIDTH = 32;
         int pixelWidth = (int) (MIN_WIDTH + (progressBarElement.getParent().getWidth() - MIN_WIDTH) * progress);
         
         progressBarElement.setConstraintWidth(new SizeValue(pixelWidth + "px"));
         progressBarElement.getParent().layoutElements();
 
          //textRenderer.setText(loadingText);
         
     }
    
     public void bind(Nifty nifty, Screen screen) {
          
           System.out.println("bind()");
      }
     
     public void onStartScreen() {
        
        progressBarElement = nifty.getScreen("start").findElementByName("progressbar");

       // element = nifty.getScreen("start").findElementByName("loadingtext");
         
       // textRenderer = element.getRenderer(TextRenderer.class);
        
        System.out.println("onStartScreen()");
        load=true;
        
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

     public void onEndScreen() {
        System.out.println("ononEndScreen()");
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
     
     
     public int getSreenWidth()
     {
     
         GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        
         return  gd.getDisplayMode().getWidth();
          
     }
     
     
     public int getScreenHeight()
     {
            GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        
            return  gd.getDisplayMode().getHeight();
     
     }
     
     //================================================================================
     //======================= use/call these methods from nifty screens
     
     public String convertScreenHeightinPercentage(int pixels)
     {
         GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        
         return 100*(pixels/gd.getDisplayMode().getHeight())+"";
     
     }
     
     public String convertScreenHightinPixels(int percentage)
     {
       GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();

        return percentage*gd.getDisplayMode().getHeight()/100+"px";
     
     }
    
     
     //================================================================================
     //======================= Play/stop  sound on JME thread.  
     public void PlayMusic()
     {
          getShipSimulationWindow().enqueue(new Callable<Void>() {
         
              public Void call() throws Exception {
            
                  soundToPlay.play();
            
            return null;
        }
        });
          
     }    
      public void StopMusic()
     {
          getShipSimulationWindow().enqueue(new Callable<Void>() {
         
              public Void call() throws Exception {
            
                  soundToPlay.stop();
            
            return null;
        }
      });
     
     }
      //================================================================================
     
     
    } 


   

//========================  Thread plays Theme song of the Project at login===================================
class SoundJLayer extends PlaybackListener implements Runnable
{
    private String filePath;
    private AdvancedPlayer player;
    private Thread playerThread;    
    
    boolean isRunning=false;

    public SoundJLayer(String filePath)
    {
        this.filePath = filePath;
    }

    public void play()
    {   
        try
        {
            String urlAsString = 
                "file:///" 
                + new java.io.File(".").getCanonicalPath() + "/" 
                + this.filePath;

            this.player = new AdvancedPlayer
            (
                new java.net.URL(urlAsString).openStream(),
                javazoom.jl.player.FactoryRegistry.systemRegistry().createAudioDevice()
            );

            this.player.setPlayBackListener(this);

            this.playerThread = new Thread(this, "AudioPlayerThread");

            this.playerThread.start();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
    
    public void stop()
    {
               if(this.player!=null)
              {
                  if(isRunning)
                  {  
                      this.player.stop();
              
                      this.player.close(); 
                      isRunning=false;
                  }
              }
            
    }

    // PlaybackListener members

    public void playbackStarted(PlaybackEvent playbackEvent)
    {
        System.out.println("playbackStarted");
    }

    public void playbackFinished(PlaybackEvent playbackEvent)
    {
        System.out.println("playbackEnded");
    }    
    
    // Runnable members
    
    public void run()
    {
        try
        {   isRunning=true;
            this.player.play();
            System.out.println("stop called!!");
        }
        catch (javazoom.jl.decoder.JavaLayerException ex)
        {
            ex.printStackTrace();
        }

    }
}