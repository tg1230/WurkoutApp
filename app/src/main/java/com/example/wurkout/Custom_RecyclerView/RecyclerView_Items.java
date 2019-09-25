package com.example.wurkout.Custom_RecyclerView;

public class RecyclerView_Items {

    private int image1;
    private int image2;
    private String text1;
    private String text2;
    private double maxRep;
    private double maxWeight;
    private int workoutDays;
    private String workoutDescription;
    private int reps;
    private int sets;
    private int position;
    private String image;

    public RecyclerView_Items(int zimage1, int zimage2, String ztext1, String ztext2) {
        image1 = zimage1;
        image2 = zimage2;
        text1 = ztext1;
        text2 = ztext2;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String s) {
        image = s;
    }

    public void changeText1(String ztext1) {
        text1 = ztext1;
    }

    public void changeText2(String ztext1) {
        text2 = ztext1;
    }

    public void setMaxRep(double maxr) {
        maxRep = maxr;
    }

    public void setMaxWeight(double maxw) {
        maxWeight = maxw;
    }

    public void setReps(int r) {
        reps = r;
    }

    public void setSets(int s) {
        sets = s;
    }

    public void setWorkoutDescription(String wdescription) {
        workoutDescription = wdescription;
    }

    public void setWorkoutDays(int wdays) {
        workoutDays = wdays;
    }

    // identifier will be the initial position the item starts at
    public void setIdentifier(int pos) {
        position = pos;
    }

    public int getIdentifier() {
        return position;
    }

    public String getWorkoutDescription() {
        return workoutDescription;
    }

    public int getImage1() {
        return image1;
    }

    public int getImage2() {
        return image2;
    }

    public int getWorkoutDays() {
        return workoutDays;
    }

    public double getMaxRep() {
        return maxRep;
    }

    public double getMaxWeight() {
        return maxWeight;
    }

    public int getReps() {
        return reps;
    }

    public int getSets() {
        return sets;
    }

    public String getText1() {
        return text1;
    }

    public String getText2() {
        return text2;
    }
}
