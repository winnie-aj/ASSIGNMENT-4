/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package azurecenprojecttry;

import azurecenprojectry.Info;
import java.util.HashMap;
import java.util.Map;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import javax.swing.Icon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.Tuple;

/**
 *
 * @author USER
 */
public class AzureCENProjectTry {
    static HashMap<Double, String> redisData = new HashMap<Double, String>();
    private static Object jedis;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       

        boolean useSsl = true;
        String cacheHostname = "Winnie.redis.cache.windows.net";
        String cachekey = "AyJuzzp0ase3YiBJTTnQhDyQd9IQpSSIj3lyQ6f8MiU=";

        // Connect to the Azure Cache for Redis over the TLS/SSL port using the key.
        JedisShardInfo shardInfo = new JedisShardInfo(cacheHostname, 6380, useSsl);
        shardInfo.setPassword ("AyJuzzp0ase3YiBJTTnQhDyQd9IQpSSIj3lyQ6f8MiU="); /* Use your access key. */
        Jedis jedis = new Jedis(shardInfo);      

        // Perform cache operations using the cache connection object...

        // Simple PING command        
        System.out.println( "\nCache Command  : Ping" );
        System.out.println( "Cache Response : " + jedis.ping());

        // Simple get and put of integral data types into the cache
        System.out.println( "\nCache Command  : GET Message" );
        System.out.println( "Cache Response : " + jedis.get("Message"));

        System.out.println( "\nCache Command  : SET Message" );
        System.out.println( "Cache Response : " + jedis.set("Message", "Hello! The cache is working from Java!"));

        // Demonstrate "SET Message" executed as expected...
        System.out.println( "\nCache Command  : GET Message" );
        System.out.println( "Cache Response : " + jedis.get("Message"));

        // Get the client list, useful to see if connection list is growing...
        System.out.println( "\nCache Command  : CLIENT LIST" );
        System.out.println( "Cache Response : " + jedis.clientList());

        jedis.close();
    
       // Jedis jedis = new Jedis("localhost");
        Info faac = new Info();
        ArrayList<String> names = new ArrayList<String>();
        ArrayList<String> value = new ArrayList<String>();
       // HashMap<Double, String> redisData = new HashMap<Double, String>();
        
         if (jedis.llen("state") == 0 && jedis.llen("number") == 0)  {
             //jedis.zadd("faac", (Map) Info.map);
             for(Map.Entry m: Info.map.entrySet()){
                 
                 jedis.lpush("state",(String)m.getValue());
                 jedis.lpush("number",m.getKey().toString());
                 
             }
         } else {
         }
        for(String s: jedis.lrange("state", 0, 100)){
            names.add(s);
                       
          }
         for(String r: jedis.lrange("number", 0, 100)){
               value.add(r);
            }
         for(int i =0; i < names.size(); i++){
             redisData.put(Double.parseDouble(value.get(i)), names.get(i));
         }
//         for(Tuple t: jedis.zrangeByScoreWithScores("faac", 0, 100)){
//        System.out.println(t.getScore());
//        redisData.put(t.getScore(), t.getElement());
//        }
         
        ArrayList<String> states = new ArrayList<String>();
         for (Map.Entry m : redisData.entrySet()) {
             states.add((String)m.getValue());
         }
         
         String[] statesArray = new String[states.size()];
         states.toArray(statesArray);

        

        JComboBox<String> stateList = new JComboBox<>(statesArray);
        stateList.addItemListener(new Handler());
       // stateList.addItemListener(null);

// add to the parent container (e.g. a JFrame):
        JFrame jframe = new JFrame();
        JLabel item1 = new JLabel("OUT OF SCHOOL CHILDREN 2013-2014");
        //item1.setToolTipText("By Ajiniran winifred");
        jframe.add(item1);
        
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setLayout(new FlowLayout());
        jframe.setSize(250,180);
        jframe.setVisible(true);
        
        jframe.add(stateList);
        
        

// get the selected item:
       // String selectedBook = (String) stateList.getSelectedItem();
       

        // check whether the server is running or not
        System.out.println("Server is running: " + jedis.ping());
        //getting the percentage for each state
       

        // storing the data into redis database
      
        
        for (Map.Entry m : Info.map.entrySet()) {
            System.out.println(m.getKey() + " " + m.getValue());

  
        }
    }
    private static class Handler implements ItemListener{
//

//           JOptionPane.showMessageDialog(null, String.format("%s", e.getActionCommand()));
//        }

        @Override
        public void itemStateChanged(ItemEvent e) {
             for (Map.Entry m : redisData.entrySet()) {
if(e.getItem().toString() == m.getValue()&& e.getStateChange() == 1){
                     
                     JOptionPane.showMessageDialog(null, m.getKey(), "VALUE IN THOUSANDS", 1);
                     //JOptionPane.showMessageDialog(null, m.getKey());
                     System.out.println(m.getKey());
                     break;
                     
                 }
          //  System.out.println(m.getKey() + " " + m.getValue());

            //jedis.llen("faac", M)
        }
       //     System.out.println(e.getItem().toString());
        }
        
    }

}
