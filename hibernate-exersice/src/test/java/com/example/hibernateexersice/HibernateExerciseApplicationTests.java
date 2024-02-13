package com.example.hibernateexersice;

import com.example.hibernateexersice.service.BookService;
import com.example.hibernateexersice.service.model.Book;
import org.assertj.core.api.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class HibernateExerciseApplicationTests {

	@Autowired
	private BookService bookService;

	@Test
	void loads_books_positive_test() {

		List<Book> books = bookService.list();

		assertEquals(books.size(), 1);
	}

}
