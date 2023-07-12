package com.amigoscode.cohort2d.onlinebookstore.category;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private CategoryDAO categoryDAO;
    private CategoryDTOMapper categoryDTOMapper;


}
