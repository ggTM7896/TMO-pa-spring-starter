package com.galvanize.tmo.paspringstarter;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.*;

import org.json.JSONObject;
import org.json.JSONArray;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
public class LibraryController {

	List<Book> bookList = new ArrayList<>();
	Long bookId = 0L;

	public static Comparator<JSONObject> bookComparator = new Comparator<JSONObject>()
	{
	    public int compare(JSONObject o1, JSONObject o2) {
		    String str1 = (String) o1.get("title");
		    String str2 = (String) o2.get("title");
		    return str1.compareTo(str2);
	    }
    };

	@GetMapping("/api/books")
    String all() {
        JSONObject outer = new JSONObject();
        List<JSONObject> entities = new ArrayList<JSONObject>();

        for(Book item: bookList) {
            entities.add(item.toJSON());
        }

        Collections.sort(entities, bookComparator);

        outer.put("books", entities);
        return outer.toString();
    }

    @PostMapping("/api/books")
    public ResponseEntity<String> addBook(@RequestBody Book newBook) {
        System.out.println("POST /api/books");
        bookId += 1;
        newBook.setId(bookId);
        bookList.add(newBook);
        return new ResponseEntity<>(newBook.toJSON().toString(), HttpStatus.CREATED);
    }

    @DeleteMapping("/api/books")
    public ResponseEntity<String> deleteBook() {
        bookList.clear();
        bookId = 0L;
        return new ResponseEntity<>("", HttpStatus.NO_CONTENT);
    }

   // maps to @RequestMapping(method = RequestMethod.GET)
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        System.out.println("GET /health");
        return new ResponseEntity<>("I am healthy.", HttpStatus.OK);
    }

    @GetMapping("/hello")
    public ResponseEntity<String> hello() {
        System.out.println("GET /hello");
        return new ResponseEntity<>("Hello, World!", HttpStatus.OK);
    }
}
