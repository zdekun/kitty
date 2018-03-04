package io.kitty.rest;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Person的Restful API的Controller.
 */
@RestController
@RequestMapping(value = "/api/v1/person")
public class PersonRestController {

    private static Logger logger = LoggerFactory.getLogger(PersonRestController.class);

    @RequestMapping(method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
    public List<Person> list() {
	List<Person> persons = new ArrayList<>();
	Person person = new Person();
	person.setId(1L);
	person.setAge(37);
	person.setName("xiaoyufeng");
	persons.add(person);
	return persons;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
    public Person get(@PathVariable("id") Long id) {
	logger.info("get person id:{}", id);
	Person person = new Person();
	person.setId(id);
	person.setAge(35);
	person.setName("zhangdekun");
	return person;
    }

    @RequestMapping(method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8, consumes = MediaTypes.JSON_UTF_8)
    public Person post(Person person) {
	logger.info("post person id:{}", person.getId());
	return person;
    }

    @RequestMapping(method = RequestMethod.PUT, produces = MediaTypes.JSON_UTF_8, consumes = MediaTypes.JSON_UTF_8)
    public Person put(Person person) {
	logger.info("put person id:{}", person.getId());
	return person;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaTypes.JSON_UTF_8, consumes = MediaTypes.JSON_UTF_8)
    public void delete(@PathVariable("id") Long id) {
	logger.info("delete person id:{}", id);
    }

}
