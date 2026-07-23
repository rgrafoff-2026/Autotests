import org.junit.Assert.*
import org.junit.Test

class CalculateCommissionTest {
    @Test
    fun calculateCommission() {
        cardType: String,
        amount: Double,
        monthSent: Double = 0.0,
        monthReceived: Double = 0.0,
        daySent: Double = 0.0,
        dayReceived: Double = 0.0
        ): String
        val result = calculateCommission(cardType, amount, monthSent, monthReceived, daySent, dayReceived)
        assertEquals(100, result)

    }

}