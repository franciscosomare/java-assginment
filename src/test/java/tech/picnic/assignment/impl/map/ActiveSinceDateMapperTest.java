package tech.picnic.assignment.impl.map;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ActiveSinceDateMapperTest {

    @Test
    public void shouldActiveSinceBeMappedCorrectly() throws Exception {
        // given

        // when
        Date date = ActiveSinceDateMapper.getActiveSinceDate("2020-06-22T11:45:00Z");

        // then
        assertEquals(1592837100000L, date.getTime());
    }
}