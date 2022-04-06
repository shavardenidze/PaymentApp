package ge.lshavardenidze.paymentapp.servicies.modelmapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DTOMapperServiceImpl implements DTOMapperService {

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public <T> T mapObject(Object incomingObject, Class<T> transformedObject) {
        if (incomingObject == null) {
            return null;
        }
        return modelMapper.map(incomingObject, transformedObject);
    }

    @Override
    public <T, Y> List<T> mapObjects(List<Y> incomingObjects, Class<T> transformedObject) {
        if (incomingObjects == null) {
            return null;
        }
        List<T> result = new ArrayList<>();
        incomingObjects.forEach(item -> result.add(mapObject(item, transformedObject)));
        return result;
    }


}
