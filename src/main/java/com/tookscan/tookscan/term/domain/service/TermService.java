package com.tookscan.tookscan.term.domain.service;

import com.tookscan.tookscan.term.domain.Term;
import com.tookscan.tookscan.term.domain.type.ETermType;
import org.springframework.stereotype.Service;

@Service
public class TermService {

    public Term createTerm(String type, String title, String content, Boolean isRequired, Boolean isVisible) {
        return Term.builder()
                .type(ETermType.fromString(type))
                .title(title)
                .content(content)
                .isRequired(isRequired)
                .isVisible(isVisible)
                .build();
    }

    public Term updateTerm(Term term, String title, String content, Boolean isRequired, Boolean isVisible) {
        term.updateTitle(title);
        term.updateContent(content);
        term.updateIsRequired(isRequired);
        term.updateIsVisible(isVisible);
        return term;
    }
}
