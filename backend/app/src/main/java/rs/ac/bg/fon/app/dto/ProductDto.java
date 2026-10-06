/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.app.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import rs.ac.bg.fon.app.entity.SupplementForm;

/**
 *
 * @author SARA
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductDto implements DomainEntityDto{
    private Long id;
    private String name;
    private String description;
    private double price;
    private int stock;
    private SupplementForm form;
    private String imageUrl;
    private CategoryDto category;
}
