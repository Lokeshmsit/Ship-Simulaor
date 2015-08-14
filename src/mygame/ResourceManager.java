package mygame;
import com.jme3.scene.Spatial;
import java.util.HashMap;


public class ResourceManager<K,T> {
     private HashMap<K,T> resourceMap;
    
    public ResourceManager()
    {
    
       resourceMap=new HashMap<K,T>();
    
    }
    
    public void addResource(K key_name,T reource)
    {
        resourceMap.put(key_name, reource);
    } 
    
    public T removeResource(K key_name)
    {
        return resourceMap.remove(key_name);
    }
    
    public T getResource(K key_name)
    {
         return resourceMap.get(key_name);
    }
    
}


   
