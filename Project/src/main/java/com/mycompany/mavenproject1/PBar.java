package com.mycompany.mavenproject1;
import javax.swing.*;

public class PBar extends Thread {
    ScanProgressBar pbar;
    
    PBar(ScanProgressBar pbar) {
        this.pbar = pbar;
    }
    
    public void run() {
        int min = 0;
        int max = 100;
        
        pbar.jProgressBar1.setMaximum(max);
        pbar.jProgressBar1.setMinimum(min);
        pbar.jProgressBar1.setValue(0);
        
        for(int i = min; i <= max; i++) {
            pbar.jProgressBar1.setValue(i);
            
            try {
                sleep(10);
            } catch(InterruptedException e) {
                System.out.println("Something went wrong");
            }
        }
        JOptionPane.showMessageDialog(null,"Scan is complete");  
        this.pbar.dispose();
    }
}
