package com.techtalk.usersservice.model.mappers;

import com.techtalk.usersservice.model.request.PodcasterRequestDto;
import com.techtalk.usersservice.model.response.PodcasterResponseDto;
import com.techtalk.usersservice.persistence.Podcaster;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PodcasterMapper {

    PodcasterMapper INSTANCE = Mappers.getMapper( PodcasterMapper.class );

    Podcaster PodcasterReqDto_to_Podcaster(PodcasterRequestDto podcasterRequestDto);

    PodcasterResponseDto Podcaster_to_PodcasterResponse(Podcaster podcaster);

    List<PodcasterResponseDto> LIST_PODCASTER_TO_LIST_PODCASTER_RESPONSE_DTO(List<Podcaster> podcasters);
}
