/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eci.arsw.draw.gui.shapes;

import eci.arsw.draw.model.Ellipse;
import java.awt.Graphics2D;

/**
 *
 * @author fchaves
 */
public interface EllipseRenderer {
    public void draw(Graphics2D g,Ellipse ellipse);
}
