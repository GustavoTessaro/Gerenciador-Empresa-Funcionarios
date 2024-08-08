/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Main;

import Controller.Controller;

/**
 *
 * @author alunolages
 */
public class Main {

    public static Controller controller;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        controller = new Controller(); 
        controller.start();
        
    }
    
}
