/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package threads.task1_task2_racing;

/**
 *
 * @author Kostadinova
 */
public class CarRacer {

    private String name;

    public CarRacer(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return "CarRacer{" + "name=" + name + '}';
    }

}
