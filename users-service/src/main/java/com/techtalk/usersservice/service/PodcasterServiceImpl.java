package com.techtalk.usersservice.service;

import com.techtalk.usersservice.model.mappers.PodcasterMapper;
import com.techtalk.usersservice.model.request.PodcasterRequestDto;
import com.techtalk.usersservice.model.response.PodcasterResponseDto;
import com.techtalk.usersservice.persistence.Podcaster;
import com.techtalk.usersservice.persistence.PodcasterRepository;
import com.techtalk.usersservice.utils.FileService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PodcasterServiceImpl implements PodcasterService{
    private PodcasterRepository podcasterRepository;
    private PodcasterMapper podcasterMapper;
    private PasswordEncoder passwordEncoder;
    private FileService fileService;

    public PodcasterServiceImpl(PodcasterRepository podcasterRepository,
                                PodcasterMapper podcasterMapper,
                                PasswordEncoder passwordEncoder,
                                FileService fileService) {
        this.podcasterRepository = podcasterRepository;
        this.podcasterMapper = podcasterMapper;
        this.passwordEncoder = passwordEncoder;
        this.fileService = fileService;
    }

    @Override
    public PodcasterResponseDto register(PodcasterRequestDto podcasterRequestDto) {
        // Map the PodcasterRequestDto to the Podcaster Entity
        Podcaster podcaster = this.podcasterMapper.PodcasterReqDto_to_Podcaster(podcasterRequestDto);

        podcaster.setPassword(this.passwordEncoder.encode(podcaster.getPassword()));

        this.podcasterRepository.save(podcaster);
        PodcasterResponseDto podcasterResponseDto = this.podcasterMapper.Podcaster_to_PodcasterResponse(podcaster);
        return podcasterResponseDto;
    }

    @Override
    public Optional<Podcaster> loadPodcasterByEmail(String email) {
        Optional<Podcaster> podcasterByEmail = this.podcasterRepository.findPodcasterByEmail(email);
        return podcasterByEmail;
    }

    @Override
    public PodcasterResponseDto getPodcasterByEMail(String email) {
        Optional<Podcaster> podcasterByEmail = this.podcasterRepository.findPodcasterByEmail(email);
        if(podcasterByEmail.isPresent()) {
            PodcasterResponseDto podcasterResponseDto = this.podcasterMapper.Podcaster_to_PodcasterResponse(podcasterByEmail.get());
            return podcasterResponseDto;
        }
        return null;
    }

    @Override
    public PodcasterResponseDto getPodcasterById(Long id) {
        Optional<Podcaster> podcasterById= this.podcasterRepository.findById(id);
        if(podcasterById.isPresent()) {
            PodcasterResponseDto podcasterResponseDto = this.podcasterMapper.Podcaster_to_PodcasterResponse(podcasterById.get());
            return podcasterResponseDto;
        }
        return null;
    }

    @Override
    public List<PodcasterResponseDto> getPodcasterByNameContains(String name) {
        List<Podcaster> podcasters = this.podcasterRepository.findPodcasterByNameContainingIgnoreCase(name);
        List<PodcasterResponseDto> podcasterResponseDtos = this.podcasterMapper.LIST_PODCASTER_TO_LIST_PODCASTER_RESPONSE_DTO(podcasters);
        return podcasterResponseDtos;
    }

    @Override
    public List<PodcasterResponseDto> getAllPodcasters(int page, int size,Integer verified) {
        Pageable pageable = PageRequest.of(page,size);
        Page<Podcaster> podcasters;
        if(verified!=null) {
            podcasters = this.podcasterRepository.findByVerified(verified,pageable);
        }else{
            podcasters = this.podcasterRepository.findAll(pageable);
        }

        List<PodcasterResponseDto> podcasterResponseDtos = this.podcasterMapper.LIST_PODCASTER_TO_LIST_PODCASTER_RESPONSE_DTO(podcasters.getContent());
        return podcasterResponseDtos;
    }

    @Override
    public int verifiedMultiplePodcasters(int newVerified, List<Long> ids ) {
        int updateVerifiedForPodcaster = this.podcasterRepository.updateVerifiedForPodcaster(newVerified, ids);
        return updateVerifiedForPodcaster;
    }

    @Override
    public int updateOwnProfile(PodcasterRequestDto podcasterRequestDto, String email) {
        Optional<Podcaster> podcasterByEmail = this.podcasterRepository.findPodcasterByEmail(email);
        if(podcasterByEmail.isPresent()){
            if(podcasterRequestDto.getPic()!=null){
                Object upload = this.fileService.upload(podcasterRequestDto.getPic());
                if(upload instanceof String picName) podcasterByEmail.get().setProfilePic(picName);
            }
            if(podcasterRequestDto.getEmail()!=null) podcasterByEmail.get().setEmail(podcasterRequestDto.getEmail());
            if(podcasterRequestDto.getBio()!=null) podcasterByEmail.get().setBio(podcasterRequestDto.getBio());
            if(podcasterRequestDto.getName()!=null) podcasterByEmail.get().setName(podcasterRequestDto.getName());
            if(podcasterRequestDto.getPhoneNumber()!=null) podcasterByEmail.get().setPhoneNumber(podcasterRequestDto.getPhoneNumber());
            if(podcasterRequestDto.getLinkedinProfile()!=null) podcasterByEmail.get().setLinkedinProfile(podcasterRequestDto.getLinkedinProfile());
            Podcaster save = this.podcasterRepository.save(podcasterByEmail.get());
            if(save!=null){
                return 1;
            }
        }
        return 0;
    }
}
