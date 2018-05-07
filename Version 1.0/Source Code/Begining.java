package algorithm_project;

import java.awt.EventQueue;

public class Begining {

    public static void main(String[] args) {
        
        EventQueue.invokeLater(new Runnable(){
            @Override
            public void run() {
            
                new Main();
            }
        });
    }    
}