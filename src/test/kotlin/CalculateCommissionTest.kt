import org.junit.Assert.*
import org.junit.Test

class CalculateCommissionTest {
    @Test
    fun calculateCommission() {
        val amount = 1000
        val cardType = "VK Pay"
        val monthSent = 40_000

        val result = calculateCommission( amount, cdrdType, )

        assertEquals (expected = 50, actual = result)
    }

}