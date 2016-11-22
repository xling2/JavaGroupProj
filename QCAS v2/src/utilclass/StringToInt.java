/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilclass;

/**
 *
 * @author lingxingyu
 */
public class StringToInt {
    
    public int toIntType(String questionType){
        int type = 0;
        
        if(questionType.equals("MA")){
            type =  0;
        }
        else if(questionType.equals("MC")){
            type =  1;
        }
        else if(questionType.equals("TF")){
            type = 2;
        }
        else
            type = 3;
        
        return type;
    }
    public int toIntDiff(String questionDiff){
        int diff = 0;
        
        if(questionDiff.equals("E")){
            diff =  0;
        }
        else if(questionDiff.equals("M")){
            diff =  1;
        }
        else if(questionDiff.equals("H")){
            diff = 2;
        }
        else
            diff = 3;
        
        return diff;
}
}
