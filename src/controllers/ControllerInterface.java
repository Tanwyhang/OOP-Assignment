// interface for controller classes to hide the implementation details
// and provide a common interface for all controllers

package controllers;

import java.util.List;

public interface ControllerInterface<T> {
    void saveToFile();
    void loadFromFile();
    List<T> getAll();
}



