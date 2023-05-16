package com.example.podcastservice.rest_controller;

import com.example.podcastservice.model.request.PodcasterRequestDto;
import com.example.podcastservice.model.request.UpdateStatus;
import com.example.podcastservice.model.response.PodcastResponseDto;
import com.example.podcastservice.service.PodcastService;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.websocket.server.PathParam;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
public class PodcastRestController {


    private PodcastService podcastService;

    public PodcastRestController(PodcastService podcastService) {
        this.podcastService = podcastService;
    }

    @PostMapping("podcasts")
    PodcastResponseDto addPodcast(@ModelAttribute PodcasterRequestDto podcasterRequestDto,Principal principal) {
        PodcastResponseDto podcastResponseDto = this.podcastService.addPodcast(podcasterRequestDto,principal.getName());
        return podcastResponseDto;
    }

    @GetMapping("podcasts")
    List<PodcastResponseDto> getPodcasts(@PathParam("page") int page,@PathParam("size") int size,@PathParam("status") int status){
        List<PodcastResponseDto> podcasts = this.podcastService.getPodcastsWithPagination(page, size,status);
        return podcasts;
    }

    @GetMapping("podcasts/{id}")
     PodcastResponseDto getSinglePodcast(@PathVariable @NotNull @DecimalMin("0") Long id){
        PodcastResponseDto podcast = this.podcastService.getSinglePodcast(id);
        return podcast;
    }

    @PutMapping("podcasts/{id}")
    PodcastResponseDto updatePodcast(@ModelAttribute PodcasterRequestDto podcasterRequestDto,@PathVariable @NotNull @DecimalMin("0") Long id){
        PodcastResponseDto podcastResponseDto = this.podcastService.updatePodcast(podcasterRequestDto, id);
        return podcastResponseDto;
    }

    @PutMapping("podcasts/status")
    int updateStatus(@RequestBody UpdateStatus updateStatus){
        int status = this.podcastService.updatePodcastStatus(updateStatus);
        return status;
    }


    @GetMapping("podcasts/own")
    List<PodcastResponseDto> getOnwPodcasts(@PathParam("page") int page,@PathParam("size") int size,Principal principal){
        List<PodcastResponseDto> onwPodcasts = this.podcastService.getOnwPodcasts(principal.getName(), page, size);
        return onwPodcasts;
    }


    @GetMapping("podcasts/podcaster/{id}")
    List<PodcastResponseDto> getPodcasterPodcasts(@PathVariable Long id,@PathParam("page") int page, @PathParam("size") int size){
        List<PodcastResponseDto> podcasterPodcasts = this.podcastService.getPodcasterPodcasts(id, page, size);
        return podcasterPodcasts;
    }
}
