/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * 
 */
@Getter
@Setter
public class Tag {
    private int id;
    private String nome;

    public Tag(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }
    
             
    
}
