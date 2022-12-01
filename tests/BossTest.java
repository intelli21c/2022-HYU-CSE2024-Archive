import static org.junit.Assert.assertTrue;

import org.junit.Test;

import entity.Entity;

public class BossTest {
    @Test
    public void testBossState() {
        Entity e = new Entity(0,0, 10, 10, null);
        Entity f = new Entity(5, 5, 10, 10, null);
        assertTrue(e.checkCollision(f));
    }
}
