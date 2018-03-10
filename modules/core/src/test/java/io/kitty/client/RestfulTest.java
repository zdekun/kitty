package io.kitty.client;

import io.kitty.client.impl.spring.HttpRestful;
import io.kitty.domain.Person;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.http.client.HttpComponentsAsyncClientHttpRequestFactory;
import org.springframework.web.client.AsyncRestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class RestfulTest {
    private static HttpRestful httpRestful;
    private static final String BASE_PERSON = "http://127.0.0.1:8080/Demo/api/v1/person";
    private static final String PERSON_ID = BASE_PERSON + "/{id}";

    @BeforeClass
    public static void beforeClass() {
        httpRestful = new HttpRestful();
        HttpComponentsAsyncClientHttpRequestFactory requestFactory = new HttpComponentsAsyncClientHttpRequestFactory();
        requestFactory.afterPropertiesSet();
        AsyncRestTemplate asyncRestTemplate = new AsyncRestTemplate(requestFactory);
        httpRestful.setAsyncRestTemplate(asyncRestTemplate);
    }

    @After
    public void after() {
        try {
            TimeUnit.MILLISECONDS.sleep(200);
        } catch (InterruptedException e) {
        }
    }

    @Test
    public void getForEntityByClassTest() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("id", Integer.toString(123));
        ResponseEntity<Person> responseEntity = httpRestful.getForEntity(PERSON_ID, null, Person.class, params, 100L);

        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(200, responseEntity.getStatusCodeValue());
        Person responsePerson = responseEntity.getBody();
        Assert.assertNotNull(responsePerson);
    }

    @Test
    public void getForEntityByTypeReferenceTest() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("id", Integer.toString(123));
        ResponseEntity<Person> responseEntity = httpRestful.getForEntity(PERSON_ID, null, new TypeReference<Person>() {
        }, params, 100L);

        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(200, responseEntity.getStatusCodeValue());
        Person responsePerson = responseEntity.getBody();
        Assert.assertNotNull(responsePerson);
    }

    @Test
    public void getForEntity4ListTest() {
        ResponseEntity<List<Person>> responseEntity = httpRestful.getForEntity(BASE_PERSON, null,
                new TypeReference<List<Person>>() {
                }, null, 100L);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(200, responseEntity.getStatusCodeValue());

        List<Person> persons = responseEntity.getBody();
        Assert.assertNotNull(persons);
        for (Person person : persons) {
            Assert.assertNotNull(person);
        }
    }

    @Test
    public void postForEntityByClassTest() {
        HttpEntity<Person> httpEntity = buildHttpEntity();
        ResponseEntity<Person> responseEntity = httpRestful.postForEntity(BASE_PERSON, httpEntity, Person.class, null,
                100L);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(200, responseEntity.getStatusCodeValue());
        Person responsePerson = responseEntity.getBody();
        Assert.assertNotNull(responsePerson);
    }

    @Test
    public void postForEntityByTypeReferenceTest() {
        HttpEntity<Person> httpEntity = buildHttpEntity();
        ResponseEntity<Person> responseEntity = httpRestful.postForEntity(BASE_PERSON, httpEntity,
                new TypeReference<Person>() {
                }, null, 100L);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(200, responseEntity.getStatusCodeValue());
        Person responsePerson = responseEntity.getBody();
        Assert.assertNotNull(responsePerson);
    }

    @Test
    public void asyncPostForEntityByClassTest() throws InterruptedException, ExecutionException {
        HttpEntity<Person> httpEntity = buildHttpEntity();
        Future<ResponseEntity<Person>> future = httpRestful.asyncPostForEntity(BASE_PERSON, httpEntity, Person.class,
                null, new InnerAsyncCallback());
        ResponseEntity<Person> responseEntity = future.get();
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(200, responseEntity.getStatusCodeValue());
        Assert.assertNotNull(responseEntity.getBody());
    }

    @Test
    public void asyncPostForEntityByTypeReferenceTest() throws InterruptedException, ExecutionException {
        HttpEntity<Person> httpEntity = buildHttpEntity();
        Future<ResponseEntity<Person>> future = httpRestful.asyncPostForEntity(BASE_PERSON, httpEntity,
                new TypeReference<Person>() {
                }, null, new InnerAsyncCallback());
        ResponseEntity<Person> responseEntity = future.get();
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(200, responseEntity.getStatusCodeValue());
        Assert.assertNotNull(responseEntity.getBody());
    }

    @Test
    public void putForEntityByClassTest() {
        HttpEntity<Person> httpEntity = buildHttpEntity();
        ResponseEntity<Person> responseEntity = httpRestful.putForEntity(BASE_PERSON, httpEntity, Person.class, null,
                100L);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(200, responseEntity.getStatusCodeValue());
        Person responsePerson = responseEntity.getBody();
        Assert.assertNotNull(responsePerson);
    }

    @Test
    public void putForEntityByTypeReferenceTest() {
        HttpEntity<Person> httpEntity = buildHttpEntity();
        ResponseEntity<Person> responseEntity = httpRestful.putForEntity(BASE_PERSON, httpEntity,
                new TypeReference<Person>() {
                }, null, 100L);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(200, responseEntity.getStatusCodeValue());
        Person responsePerson = responseEntity.getBody();
        Assert.assertNotNull(responsePerson);
    }

    @Test
    public void asyncPutForEntityByClassTest() throws InterruptedException, ExecutionException {
        HttpEntity<Person> httpEntity = buildHttpEntity();
        Future<ResponseEntity<Person>> future = httpRestful.asyncPutForEntity(BASE_PERSON, httpEntity, Person.class,
                null, new InnerAsyncCallback());
        ResponseEntity<Person> responseEntity = future.get();
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(200, responseEntity.getStatusCodeValue());
        Assert.assertNotNull(responseEntity.getBody());
    }

    @Test
    public void asyncPutForEntityByTypeReferenceTest() throws InterruptedException, ExecutionException {
        HttpEntity<Person> httpEntity = buildHttpEntity();
        Future<ResponseEntity<Person>> future = httpRestful.asyncPutForEntity(BASE_PERSON, httpEntity,
                new TypeReference<Person>() {
                }, null, new InnerAsyncCallback());
        ResponseEntity<Person> responseEntity = future.get();
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(200, responseEntity.getStatusCodeValue());
        Assert.assertNotNull(responseEntity.getBody());
    }

    private HttpEntity<Person> buildHttpEntity() {
        Person person = new Person();
        person.setId(35);
        person.setAge(35);
        person.setName("zhangdekun");

        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json");

        HttpEntity<Person> httpEntity = new HttpEntity<Person>(person, headers);
        return httpEntity;
    }

    @Test
    public void deleteForEntityTest() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("id", Integer.toString(123));
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json");
        ResponseEntity<Void> responseEntity = httpRestful.deleteForEntity(PERSON_ID, headers, params, 100L);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(200, responseEntity.getStatusCodeValue());
        Assert.assertNull(responseEntity.getBody());
    }

    @Test
    public void asyDeleteForEntityTest() throws InterruptedException, ExecutionException {
        Map<String, String> params = new HashMap<String, String>();
        params.put("id", Integer.toString(123));
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json");
        Future<ResponseEntity<Void>> future = httpRestful.asyncDeleteForEntity(PERSON_ID, headers, params,
                new AsyncCallback<Void>() {

                    @Override
                    public void onSuccess(ResponseEntity<Void> responseEntity) {
                        Assert.assertNotNull(responseEntity);
                        Assert.assertEquals(200, responseEntity.getStatusCodeValue());
                        Assert.assertNull(responseEntity.getBody());
                    }

                    @Override
                    public void onFailure(Throwable ex) {
                        Assert.fail();
                    }
                });

        ResponseEntity<Void> responseEntity = future.get();
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(200, responseEntity.getStatusCodeValue());
        Assert.assertNull(responseEntity.getBody());
    }

    private static class InnerAsyncCallback implements AsyncCallback<Person> {

        @Override
        public void onSuccess(ResponseEntity<Person> responseEntity) {
            Assert.assertNotNull(responseEntity);
            Assert.assertEquals(200, responseEntity.getStatusCodeValue());
            Assert.assertNotNull(responseEntity.getBody());
        }

        @Override
        public void onFailure(Throwable ex) {
            Assert.fail();
        }
    }
}
