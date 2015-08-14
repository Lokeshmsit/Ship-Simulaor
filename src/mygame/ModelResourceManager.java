package mygame;

import com.jme3.asset.AssetManager;
import com.jme3.asset.AssetNotFoundException;
import com.jme3.scene.Spatial;
import java.util.HashMap;


public class ModelResourceManager {
    
    private static ModelResourceManager instance = null;
    
    protected ModelResourceManager() {
        
      // Exists only to defeat instantiation.
        
        resourceMap=new HashMap<String,Spatial>();
        
    }
    public static ModelResourceManager getInstance() {
        
       if(instance == null) {
           
           instance = new ModelResourceManager();
         
       }
      
      return instance;
    }
    
    
    private HashMap<String,Spatial> resourceMap;
    
    private AssetManager assetManager;
    
    
    public boolean loadModel(String model_name,String path)
    {
        boolean load_success=false;
        
        try
        {
           Spatial spatial_model=this.getAssetManager().loadModel(path);
           
           addResource(model_name,spatial_model);
           
           load_success=true;
           
        }catch(AssetNotFoundException e)
        {
           e.printStackTrace();
        }
        
        return load_success;
           
    }
    
    public void addResource(String key_name,Spatial reource)
    {
        resourceMap.put(key_name, reource);
    } 
    
    public Spatial removeResource(String key_name)
    {
        return resourceMap.remove(key_name);
    }
    
    public Spatial getResource(String key_name)
    {
         return resourceMap.get(key_name);
    }

    /**
     * @return the assetManager
     */
    public AssetManager getAssetManager() {
        return assetManager;
    }

    /**
     * @param assetManager the assetManager to set
     */
    public void setAssetManager(AssetManager assetManager) {
        this.assetManager = assetManager;
    }
    
    
}

    

