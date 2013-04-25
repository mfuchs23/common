/* 
 * ### Copyright (C) 2008 Michael Fuchs ###
 * ### All Rights Reserved.             ###
 *
 * Author: Michael Fuchs
 * E-Mail: michael.fuchs@unico-group.com
 * URL:    http://www.michael-a-fuchs.de
 */
package org.dbdoclet.progress;

/**
 * Erweitert die Klasse {@link ProgressEvent} um die Informationen einen
 * mehrstufigen Fortschritt verwalten zu können.  Die Konstruktoren entsprechen
 * denen der Mutterklasse. Die zusätzlichen Informationen werden der Klasse mit
 * den Akzessoren {@link MultiStepProgressEvent#setActualStepIndex()} {@link
 * MultiStepProgressEvent#setMaxStepCount(int)} und {@link
 * MultiStepProgressEvent#setStepName(String)} bekannt gemacht. Diese Setter
 * geben das Objekt selbst zurück, um Method-Chaining zu erlauben
 */
public class MultiStepProgressEvent extends ProgressEvent {

    private int    maxStepCount;
    private int    actualStepIndex;
    private String stepName = "";

    private void defaultInit() {
        this.setMaxStepCount(1).setActualStepIndex(1).setStepName("");
    }

    public MultiStepProgressEvent() {
        super(-1, -1, "", "", true);
        this.defaultInit();
    }

    public MultiStepProgressEvent(int max, int index, String item, String action) {
        super(max, index, item, action);
        this.defaultInit();
    }

    public MultiStepProgressEvent(int max, int index) {
        super(max, index);
        this.defaultInit();
    }

    public MultiStepProgressEvent(int index, String item, String action) {
        super(index, item, action);
        this.defaultInit();
    }

    public MultiStepProgressEvent(int max, int index, String item, String action, boolean consider) {
        super(max, index, item, action, consider);
        this.defaultInit();
    }

    public MultiStepProgressEvent(String action, boolean consider) {
        super(action, consider);
        this.defaultInit();
    }

    public MultiStepProgressEvent(String item, String action) {
        super(item, action);
        this.defaultInit();
    }

    public MultiStepProgressEvent(String action) {
        super(action);
        this.defaultInit();
    }

    public int getMaxStepCount() {
        return maxStepCount;
    }

    public MultiStepProgressEvent setMaxStepCount(int maxStepCount) {
        this.maxStepCount = maxStepCount;
        return this;
    }

    public int getActualStepIndex() {
        return actualStepIndex;
    }

    public MultiStepProgressEvent setActualStepIndex(int actualStepIndex) {
        this.actualStepIndex = actualStepIndex;
        return this;
    }

    public String getStepName() {
        return stepName;
    }

    public MultiStepProgressEvent setStepName(String stepName) {
        this.stepName = stepName;
        return this;
    }

}
