package org.javaee7.sample;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;

/**
 * @author Arun Gupta
 */
@RunWith(Arquillian.class)
public class PersonResourceTest extends BaseTest {

    /**
     * GET all resources
     */
    @Test
    @InSequence(1)
    public void testGetAll() {
        Person[] list = target.request().get(Person[].class);
        assertEquals(8, list.length);

        verifyInitialNames(list);
    }

    private void verifyInitialNames(Person[] list) {
        assertEquals("Penny", list[0].getName());
        assertEquals("Leonard", list[1].getName());
        assertEquals("Sheldon", list[2].getName());
        assertEquals("Amy", list[3].getName());
        assertEquals("Howard", list[4].getName());
        assertEquals("Bernadette", list[5].getName());
        assertEquals("Raj", list[6].getName());
        assertEquals("Priya", list[7].getName());
    }

    /**
     * GET a single resource
     */
    @Test
    @InSequence(2)
    public void testGetSingle() {
        Person p = target
                .path("{id}")
                .resolveTemplate("id", "1")
                .request(MediaType.APPLICATION_XML)
                .get(Person.class);
        assertEquals("Leonard", p.getName());
    }

    /**
     * GET another single resource
     */
    @Test
    @InSequence(3)
    public void testGetAnotherSingle() {
        Person p = target
                .path("{id}")
                .resolveTemplate("id", "7")
                .request(MediaType.APPLICATION_XML)
                .get(Person.class);
        assertEquals("Priya", p.getName());
    }

    /**
     * POST two resources
     */
    @Test
    @InSequence(4)
    public void testAdd2Names() {
        MultivaluedHashMap<String, String> map = new MultivaluedHashMap<>();
        map.add("name", "Leslie");
        target.request().post(Entity.form(map));

        map.clear();
        map.add("name", "Stuart");
        target.request().post(Entity.form(map));

        Person[] list = target.request().get(Person[].class);
        assertEquals(10, list.length);

        verifyInitialNames(list);

        assertEquals("Leslie", list[8].getName());
        assertEquals("Stuart", list[9].getName());
    }

    /**
     * DELETE two resources
     */
    @Test
    @InSequence(5)
    public void testDeleteTwoNames() {
        target
                .path("{name}")
                .resolveTemplate("name", "Leslie")
                .request()
                .delete();
        target
                .path("{name}")
                .resolveTemplate("name", "Stuart")
                .request()
                .delete();
        Person[] list = target.request().get(Person[].class);
        verifyInitialNames(list);
    }

}
