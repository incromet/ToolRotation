/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eci.pdsw.draw.controller;

import eci.pdsw.pattern.command.*;
import eci.pdsw.draw.gui.shapes.Renderer;
import eci.pdsw.draw.model.ElementType;
import eci.pdsw.draw.model.ShapeFactory;
import eci.pdsw.draw.model.Point;
import eci.pdsw.draw.model.Shape;
import eci.pdsw.pattern.observer.Observer;
import java.awt.Dimension;
import java.awt.Toolkit;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Stack;
import javax.naming.OperationNotSupportedException;

/**
 *
 * @author fchaves
 */
public class Controller implements IController {
    private ElementType selectedElement = ElementType.Line;
    
    private final ShapeFactory shapeFactory = new ShapeFactory();
    private final List<Shape> shapes = new ArrayList<>();
    
    private List<Observer> observers = new ArrayList<>();
        
    private Renderer renderer;
    
    private Stack<Command> ReHacer=new Stack<Command>();
    
    private Stack<Command> DesHacer=new Stack<Command>();
    
    public Controller() {
    }
    
    @Override
    public void addShapeFromScreenPoints(java.awt.Point p1,java.awt.Point p2) {
        Point mp1 = Point.newPoint(new Float(p1.x), new Float(p1.y));
        Point mp2 = Point.newPoint(new Float(p2.x), new Float(p2.y));
                
        ElementType actualElementType = getSelectedElementType();
    	setSelectedElementType(actualElementType);
        addShape(mp1, mp2);     
        

    }

    
     /**
     * Duplica todas las figuras, y las ubica en una nueva posicion.
     * @pre la coleccion 'shapes' no tiene referencias duplicadas
     * @pos la coleccion 'shapes' contiene el doble de figuras
     * @pos la coleccion 'shapes' no tiene referencias duplicadas
     */
    @Override
    public void duplicateShapes(){
        int particion=shapes.size();
        List<Point> newShapesFirstPoints=new LinkedList<>();
        List<Point> newShapesSecondPoints=new LinkedList<>();
        List<Shape> nuevas=new ArrayList<>(); 
        int displacementDelta=10+new Random(System.currentTimeMillis()).nextInt(50);        
        
        for (Shape s:shapes){
            newShapesFirstPoints.add(new Point(s.getPoint1().getX(),s.getPoint1().getY()+displacementDelta));
            newShapesSecondPoints.add(new Point(s.getPoint2().getX(),s.getPoint2().getY()+displacementDelta));
        }
        Iterator<Point> it1=newShapesFirstPoints.iterator();
        Iterator<Point> it2=newShapesSecondPoints.iterator();
        
        while (it1.hasNext() && it2.hasNext()){
            //addShape(it1.next(), it2.next());
            Shape sh=shapeFactory.createShape(selectedElement,it1.next(), it2.next());
            shapes.add(sh);
            nuevas.add(sh);        
            notifyObservers();
        }
        DesHacer.push(new Duplicate(this,nuevas));       
    }
  
    
   
    
    @Override
    public void undo() {
    	if (!DesHacer.isEmpty()){
    		Command com=DesHacer.pop();
    		System.out.println("Deshacer "+DesHacer.size());
    		com.undo();    	
    		ReHacer.push(com);
      	} 	
    	
    }

    @Override
    public void redo() {
    	if(!ReHacer.isEmpty()){
    		Command com=ReHacer.pop();
    		System.out.println("Rehacer "+ReHacer.size());
    		com.redo();    		
    		DesHacer.push(com);
    	}
    }
    
    @Override
    public void addShape(Point p1,Point p2) {  
    	Shape sh=shapeFactory.createShape(selectedElement, p1, p2);
        shapes.add(sh);        
        DesHacer.push(new Draw(this, sh));
        notifyObservers();
    }

    @Override
    public void addShape(Integer index, Shape shape) {       
    	shapes.add(index,shape);              
        notifyObservers();
    }   
    
    public void addShape(Shape s) {
    	shapes.add(s);
    	 notifyObservers();
    }
    
    @Override
    public void deleteShape(Integer index) {
        int idx = index;           
        shapes.remove(idx);        
        //notificar a la capa de presentación
        notifyObservers();
    }
    
    /**
     * Rota la figura correspondiente a la posicion 'index' un angulo
     * de 90 grados a la derecha, usando como eje de rotación la esquina
     * inferior izquierda del rectángulo que contenga a la figura.
     * @param index la posicion de la figura en el conjunto de figuras
     * del controlador
     */
  
    public void rotateSelectedShape(Integer index) {
    	shapes.get(index).rotate();    
        notifyObservers();        
    }    
    
    
    @Override
    public void setRenderer(Renderer renderer) {
    	this.renderer = renderer;
    	notifyObservers();
    }
    
    @Override
    public Renderer getRenderer() {
    	return this.renderer;
    }

    
    @Override
    public List<Shape> getShapes() {
        return shapes;
    }
    
    @Override
    public void setSelectedElementType(ElementType elementType) {
        this.selectedElement = elementType;
    }  
    
    @Override
    public ElementType getSelectedElementType() {
        return this.selectedElement;
    }

    @Override
    public void addObserver(Observer o) {
            observers.add(o);
    }

    @Override
    public void deleteObserver(Observer o) {
            observers.remove(o);
    }

    @Override
    public void notifyObservers() {
            for(Observer o : observers) {
                    o.update();
            }
    }


}
