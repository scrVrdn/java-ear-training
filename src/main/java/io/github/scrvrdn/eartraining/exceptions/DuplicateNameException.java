package io.github.scrvrdn.eartraining.exceptions;

public class DuplicateNameException extends RuntimeException {
    public DuplicateNameException(String name) {
        super("A preset with the name " + name + " already exists.");
    }
}
