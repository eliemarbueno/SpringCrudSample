package br.com.ebueno.stockcontrol.common.v1.util;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

@Component
public class MappingService {
    private final ModelMapper modelMapper;
    public MappingService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    /***
     * Converts an object of type S to an object of type D using ModelMapper.
     * @param <S> source type to be converted
     * @param <D> destination type to be converted
     * @param source the source object to be converted
     * @param destinationType the class type of the destination object
     * @return an instance of type D that is a converted version of the source object
     */
    public <S, D> D map(S source, Class<D> destinationType) {
        if (source == null) {
            return null;
        }
        return modelMapper.map(source, destinationType);
    }

    /***
     * Converts a list of objects of type S to a list of objects of type D using ModelMapper.
     * @param <S> source type to be converted
     * @param <D> destination type to be converted
     * @param sourceList the list of source objects to be converted
     * @param destinationType the class type of the destination objects
     * @return a list of instances of type D that are converted versions of the source objects
     */
    public <S, D> List<D> mapList(List<S> sourceList, Class<D> destinationType) {
        if (sourceList == null || sourceList.isEmpty()) {
            return null;
        }
        return sourceList.stream().map(source -> map(source, destinationType)).collect(Collectors.toList());
    }

    /***
     * Converts a Page of objects of type S to a Page of objects of type D using ModelMapper.
     * @param <S> source type to be converted
     * @param <D> destination type to be converted
     * @param sourcePage the Page of source objects to be converted
     * @param destinationType the class type of the destination objects
     * @return a Page of instances of type D that are converted versions of the source objects
     */
    public <S, D> Page<D> mapPage(Page<S> sourcePage, Class<D> destinationType) {
        if (sourcePage == null) {
            return null;
        }
        List<D> content = mapList(sourcePage.getContent(), destinationType);
        return new PageImpl<>(content, sourcePage.getPageable(), sourcePage.getTotalElements());
    }

    /***
     * Merge a DTO object into an existing entity object using ModelMapper.
     * This is useful for updating an entity with new values from a DTO.
     * @param <S>
     * @param <D>
     * @param source
     * @param destination
     * @return
     */
    public <S, D> D merge(S source, D destination) {
        if (source == null || destination == null) {
            throw new IllegalArgumentException("Source and destination must not be null");
        }
        modelMapper.map(source, destination);
        return destination;
    }
}
