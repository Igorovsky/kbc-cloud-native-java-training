package com.example.hibernateexersice.service;

import com.example.hibernateexersice.repository.BookRepository;
import com.example.hibernateexersice.service.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public List<Book> list() {
        return bookRepository.findAll();
    }
}
