/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package rs.ac.bg.fon.app.mapper;

import rs.ac.bg.fon.app.dto.DomainEntityDto;
import rs.ac.bg.fon.app.entity.DomainEntity;

/**
 *
 * @author SARA
 */
public interface BaseMapper <DTO extends DomainEntityDto, DB extends DomainEntity> {

    public DTO toDomainDTO(DB DomainEntity);

    public DB toDomainEntity(DTO DomainDTO);
    
}
