package ge.lshavardenidze.paymentapp.servicies.modelmapper;

import java.util.List;

public interface DTOMapperService {

    <T> T mapObject(Object incomingObject, Class<T> result);

    <T, Y> List<T> mapObjects(List<Y> incomingObjects, Class<T> transformedObject);
}
