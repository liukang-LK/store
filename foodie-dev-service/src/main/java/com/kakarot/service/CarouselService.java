package com.kakarot.service;

import com.kakarot.pojo.Carousel;

import java.util.List;

public interface CarouselService {

    public List<Carousel> queryAll(Integer isShow);

}
