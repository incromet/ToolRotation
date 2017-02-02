/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eci.arsw.draw.gui;

import eci.arsw.draw.controller.IController;
import eci.arsw.draw.model.Shape;
import eci.arsw.pattern.observer.Observer;

import java.util.List;

import javax.swing.JLabel;


/**
 *
 * @author fchaves
 */
public class ShapeNumber extends JLabel implements Observer {

    private IController controller;
    
    public ShapeNumber(IController controller) {
        super(" ");
        this.controller = controller;
        this.controller.addObserver(this);
    }
    
    @Override
    public void update() {
        List<Shape> shapes = controller.getShapes();
        this.setText("Number of shapes: "+shapes.size());
    }
}
