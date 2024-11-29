package com.Royal.game.Screens;

import com.Royal.game.Elements.Bird_data;
import com.Royal.game.Elements.Block_data;
import com.Royal.game.Elements.Pig_data;

import java.io.Serializable;
import java.util.ArrayList;

public class Data implements Serializable {
    public ArrayList<Bird_data> birds_data=new ArrayList<Bird_data>();
    public ArrayList<Pig_data> pigs_data=new ArrayList<Pig_data>();
    public ArrayList<Block_data> blockData=new ArrayList<Block_data>();
}

