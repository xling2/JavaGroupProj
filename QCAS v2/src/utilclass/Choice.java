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
public class Choice {
    private String content;
    private String correct;
    // match the content and correct
    public Choice(String cont, String corr){
        this.content = cont;
        this.correct = corr;
    }
    // get content of the choice
    public String getContent(){
        return this.content;
    }
    // get correct of the choice
    public String getCorrect(){
        return this.correct;
    }
}
