package com.bowencraft.hexoland.utils;

import com.bowencraft.hexoland.Hexoland;

public class HexAlgs {
    
    private static Hexoland plugin;
    
    public static int[][] hexShape = {
        {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,0,0,0,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
        {-1,-1,-1,-1,-1,-1,-1,-1,0,0,0,1,0,0,0,-1,-1,-1,-1,-1,-1,-1,-1},
        {-1,-1,-1,-1,-1,-1,0,0,0,1,1,1,1,1,0,0,0,-1,-1,-1,-1,-1,-1},
        {-1,-1,-1,-1,-1,0,0,1,1,1,1,1,1,1,1,1,0,0,-1,-1,-1,-1,-1},
        {-1,-1,-1,0,0,0,1,1,1,1,1,1,1,1,1,1,1,0,0,0,-1,-1,-1},
        {-1,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,-1},
        {0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0},
        {0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0},
        {0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0},
        {0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0},
        {0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0},
        {0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0},
        {0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0},
        {0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0},
        {0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0},
        {0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0},
        {0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0},
        {0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0},
        {0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0},
        {0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0},
        {0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0},
        {-1,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,-1},
        {-1,-1,-1,0,0,0,1,1,1,1,1,1,1,1,1,1,1,0,0,0,-1,-1,-1},
        {-1,-1,-1,-1,-1,0,0,1,1,1,1,1,1,1,1,1,0,0,-1,-1,-1,-1,-1},
        {-1,-1,-1,-1,-1,-1,0,0,0,1,1,1,1,1,0,0,0,-1,-1,-1,-1,-1,-1},
        {-1,-1,-1,-1,-1,-1,-1,-1,0,0,0,1,0,0,0,-1,-1,-1,-1,-1,-1,-1,-1},
        {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,0,0,0,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1}
    
    
    };
    
    public HexAlgs(Hexoland plugin) {
        this.plugin = plugin;
    }
    
    public static int[] relativeToOffset(double ix, double iy) {
        
        int[] offsetHex = new int[3];
        
        int[][] hexBorder = {
            {5,5,4,4,3,2,2,1,1,0,0,0,1,1,2,2,3,4,4,5,5,6},
            {8,7,7,6,6,5,4,4,3,3,2,3,3,4,4,5,6,6,7,7,8,8},
            {0,1,1,2,2,3,4,4,5,5,6,5,5,4,4,3,2,2,1,1,0,0},
            {3,3,4,4,5,6,6,7,7,8,8,8,7,7,6,6,5,4,4,3,3,2}
            
        };
        
        boolean inHex = true;
        
        int x = (int)Math.floor(ix);
        int y = (int)Math.floor(iy);
        
        int qx = x / 22; // x 商
        int rx = x % 22; // x 余数
        
        int qy = y / 39;
        int ry = y % 39;
        
        if (rx<0) {
            qx -= 1;
            rx += 22;
        }
        if (ry<0) {
            qy -= 1;
            ry += 39;
        }
        
        int qhx = qx;
        int qhy = 2*qy;
        
        
        if (ry<6) {
            if(rx == 11){
                inHex = false;
            } else {
                inHex = true;
                
                qhy += 0;
                if(rx<11) {
                    qhx +=0;
                } else if(rx>=12) {
                    qhx+=1;
                }
            }
        } else if (ry>=33) {
            if(rx == 11){
                inHex = false;
            } else {
                inHex = true;
                
                qhy += 2;
                if(rx<11) {
                    qhx +=0;
                } else if(rx>=12) {
                    qhx+=1;
                }
            }
        } else if (ry>=13&&ry<26) {
            if (rx>=0&&rx<1) {
                inHex = false;
            } else {
                inHex = true;
                qhy += 1;
            }
        } else if (ry>=6&&ry<13) {
            ry -= 6;
            if (ry<hexBorder[0][rx]) {
                inHex = true;
                qhy += 0;
                
                if(rx<10) {
                    qhx+=0;
                }
                else if(rx>=11) {
                    qhx+=1;
                } else {
                    inHex = false;
                }
                
            } else if (ry>=hexBorder[1][rx]) {
                qhy +=1;
                inHex = true;
            } else {
                inHex = false;
            }
        } else if(ry>=26&&ry<33) {
            ry -=26;
            if (ry<hexBorder[2][rx]) {
                inHex = true;
                qhy += 1;
            } else if (ry >= hexBorder[3][rx]) {
                inHex = true;
                qhy += 2;
                if (rx<10) {
                    qhx += 0;
                } else if (rx>=11) {
                    qhx += 1;
                    
                }
            } else {
                inHex = false;
            }
        }
        
        if (inHex) {
            offsetHex[2] = 1;
        } else {
            offsetHex[2] = 0;
        }
        
        offsetHex[0] = qhx;
        offsetHex[1] = qhy;
        return offsetHex;
        
    }
    
    public static int[] offsetToCentralXY(int x, int y){
        int[] central = {0,0};
        int[] offset = {x,y};
        
        if (offset[1]%2==0){
            central[0]=offset[0]*22;
        } else {
            central[0]=offset[0]*22+11;
        }
        
        central[1] = offset[1]*20; // Y轴
        
        return central;
    }
    
    
    public static int[] offsetToLTXY(int x, int y){
        int[] central = offsetToCentralXY(x,y);
        int[] ltxy = {0,0};
    
        ltxy[0] = central[1] -13;
        ltxy[1] = central[0] -11;
        
        return ltxy;
    }
    public static boolean isInHex(int x, int y) {
        int[] offset = {0,0,0};
        offset = relativeToOffset(x,y);
        if (offset[2] == 0) {
            return false;
        } else {
            return true;
        }
    }
    
}
