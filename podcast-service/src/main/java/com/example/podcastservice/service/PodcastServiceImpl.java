package com.example.podcastservice.service;

import com.example.podcastservice.client.UserService;
import com.example.podcastservice.model.mapper.PodcastMapper;
import com.example.podcastservice.model.request.PodcasterRequestDto;
import com.example.podcastservice.model.request.UpdateStatus;
import com.example.podcastservice.model.response.PodcastResponseDto;
import com.example.podcastservice.model.response.Podcaster;
import com.example.podcastservice.persistence.Podcast;
import com.example.podcastservice.persistence.PodcastRepository;
import com.example.podcastservice.utils.FileService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PodcastServiceImpl implements PodcastService{

    private FileService fileService;
    private PodcastMapper podcastMapper;
    private PodcastRepository podcastRepository;
    private UserService userService;

    public PodcastServiceImpl(FileService fileService, PodcastMapper podcastMapper, PodcastRepository podcastRepository, UserService userService) {
        this.fileService = fileService;
        this.podcastMapper = podcastMapper;
        this.podcastRepository = podcastRepository;
        this.userService = userService;
    }

    @Override
    public PodcastResponseDto addPodcast(PodcasterRequestDto podcasterRequestDto,String email)   {
        // GET THE PODCASTER FROM THE USERS-SERVICES
        Podcaster user = this.userService.getUser(email);

        // UPLOAD THE AUDIO AND THE COVER IMAGE TO FIREBASE DB
        Object audioUpload = this.fileService.upload(podcasterRequestDto.getFileAudio());
        Object picUpload = this.fileService.upload(podcasterRequestDto.getPicture());

        Podcast podcast = this.podcastMapper.PODCAST_REQUEST_DTO_TO_PODCAST(podcasterRequestDto);
        podcast.setAudio(podcasterRequestDto.getFileAudio().getOriginalFilename());

        if(audioUpload instanceof String audioName) podcast.setAudio(audioName);
        if(picUpload instanceof String picName) podcast.setPic(picName);

        podcast.setPodcasterId(user.getId());
        podcast.setPodcaster_name(user.getName());
        podcast.setStatus(0);
        podcast.setDate(LocalDate.now());
        this.podcastRepository.save(podcast);
        PodcastResponseDto podcastResponseDto = this.podcastMapper.PODCAST_TO_PODCAST_RESPONSE_DTO(podcast);
        podcastResponseDto.setPodcaster(user);
        return podcastResponseDto;
    }

    @Override
    public List<PodcastResponseDto> getPodcastsWithPagination(int page, int size,int status) {
        Pageable pageable = PageRequest.of(page,size);
        Page<Podcast> podcasts = this.podcastRepository.findByStatus(status,pageable);
        List<Podcast> content = podcasts.getContent();
        List<PodcastResponseDto> podcastResponseDtos = this.podcastMapper.LIST_PODCAST_TO_LIST_PODCAST_RESPONSE_DTO(content);
        return podcastResponseDtos;
    }

    @Override
    public PodcastResponseDto getSinglePodcast(Long id) {
        Optional<Podcast> podcast = this.podcastRepository.findById(id);
        if(podcast.isPresent()){
            Podcaster podcasterById = userService.getPodcasterById(podcast.get().getPodcasterId());
            PodcastResponseDto podcastResponseDto = this.podcastMapper.PODCAST_TO_PODCAST_RESPONSE_DTO(podcast.get());
            podcastResponseDto.setPodcaster(podcasterById);
            return podcastResponseDto;
        }
        return null;
    }

    @Override
    public int updatePodcastStatus(UpdateStatus updateStatus) {
        Optional<Podcast> podcast = this.podcastRepository.findById(updateStatus.getId());
        if(podcast.isPresent()){
             Podcast newPodcast = podcast.get();
             newPodcast.setStatus(updateStatus.getStatus());
             if(updateStatus.getStatus()==-1) newPodcast.setRejectedMessage(updateStatus.getRejectedMessage());
             this.podcastRepository.save(newPodcast);
             return 1;
        }
        return 0;
    }

    @Override
    public List<PodcastResponseDto> getOnwPodcasts(String email,int page, int size) {
        Pageable pageable = PageRequest.of(page,size);
        Podcaster user = this.userService.getUser(email);
        Page<Podcast> podcasts = this.podcastRepository.findByPodcasterIdAndStatusNot(user.getId(),-3,pageable);

        List<PodcastResponseDto> podcastResponseDtos = this.podcastMapper.LIST_PODCAST_TO_LIST_PODCAST_RESPONSE_DTO(podcasts.getContent());
        return podcastResponseDtos;
    }

    @Override
    public PodcastResponseDto updatePodcast(PodcasterRequestDto podcasterRequestDto,Long id) {
        Optional<Podcast> podcast = this.podcastRepository.findById(id);
        if(podcast.isPresent()){
            if(podcasterRequestDto.getDescription()!=null) podcast.get().setDescription(podcasterRequestDto.getDescription());
            if(podcasterRequestDto.getTitle()!=null) podcast.get().setTitle(podcasterRequestDto.getTitle());
            if(podcasterRequestDto.getFileAudio()!=null){
                Object upload = this.fileService.upload(podcasterRequestDto.getFileAudio());
                if(upload instanceof String audioName) podcast.get().setAudio(audioName);
            }
            if(podcasterRequestDto.getPicture()!=null){
                Object upload = this.fileService.upload(podcasterRequestDto.getPicture());
                if(upload instanceof String pictureName) podcast.get().setPic(pictureName);
            }
            Podcast save = this.podcastRepository.save(podcast.get());
            PodcastResponseDto podcastResponseDto = this.podcastMapper.PODCAST_TO_PODCAST_RESPONSE_DTO(save);
            return podcastResponseDto;
        }
        return null;
    }

    @Override
    public List<PodcastResponseDto> getPodcasterPodcasts(Long podcasterId, int page, int size) {
        Pageable pageable = PageRequest.of(page,size);
        Podcaster user = this.userService.getPodcasterById(podcasterId);

        Page<Podcast> podcasts = this.podcastRepository.findPodcastByPodcasterIdAndStatus(user.getId(),1,pageable);

        List<PodcastResponseDto> podcastResponseDtos = this.podcastMapper.LIST_PODCAST_TO_LIST_PODCAST_RESPONSE_DTO(podcasts.getContent());
        return podcastResponseDtos;
    }
}
