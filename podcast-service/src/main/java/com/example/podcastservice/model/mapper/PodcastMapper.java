package com.example.podcastservice.model.mapper;

import com.example.podcastservice.model.request.PodcasterRequestDto;
import com.example.podcastservice.model.response.PodcastResponseDto;
import com.example.podcastservice.persistence.Podcast;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring") public interface PodcastMapper {

    PodcastMapper INSTANCE = Mappers.getMapper( PodcastMapper.class );


    Podcast PODCAST_REQUEST_DTO_TO_PODCAST(PodcasterRequestDto podcasterRequestDto);

    PodcastResponseDto PODCAST_TO_PODCAST_RESPONSE_DTO(Podcast podcast);

    List<PodcastResponseDto> LIST_PODCAST_TO_LIST_PODCAST_RESPONSE_DTO(List<Podcast> podcasts);
}
