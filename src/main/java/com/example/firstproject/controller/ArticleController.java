package com.example.firstproject.controller;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@Slf4j
public class ArticleController {
    @Autowired
    private ArticleRepository articleRepository;

    @GetMapping("/articles/new")
    public String newArticleForm() {
        return "articles/new";
    }

    @PostMapping("/articles/create")
    public String createArticle(ArticleForm form) {
        System.out.println(form.toString());
        Article article = form.toEntity();
        log.info(article.toString());
        //System.out.println(article.toString());

        Article saved = articleRepository.save(article);
        //System.out.println(saved.toString());
        log.info(saved.toString());
        return "redirect:/articles/" + saved.getId();
    }

    @GetMapping("/articles/{id}")
    public String show(@PathVariable Long id, Model model){
        log.info("id = "+ id);

        //id를 조회해서 데이터 가져오기
        //Optional<Article> articleEntity = articleRepository.findById(id);
        Article articleEntity =articleRepository.findById(id).orElse(null);

        //모델에 데이터 등록하기

        model.addAttribute("article",articleEntity);


        //뷰 페이지 반환하기
        return "articles/show";
    }
    @GetMapping("/articles")
    public String index(Model model){
        //모든 데이터 가져오기
        List<Article> articleEntityList = articleRepository.findAll();

        //모델에 데티어 등록하기
        model.addAttribute("articleList",articleEntityList);

        //사용자에게 보여 줄 뷰 페이지 설정하기
        return "articles/index";

    }
    @GetMapping("/articles/{id}/edit")
    public String edit(@PathVariable Long id, Model model){
        //수정할 데이터 가져오기
        Article articleEntity = articleRepository.findById(id).orElse(null);

        //모델에 데이터 등록하기
        model.addAttribute("article",articleEntity);

        //뷰 페이지 설정
        return "articles/edit";
    }


}
