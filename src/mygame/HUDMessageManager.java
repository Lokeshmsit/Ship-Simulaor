
package mygame;

import com.jme3.asset.AssetManager;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.math.ColorRGBA;
import com.jme3.renderer.queue.RenderQueue.Bucket;
import com.jme3.scene.Node;
import java.util.HashMap;
import static mygame.HUDMessageManager.counter;
import static mygame.HUDMessageManager.hudMessageMap;

public class HUDMessageManager{
    
      
       public static HashMap<String,BitmapText> hudMessageMap;
       
       static int counter;
      
       private BitmapFont myFont;
       
       private AssetManager assetManager;
  //   private BitmapFont guiFont;
    
    
    public HUDMessageManager(AssetManager mngr)
    {    
         this.assetManager=mngr;
         
         hudMessageMap=new HashMap<String,BitmapText>();
    }
       
        BitmapText createHUDMessage(String msg_string,int x,int y,int size,Node node)
       {    
           
         
           
           myFont = assetManager.loadFont("Interface/Fonts/ArialBlack.fnt");
           BitmapText hudText = new BitmapText(myFont, true); 
           hudText.setSize(30);
           hudText.setColor(ColorRGBA.Blue);                                       
           hudText.setText(msg_string);                      
          
           hudText.setQueueBucket(Bucket.Gui);
           hudText.setLocalTranslation(20,700,1);
           
           if(node!=null)
           {
                node.attachChild(hudText);
           }
           
           hudMessageMap.put(msg_string, hudText);
           
           counter++;
           
           return hudText;
            
       }
       
       void removeHUDMessage(String remove_key,Node node)
       {
          
              node.detachChild(hudMessageMap.remove(remove_key));
       } 
       
}
