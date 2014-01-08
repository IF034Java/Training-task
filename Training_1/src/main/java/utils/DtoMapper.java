package utils;

import java.util.LinkedList;
import java.util.List;

import org.modelmapper.ModelMapper;

public class DtoMapper<Entity, Dto> {

	private Class<Dto> clazz;
	
	public DtoMapper(Class<Dto> clazz) {
		this.clazz = clazz;
	}

	public List<Dto> map(List<Entity> entities){
    	ModelMapper mapper = new ModelMapper();
    	List<Dto> dtos = new LinkedList<Dto>();
    	for (Entity entity : entities){
    		Dto dto = mapper.map(entity, clazz);    		
    		dtos.add(dto);    		
    	}
    	
    	return dtos;
    }

}
