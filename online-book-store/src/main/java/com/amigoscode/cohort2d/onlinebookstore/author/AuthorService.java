package com.amigoscode.cohort2d.onlinebookstore.author;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private AuthorDAO authorDAO;
    private AuthorDTOMapper authorDTOMapper;


}
