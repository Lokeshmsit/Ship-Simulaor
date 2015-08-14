/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.ai;

import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;

/**
 *
 * @author lokesh
 */
public class AutonomousAgent extends AbstractControl{

     private Spatial ship,mainship;
     private Geometry m;
     
     //private float rotationCounter m;
     
     private float speedFactor,rotateFactor;
     
     private float currentDistance;
     
     private float moveCounter;
     
     private float rotationStepStart,rotationStepEnd;
     
     private int counterFlip=0;
     
     
     public AutonomousAgent(Spatial spobj,Spatial m_p)
     {
         
         
         
         //moveCounter=speedFactor*200;
         moveCounter=speedFactor;
         this.ship=spobj;
       
      
         this.mainship=m_p;
         moveCounter=speedFactor=0.25f;
         rotateFactor=0.15f;
         
         rotationStepStart=speedFactor*200;
         rotationStepEnd=rotationStepStart+2*speedFactor;
         
         ship.getWorldTranslation().set(mainship.getWorldTranslation());
         ship.move(0.0f,0.0f,2.0f);
         
         System.out.println("working");
     }
    
    @Override
    protected void controlUpdate(float tpf) {
       
            currentDistance=mainship.getWorldTranslation().distance(ship.getWorldTranslation());  
        
       
            
            if(moveCounter>rotationStepStart&&moveCounter<rotationStepEnd)
            {
                Quaternion rotation=ship.getLocalRotation();
                
                if(counterFlip%2==0)
                  rotation.fromAngleAxis(-rotateFactor, Vector3f.UNIT_Y);
                else
                  rotation.fromAngleAxis(rotateFactor, Vector3f.UNIT_Y);
  
                ship.rotate(rotation);
                
               if(moveCounter==rotationStepEnd)
               {
                  moveCounter=0.0f;
                  counterFlip++;
               } 
                
            }
               Quaternion rotation=ship.getLocalRotation();
               Vector3f front=new Vector3f(speedFactor,0,0);
               Vector3f Heading=rotation.mult(front);
               ship.move(Heading);
               
               moveCounter=moveCounter+speedFactor;
            
        
        
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
    }
    
}
