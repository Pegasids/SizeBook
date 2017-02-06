package com.example.sizebook;


import java.io.Serializable;

/**
 * Created by Canopy on 2017-01-20.
 *
 * This class for records.
 * It implements Serializable so getSerializableExtra can be used by activities to retrieve objects.
 * It includes setters and getters for encapsulation.
 */

public class Records implements Serializable {

    private String name, date, comment;
    private Float neck, bust, chest,  waist, hip, inseam;

    public Records(String name, String date, String comment, Float neck, Float bust, Float chest, Float waist, Float hip, Float inseam) {
        this.name = name;
        this.date = date;
        this.comment = comment;
        this.neck = neck;
        this.bust = bust;
        this.chest = chest;
        this.waist = waist;
        this.hip = hip;
        this.inseam = inseam;
    }

    /**
     * Get object's name.
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Set object's name.
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get object's date.
     * @return
     */
    public String getDate() {
        return date;
    }

    /**
     * Set object's date.
     * @param date
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Get object's comment.
     * @return
     */
    public String getComment() {
        return comment;
    }

    /**
     * Set object's comment.
     * @param comment
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * Get object's neck dimension.
     * @return
     */
    public float getNeck() {
        return neck;
    }

    /**
     * Set object's neck dimension.
     * @param neck
     */
    public void setNeck(float neck) {
        this.neck = neck;
    }

    /**
     * Get object's bust dimension.
     * @return
     */
    public float getBust() {
        return bust;
    }

    /**
     * Set object's bust dimension.
     * @param bust
     */
    public void setBust(float bust) {
        this.bust = bust;
    }

    /**
     * Get object's chest dimension.
     * @return
     */
    public float getChest() {
        return chest;
    }

    /**
     * Set object's chest dimension.
     * @param chest
     */
    public void setChest(float chest) {
        this.chest = chest;
    }

    /**
     * Get object's waist dimension.
     * @return
     */
    public float getWaist() {
        return waist;
    }

    /**
     * Set object's waist dimension.
     * @param waist
     */
    public void setWaist(float waist) {
        this.waist = waist;
    }

    /**
     * Get object's hip dimension.
     * @return
     */
    public float getHip() {
        return hip;
    }

    /**
     * Set object's hip dimension.
     * @param hip
     */
    public void setHip(float hip) {
        this.hip = hip;
    }

    /**
     * Get object's inseam dimension.
     * @return
     */
    public float getInseam() {
        return inseam;
    }

    /**
     * Set object's inseam dimension.
     * @param inseam
     */
    public void setInseam(float inseam) {
        this.inseam = inseam;
    }

    /**
     * Binding Android ListView with Custom Objects using ArrayAdapter.
     * Credit- Waqas Anwar
     * Copyright @ 2017 EzzyLearning.com
     * url: http://www.ezzylearning.com/tutorial/binding-android-listview-with-custom-objects-using-arrayadapter
     * @return
     */
    @Override
    public String toString() {
        return getName();
    }
}

