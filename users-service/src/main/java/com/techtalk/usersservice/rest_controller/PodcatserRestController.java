package com.techtalk.usersservice.rest_controller;

import com.techtalk.usersservice.model.request.PodcasterRequestDto;
import com.techtalk.usersservice.model.request.UpdateVerified;
import com.techtalk.usersservice.model.response.PodcasterResponseDto;
import com.techtalk.usersservice.utils.FileService;
import com.techtalk.usersservice.service.PodcasterService;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;


@RestController
public class PodcatserRestController {

    private PodcasterService podcasterService;
    public PodcatserRestController(PodcasterService podcasterService) {
        this.podcasterService = podcasterService;
    }

    @PostMapping("/podcaster/register")
    PodcasterResponseDto register(@RequestBody  PodcasterRequestDto podcasterRequestDto){
        PodcasterResponseDto podcasterResponseDto = this.podcasterService.register(podcasterRequestDto);
        return podcasterResponseDto;
    }

    @PostMapping("/podcaster")
    PodcasterResponseDto getPodcasterByEmail(@RequestBody String email){
        if(email!=null){
            PodcasterResponseDto podcaster = this.podcasterService.getPodcasterByEMail(email);
            return podcaster;
        }else{
            return null;
        }
    }

    @GetMapping("podcaster/{id}")
    PodcasterResponseDto getPodcasterById(@PathVariable @NotNull @DecimalMin("0") Long id){
        PodcasterResponseDto podcaster = this.podcasterService.getPodcasterById(id);
        System.out.println(podcaster);
        return podcaster;
    }

    // GET PODCASTER BY HIS NAME
    @GetMapping("podcaster")
    List<PodcasterResponseDto> getPodcasterByNameContains(@RequestParam String name){
        List<PodcasterResponseDto> podcasterByNameContains = this.podcasterService.getPodcasterByNameContains(name);
        return podcasterByNameContains;
    }

    // GET THE LOGGED IN PODCASTER
    @GetMapping("podcaster/me")
    PodcasterResponseDto getPodcaster(Principal principal){
        PodcasterResponseDto podcasterResponseDto = this.podcasterService.getPodcasterByEMail(principal.getName());
        return podcasterResponseDto;
    }

    @GetMapping("podcasters")
    List<PodcasterResponseDto> getAllPodcasters(@RequestParam int page, @RequestParam int size,@RequestParam(required = false) Integer verified){
        List<PodcasterResponseDto> podcasters = this.podcasterService.getAllPodcasters(page, size,verified);
        return podcasters;
    }

    @PutMapping("podcasters")
    @PreAuthorize("hasAuthority('ADMIN')")
    int updateMultipleVerifiedPodcasters(@RequestBody UpdateVerified data){
        if(data.getNewVerified()!=null && !data.getIds().isEmpty()){
            int verifiedMultiplePodcasters = this.podcasterService.verifiedMultiplePodcasters(data.getNewVerified(), data.getIds());
            return verifiedMultiplePodcasters;
        }
        return 0;
    }

    @PutMapping("podcasters/all")
    int updateOwnProfile(@ModelAttribute PodcasterRequestDto podcasterRequestDto, Principal principal){
        int i = this.podcasterService.updateOwnProfile(podcasterRequestDto, principal.getName());
        return i;
    }
}
