package com.tookscan.tookscan.term.application.service;

import com.tookscan.tookscan.term.application.dto.request.CreateAdminTermRequestDto;
import com.tookscan.tookscan.term.application.usecase.CreateAdminTermUseCase;
import com.tookscan.tookscan.term.domain.Term;
import com.tookscan.tookscan.term.domain.service.TermService;
import com.tookscan.tookscan.term.repository.mysql.TermRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateAdminTermService implements CreateAdminTermUseCase {

    private final TermRepository termRepository;

    private final TermService termService;

    @Override
    public void execute(CreateAdminTermRequestDto requestDto) {

        // Term 생성
        Term term = termService.createTerm(
                requestDto.type(),
                requestDto.title(),
                requestDto.content(),
                requestDto.isRequired()
        );

        termRepository.save(term);

    }
}
